import Page from './page'

class GroupUserTask extends Page{
    headline = "Group User Task"
    elements = {
        bearbeiten: () => cy.get(`.assignButton`),
        headline: () => cy.get('div.flex:nth-child(1) > h1:nth-child(2)'),
        checkBox: () => cy.get('.v-input--selection-controls__input'),
        abschliessenButton: () => cy.get('button.mt-5')
    }

    checkHeadline(){
        this.elements.headline().should('contain.text',this.headline)
    }

    clickBearbeiten(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/id/**',
        }).as('dataGetGroup')
        this.elements.bearbeiten().click()
        cy.wait('@dataGetGroup',{ timeout: 50000 }).its('response.statusCode').should('equal', 200)
    }

    tickCheckbox(){
        this.elements.checkBox().click()
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


module.exports = new GroupUserTask();