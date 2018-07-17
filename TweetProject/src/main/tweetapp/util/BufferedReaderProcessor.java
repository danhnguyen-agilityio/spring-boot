package tweetapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@FunctionalInterface
public interface BufferedReaderProcessor<T> {
  List<T> process(BufferedReader br) throws IOException;
}
