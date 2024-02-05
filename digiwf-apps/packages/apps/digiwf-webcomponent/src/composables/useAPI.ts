import {
  Configuration,
  ServiceInstanceControllerApiFactory
} from "@muenchen/digiwf-engine-api-internal";
import { computed, provide } from "vue";
import type { Ref, InjectionKey, ComputedRef } from "vue";

export type ServiceInstanceControllerAPI = ReturnType<typeof ServiceInstanceControllerApiFactory>;
export const SERVICE_INSTANCE_INJECT_KEY = Symbol() as InjectionKey<ComputedRef<ServiceInstanceControllerAPI>>

export function useAPI(basePath: Ref<string>, accessToken: Ref<string>) {
  const apiConfig = computed(() => {
    return new Configuration({
      basePath: basePath.value,
      accessToken: accessToken.value
    })
  })

  const serviceInstanceControllerAPI = computed(() => {
    return ServiceInstanceControllerApiFactory(apiConfig.value)
  })

  provide(SERVICE_INSTANCE_INJECT_KEY, serviceInstanceControllerAPI)
}
