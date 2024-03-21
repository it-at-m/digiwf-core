import type { InjectionKey, Ref } from "vue";

import { inject, provide } from "vue";

const PAGE_SIZE_INJECT_KEY = Symbol() as InjectionKey<Ref<number>>;
const MAX_PAGES_VISIBLE_INJECT_KEY = Symbol() as InjectionKey<Ref<number>>;

export function useProvideParameters(
  pageSize: Ref<number>,
  maxPagesVisible: Ref<number>
) {
  provide(PAGE_SIZE_INJECT_KEY, pageSize);
  provide(MAX_PAGES_VISIBLE_INJECT_KEY, maxPagesVisible);
}

export function useInjectParameters() {
  const pageSize = inject(PAGE_SIZE_INJECT_KEY);
  const maxPagesVisible = inject(MAX_PAGES_VISIBLE_INJECT_KEY);
  return { pageSize, maxPagesVisible };
}
