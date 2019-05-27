package behavior.interpreter.pattern.demo;

/**
 * Context class: interpretation is carried out based on our implementation
 */
class Context {
  public String input;

  public Context(String input) {
    this.input = input;
  }

  public void getBinaryForm(String input) {
    int i = Integer.parseInt(input);
    // integer to its equivalent binary string representation
    String binaryString = Integer.toBinaryString(i);
    System.out.println("Binary equivalent of " + input + " is " + binaryString);
  }

  public void printInWords(String input) {
    this.input = input;
    System.out.println("Printing the input in words: ");
    char c[] = input.toCharArray();
    for (int i = 0; i < c.length; i++) {
      switch (c[i]) {
        case '1':
          System.out.println("One ");
          break;
        case '2':
          System.out.println("Two ");
          break;
        case '3':
          System.out.println("Three ");
          break;
        case '4':
          System.out.println("Four ");
          break;
        case '5':
          System.out.println("Five ");
          break;
        default:
          System.out.println("* ");
          break;
      }
    }
  }
}

interface IExpression {
  void interpret(Context ic);
}

class StringToBinaryExp implements IExpression {
  private String str;

  public StringToBinaryExp(String s) {
    str = s;
  }

  @Override
  public void interpret(Context ic) {
    ic.getBinaryForm(str);
  }
}

class IntToWords implements IExpression {
  private String str;

  public IntToWords(String str) {
    this.str = str;
  }

  public void interpret(Context ic) {
    ic.printInWords(str);
  }
}

public class InterpreterPatternEx {
  public Context clientConext = null;
  public IExpression exp = null;
  public InterpreterPatternEx(Context c) {
    clientConext = c;
  }

  public void interpret(String str) {
    exp = new IntToWords(str);
    exp.interpret(clientConext);
    exp = new StringToBinaryExp(str);
    exp.interpret(clientConext);
  }

  public static void main(String[] args) {
    String input = "524";
    Context context = new Context(input);
    InterpreterPatternEx client = new InterpreterPatternEx(context);
    client.interpret(input);
  }
}
