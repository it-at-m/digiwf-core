import Pagination from "../components/pagination";

class StartProcess extends Pagination {
  headline = "Vorgänge";

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  findProcess(text) {
    this.paginationElements
      .searchBox()
      .type(text)
      .then(() => {
        this.waitIsLoading();
        cy.wait("@dataGetDefinitions")
          .its("response.statusCode")
          .should("equal", 200);
        this.waitLoadingFinished();
      });
  }

  startProcess(text) {
    this.findProcess(text);
    this.getItemCount().then((numProcesses) => {
      expect(numProcesses).eq(1);
    });
    this.clickItem(0);
  }
}

module.exports = new StartProcess();
