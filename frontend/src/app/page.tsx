// 클라이언트 컴포넌트로 설정
"use client";

import Link from "next/link";

export default function Home() {
  return (
    <>
      <div>Grids & Circle</div>
      <Link href="/order">주문 페이지 바로가기</Link>
    </>
  );
}
