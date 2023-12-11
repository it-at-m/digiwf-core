import {FetchUtils} from "@muenchen/digiwf-engine-api-internal";
import axios from "axios";

export interface StageInfo {
  readonly displayName: string;
  readonly color: string;
}

export interface ApplicationInfo {
  readonly name: string;
  readonly stage: StageInfo;
}

interface ActuatorResponse {
  readonly application: ApplicationInfo;
}



export default class StageInfoService {

  /**
   * Fetches the stage info from the gateways actuator info endpoint.
   */
  static getStageInfo(): Promise<StageInfo> {
    return axios.get<ActuatorResponse>("actuator/info")
      .then(res => res.data?.application.stage)
      .catch(_ => Promise.resolve(StageInfoService.getDefaultStageInfo()));
  }

  static getDefaultStageInfo(): StageInfo {
    return {
      displayName: "",
      color: "#FFCC00"
    };
  }
}

