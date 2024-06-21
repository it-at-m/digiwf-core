import Task from "../../components/task";
import {
  USER_GROUP,
  USER_REALNAME,
  USER2_GROUP,
  USER2_REALNAME,
} from "../../constants/env";

class ExampleAllInputFields extends Task {
  headline1 = "User Task";
  headline2 = "Second User Task";
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
    checkboxInput: (suffix = "") =>
      this.formElements.inputElement(`FormField_boolean${suffix}`),
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
    switchInput: (suffix = "") =>
      this.formElements.inputElement(`FormField_switch${suffix}`),
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
    optionalGroup: () => this.formElements.inputElement("optionalGroup"),
    // FIXME selector
    optionalContainer: () =>
      this.formElements.inputElement("vjsf-property-allOf-2-allOf-2"),
  };

  checkHeadline1() {
    super._checkHeadline(this.headline1);
  }

  checkHeadline2() {
    super._checkHeadline(this.headline2);
  }

  hasValidationAlert() {
    this._hasAlertMessage("Validierung Ihrer Eingaben fehlgeschlagen");
  }

  fillDefault() {
    // first page
    this.elements.textfield().type("textfield_test");
    this.elements.textarea().type("textarea_test\ntest");
    this.elements.integer().type("123");
    // FIXME
    this.elements.number().type("123,123");
    this.elements.checkbox().click();
    this.elements.date().type("2024-01-30");
    this.elements.time().type("13:45");
    this.setSelect(this.elements.select(), [0]);
    this.elements.textfield().click();
    this.setSelect(this.elements.multiselect(), [0, 1]);
    this.elements.textfield().click();
    this.elements.switch().click();
    this.elements.file().selectFile(
      [
        {
          contents: Cypress.Buffer.from("Test text"),
          fileName: "test.txt",
          mimeType: "text/plain",
        },
        {
          contents: Cypress.Buffer.from("Test text 2"),
          fileName: "test2.txt",
          mimeType: "text/plain",
        },
      ],
      { force: true }
    );
    this.elements
      .file2()
      .selectFile("res/example-cosys-document.pdf", { force: true });
    this.setUserInput(this.elements.user(), USER_REALNAME);
    this.elements.textfield().click();
    this.setUserInput(this.elements.multiUser(), USER_REALNAME);
    this.elements.textfield().click();
    this.setUserInput(this.elements.multiUser(), USER2_REALNAME);
    this.elements.textfield().click();
    this.elements.list().type("tag1{enter}tag2{enter}", { force: true });
    this.elements.markdown().type("# Test1\nTest asd", { force: true });

    // objects and optionals page
    this.formElements.tab(2).click();
    this.setSelect(this.elements.optionalGroup(), [0]);
    this.setSelect(this.elements.optionalContainer(), [0]);
  }

  validateDefault() {
    this.waitLoadingFinished();
    // first page
    this.elements.textfield().should("have.value", "textfield_test");
    this.elements.textarea().should("have.value", "textarea_test\ntest");
    this.elements.integer().should("have.value", "123");
    // FIXME
    // this.elements.number().should("have.value", "123.123");
    this.elements.checkboxInput().should("be.checked");
    this.elements.date().should("have.value", "2024-01-30");
    this.elements.time().should("have.value", "13:45");
    this.selectHasValue(this.elements.select(), ["1"]);
    this.selectHasValue(this.elements.multiselect(), ["1", "2"]);
    this.elements.switchInput().should("be.checked");
    this.multiFileInputHasValues(this.elements.file(), [
      "test.txt",
      "test2.txt",
    ]);
    this.multiFileInputHasValues(this.elements.file2(), [
      "example-cosys-document.pdf",
    ]);
    this.userInputHasValue(this.elements.user(), USER_REALNAME);
    this.comboboxHasValues(this.elements.multiUser(), [
      `${USER_REALNAME} (${USER_GROUP})`,
      `${USER2_REALNAME} (${USER2_GROUP})`,
    ]);
    this.comboboxHasValues(this.elements.list(), ["tag1", "tag2"]);
    this.markdownHasValue(this.elements.markdown(), "# Test1Test asd");
  }
}

module.exports = new ExampleAllInputFields();
