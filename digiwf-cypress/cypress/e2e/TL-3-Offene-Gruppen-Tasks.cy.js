import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import * as dataElementKeys from "../constants/dataElementKeys"
import * as environmentVariables from "../constants/environmentVariables"
import exampleGroupTask from "../pages/exampleGroupTask";
import offeneGruppenAufgaben from "../pages/offeneGruppenAufgaben";
import groupUserTasks from "../pages/groupUserTask"

const numberOfTasks = 2

before(() => {
    cy.login()
})

describe('offene Gruppentasks anzeigen', () => {
    it('Gruppen Task Ansicht testen', () => {

        // Step 0: Create group tasks
        cy.log('Step 0');
        meineAufgaben.openVorgangStarten();
        vorgangStarten.findProcess(dataElementKeys.EXAMPLE_GROUP_TASK_NAME);
        vorgangStarten.clickListElement(dataElementKeys.EXAMPLE_GROUP_TASK_KEY);
        exampleGroupTask.setNumberOfTasks(numberOfTasks);
        exampleGroupTask.setGroup("group1");
        //Necessary to set the group value
        exampleGroupTask.clickNumberOfParallelTasks();
        exampleGroupTask.clickAbschliessen();

        //Close Grouptasks
        for (let i=1; i<= numberOfTasks; i++) {
            vorgangStarten.openGruppenAufgabenOffen();
            offeneGruppenAufgaben.checkHeadline();
            offeneGruppenAufgaben.clickAktualisieren();
            offeneGruppenAufgaben.clickElement(1);
            groupUserTasks.checkHeadline();
            groupUserTasks.clickBearbeiten();
            groupUserTasks.tickCheckbox();
            groupUserTasks.clickAbschliessen();
            meineAufgaben.clickAktualisieren();
        }
    });

})