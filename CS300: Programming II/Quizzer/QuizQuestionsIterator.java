//////////////// P07 Quizzer//////////////////////////
// Title:    P07 Quiz Questions Iterator 
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
// Online Sources:  Monday's Lecture Notes
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This is an iterator that moves through MultipleChoiceQuestion(s) in a singly linked list defined 
 * by its head
 *
 */
public class QuizQuestionsIterator implements Iterator<MultipleChoiceQuestion> {
  private LinkedNode<MultipleChoiceQuestion> next; //refers to a node in the singly linked list of 
  //MultipleChoiceQuestion
  
  //constructor
  /**
   * Creates a new QuizQuestionsIterator to start iterating through a singly linked list storing 
   * MultipleChoiceQuestion elements
   * @param startNode pointer to the head of the singly linked list
   * 
   */
  public QuizQuestionsIterator(LinkedNode<MultipleChoiceQuestion> startNode) {
    this.next = startNode;
  }
  /**
   * Returns true if this iteration has more MultipleChoiceQuestion(s).
   * @return true if there are more MultipleChoiceQuestion(s) in this iteration
   */
  @Override
  public boolean hasNext() {
    return this.next != null;
  }
  /**
   * Returns the next MultipleChoiceQuestion in this iteration
   * @return the next MultipleChoiceQuestion in this iteration
   * @throws NoSuchElementException - with a descriptive error message if there are no more 
   * questions in this iteration
   */
  @Override
  public MultipleChoiceQuestion next() {
    if(hasNext() == false) {
      throw new NoSuchElementException("There are no more questions");
    }
    MultipleChoiceQuestion returnVal = this.next.getData();
    this.next = this.next.getNext();
    return returnVal;
  }
}
