//////////////// P05 Dragon Treasure Adventure 2.0//////////////////////////
// Title:    P05 Start Room
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
import processing.core.PImage;
/**
 * Class to represent StartRoom
 *
 */
public class StartRoom extends Room{
  public StartRoom(int ID, PImage image){
    super(ID, "You find yourself in the entrance to a cave holding treasure.", image);
  }
}