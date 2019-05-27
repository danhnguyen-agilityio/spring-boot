package behavior.template.method.pattern.demo;

public class TemplateMethodPatternEx {
  public static void main(String[] args) {
    BasicEngineering bs = new ComputerScience();
    System.out.println("Computer Sc papers: ");
    bs.Papers();
    bs = new Electronics();
    System.out.println("Electronics Papers: ");
    bs.Papers();
  }
}
