type ButtonColor = 'red' | 'blue' | 'gray';

export default function Button({ color, content, handler }: {
  color: ButtonColor;
  content: string;
  handler?: () => void;
}) {
  const colorClass = {
    red: 'bg-red-500 hover:bg-red-400',
    blue: 'bg-blue-500 hover:bg-blue-400',
    gray: 'bg-gray-500 hover:bg-gray-400',
  }[color];

  return (
    <button
      onClick={handler}
      className={`${colorClass} cursor-pointer text-white font-medium w-[120px] px-4 py-1 rounded-md`}
    >
      {content}
    </button>
  );
}