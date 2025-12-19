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

  const order: Order = {
    id: id,
    email: 'ex@example.com',
    address: '서울 성동구 00동',
    zipCode: '12345',
    createDate: '2025-12-12T09:07:13.632499',
    modifyDate: '2025-12-12T10:07:13.632499',
    orderItem: [
      {
        id: 1,
        name: '에티오피아 콩',
        category: '커피콩',
        quantity: 3,
        price: 14000,
        imageUrl: 'http://www.naver.com'
      },
      {
        id: 3,
        name: '맥심 커피믹스',
        category: '커피',
        quantity: 5,
        price: 8000,
        imageUrl: 'http://www.google.com'
      }
    ],
    total: 32000
  };

  const [items, setItems] = useState<Item[]>([]);
  const { counts, setCounts, cart, setCart, increase, decrease, totalAmount } = useCart(items);
  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [zipCode, setZipCode] = useState('');

  useEffect(() => {
    apiFetch('/api/v1/item/list')
      .then(setItems)
      .catch(error => alert(`${error.resultCode} : ${error.msg}`));
  }, []);

  useEffect(() => {
    if (items.length === 0) return; // 상품 목록 로드 전이면 패스

    // 1. counts 세팅 (itemId → quantity)
    const nextCounts: Record<number, number> = {};

    order.orderItem.forEach((orderItem) => {
      nextCounts[orderItem.id] = orderItem.quantity;
    });

    setCounts(nextCounts);

    // 2. cart 세팅
    const nextCart = order.orderItem.map((orderItem) => ({
      itemId: orderItem.id,
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
    console.log('수정 내역:', { cart, email, address, zipCode });
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
                  className="text-black w-full px-4 py-2 border border-gray-300 rounded-lg bg-white"
                  placeholder="이메일을 입력하세요"
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
