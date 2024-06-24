import type { PageOfTasks } from "@muenchen/digiwf-task-api-internal";

import { FetchUtils } from "@muenchen/digiwf-task-api-internal";
import { inject, readonly, ref } from "vue";

import { TASKS_API_INJECT_KEY } from "@/composables/TasksApi/useTasksAPI";
import { useInjectParameters } from "@/composables/useParameters";

export function useGetCurrentUserTasks() {
  const tasksAPI = inject(TASKS_API_INJECT_KEY)!;

  const { pageSize } = useInjectParameters();

  const loadingInternal = ref(false);
  const errorInternal = ref(false);
  const dataInternal = ref<PageOfTasks>();

  const loading = readonly(loadingInternal);
  const error = readonly(errorInternal);
  const data = readonly(dataInternal);

  const call = async (page: number): Promise<void> => {
    const service = tasksAPI.value;

    loadingInternal.value = true;
    errorInternal.value = false;
    try {
      const result = await service.getCurrentUserTasks(
        page,
        pageSize?.value,
        undefined,
        undefined,
        undefined,
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
