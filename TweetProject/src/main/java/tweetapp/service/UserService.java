package tweetapp.service;

import tweetapp.comparator.CreatedPostComparator;
import tweetapp.constant.App;
import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * UserService implement statistic on list user
 */
public class UserService {

  // FIXME:: Only print necessary properties
  /**
   * Print all info list users
   * @param users List user
   */
  public static void print(List<User> users) {
    IntStream.range(0, users.size())
        .forEach(idx -> {
          System.out.println("-------------------------------");
          System.out.print((idx + 1) + ". ");
          System.out.println(users.get(idx));
        });
  }

  // FIXME:: Consider type modifier to test this method
  /**
   * Process user data and return list user
   * @param lines Streams lines of file
   * @return Return list user
   */
  public static List<User> processUserData(Stream<String> lines) {
    return lines.skip(1)
        .map(line -> {
          // Split line to array data
          String[] data = line.split(App.SPLIT_BY_CSV);
          String id = StringUtil.substringBetween(data[0], "\"");
          String username = data[2];
          String firstName = data[3];
          String lastName = data[4];
          String avatarUrl = data[6];
          String nickname = data[7];
          String email = data[8];
          String phone = data[9];
          String address = data[10];
          Gender gender = Gender.valueOf(data[13]);
          LocalDateTime birthday = DateUtil.convertStringToLocalDateTime(data[14]);
          String description =  data[15];
          LocalDateTime createdAt = DateUtil.convertStringToLocalDateTime(data[16]);
          LocalDateTime modifiedAt = DateUtil.convertStringToLocalDateTime(data[17]);
          String version = data[18];
          return new User(id, username, firstName, lastName, avatarUrl, nickname, email, phone, address, gender,
              birthday, description, createdAt, modifiedAt, version);
        })
        .collect(toList());
  }

  /**
   * Get all list user from csv file
   * @return All info users
   * @throws IOException occur when file not found
   */
  public static List<User> getUsersFromFile(String fileName) throws IOException {
    return FileUtil.readFile(fileName, UserService::processUserData);
  }

  /**
   * Count all users
   * @param users List user
   * @return total users
   */
  public static long countAllUser(List<User> users) {
    return users.size();
  }

  /**
   * Count all female users
   * @param users List user
   * @return total female users
   */
  public static long countFemaleUsers(List<User> users) {
    return users.stream().filter(User::isFemale).count();
  }

  /**
   * Count all male users
   * @param users List user
   * @return total male users
   */
  public static long countMaleUsers(List<User> users) {
    return users.stream().filter(User::isMale).count();
  }

  // FIXME:: Revise method to implement test
  /**
   * Find user have created in period ago
   * @param users List user
   * @param period Period time
   * @return List user
   */
  public static List<User> findUsersCreatedIn(List<User> users, Period period) {
    return users.stream()
        .filter(user -> UserService.createdWithinNumberDaysAgo(user, period))
        .collect(Collectors.toList());
  }

  // FIXME:: Revise method to implement test
  /**
   * Check whether user have created within a specific days ago
   * @param user List user
   * @param period Period time
   * @return true if user have created within a specific days ago and false if other
   */
  private static boolean createdWithinNumberDaysAgo(User user, Period period) {
    return DateUtil.withinNumberDaysAgo(user.getCreatedAt(), period);
  }

  /**
   * Check whether user have birthday within given month
   * @param user List user
   * @return true if user have birthday within given month and false if other
   */
  public static boolean birthdayInMonth(User user, int month) {
    return user.getBirthday().getMonthValue() == month;
  }

  /**
   * Find users have birthday within given month
   * @param users List user
   * @param month Month is used to find
   * @return List users have birthday within given month
   */
  public static List<User> findUsersHaveBirthdayInMonth(List<User> users, int month) {
    return users.stream()
        .filter(user -> UserService.birthdayInMonth(user, month))
        .collect(toList());
  }

  // FIXME:: consider put method in here or User class
  /**
   * Test whether user have given firstName
   * @param user User need check
   * @param firstName First name of user
   * @return true if user have given firstName and false if other
   */
  public static boolean haveFirstNameWith(User user, String firstName) {
    return user.getFirstName().equalsIgnoreCase(firstName);
  }

  /**
   * Find users have specific first name
   * @param users List user
   * @param firstName First name need to find
   * @return List users have specific first name
   */
  public static List<User> findUsersWithFirstName(List<User> users, String firstName) {
    return users.stream()
        .filter(user -> UserService.haveFirstNameWith(user, firstName))
        .collect(toList());
  }

  /**
   * Check whether user have avatar
   * @param user List user
   * @return true if user have avatar
   */
  public static boolean haveAvatar(User user) {
    return !("".equals(user.getAvatarUrl()));
  }

  /**
   * Find users have avatar
   * @param users List user
   * @return List users have avatar
   */
  public static List<User> findUsersHaveAvatar(List<User> users) {
    return users.stream()
        .filter(UserService::haveAvatar)
        .collect(toList());
  }

  // FIXME:: Should write unit test this method
  /**
   * Check whether user have age greater given age
   * @param user User need to check
   * @param age Given age is used to compare
   * @param isGreater Flag to check user have age smaller or greater
   * @return true if user have age greater given age
   */
  public static boolean haveAgeGreater(User user, int age, boolean isGreater) {
    int userAge = AgeCalculator.calculateAge(user.getBirthday().toLocalDate(), LocalDate.now());
    return isGreater ? userAge > age : userAge < age;
  }

  /**
   * Find users have age greater given age
   * @param users List user
   * @param age Given age is used to compare
   * @param isGreater Flag to check user have age smaller or greater
   * @return List users have age greater given age
   */
  public static List<User> findUsersHaveAgeGreater(List<User> users, int age, boolean isGreater) {
    return users.stream()
        .filter(user -> UserService.haveAgeGreater(user, age, isGreater))
        .collect(toList());
  }

  /**
   * Find top female user by given comparator
   * @param users List user
   * @param maxSize max size of returned list
   * @param comparator Comparator is used to compare
   * @return Return top female
   */
  public static List<User> findTopFemaleUserOrderBy(List<User> users, int maxSize, Comparator<User> comparator) {
    Stream<User> topFemaleUser = users.stream() // order ascending
        .filter(User::isFemale)
        .sorted(comparator);

    topFemaleUser = StreamUtil.reverse(topFemaleUser); // order descending
    return topFemaleUser
        .limit(maxSize)
        .collect(toList());
  }

  /**
   * Find user by given user id
   * @param users List user
   * @param userId Id of user
   * @return Returns user with given id
   */
  public static User findUserBy(List<User> users, String userId) {
    return users.stream()
        .filter(user -> user.getId().equals(userId))
        .findFirst()
        .orElse(null);
  }

  /**
   * Find top female users by Having posts within given days from today, order by post created date
   * @param users List user
   * @param posts List post
   * @param maxSize Max size of returned list
   * @param period Period time
   * @return List users
   */
  public static List<User> findTopFemaleUsersOrderByCreatedPost(List<User> users, List<Post> posts, int maxSize,
                                                                Period period) {
    return findTopFemaleUsersOrderByCreatedPost(users, posts, maxSize, period, LocalDateTime.now());
  }

  /**
   * Find top female users by having posts within period days ago from startDateTime, order by post created date
   * @param users List user
   * @param posts List post
   * @param maxSize Max size of returned list
   * @param period Period time
   * @return List users
   */
  public static List<User> findTopFemaleUsersOrderByCreatedPost(List<User> users, List<Post> posts, int maxSize,
                                                                Period period, LocalDateTime startDateTime) {
    // Group data with key is userId, and value is latest Post
    Stream<User> latestPostOfEachUser = posts.stream()
        .filter(post -> DateUtil.withinNumberDaysAgo(post.getCreatedAt(), period, startDateTime))
        .sorted(Comparator.comparing(Post::getCreatedAt))
        .collect(toMap(Post::getAuthorId, Function.identity(), BinaryOperator.maxBy(new CreatedPostComparator())))

    // Stream user have post order ascending by created date
//    Stream<User> usersOrderByCreatedPost =  latestPostOfEachUser
        .keySet().stream()
        .map(userId ->  findUserBy(users, userId))
        .filter(Objects::nonNull)
        .filter(User::isFemale);

    return StreamUtil.reverse(latestPostOfEachUser) // descending order
        .limit(maxSize)
        .collect(toList());
  }

  /**
   * Check whether user name of user contain given userName
   * @param user List user
   * @param userName user name need to find
   * @return true if user name of user contains given user names
   */
  public static boolean containsUsername(User user, String userName) {
    return user.getUsername().toLowerCase().contains(userName.toLowerCase());
  }
}
