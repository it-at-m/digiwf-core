class Form {
  formElements = {
    completeButton: () => cy.get(`.container form .form-submit-button`),
    groupTaskEdit: () => cy.get("button").contains("Bearbeiten"),
    groupTaskAssign: () => cy.get("button").contains("Zuweisen"),
    headline: () => cy.get(".container h1"),
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

  _checkHeadline(text) {
    this.formElements
      .headline()
      .should("contain.text", text)
      .should("be.visible");
  }

  clickComplete() {
    this.formElements.completeButton().should("be.visible");
    this.formElements.completeButton().click();
    cy.wait("@dataGetMyTasks").its("response.statusCode").should("equal", 200);
  }
}

export default Form;
