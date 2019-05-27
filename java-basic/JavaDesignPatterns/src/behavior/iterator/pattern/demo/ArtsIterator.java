package behavior.iterator.pattern.demo;

public class ArtsIterator implements IIterator {
  private String[] subjects;
  private int position;

  public ArtsIterator(String[] subjects) {
    this.subjects = subjects;
    position = 0;
  }

  @Override
  public void first() {
    position = 0;
  }

  @Override
  public String next() {
    return subjects[position++];
  }

  @Override
  public boolean isDone() {
    return position >= subjects.length;
  }

  @Override
  public String currentItem() {
    return subjects[position];
  }
}
