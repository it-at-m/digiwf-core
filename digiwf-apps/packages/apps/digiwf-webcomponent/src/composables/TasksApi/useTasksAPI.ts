import type { ComputedRef, InjectionKey, Ref } from "vue";

import {
  Configuration,
  TasksApiFactory,
} from "@muenchen/digiwf-task-api-internal";
import { computed, provide } from "vue";

import { DIGIWF_TASKLIST_SUFFIX } from "@/util/constants";

type TasksAPI = ReturnType<typeof TasksApiFactory>;
export const TASKS_API_INJECT_KEY = Symbol() as InjectionKey<
  ComputedRef<TasksAPI>
>;

export function useTasksAPI(baseUrl: Ref<string>, accessToken: Ref<string>) {
  const apiConfig = computed(() => {
    return new Configuration({
      basePath: baseUrl.value + DIGIWF_TASKLIST_SUFFIX,
      accessToken: accessToken.value,
    });
  });

  const tasksAPI = computed(() => {
    return TasksApiFactory(apiConfig.value);
  });

  provide(TASKS_API_INJECT_KEY, tasksAPI);
}
