//////////////// VendingMachine P01 Assignment //////////////////////////
//
// Title:    Vending Machine Tester
// Course:   CS 300 Fall 2022
//
// Author:   Tanvi Wadhawan
// Email:    twadhawan@wisc.edu 
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Anna Wang
// Partner Email:   awang382@wisc.edu
// Partner Lecturer's Name: Hobbes
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _x__ Write-up states that pair programming is allowed for this assignment.
//   __x_ We have both read and understand the course Pair Programming Policy.
//   ___x We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Arrays;

// Javadoc style class header comes here
public class VendingMachineTester {
  /**
   * Checks the correctness of getIndexNextItem defined in the VendingMachine class. This method
     returns true if the test verifies a correct functionality and false if any bug is detected
   * @return true or false
   */
  public static boolean testGetIndexNextItem() {
    // Test scenarios normal and edge cases
    // Recall that the VendingMachine.getNextItem method gets the next item to be dispensed given
    // its description without removing it.

    // 1. Next item to be dispensed is NOT found: the expected output is -1
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.getIndexNextItem("candy", items, itemsCount) != -1) {
        System.out.println(
            "testGetIndexNextItem-scenario 1.Problem detected: Your getIndexNextItem did not return"
                + "-1 when no match found.");
        return false;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "testGetIndexNextItem-scenario 1. Problem detected: Your getIndexNextItem did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
    }

    // 2. Next item to be dispensed is at index 0
    {
      String[][] items = new String[][] {{"Water", "1"}, {"Chocolate", "10"}, {"Juice", "20"},
          {"Water", "5"}, {"Candy", "30"}, {"Water", "15"}, {"Chocolate", "10"}, null, null};
      int itemsCount = 7;

      // check the correctness of the output
      if (VendingMachine.getIndexNextItem("Water", items, itemsCount) != 0) {
        System.out.println(
           "testGetIndexNextItem-scenario 2.Problem detected: Your getIndexNextItem did not return "
               + "the expected output when the vending machines contains multiple matches with the "
               + "provided item description and the matching item with the soonest expiration date "
               + "is at index 0.");
        return false;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "1"}, {"Chocolate", "10"}, {"Juice", "20"}, {"Water", "5"},
              {"Candy", "30"}, {"Water", "15"}, {"Chocolate", "10"}, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "testGetIndexNextItem-scenario 2. Problem detected: Your getIndexNextItem did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
    }

    // 3. Next item to be dispensed is at the end of the array
    {
      String[][] items = new String[][] {{"Water", "15"}, {"Chocolate", "20"}, {"Juice", "20"},
          {"Water", "5"}, {"Candy", "30"}, {"Water", "15"}, {"Chocolate", "10"}, null, null};
      int itemsCount = 7;

      // check the correctness of the output
      if (VendingMachine.getIndexNextItem("Chocolate", items, itemsCount) != 6) {
        System.out.println(
         "testGetIndexNextItem-scenario 3. Problem detected: Your getIndexNextItem did not return "
              + "the expected output when the vending machines contains multiple matches with the "
              + "provided item description and the matching item with the soonest expiration date "
              + "is at the end of the array");
        return false;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "20"}, {"Juice", "20"}, {"Water", "5"},
              {"Candy", "30"}, {"Water", "15"}, {"Chocolate", "10"}, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "testGetIndexNextItem-scenario 3. Problem detected: Your getIndexNextItem did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
    }

    // 4. Next item to be dispensed is at the middle of the array
    {
      String[][] items = new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"},
          {"Water", "5"}, {"Candy", "30"}, {"Water", "15"}, {"Chocolate", "10"}, null, null};
      int itemsCount = 7;

      // check the correctness of the output
      if (VendingMachine.getIndexNextItem("Water", items, itemsCount) != 3) {
        System.out.println(
          "testGetIndexNextItem-scenario 4. Problem detected: Your getIndexNextItem did not return "
              + "the expected output when the vending machines contains matches with the provided "
              + "item description with different expiration dates.");
        return false;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, {"Water", "5"},
              {"Candy", "30"}, {"Water", "15"}, {"Chocolate", "10"}, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "testGetIndexNextItem-scenario 4. Problem detected: Your getIndexNextItem did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
    }

    return true; // No bug detected
  }

  /**
    Checks the correctness of containsItem defined in the VendingMachine class. This method
    returns true if the test verifies a correct functionality and false if any bug is detected
   * @return true or false
   */
  public static boolean testContainsItem() {
    
    // Define at least two test scenarios: (1) successful search returning true and (2) unsuccessful
    // search returning false.
    //Scenario 1 successful search returning true
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.containsItem("Water", items, itemsCount)) {
        System.out.println(
            "containsItem-scenario 1. No Problem detected: Your containsItem did return "
                + "true when no match found.");
        return true;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "containsItem-scenario 1. Problem detected: Your containsItem did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
    }
    //scenario 2 unsuccessful search returning false.
    {
        // define the vending machine
        String[][] items =
           new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
        int itemsCount = 3;

        // check the correctness of the output
        if (!VendingMachine.containsItem("Water", items, itemsCount)) {
          System.out.println(
              "containsItem-scenario 2. Problem detected: Your containstItem did not return "
                  + "false when no match found.");
          return false;
        }
        // Check that the method did not make change to the contents of the array items passed as
        // input
        String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
        if (!Arrays.deepEquals(items, originalItems)) {
          System.out.println(
              "containsItem-scenario 2. Problem detected: Your containsItem did make "
                  + "changes to the content of the array passed as input.");
          return false;
        }
    }


    return true; // default return statement to let this incomplete code compiles with no errors.
                  // Feel free to change it.
  }
  
  /**
   Checks the correctness of getItemAtIndex defined in the VendingMachine class. This method
   returns true if the test verifies a correct functionality and false if any bug is detected
 * @return true or false
 */

  public static boolean testGetItemAtIndex() {
    // Define at least two test scenarios: (1) the provided index is out of the range
    // 0..itemsCount-1, (2) the provided index is in bounds [0..itemsCount-1].
    // For each test scenario, ensure that the method returned the exact expected string output
    // without making any changes to the contents of the array.
    
    //Scenario 1 The provided index is out of the range
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      int itemsCount = 3;
      int index=5;

      // check the correctness of the output
      if (VendingMachine.getItemAtIndex(index, items, itemsCount).equals("ERROR INVALID INDEX")) {
        System.out.println(
            "getItemAtIndex-scenario 1. No problem detected: Your getItemAtIndex did return "
                + " ERROR INVALID INDEX when index out of bounds.");
        return true;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "getItemAtIndex-scenario 1. Problem detected: Your getItemAtIndex did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
  }
    //scenario 2 the provided index is in bounds
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      int itemsCount = 3;
      int index=0;

      // check the correctness of the output
      if (VendingMachine.getItemAtIndex(index, items, itemsCount).equals("Water (15)")) {
        System.out.println(
            "getItemAtIndex-scenario 2. No problem detected: Your getItemAtIndex did return "
                + " the description and expirations date when index in bounds.");
        return true;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "getItemAtIndex-scenario 2. Problem detected: Your getItemAtIndex did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
  }
    return false; // default return statement to let this incomplete code compiles with no errors.
  }
  /**
  Checks the correctness of getItemOccurrences defined in the VendingMachine class.
* @return true or false
*/

  public static boolean testGetItemsOccurrences() {
    // Define at least two test scenarios: (1) no match found so that the method returns zero,
    // (2) the items array contains multiple occurrences of the provided item description.

    //Scenario (1) no match found so that the method returns zero
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.getItemOccurrences("Salad", items, itemsCount)==0) {
        System.out.println(
          "getItemOccurrences-scenario 1. No problem detected: Your getItemOccurrences did return "
                + " 0 when no matches.");
        return true;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "getItemOccurrences-scenario 1. Problem detected: Your getItemOccurrences did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
  }
    //Scenario (2) many matches found
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, {"Water", "10"}, 
        null, null};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.getItemOccurrences("Water", items, itemsCount)>=1) {
        System.out.println(
            "getItemOccurrences-scenario 2. No problem detected: Your getItemOccurrences did return"
                + " 2 when multiple occurrences.");
        return true;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}, null, null, null};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "getItemOccurrences-scenario 2. Problem detected: Your getItemOccurrences did make "
                + "changes to the content of the array passed as input.");
        return false;
      }
  }
    // For each test scenario, ensure that the method returned the expected output without making
    // any changes to the contents of the array.
    return false; // default return statement to let this incomplete code compiles with no errors.
  }
  /**
  Checks the correctness of     Item defined in the VendingMachine class.
* @return true or false
*/
  public static boolean testAddItem() {
    //scenario 1 adding an new item to an empty vending machine who's size is zero
    {
      // define the vending machine
      String[][] items =
          new String[][] {};
      int itemsCount = 0;

      // check the correctness of the output
      if (VendingMachine.addItem("Salad", "18", items, itemsCount)==itemsCount+1) {
        System.out.println(
            "addItem-scenario 1. No problem detected: Your addItem did return "
                + " items count of plus 1.");
        return true;
        }
      String[][] originalItems = new String[][] {{"Salad", "18"}};
          if (Arrays.deepEquals(items, originalItems)) {
            System.out.println(
                "addItem-scenario 1. Problem detected: Your addItem did make "
                + "changes to the content of the array passed as input.");
            return true;
        }
      }
    //scenario 2adding a new item to a non-empty non-full vending machine
    {
      // define the vending machine
      String[][] items =
          new String[][] { {"Water", "15"}, {"Chocolate", "10"}, null};
      int itemsCount = 2;

      // check the correctness of the output
      if (VendingMachine.addItem("Salad", "18", items, itemsCount)==3) {
        System.out.println(
            "addItem-scenario 2. No problem detected: Your addItem did return "
                + " items count of 3.");
        return true;
        }
      String[][] originalItems = new String[][] {{"Water", "15"}, {"Chocolate", "10"},
        {"Salad", "18"}};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "addItem-scenario 2. Problem detected: Your addItem did not make "
            + "changes to the content of the array passed as input.");
        return false;
        }
    }
      //scenario 3 adding a new item to a full vending machine where the provided
      // itemsCount equals the length of the provided items array
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.addItem("Salad", "18", items, itemsCount) == itemsCount+1) {
        System.out.println(
            "addItem-scenario 3. Problem detected: Your addItem did return "
                + " items count of plus 1 .");
        return false;
        }
      String[][] originalItems =           
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "addItem-scenario 3. Problem detected: Your addItem did make "
            + "changes to the content of the array passed as input.");
        return false;
      }
    }
    return false; // default return statement to let this incomplete code compiles with no errors.
  }
  /**
Checks the correctness of removeNextItem defined in the VendingMachine class.
* @return true or false
*/
  

  public static boolean testRemoveNextItem() {
    // Define at least four test scenarios: (1) trying to remove a non-existing item from a vending
    // machine (the vending machine can be empty or not), (2) the next item to be removed matching
    // the provided description is at index 0 of the array, (3) the next item to be removed is at
    // index itemsCount of the array (at the end of the array), and (4) the next item to be removed
    // is at a middle index of the provided items array.

    // For each tester scenario, check for the expected returned size of the vending machine and
    // the expected content of the items array after the method call returns.

    //scenario 1 trying to remove a non-existing item from a vending
    // machine (the vending machine can be empty or not)
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.removeNextItem("Salad", items, itemsCount) == itemsCount-1) {
        System.out.println(
            "removeNextItem-scenario 3. No problem detected: Your removeNextItem did return "
                + " items count the same because item wasn't existing  .");
        return true;
        }
      String[][] originalItems =           
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"},{"Salad", "18"}};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "removeNextItem-scenario 3. No problem detected: Your removeNextItem did make "
            + "changes to the content of the array passed as input.");
        return true;
      }
    }
    //scenario 2 the next item to be removed matching
    // the provided description is at index 0 of the array
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.removeNextItem("Water", items, itemsCount) == itemsCount-1) {
        System.out.println(
            "removeNextItem-scenario 2. No problem detected: Your removeNextItem did return "
                + " items count as minus 1.");
        return true;
        }
      String[][] originalItems =           
          new String[][] { {"Chocolate", "10"}, {"Juice", "20"}};
      if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "removeNextItem-scenario 2. No problem detected: Your removeNextItem did make "
            + "changes to the content of the array passed as input.");
        return true;
      }
    }
    //3) the next item to be removed is at
    // index itemsCount of the array (at the end of the array
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.removeNextItem("Juice", items, itemsCount) == itemsCount-1) {
        System.out.println(
            "removeNextItem-scenario 2. No problem detected: Your removeNextItem did return "
                + " items count as minus 1.");
        return true;
        }
      String[][] originalItems =           
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}};
      if (Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "removeNextItem-scenario 2. No problem detected: Your removeNextItem did make "
            + "changes to the content of the array passed as input.");
        return true;
      }
    }
    //(4) the next item to be removed
    // is at a middle index of the provided items array.
    {
      // define the vending machine
      String[][] items =
          new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"}};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.removeNextItem("Chocolate", items, itemsCount) == itemsCount-1) {
        System.out.println(
            "removeNextItem-scenario 2. No problem detected: Your removeNextItem did return "
                + " items count as minus 1.");
        return true;
        }
      String[][] originalItems =           
          new String[][] {{"Water", "15"}, {"Juice", "20"}, null};
      if (Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "removeNextItem-scenario 2. No problem detected: Your removeNextItem did make "
            + "changes to the content of the array passed as input.");
        return true;
      }
    }
    return false; // default return statement to let this incomplete code compiles with no errors.
  }
  /**
  Checks the correctness of getItemsSummary defined in the VendingMachine class.
* @return true or false
*/
  public static boolean testGetItemsSummary() {
    // Define at least three scenarios: (1) the vending machine is empty, (2) the vending machine
    // contains non duplicate items (no multiple items with the same description), (3) the vending
    // machine contains multiple items with the same description at various index locations.
    //scenario 1 the vending machine is empty
    {
      // define the vending machine
      String[][] items =
          new String[][] {null, null, null};
      int itemsCount = 3;

      // check the correctness of the output
      if (VendingMachine.getItemsSummary(items, itemsCount).equals("")) {
        System.out.println(
            "testGetItemsSummary-scenario 1. Problem detected: Your getItemsSummary did not return "
                + " 0 when vending machine is empty.");
        return false;
      }
      // Check that the method did not make change to the contents of the array items passed as
      // input
      String[][] originalItems =
          new String[][] {null, null, null};
       if (!Arrays.deepEquals(items, originalItems)) {
        System.out.println(
            "testGetItemsSummary-scenario 1. Problem detected: Your getItemsSummary did make "
                + "changes to the content of the array passed as input.");
        return false;
       }
      }
      
      //scenario 2 the vending machine
      // contains non duplicate items (no multiple items with the same description)
      {
        // define the vending machine
        String[][] items =
            new String[][] {{"Water", "15"}};
        int itemsCount = 2;

        // check the correctness of the output
        if (!VendingMachine.getItemsSummary(items, itemsCount).equals("Water (15)") ) {
          System.out.println(
           "testGetItemsSummary-scenario 2. Problem detected: Your getItemsSummary did not return "
                + "Water(15).");
          return false;
        }
        // Check that the method did not make change to the contents of the array items passed as
        // input
        String[][] originalItems =
            new String[][] {{"Water", "15"}};
         if (!Arrays.deepEquals(items, originalItems)) {
          System.out.println(
              "testGetItemsSummary-scenario 2. Problem detected: Your getItemsSummary did make "
                  + "changes to the content of the array passed as input.");
          return false;
         }
        }
       // (3) the vending machine contains multiple items with the same description at various index locations
        {
          // define the vending machine
          String[][] items =
             new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"},{"Water", "15"}};
          int itemsCount = 4;

          // check the correctness of the output
          if (!VendingMachine.getItemsSummary(items, itemsCount).equals("Water (15)")) {
            System.out.println(
             "testGetItemsSummary-scenario 3. Problem detected:Your getItemsSummary did not return "
                    + " water(15).");
            return false;
          }
          // Check that the method did not make change to the contents of the array items passed as
          // input
          String[][] originalItems =
             new String[][] {{"Water", "15"}, {"Chocolate", "10"}, {"Juice", "20"},{"Water", "15"}};
          if (!Arrays.deepEquals(items, originalItems)) {
            System.out.println(
                "testGetItemsSummary-scenario 3. Problem detected: Your getItemsSummary did make "
                    + "changes to the content of the array passed as input.");
            return false;
          }
       }    
      return true;
      }// default return statement to let this incomplete code compiles with no errors.
    

  /**
  This method returns false if any of the tester methods defined in this class fails, and true 
  if no bug detected.
   * * @return true or false
*/
  public static boolean runAllTests() {
    testGetIndexNextItem();
    testContainsItem();
    testGetItemAtIndex();
    testGetItemsOccurrences();
    testAddItem();
    testRemoveNextItem();
    testGetItemsSummary();
    return false; // default return statement to let this incomplete code compiles with no errors.
  }

  // main method to call the tester methods defined in this class
  public static void main(String[] args) {
//    System.out.println("testGetIndexNextItem(): " + testGetIndexNextItem());
//    System.out.println("runAllTests(): " + runAllTests());
      System.out.println(testRemoveNextItem());
  }

}
