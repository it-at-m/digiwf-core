import {HumanTaskFileRestControllerApiFactory} from "@muenchen/digiwf-engine-api-internal";

/**
 * @deprecated
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileUploadFromEngine = (cfg: any, formContextId: string, fileName: string, filePath: string) => HumanTaskFileRestControllerApiFactory(cfg).getPresignedUrlForFileUpload(
  formContextId,
  fileName,
  filePath
);
