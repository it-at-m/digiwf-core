import Pagination from "../components/pagination";

class MyTasks extends Pagination {
  headline = "Meine Aufgaben";

  elements = {
    uncompletedTasks: () =>
      this.paginationElements.list().get('[data-test="task-is-completing"]'),
  };

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  update() {
    this._waitUpdate("@dataGetMyTasks");
  }

  waitNoUncompletedTasks() {
    this.waitLoadingFinished();
    cy.wait(2000);
    this.update();
    this.elements.uncompletedTasks().should("not.exist");
  }
}

module.exports = new MyTasks();
