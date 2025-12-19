const NEXT_PUBLIC_API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

export const apiFetch = async (url: string, options?: RequestInit) => {
  if (options?.body) {
    const headers = new Headers(options?.headers || {});
    if (!headers.has("Content-Type")) {
      headers.set("Content-Type", "application/json; charset=utf-8");
    }

    options.headers = headers;
  }

  const res = await fetch(`${NEXT_PUBLIC_API_BASE_URL}${url}`, options);

  if (!res.ok) {
    const errorData = await res.json();
    throw errorData;
  }

  return res.json();
};