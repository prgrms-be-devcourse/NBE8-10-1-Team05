'use client';

interface CounterProps {
  value: number;
  onIncrease: () => void;
  onDecrease: () => void;
}

export default function Counter({ value, onIncrease, onDecrease }: CounterProps) {
  return (
    <div className="text-black border border-gray-300 rounded-lg w-[120px] flex justify-evenly items-center">
      <button className="text-3xl font-bold cursor-pointer" onClick={onDecrease}>
        −
      </button>
      <span className="mx-4 text-xl font-medium">
        {value}
      </span>
      <button className="text-3xl font-bold cursor-pointer" onClick={onIncrease}>
        +
      </button>
    </div>
  );
}