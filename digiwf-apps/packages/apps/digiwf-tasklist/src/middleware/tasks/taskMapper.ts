import {HumanTask, HumanTaskDetails} from "./tasksModels";
import {TaskWithSchema} from "@muenchen/digiwf-task-api-internal";
import {Task} from "@muenchen/digiwf-task-api-internal/src";
import {formatIsoDate, formatIsoDateTime, getDateFromIsoDateTime} from "../../utils/time";
import {User} from "../user/userModels";

export const mapTaskFromTaskService = (response: Task, inFinishProcess: boolean, inAssignProcess: boolean, user?: User): HumanTask => {
  return {
    createTime: response.createTime ? formatIsoDateTime(response.createTime) : "-",
    followUpDate: response.followUpDate ? getDateFromIsoDateTime(response.followUpDate) : undefined,
    followUpDateFormatted: response.followUpDate ? formatIsoDate(response.followUpDate) : undefined,
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: user && user.fullInfo,
    inFinishProcess,
    inAssignProcess
  };
};
export const mapTaskDetailsFromTaskService = (response: TaskWithSchema, inFinishProcess: boolean, inAssignProcess: boolean, user?: User): HumanTaskDetails => {
  return {
    createTime: response.createTime ? formatIsoDateTime(response.createTime) : "-",
    followUpDate: response.followUpDate ? getDateFromIsoDateTime(response.followUpDate) : undefined,
    followUpDateFormatted: response.followUpDate ? formatIsoDate(response.followUpDate) : undefined,
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: user && `${user.firstName} ${user.surname} (${user.ou})`,
    form: response.schemaType === "VUETIFY_FORM_BASE" ? response.schema : undefined,
    variables: response.variables,
    processInstanceId: response.processInstanceId,
    schema: response.schemaType === "SCHEMA_BASED" ? response.schema : undefined,
    statusDocument: false,
    inFinishProcess,
    inAssignProcess,
    isCancelable: response.cancelable
  };
};
