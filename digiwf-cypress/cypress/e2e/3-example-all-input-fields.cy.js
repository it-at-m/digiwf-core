import nav from "../components/nav";
import { USER_REALNAME } from "../constants/env";
import exampleAllInputFields from "../pages/processes/exampleAllInputFields";
import exampleAllInputFieldsStart from "../pages/processes/exampleAllInputFieldsStart";

beforeEach(() => {
  cy.loginDefault();
});

describe("Example All Input Fields", () => {
  it("passes", () => {
    cy.visit("/");

    cy.log("Start process");
    let startProcess = nav.openStartProcess();
    startProcess.startProcess(exampleAllInputFieldsStart.headline);
    exampleAllInputFieldsStart.checkHeadline();
    exampleAllInputFieldsStart.setUser(USER_REALNAME);
    exampleAllInputFieldsStart.clickComplete();

    cy.log("Fill out first task");
    let myTasks = nav.openMyTasks();
    myTasks.itemContainsText(0, exampleAllInputFields.headline1);
    myTasks.itemContainsText(0, exampleAllInputFieldsStart.headline);
    myTasks.clickItem(0);
    exampleAllInputFields.checkHeadline1();
    exampleAllInputFields.waitFormVisible();
    exampleAllInputFields.clickComplete();
    exampleAllInputFields.hasValidationAlert();
    exampleAllInputFields.fillDefault();
    exampleAllInputFields.clickComplete();

    cy.log("Validate second task");
    myTasks.waitNoUncompletedTasks();
    myTasks.itemContainsText(0, exampleAllInputFields.headline2);
    myTasks.itemContainsText(0, exampleAllInputFieldsStart.headline);
    myTasks.clickItem(0);
    exampleAllInputFields.checkHeadline2();
    exampleAllInputFields.waitFormVisible();
    exampleAllInputFields.validateDefault();
    exampleAllInputFields.clickComplete();
    myTasks.waitNoUncompletedTasks();
  });
});
