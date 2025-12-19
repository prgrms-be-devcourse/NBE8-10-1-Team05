'use client';

import Button from "@/global/component/Button";
import Header from "@/global/component/Header";
import { Order } from "@/global/interface/order";
import { apiFetch } from "@/lib/backend/client";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function Page() {
  const router = useRouter();
  const { id: idStr } = useParams<{ id: string }>();
  const id = parseInt(idStr);
  const [order, setOrder] = useState<Order | null>(null);

  useEffect(() => {
    apiFetch(`/api/v1/order/detail/${id}`)
      .then(setOrder)
      .catch(error => alert(`${error.resultCode} : ${error.msg}`));
  }, []);

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

  if (!order) return <div>로딩 중..</div>;

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
          {order.orderItems.length == 0
            ? <div className="text-black">구매한 상품이 없습니다.</div>
            : order.orderItems.map((item) => (
              <div key={item.itemId} className="flex items-center gap-5 p-5 border-b border-gray-200">
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
