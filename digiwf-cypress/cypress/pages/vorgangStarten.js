import Page from './page'

class VorgangStarten extends Page{
    elements = {
        headline: () => cy.get('div.flex:nth-child(1) > h1:nth-child(1)'),
        searchBox: () => cy.get('#suchfeld'),
        listElement: (elementNumber) => cy.get(`.v-data-iterator > div:nth-child(1) > div:nth-child(${elementNumber})`),
        processList: () => cy.get(`.v-data-iterator > div:nth-child(1)`),
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

    checkPageSize(size){
        this.elements.pageSize().should('contain.text',size)
    }

    getListSize(){
        return this.elements.processList
    }

    findProcess(text){
        this.elements.searchBox().type(text)
    }

    clickListElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    getFoundProcesses(){
        return this.elements.numberOfProcesses().invoke('text').then((txt) => {
            return parseInt((txt.split(" "))[0]);
        })
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

    goToLastPage(){
        this.elements.pageNumber().invoke('text').then((txt) => {
            for(let i=1; i<=(parseInt((txt.split(" "))[4])-parseInt((txt.split(" "))[2])); i++){
                this.elements.rightArrow().click();
                this.checkPageNumber(parseInt((txt.split(" "))[2])+i)
            }
        })
    }

    getLastPageNumber(){
        return this.elements.pageNumber().invoke('text').then((txt) => {
            return (parseInt((txt.split(" "))[4]));
        })
    }



}

module.exports = new VorgangStarten();
