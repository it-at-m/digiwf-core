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
        startProcess._waitUpdate("@dataGetInstances");
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
    this.elements
      .openGroupTasksBtn()
      .click()
      .then(() => {
        openGroupTasks.checkHeadline();
        startProcess._waitUpdate("@dataGetOpenGroupTasks");
      });
    return openGroupTasks;
  }

  openInProgressGroupTasks() {
    this.elements
      .assignedGroupTasksBtn()
      .click()
      .then(() => {
        openGroupTasks.checkHeadline();
        startProcess._waitUpdate("@dataGetAssignedGroupTasks");
      });
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
