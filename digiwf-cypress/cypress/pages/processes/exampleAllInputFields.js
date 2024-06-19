import Task from "../../components/task";

class ExampleAllInputFields extends Task {
  headline1 = "User Task";
  elements = {
    textfield: (suffix = "") =>
      this.formElements.inputElement(`FormField_text${suffix}`),
    textarea: (suffix = "") =>
      this.formElements.textareaElement(`FormField_textarea${suffix}`),
    integer: (suffix = "") =>
      this.formElements.inputElement(`FormField_integer${suffix}`),
    number: (suffix = "") =>
      this.formElements.inputElement(`FormField_number${suffix}`),
    checkbox: (suffix = "") =>
      this.formElements.checkboxElement(`FormField_boolean${suffix}`),
    date: (suffix = "") =>
      this.formElements.inputElement(`FormField_date${suffix}`),
    time: (suffix = "") =>
      this.formElements.inputElement(`FormField_time${suffix}`),
    select: (suffix = "") =>
      this.formElements.inputElement(`FormField_select${suffix}`),
    multiselect: (suffix = "") =>
      this.formElements.inputElement(`FormField_multiselect${suffix}`),
  };

  checkHeadline1() {
    super._checkHeadline(this.headline1);
  }

  hasValidationAlert() {
    this._hasAlertMessage("Validierung Ihrer Eingaben fehlgeschlagen");
  }

  fillDefault() {
    this.elements.textfield().type("textfield_test");
    this.elements.textarea().type("textarea_test\ntest");
    this.elements.integer().type("123");
    this.elements.number().type("123,123");
    this.elements.checkbox().click();
    this.elements.date().type("2024-01-30");
    this.elements.time().type("13:45");
    this.setSelect(this.elements.select(), 0);
    this.elements.textfield().click();
    this.setSelect(this.elements.multiselect(), [0, 1]);
  }
}

module.exports = new ExampleAllInputFields();
