import Pagination from "./pagination";

class MyTasks extends Pagination {
    headline = "Meine Aufgaben"

    elements = {
        uncompletedTasks: () => this.paginationElements.list().get('')
    }

    checkHeadline() {
        super._checkHeadline(this.headline);
    }

    update() {
        this._waitUpdate('@dataGetMyTasks')
    }
}

module.exports = new MyTasks();