package tweetapp;

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

  /**
   * Get list user from file csv
   */
  static List<User> getUsers() {
    List<User> users = new ArrayList<>();
    String csvFile ="./src/main/source/users.csv";
    String line = "";
    String csvSplitBy = ",";
    boolean headerRow = true;

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
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
        String desciption = data[15];
        String createdAt = data[16];
        String modifiedAt = data[17];
        String version = data[18];

        // Create instance User
        User user = new User(id, username, firstName, lastName, avatarUrl, nickname, email, phone, address, gender, birthday,
            desciption, createdAt, modifiedAt, version);

        users.add(user);
      }
      return users;
    } catch (IOException e) {
      e.printStackTrace();
//      FIXME:: Should throw exception or return empty array
      return new ArrayList<>();
    }
  }

  /**
   * Count all users
   */
  public int countAllUser() {
    return users.size();
  }

  public static void main(String[] args) {
    TweetApp tweetApp = new TweetApp();
    tweetApp.users = tweetApp.getUsers();
    int totalUsers = tweetApp.countAllUser();
    System.out.println("Count all user: " + totalUsers);
  }
}
