import axios from "axios";

/**
 * heartbeats the api gateway for getting new session via set-cookie header
 */
export const startSessionReloading = () => {
  callSessionInformation().then(sessionInformation => {
    setInterval(() => {
        callSessionInformation()
      }, (sessionInformation.timeoutInSeconds - 5) * 1000
    )
  })
}

interface SessionInformationResponse {
  readonly timeoutInSeconds: number;
}

const callSessionInformation = () => axios.get<SessionInformationResponse>("/api/gateway/session").then(r => Promise.resolve(r.data))
