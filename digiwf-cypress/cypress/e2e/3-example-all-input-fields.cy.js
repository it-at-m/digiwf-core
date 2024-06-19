import nav from "../components/nav";
import { USER_REALNAME } from "../constants/env";
import exampleAllInputFields from "../pages/processes/exampleAllInputFields";
import exampleAllInputFieldsStart from "../pages/processes/exampleAllInputFieldsStart";

beforeEach(() => {
  cy.loginDefault();
});

describe("Example Usertask", () => {
  it("passes", () => {
    cy.visit("/");

    cy.log("Start process");
    let startProcess = nav.openStartProcess();
    startProcess.startProcess(exampleAllInputFieldsStart.headline);
    exampleAllInputFieldsStart.checkHeadline();
    exampleAllInputFieldsStart.setUser(USER_REALNAME);
    exampleAllInputFieldsStart.clickComplete();

    cy.log("First task");
    let myTasks = nav.openMyTasks();
    myTasks.itemContainsText(0, "User Task");
    myTasks.itemContainsText(0, exampleAllInputFieldsStart.headline);
    myTasks.clickItem(0);
    exampleAllInputFields.checkHeadline1();
    exampleAllInputFields.clickComplete();
    exampleAllInputFields.hasValidationAlert();
    exampleAllInputFields.fillDefault();
  });
});
