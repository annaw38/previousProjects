//////////////// P06 City Route Planner//////////////////////////
// Title:    P06 Path
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
import java.util.NoSuchElementException;
/**
 * This class represents a valid path through a grid of city blocks surrounded by streets. A path 
 * consists of the list of intersection objects from one crossing point (intersection) to another.
 * That is, one which only moves either one step directly east, or one step directly north at each 
 * step, meaning that only northeast paths from one intersection point to another are allowed. 
 * A list of intersection elements creates the path.
 *
 */
public class Path {
  private ArrayList<Intersection> intersections; //List of Intersections followed in this Path
  /**
   * Constructor of the Path object. Initializes this Path to start as empty
   */
  public Path() {
    intersections = new ArrayList<Intersection>();
  }
  /**
   * Returns the number of Intersections in this Path
   * @return the number of Intersections in this Path
   */
  public int length() {
    return intersections.size();
  }
  /**
   * Returns the first Intersection in this Path, if it is not empty. 
   * Otherwise, throws a NoSuchElementException.
   * @return the first Intersection in this Path, if it is not empty
   * @throws NoSuchElementException if this Path is empty
   */
  public Intersection getHead() {
    if(intersections.isEmpty() == true) {
      throw new NoSuchElementException("The path is empty");
    }
    return intersections.get(0);
  }
  /**
   * Returns the last Intersection in this Path, if it is not empty. 
   * Otherwise, throws a NoSuchElementException.
   * @return the last Intersection in this Path, if it is not empty
   * @throws NoSuchElementException if this Path is empty
   */
  public Intersection getTail() {
    if(intersections.isEmpty() == true) {
      throw new NoSuchElementException("The path is empty");
    }
    return intersections.get(intersections.size()-1); //-1 to get the last index of intersections
  }
  /**
   * Adds the given Intersection to the end of this Path if it is a valid addition. A Intersection 
   * is a valid addition if the current Path is empty, or the Intersection to add is one step 
   * directly east, or one step directly north of the current tail Intersection in this Path. 
   * @param toAdd Intersection to add to the end of this Path
   * @throws IllegalArgumentException if the Intersection to add is not valid
   */
  public void addTail(Intersection toAdd) {
    
    if(intersections.isEmpty() == true || ((getTail().getX()+1 == toAdd.getX() && getTail().getY()
        == toAdd.getY())) || ((getTail().getY()+1  == toAdd.getY() && getTail().getX() == 
        toAdd.getX()))){
      intersections.add(toAdd);
    }
    else {
      throw new IllegalArgumentException("This intersection is not a valid addition");
    }
  }
  /**
   * Adds the given Intersection to the front of this Path if it is a valid addition. A Intersection
   * is a valid addition if the current Path is empty, or the Intersection to add is one step
   * directly west, or one step directly south of the current head Intersection in this Path. 
   * @param toAdd Intersection to add to the beginning of this Path
   * @throws IllegalArgumentException if the Intersection to add is not valid
   */
  public void addHead(Intersection toAdd) {
    if(intersections.isEmpty() == true || ((getHead().getX()-1 == toAdd.getX() && getHead().getY() 
        == toAdd.getY()))  || ((getHead().getY()-1 == toAdd.getY() && getHead().getX() == 
        toAdd.getX()))){
      intersections.add(0, toAdd);
    }
    else {
      throw new IllegalArgumentException("This intersection is not a valid addition");
    }
  }
  /**
   * Returns a String representing the coordinates taken in this Path. An empty Path should return 
   * the String "Empty", while a non-empty Path should return the coordinates of the Intersections 
   * it visits separated by a "->". 
   * @return a String representing the coordinates followed by this Path
   */
  @Override
  public String toString() {
    if(intersections.isEmpty() == true) {
      return "Empty";
    }
    String result = ""; //Starting string 
    for(int i =0; i<intersections.size()-1;i++) {
      result +=(intersections.get(i).toString() + "->"); //add the coordinate and -> to the string
    }
    result += intersections.get(intersections.size()-1).toString(); //add end Intersection to string 
    return result;
  }
}