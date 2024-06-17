import Form from "../../components/form";

class ExampleUserTaskStart extends Form {
    headline = "Example Usertask"
    elements = {
        userForTask: () => this.formElements.inputElement("FORMFIELD_User")
    }

    checkHeadline() {
        super._checkHeadline(this.headline);
    }

    setUserName(user) {
        this.elements.userForTask().type(user)
        cy.wait('@dataUserSearch').its('response.statusCode').should('equal', 200)
        this.elements.userForTask().type('{enter}')
    }

    clickComplete() {
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-backend-service/rest/filter',
        }).as('dataGetFilter')
        this.formElements.completeButton().click()
        cy.wait('@dataGetFilter', {timeout: 50000}).its('response.statusCode').should('equal', 200)
    }
}

module.exports = new ExampleUserTaskStart();