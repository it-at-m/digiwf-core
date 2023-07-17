import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import exampleUserTask from "../pages/exampleUserTask"

const numberOfTasks = 6

before(() => {
    cy.login()
    cy.wait(3000)
    //cy.getCookie('XSRF-TOKEN')
    cy.wait(3000)
})



//data - cy = "process-instance-item-c9115dbc-ca47-43ab-b087-13dbd0486b05"

//data - cy = "definition-name"

//data - cy = "start-time"


describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {
        //Cypress.session.getCurrentSessionData()
        //Cypress.Cookies.debug(true, { verbose: true })
        //cy.getCookie('XSRF-TOKEN')

        //Step 1
        meineAufgaben.openVorgangStarten();
        cy.wait(3000);
        vorgangStarten.findProcess("Example Usertask",0,20);
        cy.wait(3000);
        vorgangStarten.clickListElement("Usertask-Example");
        exampleUserTask.setNumberOfTasks(numberOfTasks);
        exampleUserTask.setUserName(Cypress.env('fullUsername'));
        cy.wait(3000)
        exampleUserTask.clickAbschliessen();
        vorgangStarten.openMeineAufgaben();
        cy.wait(3000)
        meineAufgaben.clickActualize();
        meineAufgaben.getElement(1).should('contain.text', 'User Task')
        cy.wait(3000)
        meineAufgaben.getFoundTasks().should('eq',numberOfTasks)
        meineAufgaben.changePageSize(5);
        meineAufgaben.clickRightArrow();
        cy.wait(3000)
        meineAufgaben.clickLeftArrow();


            for (let i=1; i<= numberOfTasks; i++){
                meineAufgaben.clickElement(1);
                cy.wait(3000);
                exampleUserTask.clickAbschliessen();
                cy.wait(3000);
                meineAufgaben.clickActualize();
            }
    })
})
