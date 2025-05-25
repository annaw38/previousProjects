//////////////// P09 Chugidex//////////////////////////
// Title:    P09 Chugidex Tester
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
// Persons:     None
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/3045 - this post helped me with the
//                  names of some of the chugimon
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class checks the correctness of the implementation of the methods defined in the Chugimon
 * and ChugiTree classes.
 * 
 * @author Anna
 *
 */

public class ChugidexTester {
  /**
   * Checks the corectness of the Chugimon constructor
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testChugimonConstructor() {
    try {
      //test case 1: the same firstID and secondID
      try {
        Chugimon olive = new Chugimon(50,50);
        
      }catch(IllegalArgumentException e) {
        //do nothing
      }
      //test case 2: out of range firstID, in range secondID
      try {
        Chugimon dig = new Chugimon(-1,50);
        
      }catch(IllegalArgumentException e) {
        //do nothing
      }
      //test case 3: in range firstID, out of range secondID
      try {
        Chugimon bulb = new Chugimon(20,156);
        
      }catch(IllegalArgumentException e) {
        //do nothing
      }
      //test case 4: valid Chugimon
      Chugimon ratew = new Chugimon(20,151);
      //check that all the getters match what's expected
      if(ratew.getFirstID() == 20 && ratew.getSecondID() == 151 && 
          ratew.getPrimaryType() == ChugiType.NORMAL && ratew.getName().equals("Ratew") && 
          ratew.getWeight() == 11.25 && ratew.getHeight() == 0.55 && ratew.getSecondaryType() ==
          ChugiType.PSYCHIC) {
        return true;
      }
      return false;
    }catch(Exception e) {
      return false;
    }
  }
  /**
   * Checks the correctness of the implementation of both compareTo() and equals() methods defined
   * in the Chugimon class.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testChugimonCompareToEquals() {
    try {
      //test case 1: two Chugimons with the same firstID and secondID and name --> equal and 0
      Chugimon same = new Chugimon(50,100); //name: Digorb
      Chugimon same1 = new Chugimon(50,100); //name: Digorb
      
      //test case 2: first chugimon's name comes before second chugimon --> -/less than
      Chugimon first = new Chugimon(1,63); //name: Bulbra
      Chugimon second = new Chugimon(58,128); //name: Growros
      
      //test case 3: first chugimon's name comes after second chugimon --> +/more than
      Chugimon firstAlpha = new Chugimon(9,35); //name: Blastfairy
      Chugimon secondAlpha = new Chugimon(9,15); //name: Blastdrill
      
      //test case 4: same name, first chugimon's firstID greater than second Chugimon's firstID --> +
      Chugimon greaterFir = new Chugimon(62,88); //name: Polimer
      Chugimon lessFir = new Chugimon(61,88); //name: Polimer
      
      //test case 5: same name, first chugimon's firstID lesser than second Chugimon's firstID --> -
      Chugimon greatFir = new Chugimon(67,94); //name: Magar
      Chugimon lesserFir = new Chugimon(68,94); //name: Magar
      
      //test case 6: same name, same firstID, first chugimon's secondID greater than second chugimon's
      //secondID --> +
      Chugimon greaterSec = new Chugimon(62,22); //name: Polirow
      Chugimon lessSec = new Chugimon(62,21); //name: Polirow
      
      //test case 7: same name, same firstID, first chugimon's secondID lesser than second chugimon's
      //secondID --> -
      Chugimon greatSec = new Chugimon(68,21); //name: Marow
      Chugimon lesserSec = new Chugimon(68,22); //name: Marow
      
      //extra test cases
      //test case 8: same secondID, second chugimon's name alphabetically before first and second's 
      //secondID greater than first's secondID -->+
      Chugimon grass = new Chugimon(25,140); //name: Pikato
      Chugimon grasses = new Chugimon(30, 140); //name: Nidoto
      
      //test case 8: same secondID, first chugimon's name alphabetically before second and first's 
      //secondID greater than second's secondID --> -
      Chugimon storm= new Chugimon(30,140); //name: Nidoto
      Chugimon storms = new Chugimon(25, 140); //name: Pikato
      
      //check that the compareTos are what's expected
      if(same.equals(same1)&& same.compareTo(same1) == 0) {
        if(first.compareTo(second)<0 && firstAlpha.compareTo(secondAlpha)>0) {
          if(greaterFir.compareTo(lessFir)>0 && greatFir.compareTo(lesserFir)<0) {
            if(greaterSec.compareTo(lessSec)>0 && greatSec.compareTo(lesserSec)<0) {
              if(grass.compareTo(grasses)>0 && storm.compareTo(storms)<0) {
                return true;
              }
            }
          }
        }
      }
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
  }
  /**
   * Checks the correctness of the implementation of Chugimon.toString() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testChugimonToString() {
    try {
      //test case 1: normal Chugimon
      Chugimon tree = new Chugimon(145,25);
      String treeExp = "Zapchu#145.25";
      //test case 2: same ID chugimon
      try {
        Chugimon hal = new Chugimon(145,145);
      }catch(IllegalArgumentException e) {
        //do nothing
      }
      //test case 3: out of bounds IDs
      try {
        Chugimon squirt = new Chugimon(-15,194);
      }catch(IllegalArgumentException e) {
        //do nothing
      }
      if(tree.toString().equals(treeExp)) {
        return true;
      }
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
  }
  /**
   * Checks the correctness of the implementation of ChugiTree.isValidBSTHelper() method. This
   * tester should consider at least three scenarios. (1) An empty tree whose root is null should be
   * a valid BST. (2) Consider a valid BST whose height is at least 3. Create the tree using the
   * constructors of the BSTNode and its setters methods, (3) Consider a NON-valid BST where the
   * search order property is violated at at least one internal node.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testIsValidBSTHelper() {
    try {
      //test case 1: empty tree with null root
      //debug lines
      /*if(ChugiTree.isValidBSTHelper(null) == true) {
        System.out.println("hiahfd");
      }*/
      //test case 2: valid BST of height at least 3
      Chugimon first = new Chugimon(39,73);
      Chugimon second = new Chugimon(5,35);
      Chugimon third = new Chugimon(82,79);
      Chugimon fourth = new Chugimon(1,31);
      Chugimon fifth = new Chugimon(6,47);
      Chugimon sixth = new Chugimon(67,74);
      Chugimon seventh = new Chugimon(134,94);
      //create the nodes
      BSTNode<Chugimon> root = new BSTNode<Chugimon>(first);
      BSTNode<Chugimon> node2 = new BSTNode<Chugimon>(second);
      BSTNode<Chugimon> node3 = new BSTNode<Chugimon>(third);
      BSTNode<Chugimon> node4 = new BSTNode<Chugimon>(fourth);
      BSTNode<Chugimon> node5 = new BSTNode<Chugimon>(fifth);
      BSTNode<Chugimon> node6 = new BSTNode<Chugimon>(sixth);
      BSTNode<Chugimon> node7 = new BSTNode<Chugimon>(seventh);
      
      root.setLeft(node2);
      root.setRight(node3);
      node2.setLeft(node4);
      node2.setRight(node5);
      node3.setLeft(node6);
      node3.setRight(node7);
      //debug lines
      /*if(ChugiTree.isValidBSTHelper(root) == true) {
        System.out.println("udohfowoef");
        
      }*/
      
      //test case 3:Non-valid BST 
      Chugimon one = new Chugimon(68,100);
      Chugimon two = new Chugimon(65,122); 
      Chugimon three = new Chugimon(13,88);
      Chugimon four = new Chugimon(1,31);
      Chugimon five = new Chugimon(23,55);
      //create the nodes
      BSTNode<Chugimon> root3 = new BSTNode<Chugimon>(one);
      BSTNode<Chugimon> node_2 = new BSTNode<Chugimon>(two);
      BSTNode<Chugimon> node_3 = new BSTNode<Chugimon>(three);
      BSTNode<Chugimon> node_4 = new BSTNode<Chugimon>(four);
      BSTNode<Chugimon> node_5 = new BSTNode<Chugimon>(five);
      
      root3.setLeft(node_3);
      root3.setRight(node_2);
      node_3.setLeft(node_4);
      node_3.setRight(node_5);
      /*debug lines
      if(ChugiTree.isValidBSTHelper(root3) != true) {
        System.out.println("hwguiriuehg");
       
      }
      */
      //test case 4: non-valid tree with different names 
      Chugimon c = new Chugimon(37,76);
      Chugimon b = new Chugimon(12,21);
      Chugimon e = new Chugimon(105,90);
      Chugimon a = new Chugimon(9,8);
      Chugimon d = new Chugimon(51,28);
      
      BSTNode<Chugimon> root4 = new BSTNode<Chugimon>(c);
      BSTNode<Chugimon> node_b = new BSTNode<Chugimon>(b);
      BSTNode<Chugimon> node_e = new BSTNode<Chugimon>(e);
      BSTNode<Chugimon> node_a = new BSTNode<Chugimon>(a);
      BSTNode<Chugimon> node_d = new BSTNode<Chugimon>(d);
      
      root4.setLeft(node_b);
      root4.setRight(node_e);
      node_b.setLeft(node_a);
      node_b.setRight(node_d);
      //debug lines
      /*if( ChugiTree.isValidBSTHelper(root4) != true) {
        System.out.println("ausdogfuoe");
      }*/
      
      if(ChugiTree.isValidBSTHelper(null) == true && ChugiTree.isValidBSTHelper(root) == true){
        if(ChugiTree.isValidBSTHelper(root3) != true && ChugiTree.isValidBSTHelper(root4) != true) {
          return true;
        }
      }
      return false; // Default return statement added to resolve compiler errors}
    }catch(Exception e) {
      return false;
    }
  }

  /**
   * Checks the correctness of the implementation of both add() and toString() methods implemented
   * in the ChugiTree class. This unit test considers at least the following scenarios. (1) Create a
   * new empty ChugiTree, and check that its size is 0, it is empty, and that its string
   * representation is an empty string "". (2) try adding one Chugimon and then check that the add()
   * method call returns true, the tree is not empty, its size is 1, and the toString() called on
   * the tree returns the expected output. (3) Try adding another Chugimon which is less than the
   * Chugimon at the root, (4) Try adding a third Chugimon which is greater than the one at the
   * root, (5) Try adding at least two further Chugimons such that one must be added at the left
   * subtree, and the other at the right subtree. For all the above scenarios, and more, double
   * check each time that size() method returns the expected value, the add method call returns
   * true, that the ChugiTree is a valid BST, and that the toString() method returns the expected
   * string representation of the contents of the binary search tree in an increasing order from the
   * smallest to the greatest Chugimon. (6) Try adding a Chugimon already stored in the tree. Make
   * sure that the add() method call returned false, and that the size of the tree did not change.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddToStringSize() {
    try {
      boolean first3 = false; //result of the first 3 test cases
      boolean second3 = false; //result of the second 3 test cases
      
      //test case 1: empty tree with null root
      ChugiTree tree1 = new ChugiTree();
      String expected1 = "";
      //test case 2: adding a chugimon to an empty tree
      ChugiTree tree2 = new ChugiTree();
      String expected2 = "Clefby#35.98";
      Chugimon one = new Chugimon(35,98);
      //test case 3: adding a chugimon to the left 
      ChugiTree tree3 = new ChugiTree();
      String expected3 = "Bulbqueen#1.31,Clefby#35.98";
      Chugimon two = new Chugimon(1,31);
      
      //checking that all the results match the expected values
      //test case 1:empty tree with null root
      if(tree1.size() == 0 && tree1.isEmpty() == true && tree1.toString().equals(expected1) && 
          tree1.isValidBST() == true) {
        //test case 2:adding a chugimon to an empty tree
        if(tree2.add(one) == true && tree2.size() == 1 && tree2.isEmpty() == false && 
            tree2.toString().equals(expected2) && tree2.isValidBST() == true) {
          //test case 3:adding a chugimon to the left 
          if(tree3.add(one) == true && tree3.add(two) == true && tree3.size() == 2 && 
              tree3.isEmpty() == false && tree3.toString().equals(expected3) && 
              tree3.isValidBST() == true) {
            first3 = true; //if all of the tests returned true
          }
        }
      }
      
      //test case 4:third Chugimon greater than the root
      ChugiTree tree4 = new ChugiTree();
      String expected4 = "Bulbqueen#1.31,Clefby#35.98,Golkhan#42.115";
      Chugimon three = new Chugimon(42,115);
      //test adding individually 
      /*tree4.add(one);
      tree4.add(two);
      tree4.add(three);*/
      //test case 5:3 more chugimon - 2(one left and other right) to left internal node and right
      // subtree to right internal node
      ChugiTree tree5 = new ChugiTree();
      String expected5 = "Bulbqueen#1.31,Butterter#12.93,Butterzee#12.96,Clefby#35.98,"
          + "Golkhan#42.115,Pados#46.145";
      Chugimon four  = new Chugimon(12,93);
      Chugimon five  = new Chugimon(12,96);
      Chugimon six  = new Chugimon(46,145);
      //test case 6: Already added chugimon
      Chugimon seven  = new Chugimon(12,93);
      //checking that all the results match what's expected
      //test case 4:third Chugimon greater than the root
      if(tree4.add(one) == true && tree4.add(two) == true && tree4.add(three) == true && 
      tree4.size() == 3 && tree4.toString().equals(expected4) && tree4.isValidBST() == true) {
        //test case 5:3 more chugimon - 2(one left and other right) to left internal node and right
        // subtree to right internal node
        if(tree5.add(one) == true && tree5.add(four) == true && tree5.add(three) == true && 
            tree5.add(two) == true && tree5.add(five) == true && tree5.add(six) == true && 
            tree5.size() == 6 && tree5.toString().equals(expected5) && tree5.isValidBST() == true) {
          //test case 6: add an already added chugimon
          if(tree5.add(seven) == false && tree5.size() == 6 && tree5.toString().equals(expected5) 
              && tree5.isValidBST() == true) {
            second3 = true; //if all of the tests returned true
          }
        }
      }
      return first3 && second3; 
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * This method checks mainly for the correctness of the ChugiTree.lookup() method. It must
   * consider at least the following test scenarios. (1) Create a new ChugiTree. Then, check that
   * calling the lookup() method on an empty ChugiTree returns false. (2) Consider a ChugiTree of
   * height 3 which contains at least 5 Chugimons. Then, try to call lookup() method to search for a
   * Chugimon having a match at the root of the tree. (3) Then, search for a Chugimon at the right
   * and left subtrees at different levels considering successful and unsuccessful search
   * operations. Make sure that the lookup() method returns the expected output for every method
   * call.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testLookup() {
    try {
      //test case 1: empty chugiTree()
      ChugiTree tree = new ChugiTree();
      //test case 2: height 3, at least 5 chugimon chugitree
      Chugimon one = new Chugimon(35,98);
      Chugimon two = new Chugimon(1,31);
      Chugimon three = new Chugimon(42,115);
      Chugimon four  = new Chugimon(12,93);
      Chugimon five  = new Chugimon(12,96);
      Chugimon six  = new Chugimon(46,145);
      ChugiTree tree1 = new ChugiTree();
      tree1.add(one);
      tree1.add(four);
      tree1.add(three);
      tree1.add(two);
      tree1.add(five);
      tree1.add(six);
      //check that the returned values match what's expected
      //test case 1: empty Chugitree lookup will return null 
      if(tree.lookup(20, 150) == null) {
        //test case 2: check that the lookup at the root equals the root
        if(tree1.lookup(35,98).equals(tree1.getRoot())&& tree1.lookup(35, 98).equals(one)) {
          //test case 3 pt 1: search for the left subtree's left leaf(Chugimon two) equals two
          if(tree1.lookup(1,31).equals(two)) {
            //test case 3 pt 2: look for root's right internal node equals three
            if(tree1.lookup(42,115).equals(three)) {
              //test case 4: search for the root's subtree's right leaf(Chugimon six) equals six
              if(tree1.lookup(46,145).equals(six)) {
                //test case 5: looking for a chugimon that doesn't exist in a nonempty tree
                if(tree1.lookup(31, 29) == null) {
                  return true;
                }
              }
            }
          }
        }
      }
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
  }

  /**
   * Checks for the correctness of ChugiTree.countType() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testCountType() {
    try {
      //test case 1: empty chugiTree()
      ChugiTree tree = new ChugiTree();
      //test case 2: chugitree with root only
      Chugimon one = new Chugimon(35,98);
      ChugiTree tree1 = new ChugiTree();
      tree1.add(one);
      //test case 3: a valid BST
      Chugimon two = new Chugimon(1,31);
      Chugimon three = new Chugimon(42,115);
      Chugimon four  = new Chugimon(12,93);
      Chugimon five  = new Chugimon(12,96);
      Chugimon six  = new Chugimon(46,145);
      ChugiTree tree2 = new ChugiTree();
      tree2.add(one);
      tree2.add(four);
      tree2.add(three);
      tree2.add(two);
      tree2.add(five);
      tree2.add(six);
      
      //test case 4: another valid BST
      Chugimon seven = new Chugimon(73,145);
      ChugiTree tree3 = new ChugiTree();
      tree3.add(one);
      tree3.add(four);
      tree3.add(three);
      tree3.add(two);
      tree3.add(five);
      tree3.add(six);
      tree3.add(seven);
      //check that the number of types for each tree match what's expected
      if(tree.countType(ChugiType.WATER) == 0 && tree1.countType(ChugiType.WATER) == 1) {
        if(tree2.countType(ChugiType.BUG) == 3 && tree2.countType(ChugiType.NORMAL) == 2) {
          if(tree3.countType(ChugiType.POISON) == 2 && tree3.countType(ChugiType.ELECTRIC) == 2) {
            return true;
          }
        }
      }
     
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
  }
  
  /**
   * Checks for the correctness of ChugiTree.height() method. This test must consider several
   * scenarios such as, (1) ensures that the height of an empty Chugimon tree is zero. (2) ensures
   * that the height of a tree which consists of only one node is 1. (3) ensures that the height of
   * a ChugiTree with four levels for instance, is 4.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testHeight() {
    try {
      //test case 1: empty Chugimon tree
      ChugiTree tree = new ChugiTree();
      //test case 2: only root node 
      ChugiTree tree1 = new ChugiTree();
      Chugimon one = new Chugimon(35,98);
      tree1.add(one);
      //test case 3: tree with 3 levels
      Chugimon two = new Chugimon(1,31);
      Chugimon three = new Chugimon(42,115);
      Chugimon four  = new Chugimon(12,93);
      Chugimon five  = new Chugimon(12,96);
      Chugimon six  = new Chugimon(46,145);
      ChugiTree tree2 = new ChugiTree();
      tree2.add(one);
      tree2.add(four);
      tree2.add(three);
      tree2.add(two);
      tree2.add(five);
      tree2.add(six);
      //test case 4: tree with 4 levels (right subtree is taller than left)
      Chugimon seven  = new Chugimon(73,145);
      ChugiTree tree3 = new ChugiTree();
      tree3.add(one);
      tree3.add(four);
      tree3.add(three);
      tree3.add(two);
      tree3.add(five);
      tree3.add(six);
      tree3.add(seven);
      
      //check that the height matches the expected heights
      if(tree.height() == 0 && tree1.height() == 1) {
        if(tree2.height() == 3 && tree3.height() == 4) {
          return true;
        }
      }
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
  }

  /**
   * Checks for the correctness of ChugiTree.getFirst() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetFirst() {
    try {
      //test case 1: empty ChugiTree
      ChugiTree tree = new ChugiTree();
      //test case 2: only root node 
      ChugiTree tree1 = new ChugiTree();
      Chugimon one = new Chugimon(35,98);
      tree1.add(one);
      //test case 3: tree with 3 levels
      Chugimon two = new Chugimon(1,31); //expected first
      Chugimon three = new Chugimon(42,115);
      Chugimon four  = new Chugimon(12,93);
      Chugimon five  = new Chugimon(12,96);
      Chugimon six  = new Chugimon(46,145);
      ChugiTree tree2 = new ChugiTree();
      tree2.add(one);
      tree2.add(four);
      tree2.add(three);
      tree2.add(two);
      tree2.add(five);
      tree2.add(six);
      //test case 4: tree with 4 levels 
      Chugimon seven  = new Chugimon(1,13); //expected first
      ChugiTree tree3 = new ChugiTree();
      tree3.add(one);
      tree3.add(four);
      tree3.add(three);
      tree3.add(two);
      tree3.add(seven);
      tree3.add(five);
      tree3.add(six);
      //check that the getFirsts' results match what's expected
      if(tree.getFirst() == null && tree1.getFirst().equals(one)) {
        if(tree2.getFirst().equals(two) && tree3.getFirst().equals(seven)) {
          return true;
        }
      }
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
  }

  /**
   * Checks for the correctness of ChugiTree.getLast() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetLast() {
    try {
      //test case 1: empty ChugiTree
      ChugiTree tree = new ChugiTree();
      //test case 2: only root node 
      ChugiTree tree1 = new ChugiTree();
      Chugimon one = new Chugimon(35,98);
      tree1.add(one);
      //test case 3: tree with 3 levels
      Chugimon two = new Chugimon(1,31); 
      Chugimon three = new Chugimon(42,115);
      Chugimon four  = new Chugimon(12,93);
      Chugimon five  = new Chugimon(12,96);
      Chugimon six  = new Chugimon(46,145);//expected last
      ChugiTree tree2 = new ChugiTree();
      tree2.add(one);
      tree2.add(four);
      tree2.add(three);
      tree2.add(two);
      tree2.add(five);
      tree2.add(six);
      //test case 4: tree with 4 levels 
      Chugimon seven  = new Chugimon(73,145);//expected last
      ChugiTree tree3 = new ChugiTree();
      tree3.add(one);
      tree3.add(four);
      tree3.add(three);
      tree3.add(two);
      tree3.add(five);
      tree3.add(six);
      tree3.add(seven);
      //check that the getLasts' results match what's expected
      if(tree.getLast() == null && tree1.getLast().equals(one)) {
        if(tree2.getLast().equals(six) && tree3.getLast().equals(seven)) {
          return true;
        }
      }
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
    
  }

  /**
   * Checks for the correctness of ChugiTree.delete() method. This test must consider at least 3
   * test scenarios. (1) Remove a Chugimon that is at leaf node (2) Remove a Chugimon at non-leaf
   * node. For each of these scenarios, check that the size of the tree was decremented by one and
   * that the resulting ChugiTree is a valid BST, (3) ensures that the ChugiTree.delete() method
   * returns false when called on an Chugimon that is not present in the BST.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testDelete() {
    //test case 1: remove a chugimon at a leaf node
    ChugiTree tree = new ChugiTree();
    Chugimon one = new Chugimon(35,98);
    Chugimon two = new Chugimon(1,31); 
    Chugimon three = new Chugimon(42,115);
    Chugimon four  = new Chugimon(12,93);
    Chugimon five  = new Chugimon(12,96);
    Chugimon six  = new Chugimon(46,145);
    Chugimon seven  = new Chugimon(108,145);
    tree.add(one);
    tree.add(four);
    tree.add(three);
    tree.add(two);
    tree.add(five);
    tree.add(six);
    
    //testcase 1: removing a chugimon that isn't in the tree
    if(tree.delete(seven) == false) {
      //return true;
    }
    //test case 2: trying to remove a null chugimon
    try {
      tree.delete(null);
      
    }catch(IllegalArgumentException e) {
      //return true;
      //do nothing
    }
    //new tree for test case 4
    ChugiTree tree1= new ChugiTree();
    tree1.add(one);
    tree1.add(four);
    tree1.add(three);
    tree1.add(two);
    tree1.add(five);
    tree1.add(six);
    
    //new tree for test case 5
    ChugiTree tree2= new ChugiTree();
    tree2.add(one);
    tree2.add(four);
    tree2.add(three);
    tree2.add(two);
    tree2.add(five);
    tree2.add(six);
    
    //new tree for test case 6
    ChugiTree tree3= new ChugiTree();
    Chugimon newRoot = new Chugimon(42,107);
    tree3.add(one);
    tree3.add(four);
    tree3.add(three);
    tree3.add(two);
    tree3.add(five);
    tree3.add(six);
    tree3.add(seven);
    //test case 3: removing the rightmost chugimon six(level 1 remove)
    if(tree.delete(six) == true && tree.size() == 5 && tree.isValidBST() == true) {
      //test case 4: removing the parent of the rightmost chugimon(level 2 remove)
      if(tree1.delete(three) == true && tree1.size() == 5 && tree1.isValidBST() == true) {
        //test case 5: removing the parent node of 2 children(level 3 remove)
        if(tree2.delete(four) == true && tree2.size() == 5 && tree2.isValidBST() == true) {
          //test case 6: removing the root node
          if(tree3.delete(one) == true && tree3.size() == 6 && tree3.isValidBST() == true) {
            return true;
          }
        }
      }
    }
    
  
    
    return false; // Default return statement added to resolve compiler errors
  }

  /**
   * Checks for the correctness of ChugiTree.next() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testNext() {
    try {
      //test case 1: empty ChugiTree
      ChugiTree tree = new ChugiTree();
      Chugimon one = new Chugimon(35,98);
      //null chugi
      try{
        tree.next(null);
      }catch(IllegalArgumentException e) {
        //do nothing
      }
      //empty tree
      try {
        tree.next(one);
      }catch(NoSuchElementException e) {
        //do nothing
      }
      //test case 2: root and right internal node with left leaf
      Chugimon two= new Chugimon(46,114);
      Chugimon three= new Chugimon(44,100);
      Chugimon four= new Chugimon(45,100);
      tree.add(one);
      tree.add(two);
      tree.add(three);
      //test case 2 - largest Chugimon -expected to catch a NoSuchElementException
      try {
        tree.next(three); 
      }catch(NoSuchElementException e) {
        //do nothing 
      }
      
      //test case 4: a larger BST
      ChugiTree tree1 = new ChugiTree();
      Chugimon first = new Chugimon(35,98);
      Chugimon second = new Chugimon(1,31); 
      Chugimon third = new Chugimon(42,115);
      Chugimon fourth  = new Chugimon(12,93);
      Chugimon fifth  = new Chugimon(12,96);
      Chugimon sixth  = new Chugimon(46,145);
      tree1.add(first);
      tree1.add(third);
      tree1.add(fourth);
      tree1.add(second);
      tree1.add(fifth);
      tree1.add(sixth);
      
      //test case 3- searching for root's successor - expected three
      if(tree.next(one).equals(three)) {
        //test case 4 - searching for fifth's successor (left subtree)- expected one
        if(tree1.next(fifth).equals(first)) {
          //test case 5 - searching for third's sucessor(right subtree)-expected sixth
          if(tree1.next(third).equals(sixth)) {
            return true; 
          }
        }
      }
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
  }

  /**
   * Checks for the correctness of ChugiTree.previous() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testPrevious() {
    try {
      //test case 1: empty ChugiTree
      ChugiTree tree = new ChugiTree();
      Chugimon one = new Chugimon(35,98);
      //null chugi
      try{
        tree.previous(null);
      }catch(IllegalArgumentException e) {
        //do nothing
        //return true;
      }
      //empty tree
      try {
        tree.previous(one);
      }catch(NoSuchElementException e) {
        //do nothing
        //return true;
      }
      //test case 2: root and right internal node with left leaf
      Chugimon two= new Chugimon(46,114);
      Chugimon three= new Chugimon(44,100);
      Chugimon four= new Chugimon(45,100);
      tree.add(one);
      tree.add(two);
      tree.add(three);
      //test case 2 - smallest Chugimon -expected to catch a NoSuchElementException
      try {
        tree.previous(one); 
      }catch(NoSuchElementException e) {
        //do nothing 
        //return true;
      }
      
      //test case 4: a larger BST
      ChugiTree tree1 = new ChugiTree();
      Chugimon first = new Chugimon(35,98);
      Chugimon second = new Chugimon(1,31); 
      Chugimon third = new Chugimon(42,115);
      Chugimon fourth  = new Chugimon(12,93);
      Chugimon fifth  = new Chugimon(12,96);
      Chugimon sixth  = new Chugimon(46,145);
      tree1.add(first);
      tree1.add(third);
      tree1.add(fourth);
      tree1.add(second);
      tree1.add(fifth);
      tree1.add(sixth);
      
      //test case 3- searching for root's predecessor - expected five
      if(tree1.previous(one).equals(fifth)) {
        //test case 4 - searching for sixth's predecessor (right subtree)- expected third
        if(tree1.previous(sixth).equals(third)) {
          //test case 5- searching for fifth's predecessor (left subtree)- expected fourth
          if(tree1.previous(fifth).equals(fourth)) {
            return true;
          }
        }
      }
      return false; // Default return statement added to resolve compiler errors
    }catch(Exception e) {
      return false;
    }
  }

  /**
   * Calls the test methods
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("testChugimonConstructor(): " + testChugimonConstructor());
    System.out.println("testChugimonCompareToEquals: " + testChugimonCompareToEquals());
    System.out.println("testChugimonToString(): " + testChugimonToString());
    System.out.println("testIsValidBSTHelper(): " + testIsValidBSTHelper());
    System.out.println("testAddToStringSize(): " + testAddToStringSize());
    System.out.println("testLookup(): " + testLookup());
    System.out.println("testHeight(): " + testHeight());
    System.out.println("testCountType(): " + testCountType());
    System.out.println("testGetFirst(): " + testGetFirst());
    System.out.println("testGetLast(): " + testGetLast());
    System.out.println("testDelete(): " + testDelete());
    System.out.println("testNext(): " + testNext());
    System.out.println("testPrevious(): " + testPrevious());
    
  }

}
