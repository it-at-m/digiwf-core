import {useQuery} from "@tanstack/vue-query";
import {callGetTasks} from "../../api/tasks/tasksApiCalls";
import {Ref} from "vue";
import {queryClient} from "../queryClient";

export const useMyTasksQuery = (page: Ref<number>, size: Ref<number>) => useQuery({
  queryKey: ['user-tasks', page.value, size.value],
  queryFn: () => callGetTasks(page.value, size.value)
});

export const invalidMyTasksQuery = () => queryClient.invalidateQueries(["user-tasks"])
