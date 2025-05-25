//////////////// P07 Quizzer//////////////////////////
// Title:    P07 QuizzerTester
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
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/2328 - this post helped me with 
//                  my iterator testers.
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This class checks the correctness of the an implementation of cs300 Fall 2022 p07 Quizzer
 *
 */
public class QuizzerTester {
  /**
   * Runs all the tester methods defined in this QuizzerTester
   * @return true if all tests pass and false if any of the tests fails
   */
  public static boolean runAllTests() {
    try {
      if(testMultipleChoiceQuestion()== true && testLinkedNode() == true && 
          testCorrectQuestionsIterator() == true && testCorrectQuestionsIterator() == true && 
          testInCorrectQuestionsIterator() == true && testQuizQuestionsIterator() == true &&
          testAddLast() == true && testRemoveLast() == true && testRemoveFirst() == true &&
          testRemove() == true && testAdd() == true && testAddFirst() == true) {
        return true;
      }
      return false; //default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * This method test and make use of the MultipleChoiceQuestion constructor, an accessor (getter) 
   * method, overridden method toString() and equal() method defined in the MultipleChoiceQuestion 
   * class.
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testMultipleChoiceQuestion() {
    try {
      String title = "First Question"; //new title
      String question = "Are dragons real?"; //rephrased question
      String[] answers = {"Yes", "No", "Maybe"};//array of possible answers 
      //new multiple choice question
      MultipleChoiceQuestion testMCQ = new MultipleChoiceQuestion("Question 1","Are dragons real??",
          answers, 2, 10);
      //check copy method
      MultipleChoiceQuestion testMC = testMCQ.copy();
      //test the setters 
      testMCQ.setTitle(title);
      testMCQ.setQuestion(question);
      testMCQ.setCorrectAnswerIndex(1);
      testMCQ.setStudentAnswerIndex(0);
      testMCQ.setPointsPossible(5);
      testMC.setTitle(title);
      testMC.setQuestion(question);
      testMC.setCorrectAnswerIndex(1);
      testMC.setStudentAnswerIndex(2);
      testMC.setPointsPossible(5);
      String s = "QUESTION TITLE: " + "\"" + testMCQ.getTitle() + "\"" + "\n" + "Question:\n"
          + question + "\n" + "Available Answers:\n" + testMCQ.getAnswers();
      //System.out.println(testMCQ.toString()); //test/debug line
      //System.out.println(testMC.toString()); //test/debug line
      //test the getters
      if(testMCQ.getTitle().equals(title) && testMCQ.getQuestion().equals(question) && 
          testMCQ.getPointsPossible() == 5 && testMCQ.getCorrectAnswerIndex() == 1) {
        //check the getStudentAnswerIndex() and isCorrect() methods
        if(testMCQ.getStudentAnswerIndex() == 0 && testMCQ.isCorrect() == false) {
          //System.out.println(answers.toString()); //test/debug line
          //check the toString(), getAnswers() and equals() methods
          if(testMCQ.copy().equals(testMC) && testMCQ.toString().equals(s) && 
              testMCQ.getAnswers().equals(testMC.getAnswers())) {
            return true;
          }
        }
      }
      return false;
    }
    catch(IllegalArgumentException e){
      e.printStackTrace();
      return false;//default value
    }
    catch(IndexOutOfBoundsException i) {
      i.printStackTrace();
      return false;//default value
    }
    catch(Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }
  /**
   * This method test and make use of the LinkedNode constructor, an accessor (getter) method, and 
   * a mutator (setter) method defined in the LinkedNode class.
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testLinkedNode() {
    try {
      LinkedNode<Integer> node = new LinkedNode<Integer>(10);
      LinkedNode<Integer> node2 = new LinkedNode<Integer>(5);
      node.setNext(node2);
      LinkedNode<Integer> node3 = new LinkedNode<Integer>(20, node);
      node2.setNext(null);
      //System.out.println(node3.toString()+node.toString()+node2.toString());//test linking the 
      // nodes if all are correct return true
      if(node.getData().equals(10)&& node3.getNext().equals(node) && !node.toString().equals(node2)) 
      {
        return true;
      }
      return false;
    }catch(NullPointerException n) {
      n.printStackTrace();
      return false; //default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * Tester for ListQuizzer.addFirst() method
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddFirst() {
    try {
      //new ListQuizzer, answers, MultipleChoiceQuestion
      ListQuizzer list = new ListQuizzer();
      String[] answers = {"Red", "Yellow", "Orange", "Green"};
      MultipleChoiceQuestion testMCQ = new MultipleChoiceQuestion("Fruit Color","What color is an "
          + "orange?",answers, 2, 10);
      MultipleChoiceQuestion testMCQ2 = new MultipleChoiceQuestion("Color Theory","What is the" 
          + "missing primary color: Yellow, Blue, _____",answers, 0, 10);
      MultipleChoiceQuestion testMCQ3 = new MultipleChoiceQuestion("Color Theory","What is the" 
          + "missing primary color: Red, Blue, _____",answers, 1, 10);
      
      list.addFirst(testMCQ);
      list.addFirst(testMCQ3);
      list.addFirst(testMCQ2);
      
      //test case 2 adding to an empty list
      ListQuizzer list2 = new ListQuizzer();
      String[] answers2 = {"Red", "Yellow", "Orange", "Green"};
      MultipleChoiceQuestion test1 = new MultipleChoiceQuestion("Fruit Color","What color is the "
          + "outside of a watermelon?",answers2, 3, 10);
      list2.addFirst(test1);
      //check that the list's head, tail and size matches what is expected 
      if(list.getFirst().equals(testMCQ2) && list.getLast().equals(testMCQ)) {
        if(list2.getFirst().equals(test1) && list2.getLast().equals(test1)) {
          if(list.size() == 3 && list2.size()== 1) {
            return true;
          }
        }
      }
      return false;//default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
    
  /**
   * Tester for ListQuizzer.addLast() method
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddLast() {
    try {
      //new ListQuizzer, answers, MultipleChoiceQuestion
      ListQuizzer list = new ListQuizzer();
      String[] answers = {"Red", "Yellow", "Orange", "Green"};
      MultipleChoiceQuestion testMC = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "tomato?",answers, 0, 10);
      MultipleChoiceQuestion testMCQ = new MultipleChoiceQuestion("Fruit Color","What color is an "
          + "apple?",answers, 0, 10);
      MultipleChoiceQuestion testMCQ1 = new MultipleChoiceQuestion("Food Color","What color is an "
          + "jalapeño?",answers, 3, 10);
      MultipleChoiceQuestion testMCQ2 = new MultipleChoiceQuestion("Food Color","What color is an "
          + "pineapple?",answers, 0, 10);
      list.addFirst(testMC);
      list.addLast(testMCQ2);
      list.addLast(testMCQ);
      list.addLast(testMCQ1);
      //test case 2 adding a null newQuestion 
      try {
        ListQuizzer list2 = new ListQuizzer();
        String[] answers2 = {"Red", "Yellow", "Orange", "Green"};
        MultipleChoiceQuestion test1 = new MultipleChoiceQuestion("Fruit Color","",answers2, 3, 10);
        list2.addFirst(test1);
      }catch(NullPointerException n){
        //do nothing 
      }catch(Exception e) {
        e.printStackTrace();
        return false;
      }
      //check that the list's head, tail, and size matches what's expected
      if(list.getLast().equals(testMCQ1) && list.getFirst().equals(testMC) && list.size() == 4) {
        return true;
      }
      return false;//default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * Tester for ListQuizzer.add() method
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAdd() {
    try {
      //test case: normal adding to the list
      //new ListQuizzer, answers, MultipleChoiceQuestion
      ListQuizzer list = new ListQuizzer();
      String[] answers = {"Red", "Yellow", "Pink", "Purple"};
      MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Fruit Color","What color is an "
          + "apple?",answers, 0, 10);
      MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "banana?",answers, 0, 10);
      MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "dragon fruit?",answers, 0, 10);
      MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "grape?",answers, 0, 10);
      MultipleChoiceQuestion q5 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "persimmon?",answers, 0, 10);
      list.add(0,q1);
      list.add(1,q2);
      list.add(2,q3);
      list.addLast(q4);
      //test case: add to an index out of bounds index
      try {
        list.add(7, q5);
      }catch(IndexOutOfBoundsException i) {
        //do nothing 
      }catch(Exception e){
        e.printStackTrace();
        return false;
      }
      //debug lines
     /* System.out.println(list.get(0)); 
      System.out.println(list.get(1));
      System.out.println(list.get(2));
      System.out.println(list.get(3));*/
      //System.out.println(list.getLast()); //debug line
      if(list.getFirst().equals(q1) && list.getLast().equals(q4) && list.size() == 4) return true;
      return false;//default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
      
  }
  /**
   * Tester for ListQuizzer.removeLast() method
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRemoveLast() {
    try {
      //test case: remove the last MultipleChoiceQuestion
      //new ListQuizzer, answers, MultipleChoiceQuestion
      ListQuizzer list = new ListQuizzer();
      String[] answers = {"Red", "Yellow", "Pink", "Purple"};
      MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Fruit Color","What color is an "
          + "apple?",answers, 0, 10);
      MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "banana?",answers, 1, 10);
      MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "dragon fruit?",answers, 2, 10);
      MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "grape?",answers, 3, 10);
      list.add(0,q1);
      list.add(1,q2);
      list.add(2,q3);
      list.addLast(q4);
      list.removeLast();
      /*System.out.println(list.get(0)); 
      System.out.println(list.get(1));
      System.out.println(list.get(2));
      System.out.println(list.get(3));
      System.out.println("///////////////////////////////");
      
      System.out.println(list.removeLast());
      

      System.out.println(list.get(0)); 
      System.out.println(list.get(1));
      System.out.println(list.get(2));*/
     // System.out.println(list.get(3));
      
      if(!list.removeLast().equals(q3)) {
        //System.out.println("hi"); debug line
        return false;
      }
      if(!list.get(1).equals(q2)) {
        //System.out.println("banana");debug line
        return false;
      }
      //check that the head, tail and size are what is expected after removing the last 2 MCQ
      if(!list.getFirst().equals(q1) || !list.getLast().equals(q2) || list.size() != 2) {
        return false;
      }
      try {
        list.get(3);
        //System.out.println("hii");debug line
        return false;
      }catch(IndexOutOfBoundsException e) {
      }
      catch(Exception i) {
        return false;
      }
     
      //System.out.println(list.getLast()); //debug line
      return true;//default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
      
  }
  /**
   * Tester for ListQuizzer.removeFirst() method
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRemoveFirst() {
    try {
      //test case: removing the first Muliple Choice Question
      ListQuizzer list = new ListQuizzer();
      String[] answers = {"Red", "Yellow", "Pink", "Purple"};
      MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Fruit Color","What color is an "
          + "apple?",answers, 0, 10);
      MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "banana?",answers, 1, 10);
      MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "dragon fruit?",answers, 2, 10);
      MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "grape?",answers, 3, 10);
      list.add(0, q1);
      list.add(1,q2);
      list.add(2,q3);
      list.addLast(q4);
      list.removeFirst();
      //test case 2: removing from an empty list
      try {
        ListQuizzer list1 = new ListQuizzer();
        list1.remove(0);
      }catch(IndexOutOfBoundsException i) {
        //do nothing
      }catch(Exception e) {
        e.printStackTrace();
        return false;
      }
      //check that the list's head, tail and size match what's expected
      if(list.getFirst().equals(q2) && list.getLast().equals(q4)&& list.size() == 3) {
        return true;
      }
      return false;//default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * Tester for ListQuizzer.remove() method
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRemove() {
    try {
      //normal case: removing from the middle
      ListQuizzer list = new ListQuizzer();
      String[] answers = {"Red", "Yellow", "Pink", "Purple"};
      MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Fruit Color","What color is an "
          + "apple?",answers, 0, 10);
      MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "banana?",answers, 1, 10);
      MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "dragon fruit?",answers, 2, 10);
      MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "grape?",answers, 3, 10);
      list.add(0, q1);
      list.add(1,q2);
      list.add(2,q3);
      list.addLast(q4);
      list.remove(2);
      //test case 2: removing with an index out of bounds index
      try {
        list.remove(5);
      }catch(IndexOutOfBoundsException i) {
        //do nothing
      }catch(Exception e) {
        e.printStackTrace();
        return false;
      }
      if(list.getLast().equals(q4) && list.getFirst().equals(q1) && list.size() == 3) {
        return true;
      }
      return false;//default value
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * This method checks for the correctness of QuizQuestionsIterator class
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testQuizQuestionsIterator() {
    try {
      ListQuizzer list = new ListQuizzer();
      String[] fruitAnswers = {"Green", "Yellow", "Red", "Pink"};
      String[] geoAnswers = {"East Coast", "West Coast", "Wisconsin", "Minnesota"};
      MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Geography","What state is Madison in?"
          , geoAnswers, 2, 10);
      MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Geography","What coast is New York" +
         " in?", geoAnswers, 0, 10);
      MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "dragon fruit?", fruitAnswers, 3, 10);
      MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "banana?", fruitAnswers, 1, 10);
      list.add(0, q1);
      list.add(1,q2);
      list.add(2,q3);
      list.addLast(q4);
      
      // directly call quiz questions iterator
      LinkedNode<MultipleChoiceQuestion> n1 = new LinkedNode<MultipleChoiceQuestion>(q1);
      LinkedNode<MultipleChoiceQuestion> n2 = new LinkedNode<MultipleChoiceQuestion>(q2);
      LinkedNode<MultipleChoiceQuestion> n3 = new LinkedNode<MultipleChoiceQuestion>(q3);
      LinkedNode<MultipleChoiceQuestion> n4 = new LinkedNode<MultipleChoiceQuestion>(q4);
      n1.setNext(n2);
      n2.setNext(n3);
      n3.setNext(n4);
      Iterator<MultipleChoiceQuestion> i = new QuizQuestionsIterator(n1);
      
     
      //create new MCQ to check iterator's next against
      MultipleChoiceQuestion test1 = i.next();
      //if the titles match call hasNext()
      if(test1.equals(q1)) {
        if(!i.hasNext()) return false;
      }
      //because there is another question make a new MCQ to compare
      MultipleChoiceQuestion test2 = i.next();
      //if the titles match and the question is correct then call hasNext
      if(test2.equals(q2)) {
        if(!i.hasNext()) return false;
      }
      //because there is another question make a new MCQ to compare
      MultipleChoiceQuestion test3 = i.next();
      //if the titles match and the question is correct then call hasNext
      if(test3.equals(q3)) {
        if(!i.hasNext()) return false;
      }
      //because there is another question make a new MCQ to compare
      MultipleChoiceQuestion test4 = i.next();
      //if the titles match and the question is correct then call hasNext
      if(test4.equals(q4)) {
        if(i.hasNext()) return false;
      }
     
      try {
        i.next();
      }catch(NoSuchElementException n) {
        //do nothing 
      }catch(Exception ex) {
        ex.printStackTrace();
        return false;
      }
      /*call iterator from for loop*/
      list.switchMode(ListingMode.ALL);
      Iterator<MultipleChoiceQuestion> it = list.iterator();
      for(MultipleChoiceQuestion question: list) {
        if(!it.hasNext()==true && !it.next().equals(question)) {
          return false;
        }
          
      }
      return true;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
    
  }
  /**
   * This method checks for the correctness of CorrectQuestionsIterator class
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testCorrectQuestionsIterator() {
    try {
      ListQuizzer list = new ListQuizzer();
      String[] fruitAnswers = {"Green", "Yellow", "Red", "Pink"};
      String[] geoAnswers = {"East Coast", "West Coast", "Wisconsin", "Minnesota"};
      MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Geography","What state is St. Paul" +
          " in?", geoAnswers, 3, 10);
      MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Geography","What coast is California" 
          + " in?", geoAnswers, 1, 10);
      MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "pineapple?", fruitAnswers, 1, 10);
      MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "watermelon?", fruitAnswers, 0, 10);
      list.add(0, q1);
      list.add(1,q2);
      list.add(2,q3);
      list.addLast(q4);
      q1.setStudentAnswerIndex(0);//incorrect
      q2.setStudentAnswerIndex(1);//correct
      q3.setStudentAnswerIndex(0);//incorrect
      q4.setStudentAnswerIndex(0);//correct
      
      // directly call correct questions iterator
      LinkedNode<MultipleChoiceQuestion> n1 = new LinkedNode<MultipleChoiceQuestion>(q1);
      LinkedNode<MultipleChoiceQuestion> n2 = new LinkedNode<MultipleChoiceQuestion>(q2);
      LinkedNode<MultipleChoiceQuestion> n3 = new LinkedNode<MultipleChoiceQuestion>(q3);
      LinkedNode<MultipleChoiceQuestion> n4 = new LinkedNode<MultipleChoiceQuestion>(q4);
      n1.setNext(n2);
      n2.setNext(n3);
      n3.setNext(n4);
      Iterator<MultipleChoiceQuestion> i = new CorrectQuestionsIterator(n1);
      
     
      //create new MCQ to check iterator's next against
      MultipleChoiceQuestion test1 = i.next();
      //if the titles match and the question is correct then call hasNext
      if(test1.getTitle().equals(q2) && test1.isCorrect()) {
        if(!i.hasNext()) return false;
      }
      //because there is another correct answer then get make a new MCQ to test against
      MultipleChoiceQuestion test2 = i.next();
      //if the titles match and the question is correct then call hasNext
      if(test2.getTitle().equals(q4) && test2.isCorrect()) {
        if(i.hasNext()) return false;
      }
      try {
        i.next();
      }catch(NoSuchElementException n) {
        //do nothing 
      }catch(Exception ex) {
        ex.printStackTrace();
        return false;
      }
      /*call iterator from enhanced for loop*/
      list.switchMode(ListingMode.CORRECT);
      Iterator<MultipleChoiceQuestion> it = list.iterator();
      for(MultipleChoiceQuestion question: list) {
        if(!it.hasNext()==true || !it.next().equals(question)) {
          return false;
        }
      }
      return true;
      //return false;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  /**
   * This method checks for the correctness of IncorrectQuestionsIterator class
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testInCorrectQuestionsIterator() {
    try {
      ListQuizzer list = new ListQuizzer();
      String[] fruitAnswers = {"Green", "Yellow", "Red", "Pink"};
      String[] geoAnswers = {"East Coast", "West Coast", "Wisconsin", "Minnesota"};
      MultipleChoiceQuestion q1 = new MultipleChoiceQuestion("Geography","What state is Madison in?"
          , geoAnswers, 2, 10);
      MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("Geography","What coast is New York" +
         " in?", geoAnswers, 0, 10);
      MultipleChoiceQuestion q3 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "dragon fruit?", fruitAnswers, 3, 10);
      MultipleChoiceQuestion q4 = new MultipleChoiceQuestion("Fruit Color","What color is a "
          + "banana?", fruitAnswers, 1, 10);
      list.add(0, q1);
      list.add(1,q2);
      list.add(2,q3);
      list.addLast(q4);
      q1.setStudentAnswerIndex(2);//correct
      q2.setStudentAnswerIndex(0);//correct
      q3.setStudentAnswerIndex(2);//incorrect
      //q4.setStudentAnswerIndex(3);//unanswered question 
      
      // directly call correct questions iterator
      LinkedNode<MultipleChoiceQuestion> n1 = new LinkedNode<MultipleChoiceQuestion>(q1);
      LinkedNode<MultipleChoiceQuestion> n2 = new LinkedNode<MultipleChoiceQuestion>(q2);
      LinkedNode<MultipleChoiceQuestion> n3 = new LinkedNode<MultipleChoiceQuestion>(q3);
      LinkedNode<MultipleChoiceQuestion> n4 = new LinkedNode<MultipleChoiceQuestion>(q4);
      n1.setNext(n2);
      n2.setNext(n3);
      n3.setNext(n4);
      Iterator<MultipleChoiceQuestion> i = new CorrectQuestionsIterator(n1);
     
      //create new MCQ to check iterator's next against
      MultipleChoiceQuestion test1 = i.next();
      //if the titles match and the question is incorrect then call hasNext
      if(test1.getTitle().equals(q3) && !test1.isCorrect()) {
        if(!i.hasNext()) return false;
      }
      //because there is another incorrect answer then get make a new MCQ to test against
      MultipleChoiceQuestion test2 = i.next();
      //if the titles match and the question is correct then call hasNext
      if(test2.getTitle().equals(q4) && !test2.isCorrect()) {
        if(i.hasNext()) return false;
      }
      try {
        i.next();
      }catch(NoSuchElementException n) {
        //do nothing 
      }catch(Exception ex) {
        ex.printStackTrace();
        return false;
      }
      /*call iterator from for loop*/
      //switch mode to directly call incorrect iterator
      list.switchMode(ListingMode.INCORRECT);
      Iterator<MultipleChoiceQuestion> it = list.iterator();
      for(MultipleChoiceQuestion question: list) {
        if(it.hasNext()!=true || !it.next().equals(question)) {
          return false;
        }
      }
      return true;
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  /**
   * Main method 
   * @param args list of input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("runAllTests(): " + runAllTests());
    System.out.println("testMultipleChoiceQuestion(): "+ testMultipleChoiceQuestion());
    System.out.println("testLinkedNode(): " + testLinkedNode());
    System.out.println("testAddFirst(): " + testAddFirst());
    System.out.println("testAddLast(): " + testAddLast());
    System.out.println("testAdd(): " + testAdd());
    System.out.println("testRemoveLast(): " + testRemoveLast());
    System.out.println("testRemoveFirst(): " + testRemoveFirst());
    System.out.println("testRemove(): " + testRemove());
    System.out.println("testQuizQuestionsIterator(): " + testQuizQuestionsIterator());
    System.out.println("testCorrectQuestionsIterator(): " + testCorrectQuestionsIterator());
    System.out.println("testInCorrectQuestionsIterator(): " + testInCorrectQuestionsIterator());
    
  }

}