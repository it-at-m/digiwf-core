import Pagination from './pagination'

class CurrentInstances extends Pagination {
    headline = "Aktuelle Vorgänge"

    checkHeadline() {
        super._checkHeadline(this.headline);
    }
}

module.exports = new CurrentInstances();