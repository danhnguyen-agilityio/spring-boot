package tweetapp;

import tweetapp.service.PostService;
import tweetapp.service.UserService;
import tweetapp.util.BufferedReaderProcessor;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.FileUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * TweetApp implement all statistic
 */
public class TweetApp {
  List<User> users;
  List<Post> posts;

  /**
   * Count all users
   * @return total users
   */
  public long countAllUser() {
    return users.stream().count();
  }

  /**
   * Count all posts
   * @return total posts
   */
  public long countAllPost() {
    return posts.stream().count();
  }

  /**
   * Count all female users
   * @return total female users
   */
  public long countFemaleUsers() {
    return users.stream().filter(User::isFemale).count();
  }

  /**
   * Count all male users
   * @return total male users
   */
  public long countMaleUsers() {
    return users.stream().filter(User::isMale).count();
  }

  /**
   * Find user have created in number days ago
   * @param days
   * @return List user
   */
  public List<User> findUsersCreatedIn(int days) {
    return users.stream()
        .filter(user -> UserService.createdWithinNumberDaysAgo(user, days))
        .collect(Collectors.toList());
  }

  public static void main(String[] args) throws IOException {
    TweetApp tweetApp = new TweetApp();
    try {
      tweetApp.users = UserService.getUsers();
      tweetApp.posts = PostService.getPosts();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Scanner scanner;
    int question;
    char confirm;
    do {
      System.out.println("1. Count all users");
      System.out.println("2. Count all female users");
      System.out.println("3. Count all male users");
      System.out.println("4. Find all users who has been created");
      System.out.println("  a. Within today");
      System.out.println("  b. Within a week from today");
      System.out.println("  c. Within a month from today");
      System.out.println("5. Find all users who have his/her birthday within the current month");
      System.out.println("6. Find all users who has his first name is James");
      System.out.println("7. Find all users who has his avatar");
      System.out.println("8. Find all users who is under 16 years old");
      System.out.println("9. Find all users who is greater than 30 years old");
      System.out.println("10. Find top 100 female users");
      System.out.println("  a. Order by first name");
      System.out.println("  b. Order by last name");
      System.out.println("  c. Having posts within a week from today, order by post created date.");
      System.out.println("11. Find all posts which have been created");
      System.out.println("  a. Within today");
      System.out.println("  b. Within a week from today");
      System.out.println("  c. Within a month from today");
      System.out.println("12. Find all posts of a specific user");
      System.out.print("Please choose question that you want to see (1 -> 12): ");

      scanner = new Scanner(System.in);
      try {
        question = scanner.nextInt();
      } catch (InputMismatchException e) {
        question = 1000; //  Default value when user enter wrong format number
      }

      // Exit when user enter 0
      if (question == 0) break;

      // Choose again question if user enter value not belong from 0 -> 12
      if (question < 0 || question > 12) {
        System.out.println("Number question only belong from 0 -> 12. Please choose again.");
      } else { // Show info by correspond question
        switch (question) {
          case 1: { // count all user
            long totalUsers = tweetApp.countAllUser();
            System.out.println("Count all user: " + totalUsers);
            break;
          }

          case 2: { // count all female user
            long totalFemaleUsers = tweetApp.countFemaleUsers();
            System.out.println("Count all female user: " + totalFemaleUsers);
            break;
          }

          case 3: { // count all male user
            long totalMaleUsers = tweetApp.countMaleUsers();
            System.out.println("Count all male user: " + totalMaleUsers);
            break;
          }

          // Find all users who has been created
            // a. Within today
            // b. Within a week from today
            // c. Within a month from today
          case 4: {
            List<User> usersCreatedWithinToday = tweetApp.findUsersCreatedIn(1);
            System.out.println("Number users have created within today: " + usersCreatedWithinToday.size());

            List<User> usersCreatedWithinAWeek = tweetApp.findUsersCreatedIn(7);
            System.out.println("Number users have created within a week: " + usersCreatedWithinAWeek.size());

            List<User> usersCreatedWithinAMonth = tweetApp.findUsersCreatedIn(30);
            System.out.println("Number users have created within a month: " + usersCreatedWithinAMonth.size());
          }
        }
      }

      System.out.print("Would you like to continue demo app? (Y/N): ");
      confirm = (char) System.in.read();
    } while (confirm == 'y' || confirm == 'Y');
  }
}
