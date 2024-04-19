import Keycloak from "keycloak-js";

import { ACCESS_TOKEN_EVENT_NAME_DEFAULT } from "@/util/constants";

const keycloak = new Keycloak({
  realm: import.meta.env.VITE_KEYCLOAK_REALM,
  url: import.meta.env.VITE_KEYCLOAK_AUTH_URL,
  clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID,
});

function dispatchAccessTokenEvent() {
  keycloak
    .updateToken(import.meta.env.VITE_KEYCLOAK_TOKEN_MIN_VALIDITY_SECONDS)
    .then(() => {
      document.dispatchEvent(
        new CustomEvent(ACCESS_TOKEN_EVENT_NAME_DEFAULT, {
          detail: {
            accessToken: keycloak.token,
          },
        })
      );
    })
    .catch((err) => {
      console.error(err);
    });
}

setTimeout(
  () => {
    dispatchAccessTokenEvent();
    setInterval(
      () => {
        dispatchAccessTokenEvent();
      },
      import.meta.env.VITE_UPDATE_INTERVAL_SECONDS * 1000
    );
  },
  import.meta.env.VITE_DELAY_INTERVAL_SECONDS * 1000
);

keycloak
  .init({ onLoad: "login-required" })
  .then((auth) => {
    console.debug("auth", auth);
    dispatchAccessTokenEvent();
  })
  .catch((err) => {
    console.error(err);
  });
