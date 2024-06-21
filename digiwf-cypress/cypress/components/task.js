import openGroupTasks from "../pages/openGroupTasks";
import Form from "./form";

class Task extends Form {
  taskElements = {
    completeButton: () => cy.get(`.container form .form-submit-button`),
    headline: () => cy.get(".container h1"),
  };
  groupTaskElements = {
    assignSelfBtn: () => cy.get("button").contains("Bearbeiten"),
    assignSelfSubmit: () => cy.get('[data-test="assign-self-submit"]'),
    assignBtn: () => cy.get("button").contains("Zuweisen"),
    assignInput: () =>
      cy.get('[data-test="assign-task-input"] input[type!="hidden"]'),
    assignSubmit: () => cy.get('[data-test="assign-task-submit"]'),
  };

  _checkHeadline(text) {
    this.taskElements
      .headline()
      .should("contain.text", text)
      .should("be.visible");
  }

  clickComplete() {
    this.taskElements.completeButton().should("be.visible");
    this.taskElements.completeButton().click();
    cy.wait("@dataGetMyTasks").its("response.statusCode").should("equal", 200);
  }

  assignGroupTaskSelf() {
    this.groupTaskElements.assignSelfBtn().click();
  }

  assignGroupTask(userRealname) {
    this.groupTaskElements.assignBtn().click();
    this.groupTaskElements.assignInput().should("be.visible");
    this.groupTaskElements.assignInput().type(userRealname);
    cy.wait("@dataUserSearch").its("response.statusCode").should("equal", 200);
    this.groupTaskElements.assignInput().type("{enter}");
    this.groupTaskElements.assignSubmit().click();
    openGroupTasks.waitLoadingFinished();
  }

  assignGroupTaskSelfOverride() {
    this.groupTaskElements.assignSelfBtn().click();
    this.groupTaskElements.assignSelfSubmit().click();
    this.taskElements.headline().should("be.visible");
  }
}

export default Task;
