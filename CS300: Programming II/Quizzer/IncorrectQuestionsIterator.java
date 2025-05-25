//////////////// P07 Quizzer//////////////////////////
// Title:    P07 Incorrect Questions Iterator 
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
// Online Sources:  Monday's Lecture notes
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException; 
/**
 * Implements an iterator to iterate over incorrectly answered MultipleChoiceQuestion(s) stored in a
 *  singly linked list defined by its head.
 *
 */
public class IncorrectQuestionsIterator implements Iterator<MultipleChoiceQuestion> {
  
  //data fields
  private LinkedNode<MultipleChoiceQuestion> next; //refers to a node in the singly linked list of 
  //MultipleChoiceQuestion
  
  //constructor 
  /**
   * Creates a new InCorrectQuestionsIterator to start iterating through a singly linked list 
   * storing MultipleChoiceQuestion elements
   * @param startNode pointer to the head of the singly linked list
   */
  public IncorrectQuestionsIterator(LinkedNode<MultipleChoiceQuestion> startNode) {
    this.next = startNode;
  }
  /**
   * Returns true if this iteration has more MultipleChoiceQuestion(s) answered incorrectly.
   * @return true if there are more incorrect MultipleChoiceQuestion(s) in this iteration
   */
  @Override
  public boolean hasNext() {
    //while the next question is correct continue going through list until find an incorrect one
    while(next != null) {
      if(!next.getData().isCorrect()) {
        return true;
      }
      next = next.getNext();
    }
    return false;
  }
  /**
   * Returns the next incorrect MultipleChoiceQuestion in this iteration
   * @return the next incorrect MultipleChoiceQuestion in this iteration
   * @throws NoSuchElementException - with a descriptive error message if there are no more 
   * incorrect questions in this iteration
   */
  @Override
  public MultipleChoiceQuestion next() {
    if(hasNext() == false) {
      throw new NoSuchElementException("There are no more incorrect questions");
    }
    //MultipleChoiceQuestion returnVal = next incorrect question.getData();
    MultipleChoiceQuestion returnVal = next.getData();
    //next = get next incorrectquestion;
    next = next.getNext();
    return returnVal;
  }
}