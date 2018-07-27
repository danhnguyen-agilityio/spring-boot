package tweetapp.service;

import tweetapp.model.Gender;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Comparator;
import java.util.List;

/**
 * UserService interface
 */
public interface UserService {

  /**
   * Check whether user have birthday within given month
   * @param user List user
   * @return true if user have birthday within given month and false if other
   */
  static boolean birthdayInMonth(User user, int month) {
    return user.getBirthday().getMonthValue() == month;
  }

  /**
   * Test whether user have given firstName
   * @param user User need check
   * @param firstName First name of user
   * @return true if user have given firstName and false if other
   */
  static boolean haveFirstNameWith(User user, String firstName) {
    return user.getFirstName().equalsIgnoreCase(firstName);
  }

  /**
   * Check whether user have avatar
   * @param user List user
   * @return true if user have avatar
   */
  static boolean haveAvatar(User user) {
    return !("".equals(user.getAvatarUrl()));
  }

  /**
   * Check whether user have age greater given age
   * @param user User need to check
   * @param age Given age is used to compare
   * @param isGreater Flag to check user have age smaller or greater
   * @return true if user have age greater given age
   */
  static boolean haveAgeGreater(User user, int age, boolean isGreater) {
    int userAge = AgeCalculator.calculateAge(user.getBirthday().toLocalDate(), LocalDate.now());
    return isGreater ? userAge > age : userAge < age;
  }

  /**
   * Check whether user name of user contain given userName
   * @param user List user
   * @param userName user name need to find
   * @return true if user name of user contains given user names
   */
  static boolean containsUsername(User user, String userName) {
    return user.getUsername().toLowerCase().contains(userName.toLowerCase());
  }

  /**
   * Print all info list users
   * @param users List user
   */
  void print(List<User> users);

  /**
   * Get all list user from csv file
   * @return All info users
   * @throws IOException occur when file not found
   */
  List<User> getUsersFromFile(String fileName) throws IOException;

  /**
   * Count all users
   * @param users List user
   * @return total users
   */
  long countAllUser(List<User> users);

  /**
   * Count all users  with given gender
   * @param users List user
   * @return Number users with given gender
   */
  long countUsersByGender(List<User> users, Gender gender);

  /**
   * Find user have created in period ago from given fromDate
   * @param users List user
   * @param period Period time
   * @return List user
   */
  List<User> findUsersCreatedIn(List<User> users, Period period, LocalDateTime fromDate);

  /**
   * Find user have created in period ago from today
   * @param users List user
   * @param period Period time
   * @return List user
   */
  List<User> findUsersCreatedIn(List<User> users, Period period);

  /**
   * Find users have birthday within given month
   * @param users List user
   * @param month Month is used to find
   * @return List users have birthday within given month
   */
  List<User> findUsersHaveBirthdayInMonth(List<User> users, int month);

  /**
   * Find users have specific first name
   * @param users List user
   * @param firstName First name need to find
   * @return List users have specific first name
   */
  List<User> findUsersWithFirstName(List<User> users, String firstName);

  /**
   * Find users have avatar
   * @param users List user
   * @return List users have avatar
   */
  List<User> findUsersHaveAvatar(List<User> users);

  /**
   * Find users have age greater given age
   * @param users List user
   * @param age Given age is used to compare
   * @param isGreater Flag to check user have age smaller or greater
   * @return List users have age greater given age
   */
  List<User> findUsersHaveAgeGreater(List<User> users, int age, boolean isGreater);

  /**
   * Find top female user by given comparator
   * @param users List user
   * @param maxSize max size of returned list
   * @param comparator Comparator is used to compare
   * @return Return top female
   */

  List<User> findTopFemaleUserOrderBy(List<User> users, int maxSize, Comparator<User> comparator);

  /**
   * Find user by given user id
   * @param users List user
   * @param userId Id of user
   * @return Returns user with given id
   */
  User findUserBy(List<User> users, String userId);

  /**
   * Find top female users by Having posts within given days from today, order by post created date
   * @param users List user
   * @param posts List post
   * @param maxSize Max size of returned list
   * @param period Period time
   * @return List users
   */
  List<User> findTopFemaleUsersOrderByCreatedPost(List<User> users, List<Post> posts, int maxSize,
                                                         Period period);

  /**
   * Find top female users by having posts within period days ago from startDateTime, order by post created date
   * @param users List user
   * @param posts List post
   * @param maxSize Max size of returned list
   * @param period Period time
   * @return List users
   */
  List<User> findTopFemaleUsersOrderByCreatedPost(List<User> users, List<Post> posts, int maxSize,
                                                         Period period, LocalDateTime startDateTime);
}
