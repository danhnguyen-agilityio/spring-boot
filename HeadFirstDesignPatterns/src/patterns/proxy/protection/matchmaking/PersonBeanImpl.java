package patterns.proxy.protection.matchmaking;

public class PersonBeanImpl implements PersonBean {
  String name;
  String gender;
  String interests;
  int rating;
  int ratingCount;

  public PersonBeanImpl(String name, String gender, String interests, int rating, int ratingCount) {
    this.name = name;
    this.gender = gender;
    this.interests = interests;
    this.rating = rating;
    this.ratingCount = ratingCount;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getRender() {
    return gender;
  }

  @Override
  public String getInterests() {
    return interests;
  }

  @Override
  public int getHotOrNotRating() {
    if (ratingCount == 0) return  0;
    return rating / ratingCount;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public void setInterests(String interests) {
    this.interests = interests;
  }

  @Override
  public void setHotOrNotRating(int rating) {
    this.rating += rating;
    ratingCount++;
  }
}
