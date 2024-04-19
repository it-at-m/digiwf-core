import type { Ref } from "vue";

import { computed } from "vue";
import { DefaultStatus } from "@/types/DefaultStatus";

export function useStatusText(text: Ref<string>) {
  const statusText = computed(() => {
    const key = text.value.toUpperCase();
    if (key in DefaultStatus) {
      return DefaultStatus[key as keyof typeof DefaultStatus];
    } else {
      return text.value;
    }
  });
  return {
    statusText,
  };
}
