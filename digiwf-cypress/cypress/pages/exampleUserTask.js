import Page from './page'

class ExampleUserTask extends Page{
    elements = {
        numberOfParallelTasks: () => cy.get("#allOf-0-allOf-0-FORMFIELD_NumberOfTasks"),
        userForTask: () => cy.get(`.v-select__selections`),
        abschliessenButton: () => cy.get(`button.mt-5`)
    }
    setNumberOfTasks(number){
        this.elements.numberOfParallelTasks().clear()
        this.elements.numberOfParallelTasks().type(number)
    }

    setUserName(user){
        this.elements.userForTask().type(user)
        this.elements.userForTask().type('{enter}')
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