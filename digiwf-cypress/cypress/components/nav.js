import startProcess from '../pages/startProcess'
import myTasks from '../pages/myTasks'
import currentInstances from '../pages/currentInstances'
import openGroupTasks from '../pages/openGroupTasks'
import inProgressGroupTasks from '../pages/assignedGroupTasks'

class Nav {

    elements = {
        header: () => cy.get('header'),
        sidebar: () => cy.get('nav'),
        myTasksBtn: () => cy.get('nav [data-test="my-tasks-btn"]'),
        currentInstancesBtn: () => cy.get('nav [data-test="current-instances-btn"]'),
        startProcessBtn: () => cy.get('nav [data-test="start-process-btn"]'),
        openGroupTasksBtn: () => cy.get('nav [data-test="open-group-tasks-btn"]'),
        assignedGroupTasksBtn: () => cy.get('nav [data-test="assigned-group-tasks-btn"]')
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
        this.elements.assignedGroupTasksBtn().click()
        cy.wait('@userTasks').its('response.statusCode').should('equal', 200)
        return inProgressGroupTasks
    }
}

module.exports = new Nav();