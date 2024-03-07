import { callGetUserInfoFromTaskService } from "../../api/user/userApiCalls";
import { queryClient } from "../queryClient";
import { mapUserResponse } from "./userMapper";
import { User } from "./userModels";

export const getUserInfo = (id: string) =>
  queryClient.fetchQuery<User>({
    queryKey: ["user-info", id],
    queryFn: () =>
      callGetUserInfoFromTaskService(id).then((data) =>
        Promise.resolve(mapUserResponse(data))
      ),
  });
