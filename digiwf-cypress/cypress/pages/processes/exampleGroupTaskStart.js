import Form from "../../components/form";

class ExampleGroupTaskStart extends Form {
  headline = "Example Grouptask";
  elements = {
    group: () => this.formElements.inputElement("FORMFIELD_group"),
  };

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  setGroup(group) {
    this.elements.group().type(group);
    this.elements.group().blur();
  }

  clickComplete() {
    cy.intercept({
      method: "GET",
      url: "/api/digitalwf-backend-service/rest/filter",
    }).as("dataGetFilter");
    this.formElements.completeButton().click();
    cy.wait("@dataGetFilter", { timeout: 50000 })
      .its("response.statusCode")
      .should("equal", 200);
  }
}

module.exports = new ExampleGroupTaskStart();
