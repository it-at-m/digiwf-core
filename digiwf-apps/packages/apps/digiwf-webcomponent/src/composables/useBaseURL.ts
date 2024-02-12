import type { InjectionKey, Ref } from "vue";

import { inject, provide } from "vue";

export const DIGIWF_BASE_URL_INJECT_KEY = Symbol() as InjectionKey<Ref<string>>;

export function useProvideBaseURL(baseURL: Ref<string>) {
  provide(DIGIWF_BASE_URL_INJECT_KEY, baseURL);
}

export function useInjectBaseURL() {
  const digiWFBaseURL = inject(DIGIWF_BASE_URL_INJECT_KEY);
  return { digiWFBaseURL };
}
