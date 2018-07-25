package tweetapp.service;

import org.junit.BeforeClass;
import org.junit.Test;
import tweetapp.comparator.CreatedPostComparator;
import tweetapp.comparator.FirstNameComparator;
import tweetapp.comparator.LastNameComparator;
import tweetapp.mock.MockPost;
import tweetapp.mock.MockUser;
import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.DateUtil;
import tweetapp.util.StreamUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class UserServiceTest {
  private static List<User> users;
  private static List<Post> posts;
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
   * Test count all users with given size n
   * @param n Size of list
   */
  private void testCountAllUserWithSize(int n) {
    long actual = mockUser.createList(n).size();
    assertEquals(n, actual);
  }

  /**
   * Test count users by gender with list n element and have given number gender
   * @param n Size of list
   * @param number Number given gender in list
   * @param gender Gender of user
   */
  private void testCountUsersByGenderWithSize(int n,  int number, Gender gender) {
    List<User> users = mockUser.createListUserWithGender(n, number, gender);
    long actual = userService.countUsersByGender(users, gender);
    assertEquals(number, actual);
  }

  /**
   * Test find users created in period time with given size
   * @param n Size of list
   * @param number Number user created in period time
   * @param period Period time
   */
  private void testFindUsersCreatedInWithSize(int n, int number, Period period) {
    List<User> users = mockUser.createListUserWithCreatedTimeIn(n, number, period);
    List<User> result = userService.findUsersCreatedIn(users, period);
    assertEquals(number, result.size());
  }

  /**
   * Test find user with birthday in month with given size
   * @param n Size of list
   * @param number Number user have birthday in given month
   * @param month Month of year
   */
  private void testFindUserWithBirthdayInMonthWithSize(int n, int number, int month) {
    List<User> users = mockUser.createListUserWithBirthdayInMonth(n, number, month);
    List<User> result = userService.findUsersHaveBirthdayInMonth(users, month);
    assertEquals(number, result.size());
  }

  /**
   * Test find users with first name with given size
   * @param n Size of list
   * @param number Number user have given first name
   * @param firstName First name of user
   */
  private void testFindUsesWithFirstNameWithSize(int n, int number, String firstName) {
    List<User> users = mockUser.createListUserWithFirstName(n, number, firstName);
    List<User> result = userService.findUsersWithFirstName(users, firstName);
    assertEquals(number, result.size());
  }

  /**
   * Test find users have avatar with given size
   * @param n Size of list
   * @param number Number user have avatar
   */
  private void testFindUsesHaveAvatarWithSize(int n, int number) {
    List<User> users = mockUser.createListUserHaveAvatar(n, number);
    List<User> result = userService.findUsersHaveAvatar(users);
    assertEquals(number, result.size());
  }

  /**
   * Test find users have age greater given age
   * @param n Size of list
   * @param number Number users have age greater given age
   * @param age Age to compare
   * @param isGreater Flag used to check age greater than or less than given age
   */
  private void testFindUsersHaveAgeGreaterWithSize(int n, int number, int age, boolean isGreater) {
    List<User> users = mockUser.createListUserHaveAgeGreater(n, number, age, isGreater);
    List<User> result = userService.findUsersHaveAgeGreater(users, age, isGreater);
    assertEquals(number, result.size());
  }

  /**
   * Test find top female user order by given comparator
   * @param n Size of list
   * @param number Number gender user
   * @param gender Gender of user
   * @param maxSize max size of result
   * @param comparator Comparator used to compare
   */
  private void testFindTopFemaleUserOrderBy(int n, int number, Gender gender, int maxSize, Comparator comparator) {
    List<User> users = mockUser.createListUserWithGender(n, number, gender);
    List<User> result = userService.findTopFemaleUserOrderBy(users, maxSize, comparator);
    assertEquals(Math.min(number, maxSize), result.size());

    // Check order
    boolean isSorted = StreamUtil.isSorted(result, comparator);
    assertTrue(isSorted);
  }

  /**
   * Test find top female users have posted created in period time order by time created post
   * @param n Size of list user
   * @param number Number user have post created in period time
   * @param period Period time
   * @param maxSize Max size of result
   */
  private void testFindTopFemaleUsersOrderByCreatedPost(int n, int number, Period period, int maxSize) {
    List<User> users = mockUser.createListUserWithGender(n, number, Gender.FEMALE);
    List<Post> posts = mockPost.createListPostCreatedByFemaleUserAndPeriodTime(users, number, period);
    List<User> result = userService.findTopFemaleUsersOrderByCreatedPost(users, posts, maxSize, period);
    assertEquals(Math.min(number, maxSize), result.size());

    // Check order
    List<Post> postsCreatedByFemale = result.stream()
        .map(user -> postService.findPostBy(posts, user.getId()))
        .collect(Collectors.toList());
    boolean isSorted = StreamUtil.isSorted(postsCreatedByFemale, new CreatedPostComparator().reversed());
    assertTrue(isSorted);
  }

  /**
   * Test count all user
   */
  @Test
  public void testCountAllUser() {
    // Test empty list
    testCountAllUserWithSize(0);

    // Test list user with size 1
    testCountAllUserWithSize(1);

    // Test list user with size 7
    testCountAllUserWithSize(7);
  }

  /**
   * Test count female user
   */
  @Test
  public void testCountFemaleUser() {
    // Test list user no have female user
    testCountUsersByGenderWithSize(10, 0, Gender.FEMALE);

    // Test list user have one female user
    testCountUsersByGenderWithSize(10, 1, Gender.FEMALE);

    // Test list user have 10 female user
    testCountUsersByGenderWithSize(10, 10, Gender.FEMALE);
  }

  /**
   * Test count male user
   */
  @Test
  public void testCountMaleUser() {
    // Test list user no have male user
    testCountUsersByGenderWithSize(10, 0, Gender.MALE);

    // Test list user have one male user
    testCountUsersByGenderWithSize(10, 1, Gender.MALE);

    // Test list user have 10 male user
    testCountUsersByGenderWithSize(10, 10, Gender.MALE);
  }

  /**
   * Test find users created in period time
   */
  @Test
  public void testFindUsersCreatedIn() {
    //Test list user no have user created in today
    testFindUsersCreatedInWithSize(10, 0, Period.ofDays(1));

    //Test list user have one user created in today
    testFindUsersCreatedInWithSize(10, 1, Period.ofDays(1));

    //Test list user have 10 user created in today
    testFindUsersCreatedInWithSize(10, 9, Period.ofDays(1));

    //Test list user no have user created in a week ago
    testFindUsersCreatedInWithSize(10, 0, Period.ofWeeks(1));

    //Test list user have one user created in a week ago
    testFindUsersCreatedInWithSize(10, 1, Period.ofWeeks(1));

    //Test list user have 10 user created in a week ago
    testFindUsersCreatedInWithSize(10, 9, Period.ofWeeks(1));

    //Test list user no have user created in a month ago
    testFindUsersCreatedInWithSize(10, 0, Period.ofMonths(1));

    //Test list user have one user created in a month ago
    testFindUsersCreatedInWithSize(10, 1, Period.ofMonths(1));

    //Test list user have 10 user created in a month ago
    testFindUsersCreatedInWithSize(10, 9, Period.ofMonths(1));
  }

  /**
   * Test find user have birthday in month
   */
  @Test
  public void testFindUsersHaveBirthdayInMonth() {
    // Test list user no have user with birthday in month 11
    testFindUserWithBirthdayInMonthWithSize(10, 0, 11);

    // Test list user have one user with birthday in month 11
    testFindUserWithBirthdayInMonthWithSize(10, 1, 11);

    // Test list user have 10user with birthday in month 11
    testFindUserWithBirthdayInMonthWithSize(10, 10, 11);
  }

  /**
   * Test find users with first name
   */
  @Test
  public void testFindUsesWithFirstName() {
    // Test list user no have user with first name is DaVid
    testFindUsesWithFirstNameWithSize(10, 0, "David");

    // Test list user have one user with first name is DaVid
    testFindUsesWithFirstNameWithSize(10, 1, "David");

    // Test list user have ten users with first name is DaVid
    testFindUsesWithFirstNameWithSize(10, 10, "David");
  }

  /**
   * Test find users have avatar
   */
  @Test
  public void testFindUsersHaveAvatar() {
    // Test list user no have user that have avatar url
    testFindUsesHaveAvatarWithSize(10, 0);

    // Test list user have one user that have avatar url
    testFindUsesHaveAvatarWithSize(10, 1);

    // Test list user have 10 user that have avatar url
    testFindUsesHaveAvatarWithSize(10, 10);
  }

  /**
   * Test find users have age greater than or less than
   */
  @Test
  public void testFindUsersHaveAgeGreater() {
    // Test list user no have user that have age greater than 18
    testFindUsersHaveAgeGreaterWithSize(10, 0, 18, true);

    // Test list user have one user that have age greater than 18
    testFindUsersHaveAgeGreaterWithSize(10, 1, 18, true);

    // Test list user have 10 user that have age greater than 18
    testFindUsersHaveAgeGreaterWithSize(10, 10, 18, true);

    // Test list user no have user that have age less than than 18
    testFindUsersHaveAgeGreaterWithSize(10, 0, 18, false);

    // Test list user have one user that have age less than 18
    testFindUsersHaveAgeGreaterWithSize(10, 1, 18, false);

    // Test list user have 10 user that have age less than 18
    testFindUsersHaveAgeGreaterWithSize(10, 10, 18, false);
  }

  /**
   * Test find top female users order by first name
   */
  @Test
  public void testFindTopFemaleUserOrderByFirstName() {
    // Test get top 10 female user of list user no have female user
    testFindTopFemaleUserOrderBy(10, 0, Gender.FEMALE, 10, new FirstNameComparator());

    // Test get top 10 female user of list user have 5 female user
    testFindTopFemaleUserOrderBy(10, 5, Gender.FEMALE, 10, new FirstNameComparator());

    // Test get top 5 female user of list user have 10 female user
    testFindTopFemaleUserOrderBy(10, 10, Gender.FEMALE, 5, new FirstNameComparator());

    // Test get top 10 female user of list user have 10 female user
    testFindTopFemaleUserOrderBy(10, 10, Gender.FEMALE, 10, new FirstNameComparator());
  }

  /**
   * Test find top female users order by last name
   */
  @Test
  public void testFindTopFemaleUserOrderByLastName() {
    // Test get top 10 female user of list user no have female user
    testFindTopFemaleUserOrderBy(10, 0, Gender.FEMALE, 10, new LastNameComparator());

    // Test get top 10 female user of list user have 5 female user
    testFindTopFemaleUserOrderBy(10, 5, Gender.FEMALE, 10, new LastNameComparator());

    // Test get top 5 female user of list user have 10 female user
    testFindTopFemaleUserOrderBy(10, 10, Gender.FEMALE, 5, new LastNameComparator());

    // Test get top 10 female user of list user have 10 female user
    testFindTopFemaleUserOrderBy(10, 10, Gender.FEMALE, 10, new LastNameComparator());
  }

  /**
   * Test find top female users by having posts within period time, order by time created post
   */
  @Test
  public void testFindTopFemaleUsersOrderByCreatedPost() {
    // Test list user have 0 female user have post created in today
    testFindTopFemaleUsersOrderByCreatedPost(20, 0, Period.ofDays(1), 10);

    // Test list user have 10 female user have post created in today
    testFindTopFemaleUsersOrderByCreatedPost(20, 10, Period.ofDays(1), 10);

    // Test list user have 15 female user have post created in today
    testFindTopFemaleUsersOrderByCreatedPost(20, 15, Period.ofDays(1), 10);

    // Test list user have 0 female user have post created in a week
    testFindTopFemaleUsersOrderByCreatedPost(20, 0, Period.ofWeeks(1), 10);

    // Test list user have 10 female user have post created in a week
    testFindTopFemaleUsersOrderByCreatedPost(20, 10, Period.ofWeeks(1), 10);

    // Test list user have 15 female user have post created in a week
    testFindTopFemaleUsersOrderByCreatedPost(20, 15, Period.ofWeeks(1), 10);

    // Test list user have 0 female user have post created in a month
    testFindTopFemaleUsersOrderByCreatedPost(20, 0, Period.ofMonths(1), 10);

    // Test list user have 10 female user have post created in a month
    testFindTopFemaleUsersOrderByCreatedPost(20, 10, Period.ofMonths(1), 10);

    // Test list user have 15 female user have post created in a month
    testFindTopFemaleUsersOrderByCreatedPost(25, 15, Period.ofMonths(1), 10);
  }

}