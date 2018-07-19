package tweetapp.service;

import tweetapp.model.User;
import tweetapp.util.DateUtil;
import tweetapp.util.FileUtil;
import tweetapp.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class UserService {

  /**
   * Print all info list users
   * @param users
   */
  public static void print(List<User> users) {
    IntStream.range(0, users.size())
        .forEach(idx -> {
          System.out.println("-------------------------------");
          System.out.print((idx + 1) + ". ");
          System.out.println(users.get(idx));
        });
  }

  /**
   * Process user data and return list user
   * @param br
   * @return Return list user
   * @throws IOException
   */
  private static List<User> processUserData(BufferedReader br) throws IOException {
    String line;
    String csvSplitBy = ",";
    boolean headerRow = true;

    List<User> users = new ArrayList<>();

    while ((line = br.readLine()) != null) {

      // Ignore first line because it is header row
      if (headerRow) {
        headerRow = false;
        continue;
      }

      // Split line to array data
      String[] data = line.split(csvSplitBy);
      String id = StringUtil.substringBetween(data[0], "\"");
      String username = data[2];
      String firstName = data[3];
      String lastName = data[4];
      String avatarUrl = data[6];
      String nickname = data[7];
      String email = data[8];
      String phone = data[9];
      String address = data[10];
      String gender = data[13];
      LocalDateTime birthday = DateUtil.convertStringToLocalDateTime(data[14]);
      String description =  data[15];
      LocalDateTime createdAt = DateUtil.convertStringToLocalDateTime(data[16]);

      LocalDateTime modifiedAt = DateUtil.convertStringToLocalDateTime(data[17]);
      String version = data[18];

      // Create instance User
      User user = new User(id, username, firstName, lastName, avatarUrl, nickname, email, phone, address, gender, birthday,
          description, createdAt, modifiedAt, version);

      users.add(user);
    }
    return users;
  }

  /**
   * Get all list user from csv file
   * @return All info users
   * @throws IOException
   */
  public static List<User> getUsers() throws IOException {
    String csvFile ="./src/main/resources/users.csv";
    return FileUtil.readFile(csvFile, UserService::processUserData);
  }

  /**
   * Check whether user have created within a specific days ago
   * @param user
   * @param days
   * @return true if user have created within a specific days ago and false if other
   */
  public static boolean createdWithinNumberDaysAgo(User user, int days) {
    return DateUtil.withinNumberDaysAgo(user.getCreatedAt(), days);
  }

  /**
   * Check whether user have birthday within specific month
   * @param user
   * @return true if user have birthday within specific month and false if other
   */
  public static boolean birthdayInMonth(User user, int month) {
    return user.getBirthday().getMonthValue() == month;
  }

  /**
   * Find users have birthday within specific month
   * @param users
   * @param month
   * @return List users have birthday within specific month
   */
  public static List<User> findUsersHaveBirthdayInMonth(List<User> users, int month) {
    return users.stream()
        .filter(user -> UserService.birthdayInMonth(user, month))
        .collect(toList());
  }

  // FIXME:: consider put method in here or User class
  /**
   * Test whether user have given firstName
   * @param user
   * @param firstName
   * @return true if user have given firstName and false if other
   */
  public static boolean haveFirstNameWith(User user, String firstName) {
    return user.getFirstName().equalsIgnoreCase(firstName);
  }

  /**
   * Find users have specific first name
   * @param users
   * @param firstName
   * @return List users have specific first name
   */
  public static List<User> findUsersWithFirstName(List<User> users, String firstName) {
    return users.stream()
        .filter(user -> UserService.haveFirstNameWith(user, firstName))
        .collect(toList());
  }

  /**
   * Check whether user have avatar
   * @param user
   * @return true if user havve avatar
   */
  public static boolean haveAvatar(User user) {
    return !("".equals(user.getAvatarUrl()));
  }

  /**
   * Find users have avatar
   * @param users
   * @return List users have avatar
   */
  public static List<User> findUsersHaveAvatar(List<User> users) {
    return users.stream()
        .filter(user -> UserService.haveAvatar(user))
        .collect(toList());
  }

}
