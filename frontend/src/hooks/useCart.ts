import { Item } from "@/global/interface/item";
import { useState } from "react";

interface CartItem {
  itemId: number;
  name: string;
  quantity: number;
}

export function useCart(items: Item[]) {
  const [counts, setCounts] = useState<Record<number, number>>({}); // {itemId:quantity}의 맵 형태로 상태 관리
  const [cart, setCart] = useState<CartItem[]>([]);

  const increase = (item: Item) => {
    // 1. 카운트 증가
    setCounts(prev => ({
      // 기존 값들 전부 복사하되
      ...prev,
      [item.id]: (prev[item.id] ?? 0) + 1 // 해당 itemId로 된 key의 value만 덮어쓰기
    }));

    // 2. 장바구니에 추가 또는 숫자 증가
    setCart((prevCart) => {
      const findItem = prevCart.find((cartItem) => cartItem.itemId === item.id);
      // 기존에 장바구니에 있었다면 숫자만 1 증가
      if (findItem) {
        return prevCart.map((cartItem) =>
          cartItem.itemId === item.id
            ? { ...cartItem, quantity: cartItem.quantity + 1 }
            : cartItem
        );
      }
      // 기존에 장바구니에 없었다면 새로 추가
      return [...prevCart, { itemId: item.id, name: item.name, quantity: 1 }];
    });
  };

  const decrease = (item: Item) => {
    // 1. 카운트 감소
    setCounts(prev => ({
      ...prev, // 기존 값들 전부 복사하되
      [item.id]: Math.max(0, (prev[item.id] ?? 0) - 1) // 해당 itemId로 된 key의 value만 덮어쓰기
    }));

    // 2. 장바구니에서 삭제 또는 숫자 감소
    setCart((prevCart) => {
      const findItem = prevCart.find((cartItem) => cartItem.itemId === item.id);
      // 장바구니에 넣어둔 상품이어야만 상태 변경
      if (findItem) {
        // 수량이 1인 상품은 감소하면 0이 되므로 장바구니에서 삭제
        if (findItem.quantity === 1) {
          return prevCart.filter((cartItem) => cartItem.itemId !== item.id);
        }
        // 수량이 2 이상인 상품은 숫자만 1 감소
        return prevCart.map((cartItem) =>
          cartItem.itemId === item.id
            ? { ...cartItem, quantity: cartItem.quantity - 1 }
            : cartItem
        );
      }
      // 장바구니에 없는 상품은 잘못된 요청이므로 기존 상태 그대로 리턴
      return prevCart;
    });
  };

  const totalAmount = cart.reduce((sum, item) => {
    const foundItem = items.find((p) => p.id === item.itemId);
    return sum + (foundItem ? foundItem.price * item.quantity : 0);
  }, 0);

  return {
    counts,
    setCounts,
    cart,
    setCart,
    increase,
    decrease,
    totalAmount
  };
}