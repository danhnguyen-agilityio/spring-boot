package com.packtpub.restwithspring;

import javax.persistence.*;
import java.util.List;

@Entity(name = "room-categories")
public class RoomCategory {
  private long id;
  private List<Room> rooms;
  private String name;
  private String description;

  public RoomCategory() {

  }

  @Id
  @GeneratedValue
  public long getId() {
    return id;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public List<Room> getRooms() {
    return rooms;
  }

  @Column(name = "name", unique = true, nullable = false, length = 128)
  public String getName() {
    return name;
  }

  @Column(name = "desciption")
  public String getDescription() {
    return description;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setRooms(List<Room> rooms) {
    this.rooms = rooms;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
