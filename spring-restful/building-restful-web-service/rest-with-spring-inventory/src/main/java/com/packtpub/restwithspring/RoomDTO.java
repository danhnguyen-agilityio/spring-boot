package com.packtpub.restwithspring;

import java.io.Serializable;

public class RoomDTO implements Serializable {

  private long id;
  private String name;
  private long roomCategoryId;
  private String description;

  public RoomDTO(Room room) {
    this.id = room.getId();
    this.name = room.getName();
    this.roomCategoryId = room.getRoomCategory().getId();
    this.description = room.getDescription();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getRoomCategoryId() {
    return roomCategoryId;
  }

  public void setRoomCategoryId(long roomCategoryId) {
    this.roomCategoryId = roomCategoryId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
