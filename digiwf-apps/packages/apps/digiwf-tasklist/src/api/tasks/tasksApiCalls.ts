import {FetchUtils, HumanTaskRestControllerApiFactory, PageHumanTaskTO} from "@muenchen/digiwf-engine-api-internal";
import {ApiConfig} from "../ApiConfig";

export const callGetTasks = (page: number, size: number, query?: string, followUp?: boolean): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getTasks(page, size, query, followUp).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};


export const callGetOpenGroupTasks = (page: number, size: number, query?: string): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getOpenGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

export const callGetAssignedGroupTasks = (page: number, size: number, query?: string, followUp?: boolean): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getAssignedGroupTasks(page, size, query, followUp).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

export const callPostAssignTask = (taskId: string):Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).assignTask(taskId)
    .then(() => Promise.resolve())
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err,"Die Aufgabe konnte nicht zugewiesen werden.")));
};
