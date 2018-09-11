package com.packtpub.restwithspring;

import java.util.List;

public interface InventoryService {
  Room getRoom(long roomId);

  RoomCategory getRoomCategory(long categoryId);

  List<Room> getAllRoomsWithCategory(RoomCategory category);
}
