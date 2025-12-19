'use client';

import Link from "next/link";
import { useRouter } from "next/navigation";

export default function Header() {
  const router = useRouter();

  const handleLogout = () => {
    if (!confirm('브라우저에 저장된 이메일을 지우고 초기 화면으로 돌아가시겠어요?')) {
      return;
    }
    // 로컬 스토리지에 저장된 이메일 삭제
    localStorage.removeItem('email');

    // 초기 화면으로 이동
    router.push('/');
  }

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
          <div onClick={handleLogout} className="hover:text-gray-300 cursor-pointer">
            로그아웃
          </div>
        </div>
      </div>
    </header>
  );
}
