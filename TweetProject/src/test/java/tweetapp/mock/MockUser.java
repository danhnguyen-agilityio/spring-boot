package tweetapp.mock;

import tweetapp.model.Gender;
import tweetapp.model.User;
import tweetapp.model.UserBuilder;
import tweetapp.util.RandomUtil;
import tweetapp.util.StringUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Mock data user
 */
public class MockUser {

  private Random random = new Random();

  /**
   * Fake id user
   * @return Fake id of user
   */
  public String fakeId() {
    return UUID.randomUUID().toString();
  }

  public String fakeAvatarUrl() {
    return "https://s3.amazonaws.com/" + RandomUtil.randomString() + ".img";
  }

  /**
   * Create list with n user
   * @return List n users
   */
  public List<User> createList(int n) {
    List<User> userList = new ArrayList<>();
    User user;
    for (int i = 0; i < n; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list n user and have given number user with given gender
   * @param n Size of list
   * @param number Number user with given gender
   * @param gender Gender of user
   * @return List n users
   */
  public List<User> createListUserWithGender(int n, int number, Gender gender) {
    List<User> userList = new ArrayList<>();
    User user;
    Gender randomGender;

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        randomGender = gender;
        number--;
      } else {
        while (true) {
          // Random gender
          randomGender = Gender.getRandom();

          // If size != 0
          if (number != 0) {
            // If random gender is given gender, decrease size 1
            if (randomGender.equals(gender)) {
              number--;
            }
            break;
          }
          // If size = 0 and random gender is given gender => random gender again
          if (randomGender.equals(gender)) continue;

          // If size = 0 and random gender not given gender => break
          break;
        }
      }
      user = UserBuilder.user()
          .withId(fakeId())
          .withFirstName(RandomUtil.randomString())
          .withLastName(RandomUtil.randomString())
          .withGender(randomGender)
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user with created time in period time
   * @param n Size list
   * @param number Number user that created in period time
   * @param period Period time
   * @return List n user, while have given number user that created in period time
   */
  public List<User> createListUserWithCreatedTimeIn(int n, int number, Period period) {
    List<User> userList = new ArrayList<>();
    User user;
    LocalDateTime endOfDate = LocalDate.now().atTime(LocalTime.MAX);
    LocalDateTime dateAgoPeriod = endOfDate.minus(period);
    Random random = new Random();
    LocalDateTime randomDate;

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        randomDate = RandomUtil.randomDateTimeBetween(dateAgoPeriod, endOfDate);
        number--;
      } else {
        if (number == 0) {
          randomDate = RandomUtil.randomDateTimeBefore(dateAgoPeriod);
        } else if (random.nextBoolean()) {
          randomDate = RandomUtil.randomDateTimeBetween(dateAgoPeriod, endOfDate);
          number--;
        } else {
          randomDate = RandomUtil.randomDateTimeBefore(dateAgoPeriod);
        }
      }
      user = UserBuilder.user()
          .withId(fakeId())
          .withCreatedAt(randomDate)
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user with number user have birthday in given month
   * @param n Size of list
   * @param number Number user have birthday in given month
   * @param monthOfYear Month of year
   * @return List n user, while have given number user that have birthday in given month
   */
  public List<User> createListUserWithBirthdayInMonth(int n, int number, int monthOfYear) {
    List<User> userList = new ArrayList<>();
    User user;
    int randomMonth;

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        randomMonth = monthOfYear;
        number--;
      } else {
        while (true) {
          // Random month
          randomMonth = RandomUtil.randomMonthOfYear();

          // If size != 0
          if (number != 0) {
            // If randomMonth equal given monthOfYear, decrease size 1
            if (randomMonth == monthOfYear) {
              number--;
            }
            break;
          }
          // If size = 0 and randomMonth equal given monthOfYear => random month again
          if (randomMonth == monthOfYear) continue;

          // If size = 0 and randomMonth not equal given monthOfYear => break
          break;
        }
      }
      user = UserBuilder.user()
          .withId(fakeId())
          .withBirthday(RandomUtil.randomDate().withMonth(randomMonth).atStartOfDay())
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user with number user have given first name
   * @param n Size of list
   * @param number Number user have given first name
   * @param firstName First name of user
   * @return List n user, while have given number user that have given first name
   */
  public List<User> createListUserWithFirstName(int n, int number, String firstName) {
    List<User> userList = new ArrayList<>();
    User user;
    String randomFirstName;

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        randomFirstName = firstName;
        number--;
      } else {
        while (true) {
          // Random first name
          randomFirstName = RandomUtil.randomString();

          // If size != 0
          if (number != 0) {
            // If randomFirstName equal given firstName, decrease size 1
            if (randomFirstName.equals(firstName)) {
              number--;
            }
            break;
          }
          // If size = 0 and randomFirstName equal given firstName => random firstName again
          if (randomFirstName.equals(firstName)) continue;

          // If size = 0 and randomFirstName not equal given firstName => break
          break;
        }
      }
      user = UserBuilder.user()
          .withId(fakeId())
          .withFirstName(randomFirstName)
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user with number user have avatar
   * @param n Size of list
   * @param number Number user have avatar
   * @return List n user, while have given number user that have avatar
   */
  public List<User> createListUserHaveAvatar(int n, int number) {
    List<User> userList = new ArrayList<>();
    User user;
    String randomAvatar;

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        randomAvatar = fakeAvatarUrl();
        number--;
      } else {
        if (number == 0) {
          randomAvatar = "";
        } else if (random.nextBoolean()) {
          randomAvatar = fakeAvatarUrl();
          number--;
        } else {
          randomAvatar = "";
        }
      }
      user = UserBuilder.user()
          .withId(fakeId())
          .withAvatarUrl(randomAvatar)
          .build();
      userList.add(user);
    }
    return userList;
  }


  /**
   * Create list user have age greater given age or less than given age
   * @param n Size of list
   * @param number Number users have age greater given age
   * @param age Age to compare
   * @param isGreater Flag to check greater than or less than
   * @return List n user have number user that have age greater given age or less than given age
   */
  public List<User> createListUserHaveAgeGreater(int n, int number, int age, boolean isGreater) {
    List<User> userList = new ArrayList<>();
    User user;
    int randomAge;

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        if (isGreater) {
          randomAge = random.nextInt(100) + age + 1;
        } else {
          randomAge = random.nextInt(age);
        }
        number--;
      } else {
        while (true) {
          // Random age
          randomAge = random.nextInt(100 + age);

          // If size != 0
          if (number != 0) {
            // If randomAge greater given age and flag isGreater true
            // or randomAge less than given age and flag isGreater false, decrease size 1
            if ((randomAge > age) == isGreater ) {
              number--;
            }
            break;
          }
          // If size = 0 and randomAge greater given age and flag isGreater true
          // or randomAge less than given age and flag isGreater false => random age again
          if ((randomAge > age) == isGreater) continue;

          // Other case => break
          break;
        }
      }
      user = UserBuilder.user()
          .withId(fakeId())
          .withBirthday(RandomUtil.randomDateFromAge(randomAge))
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user have number userName of user contains given userName
   * @param n Size of list
   * @param number Number userName of user contains given userName
   * @param userName Substring of username of user
   * @return List user
   */
  public List<User> createListUserContainUsername(int n, int number, String userName) {
    List<User> userList = new ArrayList<>();
    User user;
    String randomUserName;

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        randomUserName = userName + RandomUtil.randomString();
        number--;
      } else {
        while (true) {
          // Random user name
          randomUserName = RandomUtil.randomString();

          // If size != 0
          if (number != 0) {
            // If randomFirstName contain given userName, decrease size 1
            if (randomUserName.contains(userName)) {
              number--;
            }
            break;
          }
          // If size = 0 and randomUserName contain given userName => random userName again
          if (randomUserName.contains(userName)) continue;

          // If size = 0 and randomUserName not contain given userName => break
          break;
        }
      }
      System.out.println(randomUserName);
      user = UserBuilder.user()
          .withId(fakeId())
          .withUsername(randomUserName)
          .build();
      userList.add(user);
    }
    return userList;
  }

}
