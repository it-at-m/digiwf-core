import {HumanTaskDetailTO, HumanTaskTO, PageHumanTaskTO} from "@muenchen/digiwf-engine-api-internal";
import {HumanTask, HumanTaskDetails} from "./tasksModels";
import {Page} from "../commonModels";
import {PageOfTasks, TaskWithSchema} from "@muenchen/digiwf-task-api-internal";
import {Task} from "@muenchen/digiwf-task-api-internal/src";
import {DateTime} from "luxon";
import {formatIsoDate, formatIsoDateTime} from "../../utils/time";

/**
 * @deprecated is only necessary until tasks will provided by task service in production
 * @param response
 */
export const mapTaskFromEngineService = (response: HumanTaskTO): HumanTask => {
  return {
    followUpDate: response.followUpDate ? DateTime.fromFormat(response.followUpDate, "yyyy-MM-dd").toLocaleString(DateTime.DATE_SHORT) : undefined,
    createTime: DateTime.fromISO(response.creationTime!).toLocaleString(DateTime.DATETIME_SHORT),
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: response.assigneeFormatted
  }
}

/**
 * @deprecated is only necessary until tasks will provided by task service in production
 * @param response
 */
export const mapTaskPageFromEngineService = (response: PageHumanTaskTO): Page<HumanTask> => {
  return {
    content: response.content?.map(mapTaskFromEngineService),
    totalElements: response.totalElements,
    totalPages: response.totalPages!,
  }
}
/**
 * @deprecated
 * @param response
 */
export const mapTaskDetailsFromEngineService = (response: HumanTaskDetailTO): HumanTaskDetails => {
  return {
    ...mapTaskFromEngineService(response),
    form: response.form,
    variables: response.variables,
    processInstanceId: response.processInstanceId,
    schema: response.jsonSchema,
    statusDocument: response.statusDocument || false,
  }
}

export const mapTaskFromTaskService = (response: Task): HumanTask => {
  return {
    createTime: response.createTime ? formatIsoDateTime(response.createTime) : "-",
    followUpDate: response.followUpDate ? formatIsoDate(response.followUpDate) : undefined,
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: `TODO format assignee for ${response.assignee}`
  };
}

export const mapTaskPageFromTaskService = (response: PageOfTasks): Page<HumanTask> => {
  return {
    content: response.content?.map(mapTaskFromTaskService),
    totalElements: response.totalElements,
    totalPages: response.totalPages!,
  }
}

export const mapTaskDetailsFromTaskService = (response: TaskWithSchema): HumanTaskDetails => {
  return {
    createTime: response.createTime ? formatIsoDateTime(response.createTime) : "-",
    followUpDate: response.followUpDate ? formatIsoDate(response.followUpDate) : undefined,
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    form: undefined, // FIXME: check if it is correct
    variables: response.variables,
    processInstanceId: response.processInstanceId,
    schema: response.schema?.schema,
    statusDocument: false,
  }
}
