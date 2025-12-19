'use client';

import Link from "next/link";

export default function Header() {
  return (
    <header className="w-full bg-gray-800 text-white py-3 fixed">
      <div className="flex items-center justify-between px-5">
        <Link href="/order">
          <h1 className="text-2xl font-bold hover:text-gray-300 cursor-pointer">Grids & Circle</h1>
        </Link>
        <div className="flex items-center gap-6">
          <Link href="/order/listByEmail" className="hover:text-gray-300 cursor-pointer">
            나의 주문 내역
          </Link>
          <Link href="/" className="hover:text-gray-300 cursor-pointer">
            로그아웃
          </Link>
        </div>
      </div>
    </header>
  );
}
