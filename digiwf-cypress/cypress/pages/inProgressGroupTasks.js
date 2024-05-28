import Pagination from './pagination'

class InProgressGroupTasks extends Pagination {
    headline = "Gruppenaufgaben in Bearbeitung"

    checkHeadline() {
        super._checkHeadline(this.headline);
    }

    clickAktualisieren() {
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/group/*',
        }).as('dataGetGroupTasksForGroup')
        this.paginationElements.update().click()
        cy.wait('@dataGetGroupTasksForGroup').its('response.statusCode').should('equal', 200)
    }
}

module.exports = new InProgressGroupTasks();