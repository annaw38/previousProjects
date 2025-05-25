//////////////// P08 MusicPlayer300//////////////////////////
// Title:    P08 MusicPlayer300
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
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * A linked-queue based music player which plays Actual Music Files based on keyboard input in an 
 * interactive console method. This music player can load playlists of music or add individual song 
 * files to the queue.
 *
 */
public class MusicPlayer300 {
  //data fields
  private Playlist playlist; //The current playlist of Songs
  private boolean filterPlay; //Whether the current playback mode should be filtered by artist; 
  //false by default
  private String filterArtist; //The artist to play if filterPlay is true; should be null otherwise
  
  //constructor
  /**
   * Creates a new MusicPlayer300 with an empty playlist
   */
  public MusicPlayer300() {
    this.playlist = new Playlist();
    this.filterPlay = false;
    this.filterArtist = null;
  }
  /**
   * Stops any song that is playing and clears out the playlist
   */
  public void clear() {
    if(playlist.isEmpty() == true) {
      //do nothing because it's already cleared
    }
    //stop the current song if playing
    if(playlist.isEmpty() == false &&  playlist.peek().isPlaying() == true) {
      playlist.peek().stop();
    }
    //dequeue all the songs
    for(int i = 0;i<playlist.size();i++) {
      playlist.dequeue();
    }
  }
  /**
   * Loads a playlist from a provided file, skipping any individual songs which cannot be loaded. 
   * Note that filenames in the provided files do NOT include the audio directory, and will need 
   * that added before they are loaded. Print "Loading" and the song's title in quotes before you 
   * begin loading a song, and an "X" if the load was unsuccessful for any reason.
   * @param file the File object to load
   * @throws FileNotFoundException  if the playlist file cannot be loaded
   */
  public void loadPlaylist(File file) throws FileNotFoundException{
    Scanner fileReader = null;
    try {
      //scanner to read from file
      fileReader = new Scanner(file);
      
      //read line by line until none left
      while(fileReader.hasNext()) {
        String nextLine = fileReader.nextLine();
        System.out.println("Loading " + nextLine.substring(0, nextLine.indexOf(",")));
        
        //parse info 
        String parts[] = nextLine.split(",");
        //test lines
        //System.out.println(parts[0]);
        //System.out.println(parts[1]);
        //System.out.println(parts[2]);
        Song newSong = new Song(parts[0], parts[1], "audio" + File.separator + parts[2]);
        playlist.enqueue(newSong);
      
      }
      
    }
    //if the load was unsuccessful
    catch(FileNotFoundException e) {
      //System.out.println("X");
      throw new FileNotFoundException("This file could not be loaded");
    }
    //if the load was unsuccessful
    catch(IllegalArgumentException e) {
     System.out.println("X");
    }finally {
      if(fileReader != null)
        fileReader.close(); //close scanner regardless of what happened for security reasons :)
    }
  }
  /**
   * Loads a single song to the end of the playlist given the title, artist, and filepath. 
   * Filepaths for P08 must refer to files in the audio directory.
   * @param title the title of the song
   * @param artist the artist of this song
   * @param filepath the full relative path to the song file, begins with the "audio" directory for
   * P08
   * @throws IllegalArgumentException if the song file cannot be read
   * 
   */
  public void loadOneSong(String title, String artist, String filepath) 
      throws IllegalArgumentException {
    try {
      //try making a new song and adding it to the queue
      Song newSong= new Song(title, artist, filepath);
      playlist.enqueue(newSong);
      //System.out.println("complete"); //debug line
    }
    //if the file can't be read throw a new IllegalArgumentException
    catch(IllegalArgumentException e) {
      throw new IllegalArgumentException("This song filepath can't be read");
    }
  }
  /**
   * Provides a string representation of all songs in the current playlist
   * @return a string representation of all songs in the current playlist
   */
  public String printPlaylist() {
    //return playlist's toString
    String s = playlist.toString();
    return s;
  }
  /**
   * Creates and returns the menu of options for the interactive console program.
   * @return the formatted menu String
   */
  public String getMenu() {
    //menu of the list of options 
    String menu = "Enter one of the following options: " + "\n" + "[A <filename>] to enqueue" 
        + " a new song file to the end of this playlist"+"\n" + "[F <filename>] to load a new"
        + " playlist from the given file"+"\n"+"[L] to list all songs in the current playlist"+"\n"
        + "[P] to start playing ALL songs in the playlist from the beginning"+"\n"+"[P -t <Title>] "
        + "to play all songs in the playlist starting from <Title>"+"\n"+"[P -a <Artist>] to start "
        + "playing only the songs in the playlist by Artist"+"\n"+"[N] to play the next song"+"\n"
        + "[Q] to stop playing music and quit the program";
    return menu;
  }
  /**
   * Stops playback of the current song (if one is playing) and advances to the next song in the 
   * playlist.
   * @throws IllegalStateException if the playlist is null or empty, or becomes empty at any time 
   * during this method
   */
  public void playNextSong() throws IllegalStateException {
    //if the playlist is null/empty
    if(this.playlist.isEmpty() == true || this.playlist == null) {
      throw new IllegalStateException("No more songs");
    }
    //if the current song is playing, stops playing it
    if(playlist.peek().isPlaying() == true) {
      playlist.peek().stop();
    }
    
    //if filterPlay is true 
    if(filterPlay == true) {
      //dequeue the current song then check if the artist equals the given artist
      playlist.dequeue();
      //if it does the play the song
      if(playlist.peek().getArtist().equals(filterArtist)) {
        playlist.peek().play();
      }
      //if it doesn't then dequeue the song
      if(!playlist.peek().getArtist().equals(filterArtist)) {
        playlist.dequeue();
      }
    }
    //general case
    else {
      //dequeue the current song and then play the next song
      playlist.dequeue();
      //check for more songs
      if(this.playlist.isEmpty() == false && playlist.peek()!= null) {
        playlist.peek().play();
      }
    }
  }
  /**
   * Interactive method to display the MusicPlayer300 menu and get keyboard input from the user.
   * @param in Scanner to read the user's inputs
   */
  public void runMusicPlayer300(Scanner in) {
    //new scanner to read the input from the user
    Scanner s = null;
    try {
      //scanner to read user input
      s = in;
      changePlaylist(s);
    }catch(NullPointerException e) {
      System.out.println("No more songs :(");
      changePlaylist(s);
    }catch(Exception e) {
      changePlaylist(s);
    }
    finally {
      if(s != null)
        s.close(); //close scanner regardless of what happened for security reasons :)
    }
  }
  /**
   * Private helper method for the various inputs in runMusicPlayer300
   * @param in scanner to read the user's input
   */
  private void changePlaylist(Scanner in) {
    Scanner s = null;
    //scanner to read user input
    s = in;
    //new String for the user's input
    String input;
    //string of expected answers 
    String expected = "AFLP -tP -aNQ";
    do {
      //print menu and > for user input
      System.out.println(getMenu());
      System.out.print(">");
      
      //saving the user's input
      input = s.nextLine();
      //System.out.println(input.substring(5));
      
      /*A – add a song to the end of the playlist. You will need to further prompt the user for 
       * the title and artist of this song, and then add the new song to the playlist.*/
      //check if the first letter of the input equals A as there can be a file/filepath
      if(input.substring(0,1).equals(expected.substring(0,1))){
        String filepath = input.substring(2);
        System.out.print("Title: ");
        String title = s.nextLine();
        System.out.print("Artist: ");
        String artist = s.nextLine();
        try {
          loadOneSong(title, artist, filepath);
        }catch(IllegalArgumentException e) {
          System.out.println("This song filepath can't be read");
        }
      }
      /*F – load in a new playlist from the given file.*/
      //check if the first letter of the input equals F as there can be a file/filepath
      else if(input.substring(0,1).equals(expected.substring(1,2))) {
        String f = input.substring(2);
        File file = new File(f);
        try {
          loadPlaylist(file);
        } catch (FileNotFoundException e) {
          System.out.println("This file could not be loaded");
          //e.printStackTrace(); 
        }
      }
      /* L – display all the songs remaining in the current playlist*/
      //check if the first letter of the input equals L as there can be a file/filepath
      else if(input.substring(0,1).equals(expected.substring(2,3))) {
        System.out.println(printPlaylist());
      }
      /*P – begin playing the songs in the playlist*/
      //check if the first and only letter of the input equals P as there could be a -t or -a
      else if(input.substring(0,1).equals(expected.substring(3,4)) && input.length() == 1) {
        if(playlist.peek().isPlaying() == false) {
          playlist.peek().play();
          //playlist.dequeue(); 
        }
      }
      /*P – begin playing the songs in the playlist, but first! Check to see if there was a 
       * modifier. -t – begin playback at the song with the given title.*/
      //first check if the length is greater than 1 to check that it's not just P
      else if(input.length()>1) {
        //check if the input has P -a or P -t
        //P -t is expected here
        if(input.substring(0,4).equals(expected.substring(3,7))){
          if(this.playlist.isEmpty() == true || this.playlist == null) {
            System.out.println("No more songs :(");
            //throw new IllegalStateException("No more songs");
          }
          //if the current song is playing, stops playing it
          if(playlist.peek().isPlaying() == true) {
            playlist.peek().stop();
          }
          //while the current title doesn't equal the given title, dequeue it
          //System.out.println(playlist.peek());
          while(!playlist.peek().getTitle().equals(input.substring(5))) {
            playlist.dequeue();
          }
          //since its broken out of the while loop we either have the song that matches the given 
          //title, play it
          if(playlist.peek().getTitle().equals(input.substring(5))) {
            playlist.peek().play();
            try {
              Thread.sleep(2000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          //otherwise, we reached the end and there's no more songs
          else {
            System.out.println("No more songs :(");
            //throw new IllegalStateException("No more songs");
          }
        }
        /*P – begin playing the songs in the playlist, but first! Check to see if there was a 
         * modifier. -a – play only the songs by the given artist (hint: use the filterPlay and
         * filterArtist data fields for assistance here)*/
        //check if the input has P -a or P -t
        //P -a is expected here 
        else if(input.substring(0,4).equals(expected.substring(7,11))) {
          String artist = input.substring(5); //the given artist 
          filterPlay = true;
          filterArtist = artist;
          //if the playlist is empty
          if(this.playlist.isEmpty() == true || this.playlist == null) {
            System.out.println("No more songs :(");
          }
          //while the current song isn't the given artist dequeue it
          while(!playlist.peek().getArtist().equals(filterArtist)) {
            playlist.dequeue();
          }
          //once broken out of the while loop, if the current song is by the given artist then 
          //play it
          if(playlist.peek().getArtist().equals(filterArtist)) {
            playlist.peek().play();
            try {
              Thread.sleep(2000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          //otherwise it's the end and there's no more songs
          else {
            System.out.println("No more songs :(");
            //throw new IllegalStateException("No more songs");
          }
        }
      }
      /* N-stop the current song and move to the next song in the playlist. Songs should only be
       * dequeued from the playlist when you’re ready to move to the next song; otherwise, you
       * won’t be able to stop their audio!*/
      //check if the first letter of the input equals N as there can be a file/filepath 
      else if(input.substring(0,1).equals(expected.substring(11,12))) {
        try {
          if(playlist.peek().isPlaying()) {
            playlist.peek().stop(); //stop the current song
          }
          //if the playlist's current song isn't playing then it would just play the next song
          playNextSong();
        }catch(IllegalStateException e) {
          System.out.println("No more songs :(");
        }
      }
      /*Q– clear out the queue and end the method. Print a "Goodbye!" message.
       */
      else if(input.substring(0,1).equals(expected.substring(12,13))){
        clear();
        System.out.println("Goodbye!");
        break;
      }
      /*Anything else – print out "I don't know how to do that." and go back to the beginning*/
      else  {
        System.out.println("I don't know how to do that.");
      }
    }while(true);
  }
  /**
   * main method to test runMusicPlayer300
   * @param args if there are any
   */
  public static void main(String[] args) {
    new MusicPlayer300().runMusicPlayer300(new Scanner(System.in));
  }
}