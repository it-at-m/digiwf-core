class UserTask {
    elements = {
        completeButton: () => cy.get(`.container form .form-submit-button`),
        headline: () => cy.get('.container h1')
    }

    checkHeadline(text) {
        this.elements.headline().should('contain.text', text)
    }

    clickComplete() {
        this.elements.completeButton().should('be.visible');
        this.elements.completeButton().click()
        cy.wait('@dataGetMyTasks').its('response.statusCode').should('equal', 200)
    }
}


module.exports = new UserTask();