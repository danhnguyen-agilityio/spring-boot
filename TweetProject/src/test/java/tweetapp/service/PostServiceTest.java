package tweetapp.service;

import org.junit.BeforeClass;
import org.junit.Test;
import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static org.junit.Assert.*;

public class PostServiceTest {

  public static User mockUser;
  public static List<User> users;
  public static List<Post> posts;
  public static UserService userService;
  public static PostService postService;

  @BeforeClass
  public static void beforeClass() throws IOException {

    userService = new UserService();
    postService = new PostService();

    mockUser = new User("5b4c63aa170bb8185792506c", "Jerrell-Herman",
        "Jerrell", "Herman", "https://s3.amazonaws.com./mage.jpg",
        "Alexa Volkman IV", "orville.bogan@yahoo.com","1-330-949-7777 x444",
        "Suite 935 28068 Oswaldo Manors", Gender.MALE, LocalDateTime.parse("1993-11-07T12:47:20.430"),
        "Quia aut commodi", LocalDateTime.parse("2018-07-16T09:21:45.492"),
        LocalDateTime.parse("2018-07-16T09:21:45.492"), "0");

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
   * Test finding post have created in given period ago from given fromDate
   */
  @Test
  public void testFindPostsCreatedIn() {
    // Find post in today
    List<Post> postCreatedInToday = postService.findPostsCreatedIn(posts, Period.ofDays(1),
        LocalDateTime.parse("2018-07-18T09:21:47.134"));
    // Check size
    assertEquals(3, postCreatedInToday.size());
    // Check id of first post
    assertEquals("5b4c63ab170bb81857925455", postCreatedInToday.get(0).getId());
    // Check if of last post
    assertEquals("5b4c63ab170bb8185792545b", postCreatedInToday.get(postCreatedInToday.size() - 1).getId());

    // Find post in a week ago
    List<Post> postCreatedInWeek = postService.findPostsCreatedIn(posts, Period.ofWeeks(1),
        LocalDateTime.parse("2018-07-18T09:21:47.134"));
    // Check size
    assertEquals(6, postCreatedInWeek.size());
    // Check id of first post
    assertEquals("5b4c63ab170bb81857925455", postCreatedInWeek.get(0).getId());
    // Check if of last post
    assertEquals("5b4c63ab170bb8185792545b", postCreatedInWeek.get(postCreatedInWeek.size() - 1).getId());
  }

  /**
   * Test finding posts by given userName
   */
  @Test
  public void TestPostService() {
    List<Post> results = postService.findPostsByUserName(users, posts, "David");

    // Check size
    assertEquals(5, results.size());

    // Check first post id
    assertEquals("5b4c63ab170bb81857925455", results.get(0).getId());

    // Check last post id
    assertEquals("5b4c63ab170bb81857925458", results.get(results.size() - 1).getId());
  }



}