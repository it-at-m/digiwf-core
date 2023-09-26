class Page{

    checkIfTasksAreEmpty(){
        cy.log("Fails if there are any open tasks")
        cy.get('.v-list-item--active > div:nth-child(1) > div:nth-child(1) > span:nth-child(2)').should('not.exist');
    }

    navBar(){
        return cy.get('.v-app-bar__nav-icon > span:nth-child(1)')
    }

    navBarMeineAufgaben(){
        return cy.get('div.v-list:nth-child(1) > a:nth-child(1) > div:nth-child(1)')
    }

    navBarAktuelleVorgaenge(){
       return cy.get('a.v-list-item:nth-child(3) > div:nth-child(1)')
    }

    navBarVorgangStarten(){
        return cy.get('a.v-list-item:nth-child(5) > div:nth-child(1)')
    }

    navbarGruppenAufgabenOffen(){
        return cy.get('a.v-list-item:nth-child(8)')
    }

    navbarGruppenAufgabenInBearbeitung(){
        return cy.get('a.v-list-item:nth-child(10)')
    }

    clickNavbar(){
        this.navBar().click()
    }

    openMeineAufgaben(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-backend-service/rest/service/*',
        }).as('dataGetDefinitions')
        this.navBarMeineAufgaben().click({ multiple: true } )
        cy.wait('@dataGetDefinitions').its('response.statusCode').should('equal', 200)
    }

    openAktuelleVorgaenge(){
        cy.intercept({
            method: 'GET',
            url: '/api//digitalwf-backend-service/rest/service/instance**',
        }).as('dataGetInstance')
        this.navBarAktuelleVorgaenge().click()
        cy.wait('@dataGetInstance').its('response.statusCode').should('equal', 200)
    }

    openVorgangStarten(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-backend-service/rest/service/*',
        }).as('dataGetDefinitions')
        this.navBarVorgangStarten().click()
        cy.wait('@dataGetDefinitions').its('response.statusCode').should('equal', 200)
    }

    openGruppenAufgabenOffen(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/group/*',
        }).as('filter')
        this.navbarGruppenAufgabenOffen().click()
        cy.wait('@filter').its('response.statusCode').should('equal', 200)
    }

    openInBearbeitung(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-tasklist-service/rest/tasks/group/*',
        }).as('userTasks')
        this.navbarGruppenAufgabenInBearbeitung().click()
        cy.wait('@userTasks').its('response.statusCode').should('equal', 200)
    }
}

export default Page