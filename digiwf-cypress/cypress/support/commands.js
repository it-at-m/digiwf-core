import {
  BASE_URL,
  PASSWORD,
  SSO_CLIENT,
  SSO_REALM,
  SSO_URL,
  USERNAME,
} from "../constants/env";

Cypress.Commands.add("loginDefault", () => {
  cy.login({
    root: SSO_URL,
    realm: SSO_REALM,
    username: USERNAME,
    password: PASSWORD,
    client_id: SSO_CLIENT,
    redirect_uri: BASE_URL,
  });
});
