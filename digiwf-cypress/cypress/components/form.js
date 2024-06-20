class Form {
  formElements = {
    form: () => cy.get(".container form .vjsf-property-allOf-0"),
    tab: (index) =>
      cy.get(
        `.container form .v-tabs [role="tablist"] .v-tab:nth-of-type(${index + 1})`
      ),
    loadingInput: () => cy.get(".container form .v-input--is-loading"),
    inputElement: (inputId) =>
      cy.get(
        `.container form .vjsf-property[class*="${inputId} "] input[type!="hidden"]`
      ),
    textareaElement: (inputId) =>
      cy.get(
        `.container form .vjsf-property[class*="${inputId} "] textarea[type!="hidden"]:visible`
      ),
    checkboxElement: (inputId) =>
      cy.get(
        `.container form .vjsf-property[class*="${inputId} "] .v-input--selection-controls__input`
      ),
    switchElement: (inputId) =>
      cy.get(
        `.container form .vjsf-property[class*="${inputId} "] .v-input--selection-controls__ripple`
      ),
    selectDropdown: (index) =>
      cy.get(`[role="listbox"]:visible .v-list-item:nth-child(${index + 1})`),
  };

  waitFormVisible() {
    this.formElements.form().should("be.visible");
  }

  waitLoadingFinished() {
    this.formElements.loadingInput().should("not.exist");
  }

  setUserInput(input, user) {
    input.type(user, { force: true });
    cy.wait("@dataUserSearch").its("response.statusCode").should("equal", 200);
    this.formElements
      .selectDropdown(0)
      .contains("Benutzer werden gesucht")
      .should("not.exist");
    this.formElements.selectDropdown(0).click();
  }

  setSelect(input, indexes) {
    input.click();
    for (const i of indexes) {
      this.formElements.selectDropdown(i).click();
    }
  }

  selectHasValue(input, items) {
    let res = [];
    input
      .parent()
      .find(".v-select__selection span")
      .each((i) => {
        cy.wrap(i)
          .invoke("text")
          .then((text) => {
            res.push(text.trim().replace(",", ""));
          });
      });
    cy.wrap(res).should("deep.equal", items);
  }
}

export default Form;
