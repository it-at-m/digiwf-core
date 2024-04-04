import { Configuration } from "@muenchen/digiwf-engine-api-internal";

export class ApiConfig {
  public static base: string = import.meta.env.VITE_VUE_APP_API_URL
    ? import.meta.env.VITE_VUE_APP_API_URL
    : "api/digitalwf-backend-service";
  public static tasklistBase: string = import.meta.env
    .VITE_VUE_APP_TASKLIST_API_URL
    ? import.meta.env.VITE_VUE_APP_TASKLIST_API_URL
    : "api/digitalwf-tasklist-service/rest";
  public static mucsDmsBase: string = import.meta.env
    .VITE_VUE_APP_MUCS_DMS_API_URL
    ? import.meta.env.VITE_VUE_APP_MUCS_DMS_API_URL
    : "api/dms-integration/mucs/rest/";
  public static alwDmsBase: string = import.meta.env
    .VITE_VUE_APP_ALW_DMS_API_URL
    ? import.meta.env.VITE_VUE_APP_ALW_DMS_API_URL
    : "api/dms-integration/alw/rest/";

  /**
   * @deprecated
   * @param fetchConfig
   */
  static getAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.base;
    cfg.baseOptions = fetchConfig;
    return cfg;
  }
  static getTasklistAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.tasklistBase;
    cfg.baseOptions = fetchConfig;
    return cfg;
  }

  static getMucsDmsAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.mucsDmsBase;
    cfg.baseOptions = fetchConfig;
    return cfg;
  }

  static getAlwDmsAxiosConfig(fetchConfig: RequestInit): Configuration {
    const cfg = new Configuration();
    cfg.basePath = this.alwDmsBase;
    cfg.baseOptions = fetchConfig;
    return cfg;
  }
}
