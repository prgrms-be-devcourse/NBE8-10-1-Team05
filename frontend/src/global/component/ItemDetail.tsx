'use client';

import { Item } from "../interface/item";
import Counter from "./Counter";

interface ItemDetailProps {
  item: Item;
  count: number;
  onIncrease: () => void;
  onDecrease: () => void;
}

export default function ItemDetail({ item, count, onIncrease, onDecrease }: ItemDetailProps) {
  return (
    <div className="flex items-center gap-5 p-5 border-b border-gray-200">
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
        <div className='font-medium'>{item.name}</div>
      </div>

      {/* Price */}
      <div className="font-medium">
        {item.price}원
      </div>

      {/* Counter */}
      <Counter
        value={count}
        onIncrease={onIncrease}
        onDecrease={onDecrease}
      />
    </div>
  );
}


