import Pagination from "../components/pagination";

class openGroupTasks extends Pagination {
  headline = "Offene Gruppenaufgaben";

  checkHeadline() {
    super._checkHeadline(this.headline);
  }
}

module.exports = new openGroupTasks();
