import Page from './page'

class GroupUserTask extends Page{
    headline = "Group User Task"
    elements = {
        bearbeiten: () => cy.get(`button.v-btn--is-elevated:nth-child(1)`),
        headline: () => cy.get('.layout > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > h1:nth-child(2)'),
        taskHeadline: () => cy.get('div.flex:nth-child(1) > h1:nth-child(2)'),
        checkBox: () => cy.get('.v-input--selection-controls__input'),
        abschliessenButton: () => cy.get('button.mt-5')
    }

    checkHeadline(){
        this.elements.headline().should('be.visible')
        this.elements.headline().should('contain.text',this.headline)
    }

    checkTaskHeadline(){
        this.elements.taskHeadline().should('be.visible')
        this.elements.taskHeadline().should('contain.text',this.headline)
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
        this.elements.checkBox().should('be.visible')
        cy.wait(2000);
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