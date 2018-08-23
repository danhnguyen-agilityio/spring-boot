package com.packtpub.restwithspring;

import javax.persistence.*;
import java.util.List;

@Entity(name = "room_category")
public class RoomCategory {

  @Id
  @GeneratedValue
  private long id;

  @OneToMany(mappedBy = "roomCategory", fetch = FetchType.EAGER)
  @OrderBy("name asc")
  private List<Room> rooms;

  @Column(name = "name", unique = true, nullable = false, length = 128)
  private String name;

  @Column(name = "description")
  private String description;

  public RoomCategory() {

  }

  public long getId() {
    return id;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public String getName() {
    return name;
  }

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
