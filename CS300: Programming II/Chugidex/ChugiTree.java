////////////////P09 Chugidex//////////////////////////
// Title:    P09 Chugimon
// Course:   CS 300 Fall 2022

// Title:    Chugi Tree
// Author:   Anna Wang
// Email:    awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    None
// Partner Email:   None
// Partner Lecturer's Name: None
//
//VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:     None
// Online Sources:  Hobbes' lecture notes, https://piazza.com/class/l7f41s35yau64i/post/3141 - this 
//                  post helped me with the toString formatting, 
//                  https://piazza.com/class/l7f41s35yau64i/post/3093 and 
//                  https://piazza.com/class/l7f41s35yau64i/post/3086- helped me understand
//                  nextHelper() more
//                  https://piazza.com/class/l7f41s35yau64i/post/3219, and
//                  https://piazza.com/class/l7f41s35yau64i/post/3190 - helped me with the delete 
//                  algorithm
//
///////////////////////////////////////////////////////////////////////////////
import java.util.NoSuchElementException;

/**
 * This class implements a ChugidexStorage as a Binary Search Tree.
 * 
 * Notes: 1) You may NOT use any arrays or Collections objects (ArrayLists, etc)
 * in this class. 2)
 * You may NOT use any loops (for, while, etc) in this class. Recursive
 * strategies only.
 *
 */
public class ChugiTree implements ChugidexStorage {

  /**
   * The root of this ChugiTree. Set to null when tree is empty.
   */
  private BSTNode<Chugimon> root;

  /**
   * The size of this ChugiTree (total number of Chugimon stored in this BST)
   */
  private int size;

  /**
   * Getter method for the Chugimon at the root of this BST.
   * 
   * @return the root of the BST.
   */
  public Chugimon getRoot() {
    return this.root.getData(); 
  }

  /**
   * A method for determining whether this ChugiTree is a valid BST. In
   * order to be a valid BST, the following must be true: For every internal
   * (non-leaf) node X of a binary tree, all the values in the node's left subtree
   * are less than the value in X, and all the values in the node's right subtree
   * are greater than the value in X.
   * 
   * @return true if this ChugiTree is a valid BST, false otherwise
   */
  public boolean isValidBST() {
    return isValidBSTHelper(root);
  }

  /**
   * A helper method for determining whether this ChugiTree is a valid BST. In
   * order to be a valid BST, the following must be true: For every internal
   * (non-leaf) node X of a binary tree, all the values in the node's left subtree
   * are less than the value in X, and all the values in the node's right subtree
   * are greater than the value in X.
   * 
   * @param node The root of the BST.
   * @return true if the binary tree rooted at node is a BST, false otherwise
   */
  public static boolean isValidBSTHelper(BSTNode<Chugimon> node) {
    boolean left = false; //boolean to return true if all the internal nodes' getleft return true 
    boolean right = false; //boolean to return true if all the internal nodes' getright return true
    //base case:
    if(node == null) {
      return true;
    }
    Chugimon n = node.getData(); //chugimon at node/root
    //if the max chugimon in the left subtree is greater than or equal to node then it's not a valid 
    //BST or if the min chugimon in the right subtree is lesser than or equal to node then it's also
    //not a valid BST
    if(node.getLeft() != null && node.getRight()!= null) {
      if(getLastHelper(node.getLeft()).compareTo(n)>=0 || 
          getFirstHelper(node.getRight()).compareTo(n)<=0) {
        return false;
      }
    }
    //recursive case:
    //if left is lesser than node and there's a left node(internal node)
    if(node.getLeft()!= null && node.getLeft().getData().compareTo(node.getData())<0) {
      if(isValidBSTHelper(node.getLeft())!= true) {
        return false;
      }
      left = true;
    }
    //if left is greater than or equal to node 
    else if(node.getLeft()!= null && node.getLeft().getData().compareTo(node.getData())>=0) {
      return false;
    }
    else {
      //leaf 
      left = true;
      
    }
    
    //if right is greater than node and there's a right node(internal node)
    if(node.getRight()!= null && node.getRight().getData().compareTo(node.getData())>0) {
      if(isValidBSTHelper(node.getRight()) != true) {
        return false;
      }
      right = true;
    }
    //if right is lesser than or equal to node
    else if(node.getRight()!= null && node.getRight().getData().compareTo(node.getData())<=0) {
      return false;
    }
    else {
      //leaf
      right = true;
    }
    
    return left && right;
  }

  /**
   * Checks whether this ChugiTree is empty or not
   * 
   * @return true if this tree is empty, false otherwise
   */
  @Override
  public boolean isEmpty() {
    //if size is 0 and root is null
    if(size == 0 && root == null) {
      return true;
    }
    return false; // default return statement
  }

  /**
   * Gets the size of this ChugiTree
   * 
   * @return the total number of Chugimons stored in this tree
   */
  @Override
  public int size() {
    return size; //return the size
  }

  /**
   * Returns a String representation of all the Chugimons stored within this
   * ChugiTree in the
   * increasing order with respect to the result of Chugimon.compareTo() method.
   * The string should
   * be a comma-separated list of all the Chugimon toString() values. No spaces
   * are expected to be
   * in the resulting string. No comma should be at the end of the resulting
   * string. For instance,
   * 
   * "nameOne#12.25,nameTwo#12.56,nameTwo#89.27"
   * 
   * @return a string containing all of the Chugimon, in the increasing order.
   *         Returns an empty
   *         string "" if this BST is empty.
   * 
   */
  @Override
  public String toString() {
    String returnValue = toStringHelper(root);
    if(returnValue.isBlank() != true) {
      returnValue = returnValue.substring(0,returnValue.length()-1); //to get rid of the extra comma
                                                                     //at the end
    }
    return returnValue;
  }

  /**
   * Recursive helper method which returns a String representation of the
   * ChugiTree rooted at node. An example of the String representation of the
   * contents of a ChugiTree storing three Chugimons is provided in the
   * description of the above toString() method.
   * 
   * @param node references the root of a subtree
   * @return a String representation of all the Chugimons stored in the sub-tree
   *         rooted at node in
   *         increasing order. Returns an empty String "" if current is null.
   */
  protected static String toStringHelper(BSTNode<Chugimon> node) {
    String returnValue = "";
    //base case: if the node/root is null
    if(node == null) {
      return returnValue;
    }
   
    //recursive case: in order traversal - left+self+right
    if(node.getLeft()!=null) {
      returnValue +=toStringHelper(node.getLeft()).toString();
    }
    returnValue += node.getData().toString()+",";
    if(node.getRight()!=null) {
      returnValue +=toStringHelper(node.getRight()).toString();
    }
   
    return returnValue;
  }

  /**
   * Adds a new Chugimon to this ChugiTree. Duplicate Chugimons are NOT allowed.
   * 
   * @param newChugimon Chugimon to add to this ChugiTree
   * @return true if if the newChugimon was successfully added to the ChugiTree,
   *         false if a match with newChugimon is already present in the tree.
   * @throws IllegalArgumentException with a descriptive error message if
   *                                  newChugimon is null.
   */
  @Override
  public boolean add(Chugimon newChugimon) {
    if(newChugimon == null) {
      throw new IllegalArgumentException("There must be a new non-null Chugimon");
    }
    //call the helper method and make sure it returns true then increase size
    if(addHelper(newChugimon, root) == true) {
      //special case: if root is null
      if(root == null) {
        root = new BSTNode<Chugimon>(newChugimon);
      }
      size++;
      return true;
    }
    return false; // default return statement
  }

  /**
   * Recursive helper method to insert a new Chugimon to a Pokedex rooted at node.
   * 
   * @param node        The "root" of the subtree we are inserting the new
   *                    Chugimon into.
   * @param newChugimon The Chugimon to be added to a BST rooted at node. We
   *                    assume that newChugimon is NOT null.
   * @return true if the newChugimon was successfully added to the ChugiTree,
   *         false if a match with
   *         newChugimon is already present in the subtree rooted at node.
   */
  protected static boolean addHelper(Chugimon newChugimon, BSTNode<Chugimon> node) {
    // edge case for if the root is null:
    if(node == null) {
      node = new BSTNode<Chugimon>(newChugimon);
      return true;
    }
    int add = newChugimon.compareTo(node.getData());
    if(add == 0) {
      return false; // the root and newChugimon are the same, no duplicates
    }
    else if(add<0) {
      //newChugimon goes to the left
      //base cases:
      if(node.getLeft() == null) {
        node.setLeft(new BSTNode<Chugimon>(newChugimon));
        return true;
      }
      //recursive case:
      else {
        return addHelper(newChugimon, node.getLeft());
      }
    }
    //newChugimon goes to the right
    else{
      //base cases:
      if(node.getRight() == null) {
        node.setRight(new BSTNode<Chugimon>(newChugimon));
        return true;
      }
      //recursive case:
      else {
        return addHelper(newChugimon, node.getRight());
      }
    }
  }

  /**
   * Searches a Chugimon given its first and second identifiers.
   * 
   * @param firstId  First identifier of the Chugimon to find
   * @param secondId Second identifier of the Chugimon to find
   * @return the matching Chugimon if match found, null otherwise.
   */
  @Override
  public Chugimon lookup(int firstId, int secondId) {
    Chugimon find = new Chugimon(firstId,secondId);
    //edge case: if root is null
    if(root == null) {
      return null;
    }
    //call the helper method then return whatever the helper returns
    Chugimon lookedUp = lookupHelper(find, root);
    return lookedUp;
  }

  /**
   * Recursive helper method to search and return a match with a given Chugimon in
   * the subtree rooted at node, if present.
   * 
   * @param toFind a Chugimon to be searched in the BST rooted at node. We assume
   *               that toFind is NOT null.
   * @param node   "root" of the subtree we are checking whether it contains a
   *               match to target.
   * @return a reference to the matching Chugimon if found, null otherwise.
   */
  protected static Chugimon lookupHelper(Chugimon toFind, BSTNode<Chugimon> node) {
    //base case: Chugimon isn't found 
    //return null;
    //
    int find = toFind.compareTo(node.getData());
    if(find == 0) {
      return node.getData(); // the root and newChugimon are the same, no duplicates
    }
    //go to left to find toFind
    else if(find<0) {
      if(node.getLeft()!= null) {
        //base case
        if(toFind.equals(node.getData())) {
          return node.getData();
        }
        //recursive case
        //if toFind is less than current node go left
        else if(toFind.compareTo(node.getData())<0) {
          return lookupHelper(toFind,node.getLeft());
        }
        //if toFind is more than current node go right
        else if(toFind.compareTo(node.getData())>0) {
          return lookupHelper(toFind,node.getRight());
        }
      }
      else {
        //base case 
        return null;
      }
    }
    //go to right to find toFind
    else {
      if(node.getRight()!= null) {
        //base case
        if(toFind.equals(node.getData())) {
          return node.getData();
        }
        //recursive case
        //if toFind is less than current node go left
        else if(toFind.compareTo(node.getData())<0) {
          return lookupHelper(toFind,node.getLeft());
        }
        //if toFind is less than current node go left
        else if(toFind.compareTo(node.getData())>0) {
          return lookupHelper(toFind,node.getRight());
        }
      }
      else {
        //base case 
        return null;
      }
    }
    return null;
  }

  /**
   * Computes and returns the height of this BST, counting the number of NODES
   * from root to the deepest leaf.
   * 
   * @return the height of this Binary Search Tree
   */
  public int height() {
    return heightHelper(root);
  }

  /**
   * Recursive helper method that computes the height of the subtree rooted at
   * node counting the number of nodes and NOT the number of edges from node to
   * the deepest leaf
   * 
   * @param node root of a subtree
   * @return height of the subtree rooted at node
   */
  protected static int heightHelper(BSTNode<Chugimon> node) {
    int leftHeight = 0; //height of node plus max children's height on the left side
    int rightHeight = 0;//height of node plus max children's height on the right side
    //base case
    if(node == null) {
      return 0;
    }
    //base case
    if(node.getLeft() == null && node.getRight() == null) {
      return 1;
    }
    if(node.getLeft()!= null) {
      //recursively adding 1(1+max children's height)
      leftHeight = heightHelper(node.getLeft()) + 1;
    }
    if(node.getRight()!= null) {
      //recursively adding 1(1+max children's height)
      rightHeight = heightHelper(node.getRight())+1;
    }
    
    //if leftHeight is more than rightHeight return it
    if(leftHeight>rightHeight) {
      return leftHeight;
    }
    //otherwise return rightHeight
    return rightHeight;
  }

  /**
   * Recursive method to find and return the first Chugimon, in the increasing
   * order, within this ChugiTree (meaning the smallest element stored in the
   * tree).
   * 
   * @return the first element in the increasing order of this BST, and null if
   *         the tree is empty.
   */
  @Override
  public Chugimon getFirst() {
    //edge case if root is null
    if(root == null) {
      return null;
    } 
    // HINT: The smallest element in a non-empty BST is the left most element
    return getFirstHelper(root);
    
  }

  /**
   * Recursive helper method for getFirst().
   * 
   * @param root the node from which to find the the minimum node
   * @return the minimum element in the increasing order from the node <b>root</b>
   */
  protected static Chugimon getFirstHelper(BSTNode<Chugimon> root) {
    // HINT: The smallest element in a non-empty BST is the left most element
    //assuming root isn't null (covered in getFirst())
    //recursive case: continue going left to find min node
    if(root.getLeft()!= null) {
      return getFirstHelper(root.getLeft());
    }
    //base case: reached the last leftmost node
    else {
      return root.getData();
    }
  }

  /**
   * Recursive method to find and return the last Chugimon, in the increasing
   * order, within this ChugiTree (meaning the greatest element stored in the
   * tree).
   * 
   * @return the last element in the increasing order of this BST, and null if the
   *         tree is empty.
   */
  @Override
  public Chugimon getLast() {
    // HINT: The greatest element in a non-empty BST is the right most element
    //edge case if root is null
    if(root == null) {
      return null; 
    }
    return getLastHelper(root);
  }

  /**
   * Recursive helper method for getLast().
   * 
   * @param root the node from which to find the the maximum node
   * @return the maximum element in the increasing order from the node <b>root</b>
   */
  protected static Chugimon getLastHelper(BSTNode<Chugimon> root) {
    // TODO Implement this method.

    // HINT: The smallest element in a non-empty BST is the right most element
    //assuming root isn't null (covered in getLast())
    //recursive case:
    if(root.getRight()!= null) {
      return getLastHelper(root.getRight());
    }
    //base case: reached the rightmost node
    else {
      return root.getData();
    }
  }

  /**
   * Recursive method to get the number of Chugimon with a primary or secondary
   * type of the specified type, stored in this ChugiTree.
   * 
   * @param chugiType the type of Chugimons to count. We assume that chugiType is
   *                  NOT null.
   * @return the number of all the Chugimon objects with a primary or secondary
   *         type of the
   *         specified type stored in this ChugiTree.
   */
  public int countType(ChugiType chugiType) {
    int count = countTypeHelper(chugiType, root);
    return count;
  }
  /**
   * Recursive helper method for countType()
   * @param chugiType the type of Chugimons to count
   * @param node the root to start form
   * @return the number of Chugimon objects with a primary or secondary type of the specified type
   * stored in this ChugiTree.
   */
  private int countTypeHelper(ChugiType chugiType, BSTNode<Chugimon> node) {
    int count = 0;
    //base case
    if(node == null) {
      return count;
    }
    //if the node's primary or secondary type equals the chugiType then add 1 to count
    if(node.getData().getPrimaryType() == chugiType ||
        node.getData().getSecondaryType() == chugiType) {
      count++;
    }
    //recursive case: go left 
    if(node.getLeft()!= null) {
      count += countTypeHelper(chugiType, node.getLeft());
    }
    //recursive case: go right
    if(node.getRight()!= null) {
      count += countTypeHelper(chugiType, node.getRight());
    }
    //base case
    if(node.getLeft() == null && node.getRight() == null) {
      return count;
    }
    return count; //return number of chugimon objects with this chugitype
    
  }
  /**
   * Finds and returns the in-order successor of a specified Chugimon in this
   * ChugiTree
   * 
   * @param chugi the Chugimon to find its successor
   * @return the in-order successor of a specified Chugimon in this ChugiTree
   * 
   * @throws IllegalArgumentException with a descriptive error message if
   *                                  <b>chugi</b> is null
   * @throws NoSuchElementException   with a descriptive error message if the
   *                                  Chugimon provided as input has no in-order
   *                                  successor in this ChugiTree.
   */
  @Override
  public Chugimon next(Chugimon chugi) {
    if(chugi == null) {
      throw new IllegalArgumentException("The chugimon to find the successor can't be null");
    }
    try {
      //create a new successor chugimon and if there's a NoSuchElementException caught then there's
      //no successor
      Chugimon successor = nextHelper(chugi,root,null);
      /*if(successor == null) {
        throw new NoSuchElementException("There is no in-order successor in this ChugiTree");
      }*/
      return successor;
    }catch(NoSuchElementException e) {
      throw new NoSuchElementException("There is no in-order successor in this ChugiTree");
    }
    
    //return successor;
  }

  /**
   * Recursive helper method to find and return the next Chugimon in the tree
   * rooted at node.
   * 
   * @param chugi a Chugimon to search its in-order successor. We assume that
   *              <b>chugi</b> is NOT
   *              null.
   * @param node  "root" of a subtree storing Chugimon objects
   * @param next  a BSTNode which stores a potentional candidate for next node
   * @return the next Chugimon in the tree rooted at node.
   * @throws NoSuchElementException with a descriptive error message if the
   *                                Chugimon provided as input has no in-order
   *                                successor in the subtree
   *                                rooted at node.
   */
  protected static Chugimon nextHelper(Chugimon chugi, BSTNode<Chugimon> node, BSTNode next) {
    // Hint: you will need to use getFirstHelper in this method. Below are
    // additional hints.

    // base case: node is null
    if(node == null) {
      throw new NoSuchElementException("There is no in-order successor in the subtree rooted at "
          + "this node");
    }
    // recursive cases:
    //chugi is less than node's chugimon
    if(chugi.compareTo(node.getData())<0) {
      next = node;
      return nextHelper(chugi,node.getLeft(),next);
    }
    //if chugi is greater than node's chugimon-go to right then recursively search right subtree
    if(chugi.compareTo(node.getData())> 0) {
      if(node.getRight()!=null) {
        //nextHelper(chugi, node.getRight(),next);
        node = node.getRight(); //go to the right then recurse left if get left isn't null
      }
      if(node.getLeft()!= null) {
        next = node;
        return nextHelper(chugi,node.getLeft(),next);
      }
    }
    //chugi is found and right child isn't null
    if(chugi.compareTo(node.getData()) == 0 && node.getRight()!= null) {
      return getFirstHelper(node.getRight());
    }
    //chugi is found but right child is null and next is not null 
    else if(chugi.compareTo(node.getData()) == 0 && node.getRight()== null && next != null){
      return (Chugimon) next.getData();
    }
    // base case: node is null
    else {
      throw new NoSuchElementException("There is no in-order successor in the subtree rooted at "
          + "this node");
    }
    //return null;
    // (1) if chugi is found and if the right child is not null, use getFirstHelper
    // to find and
    // return the next chugimon. It should be the left most child of the right
    // subtree
    
    // (2) if chugi is less than the Chugimon at node, set next as the root node and
    // search
    // recursively into the left subtree
    
  }

  /**
   * Finds and returns the in-order predecessor of a specified Chugimon in this
   * ChugiTree
   * 
   * @param chugi the Chugimon to find its predecessor
   * @return the in-order predecessor of a specified Chugimon in this ChugiTree.
   * 
   * @throws IllegalArgumentException with a descriptive error message if
   *                                  <b>chugi</b> is null
   * @throws NoSuchElementException   if there is no Chugimon directly before the
   *                                  provided Chugimon
   */
  @Override
  public Chugimon previous(Chugimon chugi) {
    if(chugi == null) {
      throw new IllegalArgumentException("The chugimon to find the predecessor can't be null");
    }
    //similar to next, create a previous chugimon then if a NoSuchElementException is caught
    //then there's no in-order predecessor
    try {
      Chugimon prev = previousHelper(chugi,root,null);
      return prev;
    }catch(NoSuchElementException e) {
      throw new NoSuchElementException("There is no in-order predecessor in this ChugiTree");
    }
    //return previousHelper(chugi, root, null);
  }

  /**
   * Recursive helper method to find and return the previous Chugimon in the tree
   * rooted at node.
   * 
   * @param chugi a Chugimon to search its in-order predecessor. We assume that
   *              <b>chugi</b> is NOT
   *              null.
   * @param node  "root" of a subtree storing Chugimon objects
   * @param prev  a BSTNode which stores a potentional candidate for previous node
   * @return the previous Chugimon in the tree rooted at node.
   * @throws NoSuchElementException with a descriptive error message if the
   *                                Chugimon provided as input has no in-order
   *                                predecessor in the subtree rooted at node.
   */
  protected static Chugimon previousHelper(Chugimon chugi, BSTNode<Chugimon> node,
      BSTNode<Chugimon> prev) {
    // TODO Implement this method.
    // Hint: you will need to use getLastHelper in this method. Below are more
    // hints.

    // base case: node is null

    // recursive cases:
    // (1) if chugi is found and if the left child is not null, use getLastHelper to
    // find and return
    // the previous chugimon. It should be the right most child of the left subtree

    // (2) if chugi is greater than the Chugimon at node, set prev as the root node
    // and search
    // recursively into the right subtree

    //return null;
    // base case: node is null
    if(node == null) {
      throw new NoSuchElementException("There is no in-order predecessor in the subtree rooted at "
          + "this node");
    }
    // recursive cases:
    //chugi is greater than node's chugimon
    if(chugi.compareTo(node.getData())>0) {
      prev = node;
      return previousHelper(chugi,node.getRight(),prev);
    }
    //if chugi is lesser than node's chugimon-go to left then recursively search left subtree
    if(chugi.compareTo(node.getData())<0) {
      if(node.getLeft()!=null) {
        //nextHelper(chugi, node.getRight(),next);
        node = node.getLeft();
      }
      if(node.getRight()!= null) {
        prev = node;
        return previousHelper(chugi,node.getRight(),prev);
      }
    }
    //chugi is found and left child isn't null
    if(chugi.compareTo(node.getData()) == 0 && node.getLeft()!= null) {
      return getLastHelper(node.getLeft());
    }
    //chugi is found but left child is null and prev is not null 
    else if(chugi.compareTo(node.getData()) == 0 && node.getLeft()== null && prev != null){
      return (Chugimon) prev.getData();
    }
    // base case: node is null
    else {
      throw new NoSuchElementException("There is no in-order predecessor in the subtree rooted at "
          + "this node");
    }
  }

  /**
   * Deletes a specific Chugimon from this ChugiTree.
   * 
   * @param chugi the Chugimon to delete
   * @return true if the specific Chugimon is successfully deleted, false if no
   *         match found with any
   *         Chugimon in this tree.
   * @throws IllegalArgumentException with a descriptive error message if
   *                                  <b>chugi</b> is null
   */
  @Override
  public boolean delete(Chugimon chugi) {
    try {
      boolean result = false; //result of if chugi was deleted
      //if chugi is null 
      if(chugi == null) {
        throw new IllegalArgumentException("The Chugimon to be deleted can't be null");
      }
      //otherwise try creating a new BST node for the chugimon to be deleted 
      BSTNode<Chugimon> del = deleteChugimonHelper(chugi,root);
      //special case: root is the target chugimon to be deleted
      if(chugi.equals(root.getData())) {
        root = new BSTNode<Chugimon>(del.getData(),root.getLeft(),root.getRight());
        if(lookupHelper(chugi, root) == null && isValidBSTHelper(root) == true) {
          result = true;
        }
      }
      size--;
     
      //check that the target chugimon was successfully deleted
      if(lookupHelper(chugi, root) == null && isValidBSTHelper(root) == true) {
        result = true;
      }
      
      return result; // default return statement
      
    }catch(NoSuchElementException e) {
      return false; //chugi couldn't be found
    }
  }

  /**
   * Recursive helper method to search and delete a specific Chugimon from the BST
   * rooted at node
   * 
   * @param target a reference to a Chugimon to delete from the BST rooted at
   *               node. We assume that target is NOT null.
   * @param node   "root" of the subtree we are checking whether it contains a
   *               match with the target Chugimon.
   * 
   * 
   * @return the new "root" of the subtree we are checking after trying to remove
   *         target
   * @throws NoSuchElementException with a descriptive error message if there is
   *                                no Chugimon matching target in the BST rooted
   *                                at <b>node</b>
   */
  protected static BSTNode<Chugimon> deleteChugimonHelper(Chugimon target, BSTNode<Chugimon> node) {
    // TODO complete the implementation of this method. Problem decomposition and
    // hints are provided in the comments below

    // if node == null (empty subtree rooted at node), no match found, throw an
    // exception
    if(node == null) {
      throw new NoSuchElementException("This is no Chugimon matching the target Chugimon to be del"
          + "eted in this BST");
    }

    // Compare the target to the data at node and proceed accordingly
    int compare = target.compareTo(node.getData());
    //if target is greater than root/current node go right
    if(compare>0) {
      node.setRight(deleteChugimonHelper(target, node.getRight()));
      
    }
    //if target is lesser than root/current node go left
    else if(compare<0) {
      node.setLeft(deleteChugimonHelper(target, node.getLeft()));
      
    }
    // Recurse on the left or right subtree with respect to the comparison result
    // Make sure to use the output of the recursive call to appropriately set the
    // left or the right child of node accordingly

    // if match with target found, three cases should be considered. Feel free to
    // organize the order of these cases at your choice.
    else if(compare == 0) {
      // Case 1: node may be a leaf (has no children), set node to null.
      if(node.getLeft() == null && node.getRight() == null){
        node = null;
        return node;
      }
      // Case 3: node may have two children,
      // Replace node with a new BSTNode whose data field value is the successor of
      // target in the tree, and having the same left and right children as node.
      // Notice carefully that you cannot set the data of a BSTNode. Hint: The
      // successor is the smallest element at the right subtree of node
      //
      // Then, remove the successor from the right subtree. The successor must have up
      // to one child.

      else if(node.getLeft() != null && node.getRight() != null) {
         BSTNode<Chugimon> successor = new BSTNode<Chugimon>(getFirstHelper(node.getRight()));
         removeSuccessorHelper(successor.getData(),node.getRight(),node);
         //case 3.5 successor isn't a leaf
         if(successor.getRight()!= null) {
            node.getRight().setLeft(successor.getRight());
         }
         node = successor;
      }
      // Case 2: node may have only one child, set node to that child (whether left or
      // right child)
      else{
        if(node.getLeft() != null) {
          node = node.getLeft();
          return node;
        }
        else {
          node = node.getRight();
          return node;
        }
      }
      
    }

    // Make sure to return node (the new root to this subtree) at the end of each
    // case or at the end of the method.
    return node;

  }
  private static void removeSuccessorHelper(Chugimon successorValue, BSTNode<Chugimon> node, 
      BSTNode<Chugimon> prev) {
    if(successorValue.compareTo(node.getData()) == 0) {
      //has right child
      if(node.getRight()!= null) {
        if(prev.getRight() == node) {
          prev.setRight(node.getRight());
          return;
        }
        if(prev.getLeft() == node) {
          prev.setLeft(node.getRight());
          return;
        }
      }
      //is a leaf
      if(node.getLeft() == null && node.getRight() == null) {
        if(prev.getRight() == node) {
          prev.setRight(null);
          return;
        }
        if(prev.getLeft() == node) {
          prev.setLeft(null);
          return;
        }
      }
    }
    removeSuccessorHelper(successorValue, node.getLeft(), node);
    
  }

}
