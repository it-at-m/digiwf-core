import Pagination from "./pagination";

class StartProcess extends Pagination {
    headline = "Vorgänge"

    checkHeadline() {
        super._checkHeadline(this.headline);
    }

    findProcess(text) {
        this.paginationElements.searchBox().type(text)
            .then(() => {
                this.waitIsLoading()
                cy.wait('@dataGetDefinitions').its('response.statusCode').should('equal', 200)
                this.waitLoadingFinished()
            })
    }

}

module.exports = new StartProcess();
