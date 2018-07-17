package tweetapp;

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
      String id = data[0];
      String username = data[2];
      String firstName = data[3];
      String lastName = data[4];
      String avatarUrl = data[6];
      String nickname = data[7];
      String email = data[8];
      String phone = data[9];
      String address = data[10];
      String gender = data[13];
      String birthday = data[14];
      String description = data[15];
      String createdAt = data[16];
      String modifiedAt = data[17];
      String version = data[18];

      // Create instance User
      User user = new User(id, username, firstName, lastName, avatarUrl, nickname, email, phone, address, gender, birthday,
          description, createdAt, modifiedAt, version);

      users.add(user);
    }
    return users;
  }

  /**
   * Process post data and return list post
   * @param br
   * @return Return list post
   * @throws IOException
   */
  private static List<Post> processPostData(BufferedReader br) throws IOException {
    String line = "";
    String csvSplitBy = ",";
    boolean headerRow = true;

    List<Post> posts = new ArrayList<>();

    while ((line = br.readLine()) != null) {

      // Ignore first line because it is header row
      if (headerRow) {
        headerRow = false;
        continue;
      }

      // Split line to array data
      String[] data = line.split(csvSplitBy);
      String id = data[0];
      String authorId = data[2];
      String message = data[22];
      String commentsCount = data[23];
      String createdAt = data[25];
      String modifiedAt = data[26];
      String version = data[27];

      // Create instance Post
      Post post = new Post(id, authorId, message, commentsCount, createdAt, modifiedAt, version);

      // Add post to list
      posts.add(post);
    }
    return posts;
  }

  /**
   * Get all list user from csv file
   * @return All info users
   * @throws IOException
   */
  public List<User> getUsers() throws IOException {
    String csvFile ="./src/main/resources/users.csv";
    return FileUtil.readFile(csvFile, TweetApp::processUserData);
  }

  /**
   * Get all post from csv file
   * @return All info posts
   * @throws IOException
   */
  public List<Post> getPosts() throws IOException {
    String csvFile ="./src/main/resources/posts.csv";
    return FileUtil.readFile(csvFile, TweetApp::processPostData);
  }

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

  public static void main(String[] args) throws IOException {
    TweetApp tweetApp = new TweetApp();
    tweetApp.users = tweetApp.getUsers();
    tweetApp.posts = tweetApp.getPosts();

    long totalUsers = tweetApp.countAllUser();
    System.out.println("Count all user: " + totalUsers);

    long totalPost = tweetApp.countAllPost();
    System.out.println("Count all post: " + totalPost);
  }
}
