package tweetapp.mock;

import tweetapp.model.Post;
import tweetapp.model.PostBuilder;
import tweetapp.model.User;
import tweetapp.model.UserBuilder;
import tweetapp.service.UserService;
import tweetapp.util.RandomUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class MockPost {
  private Random random = new Random();

  /**
   * Fake id post
   * @return Fake id of post
   */
  public String fakeId() {
    return UUID.randomUUID().toString();
  }

  /**
   * Create list post with created time in period time
   * @param n Size list
   * @param number Number post that created in period time
   * @param period Period time
   * @return List n post, while have given number post that created in period time
   */
  public List<Post> createListPostWithCreatedTimeIn(int n, int number, Period period) {
    List<Post> postList = new ArrayList<>();
    Post post;
    LocalDateTime endOfDate = LocalDate.now().atTime(LocalTime.MAX);
    LocalDateTime dateAgoPeriod = endOfDate.minus(period);
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
      post = PostBuilder.post()
          .withId(fakeId())
          .withCreatedAt(randomDate)
          .build();
      postList.add(post);
    }
    return postList;
  }

  /**
   * Create list post have number post that created by given userName
   * @param n Size of list
   * @param number Number post that created by given userName
   * @param users List user
   * @param userName User name
   * @return List post
   */
  public List<Post> createListPostCreatedByUserName(int n, int number, List<User> users, String userName) {
    List<Post> postList = new ArrayList<>();
    Post post;
    String authorId;

    List<User> usersContainName = users.stream()
        .filter(user -> UserService.containsUsername(user, userName))
        .collect(Collectors.toList());

    List<User> usersNotContainName = users.stream()
        .filter(user -> !UserService.containsUsername(user, userName))
        .collect(Collectors.toList());

    for (int i = 0; i < n; i++) {
      if (number == n - i) {
        authorId = usersContainName.get(random.nextInt(usersContainName.size())).getId(); // id of user contain userName
        number--;
      } else {
        if (number == 0) {
          // id of user not contain userName
          authorId = usersNotContainName.get(random.nextInt(usersNotContainName.size())).getId();
        } else if (random.nextBoolean()) {
          authorId = usersContainName.get(random.nextInt(usersContainName.size())).getId(); // id of user contain userName
          number--;
        } else {
          authorId = usersNotContainName.get(random.nextInt(usersNotContainName.size())).getId();
        }
      }
      System.out.println(authorId);
      post = PostBuilder.post()
          .withId(fakeId())
          .withAuthorId(authorId)
          .build();
      postList.add(post);
    }
    return postList;
  }

  /**
   * Create list post, while have number female user that have post created in period time
   * @param number Number female user that have post created in period time
   * @param period Period time
   * @return List post have size equal size of users
   */
  public List<Post> createListPostCreatedByFemaleUserAndPeriodTime(List<User> users, int number, Period period) {
    List<Post> postList = new ArrayList<>();
    Post post;
    LocalDateTime endOfDate = LocalDate.now().atTime(LocalTime.MAX);
    LocalDateTime dateAgoPeriod = endOfDate.minus(period);
    LocalDateTime randomDate;

    for (User user : users) {
      if (number > 0 && user.isFemale()) {
        randomDate = RandomUtil.randomDateTimeBetween(dateAgoPeriod, endOfDate);
        number--;
      } else {
        randomDate = RandomUtil.randomDateTimeBefore(dateAgoPeriod);
      }
      post = PostBuilder.post()
          .withId(fakeId())
          .withAuthorId(user.getId())
          .withCreatedAt(randomDate)
          .build();
      postList.add(post);
    }

    return postList;
  }

}
