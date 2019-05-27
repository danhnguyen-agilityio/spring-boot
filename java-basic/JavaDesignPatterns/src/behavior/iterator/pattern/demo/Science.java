package behavior.iterator.pattern.demo;

import java.util.LinkedList;

public class Science implements ISubject {
  private LinkedList<String> subjects;

  public Science() {
    subjects = new LinkedList<>();
    subjects.addLast("Math");
    subjects.addLast("Comp");
    subjects.addLast("Physics");
  }

  @Override
  public IIterator CreateIterator() {
    return new ScienceIterator(subjects);
  }
}
