import {FetchUtils, FilterRestControllerApiFactory, SaveFilterTO} from "@muenchen/digiwf-engine-api-internal";
import {ApiConfig} from "../ApiConfig";

export const callGetFilters = () => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return FilterRestControllerApiFactory(cfg).getFilters().then(r => Promise.resolve(r.data));
}

export const callSaveFilter = (filter: SaveFilterTO) => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
  return FilterRestControllerApiFactory(cfg).saveFilter(filter).then(r => Promise.resolve(r.data));
};

export const callDeleteFilter = (id: string) => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getDELETEConfig());
  return FilterRestControllerApiFactory(cfg).delete(id).then(r => Promise.resolve(r.data));
};

