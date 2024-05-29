import Pagination from './pagination'

class InProgressGroupTasks extends Pagination {
    headline = "Gruppenaufgaben in Bearbeitung"

    checkHeadline() {
        super._checkHeadline(this.headline);
    }

    clickUpdate() {
        this.paginationElements.update().click()
        cy.wait('@dataGetInProgressGroupTasks').its('response.statusCode').should('equal', 200)
    }
}

module.exports = new InProgressGroupTasks();