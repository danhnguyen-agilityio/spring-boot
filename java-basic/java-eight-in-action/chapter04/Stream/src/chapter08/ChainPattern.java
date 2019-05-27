package chapter08;

import java.util.function.Function;
import java.util.function.UnaryOperator;

abstract class ProcessingObject<T> {
  protected ProcessingObject<T> successor;

  public void setSuccessor(ProcessingObject<T> successor) {
    this.successor = successor;
  }

  public T handle(T input) {
    T r = handleWork(input);
    if (successor != null) {
      return successor.handle(r);
    }
    return r;
  }

  abstract protected T handleWork(T input);
}

class HeaderTextProcessing extends ProcessingObject<String> {
  public String handleWork(String text) {
    return "From Raoul, Mario and Alan: " + text;
  }
}

class SpellCheckerProcessing extends ProcessingObject<String> {
  @Override
  protected String handleWork(String input) {
    return input.replaceAll("labda", "lambda");
  }
}

public class ChainPattern {
  public static void main(String[] args) {
    ProcessingObject<String> p1 = new HeaderTextProcessing();
    ProcessingObject<String> p2 = new SpellCheckerProcessing();

    p1.setSuccessor(p2);

    String result = p1.handle("Aren't labdas really sexy");
    System.out.println(result);

    // Use lambda
    UnaryOperator<String> headerProcessing = text -> "From Raoul : " + text;
    UnaryOperator<String> spellCheckerProcessing = text -> text.replaceAll("labda", "lambda");

    // compose the two functions, resulting in a chain of operations
    Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);

    result = pipeline.apply("Aren't labdas really sexy");
    System.out.println(result);
  }
}
