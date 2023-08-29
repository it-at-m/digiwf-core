import vorgangStarten from "../pages/vorgangStarten";
import meineAufgaben from "../pages/meineAufgaben";
import {EXAMPLE_GROUP_TASK_NAME} from "../constants/dataElementKeys";
import {EXAMPLE_GROUP_TASK_KEY} from "../constants/dataElementKeys";
import {GROUP_NAME_1} from "../constants/environmentVariables";
import exampleGroupTask from "../pages/exampleGroupTask";
import offeneGruppenAufgaben from "../pages/offeneGruppenAufgaben";
import groupUserTasks from "../pages/groupUserTask"
import inBearbeitung from "../pages/inBearbeitung"

//change Pagesize for better runtime
const numberOfTasks = 11 //21

before(() => {
    cy.login()
})

describe('zugewiesene Gruppentasks anzeigen', () => {
    it('Zugewiesene Gruppen Task Ansicht testen', () => {
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
        vorgangStarten.openGruppenAufgabenOffen();
        offeneGruppenAufgaben.clickAktualisieren();
        reloadPageUntilTasksVisible();

        //assign tasks
        for (let i=1; i<= numberOfTasks; i++) {
            cy.log("step 1")
            vorgangStarten.openGruppenAufgabenOffen();
            //necessary due to slow page loading times
            cy.wait(2000);
            offeneGruppenAufgaben.checkHeadline();
            offeneGruppenAufgaben.clickAktualisieren();
            cy.log("step 2")
            offeneGruppenAufgaben.clickElement(1);
            groupUserTasks.checkHeadline();
            cy.log("step 3")
            groupUserTasks.clickBearbeiten();
            cy.wait(2000);
        }

        //Step 1: Open offene Gruppenaufgaben
        cy.log('Step 1');
        vorgangStarten.openInBearbeitung();
        inBearbeitung.checkHeadline();
        inBearbeitung.clickAktualisieren();
        reloadPageUntilTasksVisible();

        //Step 2-5: Test different page sizes
        cy.log('Step 2-5');

        pageSize = 5;
        inBearbeitung.changePageSize(pageSize);
        inBearbeitung.checkPageSize(pageSize,numberOfTasks)

        pageSize = 10;
        inBearbeitung.changePageSize(pageSize);
        inBearbeitung.checkPageSize(pageSize,numberOfTasks)

        /*pageSize = 20;
        offeneGruppenAufgaben.changePageSize(pageSize);
        offeneGruppenAufgaben.checkPageSize(pageSize,numberOfTasks)

         */
    });

    after(() => {
        vorgangStarten.openMeineAufgaben();
        cy.wait(2000);
        //Close group tasks and check necessary group task function
        for (let i=1; i<= numberOfTasks; i++) {
            meineAufgaben.clickElement(1);
            groupUserTasks.checkHeadline();
            groupUserTasks.tickCheckbox();
            groupUserTasks.clickAbschliessen();
            //necessary to wait for the task to be deleted
            cy.wait(3000);
            meineAufgaben.clickAktualisieren();
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