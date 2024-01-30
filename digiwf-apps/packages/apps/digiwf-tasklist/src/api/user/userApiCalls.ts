import {ApiConfig} from "../ApiConfig";
import {FetchUtils, UserRestControllerApiFactory, UserTO} from "@muenchen/digiwf-engine-api-internal";
import {UserApiFactory, UserProfile} from "@muenchen/digiwf-task-api-internal";

export const callGetUserInfoFromTaskService = (id: string): Promise<UserProfile> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return UserApiFactory(cfg).resolveUser(id)
    .then((res) => Promise.resolve(res.data))
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Der Nutzer konnte nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

export const callGetUserById = (id: string): Promise<UserTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return UserRestControllerApiFactory(cfg).getUser(id).then(r => Promise.resolve(r.data));
};

export const callGetUserByUsername = (username: string): Promise<UserTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return UserRestControllerApiFactory(cfg).getUserByUsername(username).then(r => Promise.resolve(r.data));
};

export const callSearchUser = (search: string, ous?: string): Promise<UserTO[]> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return UserRestControllerApiFactory(cfg).getUsers({
    searchString: search,
    ous: ous
  }).then(r => Promise.resolve(r.data));
};
