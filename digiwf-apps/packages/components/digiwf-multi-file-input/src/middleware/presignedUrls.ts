import {
  Configuration,
  FetchUtils,
  ServiceInstanceFileRestControllerApiFactory,
  ServiceStartFileRestControllerApiFactory
} from "@muenchen/digiwf-engine-api-internal";
import {Ref} from "vue";
import {
  getFileNamesFromEngine,
  getPresignedUrlForFileDeletionFromEngine,
  getPresignedUrlForFileDownloadFromEngine,
  getPresignedUrlForFileUploadFromEngine
} from "@/apiClient/engineCalls";
import {
  getFileNamesFromTaskservice,
  getPresignedUrlForFileDeletionFromTaskservice,
  getPresignedUrlForFileDownloadFromTaskservice,
  getPresignedUrlForFileUploadFromTaskservice
} from "@/apiClient/taskServiceCalls";

interface EngineInteractionConfig {
  readonly formContext: any;
  readonly apiEndpoint: string;
  readonly filePath: Ref<string>
  readonly shouldUseTaskService: boolean;
  readonly taskServiceApiEndpoint: string;
}

export const getPresignedUrlForPost = async (file: File, config: EngineInteractionConfig): Promise<string> => {
  const {filePath, formContext, shouldUseTaskService, apiEndpoint, taskServiceApiEndpoint} = config;
  const engineAxiosConfig = axiosConfig(apiEndpoint);
  const taskServiceAxiosConfig = axiosConfig(taskServiceApiEndpoint);

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(engineAxiosConfig).getPresignedUrlForFileUpload1(
      formContext!.id,
      file!.name,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    if (shouldUseTaskService) {
      res = await getPresignedUrlForFileUploadFromTaskservice(taskServiceAxiosConfig, formContext!.id, file!.name, filePath.value)
      // res.data does not exist
      return res;
    } else {
      res = await getPresignedUrlForFileUploadFromEngine(engineAxiosConfig, formContext!.id, file!.name, filePath.value)
    }
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(engineAxiosConfig).getPresignedUrlForFileUpload2(
      formContext!.id,
      file!.name,
      filePath.value
    );
  }

  return res.data;
}

export const getPresignedUrlForGet = async (filename: string, config: EngineInteractionConfig): Promise<string> => {
  const {apiEndpoint, taskServiceApiEndpoint, filePath, formContext, shouldUseTaskService} = config;
  const engineAxiosConfig = axiosConfig(apiEndpoint);
  const taskServiceAxiosConfig = axiosConfig(taskServiceApiEndpoint);

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(
      engineAxiosConfig
    ).getPresignedUrlForFileDownload1(
      formContext!.id,
      filename,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    if (shouldUseTaskService) {
      res = await getPresignedUrlForFileDownloadFromTaskservice(
        taskServiceAxiosConfig,
        formContext!.id,
        filename,
        filePath.value
      );
      // res.data does not exist
      return res;
    } else {
      res = await getPresignedUrlForFileDownloadFromEngine(
        engineAxiosConfig,
        formContext!.id,
        filename,
        filePath.value
      );
    }
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(engineAxiosConfig).getPresignedUrlForFileDownload2(
      formContext!.id,
      filename,
      filePath.value
    );
  }

  return res.data;
}

export const getPresignedUrlForDelete = async (filename: string, config: EngineInteractionConfig): Promise<string> => {
  const {apiEndpoint, filePath, formContext, shouldUseTaskService, taskServiceApiEndpoint} = config;
  const engineDeleteAxiosConfig = FetchUtils.getAxiosConfig(FetchUtils.getDELETEConfig());
  engineDeleteAxiosConfig.basePath = apiEndpoint;

  const taskServiceAxiosConfig = axiosConfig(taskServiceApiEndpoint);

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(
      engineDeleteAxiosConfig
    ).getPresignedUrlForFileDeletion1(
      formContext!.id,
      filename,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    if (shouldUseTaskService) {
      res = await getPresignedUrlForFileDeletionFromTaskservice(
        taskServiceAxiosConfig,
        formContext!.id,
        filename,
        filePath.value
      );
      // res.data does not exist
      return res;
    } else {
      res = await getPresignedUrlForFileDeletionFromEngine(
        engineDeleteAxiosConfig,
        formContext!.id,
        filename,
        filePath.value
      );
    }
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(engineDeleteAxiosConfig).getPresignedUrlForFileDeletion2(
      formContext!.id,
      filename,
      filePath.value
    );
  }

  return res.data;
}

export const getFilenames = async (config: EngineInteractionConfig): Promise<string[]> => {
  const {apiEndpoint, filePath, formContext, shouldUseTaskService, taskServiceApiEndpoint} = config;
  const engineAxiosConfig = axiosConfig(apiEndpoint);

  const taskServiceAxiosConfig = axiosConfig(taskServiceApiEndpoint);

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(engineAxiosConfig).getFileNames1(
      formContext!.id,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    if (shouldUseTaskService) {
      res = await getFileNamesFromTaskservice(
        taskServiceAxiosConfig,
        formContext!.id,
        filePath.value
      );
      // res.data does not exist
      return res;
    } else {
      res = await getFileNamesFromEngine(
        engineAxiosConfig,
        formContext!.id,
        filePath.value
      );
    }
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(engineAxiosConfig).getFileNames2(
      formContext!.id,
      filePath.value
    );
  }
  return res.data;
}

const axiosConfig = (basePath: string): Configuration => {
  const cfg = FetchUtils.getAxiosConfig(FetchUtils.getGETConfig());
  cfg.baseOptions.headers = {"Content-Type": "application/json"};
  cfg.basePath = basePath;
  return cfg;
}
