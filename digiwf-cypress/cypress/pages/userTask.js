import Page from './page'

class ExampleUserTask extends Page{
    elements = {
        abschliessenButton: () => cy.get(`button.mt-5`,{timeout:3000})
    }
    setNumberOfTasks(number){
        this.elements.numberOfParallelTasks().clear()
        this.elements.numberOfParallelTasks().type(number)
    }

    clickAbschliessen(){
        this.elements.abschliessenButton().should('be.visible');
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/user',
        }).as('dataGetTasks')
        this.elements.abschliessenButton().click()
        cy.wait('@dataGetTasks').its('response.statusCode').should('equal', 200)
    }
}


module.exports = new ExampleUserTask();