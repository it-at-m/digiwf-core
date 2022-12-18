import {useMutation, useQuery, useQueryClient} from "@tanstack/vue-query";
import {callDeleteFilter, callGetFilters, callSaveFilter} from "../../api/persistentFilters/persistentFilterApiCalls";
import {FilterTO, SaveFilterTO} from "@muenchen/digiwf-engine-api-internal";
import {queryClient} from "../queryClient";

export const useGetPersistentFilters = () => useQuery({
  queryKey: ["persistent-filter"],
  queryFn: () => callGetFilters(),
});

export const useSavePersistentFilters = () => {
  const queryClient = useQueryClient();
  return useMutation<FilterTO, any, SaveFilterTO>({
    mutationFn: (filter: SaveFilterTO) => {
      console.log("mutationFn of useSavePersistentFilters", filter)
      return callSaveFilter(filter)
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["persistent-filter"]);
    }
  })
}
export const useDeletePersistentFilters = () => {
  const queryClient = useQueryClient();
  return useMutation<void, any, string>({
    mutationFn: (id: string) => {
      return callDeleteFilter(id);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["persistent-filter"]);
    }
  })
}
/**
 * @deprecated
 */
export const getPersistentFilterForNonHookCompatibleFunction = () => {
  return queryClient.fetchQuery({
    queryKey: ["persistent-filter"],
    queryFn: () => callGetFilters(),
  })
}
