import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import startDigiWFErleben from "../pages/startDigiWFErleben"

beforeEach(() => {
    cy.loginUser();
})

describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {

        //Test auf korrekten Startzustand
        meineAufgaben.checkIfTasksAreEmpty();

        //Step 1
        cy.log("Step 1");
        meineAufgaben.openVorgangStarten();
        vorgangStarten.checkPageNumber(1);
        //Anzahl der Listenelem pruefen
        for (let i = 1; i < 11; i++) {
            expect(vorgangStarten.getListElement(i)).to.exist
        }
        vorgangStarten.getListElement(11).should('not.exist');
        //Plausibilitaetscheck Zahlen
        vorgangStarten.getFoundProcesses().then((numProcesses) => {
            vorgangStarten.getLastPageNumber().then((numPages) => {
                vorgangStarten.getPageSize().then((numListElements) => {
                    expect(numPages).to.be.eq(Math.ceil(numProcesses/numListElements))
                })
            })
        })

        //Step2
        cy.log("Step 2");
        vorgangStarten.getListElement(1).invoke('text').then((elemOld) => {
            vorgangStarten.clickRightArrow();
            vorgangStarten.checkPageNumber(2);
            vorgangStarten.getListElement(1).invoke('text').then((elemNew) => {
                expect(elemNew).not.eq(elemOld)
            })
        })

        //Step3
        cy.log("Step 3");
        expect(vorgangStarten.getListElement(10)).to.exist;
        vorgangStarten.getListElement(11).should('not.exist');
        vorgangStarten.changePageSize(20);
        //andere Anzahl an Vorgaengen pruefen
        expect(vorgangStarten.getListElement(20)).to.exist;
        vorgangStarten.getListElement(21).should('not.exist');

        //Step4
        cy.log("Step 4");
        vorgangStarten.changePageSize(10);
        vorgangStarten.goToLastPage();
        vorgangStarten.getLastPageNumber().then((maxPageNumber) =>{
            vorgangStarten.checkPageNumber(maxPageNumber);
        })

        //Step5
        cy.log("Step 5");
        vorgangStarten.getLastPageNumber().then((lastNumber)=>{
            vorgangStarten.changePageSize(20);
            vorgangStarten.getLastPageNumber().should('be.closeTo', lastNumber/2, 1);
        });


        //Step6
        cy.log("Step 6");
        let search_request = 'DigiWF er'
        vorgangStarten.getFoundProcesses().then((numProcessesOld) => {
            vorgangStarten.findProcess(search_request)
                vorgangStarten.getFoundProcesses().then((numProcesses) => {
                    expect(numProcesses).lt(numProcessesOld);
                })
                vorgangStarten.getListElement(1).invoke('text').then((txt) => {
                    expect(txt).to.contain(search_request)
                })
        })


        //Step7
        cy.log("Step 7");
        vorgangStarten.clickListElement(1);
        startDigiWFErleben.checkHeadline(search_request)

    })
})