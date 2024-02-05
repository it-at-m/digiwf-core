import { computed, inject, provide, ref } from "vue";
import type { ComputedRef, InjectionKey, Ref } from "vue";
import { useEventListener } from "@vueuse/core";
import type { AccessTokenLoadedEvent } from "@/types/AccessTokenLoadedEvent";

export const HAS_ACCESS_TOKEN_INJECT_KEY = Symbol() as InjectionKey<ComputedRef<boolean>>

export function useAccessToken(accessTokenEventName: Ref<string>) {
  const accessToken = ref("");

  useEventListener(
      document,
      accessTokenEventName.value,
      (event: AccessTokenLoadedEvent) => {
        accessToken.value = event.detail.accessToken;
      }
  );

  const hasAccessToken = computed(() => !!accessToken.value)
  provide(HAS_ACCESS_TOKEN_INJECT_KEY, hasAccessToken);

  return {
    accessToken
  }
}

export function useHasAccessToken() {
  const hasAccessToken = inject(HAS_ACCESS_TOKEN_INJECT_KEY);

  return { hasAccessToken };
}
