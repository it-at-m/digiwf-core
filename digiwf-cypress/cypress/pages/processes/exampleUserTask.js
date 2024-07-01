import Task from "../../components/task";

class ExampleUserTask extends Task {
  headline = "User Task";

  checkHeadline() {
    super._checkHeadline(this.headline);
  }
}

module.exports = new ExampleUserTask();
