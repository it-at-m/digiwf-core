import Page from './page'

class MeineAufgaben extends Page{
    elements = {
        elementBox: () => cy.get(".v-data-iterator > div:nth-child(1)"),
        listElement: (elementNumber) => cy.get(`.taskTitel > span:nth-child(${elementNumber})`),
    }

    elementIsCorrect(elementNumber,text){
        this.elements.listElement(elementNumber).should('contain.text',text)
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    tasksEmpty(text){
        this.elements.elementBox().should('contain.text',text);
    }
}

module.exports = new MeineAufgaben();