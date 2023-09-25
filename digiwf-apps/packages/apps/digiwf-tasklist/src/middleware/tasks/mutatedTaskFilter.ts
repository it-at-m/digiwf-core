export const FINISHED_TASK_IDS_KEY = "FINISHED_TASK_IDS";
export const ASSIGNED_TASK_IDS_KEY = "ASSIGNED_TASK_IDS";

export const getFinishedTaskIds = (): string[] => getMutatedTaskIds(FINISHED_TASK_IDS_KEY);
export const getAssignedTaskIds = (): string[] => getMutatedTaskIds(ASSIGNED_TASK_IDS_KEY);
export const getMutatedTaskIds = (storageKey: string): string[] => {
  const value = sessionStorage.getItem(storageKey);
  if(!value) {
    return [];
  }
  try {
    return JSON.parse(value) as string[];
  } catch (e) {
    console.warn("could not parse finished task ids from session storage");
    return [];
  }
};

const setFinishedTaskIds = (ids: string[]) => setMutatedTaskIds(FINISHED_TASK_IDS_KEY, ids);
const setAssignedTaskIds = (ids: string[]) => setMutatedTaskIds(ASSIGNED_TASK_IDS_KEY, ids);
const setMutatedTaskIds = (storageKey: string, ids: string[]) => {
  sessionStorage.setItem(storageKey, JSON.stringify(ids));
};

export const isInFinishedProcesses = (taskId: string): boolean => getFinishedTaskIds().includes(taskId);
export const isInAssignedProcesses = (taskId: string): boolean => getAssignedTaskIds().includes(taskId);
export const addFinishedTaskIds = (taskId: string): void => setFinishedTaskIds([...getFinishedTaskIds(), taskId]);
export const addAssignedTaskIds = (taskId: string): void => setAssignedTaskIds([...getAssignedTaskIds(), taskId]);
