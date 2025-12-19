'use client';

import Button from "@/global/component/Button";
import Header from "@/global/component/Header";
import { Order } from "@/global/interface/order";
import Link from "next/link";
import { useParams } from "next/navigation";

export default function Page() {
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

  const handleCancelOrder = (orderId: number): void => {
    if (!confirm(`${orderId}번 주문을 정말로 취소하시겠습니까?`)) return;
    // TODO: 주문 취소 api 연동
  }

  return (
    <>
      {/* 헤더 */}
      <Header />

      {/* 본문 */}
      <div className="bg-gray-300 px-5 pt-20 pb-5 flex-1">
        <div className="bg-white rounded-xl shadow-lg p-6">
          <h2 className="text-black text-2xl font-bold mb-6">주문 내역 상세 조회</h2>
          <div className="text-black font-medium mb-1">나의 이메일: {order.email}</div>
          <div className="text-black font-medium mb-1">주소: {order.address}</div>
          <div className="text-black font-medium mb-1">우편번호: {order.zipCode}</div>
          <div className="text-black font-medium mb-6">총금액: {order.total}원</div>
          <div className="text-black text-xl font-bold mb-6">상품 목록</div>
          {order.orderItem.length == 0
            ? <div className="text-black">구매한 상품이 없습니다.</div>
            : order.orderItem.map((item) => (
              <div key={item.id} className="flex items-center gap-5 p-5 border-b border-gray-200">
                {/* item Image */}
                <div className="w-20 h-20 bg-gray-100 rounded-lg shrink-0 flex items-center justify-center overflow-hidden">
                  <img
                    className="w-full h-full object-cover"
                    src={item.imageUrl}
                    alt="이미지"
                  />
                </div>

                {/* item Info */}
                <div className="flex-1">
                  <h3 className="text-gray-600">{item.category}</h3>
                  <div className='text-black font-medium'>{item.name}</div>
                </div>

                {/* Price */}
                <div className="text-black font-medium">
                  {item.price}원
                </div>

                {/* quantity */}
                <span className="bg-gray-800 text-sm text-white font-bold px-2 py-1 rounded-md">
                  {item.quantity}개
                </span>
              </div>
            ))}
          {/* Buttons */}
          <div className="flex gap-2 mt-6">
            <Link href={`/order/listByEmail`}>
              <Button color="gray" content="주문 목록" />
            </Link>
            <Link href={`/order/modify/${order.id}`}>
              <Button color="blue" content="주문서 수정" />
            </Link>
            <Button color="red" content="주문 취소" handler={() => handleCancelOrder(order.id)} />
          </div>
        </div>
      </div>
    </>
  );
}
