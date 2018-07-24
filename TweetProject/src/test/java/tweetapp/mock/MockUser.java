package tweetapp.mock;

import com.github.javafaker.Faker;
import tweetapp.model.User;
import tweetapp.model.UserBuilder;

import java.util.ArrayList;
import java.util.List;

public class MockUser {
  public void create() {
    Faker faker = new Faker();

    List<User> users = new ArrayList<>();
    User user;

    for (int i = 0; i < 10; i++) {
      user = UserBuilder.user()
          .withId(faker.idNumber().valid())
          .build();
      users.add(user);
    }
  }
}
