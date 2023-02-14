import {FetchUtils, FilterRestControllerApiFactory, SaveFilterTO} from "@muenchen/digiwf-engine-api-internal";
import {EngineServiceApiConfig} from "../EngineServiceApiConfig";

export const callGetFilters = () => {
  const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return FilterRestControllerApiFactory(cfg).getFilters().then(r => Promise.resolve(r.data));
}

export const callSaveFilter = (filter: SaveFilterTO) => {
  const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
  return FilterRestControllerApiFactory(cfg).saveFilter(filter).then(r => Promise.resolve(r.data));
};

export const callDeleteFilter = (id: string) => {
  const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getDELETEConfig());
  return FilterRestControllerApiFactory(cfg).delete(id).then(r => Promise.resolve(r.data));
};

