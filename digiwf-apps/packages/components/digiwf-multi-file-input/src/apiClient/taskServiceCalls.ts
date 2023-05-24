import {FileApiFactory} from "@muenchen/digiwf-task-api-internal";
import {AxiosPromise} from "axios";

/**
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileUploadFromTaskservice = (cfg: any, formContextId: string, fileName: string, filePath: string): Promise<string> => {
  return FileApiFactory(cfg).getPresignedUrlForFile(formContextId,fileName,filePath,"POST").then((response: AxiosPromise<string>) => Promise.resolve(response.data));
  // FIXME: fill with content
}

/**
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileDownloadFromTaskservice = (cfg: any, formContextId: string, fileName: string, filePath: string) => {
  FileApiFactory(cfg).getPresignedUrlForFile(formContextId,fileName,filePath,"GET");
  // FIXME: fill with content
  return Promise.resolve("url")
}

/**
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileDeletionFromTaskservice = (cfg: any, formContextId: string, fileName: string, filePath: string) => {
  FileApiFactory(cfg).getPresignedUrlForFile(formContextId,fileName,filePath,"DELETE");
  // FIXME: fill with content
  return Promise.resolve("url")
}

/**
 * @param cfg
 * @param formContextId
 * @param filePath
 */
export const getFileNamesFromTaskservice = (cfg: any, formContextId: string, filePath: string) => {
  FileApiFactory(cfg).getFileNames(formContextId,filePath);
  // FIXME: fill with content
  return Promise.resolve("url")
}

