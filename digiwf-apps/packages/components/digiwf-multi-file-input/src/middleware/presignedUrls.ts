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
  readonly axiosConfig: Configuration;
  readonly formContext: any;
  readonly apiEndpoint: string;
  readonly filePath: Ref<string>
  readonly shouldUseTaskService: boolean;
  readonly taskServiceApiEndpoint: string;
}
export const getPresignedUrlForPost = async (file: File, config: EngineInteractionConfig): Promise<string> => {
  const  {axiosConfig: cfg, filePath, formContext, shouldUseTaskService} = config;

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(cfg).getPresignedUrlForFileUpload1(
      formContext!.id,
      file!.name,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    if(shouldUseTaskService) {
      // FIXME: replace cfg with tasklist cfg
      res = await getPresignedUrlForFileUploadFromTaskservice(cfg, formContext!.id, file!.name, filePath.value)
    } else {
      res = await getPresignedUrlForFileUploadFromEngine(cfg, formContext!.id, file!.name, filePath.value)
    }

  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(cfg).getPresignedUrlForFileUpload2(
      formContext!.id,
      file!.name,
      filePath.value
    );
  }

  return res.data;
}

export const getPresignedUrlForGet = async (filename: string, config: EngineInteractionConfig): Promise<string> => {
  const  {axiosConfig: cfg, filePath, formContext, shouldUseTaskService} = config;

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(
      cfg
    ).getPresignedUrlForFileDownload1(
      formContext!.id,
      filename,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    if(shouldUseTaskService) {
      // FIXME: replace cfg with tasklist cfg
      res = await getPresignedUrlForFileDownloadFromTaskservice(
        cfg,
        formContext!.id,
        filename,
        filePath.value
      );
    } else {
      res = await getPresignedUrlForFileDownloadFromEngine(
        cfg,
        formContext!.id,
        filename,
        filePath.value
      );
    }
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(cfg).getPresignedUrlForFileDownload2(
      formContext!.id,
      filename,
      filePath.value
    );
  }

  return res.data;
}

export const getPresignedUrlForDelete = async (filename: string, config: EngineInteractionConfig): Promise<string> => {
  const  {apiEndpoint, filePath, formContext, shouldUseTaskService} = config;
  const cfg = FetchUtils.getAxiosConfig(FetchUtils.getDELETEConfig());
  cfg.basePath = apiEndpoint;

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(
      cfg
    ).getPresignedUrlForFileDeletion1(
      formContext!.id,
      filename,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    if(shouldUseTaskService) {
      // FIXME: replace cfg with tasklist cfg
      res = await getPresignedUrlForFileDeletionFromTaskservice(
        cfg,
        formContext!.id,
        filename,
        filePath.value
      );
    } else {
      res = await getPresignedUrlForFileDeletionFromEngine(
        cfg,
        formContext!.id,
        filename,
        filePath.value
      );
    }
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(cfg).getPresignedUrlForFileDeletion2(
      formContext!.id,
      filename,
      filePath.value
    );
  }

  return res.data;
}

export const getFilenames = async (config: EngineInteractionConfig): Promise<string[]> => {
  const  {axiosConfig: cfg, filePath, formContext, shouldUseTaskService} = config;

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(cfg).getFileNames1(
      formContext!.id,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    if(shouldUseTaskService) {
      // FIXME: replace cfg with tasklist cfg
      res = await getFileNamesFromTaskservice(
        cfg,
        formContext!.id,
        filePath.value
      );
    } else {
      res = await getFileNamesFromEngine(
        cfg,
        formContext!.id,
        filePath.value
      );
    }
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(cfg).getFileNames2(
      formContext!.id,
      filePath.value
    );
  }

  return res.data;
}
