class Form {
  formElements = {
    form: () => cy.get(".container form .vjsf-property-allOf-0"),
    inputElement: (inputId) =>
      cy.get(
        '.container form .vjsf-property[class*="' +
          inputId +
          ' "] input[type!="hidden"]'
      ),
    textareaElement: (inputId) =>
      cy.get(
        '.container form .vjsf-property[class*="' +
          inputId +
          ' "] textarea[type!="hidden"]'
      ),
    checkboxElement: (inputId) =>
      cy.get(
        '.container form .vjsf-property[class*="' +
          inputId +
          ' "] .v-input--selection-controls__input'
      ),
    selectDropdown: (index) =>
      cy.get(`[role="listbox"]:visible .v-list-item:nth-child(${index + 1})`),
  };

  waitFormVisible() {
    this.formElements.form().should("be.visible");
  }

  setSingleUserInput(input, user) {
    input.type(user);
    cy.wait("@dataUserSearch").its("response.statusCode").should("equal", 200);
    this.formElements.selectDropdown(0).click();
  }

  setSelect(input, indexes) {
    input.click();
    if (indexes instanceof Number) {
      this.formElements.selectDropdown(Number(indexes)).click();
    } else if (indexes instanceof Array) {
      for (const i in indexes) {
        this.formElements.selectDropdown(Number(i)).click();
      }
    }
  }
}

export default Form;
