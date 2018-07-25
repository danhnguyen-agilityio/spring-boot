package tweetapp.service;

import org.junit.BeforeClass;
import org.junit.Test;
import tweetapp.mock.MockPost;
import tweetapp.mock.MockUser;
import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static org.junit.Assert.*;

public class PostServiceTest {

  public static List<User> users;
  public static List<Post> posts;
  public static UserServiceImpl userService;
  public static PostServiceImpl postService;
  public static MockUser mockUser;
  public static MockPost mockPost;

  @BeforeClass
  public static void beforeClass() throws IOException {

    userService = new UserServiceImpl();
    postService = new PostServiceImpl();
    mockUser = new MockUser();
    mockPost = new MockPost();

    String userFile = "./src/test/resources/users-test.csv";
    users = userService.getUsersFromFile(userFile);

    String postFile = "./src/test/resources/posts-test.csv";
    posts = postService.getPostsFromFile(postFile);
  }

  /**
   * Test getting users from file not found
   */
  @Test(expected = IOException.class)
  public void testGetPostFromFileError() throws IOException {
    String postFile = "./src/test/resources/post-not-found.csv";
    postService.getPostsFromFile(postFile);
  }

  /**
   * Test getting posts from file
   */
  @Test
  public void testGetPostUsersFromFile() throws IOException {
    String postFile = "./src/test/resources/posts-test.csv";
    List<Post> posts = postService.getPostsFromFile(postFile);

    // Check same size
    long expectedSize = 7;
    long actualSize = posts.size();
    assertEquals(expectedSize, actualSize);

    // Check id of first post
    String expectedFirstPostId = "5b4c63ab170bb81857925455";
    String actualFirstPostId = posts.get(0).getId();
    assertEquals(expectedFirstPostId, actualFirstPostId);

    // Check id of first post
    String expectedLastPostId = "5b4c63ab170bb8185792545b";
    String actualLastPostId = posts.get(posts.size() -1).getId();
    assertEquals(expectedLastPostId, actualLastPostId);
  }

  /**
   * Testing getting posts from empty file
   * @throws IOException
   */
  @Test
  public void testGetPostsFromEmptyFile() throws IOException {
    String postFile = "./src/test/resources/empty-posts-test.csv";
    List<Post> posts = postService.getPostsFromFile(postFile);

    // Check same size
    long expectedSize = 0;
    long actualSize = posts.size();
    assertEquals(expectedSize, actualSize);
  }

  /**
   * Test find posts created in period time with given size
   */
  private void testFindPostsCreatedInWithSize(int n, int number, Period period) {
    List<Post> posts = mockPost.createListPostWithCreatedTimeIn(n, number, period);
    List<Post> result = postService.findPostsCreatedIn(posts, period);
    assertEquals(number, result.size());
  }

  private void testFindPostsByUserNameWithSize(int n, int number, String userName) {

  }


  /**
   * Test finding post have created in given period time ago
   */
  @Test
  public void testFindPostsCreatedIn() {
    // Test list post no have post created in today
    testFindPostsCreatedInWithSize(10, 0, Period.ofDays(1));

    // Test list post have one post created in today
    testFindPostsCreatedInWithSize(10, 1, Period.ofDays(1));

    // Test list post have ten post created in today
    testFindPostsCreatedInWithSize(10, 10, Period.ofDays(1));

    // Test list post no have post created in a week ago
    testFindPostsCreatedInWithSize(10, 0, Period.ofWeeks(1));

    // Test list post have one post created in a week ago
    testFindPostsCreatedInWithSize(10, 1, Period.ofWeeks(1));

    // Test list post have ten post created in a week ago
    testFindPostsCreatedInWithSize(10, 10, Period.ofWeeks(1));

    // Test list post no have post created in a month ago
    testFindPostsCreatedInWithSize(10, 0, Period.ofMonths(1));

    // Test list post have one post created in a month ago
    testFindPostsCreatedInWithSize(10, 1, Period.ofMonths(1));

    // Test list post have ten post created in a month ago
    testFindPostsCreatedInWithSize(10, 10, Period.ofMonths(1));
  }

  @Test
  public void findPostsByUserName() {
    List<User> users = mockUser.createListUserContainUsername(10, 5,"David");
    userService.print(users);
    List<Post> posts = mockPost.createListPostCreatedByUserName(10, 3, users, "David");
    postService.print(posts);
    List<Post> result = postService.findPostsByUserName(users, posts, "David");
    assertEquals(3, result.size());
  }

}