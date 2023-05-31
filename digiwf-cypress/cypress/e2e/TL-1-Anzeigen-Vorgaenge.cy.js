import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"

beforeEach(() => {
    cy.loginUser();
})

describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {

        //Test auf korrekten Startzustand
        meineAufgaben.checkIfTasksAreEmpty();


        //Step 1
        meineAufgaben.openVorgangStarten();
        vorgangStarten.checkPageNumber(1);
        //TODO Zahl optimieren
        vorgangStarten.getFoundProcesses().should('be.gt',10);
        //TODO Anzahl der angezeigten Listenelemente pruefen
        //vorgangStarten.getListSize().should('have.value', 10);

        //Step2
        vorgangStarten.clickRightArrow();
        vorgangStarten.checkPageNumber(2);
        //TODO andere Vorgaenge angezeigt

        //Step3
        vorgangStarten.changePageSize(20);
        vorgangStarten.checkPageSize(20)
        //TODO andere Anzahl an Vorgaengen pruefen

        //Step4
        vorgangStarten.changePageSize(10);
        vorgangStarten.goToLastPage();
        vorgangStarten.checkPageSize(10)
        //TODO pruefen, dass es die letzte seite ist
        //TODO andere Anzahl an Vorgaengen pruefen

        //Step5


    })
})