import Page from './page'

class VorgangStarten extends Page{
    elements = {
        searchBox: () => cy.get('#suchfeld'),
        firstListElement: () => cy.get('.v-data-iterator > div:nth-child(1) > div:nth-child(1)'),
    }

    findProcess(text){
        this.elements.searchBox().type(text)
    }

    clickFirstElement(){
        this.elements.firstListElement().click()
    }
}

module.exports = new VorgangStarten();

