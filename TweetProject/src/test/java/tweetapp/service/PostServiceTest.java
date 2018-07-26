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

  private static UserServiceImpl userService;
  private static PostServiceImpl postService;
  private static MockUser mockUser;
  private static MockPost mockPost;

  @BeforeClass
  public static void beforeClass() throws IOException {

    userService = new UserServiceImpl();
    postService = new PostServiceImpl();
    mockUser = new MockUser();
    mockPost = new MockPost();

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

    // Check id of last post
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
   * @param n Size of list
   * @param number Number post created in period time
   * @param period Period time
   */
  private void testFindPostsCreatedInWithSize(int n, int number, Period period) {
    List<Post> posts = mockPost.createListPostWithCreatedTimeIn(n, number, period);
    List<Post> result = postService.findPostsCreatedIn(posts, period);
    assertEquals(number, result.size());
  }

  /**
   * Test find posts created by user name
   * @param n Size of list
   * @param numberPost Number post created given user name
   * @param numberUser Number user contain given user name
   * @param userName User name of user
   */
  private void testFindPostsByUserNameWithSize(int n, int numberPost, int numberUser, String userName) {
    List<User> users = mockUser.createListUserContainUsername(n, numberUser,userName);
    List<Post> posts = mockPost.createListPostCreatedByUserName(n, numberPost, users, userName);
    List<Post> result = postService.findPostsByUserName(users, posts, userName);
    assertEquals(numberPost, result.size());
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
    // Test list post no have post that created by David
    testFindPostsByUserNameWithSize(10, 0, 5, "David");

    // Test list post have one post that created by David
    testFindPostsByUserNameWithSize(10, 1, 5, "David");

    // Test list post have 10 post that created by David
    testFindPostsByUserNameWithSize(10, 10, 5, "David");

    // Test list post have 10 post that created by David
    testFindPostsByUserNameWithSize(10, 5, 1, "David");
  }

}