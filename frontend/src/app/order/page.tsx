'use client';

import { useEffect, useState } from 'react';
import Header from '@/global/component/Header';
import { Item } from '@/global/interface/item';
import ItemDetail from '@/global/component/ItemDetail';
import { useCart } from '@/hooks/useCart';
import { apiFetch } from '@/lib/backend/client';

export default function OrderPage() {
  const [items, setItems] = useState<Item[]>([]);
  const { counts, cart, increase, decrease, totalAmount } = useCart(items);
  const [email, setEmail] = useState('');
  const [address, setAddress] = useState('');
  const [zipCode, setZipCode] = useState('');

  useEffect(() => {
    apiFetch('/api/v1/item/list')
      .then(setItems)
      .catch(error => alert(`${error.resultCode} : ${error.msg}`));
  }, []);

  const handleCheckout = () => {
    console.log('결제 내역:', { cart, email, address, zipCode });
    console.log(JSON.stringify({
      email,
      address,
      zipCode,
      orderItem: cart
    }));
  };

  return (
    <>
      {/* 헤더 */}
      <Header />

      {/* 본문 */}
      <div className="bg-gray-300 px-5 pt-20 pb-5">
        <div className="bg-white rounded-xl shadow-lg flex flex-col lg:flex-row overflow-hidden">
          {/* Left: Item List */}
          <div className="p-6 flex-1">
            <h2 className="text-black text-2xl font-bold mb-6">상품 목록</h2>
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

            {/* Checkout Button */}
            <button
              onClick={handleCheckout}
              className="w-full bg-black text-white py-4 rounded-lg font-bold hover:bg-gray-800 cursor-pointer"
            >
              결제하기
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
