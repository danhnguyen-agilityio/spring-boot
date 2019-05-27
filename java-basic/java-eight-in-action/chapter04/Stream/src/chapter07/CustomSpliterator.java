package chapter07;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class WordCounter {
  private final int counter;
  private final boolean lastSpace;

  public WordCounter(int counter, boolean lastSpace) {
    this.counter = counter;
    this.lastSpace = lastSpace;
  }

  /**
   * The accumulate method traverses the Characters one by on as done by the iterative algorithm
   */
  public WordCounter accumulate(Character c) {
    if (Character.isWhitespace(c)) {
      return lastSpace ? this : new WordCounter(counter, true);
    } else {
      // Increase the word counter when the last character is a space and the currently traversed one isn't
      return lastSpace ? new WordCounter(counter + 1, false) : this;
    }
  }

  /**
   * Combine two WordCounters by summing their counters
   */
  public WordCounter combine(WordCounter wordCounter) {
    // Use only the sum of the counters so you don't care about lastSpace
    return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
  }

  public int getCounter() {
    return counter;
  }
}

class WordCounterSpliterator implements Spliterator<Character> {
  private final String string;
  private int currentChar = 0;

  public WordCounterSpliterator(String string) {
    this.string = string;
  }

  @Override
  public boolean tryAdvance(Consumer<? super Character> action) {
    action.accept(string.charAt(currentChar++)); // consume the current character
    return currentChar < string.length(); // return true if there are further characters to be consumed
  }

  @Override
  public Spliterator<Character> trySplit() {
    int currentSize = string.length() - currentChar;
    if (currentSize < 10) {
      return null; // return null to signal that the String to be parsed is small enough to be processed sequentially
    }
    for (int slitPos = currentSize / 2 + currentChar; slitPos < string.length(); slitPos++) {
      // Advance the split position until the next space
      if (Character.isWhitespace(string.charAt(slitPos))) {
        // Create a new WordCounterSpliterator parsing the String from the start to the split position
        Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, slitPos));

        // Set the start position of this WordCounterSpliterator to the split position
        currentChar = slitPos;
        return spliterator;
      }
    }
    return  null;
  }

  @Override
  public long estimateSize() {
    return string.length() - currentChar;
  }

  @Override
  public int characteristics() {
    return ORDERED + SIZED +SUBSIZED + NONNULL + IMMUTABLE;
  }
}

public class CustomSpliterator {

  public static int countWordsIteratively(String s) {
    int counter = 0;
    boolean lastSpace = true;
    for (char c : s.toCharArray()) {
      if (Character.isWhitespace(c)) {
        lastSpace = true;
      } else {
        if (lastSpace) counter++;
        lastSpace = false;
      }
    }
    return counter;
  }

  public static int countWords(Stream<Character> stream) {
    WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
    return wordCounter.getCounter();
  }

  public static void main(String[] args) {
    final String SENTENCE = "abc def ghj";
    System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");

    Stream<Character> stream = IntStream.range(0, SENTENCE.length())
        .mapToObj(SENTENCE::charAt);
    System.out.println("Found " + countWords(stream.parallel()) + " words");

    Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
    Stream<Character> streamSpliterator = StreamSupport.stream(spliterator, true); // create parallel stream
    System.out.println("Use Spliterator to split into multi task: " + countWords(streamSpliterator) + " words");
  }
}
