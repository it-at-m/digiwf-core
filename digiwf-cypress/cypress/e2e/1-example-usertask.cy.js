import page from "../pages/nav";
import exampleUserTask from "../pages/processes/exampleUserTask"
import userTask from "../pages/userTask"

beforeEach(() => {
    cy.login({
            root: Cypress.env('sso_url'),
            realm: Cypress.env('sso_realm'),
            username: Cypress.env('username'),
            password: Cypress.env('password'),
            client_id: Cypress.env('sso_client'),
            redirect_uri: Cypress.config().baseUrl,
        }
    )
})

describe('Example Usertask', () => {
    it('passes', () => {
        cy.visit('/')
        let myTasks = page.openMyTasks()
        myTasks.getItemCount().as('initialMyTasksCount')

        cy.log("Start process")
        let startProcess = page.openStartProcess();
        startProcess.findProcess("Example Usertask")
        startProcess.getItemCount().then((numProcesses) => {
            expect(numProcesses).eq(1);
        })
        startProcess.clickItem(0);
        exampleUserTask.setUserName(Cypress.env('realname'))
        exampleUserTask.clickComplete()

        cy.log("Check instance state")
        let currentInstances = page.openCurrentInstances()
        currentInstances.itemContainsText(0, "Started")

        cy.log("Test task exists")
        myTasks = page.openMyTasks()
        myTasks.itemContainsText(0, "User Task")
        myTasks.getItemCount().as('createdMyTasksCount')
        cy.get('@initialMyTasksCount').then((initial) => {
            cy.get('@createdMyTasksCount').then((created) => {
                expect(created).eq(initial + 1)
            })
        })

        cy.log("Test task open and complete")
        myTasks.clickItem(0)
        userTask.checkHeadline("User Task")
        userTask.clickComplete()

        cy.log("Test task closed")
        myTasks.waitLoadingFinished()
        cy.wait(2000)
        myTasks.clickUpdate()
        myTasks.waitIsLoading()
        myTasks.waitLoadingFinished()
        myTasks.getItemCount().as('finishedMyTasksCount')
        cy.get('@createdMyTasksCount').then((created) => {
            cy.get('@finishedMyTasksCount').then((finished) => {
                expect(finished).eq(created - 1)
            })
        })

        cy.log("Check instance state")
        currentInstances = page.openCurrentInstances()
        currentInstances.itemContainsText(0, "Finished")
    })
})
