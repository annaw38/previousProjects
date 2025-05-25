//////////////// P08 MusicPlayer300//////////////////////////
// Title:    P08 MusicPlayerTester
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
import java.io.File;
/**
 * This class checks the correctness of the an implementation of p08 MusicPlayer300
 *
 */
public class MusicPlayerTester {
  /**
   * Tester for Song's constructor 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSongConstructor() {
    try {
      //test case 1: an invalid filepath
      try {
        Song s = new Song("Yellow Submarine", "The Beatles", "audio" + File.separator + "YS.mp3");
      }catch(IllegalArgumentException i) {
        //do nothing
      }catch(Exception e) {
        e.printStackTrace();
        return false;
      }
      //test case 2: a normal song
      Song s1 = new Song("1.mid", "hobbes", "audio"+ File.separator + "1.mid");
      //System.out.println(s1.toString()); //debug line
      String s = "\"1.mid\" (0:6) by hobbes"; //expected toString
      //System.out.println(s.substring(17)); //debug line
      //check the getters
      if(s1.getArtist().equals("hobbes") && s1.getTitle().equals("1.mid")) {
        //check that the song's artist and title matches what's expected and duration is formatted 
        //correctly
        if(s1.toString().contains(s.substring(0,8)) && s1.toString().contains(s.substring(8,9))&&
          s1.toString().contains(s.substring(10,11))&& s1.toString().contains(s.substring(12,13))
          && s1.toString().contains(s.substring(17))) {
          return true;
        }
      }
      return false;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * tester for Song's playback methods
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSongPlayback() {
    //test case: general case
    try {
      Song s1 = new Song("1.mid", "hobbes", "audio" + File.separator +"1.mid");
      s1.play();
      Thread.sleep(1000);
      // another song but not playing it
      Song s2 = new Song("3.mid", "hobbes", "audio" + File.separator + "3.mid");
      if(s1.isPlaying() == true) {
        s1.stop(); //test the stop method
        if(s1.isPlaying() == false && s2.isPlaying() == false) {
          return true;
        }
      }
      return false;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * tester for SongNode
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testSongNode() {
    try {
      //test case 1: general case
      //creating the new songs and songnodes
      Song s1 = new Song("1.mid", "hobbes", "audio"+ File.separator +"1.mid");
      Song s2 = new Song("2.mid", "hobbes", "audio"+ File.separator +"2.mid");
      Song s3 = new Song("3.mid", "hobbes", "audio"+ File.separator +"3.mid");
      SongNode sn1 = new SongNode(s1);
      SongNode sn2 = new SongNode(s2);
      SongNode sn3 = new SongNode(s3,sn1);
      
      //test case 2: creating 1 song node
      Song s4 = new Song("4.mid", "yoko kanno", "audio"+ File.separator +"4.mid");
      SongNode sn4 = new SongNode(s4);
      
      //testing the setter
      sn1.setNext(sn2);
      //testing the getters
      if(sn1.getSong().equals(s1) && sn1.getNext().equals(sn2) && sn3.getNext().equals(sn1) &&
          sn4.getNext() == null && sn4.getSong().equals(s4)) {
        return true;
      }
      return false;
    }catch(Exception e) {
      e.printStackTrace();
      return false; 
    }
  }
  /**
   * tester for Playlist's Enqueue, constructor, and accessor methods
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testEnqueue() {
    try {
      //general test case
      Song s1 = new Song("All I Want For Christmas Is You", "Mariah Carey", 
          "audio"+ File.separator +"all-i-want-for-xmas.mid");
      Song s2 = new Song("He's A Pirate", "Klaus Badelt", "audio"+ File.separator +"pirates.mid");
      Song s3 = new Song("Africa", "Toto", "audio"+ File.separator +"toto-africa.mid");
     
      Playlist p = new Playlist();
      p.enqueue(s1);
      p.enqueue(s2);
      p.enqueue(s3);
      
      //test case 2: one song
      Playlist p2 = new Playlist();
      p2.enqueue(s3);
      //try dequeue from one song in playlist
      if(p2.peek().equals(s3)) {
        p2.dequeue(); 
      }
      try {
        p2.peek();
      }catch(NullPointerException e) {
        //do nothing
      }catch(Exception e) {
        return false;
      }
      //check that everything matches what's expected
      if(p.peek().equals(s1) && p.isEmpty() == false && p.size() == 3) {
        return true;
      }
      return false;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * tester for Playlist's Dequeue, constructor, and accessor methods
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testDequeue() {
    try {
      //test general case
      Song s1 = new Song("All I Want For Christmas Is You", "Mariah Carey", 
          "audio"+ File.separator +"all-i-want-for-xmas.mid");
      Song s2 = new Song("He's A Pirate", "Klaus Badelt", "audio"+ File.separator +"pirates.mid");
      Song s3 = new Song("Africa", "Toto", "audio"+ File.separator +"toto-africa.mid");
     
      Playlist p = new Playlist();
      p.enqueue(s1);
      p.enqueue(s2);
      p.enqueue(s3);
      
      //System.out.println(p3.toString()); //debug line
      //System.out.println(p.toString()); //debug line
      //test case 2: dequeueing from a single node playlist
      Playlist p2 = new Playlist();
      p2.enqueue(s1);
      if(p2.dequeue().equals(s1)) {
        //check that the dequeue method works
        if(p2.peek() == null) {
          if(p.dequeue().equals(s1)) {
            //make sure that the general case works and has everything that's expected
            if(p.peek().equals(s2) && p.isEmpty() == false && p.size() == 2) {
              return true;
            }
          }
        }
      }
      return false;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * tester for running the rest of the testers
   * @return true when all the other tests return true, and false if any one of the testers return 
   * false
   */
  public static boolean runAllTests() {
    //try running all the testers
    try {
      if(testSongConstructor() == true && testSongPlayback() == true && testSongNode() == true && 
          testEnqueue() == true && testDequeue() == true) {
        return true;
      }
      return false;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * main method to test the testers
   * @param args if there are any
   */
  public static void main(String[] args) {
    //System.out.println("testSongConstructor(): " + testSongConstructor());
    //System.out.println("testSongPlayback(): " +testSongPlayback());
    //System.out.println("testSongNode(): " + testSongNode());
    //System.out.println("testEnqueue(): "+ testEnqueue());
    //System.out.println("testDequeue(): "+ testDequeue());
    System.out.println("runAllTests(): " + runAllTests());
  }
}
