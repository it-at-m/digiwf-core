import {
  Configuration,
  FetchUtils,
  FilterRestControllerApiFactory,
  SaveFilterTO
} from "@muenchen/digiwf-engine-api-internal";
import {EngineServiceApiConfig} from "../EngineServiceApiConfig";
import {configuredAxios} from "../statusCodeHandling";

export const callGetFilters = () => {
  const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return getFactoryFromConfig(cfg).getFilters().then(r => Promise.resolve(r.data));
}

export const callSaveFilter = (filter: SaveFilterTO) => {
  const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
  return getFactoryFromConfig(cfg).saveFilter(filter).then(r => Promise.resolve(r.data));
};

export const callDeleteFilter = (id: string) => {
  const cfg = EngineServiceApiConfig.getAxiosConfig(FetchUtils.getDELETEConfig());
  // delete function without _ does not exist
  return getFactoryFromConfig(cfg)._delete(id).then(r => Promise.resolve(r.data));
};

const getFactoryFromConfig = (cfg: Configuration) => FilterRestControllerApiFactory(cfg, undefined, configuredAxios)

