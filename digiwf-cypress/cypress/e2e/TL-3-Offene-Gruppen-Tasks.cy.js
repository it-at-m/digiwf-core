import vorgangStarten from "../pages/vorgangStarten";
import meineAufgaben from "../pages/meineAufgaben";
import {EXAMPLE_GROUP_TASK_NAME} from "../constants/dataElementKeys";
import {EXAMPLE_GROUP_TASK_KEY} from "../constants/dataElementKeys";
import {GROUP_NAME_1} from "../constants/environmentVariables";
import exampleGroupTask from "../pages/exampleGroupTask";
import offeneGruppenAufgaben from "../pages/offeneGruppenAufgaben";
import groupUserTasks from "../pages/groupUserTask"

//change Pagesize for better runtime
const numberOfTasks = 11 //21

before(() => {
    cy.login()
})

describe('offene Gruppentasks anzeigen', () => {
    it('Gruppen Task Ansicht testen', () => {
        let pageSize = 20;

        // Step 0: Create group tasks
        cy.log('Step 0');
        meineAufgaben.openVorgangStarten();
        vorgangStarten.findProcess(EXAMPLE_GROUP_TASK_NAME);
        vorgangStarten.clickListElement(EXAMPLE_GROUP_TASK_KEY);
        exampleGroupTask.setNumberOfTasks(numberOfTasks);
        exampleGroupTask.setGroup(GROUP_NAME_1);

        //Necessary to set the group value
        exampleGroupTask.clickNumberOfParallelTasks();
        exampleGroupTask.clickAbschliessen();

        //Step 1: Open offene Gruppenaufgaben
        cy.log('Step 1');
        vorgangStarten.openGruppenAufgabenOffen();
        offeneGruppenAufgaben.checkHeadline();
        offeneGruppenAufgaben.clickAktualisieren();
        reloadPageUntilTasksVisible();

        //Step 2-5: Test different page sizes
        cy.log('Step 2-5');

        pageSize = 5;
        offeneGruppenAufgaben.changePageSize(pageSize);
        offeneGruppenAufgaben.checkPageSize(pageSize,numberOfTasks)

        pageSize = 10;
        offeneGruppenAufgaben.changePageSize(pageSize);
        offeneGruppenAufgaben.checkPageSize(pageSize,numberOfTasks)

        /*pageSize = 20;
        offeneGruppenAufgaben.changePageSize(pageSize);
        offeneGruppenAufgaben.checkPageSize(pageSize,numberOfTasks)

         */
    });

    after(() => {
        //Close group tasks and check necessary group task function
        for (let i=1; i<= numberOfTasks; i++) {
            vorgangStarten.openGruppenAufgabenOffen();
            //necessary due to slow page loading times
            cy.wait(2000);
            offeneGruppenAufgaben.checkHeadline();
            offeneGruppenAufgaben.clickAktualisieren();
            offeneGruppenAufgaben.clickElement(1);
            groupUserTasks.checkHeadline();
            groupUserTasks.clickBearbeiten();
            groupUserTasks.tickCheckbox();
            groupUserTasks.clickAbschliessen();
            meineAufgaben.clickAktualisieren();
            //wait for task being closed
            cy.wait(2000);
        }
    })

    //ensures all the tasks are loaded
    function reloadPageUntilTasksVisible(maxAttempts=20, attempts=0) {
        if (attempts > maxAttempts) {
            throw new Error("Timed out waiting")
        }
        offeneGruppenAufgaben.getFoundTasks().then(numTasks => {
            if (numTasks != numberOfTasks) {
                cy.wait(100)
                cy.log('iteration')
                offeneGruppenAufgaben.clickAktualisieren();
                reloadPageUntilTasksVisible(maxAttempts, attempts+1)
            }
        })
    }

})