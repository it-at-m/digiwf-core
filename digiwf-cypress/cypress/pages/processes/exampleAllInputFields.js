import Task from "../../components/task";
import { USER_REALNAME, USER2_REALNAME } from "../../constants/env";

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
    switch: (suffix = "") =>
      this.formElements.switchElement(`FormField_switch${suffix}`),
    file: (suffix = "") =>
      this.formElements.inputElement(`FormField_file${suffix}`),
    file2: (suffix = "") =>
      this.formElements.inputElement(`FormField_file_Validation${suffix}`),
    user: (suffix = "") =>
      this.formElements.inputElement(`FormField_user-input${suffix}`),
    multiUser: (suffix = "") =>
      this.formElements.inputElement(`FormField_multi-user-input${suffix}`),
    list: (suffix = "") =>
      this.formElements.inputElement(`FormField_array${suffix}`),
    markdown: (suffix = "") =>
      this.formElements.textareaElement(`FormField_markdown${suffix}`),
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
    this.elements.textfield().click();
    this.elements.switch().click();
    // TODO file
    // TODO file2
    this.setSingleUserInput(this.elements.user(), USER_REALNAME);
    this.elements.textfield().click();
    this.setSingleUserInput(this.elements.multiUser(), USER_REALNAME);
    this.elements.textfield().click();
    this.setSingleUserInput(this.elements.multiUser(), USER2_REALNAME);
    this.elements.textfield().click();
    this.elements.list().type("tag1{enter}tag2{enter}", { force: true });
    this.elements.markdown().type("# Test1{enter}Test asd", { force: true });
  }
}

module.exports = new ExampleAllInputFields();
