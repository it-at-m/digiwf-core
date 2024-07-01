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
    nav.gatherTaskMetrics("initial", ["myTasks"]);

    cy.log("Start process");
    let startProcess = nav.openStartProcess();
    startProcess.startProcess(exampleUserTaskStart.headline);
    exampleUserTaskStart.checkHeadline();
    exampleUserTaskStart.setUserName(USER_REALNAME);
    exampleUserTaskStart.clickComplete();

    cy.log("Check instance state");
    let currentInstances = nav.openCurrentInstances();
    currentInstances.itemContainsText(0, "Started");

    cy.log("Test task exists");
    let myTasks = nav.openMyTasks();
    myTasks.itemContainsText(0, exampleUserTask.headline);
    myTasks.itemContainsText(0, exampleUserTaskStart.headline);
    nav.gatherTaskMetrics("created", ["myTasks"]);
    nav.compareTaskMetrics("initial", "created", { myTasks: 1 });

    cy.log("Test task open and complete");
    myTasks = nav.openMyTasks();
    myTasks.clickItem(0);
    exampleUserTask.checkHeadline();
    exampleUserTask.clickComplete();

    cy.log("Test task closed");
    myTasks.waitNoUncompletedTasks();
    nav.gatherTaskMetrics("finished", ["myTasks"]);
    nav.compareTaskMetrics("created", "finished", { myTasks: -1 });

    cy.log("Check instance state");
    currentInstances = nav.openCurrentInstances();
    currentInstances.itemContainsText(0, "Finished");
  });
});
