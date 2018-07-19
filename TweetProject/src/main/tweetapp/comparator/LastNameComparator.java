package tweetapp.comparator;

import tweetapp.model.User;

import java.util.Comparator;

/**
 * LastNameComparator used to sort list user by last name
 */
public class LastNameComparator implements Comparator<User> {

  @Override
  public int compare(User o1, User o2) {
    return o1.getLastName().compareTo(o2.getLastName());
  }
}
