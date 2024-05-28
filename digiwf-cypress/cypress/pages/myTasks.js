import Pagination from "./pagination";

class MyTasks extends Pagination {
    headline = "Meine Aufgaben"

    checkHeadline() {
        super._checkHeadline(this.headline);
    }

    clickAktualisieren() {
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/*',
        }).as('dataGetFilter')
        this.paginationElements.update().click()
        cy.wait('@dataGetFilter').its('response.statusCode').should('equal', 200)
    }
}

module.exports = new MyTasks();