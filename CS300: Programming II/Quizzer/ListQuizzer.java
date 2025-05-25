//////////////// P07 Quizzer//////////////////////////
// Title:    P07 List Quizzer
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
// Online Sources:  Hobbes' lecture notes 
//
///////////////////////////////////////////////////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Iterable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * This class models an iterable singly-linked list data structure which stores elements of type 
 * MultipleChoiceQuestion.
 *
 */
public class ListQuizzer implements Iterable<MultipleChoiceQuestion>{
  //private data fields
  private LinkedNode<MultipleChoiceQuestion> head; //Head of this singly linked list
  private LinkedNode<MultipleChoiceQuestion> tail; //Tail of this singly linked list
  private int size; //Total number of MultipleChoiceQuestions stored in this ListQuizzer
  private ListingMode listingMode; //The listing mode of this list quizzer which defines which 
                                //questions are going to be listed while iterating through this list
  //constructor
  /**
   * this constructor creates a new empty instance of ListQuizzer which contains zero elements and 
   * sets its listing mode to be ListingMode.ALL by default.
   */
  public ListQuizzer() {
    this.head = null;
    this.tail = null;
    this.size = 0;
    this.listingMode = ListingMode.ALL;
  }
  //accessors
  /**
   * Checks whether this list is empty
   * @return true if this list is empty and false otherwise
   */
  public boolean isEmpty() {
    //if the list is empty
    if(size == 0 && head == null & tail == null) {
      return true;
    }
    return false; //default value
  }
  /**
   * Gets the size of this list
   * @return the size of this list
   */
  public int size() {
    return this.size;              
  }
  /**
   * Sets the listing mode of this list to the one provided as input
   * @param listingMode listing mode to set
   */
  public void switchMode(ListingMode listingMode) {
    this.listingMode = listingMode;
  }
  //mutators
  /**
   * Adds a specific MultipleChoiceQuestion to a given position of this list
   * @param index position index where to add the newQuestion to this list
   * @param question some MultipleChoiceQuestion to add to this list
   * @throws NullPointerException - with a descriptive error message if newQuestion is null
   * @throws IndexOutOfBoundsException - with a descriptive error message if index is OUT of the 
   * range 0 .. size() inclusive
   */
  public void add(int index, MultipleChoiceQuestion question) {
    if(question == null) {
      throw new NullPointerException("The new question to be added can't be null");
    }
    if(index == 0 && isEmpty()== true) {
      addToEmpty(question);
      size++;
    }
    if(index != 0 && isEmpty() == true) { 
      throw new IndexOutOfBoundsException("This index is invalid when adding to an empty list");
    }
    if(index < 0 || index > size) {
      throw new IndexOutOfBoundsException("The index is out of the range 0 to size: " + size);
    }
    //new linkedNode for the question to be added
    LinkedNode<MultipleChoiceQuestion> newMid = new LinkedNode<MultipleChoiceQuestion>(question);

    //special case add at beginning 
    if(index == 0) {
      //hook up new node to current start
      newMid.setNext(head);
      //move head reference
      head = newMid;
    }
    else {
      LinkedNode<MultipleChoiceQuestion> temp = head;
      for(int i = 1;i<index;i++) {
        if (temp.getNext() != null) {
          temp = temp.getNext();
        }
      }
      //hook up to next node
      newMid.setNext(temp.getNext());
      temp.setNext(newMid);
      size++;
    }
  }
  /**
   *  Adds a specific MutlipleChoiceQuestion to the head of this list
   *  @param question some MultipleChoiceQuestion to add to the head of this list
   *  @throws NullPointerException - with a descriptive error message if newQuestion is null
   */
  public void addFirst(MultipleChoiceQuestion question) {
    if(question.equals(null)) {
      throw new NullPointerException("The new question can't be null");
    }
    //if the list is empty
    if(isEmpty() == true) {
      addToEmpty(question);
      size++;
    }
    else {
    //new linkedNode for the question to be added
    LinkedNode<MultipleChoiceQuestion> newHead = new LinkedNode<MultipleChoiceQuestion>(question);
    //set setnext to the previous head and newNode to be the new head and increase size
    newHead.setNext(head);
    head = newHead;
    size++;
    }
  }
  /**
   * Adds a specific MutlipleChoiceQuestion to the tail of this list
   * @param question some MultipleChoiceQuestion to add to the tail of this list
   * @throws NullPointerException - with a descriptive error message if newQuestion is null
   */
  public void addLast(MultipleChoiceQuestion question) {
    if(question.equals(null)) {
      throw new NullPointerException("The new question can't be null");
    }
    //if the list is empty
    if(isEmpty() == true) {
      addToEmpty(question);
      size++;
    }
    else {
      //new linkedNode for the question to be added
      LinkedNode<MultipleChoiceQuestion> newTail = new LinkedNode<MultipleChoiceQuestion>(question);
      //set the previous tail's next to the new tail and newNode to be new tail and increase size
      tail.setNext(newTail);
      tail = newTail;
      size++;
    }
  }
  /**
   * Private helper method to add a new question to an empty list
   * @param question the question to be added
   */
  private void addToEmpty(MultipleChoiceQuestion question) {
    //assuming the list is empty
    //set the head and tail to this question
    LinkedNode<MultipleChoiceQuestion> newNode = new LinkedNode<MultipleChoiceQuestion>(question);
    this.head = newNode;
    this.tail = newNode;
  }
  /**
   * This method removes all the question from this list. The list should be empty after this method 
   * is called.
   */
  public void clear() {
    head = null;
    tail = null;
    size = 0;
  }
  /**
   * Checks whether this list contains a match with someQuestion
   * @param someQuestion some question to find
   * @return true if this list contains a match with someQuestion and false otherwise
   */
  public boolean contains(MultipleChoiceQuestion someQuestion) {
    LinkedNode<MultipleChoiceQuestion> temp = head;
    for(int i = 0; i<size;i++) {
      temp = temp.getNext();
      if(temp.equals(someQuestion)) {
        return true;
      }
    }
    return false; //default value
  }
  /**
   * Returns the MultipleChoiceQuestion stored at the given index within this list
   * @param index index of the MultipleChoiceQuestion to return
   * @return the MultipleChoiceQuestion stored at the given index within this list
   * @throws IndexOutOfBoundsException - if index is out of the range 0 .. size()-1 inclusive
   */
  public MultipleChoiceQuestion get(int index) {
    if(index < 0 || index > size) {
      throw new IndexOutOfBoundsException("The index is out of the range from 0 to size");
    }
    //special case index == 0
    if(index == 0) {
      return head.getData();
    }
    //special case index == size-1
    if(index == size-1){
      return tail.getData();
    }
    //general case
    //temporary new node
    LinkedNode<MultipleChoiceQuestion> temp = head;
    for(int i = 0;i < index; i++) {
      if (temp.getNext() != null) {
        temp = temp.getNext();
      }
    }
    return temp.getData();
  }
  /**
   * Gets the MultipleChoiceQuestion at the head of this list
   * @return the MultipleChoiceQuestion at the head of this list
   * @throws NoSuchElementException - with a descriptive error message if this list is empty
   */
  public MultipleChoiceQuestion getFirst() {
    if(size == 0) {
      throw new NoSuchElementException("This list is empty");
    }
    return head.getData();
  }
  /**
   * Gets the MultipleChoiceQuestion at the tail of this list
   * @return the MultipleChoiceQuestion at the tail of this list
   * @throws NoSuchElementException - with a descriptive error message if this list is empty
   */
  public MultipleChoiceQuestion getLast() {
    if(size == 0) {
      throw new NoSuchElementException("This list is empty");
    }
    return tail.getData();
  }
  /**
   * Removes and returns the MultipleChoiceQuestion at the given index
   * @param index of the MultipleChoiceQuestion to remove
   * @return the removed MultipleChoiceQuestion
   * @throws IndexOutOfBoundsException - with a descriptive error message if index is out of the 
   * range 0 .. size()-1 inclusive
   */
  public MultipleChoiceQuestion remove(int index) {
    if(index < 0 || index > size-1) {
      throw new IndexOutOfBoundsException("The index is out of the range 0 to size-1");
    }
    //if the list is empty
    if(index == 0 && isEmpty()== true) {
      throw new IndexOutOfBoundsException("The index is out of the range, this is an empty list");
    }
    //if there's only 1 LinkedNode and index is 0
    if(size == 1 && index == 0) {
      MultipleChoiceQuestion toRemove = head.getData();
      head = null;
      tail = null;
      size--;
      return toRemove;
    }
    //if there's only 1 linkedNode and the index isnt 0
    if(size == 1 && index != 0) {
      throw new IndexOutOfBoundsException("The index is out of the range, there is only 1 "
          + "LinkedNode");
    }
    LinkedNode<MultipleChoiceQuestion> temp = head; //temporary LinkedNode to traverse the list
    LinkedNode<MultipleChoiceQuestion> prev = head; //LinkedNode previous to index (set to head to 
    //start)
    MultipleChoiceQuestion toRemove = head.getData();//question to be removed
    for(int i = 0;i<index;i++) {
      temp = temp.getNext();
      if(i == index-1) {
        prev=temp;
      }
    }
    toRemove = temp.getData(); //get the data of the LinkedNode to be removed
    prev.setNext(temp.getNext());// LinkedNode previous to index.setNext(the LinkedNode after index)
    //temp.setNext(prev);
    size--;
    return toRemove;
  }
  /**
   * Removes and returns the MultipleChoiceQuestion at the head of this list
   * @return the MultipleChoiceQuestion at the head of this list
   * @throws NoSuchElementException - with a descriptive error message if this list is empty
   */
  public MultipleChoiceQuestion removeFirst() {
    if(size == 0) {
      throw new NoSuchElementException("This list is empty");
    }
    //if there's only 1 LinkedNode
    if(size == 1) {
      MultipleChoiceQuestion toRemove = head.getData();
      head = null;
      tail = null;
      size--;
      return toRemove;
    }
    MultipleChoiceQuestion toRemove = head.getData(); //gets the data of the head to be removed
    //update references new head is index 1 of previous list
    head = head.getNext();
    size--;
    return toRemove;
  }
  /**
   * Removes and returns the MultipleChoiceQuestion at the tail of this list
   * @return the MultipleChoiceQuestion at the tail of this list
   * @throws NoSuchElementException - with a descriptive error message if this list is empty
   */
  public MultipleChoiceQuestion removeLast() {
    if(isEmpty()) {
      throw new NoSuchElementException("This list is empty");
    } //if there's only 1 LinkedNode
    if(size == 1) {
      MultipleChoiceQuestion toRemove = tail.getData();
      head = null;
      tail = null;
      size--;
      return toRemove;
    }
    MultipleChoiceQuestion toRemove = tail.getData(); //gets the data of the tail to be removed
    LinkedNode<MultipleChoiceQuestion> temp = head;
    //loop to get second to last element to make it the new tail
    for(int i = 0;i<size-2;i++) {
      temp =temp.getNext();
    }
    tail = temp;
    tail.setNext(null);
    size--;
    return toRemove;
  }
  /**
   * Returns an iterator to iterate through this list with respect to the listingMode. If the 
   * listingMode is ALL, the returned iterator is initialized to the head of this list. If the 
   * listingMode is CORRECT, the returned iterator is initialized to the node carrying first 
   * correctly answered question If the listingMode is INCORRECT, the returned iterator is 
   * initialized to the node carrying first incorrectly answered question
   * @return an iterator to iterate through this list with respect to the listingMode of this list.
   */
  @Override
  public Iterator<MultipleChoiceQuestion> iterator(){
    if(listingMode == ListingMode.ALL) {
      return new QuizQuestionsIterator(head);
    }
    if(listingMode == ListingMode.CORRECT) {
      return new CorrectQuestionsIterator(head); 
    }
    if(listingMode == ListingMode.INCORRECT) {
      return new IncorrectQuestionsIterator(head); //change head?
    }
    return new QuizQuestionsIterator(head); //default?
  }
  /**
   * Calculates the total points of the correctly answered questions of this ListQuizzer
   * @return the score of this ListQuizzer
   */
  public int calculateScore() {
    int score = 0;
    for(int i =0;i<size;i++) {
      if(get(i).getStudentAnswerIndex() == get(i).getCorrectAnswerIndex()) {
        score += get(i).getPointsPossible();
      }
    }
    return score;
  }
  /**
   * Calculates the total possible points of this ListQuizzer
   * @return the score of this ListQuizzer
   */
  public int calculateTotalPoints() {
    int totPoints = 0;
    for(int i = 0; i < size; i++) {
      totPoints += get(i).getPointsPossible();
    }
    return totPoints;
  }
  /**
   * Returns a deep copy of this list. This method creates a copy of this list as a new ListQuizzer 
   * and adds deep copies of each MultipleChoiceQuestion stored in this list to the deep copy.
   * @return a deep copy of this list
   */
  public ListQuizzer copy() {
    ListQuizzer newList = new ListQuizzer();
    for(int i = 0; i < size; i++) {
      newList.add(i, get(i));
    }
    return newList;
  }
  /**
   * Loads MultipleChoiceQuestions from a file
   * 
   * @author Jeff and Mouna
   * 
   * @param file file to read
   * @return the number of added MultipleChoiceQuestions to this list
   * @throws FileNotFoundException if the file is not found
   */
  public int loadQuestions(File file) throws FileNotFoundException {
    int loadedCount = 0; // count of loaded multiple choice questions
    int answerCount = 0; // count of possible answers per question
    int indexCorrectAnswer = 0; // index of the correct answer
    int points = 0; // possible points for a multiple choice question
    // try to read the file
    Scanner reader = null; // scanner to read the file line by line
    int lineNumber = 0; // number of the last read line

    try {
      reader = new Scanner(file);
      // parse the file lines - while loop to read parts of each multiple choice question
      while (reader.hasNextLine()) { // no more lines to read
        // read title
        String title = reader.nextLine();
        lineNumber++;

        // read question stem
        if (!reader.hasNextLine()) { // no more lines to read
          return loadedCount;
        }
        String question = reader.nextLine();
        lineNumber++;

        // read possible answers count
        if (!reader.hasNextLine()) { // no more lines to read
          return loadedCount;
        }
        String count = reader.nextLine();
        lineNumber++;
        // check the validity of count
        try {
          answerCount = Integer.parseInt(count.trim());
          if (answerCount <= 0 || answerCount > 10) {
            throw new NumberFormatException();
          }
        } catch (NumberFormatException e) { // count invalid - print an error message and return
          System.out
              .println("Syntax error! A positive integer less or equal to 10 is expected at line "
                  + lineNumber + (". Load questions operation interrupted!"));
          return loadedCount;
        }
        // valid count -> create the answerList array
        String[] answerList = new String[answerCount];
        int index = 0;
        while (index < answerCount && reader.hasNextLine()) {
          String answer = reader.nextLine();
          lineNumber++;
          answerList[index] = answer;
          index++;
        }

        // read index of the correct answer
        if (!reader.hasNextLine()) { // no more lines to read
          return loadedCount;
        }
        String line = reader.nextLine();
        lineNumber++;
        try { // check the validity of the index of the correct answer
          indexCorrectAnswer = Integer.parseInt(line.trim());
          if (indexCorrectAnswer < 0 || indexCorrectAnswer >= answerCount) {
            throw new NumberFormatException();
          }
        } catch (NumberFormatException e) { // indexCorrectAnswer invalid - print error and return
          System.out.println("Syntax error! A positive integer less than " + answerCount
              + " is expected at line " + lineNumber + (". Load questions operation interrupted!"));
          return loadedCount;
        }
        // valid index of the correct answer -> read possible points
        // read points
        if (!reader.hasNextLine()) { // no more lines to read
          return loadedCount;
        }
        line = reader.nextLine();

        lineNumber++;
        try { // check the validity of the index of the correct answer
          points = Integer.parseInt(line.trim());

          if (points < 0) {
            throw new NumberFormatException();
          }
        } catch (NumberFormatException e) { // invalid points - print error message and return
          System.out.println("Syntax error! A positive integer for possible points "
              + " is expected at line " + lineNumber + (". Load questions operation interrupted!"));

          return loadedCount;
        }
          // create and add quizQuestion
          MultipleChoiceQuestion quizQuestion =
              new MultipleChoiceQuestion(title, question, answerList, indexCorrectAnswer, points);

          this.addLast(quizQuestion);
          loadedCount += 1;
          System.out.println("Question " + loadedCount + " loaded!");

      }
    } finally {
      if (reader != null)
        reader.close();
    }

    return loadedCount;
  }

  /**
   * Allows a user to take this quiz. The quiz should be taken on a deep copy of this ListQuizzer.
   * This method should not make any changes to the contents of this ListQuizzer.
   * 
   * @author Jeff and Mouna
   * 
   * @return the instance of ListQuizzer taken by the user. It should include the user's responses.
   */
  public ListQuizzer takeQuiz() {

    ListQuizzer copy = this.copy();
    copy.switchMode(ListingMode.ALL);
    Scanner input = new Scanner(System.in);
    for (MultipleChoiceQuestion question : copy) {
      System.out.println(question);
      System.out.print("Enter your answer: ");
      int entry = input.nextInt();
      question.setStudentAnswerIndex(entry - 1);
      if (question.isCorrect()) {
        System.out.println("Correct!");
      } else {
        System.out.println("Incorrect!");
      }
    }
    double correctPoints = copy.calculateScore();
    double totalPoints = copy.calculateTotalPoints();
    System.out.println("Your Score: " + correctPoints);
    System.out.println("Percentage: " + correctPoints / totalPoints);
    input.close();
    return copy;
  }
  /**
   * Returns true if o is a ListQuizzer which has the exact same contents as this ListQuizzer
   * 
   * @author Mouna
   *
   * @param o an object to compare with
   * @return true if o is instanceof ListQuizzer with the exact same contents as this ListQuizzer
   */
  @Override 
  public boolean equals(Object o) {
    if(o instanceof ListQuizzer) {
      ListQuizzer other = (ListQuizzer)o;
      if(this.size()!= other.size())
        return false;
      this.switchMode(ListingMode.ALL);
      other.switchMode(ListingMode.ALL);
      Iterator<MultipleChoiceQuestion> iterator = this.iterator();
      Iterator<MultipleChoiceQuestion> otherIterator = other.iterator();
      while(iterator.hasNext()) {
        if(!iterator.next().equals(otherIterator.next()))
          return false;
      }
      return true;
    }
    return false;
  }
}
