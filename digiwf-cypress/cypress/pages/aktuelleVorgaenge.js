import Page from './page'

const textHeadline = "Aktuelle Vorgänge";

class AktuelleVorgaenge extends Page{

    elements = {
        listElement: (elementNumber) => cy.get(`.v-data-iterator > div:nth-child(1) > div:nth-child(${elementNumber})`),
        listElementAttribute: (elementNumber, column) => cy.get(`.v-data-iterator > div:nth-child(1) > div:nth-child(${elementNumber}) > a:nth-child(1) > div:nth-child(${column})`),
        headline: () => cy.get('.layout > div:nth-child(1) > div:nth-child(1) > h1:nth-child(1)'),
        update: () => cy.get('.v-size--large')
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    clickUpdate(){
        this.elements.update().click()
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
}

module.exports = new AktuelleVorgaenge();