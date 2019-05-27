package structure.decorator.pattern.demo;

public abstract class AbstractDecorator extends Component {
  protected Component com;

  public void setTheComponent(Component c) {
    com = c;
  }

  @Override
  void doJob() {
    if (com != null) {
      com.doJob();
    }
  }
}
