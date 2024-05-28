class UserTask {
    elements = {
        abschliessenButton: () => cy.get(`.container form .form-submit-button`),
        headline: () => cy.get('.container h1')
    }

    checkHeadline(text) {
        this.elements.headline().should('contain.text', text)
    }

    clickAbschliessen() {
        this.elements.abschliessenButton().should('be.visible');
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/*',
        }).as('dataGetTasks')
        this.elements.abschliessenButton().click()
        cy.wait('@dataGetTasks').its('response.statusCode').should('equal', 200)
    }
}


module.exports = new UserTask();