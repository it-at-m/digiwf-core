import { FetchUtils } from "@muenchen/digiwf-engine-api-internal";

export interface StageInfo {
  displayName: string;
  color: string;
}

export default class StageInfoService {
  // gateways actuator info endpoint
  // private static base: string | undefined = "http://localhost:8083/"; // for local development
  private static base: string | undefined = "/";

  /**
   * Fetches the stage info from the gateways actuator info endpoint.
   */
  static async getStageInfo(): Promise<StageInfo> {
    try {
      const response = await fetch(`${ this.base }actuator/info`, FetchUtils.getGETConfig());
      const data = await response.json();
      return data.application.stage;
    } catch (error) {
      return this.getDefaultStageInfo();
    }
  }

  static getDefaultStageInfo(): StageInfo {
    return {
      displayName: "",
      color: "#FFCC00"
    }
  }

}

