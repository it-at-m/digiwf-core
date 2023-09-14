import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import exampleUserTask from "../pages/exampleUserTask"
import userTask from "../pages/userTask"
import * as dataElementKeys from "../constants/dataElementKeys"
import * as environmentVariables from "../constants/environmentVariables"
import aktuelleVorgaenge from "../pages/aktuelleVorgaenge"
import workflowUserTask from "../pages/workflowUserTask"

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
        createTasks(numberOfTasks-1)
        //ensures all the tasks are loaded
        vorgangStarten.openMeineAufgaben();
        reloadPageUntilTasksVisible(numberOfTasks-1);
        cy.wait(3000)
        closeTasks(numberOfTasks-1)
        //ensures all the tasks are loaded
        cy.wait(3000)

        //create tracked task
        meineAufgaben.openVorgangStarten();
        cy.wait(3000)
        vorgangStarten.findProcess(dataElementKeys.EXAMPLE_USER_TASK_NAME);
        createTasks(1)

        //Step 1: Open Aktuelle Vorgaenge
        cy.log('Step 1');
        vorgangStarten.openAktuelleVorgaenge();
        cy.wait(3000)
        //aktuelleVorgaenge.getElement(1).should('contain.text', 'User Task');

        //Step 2-5: Test different page sizes
        cy.log('Step 2-5');

        pageSize = 5;
        aktuelleVorgaenge.changePageSize(pageSize);
        aktuelleVorgaenge.checkPageSize(pageSize,numberOfTasks)

        pageSize = 10;
        aktuelleVorgaenge.changePageSize(pageSize);
        aktuelleVorgaenge.checkPageSize(pageSize,numberOfTasks)

        pageSize = 20;
        aktuelleVorgaenge.changePageSize(pageSize);
        aktuelleVorgaenge.checkPageSize(pageSize,numberOfTasks)

        //Step 6-7: Check one list element
        cy.log('Step 6-7');
        aktuelleVorgaenge.findProcess(dataElementKeys.EXAMPLE_USER_TASK_NAME)
        cy.wait(3000)
        aktuelleVorgaenge.getElement(1).click();
        workflowUserTask.taskIsCorrect(1,"User Task")
        workflowUserTask.getStatus(1).should('contain.text','offen')
        cy.wait(3000)
        workflowUserTask.openMeineAufgaben()
        closeTasks(1)
        meineAufgaben.openAktuelleVorgaenge()
        cy.wait(3000)
        aktuelleVorgaenge.getElement(1).click();
        workflowUserTask.taskIsCorrect(1,"User Task")
        workflowUserTask.getStatus(1).should('not.contain.text','offen')
    })

    function reloadPageUntilTasksVisible(number, maxAttempts=20, attempts=0) {
        if (attempts > maxAttempts) {
            throw new Error("Timed out waiting")
        }
        meineAufgaben.getFoundTasks().then(numTasks => {
            if (numTasks != number) {
                cy.wait(100)
                cy.log('iteration')
                meineAufgaben.clickAktualisieren();
                reloadPageUntilTasksVisible(number, maxAttempts, attempts+1)
            }
        })
    }

    function createTasks(number){
        for (let i=1; i<= number; i++){
            vorgangStarten.clickListElement(dataElementKeys.EXAMPLE_USER_TASK_KEY);
            exampleUserTask.setNumberOfTasks(1);
            exampleUserTask.setUserName(environmentVariables.FULL_USER_NAME);
            exampleUserTask.clickAbschliessen();
        }
    }

    function closeTasks(number){
        for (let i=1; i<= number; i++){
            meineAufgaben.clickElement(1);
            userTask.clickAbschliessen();
            //necessary to wait for the task to be deleted
            cy.wait(3000);
            meineAufgaben.clickAktualisieren();
        }
    }
})