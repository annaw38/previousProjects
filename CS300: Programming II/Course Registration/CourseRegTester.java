//////////////// P10 Course Registration//////////////////////////
// Title:    P010 Course Registration Tester 
// Course:   CS 300 Fall 2022
//
// Author:   Anna Wang
// Email:    awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:     None
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/3681 - this post helped me with a 
//                  CourseRegtester test case
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This class implements unit test methods to check the correctness of Course,  CourseIterator, 
 * CourseQueue and CourseReg classes in P10.
 * 
 * Be aware that all methods in this class will be run against not only your code, but also our own
 * working and broken implementations to verify that your tests are working appropriately!
 */
public class CourseRegTester {
  
  /**
   * START HERE, and continue with testCompareTo() after this.
   * 
   * This method must test the Course constructor, accessor, and mutator methods, as well as its
   * toString() implementation. The compareTo() method will get its own test.
   * 
   * @see Course
   * @return true if the Course implementation is correct; false otherwise
   */
  public static boolean testCourse() {
    try {
      //test case 1: blank department name
      try {
        Course one = new Course(" ",550, 3, 20);
        String oneExp = "  550 (20 seats)";
        //one should throw IllegalArgumentException and not have a toString
        if(one.toString().trim().equals(oneExp)) {
          return false;
        }
      }catch(IllegalArgumentException e) {
        //do nothing
        //return true;
      }catch(Exception e) {
        return false;
      }
      
      //test case 2: invalid course number
      try {
        Course two = new Course("SOC", -100, 3, 10);
        String twoExp = "SOC -100 (10 seats)";
        //two should throw IllegalArgumentException and not have a toString
        if(two.toString().trim().equals(twoExp)) {
          return false;
        }
      }catch(IllegalArgumentException e) {
        //do nothing
        //return true;
      }catch(Exception e) {
        return false;
      }
      //test case 3: invalid number of credits
      try {
        Course three = new Course("PHYSICS", 320, 6, 1);
        String threeExp = "PHYSICS 320 (20 seats)";
        //three should throw IllegalArgumentException and not have a toString
        if(three.toString().trim().equals(threeExp)) {
          return false;
        }
      }catch(IllegalArgumentException e) {
        //do nothing 
        //return true;
      }catch(Exception e) {
        return false;
      }
      //test case 4: negative number of available seats
      try {
        Course four = new Course("BIO", 450, 4, -10);
        String fourExp = "BIO 450 (-10 seats)";
        //four should throw IllegalArgumentException and not have a toString
        if(four.toString().trim().equals(fourExp)) {
          return false;
        }
      }catch(IllegalArgumentException e) {
        //do nothing
        //return true;
      }catch(Exception e) {
        return false;
      }
      //all invalid inputs
      try {
        Course none = new Course("         ", -134, 10, -50);
      }catch(IllegalArgumentException e) {
        //do nothing
      }catch(Exception e) {
        return false;
      }
      //test case 5: a valid new course
      try {
        Course five = new Course("ENG", 150, 4, 9);
      }
      catch(Exception e) {
        return false;
      }
      
      Course six = new Course("MATH", 283, 3, 10);
      String expect = "MATH 283 (10 seats)";
      //test the getters, setters and toString
      try {
        six.setSeatsAvailable(-10);
      }catch(IllegalArgumentException e) {
        //do nothing
        //return true;
      }catch(Exception e) {
        return false;
      }
      //test toString to compare with the expected toString
      if(!six.toString().trim().equals(expect)) {
        return false;
      }
      //try setting to an invalid number of seats available
      try {
        six.setSeatsAvailable(-10);
      }catch(IllegalArgumentException e) {
        // do nothing
      }catch(Exception e) {
        return false;
      }
      //try a valid number of seats available 
      try {
        six.setSeatsAvailable(15);
      }catch(Exception e) {
        return false;
      }
      //compare the new expected toString with the new toString
      String newExpect = "Math 283 (15 seats)";
      if(!six.toString().trim().equals(newExpect));
      //try setting to a closed number of seats
      try {
        six.setSeatsAvailable(0);
      }catch(Exception e) {
        return false;
      }
      //try setting to an invalid rating
      try {
        six.setProfessor("John", -1.0);
      }catch(IllegalArgumentException e) {
        //do nothing
        //return true;
      }catch(Exception e) {
        return false;
      }
      //try a null professor and invalid rating
      try {
        six.setProfessor(null, -1);
      }catch(Exception e) {
        return false;
      }
      Course seven = new Course("MATH", 283, 3, 10);
      //try a valid setProfessor and test accessors
      try {
        six.setProfessor("John", 3.0);
        String expected = "MATH 283 (closed) with John (3.0)";
        String result = six.toString().trim();
        if(six.getNumCredits() == 3 && result.equals(expected) && !six.equals(seven)) {
          return true;
        }
        return false; 
      }catch(Exception e) {
        return false;
      }
    }catch(Exception e) {
      return false;
    }
   
  }
  
  /**
   * This method must test the Course compareTo() implementation. Be sure to test ALL FOUR levels
   * of the comparison here!
   * 
   * Once you complete this test, finish the Course implementation if you have not done so already,
   * then move to testCourseQueue() and testEnqueueDequeue().
   * 
   * @see Course#compareTo(Course)
   * @return true if the compareTo() implementation is correct; false otherwise
   */
  public static boolean testCompareTo() {
    try {
      boolean first = false; //result of first 5 test cases
      boolean second = false; // result of test cases 5-10
      boolean third = false; // result of test cases 11-15
      //test case 1: first in MAJOR_DEPT, second isn't in MAJOR_DEPT
      Course a = new Course("CS", 400, 4, 10);
      Course b = new Course("MATH", 394, 3, 5);
      Course c = new Course("CS", 350, 3, 20);
      Course d = new Course("SOC", 129, 2, 0);
      Course e = new Course("CS", 570, 3, 0);
      Course f = new Course("HIST", 392, 2, 1);
      Course g = new Course("GEN", 134, 3, 3);
      //test case 1: first in MAJOR_DEPT, second isn't in MAJOR_DEPT
      if(a.compareTo(b) > 0 && b.compareTo(a)<0) {
        //test case 2: both in MAJOR_DEPT, both have seats open, and no known professor
        if(a.compareTo(c)==0) {
          //test case 3: both in MAJOR_DEPT, both have seats open and known professor, first has 
          //higher rating than second
          a.setProfessor("hobbes", 4.5);
          c.setProfessor("jeff", 3.5);
          if(a.compareTo(c)>0 && c.compareTo(a)<0) {
            //test case 4: both not in MAJOR_DEPT, first has seats open and no known professor, 
            //second has no seats open and known professor
            if(b.compareTo(d)>0 && d.compareTo(b)<0) {
              //test case 5: first not in MAJOR_DEPT, second is in MAJOR_DEPT
              if(d.compareTo(a)<0 && a.compareTo(d)>0) {
                first = true;
              }
            }
          }
        }
      }
      
      //test case 6: both not in MAJOR_DEPT, first has available seats, second doesn't
      if(b.compareTo(d)>0 && d.compareTo(b)<0) {
        //test case 7: both in MAJOR_DEPT, first doesn't have available seats, second does
        if(e.compareTo(a)<0 && a.compareTo(e)>0) {
          //test case 8: both in MAJOR_DEPT, both have available seats, first has set professor,
          // second doesn't
          e.setSeatsAvailable(10);
          if(a.compareTo(e)>0 && e.compareTo(a)<0) {
            ///test case 9: both in MAJOR_DEPT, both have available seats, both has set professor, 
            //and both professors have the same ranking
            e.setProfessor("mouna", 4.5);
            if(a.compareTo(e) == 0) {
              //test case 10: both not in MAJOR_DEPT, both have available seats, both has known 
              //professor, and both professors have the same ranking
              d.setSeatsAvailable(7);
              b.setProfessor("nate", 4.0);
              d.setProfessor("jon", 4.0);
              if(b.compareTo(d) == 0) {
                second = true;
              }
            }
          }
        }
      }
     
      //test case 11: both not in MAJOR_DEPT, both have available seats, both have known professor,
      //first has higher ranking
      f.setProfessor("jill", 0.1);
      if(b.compareTo(f)>0 && f.compareTo(b)<0) {
        //test case 12: both not in MAJOR_DEPT, both have known professor, first has lower ranking
        //than second
        if(f.compareTo(b)<0 && b.compareTo(f)>0) {
          //test case 13: both not in MAJOR_DEPT, both have available seats, first has professor, 
          // second doesn't
          if(f.compareTo(g)>0 && g.compareTo(f)<0) {
            //test case 14: both not in MAJOR_DEPT, both don't have available seats
            b.setSeatsAvailable(0);
            d.setSeatsAvailable(0);
            if(b.compareTo(d) == 0) {
              //test case 15: first isn't in MAJOR_DEPT, second is in MAJOR_DEPT
              if(g.compareTo(a)<0 && a.compareTo(g)>0) {
                third = true;
              }
            }
          }
        }
      }
      //test some cases that should return false
      //e is in MAJOR_DEPT, b is not
      if(b.compareTo(e)>=0 && e.compareTo(b)<=0) {
        return false;
      }
      //both in MAJOR_DEPT, have seats open, a has a higher rated prof than c
      if(c.compareTo(a)>=0 && a.compareTo(c)<= 0) {
        return false;
      }
      //both not in MAJOR_DEPT, and no available seats 
      if(b.compareTo(d)!= 0) {
        return false;
      }
      //both not in MAJOR_DEPT, f has available seats, while b doesn't
      if(f.compareTo(b)<=0 && b.compareTo(f)>=0) {
        return false;
      }
      //return the results of the test cases
      return first && second && third; // TODO: complete this test
    }catch(Exception e) {
      return false;
    }
  }
  
  /**
   * This method must test the other methods in CourseQueue (isEmpty, size, peek). Verify normal 
   * cases and error cases, as well as a filled and re-emptied queue.
   * 
   * Once you have completed this method, implement the required methods in CourseQueue and verify
   * that they work correctly.
   * 
   * @see CourseQueue
   * @return true if CourseQueue's other methods are implemented correctly; false otherwise
   */
  public static boolean testCourseQueue() {
    try {
      //try creating a queue with a negative capacity
      try {
        CourseQueue q = new CourseQueue(-10);
        //q should throw IllegalArgument 
        if(q.isEmpty() == true) {
          return false;
        }
      }catch(IllegalArgumentException e) {
        //do nothing
      }catch(Exception e) {
        return false;
      }
      //create a new valid queue
      CourseQueue queue = new CourseQueue(3);
      //try peek on an empty queue -- should throw NoSuchElementException 
      try {
        queue.peek();
      }catch(NoSuchElementException e) {
        //do nothing
      }catch(Exception e) {
        return false;
      }
      //create the new courses and set professors
      Course a = new Course("CS", 400, 4, 10);
      Course b = new Course("MATH", 394, 3, 5);
      b.setProfessor("jeff", 3.5);
      Course c = new Course("BIO", 358, 4, 0);
      c.setProfessor("steve", 5.0);
      Course d = new Course("HIST", 460, 3, 8);
      boolean enqueue = false; //result of the filling the queue
      boolean dequeue = false; //result of dequeue the whole queue
      //check that the queue is empty and size is 0
      if(queue.isEmpty() && queue.size() == 0) {
        queue.enqueue(a);
        //after enqueue a check that size is 1, it's not empty and the peek's result is a 
        if(queue.size() == 1 && queue.isEmpty() == false && queue.peek().compareTo(a) == 0) {
          queue.enqueue(b);
          //after enqueue b check that the size is 2, not empty, and peek's result is still a
          if(queue.size() == 2 && queue.isEmpty() == false && queue.peek().compareTo(a) == 0) {
            queue.enqueue(c);
            //after enqueue c check that the size is 3, not empty, and peek's result is still a
            if(queue.size() == 3 && queue.isEmpty() == false && queue.peek().compareTo(a) == 0) {
              enqueue = true; //make enqueue true to verify it works as expected
            }
          }
        }
      }
      //try enqueue d to a full queue
      try {
        queue.enqueue(d);
        //check that the size didn't change because enqueue should throw IllegalStateException
        if(queue.size() == 4) {
          return false;
        }
      }catch(IllegalStateException e) {
        //do nothing
      }catch(Exception e) {
        return false;
      }
      //try dequeue from the filled queue
      //check that dequeue returns a, size is 2, and peek is b
      if(queue.dequeue() == a && queue.size() == 2 && queue.peek().compareTo(b) == 0) {
        //check that dequeue returns b, size is 1, and peek is c
        if(queue.dequeue() == b && queue.size() == 1 && queue.peek().compareTo(c) == 0) {
          //check that dequeue returns c and size is 0
          if(queue.dequeue() == c && queue.size() == 0) {
            dequeue = true; //make dequeue true to verify that dequeue worked as expected
          }
        }
      }
      //try dequeue on an empty queue- should catch a NoSuchElementException 
      try {
        queue.dequeue();
        //size should be 0
        if(queue.size() != 0) {
          return false;
        }
      }catch(NoSuchElementException e) {
        //do nothing
      }catch(Exception e) {
        return false;
      }
      //return results of enqueue and dequeue
      return enqueue && dequeue; // TODO: complete this test
    }catch(Exception e) {
      return false;
    }
  }
  
  /**
   * This method must test the enqueue and dequeue methods in CourseQueue. Verify normal cases and
   * error cases, as well as filling and emptying the queue.
   * 
   * You may also test the percolate methods directly, though this is not required.
   * 
   * Once you have completed this method, implement the enqueue/dequeue and percolate methods in
   * CourseQueue and verify that they work correctly, then move on to testCourseIterator().
   * 
   * @see CourseQueue#enqueue(Course)
   * @see CourseQueue#dequeue()
   * @return true if the CourseQueue enqueue/dequeue implementations are correct; false otherwise
   */
  public static boolean testEnqueueDequeue() {
    try {
      //create the courses 
      Course a = new Course("CS", 400, 4, 10);
      Course b = new Course("MATH", 394, 3, 5);
      b.setProfessor("jeff", 3.5);
      Course c = new Course("BIO", 358, 4, 0);
      c.setProfessor("steve", 5.0);
      Course d = new Course("HIST", 460, 3, 8);
      Course e = new Course("ENG", 237, 2, 0);
      e.setProfessor("john", 4.0);
      Course f = new Course("ART", 469, 4, 0);
      CourseQueue queue = new CourseQueue(5);
      boolean enqueue = false; //result of the enqueue testers
      boolean dequeue = false; //result of the dequeue testers
      //try enqueue a 
      queue.enqueue(a);
      //check that the queue is not empty anymore, size is 1 and highest priority course is a 
      if(queue.isEmpty()== false && queue.size() == 1 && queue.peek().compareTo(a) == 0 ) { 
        // enqueue b
        queue.enqueue(b);
        //check that the size is 2 and highest priority course is still a 
        if(queue.size() == 2 && queue.peek().compareTo(a) == 0) {
           // queue.toString().trim().equals(enqExpected) ) {
          //enqueue c
          queue.enqueue(c);
          //check that the size is 3 and highest priority course is still a 
          if(queue.size() == 3 && queue.peek().compareTo(a) == 0) {
            //enqueue d 
            queue.enqueue(d);
            //check that size is 4 and a is still peek's result
            if(queue.size()== 4 && queue.peek().compareTo(a) == 0) {
              //enqueue e
              queue.enqueue(e);
              //check that size is 5 and a is peek's result
              if(queue.size()== 5 && queue.peek().compareTo(a) == 0) {
                //return true;
                enqueue = true;
              }
            }
          }
        }
      }
     //try enqueue another course but capacity is 5 
      try {
        queue.enqueue(f);
      }catch(IllegalStateException i) {
        //do nothing
      }catch(Exception i) {
        return false;
      }
      //try enqueue a null course
      try {
        queue.enqueue(null);
      }catch(NullPointerException i) {
        //do nothing
        //return true;
      }catch(Exception i) {
        return false;
      }
      //testing the dequeue method
      //check that size is 4 and peek is now b and that it returned a 
      if(queue.dequeue() == a && queue.size() == 4 && queue.peek().compareTo(b) == 0) {
        //check that dequeue returns b, size is 3, and peek is d 
        if(queue.dequeue() == b && queue.size() == 3 && queue.peek().compareTo(d) == 0) {
          //check that dequeue returns d, size is 2, and peek is c 
          if(queue.dequeue() == d && queue.size() == 2 && queue.peek().compareTo(c) == 0) {
            //check that dequeue returns c, size is 1, and peek is e
            if(queue.dequeue() == c && queue.size() == 1 && queue.peek().compareTo(e) == 0) {
              //check that dequeue returns e and size is 0 
              if(queue.dequeue() == e && queue.size() == 0) {
                dequeue = true;
              }
            }
          }
        }
      }
      //try peek on an empty queue - should throw NoSuchElementException 
      try {
        queue.peek();
      }catch(NoSuchElementException i) {
        //do nothing
      }catch(Exception i) {
        return false;
      }
      //try dequeue on an empty queue
      try {
        queue.dequeue();
      }catch(NoSuchElementException i) {
        //do nothing
      }catch(Exception i) {
        return false;
      }
     
      return enqueue && dequeue; // return the results of enqueue and dequeue
    }
    catch(Exception e){
      return false;
    }
  }
  
  /**
   * This method must test the CourseIterator class. The CourseIterator iterates through a deep copy
   * of a CourseQueue in decreasing order of priority, returning each Course object in turn.
   * 
   * Once you have completed this method, implement the CourseIterator class and make CourseQueue
   * into an Iterable class. Verify that this works correctly, and then move on to the final test
   * method: testCourseReg().
   * 
   * @see CourseIterator
   * @return true if the CourseIterator implementation is correct; false otherwise
   */
  public static boolean testCourseIterator() {
    try {
      //create the courses and queue
      Course a = new Course("CS", 400, 4, 10);
      Course b = new Course("MATH", 394, 3, 5);
      b.setProfessor("jeff", 3.5);
      Course c = new Course("BIO", 358, 4, 0);
      c.setProfessor("steve", 5.0);
      Course d = new Course("HIST", 460, 3, 8);
      Course e = new Course("ENG", 237, 2, 0);
      e.setProfessor("john", 4.0);
      CourseQueue queue = new CourseQueue(5);
      boolean result = false; //result of the test cases 
      //enqueue all the courses
      queue.enqueue(a);
      queue.enqueue(b);
      queue.enqueue(d);
      queue.enqueue(c);
      queue.enqueue(e);
      //create the iterator
      Iterator<Course> it = queue.iterator();
      //Course i1 = it.next(); //debug line
      //System.out.println(i1); //debug line
      //check that the first element is course a and there's still more courses in the queue
      if(it.next().equals(a) && it.hasNext() == true) {
        //check that the next element is course b and there's still more courses in the queue
        if(it.next().equals(b) && it.hasNext() == true) {
          //check that the next element is course d and there's still more courses in the queue
          if(it.next().equals(d) && it.hasNext() == true) {
            //check that the next element is course c and there's still one more course in the queue
            if(it.next().equals(c) && it.hasNext() == true) {
              //check that the next element is course e and there's no more courses in the queue
              if(it.next().equals(e) && it.hasNext() == false) {
                result = true; //set result to true if all the previous test cases are true
              }
            }
          }
        }
      }
      //try calling iterator's next on an empty queue - should get NoSuchElementException 
      try {
        it.next();
      }catch(NoSuchElementException i) {
        //do nothing
      }
      catch(Exception i) {
        return false;
      }
      return result; //return result of the test cases
    }catch(Exception i) {
      return false;
    }
  }
  
  /**
   * This method must test the constructor and three methods of the CourseReg class (setCreditLoad,
   * add, and getRecommendedCourses). Verify normal cases and error cases.
   * 
   * Once you have completed this method, implement CourseReg and verify that it works correctly.
   * Then you're DONE! Save and submit to gradescope, and enjoy being DONE with programming
   * assignments in CS 300 !!!
   * 
   * @see CourseReg
   * @return true if CourseReg has been implemented correctly; false otherwise
   */
  public static boolean testCourseReg() {
    try {
      //error case: adding a negative amount of courses
      try {
        CourseReg reg1 = new CourseReg(-10, 1);
      }catch(IllegalArgumentException e) {
        //do nothing
      }catch(Exception e) {
        return false;
      }
      //test case for a non positive number as creditLoad
      try {
        CourseReg reg2 = new CourseReg(4, 0);
      }catch(IllegalArgumentException e) {
        //do nothing
      }catch(Exception e) {
        return false;
      }
      //a valid CourseReg
      CourseReg reg = new CourseReg(5, 12);
        
      //test full queue 
      Course a = new Course("CS", 400, 4, 10);
      Course b = new Course("MATH", 394, 3, 5);
      b.setProfessor("jeff", 3.5);
      Course c = new Course("BIO", 358, 4, 0);
      c.setProfessor("steve", 5.0);
      Course d = new Course("HIST", 460, 3, 8);
      Course e = new Course("ENG", 237, 2, 0);
      e.setProfessor("john", 4.0);
      Course f = new Course("CHEM", 319, 3, 0); //extra course to be added past the queue capacity
      boolean addResult = false;//result of adding the courses to CourseReg
      boolean getResult = false; //result of getRecommendedCourses
      if(reg.add(a) == true && reg.add(b) == true && reg.add(c) == true && reg.add(d) == true
            && reg.add(e) == true && reg.add(f) == false && reg.add(null) == false) {
        addResult = true;
      }
        
      //test getrecommendedcourses debug lines
      /*System.out.println(reg.getRecommendedCourses());
      System.out.println("//////////////////////////////");*/
      String str = "CS 400 (10 seats)\nMATH 394 (5 seats) with jeff (3.5)\nHIST 460 (8 seats)";
      CourseReg reg3 = new CourseReg(5, 2);
      reg3.add(e);
      reg3.add(f);
      String exp3 = "ENG 237 (closed) with john (4.0)";
      CourseReg reg5 = new CourseReg(6, 4);
      reg5.add(a);
      reg5.add(e);
      reg5.add(f);
      String exp5 = "CS 400 (10 seats)";
      
      CourseReg reg6 = new CourseReg(3, 4);
      Course cs1 = new Course("CS", 693, 3, 200);
      Course cs2 = new Course("CS", 507, 3, 0);
      Course chem1 = new Course("CHEM", 374, 1, 10);
      reg6.add(cs1);
      reg6.add(cs2);
      reg6.add(chem1);
      String exp6 = "CS 693 (200 seats)";
     //test that all the recommended courses
      if(reg.getRecommendedCourses().trim().equals(str.trim())) {
        if(reg3.getRecommendedCourses().trim().equals(exp3.trim())) {
          if(reg5.getRecommendedCourses().trim().equals(exp5.trim())) {
            if(exp6.trim().equals(reg6.getRecommendedCourses().trim())) {
              getResult = true;
            }
          }
        }
      }
      
      return addResult && getResult; // TODO: complete this test
    }catch(Exception e) {
      return false;
    }
  }
  
  /**
   * This method calls all test methods defined by us; you may add additional methods to this if
   * you like. All test methods must be public static boolean.
   * 
   * @return true if all tests in this class return true; false otherwise
   */
  public static boolean runAllTests() {
    boolean testVal = true;
    
    // test Course
    System.out.print("testCourse(): ");
    if (!testCourse()) {
      System.out.println("FAIL");
      testVal = false;
    } else { System.out.println("pass"); }
    
    // test compareTo
    System.out.print("testCompareTo(): ");
    if (!testCompareTo()) {
      System.out.println("FAIL");
      testVal = false;
    } else { System.out.println("pass"); }
    
    // test CourseIterator
    System.out.print("testCourseIterator(): ");
    if (!testCourseIterator()) {
      System.out.println("FAIL");
      testVal = false;
    } else { System.out.println("pass"); }
    
    // test CourseQueue enqueue/dequeue
    System.out.print("testEnqueueDequeue(): ");
    if (!testEnqueueDequeue()) {
      System.out.println("FAIL");
      testVal = false;
    } else { System.out.println("pass"); }
    
    // test CourseQueue
    System.out.print("testCourseQueue(): ");
    if (!testCourseQueue()) {
      System.out.println("FAIL");
      testVal = false;
    } else { System.out.println("pass"); }
    
    // test CourseReg
    System.out.print("testCourseReg(): ");
    if (!testCourseReg()) {
      System.out.println("FAIL");
      testVal = false;
    } else { System.out.println("pass"); }
    
    return testVal;
  }
  
  /**
   * Calls runAllTests() so you can verify your program
   * 
   * @param args input arguments if any.
   */
  public static void main(String[] args) {
    runAllTests();
  }
}
