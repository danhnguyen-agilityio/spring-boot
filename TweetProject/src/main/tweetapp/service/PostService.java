package tweetapp.service;

import javafx.geometry.Pos;
import tweetapp.TweetApp;
import tweetapp.model.Post;
import tweetapp.model.User;
import tweetapp.util.DateUtil;
import tweetapp.util.FileUtil;
import tweetapp.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostService {
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
      String id = StringUtil.substringBetween(data[0], "\"");
      String authorId = data[2];
      String message = data[22];
      String commentsCount = data[23];
      LocalDateTime createdAt = DateUtil.convertStringToLocalDateTime(data[25]);
      LocalDateTime modifiedAt = DateUtil.convertStringToLocalDateTime(data[26]);
      String version = data[27];

      // Create instance Post
      Post post = new Post(id, authorId, message, commentsCount, createdAt, modifiedAt, version);

      // Add post to list
      posts.add(post);
    }
    return posts;
  }

  /**
   * Get all post from csv file
   * @return All info posts
   * @throws IOException
   */
  public static List<Post> getPosts() throws IOException {
    String csvFile ="./src/main/resources/posts.csv";
    return FileUtil.readFile(csvFile, PostService::processPostData);
  }

  /**
   * Test whether given user write given post
   * @param post
   * @param user
   * @return true given user write given post or false if other
   */
  public static boolean ownPost(Post post, User user) {
    return post.getAuthorId().equals(user.getId());
  }
}
