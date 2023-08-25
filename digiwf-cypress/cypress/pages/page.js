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

    clickNavbar(){
        this.navBar().click()
    }

    openMeineAufgaben(){
        this.navBarMeineAufgaben().click({ multiple: true } )
    }

    openAktuelleVorgaenge(){
        this.navBarAktuelleVorgaenge().click()
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
}

export default Page