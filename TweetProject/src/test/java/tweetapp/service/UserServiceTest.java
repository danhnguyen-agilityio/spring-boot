package tweetapp.service;

import org.junit.BeforeClass;
import org.junit.Test;
import tweetapp.comparator.FirstNameComparator;
import tweetapp.comparator.LastNameComparator;
import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class UserServiceTest {
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
  public void getUsersFromFileError() throws IOException {
    String userFile = "./src/test/resources/user-not-found.csv";
    userService.getUsersFromFile(userFile);
  }

  /**
   * Test getting users from file
   */
  @Test
  public void getUsersFromFile() throws IOException {
    String userFile = "./src/test/resources/users-test.csv";
    List<User> users = userService.getUsersFromFile(userFile);

    // Check same size
    long expectedSize = 7;
    long actualSize = users.size();
    assertEquals(expectedSize, actualSize);

    // Check id of first user
    String expectedFirstUserId = "5b4c63aa170bb8185792506c";
    String actualFirstUserId = users.get(0).getId();
    assertEquals(expectedFirstUserId, actualFirstUserId);

    // Check birthday of first user
    LocalDateTime expectedFirstUserBirthday = DateUtil.convertStringToLocalDateTime("1974-01-15T12:47:20.430Z");
    LocalDateTime actualFirstUserBirthday = users.get(0).getBirthday();
    assertEquals(expectedFirstUserBirthday, actualFirstUserBirthday);
  }

  /**
   * Testing getting users from empty file
   * @throws IOException
   */
  @Test
  public void getUsersFromEmptyFile() throws IOException {
    String userFile = "./src/test/resources/empty-users-test.csv";
    List<User> users = userService.getUsersFromFile(userFile);

    // Check same size
    long expectedSize = 0;
    long actualSize = users.size();
    assertEquals(expectedSize, actualSize);
  }

  /**
   * Test counting all user
   */
  @Test
  public void testCountAllUser() {
    long expected = 7;
    long actual = users.size();
    assertEquals(expected, actual);
  }

  /**
   * Test counting all female users
   */
  @Test
  public void testCountFemaleUsers() {
    long expected = 3;
    long actual = userService.countFemaleUsers(users);
    assertThat(actual, is(expected));
  }

  /**
   * Test counting all male users
   */
  @Test
  public void testCountMaleUsers() {
    long expected = 3;
    long actual = userService.countMaleUsers(users);
    assertEquals(expected, actual);
  }

  /**
   * Test finding user have created in period ago from given fromDate
   */
  @Test
  public void findUsersCreatedIn() {
    // Test user created in today
    List<User> usersCreatedInToday = userService.findUsersCreatedIn(users, Period.ofDays(1),
        LocalDateTime.parse("2018-07-18T11:50:13.448"));
    assertEquals(2, usersCreatedInToday.size());
    assertEquals("5b4c63aa170bb81857925070", usersCreatedInToday.get(usersCreatedInToday.size() -1).getId());

    // Test user created in a week ago
    List<User> usersCreatedInWeek = userService.findUsersCreatedIn(users, Period.ofWeeks(1),
        LocalDateTime.parse("2018-07-18T11:50:13.448"));
    assertEquals(2, usersCreatedInWeek.size());
    assertEquals("5b4c63aa170bb81857925070", usersCreatedInWeek.get(usersCreatedInWeek.size() -1).getId());
  }

  /**
   * Test birthday user in given month
   */
  @Test
  public void birthdayInMonth() {
    boolean expected = true;
    boolean actual = userService.birthdayInMonth(mockUser, 11);
    assertEquals(expected, actual);
  }

  /**
   * Test finding users that have birthday same given month
   */
  @Test
  public void findUsersHaveBirthdayInMonth() {
    List<User> results = userService.findUsersHaveBirthdayInMonth(users, 11);

    // Check size
    long expectedSize = 3;
    long actualSize = results.size();
    assertEquals(expectedSize, actualSize);

    // Check first user birthday
    LocalDate expectedFirstUserBirthday = LocalDateTime.parse("1961-11-15T11:50:13.448").toLocalDate();
    LocalDate actualFirstUserBirthday = results.get(0).getBirthday().toLocalDate();
    assertEquals(expectedFirstUserBirthday, actualFirstUserBirthday);
  }

  /**
   * Test finding users with given first name
   */
  @Test
  public void findUsersWithFirstName() {
    String firstName = "David";
    List<User> results = userService.findUsersWithFirstName(users,  firstName);

    // Check size
    long expectedSize = 2;
    long actualSize = results.size();
    assertEquals(expectedSize, actualSize);

    // Check last user id
    String expectedLastUserId = "5b4c63aa170bb81857925072";
    String actualLastUserId = results.get(results.size() - 1).getId();
    assertEquals(expectedLastUserId, actualLastUserId);
  }

  /**
   * Test finding users have avatar
   */
  @Test
  public void findUsersHaveAvatar() {
    List<User> results = userService.findUsersHaveAvatar(users);

    // Check size
    long expectedSize = 5;
    long actualSize = results.size();
    assertEquals(expectedSize, actualSize);

    // Check last user id
    String expectedLastUserId = "5b4c63aa170bb81857925072";
    String actualLastUserId = results.get(results.size() -1).getId();
    assertEquals(expectedLastUserId, actualLastUserId);
  }

  /**
   * Test whether or not user have age grater given age
   */
  @Test
  public void haveAgeGreater() {
    boolean isGreater16 = userService.haveAgeGreater(mockUser, 16, true);
    assertEquals(true, isGreater16);

    boolean isLess24 = userService.haveAgeGreater(mockUser, 24, false);
    assertEquals(false, isLess24);
  }

  /**
   * Test finding users have age greater given age
   */
  @Test
  public void findUsersHaveAgeGreater() {
    // Test for finding user have age less 16
    List<User> usersHaveAgeLess16 = userService.findUsersHaveAgeGreater(users, 16, false);
    // Check size
    long expectedSizeUsersHaveAgeLess16 = 2;
    long actualSizeUsersHaveAgeLess16 = usersHaveAgeLess16.size();
    assertEquals(expectedSizeUsersHaveAgeLess16, actualSizeUsersHaveAgeLess16);
    // Check last user id
    String expectedLastUserIdHaveAgeLess16 = "5b4c63aa170bb81857925072";
    String actualLastUserIdHaveAgeLess16 = usersHaveAgeLess16.get(usersHaveAgeLess16.size() -1).getId();
    assertEquals(expectedLastUserIdHaveAgeLess16, actualLastUserIdHaveAgeLess16);

    // Test for finding user have age greater 30
    List<User> usersHaveAgeGreater30 = userService.findUsersHaveAgeGreater(users, 30, true);
    // Check size
    long expectedSizeUsersHaveAgeGreater30 = 2;
    long actualSizeUsersHaveAgeGreater30 = usersHaveAgeGreater30.size();
    assertEquals(expectedSizeUsersHaveAgeGreater30, actualSizeUsersHaveAgeGreater30);
    // Check last user id
    String expectedLastUserIdHaveAgeGreater30 = "5b4c63aa170bb8185792506d";
    String actualLastUserIdHaveAgeGreater30 = usersHaveAgeGreater30.get(usersHaveAgeLess16.size() -1).getId();
    assertEquals(expectedLastUserIdHaveAgeGreater30, actualLastUserIdHaveAgeGreater30);
  }

  /**
   * Test finding top female user order by first name
   */
  @Test
  public void findTopFemaleUserOrderByFirstName() {
    List<User> results = userService.findTopFemaleUserOrderBy(users, 5, new FirstNameComparator());
    // Check size
    long expectedSize = 3;
    long actualSize = results.size();
    assertEquals(expectedSize, actualSize);

    // Check top first name
    String expectedTopFirstName = "Kendra";
    String actualTopFirstName = results.get(0).getFirstName();
    assertEquals(expectedTopFirstName, actualTopFirstName);

    // Check bottom first name
    String expectedBottomFirstName = "Arlie";
    String actualBottomFirstName = results.get(results.size() - 1).getFirstName();
    assertEquals(expectedBottomFirstName, actualBottomFirstName);
  }

  /**
   * Test finding top female user order by last name
   */
  @Test
  public void findTopFemaleUserOrderByLastName() {
    List<User> results = userService.findTopFemaleUserOrderBy(users, 5, new LastNameComparator());
    // Check size
    long expectedSize = 3;
    long actualSize = results.size();
    assertEquals(expectedSize, actualSize);

    // Check last name of first user
    String expectedTopLastName = "Rose";
    String actualTopLasttName = results.get(0).getLastName();
    assertEquals(expectedTopLastName, actualTopLasttName);

    // Check last name of last user
    String expectedBottomLastName = "Corwin";
    String actualBottomLastName = results.get(results.size() - 1).getLastName();
    assertEquals(expectedBottomLastName, actualBottomLastName);
  }

  /**
   * Test finding top female users order by created post in given period
   */
  @Test
  public void findTopFemaleUsersOrderByCreatedPost() {
    // users have post created in today
    List<User> usersHaveCreatedPostToday =
        userService.findTopFemaleUsersOrderByCreatedPost(users, posts, 5, Period.ofDays(1),
            LocalDateTime.parse("2018-07-18T11:21:47.134"));
    // Check size
    assertEquals(2, usersHaveCreatedPostToday.size());
    // Check top user in result
    assertEquals("5b4c63aa170bb8185792506f", usersHaveCreatedPostToday.get(0).getId());
    // Check bottom user in result
    assertEquals("5b4c63aa170bb8185792506c", usersHaveCreatedPostToday.get(usersHaveCreatedPostToday.size() - 1).getId());

    // users have post created in a week ago
    List<User> usersHaveCreatedPostInWeek =
        userService.findTopFemaleUsersOrderByCreatedPost(users, posts, 5, Period.ofWeeks(1),
            LocalDateTime.parse("2018-07-18T11:21:47.134"));
    // Check size
    assertEquals(3, usersHaveCreatedPostInWeek.size());
    // Check top user in result
    assertEquals("5b4c63aa170bb8185792506f", usersHaveCreatedPostInWeek.get(0).getId());
    // Check bottom user in result
    assertEquals("5b4c63aa170bb81857925070", usersHaveCreatedPostInWeek.get(usersHaveCreatedPostInWeek.size() - 1).getId());
  }

  /**
   * Test whether or no user contains given user name
   */
  @Test
  public void containsUsername() {
    assertEquals(true, UserService.containsUsername(mockUser, "Jerrell") );
    assertEquals(false, UserService.containsUsername(mockUser, "David"));
  }

}