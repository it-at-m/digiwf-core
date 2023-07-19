import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import exampleUserTask from "../pages/exampleUserTask"
import userTask from "../pages/userTask"

const numberOfTasks =21

before(() => {
    cy.login()
})


describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {
        let pageSize = 20;

        //Step 0
        cy.log('Step 0');
        meineAufgaben.openVorgangStarten(0,20);
        vorgangStarten.findProcess("Example Usertask",0,20);
        vorgangStarten.clickListElement("Usertask-Example");
        exampleUserTask.setNumberOfTasks(numberOfTasks);
        exampleUserTask.setUserName(Cypress.env('fullUsername'));
        exampleUserTask.clickAbschliessen();

        //Step 1
        cy.log('Step 1');
        vorgangStarten.openMeineAufgaben(0,pageSize);
        reloadPageUntilTasksVisible();
        meineAufgaben.getFoundTasks().should('eq',numberOfTasks);
        meineAufgaben.getElement(1).should('contain.text', 'User Task');

        //Step 2-5
        cy.log('Step 2-5');

        pageSize = 5;
        meineAufgaben.changePageSize(pageSize);
        meineAufgaben.checkPageSize(pageSize,numberOfTasks)

        pageSize = 10;
        meineAufgaben.changePageSize(pageSize);
        meineAufgaben.checkPageSize(pageSize,numberOfTasks)

        pageSize = 20;
        meineAufgaben.changePageSize(pageSize);
        meineAufgaben.checkPageSize(pageSize,numberOfTasks)

        //Step 6-7
        cy.log('Step 6-7');
        meineAufgaben.clickElement(1);
        userTask.checkHeadline("User Task");
        userTask.clickAbschliessen();

            for (let i=1; i< numberOfTasks; i++){
                meineAufgaben.clickElement(1);
                userTask.clickAbschliessen();
                //necessary to wait for the task to be deleted
                cy.wait(3000);
                meineAufgaben.clickActualize();
            }
    })


    //ensures all of the tasks are loaded
    function reloadPageUntilTasksVisible(maxAttempts=10, attempts=0) {
        if (attempts > maxAttempts) {
            throw new Error("Timed out waiting")
        }
        meineAufgaben.getFoundTasks().then(numTasks => {
            if (numTasks != numberOfTasks) {
                cy.wait(1)
                cy.log('iteration')
                meineAufgaben.clickActualize();
                reloadPageUntilTasksVisible(maxAttempts, attempts+1)
            }
        })
    }
})
