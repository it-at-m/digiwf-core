import Form from "../../components/form";
import startProcess from "../startProcess";

class ExampleGroupTaskStart extends Form {
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
    this.formElements.completeButton().click();
    startProcess.waitLoadingFinished();
  }
}

module.exports = new ExampleGroupTaskStart();
