export default function Button({ color, content, handler }: {
  color: string;
  content: string;
  handler?: () => void;
}) {
  return (
    <button
      onClick={handler}
      className={`bg-${color}-500 hover:bg-${color}-400 cursor-pointer text-white font-medium w-[120px] px-4 py-1 rounded-md`}
    >
      {content}
    </button>
  );
}