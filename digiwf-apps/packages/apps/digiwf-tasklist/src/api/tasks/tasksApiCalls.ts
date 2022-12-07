import {FetchUtils, HumanTaskRestControllerApiFactory, PageHumanTaskTO} from "@muenchen/digiwf-engine-api-internal";
import {ApiConfig} from "../ApiConfig";

// https://www.baeldung.com/spring-data-web-support
// https://github.com/OpenAPITools/openapi-generator/issues/12887
export const callGetTasks = (page: number, size: number): Promise<PageHumanTaskTO> => {
  console.log("callGetTasks", {page, size})
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getTasks(size,page).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
}
