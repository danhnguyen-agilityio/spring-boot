package tweetapp.service;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class UserServiceTest {
  public static User mockUser;
  public static List<User> users;
  public static List<Post> posts;

  @BeforeClass
  public static void beforeClass() throws IOException {

    mockUser = new User("5b4c63aa170bb8185792506c", "Jerrell-Herman",
        "Jerrell", "Herman", "https://s3.amazonaws.com./mage.jpg",
        "Alexa Volkman IV", "orville.bogan@yahoo.com","1-330-949-7777 x444",
        "Suite 935 28068 Oswaldo Manors", Gender.MALE, LocalDateTime.parse("1993-11-07T12:47:20.430"),
        "Quia aut commodi", LocalDateTime.parse("2018-07-16T09:21:45.492"),
        LocalDateTime.parse("2018-07-16T09:21:45.492"), "0");

    String userFile ="./src/test/resources/users-test.csv";
    users = UserService.getUsersFromFile(userFile);
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test getting users from file
   * @throws IOException
   */
  @Test
  public void getUsersFromFile() {

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
    long expected = 2;
    long actual = UserService.countFemaleUsers(users);
    assertThat(actual, is(expected));
  }

  /**
   * Test counting all male users
   */
  @Test
  public void testCountMaleUsers() {
    long expected = 3;
    long actual = UserService.countMaleUsers(users);
    assertEquals(expected, actual);
  }

  /**
   * Test birthday user in given month
   */
  @Test
  public void birthdayInMonth() {
    boolean expected = true;
    boolean actual = UserService.birthdayInMonth(mockUser, 11);
    assertEquals(expected, actual);
  }

  /**
   * Test finding users that have birthday same given month
   */
  @Test
  public void findUsersHaveBirthdayInMonth() {
    List<User> results = UserService.findUsersHaveBirthdayInMonth(users, 11);

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
    List<User> results = UserService.findUsersWithFirstName(users,  firstName);

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
    List<User> results = UserService.findUsersHaveAvatar(users);

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
    boolean isGreater16 = UserService.haveAgeGreater(mockUser, 16, true);
    assertEquals(true, isGreater16);

    boolean isLess24 = UserService.haveAgeGreater(mockUser, 24, false);
    assertEquals(false, isLess24);
  }

  /**
   * Test finding users have age greater given age
   */
  @Test
  public void findUsersHaveAgeGreater() {
    // Test for finding user have age less 16
    List<User> usersHaveAgeLess16 = UserService.findUsersHaveAgeGreater(users, 16, false);
    // Check size
    long expectedSizeUsersHaveAgeLess16 = 2;
    long actualSizeUsersHaveAgeLess16 = usersHaveAgeLess16.size();
    assertEquals(expectedSizeUsersHaveAgeLess16, actualSizeUsersHaveAgeLess16);
    // Check last user id
    String expectedLastUserIdHaveAgeLess16 = "5b4c63aa170bb81857925072";
    String actualLastUserIdHaveAgeLess16 = usersHaveAgeLess16.get(usersHaveAgeLess16.size() -1).getId();
    assertEquals(expectedLastUserIdHaveAgeLess16, actualLastUserIdHaveAgeLess16);

    // Test for finding user have age greater 30
    List<User> usersHaveAgeGreater30 = UserService.findUsersHaveAgeGreater(users, 30, true);
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
   * Test finding top female user order by
   */
  




}