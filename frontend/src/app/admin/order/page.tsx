'use client';

import Header from "@/global/component/Header";
import { AdminOrder } from "@/global/interface/order";
import { useEffect, useState } from "react";

const sampleOrder: AdminOrder[] = [
  {
    email: 'ex1@example.com',
    address: '부산',
    zipCode: '12341',
    combinedOrderItems: [
      {
        id: 1,
        name: '에티오피아 콩',
        quantity: 3
      },
      {
        id: 3,
        name: '맥심 커피믹스',
        quantity: 5
      }
    ]
  },
  {
    email: 'ex2@example.com',
    address: '대전',
    zipCode: '01234',
    combinedOrderItems: [
      {
        id: 2,
        name: '콜롬비아 커피 콩',
        quantity: 1
      },
      {
        id: 3,
        name: '맥심 커피믹스',
        quantity: 3
      }
    ]
  }
]

export default function page() {
  const [date, setDate] = useState(new Date('2025-12-17'));
  const [orders, setOrders] = useState<AdminOrder[]>([]);

  useEffect(() => {
    setOrders(sampleOrder);
  }, []);

  const handlePrev = () => {
    setDate(prev => {
      const d = new Date(prev);
      d.setDate(d.getDate() - 1);
      return d;
    });
  };

  const handleNext = () => {
    setDate(prev => {
      const d = new Date(prev);
      d.setDate(d.getDate() + 1);
      return d;
    });
  };

  // yyyy-mm-dd
  const toDisplayString = (d: Date) => {
    return `${d.getFullYear()}-${d.getMonth() + 1}-${d.getDate()}`;
  };

  return (
    <>
      {/* 헤더 */}
      <Header />

      {/* 본문 */}
      <div className="bg-gray-300 px-5 pt-20 pb-5 flex-1">
        <div className="bg-white rounded-xl shadow-lg p-6">
          <h2 className="text-black text-2xl font-bold mb-6">주문 내역 조회</h2>
          {/* 날짜 정보 */}

          <div className="flex items-center justify-center w-full mb-6">
            <button
              className="px-6 py-2 bg-black text-white rounded border-black font-semibold mr-4 transition hover:bg-gray-700 cursor-pointer"
              onClick={handlePrev}
              type="button"
            >
              전날
            </button>
            <div className="flex-1 flex justify-center items-center border-2 border-black rounded px-4 py-2 bg-white text-black font-medium mx-2">
              {toDisplayString(date)} 14:00 배송 예정
            </div>
            <button
              className="px-6 py-2 bg-black text-white rounded border-black font-semibold ml-4 transition hover:bg-gray-700 cursor-pointer"
              onClick={handleNext}
              type="button"
            >
              다음날
            </button>
          </div>


          {/* 주문 정보 */}
          <div className="flex flex-col border-3 border-gray-300 rounded-md">
            {orders.length == 0
              ? <div className="text-black">주문 내역이 없습니다.</div>
              : orders.map((order) => (
                <div key={order.email} className="p-5 border-b-3 border-gray-300">
                  <div className="text-black font-medium mb-1">주문 이메일: {order.email}</div>
                  <div className="text-black font-medium mb-1">배송 주소: {order.address}</div>
                  <div className="text-black font-medium mb-3">상품 목록</div>
                  <div className="flex flex-col border border-gray-200 rounded-md">
                    {order.combinedOrderItems.length == 0
                      ? <div className="text-black">구매한 상품이 없습니다.</div>
                      : order.combinedOrderItems.map((item) => (
                        <div key={item.id} className="flex items-center gap-5 p-5 border-b border-gray-200">
                          {/* item Info */}
                          <div className="flex-1">
                            <div className='text-black font-medium'>{item.name}</div>
                          </div>

                          {/* quantity */}
                          <span className="bg-gray-800 text-sm text-white font-bold px-2 py-1 rounded-md">
                            {item.quantity}개
                          </span>
                        </div>
                      ))}
                  </div>
                </div>
              ))
            }
          </div>
        </div>
      </div>
    </>
  );
}