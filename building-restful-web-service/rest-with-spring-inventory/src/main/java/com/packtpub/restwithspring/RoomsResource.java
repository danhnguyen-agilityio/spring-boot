package com.packtpub.restwithspring;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomsResource {
  private final InventoryService inventoryService;

  public RoomsResource(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }

  @RequestMapping(value = "/{roomId}", method = RequestMethod.GET)
  public RoomDTO getRoom(@PathVariable("roomId") long id) {
    Room room = inventoryService.getRoom(id);
    return new RoomDTO(room);
  }

  @RequestMapping(value = "/{roomId}", method = RequestMethod.GET,
  consumes = "application/json;version=2")
  public RoomDTOv2 getRoomV2(@PathVariable("roomId") long id) {
    Room room = inventoryService.getRoom(id);
    return new RoomDTOv2(room);
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<RoomDTO> getRoomsInCategory(@RequestParam("categoryId") long categoryId) {
    RoomCategory category = inventoryService.getRoomCategory(categoryId);
    return inventoryService.getAllRoomsWithCategory(category)
        .stream().map(RoomDTO::new).collect(Collectors.toList());
  }
}
