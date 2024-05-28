import startProcess from './startProcess'
import myTasks from './myTasks'
import currentInstances from './currentInstances'
import openGroupTasks from './openGroupTasks'
import inProgressGroupTasks from './inProgressGroupTasks'

class Nav {

    elements = {
        header: () => cy.get('header'),
        sidebar: () => cy.get('nav'),
        myTasksBtn: () => cy.get('nav .v-list .v-list-item:nth-of-type(1)'),
        currentInstancesBtn: () => cy.get('nav .v-list .v-list-item:nth-of-type(2)'),
        startProcessBtn: () => cy.get('nav .v-list .v-list-item:nth-of-type(3)'),
        openGroupTasksBtn: () => cy.get('nav .v-list .v-list-item:nth-of-type(4)'),
        inProgressGroupTasksBtn: () => cy.get('nav .v-list .v-list-item:nth-of-type(5)')
    }

    openMyTasks() {
        this.elements.myTasksBtn().click().then(() => {
            myTasks.checkHeadline()
            startProcess._waitUpdate('@dataGetMyTasks')
        })
        return myTasks
    }

    openCurrentInstances() {
        this.elements.currentInstancesBtn().click().then(() => {
            currentInstances.checkHeadline()
            currentInstances.waitLoadingFinished()
            currentInstances.paginationElements.update().click()
            // workaround as request superfast
            // currentInstances.waitIsLoading()
            cy.wait('@dataGetInstances').its('response.statusCode').should('equal', 200)
            currentInstances.waitLoadingFinished()
        })
        return currentInstances
    }

    openStartProcess() {
        this.elements.startProcessBtn().click().then(() => {
            startProcess.checkHeadline()
            startProcess._waitUpdate('@dataGetDefinitions')
        })
        return startProcess
    }

    openOpenGroupTasks() {
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/group/*',
        }).as('filter')
        this.elements.openGroupTasksBtn().click()
        cy.wait('@filter').its('response.statusCode').should('equal', 200)
        return openGroupTasks
    }

    openInProgressGroupTasks() {
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/group/*',
        }).as('userTasks')
        this.elements.inProgressGroupTasksBtn().click()
        cy.wait('@userTasks').its('response.statusCode').should('equal', 200)
        return inProgressGroupTasks
    }
}

module.exports = new Nav();