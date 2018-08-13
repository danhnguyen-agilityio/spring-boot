package behavior.iterator.pattern.demo;

import java.util.Iterator;

public class Arts implements ISubject {
  private String[] subjects;

  public Arts() {
    subjects = new String[2];
    subjects[0] = "Bengali";
    subjects[1] = "English";
  }

  @Override
  public IIterator CreateIterator() {
    return new ArtsIterator(subjects);
  }
}
