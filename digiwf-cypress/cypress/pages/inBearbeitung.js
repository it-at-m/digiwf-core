import Page from './page'

class InBearbeitung extends Page{
    headline = "Gruppenaufgaben in Bearbeitung"

    elements = {
        listElement: (elementNumber) => cy.get(`a.d-flex:nth-child(${elementNumber})`),
        headline: ()  => cy.get(`.layout > div:nth-child(1) > div:nth-child(1) > h1:nth-child(1)`),
        update: () => cy.get(".v-size--large"),
        numberOfTasks: () => cy.get(`span.mr-1:nth-child(5)`),
        rightArrow: () => cy.get(`.mdi-chevron-right`),
        leftArrow: () => cy.get(`.mdi-chevron-left`),
        pageSize: () => cy.get(`button.ml-2`),
        pageSize5: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(1)`),
        pageSize10: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(2)`),
        pageSize20: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(3)`),
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    clickRightArrow(){
        this.elements.rightArrow().click({ multiple: true })
    }

    clickLeftArrow(){
        this.elements.leftArrow().click({ multiple: true })
    }

    checkHeadline(){
        this.elements.headline().should('be.visible');
        this.elements.headline().should('contain.text',this.headline)
    }

    changePageSize(number){
        this.elements.pageSize().click()
        if(number == 5){
            this.elements.pageSize5().click()
        }
        else if(number == 20){
            this.elements.pageSize20().click()
        }
        else{
            this.elements.pageSize10().click()
        }
    }

    checkPageSize(pageSize, numberOfTasks){
        //separator line is an Element, too
        cy.get('.v-data-iterator').children().its('length').should('eq', pageSize*2)
        for (let i=1; i<= numberOfTasks/pageSize; i++){
            this.clickRightArrow();
        }
        cy.get('.v-data-iterator').children().its('length').should('eq', 1*2)
        for (let i=1; i<= numberOfTasks/pageSize; i++){
            this.clickLeftArrow();
        }
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

module.exports = new InBearbeitung();