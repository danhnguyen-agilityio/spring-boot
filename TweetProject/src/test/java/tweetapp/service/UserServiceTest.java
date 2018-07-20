package tweetapp.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tweetapp.model.User;
import tweetapp.util.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test get users from file
   * @throws IOException
   */
  @Test
  public void getUsersFromFile() throws IOException {
    String userFile ="./src/test/resources/users-test.csv";
    List<User> users = UserService.getUsersFromFile(userFile);

    // Check same size
    long expectedSize = 7;
    long actualSize = users.size();
    assertEquals(expectedSize, actualSize);

    // Check id of first user
    String expectedFirstUserId = "5b4c63aa170bb8185792506c";
    String actualFirstUserId = users.get(0).getId();
    assertEquals(expectedFirstUserId, actualFirstUserId);

    // Check birthday of first user
    LocalDateTime expectedFirstUserBirthday = DateUtil.convertStringToLocalDateTime("1974-01-15T12:47:20.430Z");
    LocalDateTime actualFirstUserBirthday = users.get(0).getBirthday();
    assertEquals(expectedFirstUserBirthday, actualFirstUserBirthday);
  }

  
}