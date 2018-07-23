package tweetapp.util;

import org.junit.Test;
import tweetapp.model.User;
import tweetapp.service.UserService;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class FileUtilTest {

  /**
   * Test reading file fail
   */
  @Test(expected = IOException.class)
  public void readFileFail() throws IOException {
    FileUtil.readFile("file-not-found", UserService::processUserData);
  }

  /**
   * Test reading file and return data
   */
  @Test
  public void readFileSuccess() throws IOException {
    String userFile = "./src/test/resources/users-test.csv";
    List<User> users = FileUtil.readFile(userFile, UserService::processUserData);
    assertEquals(7, users.size());
  }
}