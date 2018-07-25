package tweetapp.service;

import tweetapp.comparator.CreatedPostComparator;
import tweetapp.constant.App;
import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.model.UserBuilder;
import tweetapp.util.*;

import java.io.IOException;
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
 * userService implement statistic on list user
 */
public class UserServiceImpl implements UserService {

  /**
   * Print all info list users
   * @param users List user
   */
  @Override
  public void print(List<User> users) {
    if (users.isEmpty()) return;

    System.out.println(String.format("| %-5s | %-25.25s | %-20.20s | %-15.15s | %-15.15s | %-25.25s | %-25.25s",
        "Index", "Id", "User name", "First name", "Last name", "BirthDate", "Created At"));
    IntStream.range(0, users.size())
        .forEach(idx -> {
          User user = users.get(idx);
          System.out.println(String.format("| %-5d | %-25.25s | %-20.20s | %-15.15s | %-15.15s | %-25.25s | %-25.25s",
              idx + 1, user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getBirthday(),
              user.getCreatedAt()));
        });
  }

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
          return UserBuilder.user()
              .withId(id)
              .withUsername(username)
              .withFirstName(firstName)
              .withLastName(lastName)
              .withAvatarUrl(avatarUrl)
              .withNickName(nickname)
              .withEmail(email)
              .withPhone(phone)
              .withAddress(address)
              .withGender(gender)
              .withBirthday(birthday)
              .withDescription(description)
              .withCreatedAt(createdAt)
              .withModifiedAt(modifiedAt)
              .withVersion(version)
              .build();
        })
        .collect(toList());
  }

  /**
   * Get all list user from csv file
   * @return All info users
   * @throws IOException occur when file not found
   */
  @Override
  public List<User> getUsersFromFile(String fileName) throws IOException {
    return FileUtil.readFile(fileName, UserServiceImpl::processUserData);
  }

  /**
   * Count all users
   * @param users List user
   * @return total users
   */
  @Override
  public long countAllUser(List<User> users) {
    return users.size();
  }

  /**
   * Count all users  with given gender
   * @param users List user
   * @return Number users with given gender
   */
  @Override
  public long countUsersByGender(List<User> users, Gender gender) {
    return users.stream().filter(user -> user.getGender().equals(gender)).count();
  }

  /**
   * Find user have created in period ago from given fromDate
   * @param users List user
   * @param period Period time
   * @return List user
   */
  @Override
  public List<User> findUsersCreatedIn(List<User> users, Period period, LocalDateTime fromDate) {
    return users.stream()
        .filter(user -> DateUtil.withinNumberDaysAgo(user.getCreatedAt(), period, fromDate))
        .collect(Collectors.toList());
  }

  /**
   * Find user have created in period ago from today
   * @param users List user
   * @param period Period time
   * @return List user
   */
  @Override
  public List<User> findUsersCreatedIn(List<User> users, Period period) {
    return findUsersCreatedIn(users, period, LocalDateTime.now());
  }

  /**
   * Find users have birthday within given month
   * @param users List user
   * @param month Month is used to find
   * @return List users have birthday within given month
   */
  @Override
  public List<User> findUsersHaveBirthdayInMonth(List<User> users, int month) {
    return users.stream()
        .filter(user -> UserService.birthdayInMonth(user, month))
        .collect(toList());
  }

  /**
   * Find users have specific first name
   * @param users List user
   * @param firstName First name need to find
   * @return List users have specific first name
   */
  @Override
  public List<User> findUsersWithFirstName(List<User> users, String firstName) {
    return users.stream()
        .filter(user -> UserService.haveFirstNameWith(user, firstName))
        .collect(toList());
  }

  /**
   * Find users have avatar
   * @param users List user
   * @return List users have avatar
   */
  @Override
  public List<User> findUsersHaveAvatar(List<User> users) {
    return users.stream()
        .filter(UserService::haveAvatar)
        .collect(toList());
  }

  /**
   * Find users have age greater given age
   * @param users List user
   * @param age Given age is used to compare
   * @param isGreater Flag to check user have age smaller or greater
   * @return List users have age greater given age
   */
  @Override
  public List<User> findUsersHaveAgeGreater(List<User> users, int age, boolean isGreater) {
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
  @Override
  public List<User> findTopFemaleUserOrderBy(List<User> users, int maxSize, Comparator<User> comparator) {
    return users.stream()
        .filter(User::isFemale)
        .sorted(comparator)
        .limit(maxSize)
        .collect(toList());
  }

  /**
   * Find user by given user id
   * @param users List user
   * @param userId Id of user
   * @return Returns user with given id
   */
  @Override
  public User findUserBy(List<User> users, String userId) {
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
  @Override
  public List<User> findTopFemaleUsersOrderByCreatedPost(List<User> users, List<Post> posts, int maxSize,
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
  @Override
  public List<User> findTopFemaleUsersOrderByCreatedPost(List<User> users, List<Post> posts, int maxSize,
                                                                Period period, LocalDateTime startDateTime) {
    // Group data with key is userId, and value is latest Post
    Map<String, Post> latestPostOfEachUser = posts.stream()
        .filter(post -> DateUtil.withinNumberDaysAgo(post.getCreatedAt(), period, startDateTime))
        .sorted(Comparator.comparing(Post::getCreatedAt))
        .collect(toMap(Post::getAuthorId, p -> p, (p1, p2) -> p2, LinkedHashMap::new));

    // Stream user have post order ascending by created date
    Stream<User> usersOrderByCreatedPost =  latestPostOfEachUser
        .keySet().stream()
        .map(userId ->  findUserBy(users, userId))
        .filter(Objects::nonNull)
        .filter(User::isFemale);

    return StreamUtil.reverse(usersOrderByCreatedPost) // descending order
        .limit(maxSize)
        .collect(toList());
  }
}
