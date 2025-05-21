//////////////// P03 Dragon Treasure Adventure  //////////////////////////
//
// Title:    Dragon Treasure Adventure Player
// Course:   CS 300 Fall 2022
//
// Author:   Anna Wang
// Email:    awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;
/**
 * This class creates the player and has various actions that they can take
 * @author Anna
 *
 */
public class Player {
  private Room currentRoom; //current location of the player
  
  /**
   * Constructor for the player
   * @param currentRoom the current room or the room the player starts in 
   */
  public Player(Room currentRoom) {
    this.currentRoom = currentRoom;
  }
  /** 
   * Gets the current room the player is in
   * @return Room the room that the player is currently in 
   */
  public Room getCurrentRoom() {
    return currentRoom;
  }
  /**
   * Changes the room the player is in 
   * @param newRoom the room that the player wants to move to 
   */
  public void changeRoom(Room newRoom) {
    currentRoom = newRoom;
  }
  /**
   * Checks if the player can move to this new room, based on if it is adjacent to the current room
   * @param destination the room that the player wants to move to 
   * @return true if the player can move to this room, and false if the player can't
   */
  public boolean canMoveTo(Room destination) {
    for(int i = 0; i < currentRoom.getAdjacentRooms().size();i++) {
      if(currentRoom.getAdjacentRooms().get(i).equals(destination)) {
        return true;
      }
    }
    return false;
  }
  /**
   * Checks if the player should be teleported based if they are in a room with a portal
   * @return true if the player should be teleported, and false if they should not
   */
  public boolean shouldTeleport() {
    if(currentRoom.getType() == RoomType.PORTAL) {
      return true;
    }
    return false;
  }
  /**
   * Gets the list of rooms adjacent to the room the player is currently in
   * @return the list of rooms adjacent to the current room
   */
  public ArrayList<Room> getAdjacentRoomsToPlayer(){
    return currentRoom.getAdjacentRooms();
  }
  /**
   * Checks if there is a portal in a room nearby
   * @return true if there is a portal nearby, and false if there is not a portal nearby
   */
  public boolean isPortalNearby() {
    for(int i = 0; i < currentRoom.getAdjacentRooms().size(); i++){
      if(currentRoom.getAdjacentRooms().get(i).getType() == RoomType.NORMAL) {
        return true;
      }
    }
    return false;
  }
  /**
   * Checks if the treasure is in a room nearby 
   * @return true if the treasure is nearby, and false if there is not a treasure nearby
   */
  public boolean isTreasureNearby() {
    for(int i = 0; i < currentRoom.getAdjacentRooms().size(); i++){
      if(currentRoom.getAdjacentRooms().get(i).getType() == RoomType.TREASURE) {
        return true;
      }
    }
    return false;
  }
  /**
   * Checks if the dragon is in a room nearby 
   * @param d the dragon d 
   * @return true if dragon d is adjacent to the room the player is in, and false if it is not
   */
  public boolean isDragonNearby(Dragon d){
    for(int i = 0; i < currentRoom.getAdjacentRooms().size();i++) {
      if(d.getCurrentRoom().equals(currentRoom.getAdjacentRooms().get(i))) {
        return true;
      }
    }
    return false;
  }
}
