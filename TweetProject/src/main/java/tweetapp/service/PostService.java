package tweetapp.service;

import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

/**
 * PostService interface
 */
public interface PostService {

  /**
   * Print all info list posts
   *
   * @param posts List post
   */
  void print(List<Post> posts);

  /**
   * Check whether post have created within given period ago
   *
   * @param post   List post
   * @param period period time
   * @return true if post have created within given period ago ago and false if other
   */
  static boolean createdWithinNumberDaysAgo(Post post, Period period) {
    return DateUtil.withinNumberDaysAgo(post.getCreatedAt(), period);
  }

  /**
   * Check whether post have created within given period ago
   *
   * @param post   List post
   * @param period period time
   * @return true if post have created within given period ago ago and false if other
   */
  static boolean createdWithinNumberDaysAgo(Post post, Period period, LocalDateTime fromDate) {
    return DateUtil.withinNumberDaysAgo(post.getCreatedAt(), period, fromDate);
  }

  /**
   * Get all post from csv file
   *
   * @return All info posts
   * @throws IOException exception thrown when occur file not found
   */
  List<Post> getPostsFromFile(String fileName) throws IOException;

  /**
   * Find post have created in given period ago from today
   *
   * @param period Period time
   * @return List Post
   */
  List<Post> findPostsCreatedIn(List<Post> posts, Period period);

  /**
   * Find post have created in given period ago from given fromDate
   *
   * @param period Period time
   * @return List Post
   */
  List<Post> findPostsCreatedIn(List<Post> posts, Period period, LocalDateTime fromDate);

  /**
   * Find posts by given userName
   *
   * @param users    List user
   * @param posts    List post
   * @param userName user name was search in list user
   * @return Posts have userName contain given userName
   */
  List<Post> findPostsByUserName(List<User> users, List<Post> posts, String userName);
}
