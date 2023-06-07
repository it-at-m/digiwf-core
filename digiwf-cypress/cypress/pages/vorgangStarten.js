import Page from './page'

class VorgangStarten extends Page{
    elements = {
        headline: () => cy.get('div.flex:nth-child(1) > h1:nth-child(1)'),
        searchBox: () => cy.get('#suchfeld'),
        listElement: (elementNumber) => cy.get(`.v-data-iterator > div:nth-child(1) > div:nth-child(${elementNumber})`),
        pageNumber: () => cy.get(`.mr-4`),
        numberOfProcesses: () => cy.get(`span.mr-1:nth-child(5)`),
        rightArrow: () => cy.get(`.ml-1`),
        pageSize: () => cy.get(`button.ml-2`),
        pageSize5: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(1)`),
        pageSize10: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(2)`),
        pageSize20: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(3)`),

    }

    checkHeadline(text){
        this.elements.headline().should('contain.text', text)
    }
    checkPageNumber(number){
        this.elements.pageNumber().should('contain.text','Seite '+ number)
    }

    getPageSize(){
        return this.elements.pageSize().invoke('text').then((txt) => {
            return parseInt((txt));
        });
    }

    getFoundProcesses(){
        return this.elements.numberOfProcesses().invoke('text').then((txt) => {
            return parseInt((txt.split(" "))[0]);
        })
    }

    getLastPageNumber(){
        return this.elements.pageNumber().invoke('text').then((txt) => {
            return (parseInt((txt.split(" "))[4]));
        })
    }

    getListElement(num){
        return this.elements.listElement(num)
    }

    findProcess(text){
        this.elements.searchBox().type(text)
    }

    clickListElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    clickRightArrow(){
        this.elements.rightArrow().click()
    }

    changePageSize(number){
        this.elements.pageSize().click()
        if(number == 5){
            this.elements.pageSize5().click()
        }
        else if(number == 20){
            this.elements.pageSize20().click()
        }
        else{
            this.elements.pageSize10().click()
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
        this.elements.rightArrow().then(($btn) => {
            if ($btn.is(":disabled")) {
                return true;
            } else {
                return cy.wrap($btn).click().then(() => {
                    return false;
                });
            }
        }).then((last) => {
            if (!last) {
                iteration = iteration+1;
                this.isLast(iteration++);
            }
        });
    }

}

module.exports = new VorgangStarten();
