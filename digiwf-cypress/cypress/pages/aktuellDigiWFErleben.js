import Page from './page'

class AktuellDigiWFErleben extends Page{
    elements = {
        elem: (elementNumber, column) => cy.get(`.v-data-table__wrapper > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(${elementNumber}) > td:nth-child(${column})`),
    }

    taskIsCorrect(elementNumber,text){
        this.elements.elem(elementNumber,2).should('contain.text',text)
    }

    isOpen(elementNumber){
        this.elements.elem(elementNumber, 3).should('contain.text','offen')
    }


}

module.exports = new AktuellDigiWFErleben();