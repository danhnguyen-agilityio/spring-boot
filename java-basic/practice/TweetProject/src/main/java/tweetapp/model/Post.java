package tweetapp.model;

import java.time.LocalDateTime;

/**
 * Post class
 */
public class Post {
  private String id;
  private String authorId;
  private String message;
  private String commentsCount;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private String version;

  public Post() {}

  public Post(String id, String authorId, String message, String commentsCount, LocalDateTime createdAt,
              LocalDateTime modifiedAt, String version) {
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(LocalDateTime modifiedAt) {
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
    return "Post{" +
        "id='" + id + '\'' +
        ", authorId='" + authorId + '\'' +
        ", message='" + message + '\'' +
        ", commentsCount='" + commentsCount + '\'' +
        ", createdAt=" + createdAt +
        ", modifiedAt=" + modifiedAt +
        ", version='" + version + '\'' +
        '}';
  }
}
