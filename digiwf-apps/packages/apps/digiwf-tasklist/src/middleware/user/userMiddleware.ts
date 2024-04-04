import { useQuery } from "@tanstack/vue-query";

import {
  callGetCurrentUserInfo,
  callGetUserInfoFromTaskService,
} from "../../api/user/userApiCalls";
import { queryClient } from "../queryClient";
import { mapUserResponse, mapUserTOToUser } from "./userMapper";
import { User } from "./userModels";

export const getUserInfo = (id: string) =>
  queryClient.fetchQuery<User>({
    queryKey: ["user-info", id],
    queryFn: () =>
      callGetUserInfoFromTaskService(id).then((data) =>
        Promise.resolve(mapUserResponse(data))
      ),
  });

export const useCurrentUserInfo = () =>
  useQuery<User>({
    queryKey: ["current-user-info"],
    queryFn: () =>
      callGetCurrentUserInfo().then((data) => {
        return Promise.resolve(mapUserTOToUser(data));
      }),
  });
