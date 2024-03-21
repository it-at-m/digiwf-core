import type { PageServiceInstanceTO } from "@muenchen/digiwf-engine-api-internal";

import { FetchUtils } from "@muenchen/digiwf-engine-api-internal";
import { inject, readonly, ref } from "vue";

import { SERVICE_INSTANCE_CONTROLLER_API_INJECT_KEY } from "@/composables/useAPI";
import { useInjectParameters } from "@/composables/useParameters";

export function useGetAssignedProcessInstances() {
  const serviceInstanceControllerAPI = inject(
    SERVICE_INSTANCE_CONTROLLER_API_INJECT_KEY
  )!;

  const { pageSize } = useInjectParameters();

  const loadingInternal = ref(false);
  const errorInternal = ref(false);
  const dataInternal = ref<PageServiceInstanceTO>();

  const loading = readonly(loadingInternal);
  const error = readonly(errorInternal);
  const data = readonly(dataInternal);

  const call = async (page: number): Promise<void> => {
    const service = serviceInstanceControllerAPI.value;

    loadingInternal.value = true;
    errorInternal.value = false;
    try {
      const result = await service.getAssignedInstances(
        page,
        pageSize?.value,
        undefined,
        FetchUtils.getGETConfig()
      );
      dataInternal.value = result.data;
    } catch (error) {
      errorInternal.value = true;
    } finally {
      loadingInternal.value = false;
    }
  };

  return {
    call,
    loading,
    error,
    data,
  };
}
