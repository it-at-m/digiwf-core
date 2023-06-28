import meineAufgaben from "../pages/meineAufgaben"

describe('dummy', () => {
    it("Login and click", () => {
        cy.loginUser();
        meineAufgaben.openVorgangStarten();
    });

})