// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: C20
// TA: Manas Trivedi 
// Lecturer: Florian Heimerl
// Notes to Grader: None

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Red Black Tree implementation with a RBTNode class for representing the nodes in the tree.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  /**
   * This class represents a RBTnode holding a single value within a Red Black tree.
   */
  protected static class RBTNode<T> extends Node<T> {
    public int blackHeight = 0;
    public RBTNode(T data) { super(data); }
    public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
    public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
    public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
  }
  /**
   * Resolves any red property violations that are introduced by inserting a new node into the red-black tree.
   * @param newNode the newly added red node to be moved because a red property is violated
   */
  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> node){
    //references to the grandparent, parent, and aunt (if there are any) of node
    RBTNode<T> gp;
    RBTNode<T> parent;
    RBTNode<T> aunt;
    
    //check if node has a grandparent
    try {
      gp = node.getUp().getUp();
    }catch(NullPointerException e) {
      //this is expected if node is either the root or a child of the root 
      gp = null;
    }
    
    //check if node has a parent 
    try {
      parent = node.getUp();
    }catch(NullPointerException e) {
      //this is expected if node is the root 
      parent = null;
    }
    
    //if there is no parent and grandparent then node is the root
    if(gp == null && parent == null ) {
      //if the root is red change it to black (in case insert is not called)
      if(node.blackHeight == 0) {
        node.blackHeight = 1;
      }
    }
    //2nd level of tree with only a parent and no grandparent 
    else if(gp == null && parent != null) {
      //do nothing because it's the 2nd level so the nodes are expected to be the black root and 2 red
      //children with no grandparent
    }
    
    //RBT Violation: red node with red child
    else {
      //if the aunt is the right child of the grandparent
      if(!parent.isRightChild()) {
        //assign aunt's reference to be grandparent's right child
        aunt = gp.getDownRight();
       
        //if aunt is black or null
        if(aunt == null || aunt.blackHeight == 1) {
          //first check if the node is a right or left child
          //if node is a right child 
          if(node.isRightChild()) {
            //left rotate at parent
            rotate(node, parent);
            //update parent reference
            try {
              parent = node.getUp();
            }catch(NullPointerException e) {
              //this is expected if node is the new root after rotation
              parent = null;
            }
            //right rotate at grandparent or child's new parent 
            rotate(node, parent);
            //color swap between grandparent and child after rotation  
            node.blackHeight = 1;
            parent.blackHeight = 0;
          }
          //node is a left child 
          else {
            //right rotate at grandparent 
            rotate(parent, gp);
            //color swap parent and grandparent's colors
            parent.blackHeight = 1;
            gp.blackHeight = 0;
          }
        }
        
        //if aunt is red 
        else {
          //re-color grandparent to be red and parent and aunt to be black 
          gp.blackHeight = 0;
          parent.blackHeight = 1;
          aunt.blackHeight = 1;
          //check above to see if other RBT properties are violated such as the root is red instead 
          //of black or a red node has a red child
          enforceRBTreePropertiesAfterInsert(gp);
        }
      }
      
      //the aunt is the left child of the grandparent 
      else {
        //assign aunt's reference as grandparent's left child 
        aunt = gp.getDownLeft();
  
        //if aunt is black or null
        if(aunt == null || aunt.blackHeight == 1) {
          //check if the node is a right or left child
          //if node is a left child 
          if(!node.isRightChild()) {
            //right rotate at parent
            rotate(node, parent);
            //update parent's reference
            try {
              parent = node.getUp();
            }catch(NullPointerException e) {
              //expected if node is the new root
              parent = null;
            }
            //left rotate at grandparent or child's new parent 
            rotate(node, parent);
            //color swap between grandparent and child after rotation  
            node.blackHeight = 1;
            parent.blackHeight = 0;
          }
          
          //node is a right child 
          else {
            //right rotate at grandparent and color swap
            rotate(parent, gp);
            parent.blackHeight = 1;
            gp.blackHeight = 0;
          }
        }
        
        //if aunt is red 
        else {
          //recolor grandparent to be red and parent and aunt to be black 
          gp.blackHeight = 0;
          parent.blackHeight = 1;
          aunt.blackHeight = 1;
          //check above to see if other RBT properties are violated with the grandparent as node
          enforceRBTreePropertiesAfterInsert(gp);
        }
      }
    }
   
  }
  
  /**
   * Insert a new RBTNode into the RBTree and change the root node's blackHeight to be 1 or black.
   * @param data data to be inserted into the RBTree
   */
  @Override
  public boolean insert(T data) throws NullPointerException {
    //insert new RBTNode
    RBTNode<T> newNode = new RBTNode<T>(data);
    insertHelper(newNode);
    //call enforceRBTreePropertiesAfterInsert
    enforceRBTreePropertiesAfterInsert(newNode);
    //recolor the root to black
    RBTNode<T> root = (RBTNode<T>) this.root;
    root.blackHeight = 1;
    return true;
  }

  /**
   * Test case 0: Test inserting a node into an empty tree and check that it is black and not red
   * 
   * expected:
   *    45(B)
   */
  @Test 
  public void testInsertRoot() {
    //create new RedBlackTree
    RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
    //insert 45 as the root 
    tree.insert(45);
    RBTNode<Integer> root = (RBTNode<Integer>) tree.root;
    //check that root's color is black and not red 
    if(root.blackHeight != 1) {
      Assertions.fail("The root is not black, it is still red.");
    }
  }
  /**
   * Test case 1: Tree with height 2, test that the tree inserts 3 red nodes and turns the root node 
   * black after the first insert method 
   *   expected:
   *      15(B)
   *      / \
   *   10(R) 30(R)
   */
 @Test
 public void testInsertThreeRedNodes() {
    //create new RBT
    RedBlackTree<Integer> tree1 = new RedBlackTree<Integer>();
    
    //test inserting a new red node and turn it black because it's the root node
    tree1.insert(15);
    RBTNode<Integer> node1 = (RBTNode<Integer>) tree1.root;
    //check if the root is black
    if(node1.blackHeight != 1) {
      Assertions.fail("The root is not black, it is still red.");
    }
    //test inserting 2 more red nodes
    tree1.insert(30);
    tree1.insert(10);
    
    RBTNode<Integer> node2 = node1.getDownLeft();
    RBTNode<Integer> node3 = node1.getDownRight();
    
    //expected in-order of RBT after all the nodes are inserted
    String e1 = "[ 10, 15, 30 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e2 = "[ 15, 10, 30 ]";
    
    //check that the size, root and data of each node matches what's expected
    if(tree1.size != 3 || tree1.root != node1 || node1.data != 15 || node2.data != 10 || node3.data != 30) {
      Assertions.fail("The tree's size does not match the expected tree size of 3 or one or more of the nodes' data"
          + "does not match their expected data.");
    }
    //check that the nodes match their expected colors
    if(node1.blackHeight != 1 || node2.blackHeight != 0 || node3.blackHeight != 0) {
      Assertions.fail("One or more of the nodes' color does not match their expected color.");
    }
    //check that the in-order and level-order traversals match what's expected 
    if(!tree1.toInOrderString().equals(e1)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree1.toLevelOrderString().equals(e2)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //test should pass by default if it did not fail any of the previous tests
  }
  
  /**
   * Test case 2: Test the first case in the rulebook: rotate then rotate again and color swap in a 
   * tree with height 2. This is tested by inserting red node 17 as red node 15's right child, causing
   * a red node with red child property violation. 
   *    starts with:                    expected:
   *       20(B)                          17(B)
   *      /   \                          /    \
   *  15(R)   30(B)        -->         15(R)   20(R)
   *      \                                       \
   *       17(R)                                  30(B)
   */
  @Test 
  public void testDoubleRotation() {
    //create new tree and new nodes to be inserted into the tree
    RedBlackTree<Integer> tree2 = new RedBlackTree<Integer>();
    //set the parent child relationships and blackHeight of each node 
    RBTNode<Integer> n20 = new RBTNode<Integer>(20);
    n20.blackHeight = 1;
    tree2.root = n20;
    
    //left child
    n20.down[0] = new RBTNode<Integer>(15);
    RBTNode<Integer> n15 = n20.getDownLeft();
    n15.up = n20;
    n15.blackHeight = 0;
    
    //right child
    n20.down[1] = new RBTNode<Integer>(30);
    RBTNode<Integer> n30 = n20.getDownRight();
    n30.up = n20;
    n30.blackHeight = 1;
    
    tree2.size = 3; //size of tree before inserting 17
    
    //new node to be inserted as node15's left child
    tree2.insert(17);
    RBTNode<Integer> n17 = n15.getUp();
    
    //expected in-order of RBT after all the nodes are inserted
    String e3 = "[ 15, 17, 20, 30 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e4 = "[ 17, 15, 20, 30 ]";
    
    //check that the in-order and level-order traversals match what's expected 
    if(!tree2.toInOrderString().equals(e3)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree2.toLevelOrderString().equals(e4)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //also check that the blackHeights of the nodes match what's expected
    if(n17.blackHeight != 1 || n15.blackHeight != 0 || n20.blackHeight != 0 || n30.blackHeight != 1) {
      Assertions.fail("The color of one of the nodes does not match their expected color.");
    }
    // pass if the in-order and level-order traversals match what's expected 
  }
    
  /**
   * Test case 3: Test the 2nd case in the rulebook: recolor then check above and turn the root black
   * in tree with height 2. This is tested by inserting the red node 10 as the red node 15's left child.
   *  start with:                       expected:
   *       20(B)                          20(B)
   *      /   \                          /    \
   *  15(R)   40(R)        -->         15(B)   40(B)
   *    /                               /
   * 10(R)                            10(R)
   */
  @Test 
  public void testRecolor() {
    //create a new tree and insert 4 nodes in the level order of the start with diagram above
    RedBlackTree<Integer> tree3 = new RedBlackTree<Integer>();
    tree3.insert(20);
    tree3.insert(15);
    tree3.insert(40);
    tree3.insert(10);
    
    //create node references for the nodes in the tree
    RBTNode<Integer> n20 = (RBTNode<Integer>) tree3.root;
    RBTNode<Integer> n15 = n20.getDownLeft();
    RBTNode<Integer> n40 = n20.getDownRight();
    RBTNode<Integer> n10 = n15.getDownLeft();
    
    //expected in-order of RBT after all the nodes are inserted
    String e5 = "[ 10, 15, 20, 40 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e6 = "[ 20, 15, 40, 10 ]";
    
    //check that the size, root, and data match what's expected
    if(tree3.size != 4 || tree3.root != n20 || n20.data != 20 || n15.data != 15 || n40.data != 40 || n10.data != 10) {
      Assertions.fail("The tree's size does not match the expected tree size of 3 or one or more of the nodes' data"
          + "does not match their expected data.");
    }
    //check that the nodes match their expected colors 
    if(n20.blackHeight != 1 || n15.blackHeight != 1 || n40.blackHeight != 1 || n10.blackHeight != 0) {
      Assertions.fail("One or more of the nodes' color does not match their expected color.");
    }
    //check that the in-order and level-order traversals match what's expected 
    if(!tree3.toInOrderString().equals(e5)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree3.toLevelOrderString().equals(e6)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //passes if all of the previous tests passes
   }
  
  /**
   * Test case 4: Tree of height 5 testing the enforceRBTreePropertiesAfterInsert method with a 
   * null aunt in the left branch of the right subtree by inserting 95 as 100's left child causing a 
   * red node with red child property violation
   * 
   *      start with:                       expected:
   *          50(B)                            50(B)
   *         /     \                          /    \
   *      15(B)    80(R)        -->         15(B)   80(R)
   *        / \     /   \                    / \     /   \
   *     10(R)20(R)75(B)90(B)            10(R) 20(R)75(B)95(B)
   *                        \                            /  \
   *                        100(R)                    90(R) 100(R)
   *                        /
   *                     95(R)
   */           
  @Test 
  public void testBiggerTreeWithNullAunt() {
    //create new tree and add a root node
    RedBlackTree<Integer> tree4 = new RedBlackTree<Integer>();
    RBTNode<Integer> node50 = new RBTNode<Integer>(50);
    node50.blackHeight = 1;
    tree4.root = node50;
    
    //left subtree: 15(B), 10(R), 20(R) in level order creating the nodes and establishing the parent 
    //child relationships and blackHeights
    node50.down[0] = new RBTNode<Integer>(15);
    RBTNode<Integer> node15 = node50.getDownLeft();
    node15.up = node50;
    node15.blackHeight = 1;
    
    node15.down[0] = new RBTNode<Integer>(10);
    RBTNode<Integer> node10 = node15.getDownLeft();
    node10.up = node15;
    node10.blackHeight = 0;
    
    node15.down[1] = new RBTNode<Integer>(20);
    RBTNode<Integer> node20 = node15.getDownRight();
    node20.up = node15;
    node20.blackHeight = 0;
    
    //right subtree: 80(R), 75(B), 90(B), 100(R) in level order creating the nodes and establishing the parent 
    //child relationships and blackHeights
    node50.down[1] = new RBTNode<Integer>(80);
    RBTNode<Integer> node80 = node50.getDownRight();
    node80.blackHeight = 0;
    node80.up = node50;
    
    node80.down[0] = new RBTNode<Integer>(75);
    RBTNode<Integer> node75 = node80.getDownLeft();
    node75.up = node80;
    node75.blackHeight = 1;
    
    node80.down[1] = new RBTNode<Integer>(90);
    RBTNode<Integer> node90 = node80.getDownRight();
    node90.up = node80;
    node90.blackHeight = 1;
    
    node90.down[1] = new RBTNode<Integer>(100);
    RBTNode<Integer> node100 = node90.getDownRight();
    node100.up = node90;
    node100.blackHeight = 0;
    
    tree4.size = 8; //size of tree before inserting 95
    
    //insert red node 95 as the left child of node100
    tree4.insert(95);
    RBTNode<Integer> node95 = node80.getDownRight();
    
    //expected in-order of RBT after all the nodes are inserted
    String e7 = "[ 10, 15, 20, 50, 75, 80, 90, 95, 100 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e8 = "[ 50, 15, 80, 10, 20, 75, 95, 90, 100 ]";
    
    //check that the in-order and level-order traversals match what's expected 
    if(!tree4.toInOrderString().equals(e7)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree4.toLevelOrderString().equals(e8)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //check that the blackHeight of each node also match their expected blackHeight
    if(node50.blackHeight != 1 || node15.blackHeight != 1 || node80.blackHeight != 0 || 
        node10.blackHeight != 0 || node20.blackHeight != 0 || node75.blackHeight != 1 || 
        node95.blackHeight != 1 || node90.blackHeight != 0 || node100.blackHeight != 0) {
      Assertions.fail("One of the nodes' blackHeight does not match their expected blackHeight.");
    }
    // pass if the in-order and level-order traversals match what's expected 
   }
  
  /**
   * Test case 5: Testing a tree with height 3 testing the enforceRBTreePropertiesAfterInsert method with a 
   * black aunt in the right branch of the right subtree (it ends up not being a valid RBT because it 
   * violates the every path from the root to a null child has the same number of black nodes property
   * but this is expected). It is tested by inserting the red node 13 as the left child of the red
   * node 15, causing a red node with red child property violation.
   * 
   *        start with:                       expected:
   *          50(B)                            15(B)
   *         /     \                          /    \
   *      15(R)    80(B)        -->         13(B)   50(R)
   *        / \     /   \                           /   \
   *     13(R)30(B)75(R)90(R)                      30(B)80(R)
   *                                                    /  \
   *                                                    75(R)90(R)
   *                       
   */
  @Test 
  public void testBlackAuntInRightBranch() {
    RedBlackTree<Integer> tree5 = new RedBlackTree<Integer>();
    RBTNode<Integer> num50 = new RBTNode<Integer>(50);
    num50.blackHeight = 1;
    tree5.root = num50;
    
    //left subtree - red 15 and black 30 in level order creating the nodes and establishing the parent 
    //child relationships and blackHeights
    num50.down[0] = new RBTNode<Integer>(15);
    RBTNode<Integer> num15 = num50.getDownLeft();
    num15.up = num50;
    num15.blackHeight = 1;
    
    num15.down[1] = new RBTNode<Integer>(30);
    RBTNode<Integer> num30 = num15.getDownRight();
    num30.up = num15;
    num30.blackHeight = 1;
    
    //right subtree - black 80, red 75, and red 90 in level order creating the nodes and establishing the parent 
    //child relationships and blackHeights
    num50.down[1] = new RBTNode<Integer>(80);
    RBTNode<Integer> num80 = num50.getDownRight();
    num80.blackHeight = 1;
    num80.up = num50;
    
    num80.down[0] = new RBTNode<Integer>(75);
    RBTNode<Integer> num75 = num80.getDownLeft();
    num75.up = num80;
    num75.blackHeight = 0;
    
    num80.down[1] = new RBTNode<Integer>(90);
    RBTNode<Integer> num90 = num80.getDownRight();
    num90.up = num80;
    num90.blackHeight = 0;
    
    tree5.size = 6; //size of tree before inserting 13
    
    //insert red node 13 as the left child of num15
    tree5.insert(13);
    RBTNode<Integer> num13 = num15.getDownLeft();
    
    
    //expected in-order of RBT after all the nodes are inserted
    String e9 = "[ 13, 15, 30, 50, 75, 80, 90 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e10 = "[ 15, 13, 50, 30, 80, 75, 90 ]";
    
    //check that the in-order and level-order traversals match what's expected 
    if(!tree5.toInOrderString().equals(e9)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree5.toLevelOrderString().equals(e10)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //check that all the nodes match their expected blackHeight after inserting 13
    if(num15.blackHeight != 1 || num13.blackHeight != 0 || num50.blackHeight != 0 || num30.blackHeight != 1 ||
        num80.blackHeight != 1 || num75.blackHeight != 0 || num90.blackHeight != 0) {
      Assertions.fail("One of the nodes' blackHeight does not match their expected blackHeight.");
    }
    // pass if the in-order and level-order traversals match what's expected 
   
   }
  
  /**
   * Test case 6: Testing a cascading fix in a tree with height 5 by inserting the red node 70 as
   * red node 90's left child.
   * 
   *      start with:                       expected:
   *          40(B)                            50(B)
   *         /     \                          /    \
   *      17(B)    50(R)        -->         40(R)   65(R)
   *        / \     /  \                    / \     /   \
   *   7(R) 14(R) 45(B) 65(B)           17(B) 45(B)55(B)90(B)
   *                    /   \             / \             /
   *                55(R)    90(R)     7(R) 14(R)      70(R)
   *                          /
   *                        70(R) 
   */
  @Test 
  public void testCascadingFix() {
    //create new tree and add a root node
    RedBlackTree<Integer> tree6 = new RedBlackTree<Integer>();
    RBTNode<Integer> no40 = new RBTNode<Integer>(40);
    no40.blackHeight = 1;
    tree6.root = no40;
    
    //left subtree: 17(B), 7(R), 14(R) in level order creating the nodes and establishing the parent 
    //child relationships and blackHeights
    no40.down[0] = new RBTNode<Integer>(17);
    RBTNode<Integer> no17 = no40.getDownLeft();
    no17.up = no40;
    no17.blackHeight = 1;
    
    no17.down[0] = new RBTNode<Integer>(7);
    RBTNode<Integer> no7 = no17.getDownLeft();
    no7.up = no17;
    no7.blackHeight = 0;
    
    no17.down[1] = new RBTNode<Integer>(20);
    RBTNode<Integer> no20 = no17.getDownRight();
    no20.up = no17;
    no20.blackHeight = 0;
    
    //right subtree: 50(R), 45(B), 65(B), 55(R), 90(R) in level order creating the nodes and establishing the parent 
    //child relationships and blackHeights
    no40.down[1] = new RBTNode<Integer>(50);
    RBTNode<Integer> no50 = no40.getDownRight();
    no50.blackHeight = 0;
    no50.up = no40;
    
    no50.down[0] = new RBTNode<Integer>(45);
    RBTNode<Integer> no45 = no50.getDownLeft();
    no45.up = no50;
    no45.blackHeight = 1;
    
    no50.down[1] = new RBTNode<Integer>(65);
    RBTNode<Integer> no65 = no50.getDownRight();
    no65.up = no50;
    no65.blackHeight = 1;
    
    no65.down[0] = new RBTNode<Integer>(55);
    RBTNode<Integer> no55 = no65.getDownLeft();
    no55.up = no65;
    no55.blackHeight = 0;
    
    no65.down[1] = new RBTNode<Integer>(90);
    RBTNode<Integer> no90 = no65.getDownRight();
    no90.up = no65;
    no90.blackHeight = 0;
    
    tree6.size = 9; //size of tree before inserting 70
    
    //insert red node 70 as the left child of no90
    tree6.insert(70);
    RBTNode<Integer> no70 = no90.getDownLeft();
    
    //expected in-order of RBT after all the nodes are inserted
    String e11 = "[ 7, 17, 20, 40, 45, 50, 55, 65, 70, 90 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e12 = "[ 50, 40, 65, 17, 45, 55, 90, 7, 20, 70 ]";
    
    //check that the in-order and level-order traversals match what's expected 
    if(!tree6.toInOrderString().equals(e11)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree6.toLevelOrderString().equals(e12)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //check that the blackHeight of each node also match their expected blackHeight
    if(no50.blackHeight != 1 || no40.blackHeight != 0 || no65.blackHeight != 0 || 
        no17.blackHeight != 1 || no45.blackHeight != 1 || no55.blackHeight != 1 || 
        no90.blackHeight != 1 || no7.blackHeight != 0 || no20.blackHeight != 0|| no70.blackHeight != 0) {
      Assertions.fail("One of the nodes' blackHeight does not match their expected blackHeight.");
    }
    // pass if the in-order and level-order traversals match what's expected 
  }
  /**
   * Test case 7: Testing a null aunt in the left branch by inserting red node 60 into a tree of height
   * 2 initially. 
   * 
   *      start with:                       expected:
   *          40(B)                            45(B)
   *            \                              /  \
   *            45(R)                        40(R)60(R)
   *              \ 
   *             60(R)
   */
  @Test
  public void testNullAuntLeftBranch() {
    //create a new tree and insert 4 nodes in the level order of the start with diagram above
    RedBlackTree<Integer> tree7 = new RedBlackTree<Integer>();
    tree7.insert(40);
    tree7.insert(45);
    tree7.insert(60);
    
    //create node references for the nodes in the tree
    RBTNode<Integer> node45 = (RBTNode<Integer>) tree7.root;
    RBTNode<Integer> node40 = node45.getDownLeft();
    RBTNode<Integer> node60 = node45.getDownRight();
    
    //expected in-order of RBT after all the nodes are inserted
    String e13 = "[ 40, 45, 60 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e14 = "[ 45, 40, 60 ]";
    
    //check that the nodes match their expected colors 
    if(node45.blackHeight != 1 || node40.blackHeight != 0 || node60.blackHeight != 0) {
      Assertions.fail("One or more of the nodes' color does not match their expected color.");
    }
    //check that the in-order and level-order traversals match what's expected 
    if(!tree7.toInOrderString().equals(e13)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree7.toLevelOrderString().equals(e14)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //passes if all of the previous tests passes
   }
  /**
   * Test case 8: Testing a recolor repair in a tree with height 4. This is tested by inserting 1 as the 
   * left child of 5.
   * 
   *      start with:                       expected:
   *          30(B)                            30(B)
   *          /  \                              /  \
   *        10(B) 42(B)                      10(R) 42(B)
   *        /  \                              /  \
   *       5(R)27(R)                        5(B)27(B)   
   *       /                                 /
   *      1(R)                             1(R)
   */
  @Test
  public void testRecolorLeftSubtree() {
    //create new tree and add a root node
    RedBlackTree<Integer> tree8 = new RedBlackTree<Integer>();
    RBTNode<Integer> m30 = new RBTNode<Integer>(30);
    m30.blackHeight = 1;
    tree8.root = m30;
    
    //left subtree: 10(B), 5(R), 27(R) in level order creating the nodes and establishing the parent 
    //child relationships and blackHeights
    m30.down[0] = new RBTNode<Integer>(10);
    RBTNode<Integer> m10 = m30.getDownLeft();
    m10.up = m30;
    m10.blackHeight = 1;
    
    m10.down[0] = new RBTNode<Integer>(5);
    RBTNode<Integer> m5 = m10.getDownLeft();
    m5.up = m10;
    m5.blackHeight = 0;
    
    m10.down[1] = new RBTNode<Integer>(27);
    RBTNode<Integer> m27 = m10.getDownRight();
    m27.up = m10;
    m27.blackHeight = 0;
    
    //right child of root: 42(B) create the node and establish the parent child relationship and its blackHeight 
    m30.down[1] = new RBTNode<Integer>(42);
    RBTNode<Integer> m42 = m30.getDownRight();
    m42.blackHeight = 1;
    m42.up = m30;
    
    
    tree8.size = 5; //size of tree before inserting 1
    
    //insert red node 1 as the left child of m5
    tree8.insert(1);
    RBTNode<Integer> m1 = m5.getDownLeft();
    
    //expected in-order of RBT after all the nodes are inserted
    String e11 = "[ 1, 5, 10, 27, 30, 42 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e12 = "[ 30, 10, 42, 5, 27, 1 ]";
    
    //check that the in-order and level-order traversals match what's expected 
    if(!tree8.toInOrderString().equals(e11)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree8.toLevelOrderString().equals(e12)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //check that the blackHeight of each node also match their expected blackHeight
    if(m30.blackHeight != 1 || m10.blackHeight != 0 || m42.blackHeight != 1 || 
        m5.blackHeight != 1 || m27.blackHeight != 1 || m1.blackHeight != 0 ) {
      Assertions.fail("One of the nodes' blackHeight does not match their expected blackHeight.");
    }
    // pass if the in-order and level-order traversals match what's expected 
  }
  /**
   * Test case 9: Testing a recolor repair in a tree with height 4. This is tested by inserting 100 as the 
   * right child of 95.
   * 
   *      start with:                       expected:
   *          37(B)                            37(B)
   *          /  \                              /  \
   *        18(B) 39(B)                      18(B) 39(R)
   *              /  \                              /  \
   *             38(R)95(R)                        38(B)95(B)   
   *                   \                                 \
   *                   100(R)                            100(R)
   */
  @Test
  public void testRecolorRightSubtree() {
    //create new tree and add a root node
    RedBlackTree<Integer> tree9 = new RedBlackTree<Integer>();
    RBTNode<Integer> m37 = new RBTNode<Integer>(37);
    m37.blackHeight = 1;
    tree9.root = m37;
    
    //left subtree: 18(B) create the node and establish the parent child relationship and its blackHeight
    m37.down[0] = new RBTNode<Integer>(18);
    RBTNode<Integer> m18 = m37.getDownLeft();
    m18.up = m37;
    m18.blackHeight = 1;
    
    //right child of root: 39(B), 38(R), 95(R) in level order creating the nodes and establishing the parent 
    //child relationships and blackHeights
    m37.down[1] = new RBTNode<Integer>(39);
    RBTNode<Integer> m39 = m37.getDownRight();
    m39.blackHeight = 1;
    m39.up = m37;
    
    m39.down[0] = new RBTNode<Integer>(38);
    RBTNode<Integer> m38 = m39.getDownLeft();
    m38.up = m39;
    m38.blackHeight = 0;
    
    m39.down[1] = new RBTNode<Integer>(95);
    RBTNode<Integer> m95 = m39.getDownRight();
    m95.up = m39;
    m95.blackHeight = 0;
    
    tree9.size = 5; //size of tree before inserting 100
    
    //insert red node 100 as the left child of m95
    tree9.insert(100);
    RBTNode<Integer> m100 = m95.getDownRight();
    
    //expected in-order of RBT after all the nodes are inserted
    String e13 = "[ 18, 37, 38, 39, 95, 100 ]";
    //expected level-order of RBT after all the nodes are inserted
    String e14 = "[ 37, 18, 39, 38, 95, 100 ]";
    
    //check that the in-order and level-order traversals match what's expected 
    if(!tree9.toInOrderString().equals(e13)) {
      Assertions.fail("The RBT does not match the expected in-order traversal.");
    }
    if(!tree9.toLevelOrderString().equals(e14)) {
      Assertions.fail("The RBT does not match the expected level-order traversal.");
    }
    //check that the blackHeight of each node also match their expected blackHeight
    if(m37.blackHeight != 1 || m18.blackHeight != 1 || m39.blackHeight != 0 || 
        m38.blackHeight != 1 || m95.blackHeight != 1 || m100.blackHeight != 0 ) {
      Assertions.fail("One of the nodes' blackHeight does not match their expected blackHeight.");
    }
    // pass if the in-order and level-order traversals match what's expected 
  }
}