export interface HumanTask {
  readonly id: string;
  readonly createTime: string;
  readonly followUpDate?: string;
  readonly processName?: string;
  readonly name: string
  readonly description?: string;
  readonly assigneeId?: string;
  // FIXME formatted assignee
}

export interface HumanTaskDetails extends HumanTask{
  readonly form?: any;
  readonly schema?: any;
  readonly variables: any

  readonly processInstanceId?: string;
  /**
   * @deprecated
   */
  readonly statusDocument: boolean;
}
