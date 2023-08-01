import Page from './page'

class MeineAufgaben extends Page{
    elements = {
        elementBox: () => cy.get(".v-data-iterator > div:nth-child(1)"),
        update: () => cy.get(".v-size--large"),
        listElement: (elementNumber) => cy.get(`a.d-flex:nth-child(${elementNumber})`),
        numberOfTasks: () => cy.get(`span.mr-1:nth-child(5)`),
        rightArrow: () => cy.get(`.mdi-chevron-right`),
        leftArrow: () => cy.get(`.mdi-chevron-left`),
        pageSize: () => cy.get(`button.ml-2`),
        pageSize5: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(1)`),
        pageSize10: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(2)`),
        pageSize20: () => cy.get(`#app > div.v-menu__content.theme--light.menuable__content__active > div > div:nth-child(3)`),
    }

    getFoundTasks(){
        return this.elements.numberOfTasks().invoke('text').then((txt) => {
            return parseInt((txt.split(" "))[0]);
        })
    }

    clickAktualisieren(){
       cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/*',
        }).as('dataGetFilter')
        this.elements.update().click()
        cy.wait('@dataGetFilter').its('response.statusCode').should('equal', 200)
    }

    clickRightArrow(){
        this.elements.rightArrow().click({ multiple: true })
    }

    clickLeftArrow(){
        this.elements.leftArrow().click({ multiple: true })
    }


    elementIsCorrect(elementNumber,text){
        this.elements.listElement(elementNumber).should('contain.text',text)
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    getElement(elementNumber){
        return this.elements.listElement(elementNumber)
    }

    tasksEmpty(text){
        this.elements.elementBox().should('contain.text',text);
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
}

module.exports = new MeineAufgaben();