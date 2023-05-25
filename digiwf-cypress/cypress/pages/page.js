class Page{

    checkIfTasksAreEmpty(){
        cy.log("Fails if there are any open tasks")
        cy.get('.v-list-item--active > div:nth-child(1) > div:nth-child(1) > span:nth-child(2)').should('not.exist');
    }

    navBar(){
        return cy.get('.v-app-bar__nav-icon > span:nth-child(1)')
    }

    navBarMeineAufgaben(){
        return cy.get('a.v-list-item:nth-child(1) > div:nth-child(1)')
    }

    navBarAktuelleVorgaenge(){
       return cy.get('a.v-list-item:nth-child(3) > div:nth-child(1)')
    }

    navBarVorgangStarten(){
        return cy.get('a.v-list-item:nth-child(5) > div:nth-child(1)')
    }

    clickNavbar(){
        this.navBar().click()
    }

    openMeineAufgaben(){
        this.navBarMeineAufgaben().click()
    }

    openAktuelleVorgaenge(){
        this.navBarAktuelleVorgaenge().click()
    }

    openVorgangStarten(){
        cy.intercept({
            method: 'GET',
            url: '/api/digitalwf-backend-service/rest/service/definition',
        }).as('dataGetAntraege')
        this.navBarVorgangStarten().click()
        cy.wait('@dataGetAntraege').its('response.statusCode').should('equal', 200)
    }
}

export default Page