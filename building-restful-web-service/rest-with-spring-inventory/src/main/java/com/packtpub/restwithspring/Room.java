package com.packtpub.restwithspring;

import javax.persistence.*;

@Entity(name = "rooms")
public class Room {
  private long id;
  private RoomCategory roomCategory;
  private String name;
  private String description;

  public Room() {

  }

  @Id
  @GeneratedValue
  public long getId() {
    return id;
  }

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
  public RoomCategory getRoomCategory() {
    return roomCategory;
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

  public void setRoomCategory(RoomCategory roomCategory) {
    this.roomCategory = roomCategory;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
