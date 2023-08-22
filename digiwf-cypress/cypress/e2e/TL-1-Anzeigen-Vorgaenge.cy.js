import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import startDigiWFErleben from "../pages/startDigiWFErleben"
import {EXAMPLE_USER_TASK_NAME} from "../constants/dataElementKeys";
import {EXAMPLE_USER_TASK_KEY} from "../constants/dataElementKeys";

beforeEach(() => {
    cy.loginUser();
})

describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {
        Cypress.on('uncaught:exception', (err, runnable) => {
            return false;
        });
        //Test auf korrekten Startzustand
        meineAufgaben.checkIfTasksAreEmpty();

        //Step 1
        cy.log("Step 1");
        meineAufgaben.openVorgangStarten();
        vorgangStarten.changePageSize(10);
        vorgangStarten.checkPageNumber(1);

        //Anzahl der Listenelem pruefen
        vorgangStarten.checkPageSize(10)

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
        vorgangStarten.getListElementByNumber(1).invoke('text').then((elemOld) => {
            vorgangStarten.clickRightArrow();
            cy.wait(3000)
            vorgangStarten.checkPageNumber(2);
            vorgangStarten.getListElementByNumber(1).invoke('text').then((elemNew) => {
                expect(elemNew).not.eq(elemOld)
            })
        })

        //Step3
        cy.log("Step 3");
        vorgangStarten.checkPageSize(10)
        vorgangStarten.changePageSize(20);
        //andere Anzahl an Vorgaengen pruefen
        vorgangStarten.checkPageSize(20)

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
            //cy.wait(3000)
            //ToDO: Wait till page SIze changes
            vorgangStarten.getLastPageNumber().should('be.closeTo', lastNumber/2, 1);
        });


        //Step6
        cy.log("Step 6");
        vorgangStarten.getFoundProcesses().then((numProcessesOld) => {
            vorgangStarten.findProcess(EXAMPLE_USER_TASK_NAME)
                vorgangStarten.getFoundProcesses().then((numProcesses) => {
                    expect(numProcesses).lt(numProcessesOld);
                })
                vorgangStarten.getListElement(EXAMPLE_USER_TASK_KEY).invoke('text').then((txt) => {
                    expect(txt).to.contain(EXAMPLE_USER_TASK_NAME)
                })
        })


        //Step7
        cy.log("Step 7");
        vorgangStarten.clickListElement(EXAMPLE_USER_TASK_KEY);
        startDigiWFErleben.checkHeadline(EXAMPLE_USER_TASK_NAME)

    })
})