'use client';

import { useEffect, useState } from 'react';
import Header from '@/global/component/Header';
import { Item } from '@/global/interface/item';
import ItemDetail from '@/global/component/ItemDetail';
import { useCart } from '@/hooks/useCart';
import { apiFetch } from '@/lib/backend/client';
import Link from 'next/link';
import Button from '@/global/component/Button';
import { useParams, useRouter } from 'next/navigation';
import { Order } from '@/global/interface/order';

export default function Page() {
  const router = useRouter();
  const { id: idStr } = useParams<{ id: string }>();
  const id = parseInt(idStr);

  const [items, setItems] = useState<Item[]>([]);
  const { counts, setCounts, cart, setCart, increase, decrease, totalAmount } = useCart(items);
  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [zipCode, setZipCode] = useState('');
  const [order, setOrder] = useState<Order | null>(null);

  useEffect(() => {
    // 주문 단건 조회
    apiFetch(`/api/v1/order/detail/${id}`)
      .then(setOrder)
      .catch(error => alert(`${error.resultCode} : ${error.msg}`));

    // 상품 목록 불러오기
    apiFetch('/api/v1/item/list')
      .then((data) => setItems(data.data))
      .catch(error => alert(`${error.resultCode} : ${error.msg}`));
  }, []);

  useEffect(() => {
    if (!order) return; // 주문 목록 로드 전이면 패스
    if (items.length === 0) return; // 상품 목록 로드 전이면 패스

    // 1. counts 세팅 (itemId → quantity)
    const nextCounts: Record<number, number> = {};

    order.orderItems.forEach((orderItem) => {
      nextCounts[orderItem.itemId] = orderItem.quantity;
    });

    setCounts(nextCounts);

    // 2. cart 세팅
    const nextCart = order.orderItems.map((orderItem) => ({
      itemId: orderItem.itemId,
      name: orderItem.name,
      quantity: orderItem.quantity,
    }));

    setCart(nextCart);

    // 3. 배송 정보 세팅  
    setEmail(order.email);
    setAddress(order.address);
    setZipCode(order.zipCode);
  }, [items]);

  const handleModify = () => {
    // 상품 개수가 0개인지 확인
    if (cart.length == 0) {
      alert('1개 이상의 상품을 선택해주세요.');
      return;
    }

    // 주소, 우편번호 유효성 검사
    if (!address || address.trim() === '') {
      alert('주소를 입력해주세요.');
      return;
    }

    if (!zipCode || zipCode.trim() === '') {
      alert('우편번호를 입력해주세요.');
      return;
    }

    apiFetch(`/api/v1/order/modify/${id}`, {
      method: 'PUT',
      body: JSON.stringify({
        email,
        address,
        zipCode,
        items: cart
      })
    })
      .then(() => {
        alert('수정이 완료되었습니다.');
        router.push(`/order/detail/${id}`);
      })
      .catch(error => alert(`${error.resultCode} : ${error.msg}`));
  };

  const handleCancelOrder = (orderId: number): void => {
    if (!confirm(`${orderId}번 주문을 정말로 취소하시겠습니까?`)) return;
    // 주문 취소 api 연동
    apiFetch(`/api/v1/order/cancel/${orderId}`, {
      method: 'DELETE'
    })
      .then(() => {
        alert('주문이 취소되었습니다.');
        router.replace('/order/listByEmail');
      })
      .catch(error => alert(`${error.resultCode} : ${error.msg}`));
  }

  if (!order) return <div>로딩 중...</div>;

  return (
    <>
      {/* 헤더 */}
      <Header />

      {/* 본문 */}
      <div className="bg-gray-300 px-5 pt-20 pb-5">
        <div className="bg-white rounded-xl shadow-lg flex flex-col lg:flex-row overflow-hidden">
          {/* Left: Item List */}
          <div className="p-6 flex-1">
            <h2 className="text-black text-2xl font-bold mb-6">주문서 수정</h2>
            <div className="text-black font-medium mb-6">주문 번호: {order.id}</div>
            <div className="text-black text-xl font-bold mb-6">상품 목록</div>
            {items.length == 0
              ? <div>상품이 없습니다.</div>
              : items.map((item) => (
                <ItemDetail
                  key={item.id}
                  item={item}
                  count={counts[item.id] ?? 0}
                  onIncrease={() => increase(item)}
                  onDecrease={() => decrease(item)}
                />
              ))}
          </div>

          {/* Right: Order Summary */}
          <div className="bg-gray-200 p-6 shrink-0">
            <h2 className="text-black text-2xl font-bold mb-6">Summary</h2>

            {/* Selected Items */}
            <div className="mb-6 flex flex-col gap-2">
              {cart.length === 0 ? (
                <div className="text-gray-500 text-sm">장바구니가 비어있습니다.</div>
              ) : (
                cart.map((item) => (
                  <div key={item.itemId} className="flex items-center gap-2">
                    <span className="text-black">{item.name}</span>
                    <span className="bg-gray-800 text-sm text-white font-bold px-2 py-1 rounded-md">
                      {item.quantity}개
                    </span>
                  </div>
                ))
              )}
            </div>

            {/* Form Fields */}
            <div className="flex flex-col gap-4 mb-6">
              <div>
                <div className="text-black mb-2 font-medium">이메일</div>
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="text-black w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-100"
                  placeholder="이메일을 입력하세요"
                  readOnly // 수정 불가
                />
              </div>

              <div>
                <div className="text-black mb-2 font-medium">주소</div>
                <input
                  type="text"
                  value={address}
                  onChange={(e) => setAddress(e.target.value)}
                  className="text-black w-full px-4 py-2 border border-gray-300 rounded-lg bg-white"
                  placeholder="주소를 입력하세요"
                />
              </div>

              <div>
                <div className="text-black mb-2 font-medium">우편번호</div>
                <input
                  type="text"
                  value={zipCode}
                  onChange={(e) => setZipCode(e.target.value)}
                  className="text-black w-full px-4 py-2 border border-gray-300 rounded-lg bg-white"
                  placeholder="우편번호를 입력하세요"
                />
              </div>
            </div>

            {/* Delivery Information */}
            <div className="text-black mb-6">당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>

            {/* Total Amount */}
            <div className="flex justify-between items-center mb-6">
              <span className="text-black text-xl font-medium">총금액</span>
              <span className="text-black text-2xl font-bold">{totalAmount}원</span>
            </div>

            {/* Buttons */}
            <button
              onClick={handleModify}
              className="w-full bg-black text-white py-4 rounded-lg font-bold hover:bg-gray-800 cursor-pointer"
            >
              수정하기
            </button>
            <div className="flex gap-2 mt-6 justify-center">
              <Link href={`/order/detail/${order.id}`}>
                <Button color="gray" content="돌아가기" />
              </Link>
              <Button color="red" content="주문 취소" handler={() => handleCancelOrder(order.id)} />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
