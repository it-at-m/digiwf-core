import Page from './page'

class AufgabeWasIstDigiWF extends Page{
    elements = {
        headline: () => cy.get('div.flex:nth-child(1) > h1:nth-child(2)'),
        button: () => cy.get('div.buttonGroup:nth-child(1)')
    }

    checkHeadline(text){
        this.elements.headline().should('contain.text',text)
    }

    finishTask(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-backend-service/rest/task?page=0&size=20&followUp=false',
        }).as('data');
        this.elements.button().click()
        cy.wait('@data').its('response.statusCode').should('equal', 200);
    }
}

module.exports = new AufgabeWasIstDigiWF();
