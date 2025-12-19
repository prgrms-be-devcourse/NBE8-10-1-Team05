// 클라이언트 컴포넌트로 설정
"use client";

import { useRouter } from "next/navigation";
import { useState } from "react";

export default function LoginPage({ isAdminPage }: { isAdminPage: boolean }) {
  const router = useRouter();
  const [email, setEmail] = useState('');

  const handleLogin = (email: string) => {
    // 이메일 유효성 검사
    if (!email || email.trim() === '') {
      alert('이메일을 입력해주세요.');
      return;
    }

    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      alert('올바른 이메일 형식으로 입력해주세요.');
      return;
    }

    // 로컬 스토리지 저장
    localStorage.setItem('email', email);

    // 일반 고객: 주문 페이지로 이동
    // 관리자: 합산된 주문 목록 페이지로 이동
    if (isAdminPage) {
      router.push('/admin/order');
    } else {
      router.push('/order');
    }
  }

  return (
    <div className="p-6 flex flex-col flex-1 justify-center items-center">
      <div className="mb-3 font-extrabold text-5xl">Grids & Circle</div>
      {isAdminPage ? <div className="mb-6 font-extrabold text-5xl">(관리자용)</div> : ''}
      <div className="mb-10 font-medium text-2xl">이메일로 접속하기</div>
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        className="text-black w-[60%] px-4 py-2 mb-4 border border-gray-300 rounded-lg bg-white"
        placeholder="이메일을 입력하세요"
      />
      <button
        onClick={() => handleLogin(email)}
        className="w-[60%] bg-black text-white py-4 rounded-lg font-bold hover:bg-gray-800 cursor-pointer"
      >
        접속
      </button>
    </div>
  );
}
