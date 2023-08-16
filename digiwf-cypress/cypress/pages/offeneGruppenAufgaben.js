import Page from './page'

class offeneGruppenAufgaben extends Page{
    headline = "Offene Gruppenaufgaben"

    elements = {
        listElement: (elementNumber) => cy.get(`a.d-flex:nth-child(${elementNumber})`),
        headline: ()  => cy.get(`.layout > div:nth-child(1) > div:nth-child(1) > h1:nth-child(1)`)
    }

    clickElement(elementNumber){
        this.elements.listElement(elementNumber).click()
    }

    checkHeadline(){
        this.elements.headline().should('be.visible');
        this.elements.headline().should('contain.text',this.headline)
    }

}

module.exports = new offeneGruppenAufgaben;
