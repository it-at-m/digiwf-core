import {useMutation, useQuery, useQueryClient} from "@tanstack/vue-query";
import {
  callGetAssignedGroupTasksFromEngine,
  callGetAssignedGroupTasksFromTaskService,
  callGetOpenGroupTasksFromEngine,
  callGetOpenGroupTasksFromTaskService,
  callGetTasksFromEngine,
  callGetTasksFromTaskService,
  callPostAssignTaskInEngine, callPostAssignTaskInTaskService
} from "../../api/tasks/tasksApiCalls";
import {computed, ref, Ref} from "vue";
import {Page} from "../commonModels";
import {HumanTask} from "./tasksModels";
import {isServiceTaskServiceEnabled} from "../../utils/featureToggles";
import {mapTaskPageFromEngineService, mapTaskPageFromTaskService} from "./taskMapper";
import {useStore} from "../../hooks/store";

const shouldUseTaskService = isServiceTaskServiceEnabled();
if (shouldUseTaskService) {
  console.log("feature toggle enabled. New tasklist service is used for network requests.")
}
export const useMyTasksQuery = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>, followUp: Ref<boolean | undefined>) => useQuery({
  queryKey: ["user-tasks", page.value, size.value, query.value, followUp.value],
  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService
      ? callGetTasksFromTaskService(page.value, size.value, query.value, followUp.value).then((r) => Promise.resolve(mapTaskPageFromTaskService(r)))
      : callGetTasksFromEngine(page.value, size.value, query.value, followUp.value).then((r) => Promise.resolve(mapTaskPageFromEngineService(r)))
  },
});

export const useOpenGroupTasksQuery = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>) => useQuery({
  queryKey: ["open-group-tasks", page.value, size.value, query.value],
  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService
      ? callGetOpenGroupTasksFromTaskService(page.value, size.value, query.value).then((r) => Promise.resolve(mapTaskPageFromTaskService(r)))
      : callGetOpenGroupTasksFromEngine(page.value, size.value, query.value).then((r) => Promise.resolve(mapTaskPageFromEngineService(r)))
  },
});

export const useAssignedGroupTasksQuery = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>) => useQuery({
  queryKey: ["assigned-group-tasks", page.value, size.value, query.value],
  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService
      ? callGetAssignedGroupTasksFromTaskService(page.value, size.value, query.value).then((r) => Promise.resolve(mapTaskPageFromTaskService(r)))
      : callGetAssignedGroupTasksFromEngine(page.value, size.value, query.value).then((r) => Promise.resolve(mapTaskPageFromEngineService(r)))
  },
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

  // FIXME: remove useStore and replace it with context
  const lhmObjectId = (useStore().state as any).user?.info?.lhmObjectId;
  return useMutation<void, any, string>({
    mutationFn: (taskId) => {
      return shouldUseTaskService
        ? callPostAssignTaskInTaskService(taskId, lhmObjectId)
        : callPostAssignTaskInEngine(taskId)
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["user-tasks"]);
      queryClient.invalidateQueries(["assigned-group-tasks"]);
      queryClient.invalidateQueries(["open-group-tasks"]);
    },
  })
}
