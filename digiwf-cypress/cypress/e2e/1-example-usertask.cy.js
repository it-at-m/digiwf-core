import nav from "../components/nav";
import { USER_REALNAME } from "../constants/env";
import exampleUserTask from "../pages/processes/exampleUserTask";
import exampleUserTaskStart from "../pages/processes/exampleUserTaskStart";

beforeEach(() => {
  cy.loginDefault();
});

describe("Example Usertask", () => {
  it("passes", () => {
    cy.visit("/");
    let myTasks = nav.openMyTasks();
    myTasks.getItemCount().as("initialMyTasksCount");

    cy.log("Start process");
    let startProcess = nav.openStartProcess();
    startProcess.findProcess("Example Usertask");
    startProcess.getItemCount().then((numProcesses) => {
      expect(numProcesses).eq(1);
    });
    startProcess.clickItem(0);
    exampleUserTaskStart.checkHeadline();
    exampleUserTaskStart.setUserName(USER_REALNAME);
    exampleUserTaskStart.clickComplete();

    cy.log("Check instance state");
    let currentInstances = nav.openCurrentInstances();
    currentInstances.itemContainsText(0, "Started");

    cy.log("Test task exists");
    myTasks = nav.openMyTasks();
    myTasks.itemContainsText(0, "User Task");
    myTasks.getItemCount().as("createdMyTasksCount");
    cy.get("@initialMyTasksCount").then((initial) => {
      cy.get("@createdMyTasksCount").then((created) => {
        expect(created).eq(initial + 1);
      });
    });

    cy.log("Test task open and complete");
    myTasks.clickItem(0);
    exampleUserTask.checkHeadline();
    exampleUserTask.clickComplete();

    cy.log("Test task closed");
    myTasks.waitNoUncompletedTasks();
    myTasks.getItemCount().as("finishedMyTasksCount");
    cy.get("@createdMyTasksCount").then((created) => {
      cy.get("@finishedMyTasksCount").then((finished) => {
        expect(finished).eq(created - 1);
      });
    });

    cy.log("Check instance state");
    currentInstances = nav.openCurrentInstances();
    currentInstances.itemContainsText(0, "Finished");
  });
});
