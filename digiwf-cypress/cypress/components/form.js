class Form {
  formElements = {
    completeButton: () => cy.get(`.container form .form-submit-button`),
    headline: () => cy.get(".container h1"),
    inputElement: (inputId) =>
      cy.get(
        '.container form .vjsf-property[class*="' +
          inputId +
          ' "] input[type!="hidden"]'
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
