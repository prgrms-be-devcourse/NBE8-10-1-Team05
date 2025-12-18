'use client';

import Button from "@/global/component/Button";
import Header from "@/global/component/Header";
import Link from "next/link";
import { useSearchParams } from "next/navigation";

interface Order {
  id: number;
  createDate: string;
}

const orders: Order[] = [
  {
    id: 5,
    createDate: "2025-12-12T09:07:13.632499"
  },
  {
    id: 4,
    createDate: "2025-12-12T09:07:13.632499"
  },
  {
    id: 3,
    createDate: "2025-12-12T09:07:13.632499"
  },
  {
    id: 2,
    createDate: "2025-12-12T09:07:13.632499"
  },
  {
    id: 1,
    createDate: "2025-12-12T09:07:13.632499"
  },
];


export default function Page() {
  // listByEmail?email=ex@example.com 형식을 받아오기
  const searchParams = useSearchParams();
  const email = searchParams.get('email');

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
          <h2 className="text-black text-2xl font-bold mb-2">나의 주문 내역</h2>
          <div className="text-black font-medium mb-6">나의 이메일: {email}</div>
          {/* Order List */}
          <div className="flex flex-col border-3 border-gray-200 rounded-md">
            {orders.length == 0
              ? <div className="text-black">주문 내역이 없습니다.</div>
              : orders.map((order) => (
                <div key={order.id} className="p-5 border-b-3 border-gray-200 flex items-center justify-between">
                  <div>
                    <Link href={`/order/detail/${order.id}`} className="text-blue-800 underline">
                      주문 번호: {order.id}
                    </Link>
                    <div className="text-black">주문 시각: {order.createDate.replace("T", " ").substring(0, 19)}</div>
                  </div>
                  {/* Buttons */}
                  <div className="flex gap-2">
                    <Link href={`/order/modify/${order.id}`}>
                      <Button color="blue" content="주문서 수정" />
                    </Link>
                    <Button color="red" content="주문 취소" handler={() => handleCancelOrder(order.id)} />
                  </div>
                </div>
              ))}
          </div>
        </div>
      </div>
    </>
  );
}
