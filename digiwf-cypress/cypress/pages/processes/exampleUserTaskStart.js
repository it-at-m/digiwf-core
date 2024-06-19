import Task from "../../components/task";
import startProcess from "../startProcess";

class ExampleUserTaskStart extends Task {
  headline = "Example Usertask";
  elements = {
    userForTask: () => this.formElements.inputElement("FORMFIELD_User"),
  };

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  setUserName(user) {
    this.setSingleUserInput(this.elements.userForTask(), user);
  }

  clickComplete() {
    this.taskElements.completeButton().click();
    startProcess.waitLoadingFinished();
  }
}

module.exports = new ExampleUserTaskStart();
