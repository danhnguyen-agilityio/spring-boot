package tweetapp.comparator;

import tweetapp.model.User;

import java.util.Comparator;

/**
 * FirstNameComparator used to sort list user by first name
 */
public class FirstNameComparator implements Comparator<User> {

  @Override
  public int compare(User o1, User o2) {
    return o1.getFirstName().compareTo(o2.getFirstName());
  }
}
