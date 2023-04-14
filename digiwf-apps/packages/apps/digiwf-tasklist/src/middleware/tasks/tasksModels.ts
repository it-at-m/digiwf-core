export interface HumanTask {
  readonly id: string;
  readonly createTime: string;
  readonly followUpDate: string;
  readonly processName?: string;
  readonly name?: string
  readonly description?: string;
}
