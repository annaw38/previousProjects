//////////////// P03 Dragon Treasure Adventure  //////////////////////////
//
// Title:    Dragon Treasure Adventure Tester
// Course:   CS 300 Fall 2022
//
// Author:   Anna Wang
// Email:    awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/815 - this post helped me see which
//                  rooms need to be tested and for what if it does, 
//                  https://piazza.com/class/l7f41s35yau64i/post/845 - this post helped me figure 
//                  out what to do for the gradescope error that I got, and 
//                  https://piazza.com/class/l7f41s35yau64i/post/846 - this post helped me 
//                  understand the isAdjacent() method.
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This class tests the methods created in the other classes
 * @author Anna
 */
public class DragonTreasureTester {
  /**
   * Checks the correctness of the instance methods of the room class
   * @return true if all of them are true, and false if any of them are false
   */
  public static boolean testRoomInstanceMethods(){
    try 
    {
      //create new rooms
      Room a = new Room(5, "a plain old room");
      Room b = new Room(11, "a portal room");
      Room c = new Room(1, "where the player starts");
      Room d = new Room(13, "room contains dragon's treasure");
      Room f = new Room(12, "a plain old room");
      
      //add to a's adjacent rooms and set room types
      a.addToAdjacentRooms(b);
      a.addToAdjacentRooms(d);
      a.addToAdjacentRooms(f);
      a.addToAdjacentRooms(c);
      
      b.setRoomType(RoomType.PORTAL);
      c.setRoomType(RoomType.START);
      d.setRoomType(RoomType.TREASURE);
      
      int teleportID = 12;
      b.assignTeleportLocation(teleportID);
      
        
      // check constructor and accessor methods
      if(a.getID() == 5 && a.getRoomDescription().equals("a plain old room")) 
      {
        if(a.getType() == RoomType.NORMAL)
        {
          if(b.getTeleportationRoom() == 12)
          {
            if(a.isAdjacent(b)) 
            {
              if(b.getAdjacentRooms() == null) 
              {
                return true;
              }
            }
          }
        }
      }
    }
    catch(Exception e){
     e.printStackTrace();
     return false;
    }
    return true;
  }
  /**
   * Checks the correctness of the static methods in the room class 
   * @return true if all of them return true, and false if any of them are false
   */
  public static boolean testRoomStaticMethods(){
    //create new rooms
    Room a = new Room(5, "a plain old room");
    Room b = new Room(11, "a portal room");
    Room c = new Room(1, "where the player starts");
    Room d = new Room(13, "room contains dragon's treasure");
    Room e = new Room(12, "a plain old room");
    
    //add to a's adjacent rooms and set room types
    a.addToAdjacentRooms(b);
    a.addToAdjacentRooms(d);
    a.addToAdjacentRooms(e);
    a.addToAdjacentRooms(c);
    
    b.setRoomType(RoomType.PORTAL);
    c.setRoomType(RoomType.START);
    d.setRoomType(RoomType.TREASURE);
    
    //testing the static methods
    int teleportID = 12;
    b.assignTeleportLocation(teleportID);
    //System.out.println(d.getTreasureWarning().equals("You sense that there is treasure nearby.\n"));
    //b.getPortalWarning();
    
    if(b.getTeleportationRoom() == teleportID) {
      if(d.getTreasureWarning().equals("You sense that there is treasure nearby.\n")) {
        if(b.getPortalWarning().equals("You feel a distortion in space nearby.\n")) {
          return true;
        }
      }
    }
    return false;
  }
  /**
   * Checks the correctness of PlayerCanMoveTo()
   * @return true if the player can move into the room, and false if the player can't
   */
  public static boolean testPlayerCanMoveTo() {
    //create new rooms and player
    Room rob = new Room(5, "a plain old room");
    Room bob = new Room(11, "a portal room");
    Room dan = new Room(1, "where the player starts");
    Room jan = new Room(13, "room contains dragon's treasure");
    Room ed = new Room(12, "a plain old room");
    
    Player pam = new Player(jan);
    
    //add rooms to rob's adjacent rooms and set room types
    rob.addToAdjacentRooms(bob);
    rob.addToAdjacentRooms(dan);
    rob.addToAdjacentRooms(jan);
    //change room to rob
    pam.changeRoom(rob);
    
    bob.setRoomType(RoomType.PORTAL);
    dan.setRoomType(RoomType.START);
    jan.setRoomType(RoomType.TREASURE);
    
    if(pam.canMoveTo(ed) == false) {
      return true;
    }
    return false;
  }
  /**
   * Checks for the correctness of shouldTeleport()
   * @return true if the player should teleport, and false if the player should not
   */
  public static boolean testPlayerShouldTeleport() {
    //create new rooms and player
    Room rob = new Room(5, "a plain old room");
    Room bob = new Room(11, "a portal room");
    Room dan = new Room(1, "where the player starts");
    Room jan = new Room(13, "room contains dragon's treasure");
    Room ed = new Room(12, "a plain old room");
    
    Player pam = new Player(dan);
    
    //add rooms to rob's adjacent rooms and set room types
    rob.addToAdjacentRooms(bob);
    rob.addToAdjacentRooms(dan);
    rob.addToAdjacentRooms(ed);
    rob.addToAdjacentRooms(jan);
    
    bob.setRoomType(RoomType.PORTAL);
    dan.setRoomType(RoomType.START);
    jan.setRoomType(RoomType.TREASURE);
   
    pam.changeRoom(bob);
    if(pam.shouldTeleport() == true) {
      return true;
    }
    return false;
  }
  /**
   * Checks the correctness of both isPortalNearby() and isTreasureNearby() 
   * @return true if a portal and treasure is nearby, and false if one of them is not nearby
   */
  public static boolean testPlayerDetectNearbyRooms() {
    //create new rooms and player
    Room rob = new Room(5, "a plain old room");
    Room bob = new Room(11, "a portal room");
    Room dan = new Room(1, "where the player starts");
    Room jan = new Room(13, "room contains dragon's treasure");
    Room ed = new Room(12, "a plain old room");
    
    Player pam = new Player(dan);
    
    //set room types and add to adjacent rooms
    bob.setRoomType(RoomType.PORTAL);
    dan.setRoomType(RoomType.START);
    jan.setRoomType(RoomType.TREASURE);
    
    bob.addToAdjacentRooms(rob);
    bob.addToAdjacentRooms(dan);
    bob.addToAdjacentRooms(ed);
    bob.addToAdjacentRooms(jan);
    
    jan.addToAdjacentRooms(rob);
    jan.addToAdjacentRooms(dan);
    jan.addToAdjacentRooms(ed);
    jan.addToAdjacentRooms(bob);
    
    dan.addToAdjacentRooms(rob);
    dan.addToAdjacentRooms(dan);
    dan.addToAdjacentRooms(ed);
    
    pam.changeRoom(jan);
   
    if(pam.isPortalNearby() == true) {
      //System.out.println("a portal's nearby");
      pam.changeRoom(bob);
      if(pam.isTreasureNearby() == true){
        //System.out.println("treasure's nearby");
        return true;
      }
    }
    return false;
  }
  /**
   * Checks the correctness of the dragonChangeRooms() 
   * @return true if the Dragon changes rooms and is not a portal room, false otherwise
   */
  public static boolean testDragonChangeRooms() {
    // create new rooms and dragon
    Room rob = new Room(5, "a plain old room");
    Room bob = new Room(11, "a portal room");
    Room dan = new Room(1, "where the player starts");
    Room jan = new Room(13, "room contains dragon's treasure");
    Room ed = new Room(12, "a plain old room");
    Dragon zak = new Dragon(jan);
    
    //set room types and add to adjacent rooms list
    bob.setRoomType(RoomType.PORTAL);
    dan.setRoomType(RoomType.START);
    jan.setRoomType(RoomType.TREASURE);
    
    bob.addToAdjacentRooms(rob);
    bob.addToAdjacentRooms(dan);
    bob.addToAdjacentRooms(ed);
    bob.addToAdjacentRooms(jan);
    
    jan.addToAdjacentRooms(rob);
    jan.addToAdjacentRooms(ed);
    jan.addToAdjacentRooms(bob);
    
    dan.addToAdjacentRooms(rob);
    dan.addToAdjacentRooms(jan);
    dan.addToAdjacentRooms(ed);
    
    zak.changeRooms();
      
    if(!zak.getCurrentRoom().equals(jan) && !zak.getCurrentRoom().equals(bob)) {
      return true;
    }
    return false;
  }
  /**
   * Checks the correctness of isDragonNearby()
   * @return true if the dragon is nearby, and false if it isn't
   */
  public static boolean testIsDragonNearby() {
    //create new rooms
    Room rob = new Room(5, "a plain old room");
    Room bob = new Room(11, "a portal room");
    Room dan = new Room(1, "where the player starts");
    Room jan = new Room(13, "room contains dragon's treasure");
    Room ed = new Room(12, "a plain old room");
    Room drake = new Room(4, "a plain old room");
    Room tris = new Room(3, "a plain old room");
    Room jon = new Room(2, "a plain old room");
    Room bill = new Room(6, "a plain old room");
    
    //create new player and dragon
    Player pam = new Player(dan);
    Dragon zak = new Dragon(jan);
    
    //set room types and add to adjacent rooms lists
    bob.setRoomType(RoomType.PORTAL);
    dan.setRoomType(RoomType.START);
    jan.setRoomType(RoomType.TREASURE);
    
    
    bob.addToAdjacentRooms(rob);
    bob.addToAdjacentRooms(dan);
    bob.addToAdjacentRooms(ed);
    bob.addToAdjacentRooms(jan);
    
    jan.addToAdjacentRooms(rob);
    jan.addToAdjacentRooms(dan);
    jan.addToAdjacentRooms(bob);
    jan.addToAdjacentRooms(tris);
    
    dan.addToAdjacentRooms(rob);
    dan.addToAdjacentRooms(dan);
    dan.addToAdjacentRooms(ed);
    dan.addToAdjacentRooms(bill);
    
    ed.addToAdjacentRooms(dan);
    ed.addToAdjacentRooms(bob);
    ed.addToAdjacentRooms(jan);
    ed.addToAdjacentRooms(jon);
    
    rob.addToAdjacentRooms(jan);
    rob.addToAdjacentRooms(ed);
    rob.addToAdjacentRooms(bob);
    rob.addToAdjacentRooms(dan);
    rob.addToAdjacentRooms(drake);
    
    tris.addToAdjacentRooms(bill);
    tris.addToAdjacentRooms(drake);
    tris.addToAdjacentRooms(jon);
    tris.addToAdjacentRooms(jan);
    
    jon.addToAdjacentRooms(drake);
    jon.addToAdjacentRooms(bob);
    jon.addToAdjacentRooms(rob);
    
    bill.addToAdjacentRooms(dan);
    bill.addToAdjacentRooms(drake);
    bill.addToAdjacentRooms(tris);
    
    drake.addToAdjacentRooms(jan);
    drake.addToAdjacentRooms(dan);
    drake.addToAdjacentRooms(ed);
    
    zak.changeRooms();
    pam.changeRoom(drake);
    
    
    //System.out.println(zak.getCurrentRoom());
    //System.out.println(pam.getCurrentRoom());
   
    if(pam.isDragonNearby(zak) == true) {
      return true;
      }
    return false;
  }
  /*Main for testing the testers*/
  public static void main(String[] args) {
    System.out.println("testRoomInstanceMethods(): " + testRoomInstanceMethods());
    System.out.println("testRoomStaticMethods(): " + testRoomStaticMethods());
    System.out.println("testPlayerCanMoveTo(): " + testPlayerCanMoveTo());
    System.out.println("testPlayerShouldTeleport(): " + testPlayerShouldTeleport());
    System.out.println("testPlayerDetectNearbyRooms(): " + testPlayerDetectNearbyRooms());
    System.out.println("testDragonChangeRooms(): " + testDragonChangeRooms());
    System.out.println("testIsDragonNearby(): " + testIsDragonNearby());
  }

}
