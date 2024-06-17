import Pagination from "../components/pagination";

class AssignedGroupTasks extends Pagination {
  headline = "Gruppenaufgaben in Bearbeitung";

  checkHeadline() {
    super._checkHeadline(this.headline);
  }

  update() {
    this._waitUpdate("@dataGetAssignedGroupTasks");
  }
}

module.exports = new AssignedGroupTasks();
