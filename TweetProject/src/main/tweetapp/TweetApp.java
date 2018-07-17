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
import java.util.List;

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
  public long countFemaleUser() {
    return users.stream().filter(UserService::isFemaleUser).count();
  }

  public static void main(String[] args) throws IOException {
    TweetApp tweetApp = new TweetApp();
    tweetApp.users = UserService.getUsers();
    tweetApp.posts = PostService.getPosts();

    long totalUsers = tweetApp.countAllUser();
    System.out.println("Count all user: " + totalUsers);

    long totalPost = tweetApp.countAllPost();
    System.out.println("Count all post: " + totalPost);

    long totalFemaleUsers = tweetApp.countFemaleUser();
    System.out.println("Count all female user: " + totalFemaleUsers);
  }
}
