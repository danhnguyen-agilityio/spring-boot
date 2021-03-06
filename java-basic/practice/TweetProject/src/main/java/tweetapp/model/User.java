package tweetapp.model;

import java.time.LocalDateTime;

/**
 * User class
 */
public class User {
  private String id;
  private String username;
  private String firstName;
  private String lastName;
  private String avatarUrl;
  private String nickname;
  private String email;
  private String phone;
  private String address;
  private Gender gender;
  private LocalDateTime birthday;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private String version;

  /**
   * Constructor default
   */
  public User() {}

  /**
   * Constructor with given parameters
   */
  public User(String id, String username, String firstName, String lastName, String avatarUrl, String nickname,
      String email, String phone, String address, Gender gender, LocalDateTime birthday, String description,
      LocalDateTime createdAt, LocalDateTime modifiedAt, String version) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.avatarUrl = avatarUrl;
    this.nickname = nickname;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.gender = gender;
    this.birthday = birthday;
    this.description = description;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public LocalDateTime getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDateTime birthday) {
    this.birthday = birthday;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  /**
   * Check whether user is female
   * @return true if female user or false if reverse
   */
  public boolean isFemale() {
    return Gender.FEMALE.equals(gender);
  }

  /**
   * Check whether user is female
   * @return true if male user or false if reverse
   */
  public boolean isMale() {
    return Gender.MALE.equals(gender);
  }

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        ", username='" + username + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        ", nickname='" + nickname + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", address='" + address + '\'' +
        ", gender=" + gender +
        ", birthday=" + birthday +
        ", description='" + description + '\'' +
        ", createdAt=" + createdAt +
        ", modifiedAt=" + modifiedAt +
        ", version='" + version + '\'' +
        '}';
  }
}
