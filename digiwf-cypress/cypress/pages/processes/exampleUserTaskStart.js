import Form from "../../components/form";
import startProcess from "../startProcess";

class ExampleUserTaskStart extends Form {
  headline = "Example Usertask";
  elements = {
    userForTask: () => this.formElements.inputElement("FORMFIELD_User"),
  };

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  setUserName(user) {
    this.elements.userForTask().type(user);
    cy.wait("@dataUserSearch").its("response.statusCode").should("equal", 200);
    this.elements.userForTask().type("{enter}");
  }

  clickComplete() {
    this.formElements.completeButton().click();
    startProcess.waitLoadingFinished();
  }
}

module.exports = new ExampleUserTaskStart();
