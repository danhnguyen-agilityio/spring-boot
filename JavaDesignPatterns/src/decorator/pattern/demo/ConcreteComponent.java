package decorator.pattern.demo;

public class ConcreteComponent extends Component {
  public ConcreteComponent() {}

  @Override
  void doJob() {
    System.out.println("Concrete component - Not modify");
  }
}
