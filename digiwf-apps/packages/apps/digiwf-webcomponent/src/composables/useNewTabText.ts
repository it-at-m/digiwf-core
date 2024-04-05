import type { Ref } from "vue";

import { computed } from "vue";

import { NEW_TAB_SUFFIX } from "@/util/constants";

export function useNewTabText(text: Ref<string>) {
  const newTabText = computed(() => {
    return `${text.value} ${NEW_TAB_SUFFIX}`;
  });
  return {
    newTabText,
  };
}
