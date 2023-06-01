import {HumanTaskFileRestControllerApiFactory} from "@muenchen/digiwf-engine-api-internal";

/**
 * @deprecated
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileUploadFromEngine = (cfg: any, formContextId: string, fileName: string, filePath: string): Promise<string> => {
  return HumanTaskFileRestControllerApiFactory(cfg).getPresignedUrlForFileUpload(
    formContextId,
    fileName,
    filePath
  );
}

/**
 * @deprecated
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileDownloadFromEngine = (cfg: any, formContextId: string, fileName: string, filePath: string) => {
  return HumanTaskFileRestControllerApiFactory(cfg).getPresignedUrlForFileDownload(
    formContextId,
    fileName,
    filePath
  );
}

/**
 * @deprecated
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileDeletionFromEngine = (cfg: any, formContextId: string, fileName: string, filePath: string) => {
  return HumanTaskFileRestControllerApiFactory(cfg).getPresignedUrlForFileDeletion(
    formContextId,
    fileName,
    filePath
  );
}

/**
 * @deprecated
 * @param cfg
 * @param formContextId
 * @param filePath
 */
export const getFileNamesFromEngine = (cfg: any, formContextId: string, filePath: string) => {
  return HumanTaskFileRestControllerApiFactory(cfg).getFileNames(
    formContextId,
    filePath
  );
}
