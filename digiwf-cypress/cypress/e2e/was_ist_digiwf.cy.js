describe('template spec', () => {
    it('passes', () => {
        const firstListElement = '.v-data-iterator > div:nth-child(1) > div:nth-child(1)';
        const firstListElementMeineAufgaben = '.taskTitel > span:nth-child(1)'
        const checkboxWasIstDigiWF = 'div.col-12:nth-child(6) > div:nth-child(1)';
        const navBar = ".v-app-bar__nav-icon > span:nth-child(1)";
        const navBarAktuelleVorgaenge = 'a.v-list-item:nth-child(3) > div:nth-child(1)';
        const navBarMeineAufgaben = 'a.v-list-item:nth-child(1) > div:nth-child(1)';
        const headlineAufgaben = 'div.flex:nth-child(1) > h1:nth-child(2)';

        cy.loginUser();

        //Test auf korrekten Startzustand
        cy.log("Fails if there are any open tasks")
        cy.get('.v-list-item--active > div:nth-child(1) > div:nth-child(1) > span:nth-child(2)').should('not.exist');

        // TODO: Texte bezueglich der Leerzeichen ueberpruefen
        //Vorgang starten
        cy.openVorgangStarten();
        cy.get('#suchfeld').type("DigiWF erleben");
        cy.get(firstListElement).click();
        cy.get(checkboxWasIstDigiWF).click();
        cy.clickSingleButton();
        cy.get(navBar).click();


        //Aktuelle Vorgeange pruefen
        cy.get(navBarAktuelleVorgaenge).click();
        cy.get(firstListElement + " > a:nth-child(1) > div:nth-child(2)").should('have.text'," Was ist DigiWF");
        cy.get(firstListElement).click();
        cy.get(".d-flex").should('have.text'," offen ");


        //Meine Aufgaben pruefen
        //Task1
        cy.get(navBar).click();
        cy.get(navBarMeineAufgaben).click();
        cy.get(firstListElementMeineAufgaben).should('have.text'," Was ist DigiWF? ");
        cy.get(firstListElementMeineAufgaben).click();


        cy.get(headlineAufgaben).should('have.text',"Was ist DigiWF?");
        cy.clickSingleButton();

        //Task2
        cy.get(firstListElementMeineAufgaben).should('have.text'," Auf Wiedersehen bei DigiWF! ");
        cy.get(firstListElementMeineAufgaben).click();
        cy.get(headlineAufgaben).should('have.text',"Auf Wiedersehen bei DigiWF!");
        cy.clickSingleButton();

        //Aufgaben abgearbeitet
        cy.get(".v-data-iterator > div:nth-child(1)").should('have.text',"Keine Aufgaben gefunden");

        //aktuelle Vorgaenge beendet
        cy.get(navBar).should("be.visible");
        cy.get(navBar).click();
        cy.get(navBarAktuelleVorgaenge).click({force:true});
        // TODO: reload kann entfernt werden, wenn Daten automatisch nachgeladen werden
        cy.reload(true);
        cy.get(firstListElement +' > a:nth-child(1) > div:nth-child(2)').should('have.text',' Beendet');
    })
})