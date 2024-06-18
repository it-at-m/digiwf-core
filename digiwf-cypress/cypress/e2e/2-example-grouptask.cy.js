import nav from "../components/nav";
import { USER_GROUP } from "../constants/env";
import exampleGroupTask from "../pages/processes/exampleGroupTask";
import exampleGroupTaskStart from "../pages/processes/exampleGroupTaskStart";

beforeEach(() => {
  cy.loginDefault();
});

describe("Example Grouptask", () => {
  it("passes", () => {
    cy.visit("/");
    nav.gatherTaskMetrics("initial");

    cy.log("Start process");
    let startProcess = nav.openStartProcess();
    startProcess.startProcess("Example Grouptask");
    exampleGroupTaskStart.checkHeadline();
    exampleGroupTaskStart.setGroup(USER_GROUP);
    exampleGroupTaskStart.clickComplete();

    cy.log("Test task exists");
    nav.gatherTaskMetrics("created");
    nav.compareTaskMetrics("initial", "created", {
      myTasks: 0,
      openGroupTasks: 1,
      inProgressGroupTasks: 0,
    });

    cy.log("Test task open and assign");
    let openGroupTasks = nav.openOpenGroupTasks();
    openGroupTasks.clickItem(0);
    exampleGroupTask.checkHeadline();
    exampleGroupTask.formElements.groupTaskEdit().click();

    cy.log("Test task assigned");
    nav.gatherTaskMetrics("assigned");
    nav.compareTaskMetrics("created", "assigned", {
      myTasks: 1,
      openGroupTasks: -1,
      inProgressGroupTasks: 1,
    });

    cy.log("Test task complete");
    let myTasks = nav.openMyTasks();
    myTasks.clickItem(0);
    exampleGroupTask.checkHeadline();
    exampleGroupTask.checkCheckbox();
    exampleGroupTask.clickComplete();
    nav.gatherTaskMetrics("finished");
    nav.compareTaskMetrics("assigned", "finished", {
      myTasks: -1,
      openGroupTasks: 0,
      inProgressGroupTasks: -1,
    });
  });
});
