import {useQuery} from "@tanstack/vue-query";
import {callGetTasks} from "../../api/tasks/tasksApiCalls";
import {Ref} from "vue";
import {queryClient} from "../queryClient";
import {PageHumanTaskTO} from "@muenchen/digiwf-engine-api-internal";

export const useMyTasksQuery = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>, followUp: Ref<boolean | undefined>) => useQuery({
  queryKey: ['user-tasks', page.value, size.value, query.value, followUp.value],
  queryFn: (): Promise<PageHumanTaskTO> => {
    console.log("query: ", query.value)
    return callGetTasks(page.value, size.value, query.value, followUp.value)
  }
});

export const invalidMyTasksQuery = () => queryClient.invalidateQueries(["user-tasks"])
