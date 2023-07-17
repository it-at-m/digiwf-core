import Page from './page'

class ExampleUserTask extends Page{
    elements = {
        abschliessenButton: () => cy.get(`button.mt-5`)
    }
    setNumberOfTasks(number){
        this.elements.numberOfParallelTasks().clear()
        this.elements.numberOfParallelTasks().type(number)
    }

    clickAbschliessen(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-backend-service/rest/filter',
        }).as('dataGetTasks')
        this.elements.abschliessenButton().click()
        cy.wait('@dataGetTasks').its('response.statusCode').should('equal', 200)
    }
}


module.exports = new ExampleUserTask();