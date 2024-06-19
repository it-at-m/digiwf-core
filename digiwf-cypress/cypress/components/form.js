class Form {
  formElements = {
    inputElement: (inputId) =>
      cy.get(
        '.container form .vjsf-property[class*="' +
          inputId +
          ' "] input[type!="hidden"]'
      ),
    checkboxElement: (inputId) =>
      cy.get(
        '.container form .vjsf-property[class*="' +
          inputId +
          ' "] .v-input--selection-controls__input'
      ),
  };

  setSingleUserInput(input, user) {
    input.type(user);
    cy.wait("@dataUserSearch").its("response.statusCode").should("equal", 200);
    input.type("{enter}");
  }
}

export default Form;
