import Form from "../../components/form";

class ExampleUserTask extends Form {
  headline = "User Task";

  checkHeadline() {
    super._checkHeadline(this.headline);
  }
}

module.exports = new ExampleUserTask();
