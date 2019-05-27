package behavior.template.method.pattern.demo;

public abstract class BasicEngineering {
  // Papers() in the template method
  public void Papers() {
    // Common Papers
    Math();
    SoftSkills();
    // Specialized Paper
    SpecialPaper();
  }

  private void Math() {
    System.out.println("Mathematics");
  }

  private void SoftSkills() {
    System.out.println("SoftSkills");
  }

  // Abstract method must be implemented in derived classes
  public abstract void SpecialPaper();
}
