import Page from './page'

const textHeadline = "Aktuelle Vorgänge";

class AktuelleVorgaenge extends Page{

    elements = {
        listElement: (elementNumber) => cy.get(`div.v-list:nth-child(4) > div:nth-child(${elementNumber})`),
        listElementAttribute: (elementNumber, column) => cy.get(`.v-data-iterator > div:nth-child(1) > div:nth-child(${elementNumber}) > a:nth-child(1) > div:nth-child(${column})`),
        headline: () => cy.get('.layout > div:nth-child(1) > div:nth-child(1) > h1:nth-child(1)'),
        update: () => cy.get('.v-size--large'),
        searchBox: () => cy.get('#suchfeld'),
        numberOfTasks: () => cy.get(`span.mr-1:nth-child(5)`),
        rightArrow: () => cy.get(`.mdi-chevron-right`),
        leftArrow: () => cy.get(`.mdi-chevron-left`),
        pageSize: () => cy.get(`button.ml-2`),
        pageSize5: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(1)`),
        pageSize10: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(2)`),
        pageSize20: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(3)`),
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    clickUpdate(){
        this.elements.update().click()
    }

    clickRightArrow(){
        this.elements.rightArrow().click({ multiple: true })
    }

    clickLeftArrow(){
        this.elements.leftArrow().click({ multiple: true })
    }

    checkHeadline(){
        this.elements.headline().should('contain.text',textHeadline)
    }

    checkStatusElement(elementNumber,text){
        this.elements.listElementAttribute(elementNumber,2).should('contain.text',text)
    }

    taskIsCorrect(elementNumber, text){
        this.elements.listElementAttribute(elementNumber,1).should('contain.text',text)
    }

    getFoundTasks(){
        return this.elements.numberOfTasks().invoke('text').then((txt) => {
            return parseInt((txt.split(" "))[0]);
        })
    }

    getElement(elementNumber){
        return this.elements.listElement(elementNumber)
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

    clickListElement(key){
        cy.get('[data-element-key="' + key + '"]')
            .click()
    }

    findProcess(text){
        this.elements.searchBox().type(text)
    }

    checkPageSize(pageSize, numberOfTasks){
        //separator line is an Element, too
        cy.get('div.v-list:nth-child(4)').children().its('length').should('eq', pageSize)
    }
}

module.exports = new AktuelleVorgaenge();