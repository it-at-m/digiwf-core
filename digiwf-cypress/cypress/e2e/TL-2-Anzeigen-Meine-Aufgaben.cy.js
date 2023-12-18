import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import exampleUserTask from "../pages/exampleUserTask"
import userTask from "../pages/userTask"
import * as dataElementKeys from "../constants/dataElementKeys"
import * as environmentVariables from "../constants/environmentVariables"

const numberOfTasks = 21

before(() => {
    cy.login()
})


describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {
        let pageSize = 20;

        // Step 0: Create User processes
        cy.log('Step 0');
        meineAufgaben.openVorgangStarten();
        vorgangStarten.findProcess(dataElementKeys.EXAMPLE_USER_TASK_NAME);
        vorgangStarten.clickListElement(dataElementKeys.EXAMPLE_USER_TASK_KEY);
        exampleUserTask.setNumberOfTasks(numberOfTasks);
        exampleUserTask.setUserName(environmentVariables.FULL_USER_NAME);
        exampleUserTask.clickAbschliessen();

        //Step 1: Open Meine Aufgaben
        cy.log('Step 1');
        vorgangStarten.openMeineAufgaben();
        reloadPageUntilTasksVisible();
        meineAufgaben.getFoundTasks().should('eq',numberOfTasks);
        meineAufgaben.getElement(1).should('contain.text', 'User Task');

        //Step 2-5: Test different page sizes
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

        //Step 6-7: Check one list element
        cy.log('Step 6-7');
        meineAufgaben.clickElement(1);
        userTask.checkHeadline("User Task");
        userTask.clickAbschliessen();
    })

    // Close tasks
    after(() => {
            for (let i=1; i< numberOfTasks; i++){
                meineAufgaben.clickElement(1);
                userTask.clickAbschliessen();
                //necessary to wait for the task to be deleted
                cy.wait(3000);
                meineAufgaben.clickAktualisieren();
            }
    })


    //ensures the tasks are loaded
    function reloadPageUntilTasksVisible(maxAttempts=20, attempts=0) {
        if (attempts > maxAttempts) {
            throw new Error("Timed out waiting")
        }
        meineAufgaben.getFoundTasks().then(numTasks => {
            if (numTasks != numberOfTasks) {
                cy.wait(100)
                cy.log('iteration')
                meineAufgaben.clickAktualisieren();
                reloadPageUntilTasksVisible(maxAttempts, attempts+1)
            }
        })
    }
})
