//////////////// P05 Dragon Treasure Adventure 2.0//////////////////////////
// Title:    P05 Player
// Course:   CS 300 Fall 2022
//
// Author:   Anna Wang
// Email:    awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    None
// Partner Email:   None
// Partner Lecturer's Name: None
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:        None
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/1586 - this post helped me figure
//                  out how to make it so that I can call the PortalRoom method
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;

/**
 * Class to represent the player
 *
 */
public class Player extends Character implements Moveable {
  private boolean hasKey; //player has key to unlock the treasure
  /**
   * Constructor for player object.
   * @param currentRoom  the room that the player should start in
   * @throws IllegalArgumentException if the currentRoom is not a StartRoom
   */
  public Player(Room currentRoom) {
    super(currentRoom, "PLAYER");
    if(!(currentRoom instanceof StartRoom)) {
        throw new IllegalArgumentException("The current room is not a start room");
    }
    hasKey = false;
  }
  /**
   * Determines if the player has the key.
   * @return true if the player has the key, false otherwise
   */
  public boolean hasKey() {
    if(hasKey == true) {
      return true;
    }
    return false;
  }
  /**
   * Gives player the key.
   */
  public void obtainKey() {
    hasKey = true;
  }
  
  /**
   * Moves the Player to the destination room.
   * @param destination the Room to change the player to
   * @return true if the change was successful, false otherwise
   */
  @Override
  public boolean changeRoom(Room destination) {
    //call the canMoveTo method to see if the player can move to the destination
    if(canMoveTo(destination) == true) {
      super.setCurrentRoom(destination);
      //System.out.println("Room changed"); //debug line
      if(super.getCurrentRoom().equals(destination)) {
        //System.out.println("Player is in new room"); //debug line
        return true;
      }
    }
    return false;
  }
  /**
   * Checks if the player can move to the given destination.
   * @param destination the room to check if the player can move towards
   * @return true if they can, false otherwise
   */
  @Override
  public boolean canMoveTo(Room destination) {
    if(getCurrentRoom().isAdjacent(destination)) {
      return true;
    }
    return false;
  }
  /**
   * Checks if the player needs to teleport and move them if needed.
   * @return true if a teleport occurred, false otherwise
   */
  public boolean teleport() {
    if(getCurrentRoom() instanceof PortalRoom) {
      changeRoom(((PortalRoom)getCurrentRoom()).getTeleportLocation());
      System.out.println("You successfully teleported to a new room!");
      return true;
    }
    return false;
  }
  /**
   * Determines whether or not the given dragon is nearby. 
   * @param d the dragon to check if nearby
   * @return true if the dragon is nearby, false otherwise
   */
  public boolean isDragonNearby(Dragon d) {
  if(getCurrentRoom().isAdjacent(d.getCurrentRoom())) {
    return true;
  }
  return false;
  }
  /**
   * Determines whether or not a portal room is nearby.
   * @return true if a portal room is nearby, false otherwise
   */
  public boolean isPortalNearby() {
    ArrayList<Room> adjRooms = getCurrentRoom().getAdjacentRooms();
    for(int i = 0; i < adjRooms.size(); i++) {
      if(adjRooms.get(i) instanceof PortalRoom) {
        return true;
      }
    }
    return false;
  }
  /**
   * Determines whether or not the treasure room is nearby.
   * @return true if the treasure room is nearby, false otherwise
   */
  public boolean isTreasureNearby() { 
    ArrayList<Room> adjRooms = getCurrentRoom().getAdjacentRooms();
    for(int i = 0; i < adjRooms.size(); i++) {
      if(adjRooms.get(i) instanceof TreasureRoom) {
        return true;
      }
    }
    return false;
  }  
}