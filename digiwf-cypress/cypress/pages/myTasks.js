import Pagination from "./pagination";

class MyTasks extends Pagination {
    headline = "Meine Aufgaben"

    checkHeadline() {
        super._checkHeadline(this.headline);
    }

    clickUpdate() {
        this.paginationElements.update().click()
        cy.wait('@dataGetMyTasks').its('response.statusCode').should('equal', 200)
    }
}

module.exports = new MyTasks();