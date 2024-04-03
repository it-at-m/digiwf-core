import {
  Configuration,
  FetchUtils,
  Metadata,
} from "@muenchen/digiwf-dms-api-internal";

import { getMetadataFromDmsservice } from "@/apiClient/dmsApiCalls";

export enum Objectclass {
  Sachakte = "Sachakte",
  Vorgang = "Vorgang",
  Eingang = "Eingang",
  Ausgang = "Ausgang",
  Intern = "Intern",
  Schriftstueck = "Schriftstueck",
}

export const getMetadata = async (
  ojectclass: Objectclass,
  coo: string,
  apiEndpoint: string
): Promise<Metadata> => {
  const dmsAxiosConfig = axiosConfig(apiEndpoint);
  return getMetadataFromDmsservice(ojectclass, coo, dmsAxiosConfig);
};

const axiosConfig = (basePath: string): Configuration => {
  const cfg = FetchUtils.getAxiosConfig(FetchUtils.getGETConfig());
  cfg.baseOptions.headers = { "Content-Type": "application/json" };
  cfg.basePath = basePath;
  return cfg;
};
