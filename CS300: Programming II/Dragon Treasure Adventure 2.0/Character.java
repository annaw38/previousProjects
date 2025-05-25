//////////////// P05 Dragon Treasure Adventure 2.0//////////////////////////
// Title:    P05 Character
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
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;
/**
 * Class to represent a character
 *
 */
public class Character {
  private Room currentRoom; //current room the character is in
  private String label; //a label giving a basic description of the character
  
  /**
   * Constructor for a Character object. Initializes all instance fields.
   * @param currentRoom the room that the Character is located in
   * @param label a descriptive label of this Character
   * @throws IllegalArgumentException if current room is null
   */
  public Character(Room currentRoom, String label) {
    //check if the current room is null or not
    if(currentRoom == null) {
      throw new IllegalArgumentException("The current room can not be null");
    }
    this.currentRoom = currentRoom;
    this.label = label;
  }
  /**
   * Getter for the current room of this Character.
   * @return the room where the character is
   */
  public Room getCurrentRoom() {
    return currentRoom;
  }
  /**
   * Getter for the label of this Character.
   * @return this Character's descriptive label
   */
  public String getLabel() {
    return label;
  }
  /**
   * Gets the list of rooms adjacent to this Character.
   * @return an ArrayList of rooms adjacent to this character
   */
  public ArrayList<Room> getAdjacentRooms(){
    return currentRoom.getAdjacentRooms();
  }
  /**
   * Sets the current room to the one given.
   * @param newRoom the room that should become the current room
   */
  public void setCurrentRoom(Room newRoom) {
    currentRoom = newRoom;
  }


}
