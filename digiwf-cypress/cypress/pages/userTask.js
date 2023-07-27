import Page from './page'

class UserTask extends Page{
    elements = {
        abschliessenButton: () => cy.get(`button.mt-5`,{timeout:3000}),
        headline: () => cy.get('div.flex:nth-child(1) > h1:nth-child(2)')
    }
    setNumberOfTasks(number){
        this.elements.numberOfParallelTasks().clear()
        this.elements.numberOfParallelTasks().type(number)
    }

    checkHeadline(text){
        this.elements.headline().should('contain.text',text)
    }

    clickAbschliessen(){
        this.elements.abschliessenButton().should('be.visible');
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/*',
        }).as('dataGetTasks')
        this.elements.abschliessenButton().click()
        cy.wait('@dataGetTasks').its('response.statusCode').should('equal', 200)
    }
}


module.exports = new UserTask();