class Pagination {
    paginationElements = {
        headline: () => cy.get('.container h1'),
        searchBox: () => cy.get('[data-test="search-field"]'),
        update: () => cy.get("button").contains('Aktualisieren'),
        updateLoading: () => cy.get("button").contains('Aktualisieren').get('.v-progress-circular'),
        list: () => cy.get('.container .v-list'),
        listElement: (elementNumber) => cy.get(`.container .v-list .v-list-item:nth-child(${elementNumber + 1}), .container .v-data-iterator .v-list-item:nth-child(${elementNumber + 1})`),
        nextPage: () => cy.get(`[data-test="pagination-next-page"]`),
        previousPage: () => cy.get(`[data-test="pagination-previous-page"]`),
        numberOfItems: () => cy.get(`[data-test="pagination-item-count"]`),
        pageNumber: () => cy.get(`[data-test="pagination-page-index"]`),
        pageSizeBtn: () => cy.get(`[data-test="pagination-page-size-btn"]`),
        pageSize5: () => cy.get(`[data-test="pagination-page-select"] .v-list-item:nth-child(1)`),
        pageSize10: () => cy.get(`[data-test="pagination-page-select"] .v-list-item:nth-child(2)`),
        pageSize20: () => cy.get(`[data-test="pagination-page-select"] .v-list-item:nth-child(3)`),
    }

    _checkHeadline(text) {
        this.paginationElements.headline()
            .should('contain.text', text)
            .should('be.visible')
    }

    getItemCount() {
        return this.paginationElements.numberOfItems().invoke('text').then((txt) => {
            return parseInt((txt.split(" "))[0]);
        })
    }

    clickItem(index) {
        this.paginationElements.listElement(index).click()
    }

    itemContainsText(itemIndex, text) {
        this.paginationElements.listElement(itemIndex).should('contain.text', text)
    }

    _waitUpdate(interceptName) {
        this.waitLoadingFinished()
        this.paginationElements.update().click()
        this.waitIsLoading()
        cy.wait(interceptName).its('response.statusCode').should('equal', 200)
        this.waitLoadingFinished()
    }

    waitIsLoading() {
        this.paginationElements.updateLoading().should('exist');
    }

    waitLoadingFinished() {
        this.paginationElements.updateLoading().should('not.exist');
    }

    nextPage() {
        this.paginationElements.nextPage().click()
    }

    previousPage() {
        this.paginationElements.previousPage().click()
    }

    getPageSize() {
        return this.paginationElements.pageSize().invoke('text').then((txt) => {
            return parseInt((txt));
        });
    }

    getLastPageNumber() {
        return this.paginationElements.pageNumber().invoke('text').then((txt) => {
            return (parseInt((txt.split(" "))[4]));
        })
    }

    changePageSize(number) {
        this.paginationElements.pageSize().click()
        if (number === 5) {
            this.paginationElements.pageSize5().click()
        } else if (number === 20) {
            this.paginationElements.pageSize20().click()
        } else {
            this.paginationElements.pageSize10().click()
        }
    }

    goToLastPage() {
        let iteration = 1;
        this.isLast(iteration);
    }

    isLast(iteration) {
        const maxIterations = 1000;
        cy.log(iteration.toString())
        if (iteration > maxIterations) {
            cy.log("Maximum iterations reached. Exiting loop.");
            return;
        }
        this.paginationElements.nextPage().then(($btn) => {
            if ($btn.is(":disabled")) {
                return true;
            } else {
                return cy.wrap($btn).click().then(() => {
                    return false;
                });
            }
        }).then((last) => {
            if (!last) {
                iteration = iteration + 1;
                this.isLast(iteration++);
            }
        });
    }
}

export default Pagination