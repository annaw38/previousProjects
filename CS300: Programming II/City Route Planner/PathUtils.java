//////////////// P06 City Route Planner//////////////////////////
// Title:    P06 PathUtils
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
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/1829 - this post helped me get
//                  started with the recursive base case logic 
//                  https://www.codingninjas.com/codestudio/library/count-all-number-of-paths-of-
//                  a-given-matrix this website helped me with the countPaths logic
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;
/**
 * Utility methods for planning a trip through a grid of city intersections.
 *
 */
public class PathUtils {
  /**
   * Constructor of PathUtils
   */
  public PathUtils() {
    
  }
  /**
   * Finds the number of valid Paths between the given start and end Intersections. If it is not 
   * possible to get from the start to the end intersection by moving up or right, then 0 should be 
   * returned. 
   * @param start Intersection to start at
   * @param end Intersection to end at
   * @return the number of valid Paths which start and end at the given Intersections
   */
  public static int countPaths(Intersection start, Intersection end) {
    //x represents when start goes East and y represents when start goes North 
    int x = end.getX()-start.getX()+1;
    int y = end.getY()-start.getY()+1;
    int val = 0; //return value
    //base cases:
    //if the start equals the end then return 1
    if(start.equals(end)) {
      return 1;
    }
    //if end's coordinates are South or West to start's coordinates return 0
    if(start.getX()>end.getX() || start.getY()>end.getY()){
      return 0;
    }
    //goEast if the end's X coordinate is larger than start's and then call helper
    if(end.getX()>start.getX()) {
      val = countPathsHelper(x,y);
    }
    //goNorth if the end's Y coordinate is larger than start's and then call helper
    if(end.getY()>start.getY()) {
      val = countPathsHelper(x,y);
    }
    return val;
  }
  /**
   * Private helper method for countPaths()
   * @param x (end's x coordinate - start's x coordinate)+1 when start goes East
   * @param y (end's y coordinate - start's y coordinate)+1 when start goes North
   * @return the number of paths
   */
  private static int countPathsHelper(int x, int y) {
    //base case: if both x and y are 1 because they are at the same coordinate/Intersection
    if(x == 1 || y == 1) {            
       return 1;
    }
    //recursive method to count the paths 
    //add together the number of paths for when start goes North (x-1,y) and the number of paths for  
    //when start goes East(x,y-1)
    return countPathsHelper(x - 1, y) + countPathsHelper(x, y - 1);
  }
  
  /**
   * Finds all valid Paths between the given start and end Intersections. If it is not possible to 
   * get from the start to the end intersection by moving up or right, then an empty ArrayList 
   * should be returned.
   * @param start Intersection to start at
   * @param end Intersection to end at
   * @return an ArrayList containing all valid Paths which start and end at the given Intersections
   */
  public static ArrayList<Path> findAllPaths(Intersection start, Intersection end) {  
    ArrayList<Path> allPaths = new ArrayList<Path>();
    //base case 1: if start's x and y is greater than end's x and y - return an empty arrayList
    if(start.getX()>end.getX() || start.getY()>end.getY()) {
      return allPaths;
    } 
    //base case 2: if start equals end - just add the start 
    if(start.equals(end)) {
      Path p = new Path();
      p.addHead(start);
      allPaths.add(p);
    }
    //check conditions if start.getX<end.getX true - goEast
    if(start.getX()<end.getX() == true) {
      //for each path if start goes East then add the start to the beginning of the path and then 
      //add the path to the arraylist of all the paths
      for(Path p : findAllPaths(start.goEast(), end)) {
        p.addHead(start);
        allPaths.add(p);
      }
    }
    //check conditions if start.getY<end.getY true - goNorth
    if(start.getY()<end.getY()) {
    //for each path if start goes North then add the start to the beginning of the path and then 
      //add the path to the arraylist of all the paths
      for(Path p : findAllPaths(start.goNorth(), end)) {
        p.addHead(start);
        allPaths.add(p);
      }
    }
    return allPaths;         
  }
}