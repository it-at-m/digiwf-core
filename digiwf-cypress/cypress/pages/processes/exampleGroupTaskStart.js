import Task from "../../components/task";
import startProcess from "../startProcess";

class ExampleGroupTaskStart extends Task {
  headline = "Example Grouptask";
  elements = {
    group: () => this.formElements.inputElement("FORMFIELD_group"),
  };

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  setGroup(group) {
    this.elements.group().type(group);
    this.elements.group().blur();
  }

  clickComplete() {
    this.taskElements.completeButton().click();
    startProcess.waitLoadingFinished();
  }
}

module.exports = new ExampleGroupTaskStart();
