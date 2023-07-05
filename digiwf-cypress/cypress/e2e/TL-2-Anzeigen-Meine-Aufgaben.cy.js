import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import exampleUserTask from "../pages/exampleUserTask"

beforeEach(() => {
    cy.loginUser();
})



//data - cy = "process-instance-item-c9115dbc-ca47-43ab-b087-13dbd0486b05"

//data - cy = "definition-name"

//data - cy = "start-time"


describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {
        const numberOfTasks = 6

        //Step 1
        meineAufgaben.openVorgangStarten();
        vorgangStarten.findProcess("Example Usertask",0,20);
        vorgangStarten.clickListElement("Usertask-Example");
        exampleUserTask.setNumberOfTasks(numberOfTasks);
        exampleUserTask.setUserName(Cypress.env('fullUsername'));
        exampleUserTask.clickAbschliessen();
        vorgangStarten.openMeineAufgaben();
        meineAufgaben.clickActualize();
        meineAufgaben.getElement(1).should('contain.text', 'User Task')
        meineAufgaben.getFoundTasks().should('eq',numberOfTasks)
    })
})