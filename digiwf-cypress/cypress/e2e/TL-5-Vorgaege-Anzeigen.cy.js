import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import exampleUserTask from "../pages/exampleUserTask"
import userTask from "../pages/userTask"
import {EXAMPLE_USER_TASK_KEY} from "../constants/dataElementKeys"
import {EXAMPLE_USER_TASK_NAME} from "../constants/dataElementKeys"
import {FULL_USER_NAME} from "../constants/environmentVariables"
import aktuelleVorgaenge from "../pages/aktuelleVorgaenge"
import workflowUserTask from "../pages/workflowUserTask"

const numberOfTasks = 21

before(() => {
    cy.login();
})


describe('Vorgaenge Anzeigen', () => {
    it('passes', () => {

        // Step 0: Create User processes
        cy.log('Step 0');
        meineAufgaben.openVorgangStarten();
        vorgangStarten.findProcess(EXAMPLE_USER_TASK_NAME);
        createTasks(numberOfTasks-1);
        //ensures all the tasks are loaded
        vorgangStarten.openMeineAufgaben();
        reloadPageUntilTasksVisible(numberOfTasks-1);
        closeTasks(numberOfTasks-1);

        //create tracked task
        meineAufgaben.openVorgangStarten();
        vorgangStarten.getSearchField().should('be.visible');
        vorgangStarten.findProcess(EXAMPLE_USER_TASK_NAME);
        createTasks(1)

        //Step 1: Open Aktuelle Vorgaenge
        cy.log('Step 1');
        vorgangStarten.openAktuelleVorgaenge();

        //Step 2-5: Test different page sizes
        cy.log('Step 2-5');

        let pageSize = 5;
        aktuelleVorgaenge.changePageSize(pageSize);
        aktuelleVorgaenge.checkPageSize(pageSize,numberOfTasks);

        pageSize = 10;
        aktuelleVorgaenge.changePageSize(pageSize);
        aktuelleVorgaenge.checkPageSize(pageSize,numberOfTasks);

        pageSize = 20;
        aktuelleVorgaenge.changePageSize(pageSize);
        aktuelleVorgaenge.checkPageSize(pageSize,numberOfTasks);

        //Step 6-7: Check one list element
        cy.log('Step 6-7');
        aktuelleVorgaenge.findProcess(EXAMPLE_USER_TASK_NAME);
        aktuelleVorgaenge.getElement(1).click();
        workflowUserTask.taskIsCorrect(1,"User Task");
        workflowUserTask.getStatus(1).should('contain.text','offen');
        workflowUserTask.openMeineAufgaben();
        closeTasks(1)
        meineAufgaben.openAktuelleVorgaenge()
        aktuelleVorgaenge.getElement(1).should('be.visible');
        aktuelleVorgaenge.getElement(1).click();
        workflowUserTask.taskIsCorrect(1,"User Task");
        workflowUserTask.getStatus(1).should('not.contain.text','offen');
    })

    function reloadPageUntilTasksVisible(number, maxAttempts=25, attempts=0) {
        if (attempts > maxAttempts) {
            throw new Error("Timed out waiting");
        }
        meineAufgaben.getFoundTasks().then(numTasks => {
            if (numTasks != number) {
                cy.wait(100);
                cy.log('iteration');
                meineAufgaben.clickAktualisieren();
                reloadPageUntilTasksVisible(number, maxAttempts, attempts+1);
            }
        })
    }

    function createTasks(number){
        for (let i=1; i<= number; i++){
            vorgangStarten.clickListElement(EXAMPLE_USER_TASK_KEY);
            exampleUserTask.setNumberOfTasks(1);
            exampleUserTask.setUserName(FULL_USER_NAME);
            exampleUserTask.clickAbschliessen();
        }
    }

    function closeTasks(number){
        for (let i=1; i<= number; i++){
            meineAufgaben.clickElement(1);
            userTask.clickAbschliessen();
            //necessary to wait for the task to be deleted
            cy.wait(2000);
            meineAufgaben.clickAktualisieren();
        }
    }
})