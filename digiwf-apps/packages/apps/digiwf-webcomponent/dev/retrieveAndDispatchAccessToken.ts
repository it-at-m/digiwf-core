import Keycloak from "keycloak-js";

import { ACCESS_TOKEN_EVENT_NAME_DEFAULT } from "../src/util/constants";
import {
  DELAY_INTERVAL_SECONDS,
  KEYCLOAK_AUTH_URL,
  KEYCLOAK_CLIENT_ID,
  KEYCLOAK_REALM,
  KEYCLOAK_TOKEN_MIN_VALIDITY_SECONDS,
  UPDATE_INTERVAL_SECONDS,
} from "./constants";

const keycloak = new Keycloak({
  realm: KEYCLOAK_REALM,
  url: KEYCLOAK_AUTH_URL,
  clientId: KEYCLOAK_CLIENT_ID,
});

function dispatchAccessTokenEvent() {
  keycloak.updateToken(KEYCLOAK_TOKEN_MIN_VALIDITY_SECONDS).then(() => {
    document.dispatchEvent(
      new CustomEvent(ACCESS_TOKEN_EVENT_NAME_DEFAULT, {
        detail: {
          accessToken: keycloak.token,
        },
      })
    );
  });
}

setTimeout(() => {
  dispatchAccessTokenEvent();
  setInterval(() => {
    dispatchAccessTokenEvent();
  }, UPDATE_INTERVAL_SECONDS * 1000);
}, DELAY_INTERVAL_SECONDS * 1000);

keycloak.init({ onLoad: "login-required" }).then((auth) => {
  console.debug("auth", auth);
  dispatchAccessTokenEvent();
});
