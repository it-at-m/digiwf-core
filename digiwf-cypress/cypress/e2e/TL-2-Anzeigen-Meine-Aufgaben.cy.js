import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import exampleUserTask from "../pages/exampleUserTask"

const numberOfTasks = 21

before(() => {
    cy.login()
    //cy.getCookie('XSRF-TOKEN')
})



//data - cy = "process-instance-item-c9115dbc-ca47-43ab-b087-13dbd0486b05"

//data - cy = "definition-name"

//data - cy = "start-time"


describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {
        let pageSize = 11;
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

        pageSize = 5;
        meineAufgaben.changePageSize(pageSize);
        meineAufgaben.checkPageSize(pageSize,numberOfTasks)

        pageSize = 10;
        meineAufgaben.changePageSize(pageSize);
        meineAufgaben.checkPageSize(pageSize,numberOfTasks)

        pageSize = 20;
        meineAufgaben.changePageSize(pageSize);
        meineAufgaben.checkPageSize(pageSize,numberOfTasks)

            for (let i=1; i<= numberOfTasks; i++){
                meineAufgaben.clickElement(1);
                cy.wait(3000);
                exampleUserTask.clickAbschliessen();
                cy.wait(3000);
                meineAufgaben.clickActualize();
            }
    })
})
