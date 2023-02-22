import {Configuration} from "@muenchen/digiwf-engine-api-internal";

export class EngineServiceApiConfig {

  public static base: string = '/api/digitalwf-backend-service';

  static getAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.base;
    cfg.baseOptions = fetchConfig;
    return cfg;
  }
}
