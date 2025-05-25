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
// Online Sources: https://piazza.com/class/l7f41s35yau64i/post/3424 - this post helped me with the
//                 deepCopy() method, hobbes' lecture notes
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based heap implementation of a priority queue containing Courses. Guarantees the
 * max-heap invariant, so that the Course at the root should have the highest score, and all
 * children always have a score lower than or equal to their parent's.
 * 
 * The root of a non-empty queue is always at index 0 of this array-heap.
 */
public class CourseQueue implements Iterable<Course>, PriorityQueueADT<Course> {
  
  // data fields
  private Course[] queue; // array max-heap of courses representing this priority queue
  private int size;       // number of courses currently in this priority queue
  
  /**
   * Creates a new, empty CourseQueue with the given capacity
   * 
   * @param capacity the capacity of this CourseQueue
   * @throws IllegalArgumentException if the capacity is not a positive integer
   */
  public CourseQueue(int capacity) {
    if(capacity < 0) {
      throw new IllegalArgumentException("The capacity has to be a positive integer");
    }
    if(capacity == 0) {
      throw new IllegalArgumentException("The capacity must be a nonzero positive integer");
    }
    queue = new Course[capacity];
    size = 0;
  }
  
  /**
   * Returns a deep copy of this CourseQueue containing all of its elements in the same order.
   * This method does not return the deepest copy, meaning that you do not need to duplicate 
   * courses. Only the instance of the heap (including the array and its size) will be duplicated.
   * 
   * @return a deep copy of this CourseQueue, which has the same capacity and size as this queue.
   */
  public CourseQueue deepCopy() {
    //create new CourseQueue with the same capacity as queue
    CourseQueue copy = new CourseQueue(queue.length);
    //copy over the elements from queue
    for(int i = 0;i<size;i++) {
      copy.queue[i] = queue[i];
    }
    //keep the size the same
    copy.size = size;
    
    return copy;
  }
  
  /**
   * Returns an Iterator for this CourseQueue which proceeds from the highest-priority to the
   * lowest-priority Course in the queue. Note that this should be an iterator over a DEEP COPY
   * of this queue.
   * 
   * @see CourseIterator
   * @return an Iterator for this CourseQueue
   */
  @Override
  public Iterator<Course> iterator() {
    return new CourseIterator(this.deepCopy());
  }
  
  ///////////////////////////// TODO: PRIORITY QUEUE METHODS //////////////////////////////////
  // Add the @Override annotation to these methods once this class implements PriorityQueueADT!
  
  /**
   * Checks whether this CourseQueue is empty
   * 
   * @return {@code true} if this CourseQueue is empty
   */
  @Override
  public boolean isEmpty() {
    if(size == 0) return true;
    return false; 
  }
  
  /**
   * Returns the size of this CourseQueue
   * 
   * @return the size of this CourseQueue
   */
  @Override
  public int size() {
    return size;
  }
  
  /**
   * Adds the given Course to this CourseQueue and use the percolateUp() method to
   * maintain max-heap invariant of CourseQueue. Courses should be compared using 
   * the Course.compareTo() method.
   * 
   * 
   * @param toAdd Course to add to this CourseQueue
   * @throws NullPointerException if the given Course is null
   * @throws IllegalStateException with a descriptive error message if this CourseQueue is full
   */
  @Override
  public void enqueue(Course toAdd) throws NullPointerException, IllegalStateException {
    //if toAdd is null throw new NullPointerException
    if(toAdd == null) {
      throw new NullPointerException("The Course to be added can't be null");
    }
    //if queue is full
    if(size == queue.length) {
      throw new IllegalStateException("This CourseQueue is full");
    }
    //special case: if size is 0
    if(size == 0) {
      //make the root toAdd then increase size
      queue[0] = toAdd;
      size++;
    }
    //else if size is less than queue's capacity
    else if(size <= queue.length){
      //find the next empty leaf node and then add toAdd there
      for(int i = 0;i<size+1;i++) {
        if(queue[i] == null) {
          queue[i] = toAdd;
        }
      }
      //increase size
      size++;
      //call the helper method
      percolateUp(size-1);
    }
  }
  
  /**
   * Removes and returns the Course at the root of this CourseQueue, i.e. the Course
   * with the highest priority. Use the percolateDown() method to maintain max-heap invariant of
   * CourseQueue. Courses should be compared using the Course.compareTo() method.
   * 
   * @return the Course in this CourseQueue with the highest priority
   * @throws NoSuchElementException with a descriptive error message if this CourseQueue is
   *                                empty
   */
  @Override
  public Course dequeue() throws NoSuchElementException {
    if(size == 0) {
      throw new NoSuchElementException("This CourseQueue is empty");
    }
    // find root
    Course remove = queue[0];  
    
    //replace with last Course in queue
    if(size > 1) {
      queue[0] = queue[size-1];
      queue[size-1] = null;//set the last Course in queue to be null
      size--;//decrease size
      //call helper method 
      percolateDown(0);
    }
    //special case: remove the course at index 0
    else {
      queue[0] = null;
      size--;
    }
   
    return remove;
  }
  
  /**
   * Returns the Course at the root of this CourseQueue, i.e. the Course with
   * the highest priority.
   * 
   * @return the Course in this CourseQueue with the highest priority
   * @throws NoSuchElementException if this CourseQueue is empty
   */
  @Override
  public Course peek() throws NoSuchElementException {
    //if empty throw NoSuchElementException otherwise return index 0
    if(isEmpty()) throw new NoSuchElementException("This queue is empty");
    return this.queue[0];
  }
  
  ///////////////////////////// TODO: QUEUE HELPER METHODS //////////////////////////////////
  
  /**
   * Restores the max-heap invariant of a given subtree by percolating its root down the tree. If 
   * the element at the given index does not violate the max-heap invariant (it is higher priority 
   * than its children), then this method does not modify the heap. 
   * 
   * Otherwise, if there is a heap violation, then swap the element with the correct child and 
   * continue percolating the element down the heap.
   * 
   * This method may be implemented iteratively or recursively.
   * 
   * @param index index of the element in the heap to percolate downwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  protected void percolateDown(int index) throws IndexOutOfBoundsException {
    if(index>queue.length-1) {
      throw new IndexOutOfBoundsException("This index is out of bounds");
    }
    // get the children's indices
    int leftChild = 2*index + 1;
    int rightChild = 2*index + 2;
    
    // no children
    if (leftChild >= size) {
      return;
    }
    // one child
    else if (rightChild >= size) {
      if (queue[index].compareTo(queue[leftChild])< 0 ) {
        //swap the index and child
        Course temp = queue[index];
        queue[index] = queue[leftChild];
        queue[leftChild] = temp;
        
        return;
      }
      else {
        return;
      }
    }
    // case 1: two children, compare to BOTH child values
    else {
      // swap with larger child if this value is larger than it
      if (queue[index].compareTo(queue[leftChild])< 0 ||
          queue[index].compareTo(queue[rightChild])<0) {
        // find smaller child index
        int largerChild = leftChild;
        if (queue[leftChild].compareTo(queue[rightChild]) < 0)
          largerChild = rightChild;
        
        // swap
        Course temp = queue[index];
        queue[index] = queue[largerChild];
        queue[largerChild] = temp;
        
        // keep percolating
        percolateDown(largerChild);
      }
      // stop percolating when newRoot is less than both children
      else {
        return;
      }
    }
  }
  
  /**
   * Restores the max-heap invariant of the tree by percolating a leaf up the tree. If the element 
   * at the given index does not violate the max-heap invariant (it is lower priority than its 
   * parent), then this method does not modify the heap.
   * 
   * Otherwise, if there is a heap violation, swap the element with its parent and continue
   * percolating the element up the heap.
   * 
   * This method may be implemented iteratively or recursively.
   * 
   * @param index index of the element in the heap to percolate upwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  protected void percolateUp(int index) throws IndexOutOfBoundsException {
    if(index>queue.length-1) {
      throw new IndexOutOfBoundsException("This index is out of bounds");
    }
    // percolate UP (towards the root): find the parent index
    int parentIndex = (index-1)/2;
    
    // compare thing at index to thing at parent index
    int compare = queue[index].compareTo(queue[parentIndex]);
    
    // swap those things IF thing at index was GREATER
    if (compare > 0) {
      Course temp = queue[parentIndex];
      //swap
      queue[parentIndex] = queue[index];
      queue[index] = temp;
      
      // stop when the new Course is the root
      if (parentIndex == 0) {
        // stop
        return;
      }
      else {
        // keep on percolating 
        percolateUp(parentIndex);
      }
    }
    else {
      // stop percolating when newData is greater than or equal to its parent
      return;
    }
  
  }
  
  ////////////////////////////// PROVIDED: TO STRING ////////////////////////////////////
  
  /**
   * Returns a String representing this CourseQueue, where each element (course) of the queue is 
   * listed on a separate line, in order from the highest priority to the lowest priority.
   * 
   * @author yiwei
   * @see Course#toString()
   * @see CourseIterator
   * @return a String representing this CourseQueue
   */
  @Override
  public String toString() {
    StringBuilder val = new StringBuilder();
    
    for (Course c : this) {
      val.append(c).append("\n");
    }
    
    return val.toString().trim();
  }

}
