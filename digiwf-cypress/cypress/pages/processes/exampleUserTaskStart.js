import Form from "../../components/form";

class ExampleUserTaskStart extends Form {
    headline = "Example Usertask"
    elements = {
        numberOfParallelTasks: () => cy.get("#allOf-0-allOf-0-FORMFIELD_NumberOfTasks"),
        userForTask: () => this.formElements.inputElement("FORMFIELD_User"),
        completeButton: () => cy.get(`button.mt-5`)
    }

    checkHeadline() {
        super._checkHeadline(this.headline);
    }

    setNumberOfTasks(number) {
        this.elements.numberOfParallelTasks().clear()
        this.elements.numberOfParallelTasks().type(number)
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
        this.elements.completeButton().click()
        cy.wait('@dataGetFilter', {timeout: 50000}).its('response.statusCode').should('equal', 200)
    }
}


module.exports = new ExampleUserTaskStart();