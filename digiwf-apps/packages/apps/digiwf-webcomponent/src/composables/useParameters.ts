import type { RootHeadingLevel } from "@/types/RootHeadingLevel";
import type { InjectionKey, Ref } from "vue";

import { inject, provide } from "vue";

const PAGE_SIZE_INJECT_KEY = Symbol() as InjectionKey<Ref<number>>;
const MAX_PAGES_VISIBLE_INJECT_KEY = Symbol() as InjectionKey<Ref<number>>;
const ROOT_HEADING_LEVEL_INJECT_KEY = Symbol() as InjectionKey<
  Ref<RootHeadingLevel>
>;

export function useProvideParameters(
  pageSize: Ref<number>,
  maxPagesVisible: Ref<number>,
  rootHeadingLevel: Ref<RootHeadingLevel>
) {
  provide(PAGE_SIZE_INJECT_KEY, pageSize);
  provide(MAX_PAGES_VISIBLE_INJECT_KEY, maxPagesVisible);
  provide(ROOT_HEADING_LEVEL_INJECT_KEY, rootHeadingLevel);
}

export function useInjectParameters() {
  const pageSize = inject(PAGE_SIZE_INJECT_KEY);
  const maxPagesVisible = inject(MAX_PAGES_VISIBLE_INJECT_KEY);
  const rootHeadingLevel = inject(ROOT_HEADING_LEVEL_INJECT_KEY);
  return { pageSize, maxPagesVisible, rootHeadingLevel };
}
