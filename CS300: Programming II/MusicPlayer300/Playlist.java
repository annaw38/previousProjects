//////////////// P08 MusicPlayer300//////////////////////////
// Title:    P08 Playlist
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
// Persons:     None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////
/**
 * A FIFO linked queue of SongNodes, conforming to our QueueADT interface.
 */
public class Playlist implements QueueADT<Song>{
  //data fields
  private SongNode first; //The current first song in the queue; the next one up to play (front of 
  //the queue)
  private SongNode last; //The current last song in the queue; the most-recently added one (back of
  //the queue)
  private int numSongs; //The number of songs currently in the queue
  
  //constructor
  /**
   * Constructs a new, empty playlist queue
   */
  public Playlist(){
    this.first = null;
    this.last = null;
    this.numSongs = 0;
  }
  /**
   * Adds a new song to the end of the queue
   * @param element the song to add to the Playlist
   */
  @Override
  public void enqueue(Song element) {
    SongNode newNode = new SongNode(element);
    //special case: if the queue is empty
    if(isEmpty()) {
      first = newNode;
      last = newNode;
      numSongs++;
    }
    //general case
    else {
      last.setNext(newNode);
      last = newNode;
      numSongs++;
    }
  }
  /**
   * Removes the song from the beginning of the queue
   * @return the song that was removed from the queue, or null if the queue is empty
   */
  @Override
  public Song dequeue() {
    Song oldFirst = first.getSong();
    //special case: if the queue is empty
    if(isEmpty()) {
      return null;
    }
    //special case: if there is only 1 song
    if(first.getSong().equals(last.getSong())) {
      first = null;
      last = null;
      numSongs--;
      return oldFirst;
    }
    //general case
    else {
      this.first = first.getNext();
      //last remains the same
      numSongs--;
      return oldFirst;
    }
  }
  /**
   * Returns the song at the front of the queue without removing it
   * @return the song that is at the front of the queue, or null if the queue is empty
   */
  @Override
  public Song peek() {
    if(isEmpty()) {
      return null;
    }
    return first.getSong();
  }
  /**
   * Returns true if and only if there are no songs in this queue
   * @return true if this queue is empty, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return(numSongs == 0 && first == null && last == null);
  }
  /**
   * Returns the number of songs in this queue
   * @return the number of songs in this queue
   */
  public int size() {
    return numSongs;
  }
  /**
   * Creates and returns a formatted string representation of this playlist, with the string version 
   * of each song in the list on a separate line. For example:
   * "He's A Pirate" (1:21) by Klaus Badelt
   * "Africa" (4:16) by Toto
   * "Waterloo" (2:45) by ABBA
   * "All I Want For Christmas Is You" (4:10) by Mariah Carey
   * "Sandstorm" (5:41) by Darude
   * "Never Gonna Give You Up" (3:40) by Rick Astley
   * @return the string representation of this playlist
   */
  @Override
  public String toString() {
    if(isEmpty()) {
      return "";
    }
    String s = "";
    SongNode temp = first;
    
    for(int i =0;i<numSongs - 1;i++) {
      s += temp.getSong().toString() + "\n";
      temp = temp.getNext();
    }
    s += temp.getSong().toString();
    return s;
  }
}
