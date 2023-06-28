import meineAufgaben from "../pages/meineAufgaben"

describe('dummy', () => {
    it("Login and click", () => {
        Cypress.on('uncaught:exception', (err, runnable) => {
            return false;
        });
        cy.loginUser();
        meineAufgaben.openVorgangStarten();
    });

})