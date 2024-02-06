import {ApiConfig} from "../ApiConfig";
import {
  FetchUtils,
  PageServiceDefinitionTO,
  ServiceDefinitionControllerApiFactory, ServiceDefinitionDetailTO
} from "@muenchen/digiwf-engine-api-internal";
import {AxiosError} from "axios";


export const callGetProcessDefinitionsFromEngine = (page: number, size: number, query?: string): Promise<PageServiceDefinitionTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());

  return ServiceDefinitionControllerApiFactory(cfg).getServiceDefinitions(page, size, query)
    .then(res => Promise.resolve(res.data))
    .catch(() => Promise.reject("Die Vorgänge konnten nicht geladen werden. Bitte versuchen Sie es erneut."));
};

export const callGetProcessDefinition = (processKey: string): Promise<ServiceDefinitionDetailTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return ServiceDefinitionControllerApiFactory(cfg)
    .getServiceDefinition(processKey)
    .then(res => Promise.resolve<ServiceDefinitionDetailTO>(res.data))
    .catch((e: AxiosError) => {
      const status = e.response?.status;
      if(!status) {
        return Promise.reject("Der Vorgang konnte nicht geladen werden.");
      }
      if(status === 403) {
        return Promise.reject("Es liegt keine Berechtigung zum Starten dieses Vorgangs vor. " +
          "Weitere Infos finden Sie in unseren FAQs in Wilma.");
      }
      return Promise.reject("Der Vorgang konnte nicht geladen werden. Status Code: " + status);
    });
};
