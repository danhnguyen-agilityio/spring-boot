package tweetapp.mock;

import tweetapp.model.Gender;
import tweetapp.model.User;
import tweetapp.model.UserBuilder;
import tweetapp.util.RandomUtil;

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
    System.out.println("dateAgoPeriod" + dateAgoPeriod);
    Random random = new Random();
    LocalDateTime randomDate;

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        randomDate = RandomUtil.randomDateTimeBetween(dateAgoPeriod, endOfDate);
        number--;
      } else {
        if (number == 0) {
          randomDate = RandomUtil.randomDateTimeBefore(dateAgoPeriod);
          System.out.println("randomDate" + randomDate);
        } else if (random.nextBoolean()) {
          randomDate = RandomUtil.randomDateTimeBetween(dateAgoPeriod, endOfDate);
          System.out.println(randomDate);
          number--;
        } else {
          randomDate = RandomUtil.randomDateTimeBefore(dateAgoPeriod);
          System.out.println(randomDate);
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
   * Create user with given birthday
   * @param birthday
   * @return User
   */
  public User createUserWithBirthday(LocalDateTime birthday) {
    return UserBuilder.user()
        .withId(fakeId())
        .withBirthday(birthday)
        .build();
  }

  /**
   * Create list user with created time of each user correspond with given createdTimes
   * @param createdTimes List time that formatted string
   * @return List user with created time correspond with given times
   */
  public List<User> createListUserWithCreatedAt(String[] createdTimes) {
    List<User> userList = new ArrayList<>();
    User user;
    for (int i = 0; i < createdTimes.length; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withCreatedAt(LocalDateTime.parse(createdTimes[i]))
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user with birthday time of each user correspond with given birthdayTimes
   * @param birthdayTimes List time that formatted string
   * @return List user with created time correspond with given birthdayTimes
   */
  public List<User> createListUserWithBirthday(String[] birthdayTimes) {
    List<User> userList = new ArrayList<>();
    User user;
    for (int i = 0; i < birthdayTimes.length; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withBirthday(LocalDateTime.parse(birthdayTimes[i]))
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user with first name of each user correspond with given firstNames
   * @param firstNames List first name
   * @return List user with first name correspond with given firstNames
   */
  public List<User> createListUserWithFirstName(String[] firstNames) {
    List<User> userList = new ArrayList<>();
    User user;
    for (int i = 0; i < firstNames.length; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withFirstName(firstNames[i])
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user with avatar of each user correspond with given avatarUrls
   * @param avatarUrls List avatar
   * @return List user with avatar correspond with given avatarUrls
   */
  public List<User> createListUserWithAvatar(String[] avatarUrls) {
    List<User> userList = new ArrayList<>();
    User user;
    for (int i = 0; i < avatarUrls.length; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withAvatarUrl(avatarUrls[i])
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list user with firstName and gender of each user correspond with given avatarUrls and genders
   * @param firstNames List first name
   * @param genders List gender
   * @return List user
   */
  public List<User> createListUserWithFirstNameAndGender(String[] firstNames, Gender[] genders) {
    List<User> userList = new ArrayList<>();
    User user;
    for (int i = 0; i < firstNames.length; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withFirstName(firstNames[i])
          .withGender(genders[i])
          .build();
      userList.add(user);
    }
    return userList;
  }

}
