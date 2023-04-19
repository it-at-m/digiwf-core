import {FetchUtils, HumanTaskRestControllerApiFactory, PageHumanTaskTO} from "@muenchen/digiwf-engine-api-internal";
import {ApiConfig} from "../ApiConfig";
import {PageOfTasks, TaskApiFactory, TasksApiFactory} from "@muenchen/digiwf-task-api-internal"

/**
 * old api for getting tasks. will be replaced by callGetTasksFromTaskService
 * @deprecated
 * @param page
 * @param size
 * @param query
 * @param followUp
 */
export const callGetTasksFromEngine = (page: number, size: number, query?: string, followUp?: boolean): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getTasks(page, size, query, followUp).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};
export const callGetTasksFromTaskService = (page: number, size: number, query?: string, followUp?: string): Promise<PageOfTasks> => {
  // follow-up: YYYY-MM-dd: e.g. 2023-04-17
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getCurrentUserTasks(page, size, query) // FIXME: followUp?
    .then(res => Promise.resolve(res.data))
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

/**
 * old api for getting tasks. will be replaced by callGetOpenGroupTasksFromTaskService
 * @deprecated
 * @param page
 * @param size
 * @param query
 */
export const callGetOpenGroupTasksFromEngine = (page: number, size: number, query?: string): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getOpenGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};
export const callGetOpenGroupTasksFromTaskService = (page: number, size: number, query?: string): Promise<PageOfTasks> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getUnassignedGroupTasks(page, size, query)
    .then((res) => Promise.resolve(res.data))
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

// FIXME: before there was a never used parameter followUp. Why?
/**
 * old api for getting tasks. will be replaced by callGetAssignedGroupTasksFromTaskService
 * @deprecated
 * @param page
 * @param size
 * @param query
 * @param followUp
 */
export const callGetAssignedGroupTasksFromEngine = (page: number, size: number, query?: string): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getAssignedGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};
export const callGetAssignedGroupTasksFromTaskService = (page: number, size: number, query?: string): Promise<PageOfTasks> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getAssignedGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

/**
 * @deprecated
 * @param taskId
 */
export const callPostAssignTaskInEngine = (taskId: string): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).assignTask(taskId)
    .then(() => Promise.resolve())
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht zugewiesen werden.")));
};

export const callPostAssignTaskInTaskService = (taskId: string, assignee: string): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).assignTask(taskId, {assignee})
    .then(() => Promise.resolve())
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht zugewiesen werden.")));
};

