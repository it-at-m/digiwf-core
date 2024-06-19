import Task from "../../components/task";
import startProcess from "../startProcess";

class ExampleAllInputFieldsStart extends Task {
  headline = "Example All Input Fields";
  elements = {
    user: () => this.formElements.inputElement("FormField_User"),
  };

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  setUser(user) {
    this.setSingleUserInput(this.elements.user(), user);
  }

  clickComplete() {
    this.taskElements.completeButton().click();
    startProcess.waitLoadingFinished();
  }
}

module.exports = new ExampleAllInputFieldsStart();
