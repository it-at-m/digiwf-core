import {
  Configuration,
  FetchUtils,
  HumanTaskFileRestControllerApiFactory, ServiceInstanceFileRestControllerApiFactory,
  ServiceStartFileRestControllerApiFactory
} from "@muenchen/digiwf-engine-api-internal";
import {Ref} from "vue";
import {getPresignedUrlForFileUploadFromEngine} from "@/apiClient/engineCalls";
import {getPresignedUrlForFileUploadFromTaskservice} from "@/apiClient/taskServiceCalls";

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
  const  {axiosConfig: cfg, filePath, formContext} = config;

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
    res = await HumanTaskFileRestControllerApiFactory(
      cfg
    ).getPresignedUrlForFileDownload(
      formContext!.id,
      filename,
      filePath.value
    );
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
  const  {apiEndpoint, filePath, formContext} = config;
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
    res = await HumanTaskFileRestControllerApiFactory(
      cfg
    ).getPresignedUrlForFileDeletion(
      formContext!.id,
      filename,
      filePath.value
    );
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
