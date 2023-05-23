import Page from './page'

class AktuelleVorgaenge extends Page{

    elements = {
        listElement: (elementNumber) => cy.get(`.v-data-iterator > div:nth-child(1) > div:nth-child(${elementNumber})`),
        listElementAttribute: (elementNumber, column) => cy.get(`.v-data-iterator > div:nth-child(1) > div:nth-child(${elementNumber}) > a:nth-child(1) > div:nth-child(${column})`),
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    checkStatusElement(elementNumber,text){
        this.elements.listElementAttribute(elementNumber,2).should('contain.text',text)
    }

    taskIsCorrect(elementNumber, text){
        this.elements.listElementAttribute(elementNumber,1).should('contain.text',text)
    }
}

module.exports = new AktuelleVorgaenge();