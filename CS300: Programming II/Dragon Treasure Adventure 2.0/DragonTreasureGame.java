//////////////// P05 Dragon Treasure Adventure 2.0//////////////////////////
// Title:    P05 Dragon Treasure Game
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
// Persons:        Alan and Jonah - helped me with the printing the win/lose statements once instead
//                 of infinitely
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/1601 - this post helped me with 
//                 keyPressed and how to turn it into an int from a char
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;
import java.util.Scanner;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;
import java.io.IOException;
/**
 * Class to represent the Dragon Treasure Game
 *
 */
public class DragonTreasureGame extends PApplet{
  private ArrayList<Room> roomList;
  private ArrayList<Character> characters;
  private File roomInfo;
  private File mapInfo;
  private boolean isDragonTurn;
  private int gameState;
  /**
   * Overriden settings method for the window
   */
  @Override 
  public void settings() {
    size(800,600);
  }
  /**
   * Overriden setup method for the game
   */
  @Override
  public void setup() {
    this.getSurface().setTitle("Dragon Treasure Adventure"); // sets the title of the window
    this.imageMode(PApplet.CORNER); //Images are drawn using the x,y-coordinate
    //as the top-left corner
    this.rectMode(PApplet.CORNERS); //When drawing rectangles interprets args
    //as top-left corner and bottom-right corner respectively
    this.focused = true; // window will be active upon running program
    this.textAlign(CENTER); // sets the text alignment to center
    this.textSize(20); //sets the font size for the text
    roomList = new ArrayList<Room>(); //set roomList to an empty array
    Room.setProcessing(this); //setup for room's processing
    roomInfo = new File("roominfo.txt"); //create new file objects for roominfo and map
    mapInfo = new File("map.txt");
    PImage treasure = loadImage("images/treasure.jpg"); //create new PImages for treasure and portal
    PImage portal = loadImage("images/portal.png");
    TreasureRoom.setTreasureBackground(treasure); //set the backgrounds for treasure and portal
    PortalRoom.setPortalImage(portal);
    loadRoomInfo(); //load roominfo and map
    loadMap();
    characters = new ArrayList<Character>(); //initialize characters arraylist
    loadCharacters(); //load the characters
    isDragonTurn = false; //default - player goes first
    gameState = 0; //default - game should continue
    
  }
  /**
   * Overridden method that draws the room
   */
  @Override
  public void draw() {
    //roomList.get(2).draw(); //test loadRoominfo and loadmap
   //characters.get(2).getCurrentRoom().draw(); //test loadcharacters
    //if the gameState is 1 or 2 then print win/lose statement once before exiting draw
    if(gameState != 0) {
      return;
    }
    //index of the player
    int pIndex = 0;
    //index of dragon
    int dIndex = 0;
    //index of keyholder
    int kIndex = 0;
    //find the player, dragon, and keyholder's index
    //in characters arraylist and draw the player's current room
    for(int i = 0; i < characters.size(); i++) {
      if(characters.get(i).getLabel().equals("PLAYER")) {
        pIndex = i;
        //System.out.println("player is in room 1"); //debug line
        characters.get(i).getCurrentRoom().draw();
      }
      if(characters.get(i).getLabel().equals("DRAGON")) {
        dIndex = i;
      }
      if(characters.get(i).getLabel().equals("KEYHOLDER")) {
        kIndex = i;
      }
    }
    //player, dragon, and keyholder references
    Player p = (Player) characters.get(pIndex);
    Dragon d = (Dragon) characters.get(dIndex);
    Character k = characters.get(kIndex);
    //check if a portal is nearby
    if(p.isPortalNearby() == true) {
      System.out.println(PortalRoom.getPortalWarning());
    }
    //check if treasure is nearby
    if(p.isTreasureNearby() == true) {
      System.out.println(TreasureRoom.getTreasureWarning());
    }
    //check if a dragon is nearby
    if(p.isDragonNearby(d) == true) {
      System.out.println(Dragon.getDragonWarning());
    }
    //check if the player is in the same room as the keyholder to get the key to the treasure
    if(p.getCurrentRoom().equals(k.getCurrentRoom()) == true && p.hasKey() == false) {
      p.obtainKey();
      System.out.println("KEY OBTAINED");
    }
    
    //check if the player needs to teleport 
    if(p.getCurrentRoom() instanceof PortalRoom) {
      if(p.teleport() == true) {
        System.out.println(PortalRoom.getTeleportMessage());
      }
    }
    //check if it's the dragon's turn
    if(isDragonTurn == true) {
      if(d.changeRoom(d.pickRoom()) == true) {
        isDragonTurn = false;
      }
    }
    
    //check find treasure room in the roomList then see if the player can get the treasure and 
    //change gamestate 
    for(int i = 0; i < roomList.size(); i++) {
      if(roomList.get(i) instanceof TreasureRoom) {
        if(((TreasureRoom) roomList.get(i)).playerCanGrabTreasure(p) == true) {
          gameState = 1;
        }
        if(d.getCurrentRoom().equals(p.getCurrentRoom())) {
          gameState = 2;
        }
      }
    }
    if(gameState == 1) {
      System.out.println("You won! You found the treasure!");
     
    }
    else if(gameState == 2) {
      System.out.println(Dragon.getDragonEncounter());
      System.out.println("You lost :( You were scorched by the dragon");
      //System.out.println(p.getCurrentRoom()); debug line
      
    }
    
  }
  /** Loads in room info using the file stored in roomInfo
   *  @author Michelle 
   */
  private void loadRoomInfo() {
    System.out.println("Loading rooms...");
    Scanner fileReader = null;
    try {
      
      //scanner to read from file
      fileReader = new Scanner(roomInfo);
      
      //read line by line until none left
      while(fileReader.hasNext()) {
        String nextLine = fileReader.nextLine();
        
        //parse info and create new room 
        String[] parts = nextLine.split(" \\| ");
        int ID = Integer.parseInt(parts[1].trim()); //get the room id
        String imageName = null;
        String description = null;
        PImage image = null;
        Room newRoom = null;
        
        if(parts.length >= 3) {
          imageName = parts[2].trim();
          image = this.loadImage("images" + File.separator + imageName);
         
        }
        
        if(parts.length == 4) {
          description = parts[3].trim(); //get the room description
        }
   
        switch(parts[0].trim()) {
          case "S":
            newRoom = new StartRoom(ID, image);
            break;
          case "R":
            newRoom = new Room(ID, description, image);
            break;
          case "P":
            newRoom = new PortalRoom(ID, description, image);
            break;
          case "T":
            newRoom = new TreasureRoom(ID);
            break;
          default:
            break;
        }  
        
        if(newRoom != null) {
          roomList.add(newRoom);
        }
      }
    }catch(IOException e) { //handle checked exception
      e.printStackTrace();
    }finally {
      if(fileReader != null)
        fileReader.close(); //close scanner regardless of what happened for security reasons :)
    }
  }
  
  /** Loads in room connections using the file stored in mapInfo
   *  @author Michelle 
   */
  private void loadMap() {
    System.out.println("Loading map...");
    Scanner fileReader = null;
    try {
          //scanner to read from file
          fileReader= new Scanner(mapInfo);
          
        //read line by line until none left
          while(fileReader.hasNext()) {
            
            //parse info
            String nextLine = fileReader.nextLine();
            String parts[] = nextLine.split(" ");
            int id = Integer.parseInt(parts[0]);
            
            Room toEdit = getRoomByID(id); //get the room we need to update info for adjacent rooms
            
            //add all the rooms to the adj room list of toEdit
            for(int i=1; i<parts.length; i++) {
              Room toAdjAdd = getRoomByID(Integer.parseInt(parts[i]));
              toEdit.addToAdjacentRooms(toAdjAdd);
            }
          }
        }catch(IOException e) { //handle checked exception
          e.printStackTrace();
        }finally {    //close scanner regardless of what happened for security reasons :)
          if(fileReader != null)
            fileReader.close();
        }
  }
  
  /**
   * Get the room objected associated with the given ID.
   * @param id the ID of the room to retrieve
   * @return the Room that corresponds to that id
   * @author Michelle
   */
  private Room getRoomByID(int id){
    int indexToEdit = roomList.indexOf(new Room(id,"dummy", null));
    Room toEdit = roomList.get(indexToEdit); 
    return toEdit;
  }
  /**
   * Loads the characters in the game
   */
  private void loadCharacters() {
    System.out.println("Adding characters...");
    characters.add(new Character(getRoomByID(5),"KEYHOLDER"));
    characters.add(new Player(getRoomByID(1)));
    characters.add(new Dragon(getRoomByID(9)));
    }
  /**
   * Overriden keyPressed method that changes the player's current room
   */
  @Override
  public void keyPressed() {
    //if gameState is 1 or 2 then stops the player from moving rooms 
    if(gameState == 1 || gameState == 2) {
      return;
    }
    try {
      //index of the player
      int pIndex = 0;
      //find the player's index in the characters arraylist
      for(int i = 0; i < characters.size(); i++) {
        if(characters.get(i).getLabel().equals("PLAYER")) {
          pIndex = i;
        }
      }
      //turn the key pressed into an int 
      int keys = ((Integer.parseInt((key-48) + "")));
      System.out.println("You chose: " + keys);
      //change the player's room and end the player's turn if they successfully changed rooms
      if(((Player) characters.get(pIndex)).changeRoom(getRoomByID(keys)) == true) {
        isDragonTurn = true;
        //System.out.print("here"); //debug line
      }
      //otherwise tell the player to choose a valid room
      else {
        System.out.println("Select a valid adjacent room");
      }
    }
    catch(NumberFormatException e) {
      System.out.println("You did not enter a number");
    }
  }
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    PApplet.main("DragonTreasureGame");

  }

}