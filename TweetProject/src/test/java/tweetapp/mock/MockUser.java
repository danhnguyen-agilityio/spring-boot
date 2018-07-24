package tweetapp.mock;

import com.github.javafaker.Faker;
import tweetapp.model.Gender;
import tweetapp.model.User;
import tweetapp.model.UserBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Mock data user
 */
public class MockUser {
  Faker faker = new Faker();

  /**
   * Fake id user
   * @return Fake id of user
   */
  public String fakeId() {
    return UUID.randomUUID().toString();
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
   * Create list 0 female user
   * @return List have no user
   */
  public List<User> createList0FemaleUser() {
    List<User> userList = new ArrayList<>();
    User user;
    Gender[] genders = { Gender.MALE, Gender.MALE, Gender.MALE, Gender.OTHER, Gender.OTHER,
        Gender.MALE, Gender.MALE, Gender.MALE, Gender.OTHER, Gender.OTHER,
    };

    for (int i = 0; i < 10; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withGender(genders[i])
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list 1 female user
   * @return List have 1 female user
   */
  public List<User> createList1FemaleUser() {
    List<User> userList = new ArrayList<>();
    User user;
    Gender[] genders = { Gender.MALE, Gender.FEMALE, Gender.MALE, Gender.OTHER, Gender.OTHER,
        Gender.MALE, Gender.MALE, Gender.MALE, Gender.OTHER, Gender.OTHER,
    };

    for (int i = 0; i < 10; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withGender(genders[i])
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list 10 female user
   * @return List 10 female user
   */
  public List<User> createList10FemaleUser() {
    List<User> userList = new ArrayList<>();
    User user;
    Gender[] genders = { Gender.FEMALE, Gender.FEMALE, Gender.FEMALE, Gender.FEMALE, Gender.FEMALE,
        Gender.FEMALE, Gender.FEMALE, Gender.FEMALE, Gender.FEMALE, Gender.FEMALE,
    };

    for (int i = 0; i < 10; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withGender(genders[i])
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list 0 male user
   * @return List have no male user
   */
  public List<User> createList0MaleUser() {
    List<User> userList = new ArrayList<>();
    User user;
    Gender[] genders = { Gender.FEMALE, Gender.FEMALE, Gender.FEMALE, Gender.FEMALE, Gender.OTHER,
        Gender.FEMALE, Gender.FEMALE, Gender.FEMALE, Gender.OTHER, Gender.OTHER,
    };

    for (int i = 0; i < 10; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withGender(genders[i])
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list 1 male user
   * @return List 1 male user
   */
  public List<User> createList1MaleUser() {
    List<User> userList = new ArrayList<>();
    User user;
    Gender[] genders = { Gender.FEMALE, Gender.FEMALE, Gender.FEMALE, Gender.OTHER, Gender.OTHER,
        Gender.FEMALE, Gender.FEMALE, Gender.MALE, Gender.OTHER, Gender.OTHER,
    };

    for (int i = 0; i < 10; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withGender(genders[i])
          .build();
      userList.add(user);
    }
    return userList;
  }

  /**
   * Create list 10 male user
   * @return List 10 male user
   */
  public List<User> createList10MaleUser() {
    List<User> userList = new ArrayList<>();
    User user;
    Gender[] genders = { Gender.MALE, Gender.MALE, Gender.MALE, Gender.MALE, Gender.MALE,
        Gender.MALE, Gender.MALE, Gender.MALE, Gender.MALE, Gender.MALE,
    };

    for (int i = 0; i < 10; i++) {
      user = UserBuilder.user()
          .withId(fakeId())
          .withGender(genders[i])
          .build();
      userList.add(user);
    }
    return userList;
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

}
