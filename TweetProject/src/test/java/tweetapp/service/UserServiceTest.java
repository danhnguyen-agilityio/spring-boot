package tweetapp.service;

import org.junit.BeforeClass;
import org.junit.Test;
import tweetapp.comparator.FirstNameComparator;
import tweetapp.comparator.LastNameComparator;
import tweetapp.mock.MockUser;
import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
  public static User user;
  public static List<User> users;
  public static List<Post> posts;
  public static UserServiceImpl userService;
  public static PostServiceImpl postService;
  public static MockUser mockUser;

  @BeforeClass
  public static void beforeClass() throws IOException {

    userService = new UserServiceImpl();
    postService = new PostServiceImpl();
    mockUser = new MockUser();

    user = new User("5b4c63aa170bb8185792506c", "Jerrell-Herman",
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
  public void testGetUsersFromFileError() throws IOException {
    String userFile = "./src/test/resources/user-not-found.csv";
    userService.getUsersFromFile(userFile);
  }

  /**
   * Test getting users from file
   */
  @Test
  public void testGetUsersFromFile() throws IOException {
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
  public void testGetUsersFromEmptyFile() throws IOException {
    String userFile = "./src/test/resources/empty-users-test.csv";
    List<User> users = userService.getUsersFromFile(userFile);

    // Check same size
    long expectedSize = 0;
    long actualSize = users.size();
    assertEquals(expectedSize, actualSize);
  }

  /**
   * Test count equal 0 if list empty
   */
  @Test
  public void testCountEqual0IfListEmpty() {
    long actual = mockUser.createList(0).size();
    assertEquals(0, actual);
  }

  /**
   * Test count equal 1 if list have 1 element
   */
  @Test
  public void testCountEqual1IfListHave1Users() {
    long actual = mockUser.createList(1).size();
    assertEquals(1, actual);
  }

  /**
   * Test count equal 10 if list have 10 element
   */
  @Test
  public void testCountEqual1IfListHave10Users() {
    long actual = mockUser.createList(10).size();
    assertEquals(10, actual);
  }

  /**
   * Test count equal 0 if list no have female user
   */
  @Test
  public void testCountEqual0IfListNoHaveFemaleUser() {
    List<User> users = mockUser.createList0FemaleUser();
    long actual = userService.countFemaleUsers(users);
    assertEquals(0, actual);
  }

  /**
   * Test count equal 0 if list have one female user
   */
  @Test
  public void testCountEqual1IfListHave1FemaleUser() {
    List<User> users = mockUser.createList1FemaleUser();
    long actual = userService.countFemaleUsers(users);
    assertEquals(1, actual);
  }

  /**
   * Test count equal 10 if list have 10 female user
   */
  @Test
  public void testCountEqual10IfListNoHave10FemaleUser() {
    List<User> users = mockUser.createList10FemaleUser();
    long actual = userService.countFemaleUsers(users);
    assertEquals(10, actual);
  }

  /**
   * Test count equal 0 if list no have male user
   */
  @Test
  public void testCountEqual0IfListNoHaveMaleUser() {
    List<User> users = mockUser.createList0MaleUser();
    long actual = userService.countMaleUsers(users);
    assertEquals(0, actual);
  }

  /**
   * Test count equal 0 if list have one male user
   */
  @Test
  public void testCountEqual1IfListHave1MaleUser() {
    List<User> users = mockUser.createList1MaleUser();
    long actual = userService.countMaleUsers(users);
    assertEquals(1, actual);
  }

  /**
   * Test count equal 10 if list have 10 male user
   */
  @Test
  public void testCountEqual10IfListNoHave10MaleUser() {
    List<User> users = mockUser.createList10MaleUser();
    long actual = userService.countMaleUsers(users);
    assertEquals(10, actual);
  }

  /**
   * Test no users created in today
   */
  @Test
  public void testNoUsersCreatedInToday() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    String[] times = {
        "2018-07-11T09:21:45", "2018-07-12T09:21:45", "2018-07-16T09:21:45", "2018-07-17T22:21:45",
        "2018-07-12T09:21:45", "2018-07-13T09:21:45", "2018-07-17T09:21:45"
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofDays(1), fromDate);
    assertEquals(0, result.size());
  }

  /**
   * Test 1 user created in today
   */
  @Test
  public void testOneUserCreatedInToday() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    // List contain one time created in today
    String[] times = {
        "2018-07-11T09:21:45", "2018-07-12T09:21:45", "2018-07-16T09:21:45", "2018-07-18T22:21:45",
        "2018-07-12T09:21:45", "2018-07-13T09:21:45", "2018-07-17T09:21:45"
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofDays(1), fromDate);
    assertEquals(1, result.size());
  }

  /**
   * Test 7 user created in today
   */
  @Test
  public void testSevenUserCreatedInToday() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    // List contain one time created in today
    String[] times = {
        "2018-07-18T23:21:45", "2018-07-18T09:21:45", "2018-07-18T00:00:00", "2018-07-18T22:21:45",
        "2018-07-18T09:21:45", "2018-07-18T03:21:45", "2018-07-18T09:21:45"
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofDays(1), fromDate);
    assertEquals(7, result.size());
  }

  /**
   * Test no users created in a week
   */
  @Test
  public void testNoUsersCreatedInAWeek() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    String[] times = {
        "2018-07-11T09:21:45", "2018-07-10T09:21:45", "2018-07-09T09:21:45", "2018-07-08T22:21:45",
        "2018-07-07T09:21:45", "2018-07-06T09:21:45", "2018-07-05T09:21:45"
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofWeeks(1), fromDate);
    assertEquals(0, result.size());
  }

  /**
   * Test one user created in a week
   */
  @Test
  public void testOneUserCreatedInAWeek() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    String[] times = {
        "2018-07-12T00:21:45", "2018-07-10T09:21:45", "2018-07-09T09:21:45", "2018-07-08T22:21:45",
        "2018-07-07T09:21:45", "2018-07-06T09:21:45", "2018-07-05T09:21:45"
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofWeeks(1), fromDate);
    assertEquals(1, result.size());
  }

  /**
   * Test seven user created in a week
   */
  @Test
  public void testSevenUserCreatedInAWeek() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    String[] times = {
        "2018-07-12T00:21:45", "2018-07-13T09:21:45", "2018-07-14T09:21:45", "2018-07-15T22:21:45",
        "2018-07-16T09:21:45", "2018-07-17T09:21:45", "2018-07-18T09:21:45"
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofWeeks(1), fromDate);
    assertEquals(7, result.size());
  }

  /**
   * Test no user created in a month
   */
  @Test
  public void testNoUserCreatedInAMonth() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    String[] times = {
        "2018-06-18T00:21:45", "2018-06-18T00:21:45", "2018-06-18T00:21:45", "2018-06-18T00:21:45",
        "2018-06-18T00:21:45", "2018-06-18T00:21:45", "2018-06-18T00:21:45",
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofMonths(1), fromDate);
    assertEquals(0, result.size());
  }

  /**
   * Test one user created in a month
   */
  @Test
  public void testOneUserCreatedInAMonth() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    String[] times = {
        "2018-06-19T00:21:45", "2018-06-18T23:59:59", "2018-06-18T00:21:45", "2018-06-18T00:21:45",
        "2018-06-18T00:21:45", "2018-06-18T00:21:45", "2018-06-18T00:21:45",
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofMonths(1), fromDate);
    assertEquals(1, result.size());
  }

  /**
   * Test seven user created in a month
   */
  @Test
  public void testSevenUserCreatedInAMonth() {
    LocalDateTime fromDate = LocalDateTime.parse("2018-07-18T09:21:45.492");

    String[] times = {
        "2018-06-19T00:21:45", "2018-06-20T23:59:59", "2018-06-21T00:21:45", "2018-06-22T00:21:45",
        "2018-06-23T00:21:45", "2018-06-24T00:21:45", "2018-06-25T00:21:45",
    };
    List<User> users = mockUser.createListUserWithCreatedAt(times);
    List<User> result = userService.findUsersCreatedIn(users, Period.ofMonths(1), fromDate);
    assertEquals(7, result.size());
  }

  /**
   * Test no user have birthday in month
   */
  @Test
  public void testNoUsersHaveBirthdayInMonth() {
    int month = 11;

    String[] times = {
        "2018-06-18T00:21:45", "2018-07-18T00:21:45", "2018-08-18T00:21:45", "2018-09-18T00:21:45",
        "2018-10-18T00:21:45", "2018-12-18T00:21:45", "2018-05-18T00:21:45",
    };
    List<User> users = mockUser.createListUserWithBirthday(times);
    List<User> result = userService.findUsersHaveBirthdayInMonth(users, month);
    assertEquals(0, result.size());
  }

  /**
   * Test one user have birthday in month
   */
  @Test
  public void testOneUserHaveBirthdayInMonth() {
    int month = 11;

    String[] times = {
        "2018-06-18T00:21:45", "2018-07-18T00:21:45", "2018-08-18T00:21:45", "2018-09-18T00:21:45",
        "2018-10-18T00:21:45", "2018-12-18T00:21:45", "2018-11-18T00:21:45",
    };
    List<User> users = mockUser.createListUserWithBirthday(times);
    List<User> result = userService.findUsersHaveBirthdayInMonth(users, month);
    assertEquals(1, result.size());
  }

  /**
   * Test seven user have birthday in month
   */
  @Test
  public void testSevenUserHaveBirthdayInMonth() {
    int month = 11;

    String[] times = {
        "2018-11-18T00:21:45", "2018-11-18T00:21:45", "2018-11-18T00:21:45", "2018-11-18T00:21:45",
        "2018-11-18T00:21:45", "2018-11-18T00:21:45", "2018-11-18T00:21:45",
    };
    List<User> users = mockUser.createListUserWithBirthday(times);
    List<User> result = userService.findUsersHaveBirthdayInMonth(users, month);
    assertEquals(7, result.size());
  }

  /**
   * Test finding users with given first name
   */
  @Test
  public void testFindUsersWithFirstName() {
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
  public void testFindUsersHaveAvatar() {
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
  public void testHaveAgeGreater() {
    boolean isGreater16 = UserService.haveAgeGreater(user, 16, true);
    assertEquals(true, isGreater16);

    boolean isLess24 = UserService.haveAgeGreater(user, 24, false);
    assertEquals(false, isLess24);
  }

  /**
   * Test finding users have age greater given age
   */
  @Test
  public void testFindUsersHaveAgeGreater() {
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
  public void testFindTopFemaleUserOrderByFirstName() {
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
  public void testFindTopFemaleUserOrderByLastName() {
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
  public void testFindTopFemaleUsersOrderByCreatedPost() {
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
  public void testContainsUsername() {
    assertEquals(true, UserService.containsUsername(user, "Jerrell") );
    assertEquals(false, UserService.containsUsername(user, "David"));
  }

}