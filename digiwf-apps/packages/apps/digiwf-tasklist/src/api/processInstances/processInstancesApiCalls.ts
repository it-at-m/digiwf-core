import {
  FetchUtils,
  PageServiceInstanceTO,
  ServiceDefinitionControllerApiFactory,
  ServiceDefinitionDetailTO,
  ServiceInstanceControllerApiFactory,
  ServiceInstanceDetailTO,
  StartInstanceTO,
} from "@muenchen/digiwf-engine-api-internal";

import { ApiConfig } from "../ApiConfig";

export const callGetProcessInstances = (
  page: number,
  size: number,
  query?: string
): Promise<PageServiceInstanceTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return ServiceInstanceControllerApiFactory(cfg)
    .getAssignedInstances(page, size, query)
    .then((response) => Promise.resolve<PageServiceInstanceTO>(response.data));
};

export const callGetProcessInstance = (
  id: string
): Promise<ServiceInstanceDetailTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return ServiceInstanceControllerApiFactory(cfg)
    .getProcessInstanceDetail(id)
    .then((res) => Promise.resolve(res.data));
};

export const callPostProcessInstance = (
  processKey: string,
  variables: any
): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));

  const request: StartInstanceTO = {
    key: processKey,
    variables,
  };

  return ServiceDefinitionControllerApiFactory(cfg)
    .startInstance(request)
    .then(() => Promise.resolve());
};
