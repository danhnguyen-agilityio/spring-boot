package tweetapp.service;

import tweetapp.constant.App;
import tweetapp.model.Post;
import tweetapp.model.PostBuilder;
import tweetapp.model.User;
import tweetapp.util.DateUtil;
import tweetapp.util.FileUtil;
import tweetapp.util.StringUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PostServiceImpl implements PostService {
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
          return PostBuilder.post()
              .withId(id)
              .withAuthorId(authorId)
              .withMessage(message)
              .withCommentsCount(commentsCount)
              .withCreatedAt(createdAt)
              .withModifiedAt(modifiedAt)
              .withVerion(version)
              .build();
        })
        .collect(toList());
  }

  /**
   * Print all info list posts
   * @param posts List post
   */
  @Override
  public void print(List<Post> posts) {
    if (posts.size() == 0) return;

    System.out.println(String.format("| %-5s | %-25s | %-25.25s | %-25.25s",
        "Index", "Id", "Author Id", "Created At"));
    IntStream.range(0, posts.size())
        .forEach(idx -> {
          Post post = posts.get(idx);
          System.out.println(String.format("| %-5d | %-25s | %-25.25s | %-15.15s",
              idx + 1, post.getId(), post.getAuthorId(), post.getCreatedAt()));
        });
  }

  /**
   * Get all post from csv file
   * @return All info posts
   * @throws IOException exception thrown when occur file not found
   */
  @Override
  public List<Post> getPostsFromFile(String fileName) throws IOException {
    return FileUtil.readFile(fileName, PostServiceImpl::processPostData);
  }

  /**
   * Find post have created in given period ago from today
   * @param period Period time
   * @return List Post
   */
  @Override
  public List<Post> findPostsCreatedIn(List<Post> posts, Period period) {
    return findPostsCreatedIn(posts, period, LocalDateTime.now());
  }

  /**
   * Find post have created in given period ago from given fromDate
   * @param period Period time
   * @return List Post
   */
  @Override
  public List<Post> findPostsCreatedIn(List<Post> posts, Period period, LocalDateTime fromDate) {
    return posts.stream()
        .filter(post -> PostService.createdWithinNumberDaysAgo(post, period, fromDate))
        .collect(toList());
  }

  /**
   * Find posts by given userName
   * @param users List user
   * @param posts List post
   * @param userName user name was search in list user
   * @return Posts have userName contain given userName
   */
  @Override
  public List<Post> findPostsByUserName(List<User> users, List<Post> posts, String userName) {
    return users.stream()
        .filter(user -> UserService.containsUsername(user, userName))
        .map(User::getId)
        .flatMap(userId -> posts.stream().filter(post -> post.getAuthorId().equals(userId)))
        .collect(toList());
  }
}
