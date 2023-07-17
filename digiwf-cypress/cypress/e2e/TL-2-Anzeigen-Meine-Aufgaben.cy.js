import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import exampleUserTask from "../pages/exampleUserTask"

const numberOfTasks = 21

before(() => {
    cy.login()
})


describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {
        let pageSize = 20;

        //Step 1
        meineAufgaben.openVorgangStarten(0,20);
        vorgangStarten.findProcess("Example Usertask",0,20);
        vorgangStarten.clickListElement("Usertask-Example");
        exampleUserTask.setNumberOfTasks(numberOfTasks);
        exampleUserTask.setUserName(Cypress.env('fullUsername'));
        exampleUserTask.clickAbschliessen();
        vorgangStarten.openMeineAufgaben(0,pageSize);
        cy.wait(3000)
        meineAufgaben.clickActualize();
        cy.wait(3000)
        meineAufgaben.getElement(1).should('contain.text', 'User Task')
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
                exampleUserTask.clickAbschliessen();
                cy.wait(3000);
                meineAufgaben.clickActualize();
            }
    })
})
