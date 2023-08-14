import Page from './page'

class ExampleGroupTask extends Page{
    elements = {
        numberOfParallelTasks: () => cy.get("#allOf-0-allOf-0-FORMFIELD_NumberOfTasks"),
        groupForTask: () => cy.get(`div.col-sm-12:nth-child(3) > div:nth-child(1)`),
        abschliessenButton: () => cy.get(`button.mt-5`)
    }

    setGroup(group){
        this.elements.groupForTask().type(group)
        this.elements.groupForTask().type('{enter}')
    }

    setNumberOfTasks(number){
        this.elements.numberOfParallelTasks().clear()
        this.elements.numberOfParallelTasks().type(number)
        this.elements.numberOfParallelTasks().type('{enter}')
    }

    clickNumberOfParallelTasks(){
        this.elements.numberOfParallelTasks().click()
    }

    clickAbschliessen(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-backend-service/rest/filter',
        }).as('dataGetFilter')
        this.elements.abschliessenButton().click()
        cy.wait('@dataGetFilter',{ timeout: 50000 }).its('response.statusCode').should('equal', 200)
    }
}


module.exports = new ExampleGroupTask();