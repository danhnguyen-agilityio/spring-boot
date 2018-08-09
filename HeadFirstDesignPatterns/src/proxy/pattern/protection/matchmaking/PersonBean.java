package proxy.pattern.protection.matchmaking;

public interface PersonBean {
  String getName();
  String getRender();
  String getInterests();
  int getHotOrNotRating();

  void setName(String name);
  void setGender(String gender);
  void setInterests(String interests);
  void setHotOrNotRating(int rating);

}
