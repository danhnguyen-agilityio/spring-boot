package tweetapp.model;

import java.time.LocalDateTime;

/**
 * Builder for Post class
 */
public class PostBuilder {
  private final Post post;

  private PostBuilder() {
    post = new Post();
  }

  public static PostBuilder post() {
    return new PostBuilder();
  }

  public PostBuilder withId(String id) {
    post.setId(id);
    return this;
  }

  public PostBuilder withAuthorId(String authorId) {
    post.setAuthorId(authorId);
    return this;
  }

  public PostBuilder withMessage(String message) {
    post.setMessage(message);
    return this;
  }

  public PostBuilder withCommentsCount(String commentsCount) {
    post.setCommentsCount(commentsCount);
    return this;
  }

  public PostBuilder withCreatedAt(LocalDateTime createdAt) {
    post.setCreatedAt(createdAt);
    return this;
  }

  public PostBuilder withModifiedAt(LocalDateTime modifiedAt) {
    post.setModifiedAt(modifiedAt);
    return this;
  }

  public PostBuilder withVerion(String version) {
    post.setVersion(version);
    return this;
  }

  public Post build() {
    return post;
  }
}
