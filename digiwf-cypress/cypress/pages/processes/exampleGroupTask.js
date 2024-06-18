import Form from "../../components/form";

class ExampleGroupTask extends Form {
  headline = "Group User Task";
  elements = {
    checkbox: () => this.formElements.checkboxElement("FORMFIELD_checkbox"),
    checkboxInput: () => this.formElements.inputElement("FORMFIELD_checkbox"),
  };

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  checkCheckbox() {
    this.elements.checkbox().should("be.visible");
    this.elements.checkbox().click();
    this.elements.checkboxInput().should("be.checked");
  }
}

module.exports = new ExampleGroupTask();
