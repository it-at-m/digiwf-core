import Page from './page'

class offeneGruppenAufgaben extends Page{
    headline = "Offene Gruppenaufgaben"

    elements = {
        listElement: (elementNumber) => cy.get(`a.d-flex:nth-child(${elementNumber})`),
        headline: ()  => cy.get(`.layout > div:nth-child(1) > div:nth-child(1) > h1:nth-child(1)`),
        update: () => cy.get(".v-size--large"),
        numberOfTasks: () => cy.get(`span.mr-1:nth-child(5)`),
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    checkHeadline(){
        this.elements.headline().should('be.visible');
        this.elements.headline().should('contain.text',this.headline)
    }

    getFoundTasks(){
        return this.elements.numberOfTasks().invoke('text').then((txt) => {
            return parseInt((txt.split(" "))[0]);
        })
    }

    clickAktualisieren(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/group/*',
        }).as('dataGetGroupTasksForGroup')
        this.elements.update().click()
        cy.wait('@dataGetGroupTasksForGroup').its('response.statusCode').should('equal', 200)
    }

}

module.exports = new offeneGruppenAufgaben;
