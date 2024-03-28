import { computed } from "vue";

export function useBaseURL() {
  const baseURL = computed(() => {
    return new URL(import.meta.url).origin;
  });
  return {
    baseURL,
  };
}
