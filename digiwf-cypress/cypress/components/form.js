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
}

export default Form;
