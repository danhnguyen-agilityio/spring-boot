package tweetapp.service;

import tweetapp.constant.App;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.DateUtil;
import tweetapp.util.FileUtil;
import tweetapp.util.StringUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PostService {
  /**
   * Process post data and return list post
   * @param lines Stream lines in file
   * @return Return list post
   */
  private static List<Post> processPostData(Stream<String> lines) {
    return lines.skip(1)
        .map(line -> {
          // Split line to array data
          String[] data = line.split(App.SPLIT_BY_CSV);
          String id = StringUtil.substringBetween(data[0], "\"");
          String authorId = data[2];
          String message = data[22];
          String commentsCount = data[23];
          LocalDateTime createdAt = DateUtil.convertStringToLocalDateTime(data[25]);
          LocalDateTime modifiedAt = DateUtil.convertStringToLocalDateTime(data[26]);
          String version = data[27];
          return new Post(id, authorId, message, commentsCount, createdAt, modifiedAt, version);
        })
        .collect(toList());
  }

  /**
   * Get all post from csv file
   * @return All info posts
   * @throws IOException exception thrown when occur file not found
   */
  public static List<Post> getPostsFromFile(String fileName) throws IOException {
    return FileUtil.readFile(fileName, PostService::processPostData);
  }

  /**
   * Check whether post have created within given period ago
   * @param post List post
   * @param period period time
   * @return true if post have created within given period ago ago and false if other
   */
  private static boolean createdWithinNumberDaysAgo(Post post, Period period) {
    return DateUtil.withinNumberDaysAgo(post.getCreatedAt(), period);
  }

  /**
   * Check whether post have created within given period ago
   * @param post List post
   * @param period period time
   * @return true if post have created within given period ago ago and false if other
   */
  private static boolean createdWithinNumberDaysAgo(Post post, Period period, LocalDateTime fromDate) {
    return DateUtil.withinNumberDaysAgo(post.getCreatedAt(), period, fromDate);
  }

  /**
   * Find post have created in given period ago from today
   * @param period Period time
   * @return List Post
   */
  public static List<Post> findPostsCreatedIn(List<Post> posts, Period period) {
    return findPostsCreatedIn(posts, period, LocalDateTime.now());
  }

  /**
   * Find post have created in given period ago from given fromDate
   * @param period Period time
   * @return List Post
   */
  public static List<Post> findPostsCreatedIn(List<Post> posts, Period period, LocalDateTime fromDate) {
    return posts.stream()
        .filter(post -> createdWithinNumberDaysAgo(post, period, fromDate))
        .collect(toList());
  }

  /**
   * Print all info list posts
   * @param posts List post
   */
  public static void print(List<Post> posts) {
    IntStream.range(0, posts.size())
        .forEach(idx -> {
          System.out.println("-------------------------------");
          System.out.print((idx + 1) + ". ");
          System.out.println(posts.get(idx));
        });
  }

  /**
   * Find posts by given userName
   * @param users List user
   * @param posts List post
   * @param userName user name was search in list user
   * @return Posts have userName contain given userName
   */
  public static List<Post> findPostsByUserName(List<User> users, List<Post> posts, String userName) {
    return users.stream()
        .filter(user -> UserService.containsUsername(user, userName))
        .map(User::getId)
        .flatMap(userId -> posts.stream().filter(post -> post.getAuthorId().equals(userId)))
        .collect(toList());
  }
}
