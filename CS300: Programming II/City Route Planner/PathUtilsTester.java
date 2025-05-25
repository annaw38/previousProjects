//////////////// P06 City Route Planner//////////////////////////
// Title:    P06 PathUtilsTester
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
// Online Sources: https://piazza.com/class/l7f41s35yau64i/post/2039_f1 - the followup of this post
//                 tremendously helped me with the findAllPaths testers
//
//
///////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Tester class for PathUtils
 *
 */
public class PathUtilsTester {
  /**
   * Constructor of PathUtilsTester
   */
  public PathUtilsTester() {
    
  }
  /**
   * Tests the case of countPaths() when there are no valid Paths. 
   * @return true if all test cases are passed
   */
  public static boolean testCountPathsNoPath() {
    try {
      //expected path count: 0
      Intersection start = new Intersection(1,1);
      Intersection end = new Intersection(0,1);
      //expected path count: 0
      Intersection start1 = new Intersection(0,5);
      Intersection end1 = new Intersection(-1,5);
      //expected path count: 0
      Intersection start2 = new Intersection(9,10);
      Intersection end2 = new Intersection(8,10);
      //call countPaths and 0 is expected for all
      if(PathUtils.countPaths(start, end) == 0 && PathUtils.countPaths(start1, end1) == 0 &&
          PathUtils.countPaths(start2, end2) == 0) {
        return true;
      }
      return false; 
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * Tests the case of countPaths() when there is a single valid Path.
   * @return true if all test cases are passed
   */
  public static boolean testCountPathsOnePath() {
    try {
      //expected path count: 1
      Intersection start = new Intersection(1,1);
      Intersection end = new Intersection(1,2);
      //expected path count: 1
      Intersection start1 = new Intersection(0,5);
      Intersection end1 = new Intersection(1,5);
      //expected path count: 1
      Intersection start2 = new Intersection(8,10);
      Intersection end2 = new Intersection(8,11);
      //call countPaths and 1 is expected for all
      if(PathUtils.countPaths(start, end) == 1 && PathUtils.countPaths(start1, end1) == 1 &&
          PathUtils.countPaths(start2, end2) == 1) {
        return true;
      }
      return false;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
    
  /**
   * Tests the case of countPaths() when there are multiple possible paths.
   * @return true if all test cases are passed
   */
  public static boolean testCountPathsRecursive() {
    try {
      //expected path count: 4
      Intersection start = new Intersection(-3,5);
      Intersection end = new Intersection(0,6);
      //expected path count: 6
      Intersection start1 = new Intersection(-4,0);
      Intersection end1 = new Intersection(-2,2);
      //expected path count: 4
      Intersection start2 = new Intersection(2,-2);
      Intersection end2 = new Intersection(5,-1);
      //expected path count: 3
      Intersection start3 = new Intersection(0,0);
      Intersection end3 = new Intersection(1,2);
      //call countPaths and 4 is expected for the 1st and 3rd and 6 is expected for the 2nd
      //3 is expected for the 4th
      if(PathUtils.countPaths(start, end) == 4 && PathUtils.countPaths(start1, end1) == 6 && 
          PathUtils.countPaths(start2, end2) == 4 && PathUtils.countPaths(start3, end3) == 3) {
        return true;
      }
      return false; //default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
    
  /**
   * Tests the case of findAllPaths() when there are no valid Paths.
   * @return true if all test cases are passed
   */
  public static boolean testFindAllPathsNoPath() {
    try {
      ArrayList<Path> paths = new ArrayList<Path>();
      //expected path: empty ArrayList
      Intersection start = new Intersection(-2,-2);
      Intersection end = new Intersection(-4,-4);
      //expected path: empty ArrayList
      Intersection start1 = new Intersection(3,3);
      Intersection end1 = new Intersection(0,2);
      //expected path: empty Arraylist
      Intersection start2 = new Intersection(15,18);
      Intersection end2 = new Intersection(10,11);
      //call findAllPaths and an empty arraylist is expected for all
      if((PathUtils.findAllPaths(start, end).equals(paths)) && 
          ((PathUtils.findAllPaths(start1, end1).equals(paths)) && 
              ((PathUtils.findAllPaths(start2, end2).equals(paths))))) {
        return true;
      }
      return false; //default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * Tests the case of findAllPaths() when there is a single valid Path. 
   * @return true if all test cases are passed
   */
  public static boolean testFindAllPathsOnePath() {
    try {
      //arraylist of expected paths
      ArrayList<Path> expectedPaths = new ArrayList<Path>();
      //the same coordinate
      Path p1 = new Path();
      //expected path: (104,104)
      Intersection start = new Intersection(104,104);
      Intersection end = new Intersection(104,104);
      p1.addHead(start);
       
      //east 1 coordinate 
      Path p2 = new Path();
      //expected path: (0,2)->(0,3)
      Intersection start1 = new Intersection(0,2);
      Intersection end1 = new Intersection(0,3);
      p2.addHead(start1);
      p2.addTail(end1);
      
      //north 1 coordinate 
      Path p3 = new Path();
      //expected path: (0,0)->(0,1)
      Intersection start2 = new Intersection(0,0);
      Intersection end2 = new Intersection(0,1);
      p3.addHead(start2);
      p3.addTail(end2);
        
      // adding p1,p2, and p3 to expectedPaths
      expectedPaths.add(p1);
      expectedPaths.add(p2);
      expectedPaths.add(p3);
      
      //resultpaths for each path
      ArrayList<Path> resultPaths = PathUtils.findAllPaths(start,end);
      ArrayList<Path> resultPaths2 = PathUtils.findAllPaths(start1,end1);
      ArrayList<Path> resultPaths3 = PathUtils.findAllPaths(start2,end2);
        
        
      //System.out.println(resultPaths.get(0)); debug line
      //System.out.println(expectedPaths); debug line
      //check if the size of all the result paths' ArrayLists are 1
      if(resultPaths.size() == 1 && resultPaths2.size() == 1 && resultPaths3.size() == 1) {
        //calling the helper method
        if(tfapHelper(expectedPaths,resultPaths) == true && 
            tfapHelper(expectedPaths,resultPaths2) == true && 
            tfapHelper(expectedPaths,resultPaths3) == true) {
          return true;
        }
      }
      return false; //default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * Tests the case of findAllPaths() when there are multiple possible paths. For example, when the 
   * start position is Intersection(0, 0) and the ending position is Intersection(1, 2), there 
   * should be three possible Paths. For each of your cases, ensure that there is both the correct
   * number of Paths, and that the returned Paths exactly match what you expect to see. 
   * @return true if all test cases are passed
   */
  public static boolean testFindAllPathsRecursive() {
    try {
      //arraylist of all the expected paths
      ArrayList<Path> expectedPaths = new ArrayList<Path>();
      
      //1st test case start-(1,0) end-(3,2)
      //6 expected paths:
      //1.(1,0)->(2,0)->(2,1)->(2,2)->(3,2)
      //2.(1,0)->(2,0)->(3,0)->(3,1)->(3,2)
      //3.(1,0)->(2,0)->(2,1)->(3,1)->(3,2)
      //4.(1,0)->(1,1)->(1,2)->(2,2)->(3,2)
      //5.(1,0)->(1,1)->(2,1)->(2,2)->(3,2)
      //6.(1,0)->(1,1)->(2,1)->(3,1)->(3,2)
      //create the intersections
      Intersection start = new Intersection(1,0);
      Intersection end = new Intersection(3,2);
      Intersection m1 = new Intersection(2,0); //all possible middle values
      Intersection m2 = new Intersection(2,1);
      Intersection m3 = new Intersection(3,0);
      Intersection m4 = new Intersection(3,1);
      Intersection m5 = new Intersection(2,2);
      Intersection m6 = new Intersection(1,1);
      Intersection m7 = new Intersection(1,2);
      
      //create the expected paths and add the intersections to their path
      Path p1 = new Path();
      //expected path: 1.(1,0)->(2,0)->(2,1)->(2,2)->(3,2)
      p1.addHead(start);
      p1.addTail(m1);
      p1.addTail(m2);
      p1.addTail(m5);
      p1.addTail(end);
      
      
      Path p2 = new Path();
      //expected path: 2.(1,0)->(2,0)->(3,0)->(3,1)->(3,2)
      p2.addHead(start);
      p2.addTail(m1);
      p2.addTail(m3);
      p2.addTail(m4);
      p2.addTail(end);
        
       
      Path p3 = new Path();
      //expected path: 3.(1,0)->(2,0)->(2,1)->(3,1)->(3,2)
      p3.addHead(start);
      p3.addTail(m1);
      p3.addTail(m2);
      p3.addTail(m4);
      p3.addTail(end);
      
      Path p4 = new Path();
      //expected path: 4.(1,0)->(1,1)->(1,2)->(2,2)->(3,2)
      p4.addHead(start);
      p4.addTail(m6);
      p4.addTail(m7);
      p4.addTail(m5);
      p4.addTail(end);
      
      
      Path p5 = new Path();
      //expected path: 5.(1,0)->(1,1)->(2,1)->(2,2)->(3,2)
      p5.addHead(start);
      p5.addTail(m6);
      p5.addTail(m2);
      p5.addTail(m5);
      p5.addTail(end);
        
       
      Path p6 = new Path();
      //expected path: 6.(1,0)->(1,1)->(2,1)->(3,1)->(3,2)
      p6.addHead(start);
      p6.addTail(m6);
      p6.addTail(m2);
      p6.addTail(m4);
      p6.addTail(end);
      
      //add the paths to the expectedPaths arrayList
      expectedPaths.add(p1);
      expectedPaths.add(p2);
      expectedPaths.add(p3);
      expectedPaths.add(p4);
      expectedPaths.add(p5);
      expectedPaths.add(p6);
      
      //arrayList of results from FindAllPaths for the first test case
      ArrayList<Path> resultPaths1 = PathUtils.findAllPaths(start,end);
      //System.out.println(resultPaths); debug line
      
      //2nd test case start-(-1,3) end-(2,4)
      //4 expected paths:
      //1.(-1,3)->(-1,4)->(0,4)->(1,4)->(2,4)
      //2.(-1,3)->(0,3)->(1,3)->(2,3)->(2,4)
      //3.(-1,3)->(0,3)->(0,4)->(1,4)->(2,4)
      //4.(-1,3)->(0,3)->(1,3)->(1,4)->(2,4)
      
      //create the intersections
      Intersection start1 = new Intersection(-1,3);
      Intersection end1 = new Intersection(2,4);
      Intersection i1 = new Intersection(0,3); //all possible intermediate values
      Intersection i2 = new Intersection(-1,4);
      Intersection i4 = new Intersection(2,3);
      Intersection i5 = new Intersection(1,3);
      Intersection i6 = new Intersection(0,4);
      Intersection i7 = new Intersection(1,4);
      
      //create the expected paths and add the intersections to their path
      Path pa1 = new Path();
      //expected path: 1.(-1,3)->(-1,4)->(0,4)->(1,4)->(2,4)
      pa1.addHead(start1);
      pa1.addTail(i2);
      pa1.addTail(i6);
      pa1.addTail(i7);
      pa1.addTail(end1);
      
      
      Path pa2 = new Path();
      //expected path: 2.(-1,3)->(0,3)->(1,3)->(2,3)->(2,4)
      pa2.addHead(start1);
      pa2.addTail(i1);
      pa2.addTail(i5);
      pa2.addTail(i4);
      pa2.addTail(end1);
        
       
      Path pa3 = new Path();
      //expected path: 3.(-1,3)->(0,3)->(0,4)->(1,4)->(2,4)
      pa3.addHead(start1);
      pa3.addTail(i1);
      pa3.addTail(i6);
      pa3.addTail(i7);
      pa3.addTail(end1);
      
      Path pa4 = new Path();
      //expected path: 4.(-1,3)->(0,3)->(1,3)->(1,4)->(2,4)
      pa4.addHead(start1);
      pa4.addTail(i1);
      pa4.addTail(i5);
      pa4.addTail(i7);
      pa4.addTail(end1);
      
      //add the paths to the expectedPaths arrayList
      expectedPaths.add(pa1);
      expectedPaths.add(pa2);
      expectedPaths.add(pa3);
      expectedPaths.add(pa4);
      
      //arrayList of results from FindAllPaths
      ArrayList<Path> resultPaths2 = PathUtils.findAllPaths(start1,end1);
      //System.out.println(resultPaths); debug line
      
      //3rd test case start-(0,1) end-(4,2)
      //5 expected paths:
      //1.(0,1)->(0,2)->(1,2)->(2,2)->(3,2)->(4,2)
      //2.(0,1)->(1,1)->(2,1)->(3,1)->(4,1)->(4,2)
      //3.(0,1)->(1,1)->(1,2)->(2,2)->(3,2)->(4,2)
      //4.(0,1)->(1,1)->(2,1)->(2,2)->(3,2)->(4,2)
      //5.(0,1)->(1,1)->(2,1)->(3,1)->(3,2)->(4,2)
      
      //create the intersections
      Intersection start2 = new Intersection(0,1);
      Intersection end2 = new Intersection(4,2);
      Intersection j1 = new Intersection(0,2); //all possible intermediate values
      Intersection j2 = new Intersection(1,1);
      Intersection j3 = new Intersection(1,2);
      Intersection j4 = new Intersection(2,1);
      Intersection j5 = new Intersection(2,2);
      Intersection j6 = new Intersection(3,1);
      Intersection j7 = new Intersection(3,2);
      Intersection j8 = new Intersection(4,1);
      
      //create the expected paths and add the intersections to their path
      Path path1 = new Path();
      //expected path: 1.(0,1)->(0,2)->(1,2)->(2,2)->(3,2)->(4,2)
      path1.addHead(start2);
      path1.addTail(j1);
      path1.addTail(j3);
      path1.addTail(j5);
      path1.addTail(j7);
      path1.addTail(end2);
      
      
      Path path2 = new Path();
      //expected path: 2.(0,1)->(1,1)->(2,1)->(3,1)->(4,1)->(4,2)
      path2.addHead(start2);
      path2.addTail(j2);
      path2.addTail(j4);
      path2.addTail(j6);
      path2.addTail(j8);
      path2.addTail(end2);
        
       
      Path path3 = new Path();
      //expected path: 3.(0,1)->(1,1)->(1,2)->(2,2)->(3,2)->(4,2)
      path3.addHead(start2);
      path3.addTail(j2);
      path3.addTail(j3);
      path3.addTail(j5);
      path3.addTail(j7);
      path3.addTail(end2);
      
      Path path4 = new Path();
      //expected path: 4.(0,1)->(1,1)->(2,1)->(2,2)->(3,2)->(4,2)
      path4.addHead(start2);
      path4.addTail(j2);
      path4.addTail(j4);
      path4.addTail(j5);
      path4.addTail(j7);
      path4.addTail(end2);
      
      Path path5 = new Path();
      //expected path: 5.(0,1)->(1,1)->(2,1)->(3,1)->(3,2)->(4,2)
      path5.addHead(start2);
      path5.addTail(j2);
      path5.addTail(j4);
      path5.addTail(j6);
      path5.addTail(j7);
      path5.addTail(end2);
      
      //add the paths to the expectedPaths arrayList
      expectedPaths.add(path1);
      expectedPaths.add(path2);
      expectedPaths.add(path3);
      expectedPaths.add(path4);
      expectedPaths.add(path5);
      
      //arrayList of results from FindAllPaths
      ArrayList<Path> resultPaths3 = PathUtils.findAllPaths(start2,end2);
      //System.out.println(resultPaths); debug line
      //check that the resultPaths1,resultPaths2, and resultPaths3 have the correct number of paths
      if(resultPaths1.size() == 6 && resultPaths2.size() == 4 && resultPaths3.size() == 5) {
        //call helper method 
        if(tfapHelper(expectedPaths,resultPaths1) == true && 
            tfapHelper(expectedPaths,resultPaths2) == true && tfapHelper(expectedPaths,resultPaths3) 
            == true) {
          return true;
        }
      }
      return false; //default value
    }
    catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * Private helper method for testFindAllPathsOnePath() and testFindAllPathsRecursive()
   * @param expectedPaths an ArrayList of the expected paths 
   * @param resultPaths an ArrayList of the paths findAllPaths returns
   * @return true if the expected paths meets the paths findAllPaths returns
   */
  private static boolean tfapHelper(ArrayList<Path>expectedPaths, ArrayList<Path>resultPaths){
    //loop through the expected paths arraylist for each resultPath path and compare their toStrings
    //if they're the same then return true
    for(int i = 0;i<resultPaths.size();i++) {
      for(int j = 0;j<expectedPaths.size();j++) {
        if(resultPaths.get(i).toString().equals(expectedPaths.get(j).toString())) {
          return true;
        }
      }
    }
    return false;
  }
  /**
   * Main method for inputting coordinates
   * @param args a string array accepted from the user
   */
  public static void main(String[] args) {
    try (Scanner keyboard = new Scanner(System.in)) {
      int startX, startY, endX, endY;
      String input = "Y";
      
      while (input.equalsIgnoreCase("Y")) {
        System.out.print("Enter starting X coordinate: ");
        startX = keyboard.nextInt();
        System.out.print("Enter starting Y coordinate: ");
        startY = keyboard.nextInt();
        System.out.print("Enter ending X coordinate: ");
        endX = keyboard.nextInt();
        System.out.print("Enter ending Y coordinate: ");
        endY = keyboard.nextInt();
        
        Intersection start = new Intersection(startX, startY);
        Intersection end = new Intersection(endX, endY);
        
        System.out.println("Number of paths from start to end: " + PathUtils.countPaths(start, end));
        System.out.println("List of possible paths:");
        
      for (Path p : PathUtils.findAllPaths(start, end)) {
        System.out.println(p);
      }
      do {
        System.out.print("Try another route? (Y/N): ");
        input = keyboard.next();
       } while (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"));
      }
    }
    //calling all the testers
    System.out.println("testCountPathsNoPath(): " +testCountPathsNoPath());
    System.out.println("testCountPathsOnePath(): " + testCountPathsOnePath());
    System.out.println("testCountPathsRecursive(): " + testCountPathsRecursive());
    System.out.println("testFindAllPathsNoPath(): " + testFindAllPathsNoPath());
    System.out.println("testFindAllPathsOnePath(): " + testFindAllPathsOnePath());
    System.out.println("testFindAllPathsRecursive(): " + testFindAllPathsRecursive());
  }
}
