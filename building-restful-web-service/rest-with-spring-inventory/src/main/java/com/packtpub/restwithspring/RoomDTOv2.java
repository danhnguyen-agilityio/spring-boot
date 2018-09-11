package com.packtpub.restwithspring;

import java.io.Serializable;

public class RoomDTOv2 implements Serializable {

  private long id;
  private String name;

  public RoomDTOv2(Room room) {
    this.id = room.getId();
    this.name = room.getName();
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

}
