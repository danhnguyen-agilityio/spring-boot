package tweetapp.model;

import java.time.LocalDateTime;

public class UserBuilder {
  private final User user;

  public UserBuilder() {
    user = new User();
  }

  public static UserBuilder user() {
    return new UserBuilder();
  }

  public UserBuilder withId(String id) {
    user.setId(id);
    return this;
  }

  public UserBuilder withUsername(String username) {
    user.setUsername(username);
    return this;
  }

  public UserBuilder withFirstName(String firstName) {
    user.setFirstName(firstName);
    return this;
  }

  public UserBuilder withLastName(String lastName) {
    user.setLastName(lastName);
    return this;
  }

  public UserBuilder withAvatarUrl(String avatarUrl) {
    user.setAvatarUrl(avatarUrl);
    return this;
  }

  public UserBuilder withNickName(String nickname) {
    user.setNickname(nickname);
    return this;
  }

  public UserBuilder withPhone(String phone) {
    user.setPhone(phone);
    return this;
  }

  public UserBuilder withEmail(String email) {
    user.setEmail(email);
    return this;
  }

  public UserBuilder withAddress(String address) {
    user.setAddress(address);
    return this;
  }

  public UserBuilder withGender(Gender gender) {
    user.setGender(gender);
    return this;
  }

  public UserBuilder withBirthday(LocalDateTime birthday) {
    user.setBirthday(birthday);
    return this;
  }

  public UserBuilder withDescription(String description) {
    user.setDescription(description);
    return this;
  }

  public UserBuilder withCreatedAt(LocalDateTime createdAt) {
    user.setCreatedAt(createdAt);
    return this;
  }

  public UserBuilder withModifiedAt(LocalDateTime modifiedAt) {
    user.setModifiedAt(modifiedAt);
    return this;
  }

  public UserBuilder withVersion(String version) {
    user.setVersion(version);
    return this;
  }

  public User build() {
    return user;
  }

}
