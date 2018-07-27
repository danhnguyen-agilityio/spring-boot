package tweetapp;

import tweetapp.comparator.FirstNameComparator;
import tweetapp.comparator.LastNameComparator;
import tweetapp.constant.App;
import tweetapp.model.Gender;
import tweetapp.service.PostServiceImpl;
import tweetapp.service.UserServiceImpl;
import tweetapp.model.Post;
import tweetapp.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/**
 * TweetApp implement all statistic
 */
public class TweetApp {

  private List<User> users;
  private List<Post> posts;

  public static void main(String[] args) throws IOException {

    TweetApp tweetApp = new TweetApp();
    UserServiceImpl userService = new UserServiceImpl();
    PostServiceImpl postService = new PostServiceImpl();
    tweetApp.users = userService.getUsersFromFile(App.USER_FILE_PATH);
    tweetApp.posts = postService.getPostsFromFile(App.POST_FILE_PATH);

    String[] menu = {
        "0. Exit",
        "1. Count all users",
        "2. Count all female users",
        "3. Count all male users",
        "4. Find all users who has been created Within today",
        "5. Find all users who has been created Within a week from today",
        "6. Find all users who has been created Within a month from today",
        "7. Find all users who have his/her birthday within the current month",
        "8. Find all users who has his first name is James",
        "9. Find all users who has his avatar",
        "10. Find all users who is under 16 years old",
        "11. Find all users who is greater than 30 years old",
        "12. Find top 100 female users Order by first name",
        "13. Find top 100 female users Order by last name",
        "14. Find top 100 female users Having posts within a week from today, order by post created date.",
        "15. Find all posts which have been created Within today",
        "16. Find all posts which have been created Within a week from today",
        "17. Find all posts which have been created Within a month from today",
        "18. Find all posts of a specific user"
    };

    Scanner scanner;
    int question;
    char confirm;
    do {

      for (String item : menu) {
        System.out.println(item);
      }

      System.out.print("Please choose question (1 -> 18): ");

      scanner = new Scanner(System.in);
      try {
        question = scanner.nextInt();
      } catch (InputMismatchException e) {
        question = 1000; //  Default value when user enter wrong format number
      }

      // Exit when user enter 0
      if (question == 0) break;

      // Choose again question if user enter value not belong from 0 -> 18
      if (question < 0 || question > 18) {
        System.out.println("Number question only belong from 0 -> 18. Please choose again.");
      } else { // Show info by correspond question
        switch (question) {
          case 1: { // count all user
            long totalUsers = userService.countAllUser(tweetApp.users);
            System.out.println("Count all user: " + totalUsers);
            break;
          }

          case 2: { // count all female user
            long totalFemaleUsers = userService.countUsersByGender(tweetApp.users, Gender.FEMALE);
            System.out.println("Count all female user: " + totalFemaleUsers);
            break;
          }

          case 3: { // count all male user
            long totalMaleUsers = userService.countUsersByGender(tweetApp.users, Gender.MALE);
            System.out.println("Count all male user: " + totalMaleUsers);
            break;
          }

          case 4: { // Find all users who has been created within today
            List<User> usersCreatedWithinToday = userService.findUsersCreatedIn(tweetApp.users, Period.ofDays(1));
            System.out.println("Number users have created within today: " + usersCreatedWithinToday.size());
            userService.print(usersCreatedWithinToday);
            break;
          }

          case 5: { // Find all users who has been created within a week from today
            List<User> usersCreatedWithinAWeek = userService.findUsersCreatedIn(tweetApp.users, Period.ofWeeks(1));
            System.out.println("Number users have created within a week: " + usersCreatedWithinAWeek.size());
            userService.print(usersCreatedWithinAWeek);
            break;
          }

          case 6: { // Find all users who has been created within a month from today
            List<User> usersCreatedWithinAMonth = userService.findUsersCreatedIn(tweetApp.users, Period.ofMonths(1));
            System.out.println("Number users have created within a month: " + usersCreatedWithinAMonth.size());
            userService.print(usersCreatedWithinAMonth);
            break;
          }

          case 7: { // Find all users who have his/her birthday within the current month");
            List<User> birthdayInCurrentMonth = userService.findUsersHaveBirthdayInMonth(tweetApp.users,
                LocalDateTime.now().getMonthValue());
            System.out.println(birthdayInCurrentMonth.size() + " users who have his/her birthday within the current month");
            userService.print(birthdayInCurrentMonth);
            break;
          }

          case 8: { // Find all users who has his first name is James
            String firstName = "James";
            List<User> usersWithFirstName = userService.findUsersWithFirstName(tweetApp.users, firstName);
            System.out.println(usersWithFirstName.size() + " users who has his first name is James");
            userService.print(usersWithFirstName);
            break;
          }

          case 9: { // Find all users who has his avatar
            List<User> usersHaveAvatar = userService.findUsersHaveAvatar(tweetApp.users);
            System.out.println(usersHaveAvatar.size() + " users who has his avatar");
            userService.print(usersHaveAvatar);
            break;
          }

          case 10: { // Find all users who is under 16 years old
            int age = 16;
            List<User> usersHaveAgeUnder = userService.findUsersHaveAgeGreater(tweetApp.users, age, false);
            System.out.println(usersHaveAgeUnder.size() + " users who is under 16 years old");
            userService.print(usersHaveAgeUnder);
            break;
          }

          case 11: { // Find all users who is greater than 30 years old
            int age = 30;
            List<User> usersHaveAgeGreater = userService.findUsersHaveAgeGreater(tweetApp.users, age,true);
            System.out.println(usersHaveAgeGreater.size() + " users who is greater than 30 years old");
            userService.print(usersHaveAgeGreater);
            break;
          }

          case 12: { // Find top 100 female users by Order by first name
            List<User> topFemaleUserOrderByFirstName = userService.findTopFemaleUserOrderBy(tweetApp.users, App.MAX_SIZE,
                new FirstNameComparator());
            System.out.println("************ Top 100 female users order by first name ***********");
            userService.print(topFemaleUserOrderByFirstName);

            break;
          }

          case 13: { // Find top 100 female users by Order by last name
            List<User> topFemaleUserOrderByLastName = userService.findTopFemaleUserOrderBy(tweetApp.users, App.MAX_SIZE,
                new LastNameComparator());
            System.out.println("************ Top 100 female users order by first name **********");
            userService.print(topFemaleUserOrderByLastName);
            break;
          }

          case 14: { // Find top 100 female users by Having posts within a week from today, order by post created date
            List<User> topFemaleUserOrderByCreatedPost = userService.findTopFemaleUsersOrderByCreatedPost(
                tweetApp.users, tweetApp.posts, App.MAX_SIZE, Period.ofWeeks(1));
            System.out.println("Top 100 female users order by first name Having posts within a week from today, " +
                "order by post created date");
            userService.print(topFemaleUserOrderByCreatedPost);
            break;
          }

          case 15: { // Find all posts which have been created Within today
            List<Post> postsCreatedWithinToday = postService.findPostsCreatedIn(tweetApp.posts, Period.ofDays(1));
            System.out.println(postsCreatedWithinToday.size() + " posts which have been created Within today");
            postService.print(postsCreatedWithinToday);
            break;
          }

          case 16: { // Find all posts which have been created Within a week from today
            List<Post> postsCreatedWithinWeek = postService.findPostsCreatedIn(tweetApp.posts, Period.ofWeeks(1));
            System.out.println(postsCreatedWithinWeek.size() + " posts which have been created Within a week from today");
            postService.print(postsCreatedWithinWeek);
            break;
          }

          case 17: { // Find all posts which have been created Within a month from today
            List<Post> postsCreatedWithinMonth = postService.findPostsCreatedIn(tweetApp.posts, Period.ofMonths(1));
            System.out.println(postsCreatedWithinMonth.size() + " posts which have been created Within a month from today");
            postService.print(postsCreatedWithinMonth);
            break;
          }

          case 18: { // Find all posts of a specific user
            String userName = "Kendra";
            List<Post> postsByUserName = postService.findPostsByUserName(tweetApp.users, tweetApp.posts, userName);
            System.out.println(postsByUserName.size() + " posts of a user with user name: " + userName );
            postService.print(postsByUserName);
            break;
          }

        }
      }

      System.out.print("Would you like to continue demo app? (Y/N): ");
      confirm = (char) System.in.read();
    } while (confirm == 'y' || confirm == 'Y');
  }
}
