import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

//////////////// P04 Exceptional Vending Machine  //////////////////////////
//
// Title:    Exceptional Vending Machine Testers
// Course:   CS 300 Fall 2022
//
// Author:   Anna Wang
// Email:   awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Tanvi Wadhawan
// Partner Email:   twadhawan@wisc.edu
// Partner Lecturer's Name: Mouna Kacem
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X__ Write-up states that pair programming is allowed for this assignment.
//   _X__ We have both read and understand the course Pair Programming Policy.
//   _X__ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         (identify each by name and describe how they helped)
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/1104 - this post helped my
//                  confusion on how to test the methods that throws an exception
//
///////////////////////////////////////////////////////////////////////////////
// TODO import relevant exceptions here

/**
 * This class implements testers to check the correctness of the methods implemented in p04
 * Exceptional Vending Machine
 *
 */
public class ExceptionalVendingMachineTester {
  // TODO complete the implementation of all the public static tester methods defined below

  // It is recommended but NOT required to add additional tester methods to check the correctness
  // of loadItems and saveVendingMachineSumary defined in the ExceptionalVendingMachine class.

  /**
   * Checks the correctness of the constructor of the class Item when passed invalid inputs
   * 
   * @return true if the test verifies a correct functionality and false if any bug is detected
   */
  public static boolean testItemConstructorNotValidInput() {
    try {
      //create new item with invalid inputs
      Item chocolate = new Item(null, 1);
    
    }catch (IllegalArgumentException e){
      System.out.println("error1" + e.getMessage()); // Prints out the descriptive message you provided.
      return true;
    }   
    catch(Exception e) {
      return false;
    }
    return false;
  }

  /**
   * Checks the correctness of the constructor of the class Item, Item.getDescription(),
   * Item.getExpirationDate(), Item.setDescription(), and Item.toString() when passed valid inputs
   * 
   * @return true if the test verifies a correct functionality and false if any bug is detected
   */
  public static boolean testItemConstructorGettersSetters() {
    //create new item and set description
    Item apple = new Item("apple", 3);
    apple.setDescription("a juicy red apple");
    if(apple.getDescription().equals("a juicy red apple")) {
      //System.out.println("an apple"); //debug statement
      if(apple.getExpirationDate() == 3)
        //System.out.println("the expiration date is 3"); //debug statement
        if(apple.toString().equals("a juicy red apple: 3")) {
          return true;
        }
    }
    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Checks the correctness of the Item.equals() method. You should consider at least the following
   * four scenarios. (1) Create an item with valid description and expiration date, comparing it to
   * itself should return true. (2) Two items having the same description but different expiration
   * dates should be equal. (3) Passing a null reference to the Item.equals() method should return
   * false. (4) An item MUST NOT be equal to an object NOT instance of the class Item, for instance
   * a string object.
   * 
   * @return true if the test verifies a correct functionality and false if any bug is detected
   */
  public static boolean testItemEquals() {
    //create new items
    Item water = new Item("a bottle of water", 15);
    Item water2 = new Item("a bottle of water", 0);
    Item apple = new Item("an apple", 0);
    String waterBottle = "a bottle of water";
    //check itemEquals
    if(water.equals(water)) {
      if(water.equals(water2)) {
        if(water.equals(null) == false) {
          if(!water.equals(waterBottle)) {
            return true;
          }
        }
      }
    }
    return false; // default return statement added to resolve compiler errors
  }
  /**
   * Checks the correctness of the constructor of the ExceptionalVendingMachine when passed invalid
   * input
   * 
   * @return true if the test verifies a correct functionality and false if any bug is detected
   */
  public static boolean testExceptionalVendingMachineConstructor() {
    try {
      ExceptionalVendingMachine vendy = new ExceptionalVendingMachine(-5);
      
      } 
    catch (IllegalArgumentException e) {
        System.out.println("error2" + e.getMessage()); // Prints out the descriptive message you provided.
        return true;
      }
    
    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Checks the correctness of the following methods defined in the ExceptionalVendingMachine class
   * when an exception is expected to be thrown:
   * 
   * addItem(), containsItem(), getIndexNextItem(), getItemAtIndex(), getItemOccurrences(),
   * getItemOccurrencesByExpirationDate(), removeNextItem().
   * 
   * @return true if the test verifies a correct functionality and false if any bug is detected
   */
  public static boolean testExceptionalVendingMachineAddContainsRemoveGetters() {
    try {
      //create new vending machine
      int count=0;
      ExceptionalVendingMachine vendy = new ExceptionalVendingMachine(4);
      //add items to vending machine 
      Item one= new Item("a chocolate drink", 3);
      vendy.addItem("a chocolate drink", 3);
      vendy.addItem("a chocolate bar", 10);
      vendy.addItem("water", 15);
      vendy.addItem("water", 15);
      
      if(vendy.isFull()) {
        count++;
      }

      //vendy.removeNextItem("a chocolate bar");
      if(vendy.getItemAtIndex(0).equals(one.toString())) {
       //System.out.println("Chocolate");  //debug statement
        count++;
      }

      if(vendy.getItemOccurrences("water") == 2) {
        //System.out.println("Chocolate");
        count++;
       }

       if(vendy.getItemOccurrencesByExpirationDate("a chocolate bar", 10) == 1) {

         count++;
         //System.out.println("Chocolate12");  //debug statement
       }

       if(vendy.containsItem("an apple") == false) {
         count++;
       }

       if(vendy.getIndexNextItem("water") == 2) {
         count++;
       }
//       System.out.println(count);
       if(count==6) {
         return true;
       }
    }
    catch(NoSuchElementException e) {
      System.out.println(1);

      System.out.println("error3" + e.getMessage()); // Prints out the descriptive message you provided.
      return false;
    }
    catch(Exception e){
      System.out.println("error4" + e.getMessage()); // Prints out the descriptive message you provided.
    }
    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Checks the correctness of isEmpty(), size(), and isFull() methods defined in the
   * ExceptionalVendingMachine class
   * 
   * @return true if the test verifies a correct functionality and false if any bug is detected
   */
  public static boolean testEmptySizeFullExceptionalVendingMachine() {
    ExceptionalVendingMachine vendy = new ExceptionalVendingMachine(3);
    if(vendy.size() == 0 && vendy.isEmpty() && !vendy.isFull()) {
      return true;
    }
    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Checks the correctness of loadOneItem method with respect to its specification. Consider at
   * least the four following scenarios. (1) Successful scenario for loading one item with a valid
   * string representation to a non-full vending machine. (2) Unsuccessful scenario for passing null
   * or a blank string (for instance one space or empty string) to the loadOneItem() method call, an
   * IllegalArgumentEXception is expected to be thrown. (3) Unsuccessful scenario for passing a
   * badly formatted string to the loadOneItem method. A DataFormatException is expected to be
   * thrown. (4) Unsuccessful scenario for trying to load an item with a valid representation to a
   * full vending machine. An IllegalStateException is expected to be thrown.
   * 
   * @return true if the test verifies a correct functionality and false if any bug is detected
   */
  public static boolean testLoadOneItem() {
    try {
      ExceptionalVendingMachine vendy = new ExceptionalVendingMachine(3);
     
      vendy.addItem("a chocolate bar", 10);
      vendy.addItem("water", 15);
      vendy.loadOneItem("soda: 10");//successful scenario
      vendy.loadOneItem(" ");//unsuccessful scenario
      vendy.loadOneItem("     latte:10 :  30"); //DataFormatException expected to be thrown
      vendy.loadOneItem("water, 0");//unsuccessful add to a full vending machine
    }
    catch(DataFormatException e) {
      System.out.println("error5" + e.getMessage()); // Prints out the descriptive message you provided.
      return true;
    }
    catch (IllegalArgumentException f){
      System.out.println("error6" + f.getMessage()); // Prints out the descriptive message you provided.
      return true;
    }
    catch(IllegalStateException g) {
      System.out.println("error7" + g.getMessage()); // Prints out the descriptive message you provided.
      return true;
    }
    catch(Exception e) {
      return false;
    }
    return false; // default return statement added to resolve compiler errors
  }

  /**
   * Invokes all the public tester methods implemented in this class
   * 
   * @return true if all testers pass with no errors, and false if any of the tester fails.
   */
  public static boolean runAllTests() {
    if(testItemConstructorGettersSetters() 
    && testItemConstructorNotValidInput() &&testItemEquals()        
    &&testExceptionalVendingMachineConstructor()
   && testExceptionalVendingMachineAddContainsRemoveGetters()
   && testEmptySizeFullExceptionalVendingMachine()
   && testLoadOneItem()) {
      return true;
    }
    return false;
    
//    System.out.println(testItemConstructorGettersSetters());
//    System.out.println( testItemConstructorNotValidInput());
//    System.out.println(testItemEquals());
//    System.out.println(testExceptionalVendingMachineConstructor());
//    System.out.println( testExceptionalVendingMachineAddContainsRemoveGetters());
//    System.out.println( testEmptySizeFullExceptionalVendingMachine());
//    System.out.println(testLoadOneItem());
  }
  /**
  /**
   * Main method for the tester class
   * 
   * @param args list of input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("runAllTests(): " + runAllTests());
//    System.out.println("testItemConstructorGettersSetters(): " + testItemConstructorGettersSetters());
//    System.out.println("testItemConstructorNotValidInput(): " + testItemConstructorNotValidInput());
//    System.out.println("testItemEquals(): " + testItemEquals());
//    System.out.println("testExceptionalVendingMachineConstructor(): "+ testExceptionalVendingMachineConstructor());
//    System.out.println("testExceptionalVendingMachineAddContainsRemoveGetters(): " + testExceptionalVendingMachineAddContainsRemoveGetters());
//    System.out.println("testEmptySizeFullExceptionalVendingMachine(): " + testEmptySizeFullExceptionalVendingMachine());
//    System.out.println("testLoadOneItem(): " + testLoadOneItem());

  }

}
