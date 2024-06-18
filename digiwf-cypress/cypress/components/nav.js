import inProgressGroupTasks from "../pages/assignedGroupTasks";
import currentInstances from "../pages/currentInstances";
import myTasks from "../pages/myTasks";
import openGroupTasks from "../pages/openGroupTasks";
import startProcess from "../pages/startProcess";

class Nav {
  elements = {
    header: () => cy.get("header"),
    sidebar: () => cy.get("nav"),
    myTasksBtn: () => cy.get('nav [data-test="my-tasks-btn"]'),
    currentInstancesBtn: () =>
      cy.get('nav [data-test="current-instances-btn"]'),
    startProcessBtn: () => cy.get('nav [data-test="start-process-btn"]'),
    openGroupTasksBtn: () => cy.get('nav [data-test="open-group-tasks-btn"]'),
    assignedGroupTasksBtn: () =>
      cy.get('nav [data-test="assigned-group-tasks-btn"]'),
  };

  openMyTasks() {
    this.elements
      .myTasksBtn()
      .click()
      .then(() => {
        myTasks.checkHeadline();
        startProcess._waitUpdate("@dataGetMyTasks");
      });
    return myTasks;
  }

  openCurrentInstances() {
    this.elements
      .currentInstancesBtn()
      .click()
      .then(() => {
        currentInstances.checkHeadline();
        currentInstances.waitLoadingFinished();
        currentInstances.paginationElements.update().click();
        // workaround as request superfast
        // currentInstances.waitIsLoading()
        cy.wait("@dataGetInstances")
          .its("response.statusCode")
          .should("equal", 200);
        currentInstances.waitLoadingFinished();
      });
    return currentInstances;
  }

  openStartProcess() {
    this.elements
      .startProcessBtn()
      .click()
      .then(() => {
        startProcess.checkHeadline();
        startProcess._waitUpdate("@dataGetDefinitions");
      });
    return startProcess;
  }

  openOpenGroupTasks() {
    cy.intercept({
      method: "GET",
      url: "/api/digitalwf-tasklist-service/rest/tasks/group/*",
    }).as("filter");
    this.elements.openGroupTasksBtn().click();
    cy.wait("@filter").its("response.statusCode").should("equal", 200);
    return openGroupTasks;
  }

  openInProgressGroupTasks() {
    cy.intercept({
      method: "GET",
      url: "/api/digitalwf-tasklist-service/rest/tasks/group/*",
    }).as("userTasks");
    this.elements.assignedGroupTasksBtn().click();
    cy.wait("@userTasks").its("response.statusCode").should("equal", 200);
    return inProgressGroupTasks;
  }

  gatherTaskMetrics(
    prefix,
    types = ["myTasks", "openGroupTasks", "inProgressGroupTasks"]
  ) {
    if (types.includes("myTasks")) {
      let myTasks = this.openMyTasks();
      myTasks.getItemCount().as(prefix + "_myTasksCount");
    }
    if (types.includes("openGroupTasks")) {
      let openGroupTasks = this.openOpenGroupTasks();
      openGroupTasks.getItemCount().as(prefix + "_openGroupTasksCount");
    }
    if (types.includes("inProgressGroupTasks")) {
      let inProgressGroupTasks = this.openInProgressGroupTasks();
      inProgressGroupTasks
        .getItemCount()
        .as(prefix + "_inProgressGroupTasksCount");
    }
  }

  compareTaskMetrics(prefix1, prefix2, differences) {
    for (const key in differences) {
      const value = differences[key];
      cy.get(`@${prefix1}_${key}Count`).then((p1) => {
        cy.get(`@${prefix2}_${key}Count`).then((p2) => {
          expect(p2).eq(p1 + value);
        });
      });
    }
  }
}

module.exports = new Nav();
