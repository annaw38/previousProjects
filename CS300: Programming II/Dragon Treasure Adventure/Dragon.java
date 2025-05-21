//////////////// P03 Dragon Treasure Adventure  //////////////////////////
//
// Title:    Dragon Treasure Adventure Dragon
// Course:   CS 300 Fall 2022
//
// Author:   Anna Wang
// Email:    awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/870 - this piazza post helped me 
//                  by realizing that I can create an ArrayList of all the non portal rooms, so I 
//                  do not have to worry about the dragon going into a portal room.
//
///////////////////////////////////////////////////////////////////////////////
//import java.util.ArrayList;
import java.util.Random;
import java.util.ArrayList;
/**
 * This class creates the fire breathing dragon and the various features it has
 * @author Anna
 *
 */
public class Dragon {
  private Room currentRoom; //current location of the dragon
  private Random randGen; //random num generator used for moving
  private static final String DRAGON_WARNING = "You hear a fire breathing nearby!\n";
  
  /**
   * Constructor for the dragon 
   * @param currentRoom the current room or the room the dragon starts in
   */
  public Dragon(Room currentRoom) {
    this.currentRoom = currentRoom;
    randGen = new Random();
  }
  /**
   * Gets the room the dragon is currently in 
   * @return Room the room the dragon is in
   */
  public Room getCurrentRoom() {
    return currentRoom;
  }
  /**
   * Randomly picks the room the dragon will move to from a list of room(s) that the dragon can be 
   * in and moves it there. 
   * 
   */
  public void changeRooms() {
    ArrayList<Room> nonPortalRooms = new ArrayList<Room>();
    for(int i = 0; i < currentRoom.getAdjacentRooms().size();i++) {
      if(currentRoom.getAdjacentRooms().get(i).getType() != RoomType.PORTAL) {
        nonPortalRooms.add(currentRoom.getAdjacentRooms().get(i));
      }
    }
    int randRoom = randGen.nextInt(nonPortalRooms.size());
    if(nonPortalRooms.get(randRoom).getType() == RoomType.PORTAL ||
        !currentRoom.isAdjacent(nonPortalRooms.get(randRoom))) {
      randRoom = randGen.nextInt(nonPortalRooms.size());
    }
    else{
     currentRoom = nonPortalRooms.get(randRoom);
    }
  }
  /**
   * Gets the dragon warning for when the dragon is nearby
   * @return String a warning that the player hears a fire breathing dragon nearby
   */
  public static String getDragonWarning() {
    return DRAGON_WARNING;
  }
}
