package tweetapp.comparator;

import tweetapp.model.Post;

import java.util.Comparator;

/**
 * CreatedPostComparator used to sort list user by time created post
 */
public class CreatedPostComparator implements Comparator<Post> {

  @Override
  public int compare(Post o1, Post o2) {
    return o1.getCreatedAt().compareTo(o2.getCreatedAt());
  }
}
