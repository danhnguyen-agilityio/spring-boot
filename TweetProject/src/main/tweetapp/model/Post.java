package tweetapp.model;

import javafx.geometry.Pos;

/**
 * Post class contain info about posts
 */
public class Post {
  private String id;
  private String authorId;
  private String message;
  private String commentsCount;
  private String createdAt;
  private String modifiedAt;
  private String version;

  public Post(String id, String authorId, String message, String commentsCount, String createdAt, String modifiedAt, String version) {
    this.id = id;
    this.authorId = authorId;
    this.message = message;
    this.commentsCount = commentsCount;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
    this.version = version;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCommentsCount() {
    return commentsCount;
  }

  public void setCommentsCount(String commentsCount) {
    this.commentsCount = commentsCount;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(String modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public String toString() {
    System.out.println("Info post with id: " + id);
    System.out.println("id: " + id);
    System.out.println("authorId: " + authorId);
    System.out.println("message: " + message);
    System.out.println("commentsCount: " + commentsCount);
    System.out.println("createdAt: " + createdAt);
    System.out.println("modifiedAt: " + createdAt);
    System.out.println("version: " + version);
    return null;
  }
}
