//////////////// P10 Course Registration//////////////////////////
// Title:    P10 Course Reg
// Course:   CS 300 Fall 2022
//
// Author:   Anna Wang
// Email:    awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:     None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;

/**
 * A application handler for course registration using a priority queue.
 */
public class CourseReg {

  // data fields
  private CourseQueue courses;  // the priority queue of all courses
  private int creditLoad;       // the maximum number of credits you want to take
  
  /**
   * Creates a new course registration object
   * @param capacity the maximum number of courses to store in the priority queue
   * @param creditLoad the maximum number of credits to take next semester
   * @throws IllegalArgumentException if either capacity or creditLoad are not a positive integer
   */
  public CourseReg(int capacity, int creditLoad) throws IllegalArgumentException {
    //if the capacity is not a positive integer
    if(capacity <= 0) {
      throw new IllegalArgumentException("capacity must be a positive integer");
    }
    //if creditLoad isn't a positive integer
    if(creditLoad <= 0) {
      throw new IllegalArgumentException("creditLoad must be a positive integer");
    }
    //if capacity and creditLoad are both valid then initialize them 
    courses = new CourseQueue(capacity);
    this.creditLoad = creditLoad;
  }
  
  /**
   * Returns a string representation of the highest-priority courses with a total number of credits
   * less than or equal to the creditLoad of this CourseReg object. Use the Iterable property of the
   * CourseQueue to help you out!
   * 
   * Note that this is NOT a "knapsack" problem - you're trying to maximize priority, not number of
   * credits. Just add courses to your result String until adding the next would take you over this
   * CourseReg object's creditLoad limit.
   * 
   * @return a string representation with one course on each line, where the total number of credits
   *   represented is less than or equal to the current creditLoad value
   */
  public String getRecommendedCourses() {
    String str = "";
    int total = 0;
    Iterator<Course> it = courses.iterator();
    //if total is greater than creditLoad stop
    while(total < creditLoad) {
      if(it.hasNext()) {
        total += courses.peek().getNumCredits(); //check if adding the next course's credits will 
        //go over creditLoad
        //str += it.next().toString() + "\n";
        if(total <= creditLoad) {
          //total += courses.peek().getNumCredits();
          str += it.next().toString() + "\n"; //dequeue the course and add to str
        }
        //adding another course results in the total being greater than creditLoad
        else if(total>creditLoad) {
          total-=courses.peek().getNumCredits();//subtract the next course's credits as it will go 
          //over creditLoad then return string
          return str.trim(); //trim to remove the last \n
        }
        //adding another course equals courseLoad
        else {
          return str.trim(); //trim to remove the last \n
        }
      }
      //if there's no more courses
      else {
        return str.trim(); //trim to remove the last \n
      }
    }
    return str.trim(); //trim to remove the last \n
    
  }
  
  /**
   * Tries to add the given course to the priority queue; return false if the queue is full
   * 
   * @param toAdd the course to add
   * @return true if the course was successfully added to the queue
   */
  public boolean add(Course toAdd) {
    //try enqueue the course
    try{
      courses.enqueue(toAdd);
      return true;
    }
    //if it catches an IllegalStateException then queue is full and return false
    catch(IllegalStateException e) {
      return false;
    }
    //if it catches a NullPointerException then the course toAdd is null and return false
    catch(NullPointerException e) {
      return false;
    }
  }
  
  /**
   * Updates the creditLoad data field to the provided value
   * @param creditLoad the maximum number of credits to take next semester
   * @throws IllegalArgumentException if creditLoad is not a positive integer
   */
  public void setCreditLoad(int creditLoad) throws IllegalArgumentException {
    //if creditLoad isn't a positive integer
    if(creditLoad<=0) {
      throw new IllegalArgumentException("CreditLoad must be a positive integer");
    }
    //otherwise set it to be this creditLoad
    this.creditLoad = creditLoad;
  }
}
