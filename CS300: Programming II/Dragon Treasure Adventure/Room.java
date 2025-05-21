//////////////// P03 Dragon Treasure Adventure  //////////////////////////
//
// Title:    Dragon Treasure Adventure Room
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
 * This class represents a room in the cave in the game and some of its features
 * @author Anna
 *
 */
public class Room{
  private RoomType type; //one of the four types a room could be
  private String roomDescription; //a brief description of the room
  private ArrayList<Room> adjRooms; //arraylist that holds the rooms adjacent
  private final int ID; //unique ID for each room to identify it
  private static int teleportLocationID; //place where all portal rooms will go to
  private static final String PORTAL_WARNING = "You feel a distortion in space nearby.\n";
  private static final String TREASURE_WARNING = "You sense that there is treasure nearby.\n";
  
  /**
   * constructor for a room 
   * @param id the ID of the room
   * @param roomDescription a description of the room
   */
  public Room(int id, String roomDescription) {
    this.ID = id;
    this.roomDescription = roomDescription;
    adjRooms = new ArrayList<Room>();
    type = RoomType.NORMAL;
  }
 
  /** Determines if the given object is equal to this room.
  * They are equal if other is a Room and their IDs are the same.
  * @param other, another object to check if it is equal to this
  * @return true if the two rooms are equal, false otherwise
  * @author Michelle */
  @Override
  public boolean equals(Object other){
    if(other instanceof Room) {
      Room otherRoom = (Room)other;
      return this.ID == otherRoom.ID;
    }
    return false;
  }
  
  /** Returns a String representation of this room.
  * @return the string representation of this room and
  * it’s object data field values
  * @author Michelle*/
  @Override
  public String toString(){
    String s = this.ID +": " + this.roomDescription+ " (" + type +")\n Adjacent Rooms: ";
    for(int i = 0; i<adjRooms.size(); i++){
      s+= adjRooms.get(i).ID +" ";
    }
    return s;
  }
  /**
   * Gets the rooms adjacent to the current room.
   * @return a list of all the rooms adjacent to the current room
   */
  public ArrayList<Room> getAdjacentRooms(){
    return adjRooms;
  }
  /**
   * Gets the ID of the room that calls this method.
   * @return the ID of the room that calls this method
   */
  public int getID() {
    return ID;
  }
  /**
   * Gets the portal warning when there is a portal nearby.
   * @return the string that is the room class's portal warning, to say that there is one nearby
   */
  public static String getPortalWarning() {
    return PORTAL_WARNING;
  }
  /**
   * Gets the room description that is initially passed in as an argument when creating the room.
   * @return the string that holds the room's description
   */
  public String getRoomDescription() {
    return roomDescription;
  }
  /**
   * Gets the teleport ID of the room all the portals will teleport the player to.
   * @return the ID of the room all the portal rooms will teleport the player to
   */
  public static int getTeleportationRoom() {
    return teleportLocationID;
  }
  /**
   * Gets the treasure warning when the treasure is nearby.
   * @return the string that is the treasure warning to say that the treasure is nearby
   */
  public static String getTreasureWarning() {
    return TREASURE_WARNING;
  }
  /**
   * Get the type of room that called this method - if it is Normal, Start, Portal, or Treasure.
   * @return the RoomType of the room that called this method
   */
  public RoomType getType() {
    return type;
  }
  /**
   * Changes the RoomType of the room that calls this method to the new room type.
   * @param newType the new room type 
   */
  public void setRoomType(RoomType newType) {
    this.type = newType;
  }
  /**
   * Adds the toAdd room to the room that called this method's adjacent rooms list.
   * @param toAdd the room to add to the adjacent rooms list
   */
  public void addToAdjacentRooms(Room toAdd) {
    adjRooms.add(toAdd);  
  }
  /**
   * Checks if the room r is in the room that called this methods' adjacent rooms list.
   * @param r the room that is checked to see if it is in the adjacent rooms list
   * @return true if the room is adjacent and false if the room is not 
   */
  public boolean isAdjacent(Room r) {
    for(int i = 0; i < adjRooms.size(); i++) {
      if(adjRooms.get(i) == r) {
        return true;
      }
    }
    return false;
  }
  /**
   * Sets the class field teleportLocation to the teleportID given.
   * @param teleportID the ID that all the portals should teleport to
   */
  public static void assignTeleportLocation(int teleportID) {
    Room.teleportLocationID = teleportID;
    
  }
  
}
