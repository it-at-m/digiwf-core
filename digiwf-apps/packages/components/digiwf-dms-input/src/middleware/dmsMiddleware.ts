import {
  getMetadataFromDmsservice
} from "@/apiClient/dmsApiCalls";
import { Configuration, FetchUtils, Metadata } from "@muenchen/digiwf-dms-api-internal";
import {Objectclass} from "../../types";


export const getMetadataFromDmsservice = async (ojectclass: Objectclass, url: string, apiEndpoint: string): Promise<Metadata> => {
  const axiosConfig = axiosConfig(apiEndpoint);
  return getMetadataFromDmsservice(ojectclass,url,axiosConfig);
}

const axiosConfig = (basePath: string): Configuration => {
  const cfg = FetchUtils.getAxiosConfig(FetchUtils.getGETConfig());
  cfg.baseOptions.headers = {"Content-Type": "application/json"};
  cfg.basePath = basePath;
  return cfg;
}
