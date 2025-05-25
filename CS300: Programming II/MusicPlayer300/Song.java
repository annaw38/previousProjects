//////////////// P08 MusicPlayer300//////////////////////////
// Title:    P08 Song
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
import java.io.IOException;
/**
 * A representation of a single Song. Interfaces with the provided AudioUtility class, which uses 
 * the javax.sound.sampled package to play audio to your computer's audio output device
 *
 */
public class Song {
  //data fields
  private AudioUtility audioClip; //This song's AudioUtility interface to javax.sound.sampled
  private String title; //The title of this song
  private String artist; //The artist of this song
  private int duration; //The duration of this song in number of seconds
  
  //constructor
  /**
   * Initializes all instance data fields according to the provided values
   * @param title the title of the song, set to empty string if null
   * @param artist the artist of this song, set to empty string if null
   * @param filepath the full relative path to the song file, begins with the "audio" directory for
   * P08
   * @throws IllegalArgumentException if the song file cannot be read
   */
  public Song(String title, String artist, String filepath) throws IllegalArgumentException {
    try {
      this.audioClip = new AudioUtility(filepath);
    } catch (IOException e) {
      throw new IllegalArgumentException("The song file can't be read");
    }
    if(title.isBlank()) {
      this.title = "";
    }
    if(artist.isBlank()) {
      this.artist = "";
    }
    this.title = title;
    this.artist = artist;
    this.duration = audioClip.getClipLength();
  }
  /**
   * Tests whether this song is currently playing using the AudioUtility
   * @return true if the song is playing, false otherwise
   */
  public boolean isPlaying() {
    if(audioClip.isRunning()) {
      return true;
    }
    return false;
  }
  /**
   * Accessor method for the song's title
   * @return the title of this song
   */
  public String getTitle() {
    return this.title;
  }
  /**
   * Accessor method for the song's artist
   * @return the artist of this song
   */
  public String getArtist() {
    return this.artist;
  }
  /**
   * Uses the AudioUtility to start playback of this song, reopening the clip for playback if 
   * necessary
   */
  public void play() {
    if(audioClip.isReadyToPlay() == false) {
      audioClip.reopenClip();
    }
    audioClip.startClip();
    System.out.println("Playing... " + toString());
  }
  /**
   * Uses the AudioUtility to stop playback of this song
   */
  public void stop() {
    audioClip.stopClip();
  }
  /**
   * Creates and returns a string representation of this Song, for example:
   * "Africa" (4:16) by Toto
   * The title should be in quotes, the duration should be split out into minutes and seconds 
   * (recall it is stored as seconds only!), and the artist should be preceded by the word "by".
   * It is intended for this assignment to leave single-digit seconds represented as 0:6, for 
   * example, but if you would like to represent them as 0:06, this is also allowed.
   * @return a formatted string representation of this Song
   */
  @Override
  public String toString() {
    String s = "";
    if(duration > 60) {
      s = "\""+ this.title + "\"" + " (" + duration/60 + ":" + (duration-((duration/60)*60)) + ")"
          + " by " + this.artist ;
    }
    else {
      s = "\""+ this.title + "\"" + " (" + "0" + ":" + duration + ")" + " by " + this.artist;
    }
    return s;
  }
}