//////////////// P06 City Route Planner//////////////////////////
// Title:    P06 Intersection
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
/**
 * This class represents a single intersection point where two streets laid out on a grid plan cross
 * at specified x and y coordinate positions.
 *
 */
public class Intersection {
  private final int x; //X-axis coordinate of this intersection
  private final int y; //Y-axis coordinate of this intersection
  /**
   * Constructor for an Intersection object. Initializes the intersection with the given coordinates
   * @param x Horizontal position of this Intersection
   * @param y Vertical position of this Intersection
   */
  public Intersection(int x, int y) {
    this.x = x;
    this.y = y;
  }
  /**
   * Returns the horizontal position of this Intersection
   * @return the horizontal position of this Intersection
   */
  public int getX() {
    return x;
  }
  /**
   * Returns the vertical position of this Intersection
   * @return the vertical position of this Intersection
   */
  public int getY() {
    return y;
  }
  /**
   * Returns a coordinate-pair representation of this Intersection in the form "(x,y)"
   * @return a coordinate-pair representation of this Intersection
   */
  @Override
  public String toString() {
    return ("(" + x + "," + y + ")" );
  }
  /**
   * Returns true if the given Object is identical to this Intersection
   * @param o object to compare for equality
   * @return true if the given Object is an Intersection object which has the same x and y 
   * coordinates as this Intersection
   */
  @Override
  public boolean equals(Object o) {
    if(o instanceof Intersection) {
     Intersection otherIntersection = (Intersection)o;
      if(this.x == otherIntersection.x && this.y == otherIntersection.y) {
        return true;
      }
    }
    return false;
  }
  /**
   * Creates a new Intersection instance which is one step directly above this Intersection. 
   * Should not modify the original Intersection object.
   * @return a new Intersection instance which is one step directly above this Intersection
   */
  public Intersection goNorth() {
    Intersection north = new Intersection(x, y+1);
    return north;
  }
  /**
   * Creates a new Intersection instance which is one step directly below this Intersection. 
   * Should not modify the original Intersection object.
   * @return a new Intersection instance which is one step directly below this Intersection
   */
  public Intersection goSouth() {
    Intersection south = new Intersection(x, y-1);
    return south;
  }
  /**
   * Creates a new Intersection instance which is one step directly to the right of this 
   * Intersection object. Should not modify the original Intersection object.
   * @return a new Intersection instance which is one step directly to the right of this 
   * Intersection
   */
  public Intersection goEast() {
    Intersection east = new Intersection(x+1, y);
    return east;
  }
  /**
   * Creates a new Intersection instance which is one step directly to the left of this 
   * Intersection. Should not modify the original Intersection object.
   * @return a new Intersection instance which is one step directly to the left of this 
   * Intersection
   */
  public Intersection goWest() {
    Intersection west = new Intersection(x-1, y);
    return west;
  }
  
}
