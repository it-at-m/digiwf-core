import { inject } from "vue";

export const ACCESS_TOKEN_INJECT_KEY = "accessToken";

export function useAccessToken() {
  const accessToken = inject(ACCESS_TOKEN_INJECT_KEY);

  return { accessToken };
}
