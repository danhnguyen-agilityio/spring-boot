/** An exception for queue-full errors */
public class QueueFullException extends Exception {
  int size;

  QueueFullException(int s) {
    size = s;
  }

  /** Get info exception */
  public String toString() {
    return "\nQueue is full. Maximum size is " + " size";
  }
}