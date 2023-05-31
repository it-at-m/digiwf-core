import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"

beforeEach(() => {
    cy.loginUser();
})

describe('Vorgaenge Anzeigen', () => {
    it('passes', async () => {

        //Test auf korrekten Startzustand
        meineAufgaben.checkIfTasksAreEmpty();


        //Step 1
        meineAufgaben.openVorgangStarten();
        vorgangStarten.checkPageNumber(1);
        //TODO Zahl optimieren
        vorgangStarten.getFoundProcesses().should('be.gt',10);
        //TODO Anzahl der angezeigten Listenelemente pruefen


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
        //TODO pruefen, dass es die letzte Seite ist
        //TODO andere Anzahl an Vorgaengen pruefen

        //Step5
        let comparePageNumber = await vorgangStarten.getLastPageNumber();
        vorgangStarten.changePageSize(20);
        vorgangStarten.checkPageSize(20);
        vorgangStarten.getLastPageNumber().should('be.closeTo', comparePageNumber/2, 1);

        //Step6
        vorgangStarten.findProcess("all");
        //TODO alle Prozesse starten mit all

        //Step7
        vorgangStarten.clickListElement(1);
        vorgangStarten.checkHeadline("All")

        //vorgangStarten.getLastPageNumber()
    })
})