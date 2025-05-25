//////////////// P10 Course Registration//////////////////////////
// Title:    P10 Course 
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
import java.util.NoSuchElementException;

/**
 * An iterator for Courses in a priority queue, which returns the Courses in order from highest
 * priority to lowest.
 */
public class CourseIterator implements Iterator<Course> {
  
  // data field - you may NOT add any additional data fields to this class!
  private CourseQueue queue; // a DEEP COPY of the priority queue of courses to iterate over
  
  /**
   * Creates a new CourseIterator which iterates over the elements of the given CourseQueue
   * in order from the highest-priority course to the lowest-priority course
   * 
   * @param queue a DEEP COPY of the queue to iterate over
   */
  public CourseIterator(CourseQueue queue) {
    this.queue = queue;
  }

  /**
   * Returns true if the iteration has more elements.
   * 
   * @return {@code true} if the iteration has more elements
   */
  @Override
  public boolean hasNext() {
    //if the first item doesn't throw NoSuchElementException then return true
    try {
      queue.peek();
      return true;
    }//otherwise return false
    catch(NoSuchElementException e) {
      return false;
    }
  }

  /**
   * Returns the next element in the iteration. Consider how to use the priority queue's methods
   * to get the next course in descending order.
   * 
   * @return the next element in the iteration
   * @throws NoSuchElementException if the iteration has no more elements
   */
  @Override
  public Course next() throws NoSuchElementException {
    //if there's no more elements
    if(hasNext() == false) {
      throw new NoSuchElementException("There are no more courses");
    }
    //return next element in iteration
    //Course returnVal = queue.dequeue();
    //Course returnVal = queue.peek(); 
    //if there is another element, dequeue it
    return this.queue.dequeue();
  }

}