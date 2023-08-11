import vorgangStarten from "../pages/vorgangStarten"
import meineAufgaben from "../pages/meineAufgaben"
import * as dataElementKeys from "../constants/dataElementKeys"
import * as environmentVariables from "../constants/environmentVariables"
import exampleGroupTask from "../pages/exampleGroupTask";
import exampleUserTask from "../pages/exampleUserTask";

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
        exampleUserTask.setNumberOfTasks(numberOfTasks);
        exampleGroupTask.setGroup("group1");
        cy.log("abschliessen")
        exampleGroupTask.clickAbschliessen();
        cy.log("after")
    });

})