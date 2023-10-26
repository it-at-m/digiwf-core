import Page from './page'

class StartDigiWFErleben extends Page{
    elements = {
        headline: () => cy.get('div.flex:nth-child(1) > h1:nth-child(1)'),
        checkBoxAllesSehen: () => cy.get('div.type-checkbox:nth-child(1)'),
        singleTopics: (topic) => cy.get(`div.col-12:nth-child(${topic})`),
        buttonLosGehts: () => cy.get('div.buttonGroup:nth-child(1)'),
    }

    checkHeadline(text){
        this.elements.headline().should('contain.text',text);
    }

    tickAllesSehen(){
        this.elements.checkBoxAllesSehen().click()
    }

    tickWasIstDigiWF(){
        this.elements.singleTopics(6).click()
    }

    tickBasics(){
        this.elements.singleTopics(8).click()
    }

    tickAufgabenBearbeiten(){
        this.elements.singleTopics(10).click()
    }

    tickWeitereFunktionen(){
        this.elements.singleTopics(12).click()
    }

    clickLosGehts(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-backend-service/rest/filter',
        }).as('dataGetFilter');
        this.elements.buttonLosGehts().click();
        cy.wait('@dataGetFilter').its('response.statusCode').should('equal', 200);
    }

}

module.exports = new StartDigiWFErleben();