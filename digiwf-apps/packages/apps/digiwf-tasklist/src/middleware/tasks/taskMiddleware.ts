import {useMutation, useQuery, useQueryClient} from "@tanstack/vue-query";
import {
  callGetAssignedGroupTasks,
  callGetOpenGroupTasks,
  callGetTasks,
  callPostAssignTask
} from "../../api/tasks/tasksApiCalls";
import {computed, ref, Ref} from "vue";
import {PageHumanTaskTO} from "@muenchen/digiwf-engine-api-internal";

export const useMyTasksQuery = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>, followUp: Ref<boolean | undefined>) => useQuery({
  queryKey: ["user-tasks", page.value, size.value, query.value || "", followUp.value],
  queryFn: (): Promise<PageHumanTaskTO> => callGetTasks(page.value, size.value, query.value, followUp.value),
});

export const useOpenGroupTasksQuery = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>) => useQuery({
  queryKey: ["open-group-tasks", page.value, size.value, query.value || ""],
  queryFn: (): Promise<PageHumanTaskTO> => callGetOpenGroupTasks(page.value, size.value, query.value),
});

export const useAssignedGroupTasksQuery = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>) => useQuery({
  queryKey: ["assigned-group-tasks", page.value, size.value, query.value || ""],
  queryFn: (): Promise<PageHumanTaskTO> => callGetAssignedGroupTasks(page.value, size.value, query.value),
});

export interface UseNumberOfTasksReturn {
  readonly myTasks: Ref<number>;
  readonly assignedGroupTasks: Ref<number>;
  readonly openGroupTasks: Ref<number>;
}

export const useNumberOfTasks = (): UseNumberOfTasksReturn => {
  const dummyPage = ref(0);
  const dummyPageSize = ref(20);
  const dummyQuery = ref(undefined);
  const {data: myTasksData} = useMyTasksQuery(dummyPage, dummyPageSize, dummyQuery, ref(false));
  const {data: assignGroupData} = useAssignedGroupTasksQuery(dummyPage, dummyPageSize, dummyQuery);
  const {data: openGroupData} = useOpenGroupTasksQuery(dummyPage, dummyPageSize, dummyQuery);
  return {
    myTasks: computed(() => myTasksData?.value?.totalElements || 0),
    assignedGroupTasks: computed(() => assignGroupData?.value?.totalElements || 0),
    openGroupTasks: computed(() => openGroupData?.value?.totalElements || 0),
  }
}

export const useAssignTaskMutation = () => {
  const queryClient = useQueryClient();

  return useMutation<void, any, string>({
    mutationFn: (taskId) => callPostAssignTask(taskId),
    onSuccess: () => {
      queryClient.invalidateQueries(["user-tasks"]);
      queryClient.invalidateQueries(["assigned-group-tasks"]);
      queryClient.invalidateQueries(["open-group-tasks"]);
    },
  })
}
