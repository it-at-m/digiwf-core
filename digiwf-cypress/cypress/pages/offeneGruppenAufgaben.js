import Page from './page'

class offeneGruppenAufgaben extends Page{
    headline = "Offene Gruppenaufgaben"

    elements = {
        listElement: (elementNumber) => cy.get(`a.d-flex:nth-child(${elementNumber})`),
        headline: ()  => cy.get(`.layout > div:nth-child(1) > div:nth-child(1) > h1:nth-child(1)`),
        update: () => cy.get(".v-size--large"),
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    checkHeadline(){
        this.elements.headline().should('be.visible');
        this.elements.headline().should('contain.text',this.headline)
    }

    clickAktualisieren(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/*',
        }).as('dataGetFilter')
        this.elements.update().click()
        cy.wait('@dataGetFilter').its('response.statusCode').should('equal', 200)
    }

}

module.exports = new offeneGruppenAufgaben;
