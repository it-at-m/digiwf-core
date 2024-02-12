import type { PageServiceInstanceTO } from "@muenchen/digiwf-engine-api-internal";

import { FetchUtils } from "@muenchen/digiwf-engine-api-internal";
import { inject } from "vue";

import { SERVICE_INSTANCE_CONTROLLER_API_INJECT_KEY } from "@/composables/useAPI";

export function useServiceInstanceControllerAPI() {
  const serviceInstanceControllerAPI = inject(
    SERVICE_INSTANCE_CONTROLLER_API_INJECT_KEY
  );

  const callGetAssignedProcessInstances = async (
    page: number,
    size: number,
    query?: string
  ): Promise<PageServiceInstanceTO | undefined> => {
    const service = serviceInstanceControllerAPI!.value;

    try {
      const result = await service.getAssignedInstances(
        page,
        size,
        query,
        FetchUtils.getGETConfig()
      );
      return result.data;
    } catch (error) {
      console.log(error); // ADD PROPER ERROR HANDLING LATER (E.G. TOASTS)
    }
  };

  return {
    callGetAssignedProcessInstances,
  };
}
