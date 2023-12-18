import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import startDigiWFErleben from "../pages/startDigiWFErleben"
import page from "../pages/page";
import {EXAMPLE_GROUP_TASK_KEY, EXAMPLE_GROUP_TASK_NAME} from "../constants/dataElementKeys";

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
        let pageSize = 10
        vorgangStarten.changePageSize(pageSize);
        vorgangStarten.getList().children().its('length').should('eq',pageSize);
        //Plausibilitaetscheck Zahlen
        vorgangStarten.getFoundProcesses().then((numProcesses) => {
            vorgangStarten.getLastPageNumber().then((numPages) => {
                vorgangStarten.getPageSize().then((numListElements) => {
                    expect(numPages).to.be.eq(Math.ceil(numProcesses/numListElements))
                })
            })
        })

        //Step2
        /*cy.log("Step 2");
        vorgangStarten.getListElement(1).invoke('text').then((elemOld) => {
            vorgangStarten.clickRightArrow();
            vorgangStarten.checkPageNumber(2);
            vorgangStarten.getListElement(1).invoke('text').then((elemNew) => {
                expect(elemNew).not.eq(elemOld)
            })
        })
         */

        //Step3
        cy.log("Step 3");
        pageSize = 20
        vorgangStarten.changePageSize(pageSize);
        //andere Anzahl an Vorgaengen pruefen
        vorgangStarten.getList().children().its('length').should('eq',pageSize);

        //Step4
        cy.log("Step 4");
        pageSize = 10
        vorgangStarten.changePageSize(pageSize);
        cy.wait(3000)
        vorgangStarten.goToLastPage();
        vorgangStarten.getLastPageNumber().then((maxPageNumber) =>{
            vorgangStarten.checkPageNumber(maxPageNumber);
        })

        //Step5
        cy.log("Step 5");
        /*vorgangStarten.getLastPageNumber().then((lastNumber)=>{
            cy.log(lastNumber)
            vorgangStarten.changePageSize(20);
            cy.wait(100000)
            vorgangStarten.getLastPageNumber().should('be.closeTo', lastNumber/2, 1);
        });

         */

        //Step6
        cy.log("Step 6");
        vorgangStarten.getFoundProcesses().then((numProcessesOld) => {
            vorgangStarten.findProcess(EXAMPLE_GROUP_TASK_NAME)
            cy.wait(3000)
                vorgangStarten.getFoundProcesses().then((numProcesses) => {
                    expect(numProcesses).lt(numProcessesOld);
                })
                vorgangStarten.getListElement(EXAMPLE_GROUP_TASK_KEY).invoke('text').then((txt) => {
                    expect(txt).to.contain(EXAMPLE_GROUP_TASK_NAME)
                })
        })


        //Step7
        cy.log("Step 7");
        vorgangStarten.clickListElement(EXAMPLE_GROUP_TASK_KEY);
        cy.wait(3000)
        startDigiWFErleben.checkHeadline(EXAMPLE_GROUP_TASK_NAME)

    })
})