//////////////// P05 Dragon Treasure Adventure 2.0//////////////////////////
// Title:    P05 Room
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
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
/**
 * Class to represent a room of the game
 *
 */
public class Room extends Object{
  private String description; //verbal description of the room
  private ArrayList<Room> adjRooms; //list of all rooms directly connect
  private final int ID; // a "unique" identifier for each room
  protected static PApplet processing; //PApplet object which the rooms will use to
  //draw stuff to the GUI
  private PImage image; //stores the image that corresponds to the background of a room
  /**
   * Constructor for the room object. Initializes all the instance data fields
   * @param ID the ID of the room
   * @param description the description of the room
   * @param image the image that should be used as the background when drawing the room
   */
  public Room(int ID, String description, processing.core.PImage image) {
    this.ID = ID;
    this.description = description;
    this.image = image;
    this.adjRooms = new ArrayList<Room>();

  }
  /**
   * Getter for the ID of the room
   * @return ID of the room
   */
  public int getID() {
    return ID;
  }
  /**
   * Getter for the description of the room
   * @return the description of the room
   */
  public String getDescription() {
    return description;
  }
  /**
   * Getter for the list of adjacent rooms
   * @return the list of adjacent rooms
   */
  public ArrayList<Room> getAdjacentRooms(){
    return adjRooms;
  }
  /**
   * Sets the processing for the class
   * @param processing the PApplet that this room will use to draw to the window
   */
  public static void setProcessing(processing.core.PApplet processing) {
    Room.processing = processing;
  }
  /**
   * Adds the room toAdd to this room's list of adjacent rooms 
   * @param toAdd the room to add to this room's list of adjacent rooms
   */
  public void addToAdjacentRooms(Room toAdd) {
    adjRooms.add(toAdd);
  }
  /**
   * Checks if the room r is adjacent to this room
   * @param r the room that is checked if it is adjacent to this room
   * @return true if the room r is adjacent to this room, false otherwise
   */
  public boolean isAdjacent(Room r) {
    for(int i = 0; i < adjRooms.size(); i++) {
      if(r == adjRooms.get(i)) {
        return true;
      }
    }
    return false;
    
  }
  /**
   * Overrides Object's equals method 
   * @param other the object to check against this room
   * @return true if other is type Room and has the same ID, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if(other instanceof Room) {
      Room otherRoom = (Room)other;
      return this.ID == otherRoom.ID;
    }
    
    return false;

  }
  /** Overrides Object's toString() and determines if two objects are equal
   * @return string representation of the room
   */
  @Override
  public String toString() {
    String s = this.ID +": " + this.description+ "\n Adjacent Rooms: ";
    for(int i = 0; i<adjRooms.size(); i++)
    {
      s+= adjRooms.get(i).getID() +" ";
    }
    
    return s;
  }
  /**
   * Draws this room to the window by drawing the background image, a rectangle, and some text
   */
  public void draw() {
    processing.image(this.image, 0, 0);
    processing.fill(-7028);
    processing.rect(0, 500, 800, 600);
    processing.fill(0);
    processing.text(toString(), 300, 525);
  }
}