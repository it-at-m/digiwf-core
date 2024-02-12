import type { ComputedRef, InjectionKey, Ref } from "vue";

import {
  Configuration,
  ServiceInstanceControllerApiFactory,
} from "@muenchen/digiwf-engine-api-internal";
import { computed, provide } from "vue";

import { DIGIWF_ENGINE_SUFFIX } from "@/util/constants";

export type ServiceInstanceControllerAPI = ReturnType<
  typeof ServiceInstanceControllerApiFactory
>;
export const SERVICE_INSTANCE_CONTROLLER_API_INJECT_KEY =
  Symbol() as InjectionKey<ComputedRef<ServiceInstanceControllerAPI>>;

export function useAPI(baseUrl: Ref<string>, accessToken: Ref<string>) {
  const apiConfig = computed(() => {
    return new Configuration({
      basePath: baseUrl.value + DIGIWF_ENGINE_SUFFIX,
      accessToken: accessToken.value,
    });
  });

  const serviceInstanceControllerAPI = computed(() => {
    return ServiceInstanceControllerApiFactory(apiConfig.value);
  });

  provide(
    SERVICE_INSTANCE_CONTROLLER_API_INJECT_KEY,
    serviceInstanceControllerAPI
  );
}
