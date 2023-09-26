import Page from './page'

class WorkflowUserTask extends Page{
    elements = {
        elem: (elementNumber, column) => cy.get(`.v-data-table__wrapper > table:nth-child(1) > tbody:nth-child(2) > tr:nth-child(${elementNumber}) > td:nth-child(${column})`),
    }

    taskIsCorrect(elementNumber,text){
        this.elements.elem(elementNumber,2).should('contain.text',text)
    }

    getStatus(elementNumber){
        return this.elements.elem(elementNumber, 3)
    }


}

module.exports = new WorkflowUserTask();