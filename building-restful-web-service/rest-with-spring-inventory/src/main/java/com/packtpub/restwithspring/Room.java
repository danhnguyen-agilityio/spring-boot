package com.packtpub.restwithspring;

import javax.persistence.*;

@Entity(name = "room")
public class Room {

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne
  @JoinColumn(name = "room_category_id")
  private RoomCategory roomCategory;

  @Column(name = "name", unique = true, nullable = false, length = 128)
  private String name;

  @Column(name = "description")
  private String description;

  public Room() {

  }

  public long getId() {
    return id;
  }

  public RoomCategory getRoomCategory() {
    return roomCategory;
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
