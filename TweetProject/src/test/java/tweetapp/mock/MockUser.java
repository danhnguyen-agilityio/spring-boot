package tweetapp.mock;

import com.github.javafaker.Faker;
import tweetapp.model.Gender;
import tweetapp.model.User;
import tweetapp.model.UserBuilder;

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
   */
  public String fakeId() {
    return UUID.randomUUID().toString();
  }

  /**
   * Create list with n user
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

}
