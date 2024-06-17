


import "./commands";
import "cypress-keycloak";





const DEFAULT_DELAY = 500;

function setupDefaultIntercept(req) {
  req.continue((res) => {
    res.setDelay(DEFAULT_DELAY);
  });
}

beforeEach(() => {
  cy.intercept(
    {
      method: "GET",
      url: "/api/digitalwf-tasklist-service/rest/tasks/user*",
    },
    setupDefaultIntercept
  ).as("dataGetMyTasks");
  cy.intercept(
    {
      method: "GET",
      url: "/api/digitalwf-backend-service/rest/service/instance*",
    },
    setupDefaultIntercept
  ).as("dataGetInstances");
  cy.intercept(
    {
      method: "GET",
      url: "/api/digitalwf-backend-service/rest/service/definition*",
    },
    setupDefaultIntercept
  ).as("dataGetDefinitions");
  cy.intercept(
    {
      method: "GET",
      url: "/api/digitalwf-tasklist-service/rest/tasks/group/unassigned*",
    },
    setupDefaultIntercept
  ).as("dataGetOpenGroupTasks");
  cy.intercept(
    {
      method: "GET",
      url: "/api/digitalwf-tasklist-service/rest/tasks/group/assigned*",
    },
    setupDefaultIntercept
  ).as("dataGetAssignedGroupTasks");
  cy.intercept(
    {
      method: "POST",
      url: "/api/digitalwf-backend-service/rest/user/search",
    },
    setupDefaultIntercept
  ).as("dataUserSearch");
});
