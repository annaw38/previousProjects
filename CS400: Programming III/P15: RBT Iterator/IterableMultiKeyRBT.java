// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: <your group's name: C20
// TA: Manas Trivedi
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
/**
 * This class is a RedBlackTree that is iterated over with a iterator and helper stack.
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T>{
  Comparable<T> startingPoint = null;// the iteration startPoint
  int numKeys = 0;// number of keys
  /**
   * Inserts value into tree that can store multiple objects per key by keeping
   * lists of objects in each node of the tree.
   * @param key object to insert
   * @return true if obj was inserted
   */
  @Override
  public boolean insertSingleKey(T key) {
    //if the key is null throw IllegalArgumentException
    if(key == null) {
      throw new IllegalArgumentException("The key can't be null.");
    }
    //create keyList with new key 
    KeyList<T> keyList = new KeyList<T>(key);
    numKeys++;//increase number of keys by 1
    //check if the tree already contains a node with duplicates
    //if the tree already contains a node with duplicates add the key to the KeyList in the node
    if(findNode(keyList) != null) {
      findNode(keyList).data.addKey(key);
      return false;
    }
    //otherwise insert the new KeyList into a new node of the tree
    insert(keyList);
    return true;
  }

  /**
   * Returns the number of values in the tree.
   * @return the number of values in the tree.
   */
  @Override
  public int numKeys() {
    return numKeys;
  }
  
  /**
   * Returns an iterator over the objects stored in the list.
   * @returns the iterator object
   */
  @Override
  public Iterator<T> iterator() {
    Stack<Node<KeyListInterface<T>>> stack = getStartStack(); //get start stack
    
    //anonymous class iterator to iterate over the nodes in the RBT
    Iterator<T> it = new Iterator<T>() {
      /**
       * Checks if there is another node in the stack
       */
      @Override
      public boolean hasNext() {
        //check if the first element in the stack is null or not
        try {
          Node<KeyListInterface<T>> peekStack = stack.peek(); 
          return peekStack != null; //there is still at least 1 node in the stack
        }catch(EmptyStackException e) {
          return false; //there are no more nodes in the stack
        }
        //return true;
      }
      
      Iterator<T> iter = null; //iterator for keyList in current node
      
      /**
       * Gets the next node and pushes the current node's right child and its left child on the stack (if they exist)
       */
      @Override
      public T next() {
        //if hasNext is false then throw exception that there are no more nodes in the stack
        if(hasNext() == false) {
          throw new NoSuchElementException("There are no more nodes.");
        }
        //set current node to be the first node in stack
        Node<KeyListInterface<T>> current = stack.pop();
        //if the keyList iterator of the last node/beginning iterator is null or does not have any more keys set it to the current node's iterator
        if(iter == null || iter.hasNext() == false) {
          //set iter as the current node's KeyList iterator
          iter = current.data.iterator(); 
          //iter = stack.pop().data.iterator();
          //if current has a right child 
          if(current.down[1] != null) {
            stack.push(current.down[1]); //then push it onto the stack
            //if current's right child has a left child
            if(current.down[1].down[0] != null) {
              stack.push(current.down[1].down[0]); //then push it onto the stack as well
            }
          }
        }
        return iter.next(); //return the next key in the keylist
      }
    };
    return it; //return the iterator 
  }
  /**
   * Helper method that returns the start stack for iterator
   * @return the start stack for iterator
   */
  protected Stack<Node<KeyListInterface<T>>> getStartStack() {
    Stack<Node<KeyListInterface<T>>> stack = new Stack<Node<KeyListInterface<T>>>(); //Initialize the stack
    //if root is null then return the empty stack because it is an empty tree
    if(root == null) {
      return stack;
    }
    //Iterator<T> it = root.data.iterator();
    //if the iteration start point is set - the stack is initialized with all the nodes with keys equal to or larger than the start point 
    if(startingPoint != null) {
      //set root as the current
      Node<KeyListInterface<T>> current = root;
      //int compare = startingPoint.compareTo(current.data.iterator().next());
      while(true && current != null){
        //compare is negative if the starting point is smaller than current node's value and positive if starting point is larger than current node's value
        int compare = startingPoint.compareTo(current.data.iterator().next());
        //check if compare is negative
        if(compare <= 0) {
          //find if node exists then push the path of larger values from the root to node on stack
          stack.push(current);
          //break when they are equal because it found the node with startingPoint
          if(compare == 0) {
            break;
          }
          //set current as the left child
          current = current.down[0];
        }
        else {
          //otherwise set current as the left child
          current = current.down[1];
        }
      }
    }
    //else the starting point is null - on the path from root to (and including node) with the smallest key in the tree
    else {
      //push the root onto the stack
      stack.push(root);
      //create a new node to hold the next node in the path 
      Node<KeyListInterface<T>> next = root.down[0];
      while(next != null) {
          stack.push(next); //push the next node onto the stack
          next = next.down[0]; //set next as the left child of the previous node
      }
    }
    return stack;
    
  }
  
  /**
   * Sets the starting point for iterations. Future iterations will start at the
   * starting point or the key closest to it in the tree. This setting is remembered
   * until it is reset. Passing in null disables the starting point.
   * @param startPoint the start point to set for iterations
   */
  @Override
  public void setIterationStartPoint(Comparable<T> startPoint) {
    startingPoint = startPoint; //set startingPoint as startPoint
  }
  /**
   * Removes all keys from the tree.
   */
  @Override
  public void clear() {
    super.clear(); 
    numKeys = 0;
  }
  
  
  /**
   * Test case 1: Test inserting a single key into the tree then inserting 3 more keys and clear the tree
   */
  @Test
  public void testInsertion() {
    //create new tree and try inserting a key
    IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();
    tree.insertSingleKey(10);
    //test that the number of keys and size is 1
    Assertions.assertEquals(1, tree.numKeys());
    Assertions.assertEquals(1, tree.size());
    //test inserting 3 more keys
    tree.insertSingleKey(35);
    tree.insertSingleKey(86);
    tree.insertSingleKey(75);
    
    //test that the number of keys and size is 4
    Assertions.assertEquals(4, tree.numKeys());
    Assertions.assertEquals(4, tree.size());
    
    //test that clear works as intended
    tree.clear();
    Assertions.assertEquals(0, tree.size());
    Assertions.assertEquals(0, tree.numKeys());
  }
  /**
   * Test case 2: Test adding duplicates into the tree and check that it maintains a size of 2 but has 3 keys
   */
  @Test
  public void testAddDuplicates() {
    //create new tree
    IterableMultiKeyRBT<Integer> tree1 = new IterableMultiKeyRBT<>();
    //test inserting duplicates so that it ends up being (10, 10)
    //                                                       \
    //                                                       (25)
    tree1.insertSingleKey(10);
    tree1.insertSingleKey(25);
    tree1.insertSingleKey(10);
    //create reference variables for the nodes' iterators
    Iterator<Integer> it = tree1.root.data.iterator();
    Iterator<Integer> it2 = tree1.root.down[1].data.iterator();
    //check that the nodes' values match what's expected
    Assertions.assertEquals(10, it.next());
    Assertions.assertEquals(10, it.next());
    Assertions.assertEquals(25, it2.next());
    //check that the size remains 2 but the number of keys is 3 with the duplicate
    Assertions.assertEquals(2, tree1.size);
    Assertions.assertEquals(3, tree1.numKeys);
  }
  /**
   * Test case 3: checking that iterator works and that next and hasNext works
   */
  @Test
  public void testIterator() {
    //create a new tree and insert 3 keys
    IterableMultiKeyRBT<Integer> tree2 = new IterableMultiKeyRBT<>();
    tree2.insertSingleKey(10);
    tree2.insertSingleKey(35);
    tree2.insertSingleKey(135);
    //initialize iterator
    Iterator<Integer> it1 = tree2.iterator();
    int count = 0; //count the number of exceptions caught
   
    //check that it matches the expected {10, 35, 135} 
    Assertions.assertEquals(10, it1.next());
    Assertions.assertTrue(it1.hasNext());
    Assertions.assertEquals(35, it1.next());
    Assertions.assertTrue(it1.hasNext());
    Assertions.assertEquals(135, it1.next());
    //check that hasNext returns false when there's no more nodes in the stack
    Assertions.assertEquals(false, it1.hasNext());
    //check that next throws an exception when there's no more nodes in the stack
    try {
      it1.next();
    }
    catch(NoSuchElementException e){
      count++;
    }
    Assertions.assertEquals(1, count); //only 1 exception should have been caught
  }
  
  /**
   * Test case 4: Testing the iterator with a start point larger than all the keys in the tree
   */
  @Test
  public void testIteratorNonValidStartPoint() {
    //create a new tree
    IterableMultiKeyRBT<Integer> tree4 = new IterableMultiKeyRBT<>();
    //insert 4 keys
    tree4.insertSingleKey(10);
    tree4.insertSingleKey(20);
    tree4.insertSingleKey(30);
    tree4.insertSingleKey(30);
    //set starting point to 50, greater than all keys in tree
    tree4.setIterationStartPoint(50);
    Iterator<Integer> it4 = tree4.iterator();
    //check that hasNext is false
    Assertions.assertEquals(false, it4.hasNext());
    
    //Assertions.fail("Not implemented");
  }
  /**
   * Test case 5: Testing the iterator with duplicates
   */
  @Test
  public void testIteratorDuplicates() {
    //create new tree
    IterableMultiKeyRBT<Integer> tree5 = new IterableMultiKeyRBT<>();
    //insert 3 duplicate keys
    tree5.insertSingleKey(10);
    tree5.insertSingleKey(10);
    tree5.insertSingleKey(10);
    //Iterator<Integer> it = tree5.iterator();
    //Iterator it1 = tree5.root.down[1].data.iterator();
    //check that size is 1 and keys is 3
    Assertions.assertEquals(1, tree5.size());
    Assertions.assertEquals(3, tree5.numKeys());
    //check that next matches 10 for all 3 
    //int count = 0;
    Iterator<Integer> iter = tree5.iterator();
    for (Integer key : tree5) {
        int expected = 10;
        Assertions.assertEquals(expected, iter.next());
    }
  }
  
  /**
   * Test case 6: checking that iterator's startPoint, next, and hasNext works after setting the starting point as the root's right child
  */
 @Test
 public void testIteratorStartPointRightChild() {
   //create a new tree
   IterableMultiKeyRBT<Integer> tree4 = new IterableMultiKeyRBT<>();
   
   //in order [56, 30, 69, 15, 35, 65, 87, 60]
   //insert 8 keys 
   tree4.insertSingleKey(30);
   tree4.insertSingleKey(56);
   tree4.insertSingleKey(69);
   tree4.insertSingleKey(87);
   tree4.insertSingleKey(65);
   tree4.insertSingleKey(60);
   tree4.insertSingleKey(15);
   tree4.insertSingleKey(35);
   //initialize iterator and the staring point as the root's right child
   tree4.setIterationStartPoint(69);
   Iterator<Integer> it4 = tree4.iterator();
   int count = 0;//number of exceptions caught
   
   //expected next is 69
   Assertions.assertTrue(it4.hasNext());
   Assertions.assertEquals(69, it4.next());
   
   //expected next is 87
   Assertions.assertTrue(it4.hasNext());
   Assertions.assertEquals(87, it4.next());
   
   //expected next is nothing
   Assertions.assertEquals(false, it4.hasNext());
   try {
     it4.next();
   }
   catch(NoSuchElementException e){
     count++;
   }
   Assertions.assertEquals(1, count); //only 1 exception should have been caught
   
  
 }
 /**
  * Test case 7: checking that iterator's startPoint, next, and hasNext works after setting the starting point as the root's left child
  */
 @Test
 public void testIteratorStartPointLeftChild() {
   //create a new tree
   IterableMultiKeyRBT<Integer> tree6 = new IterableMultiKeyRBT<>();
   
   //insert 5 keys 
   tree6.insertSingleKey(10);
   tree6.insertSingleKey(35);
   tree6.insertSingleKey(135);
   tree6.insertSingleKey(124);
   tree6.insertSingleKey(214);
   
   int count = 0; //number of exceptions caught
   //initialize iterator and the staring point
   tree6.setIterationStartPoint(35);
   Iterator<Integer> it3 = tree6.iterator();
  
   
   //expected next is 35
   Assertions.assertTrue(it3.hasNext());
   Assertions.assertEquals(35, it3.next());
   //expected next is 124
   Assertions.assertTrue(it3.hasNext());
   Assertions.assertEquals(124, it3.next());
   //expected next is 135
   Assertions.assertTrue(it3.hasNext());
   Assertions.assertEquals(135, it3.next());
   //expected next is 214
   Assertions.assertTrue(it3.hasNext());
   Assertions.assertEquals(214, it3.next());
   
   
   //expected next is nothing
   Assertions.assertEquals(false, it3.hasNext());
   try {
     it3.next();
   }
   catch(NoSuchElementException e){
     count++;
   }
   Assertions.assertEquals(1, count); //only 1 exception should have been caught
 }
 /**
  * Test case 8: checking that iterator's startPoint, next, and hasNext works with null startPoint.
  */
 @Test
 public void testIteratorStartPointNull() {
 //create a new tree
   IterableMultiKeyRBT<Integer> tree8 = new IterableMultiKeyRBT<>();
   
   //insert 5 keys 
   tree8.insertSingleKey(10);
   tree8.insertSingleKey(35);
   tree8.insertSingleKey(55);
   tree8.insertSingleKey(75);
   tree8.insertSingleKey(135);
   
   Iterator<Integer> it8 = tree8.iterator(); //iterator for tree
   int count = 0;//number of exceptions caught
   
   //expected next is 10
   Assertions.assertTrue(it8.hasNext());
   Assertions.assertEquals(10, it8.next());
   //expected next is 35
   Assertions.assertTrue(it8.hasNext());
   Assertions.assertEquals(35, it8.next());
   //expected next is 55
   Assertions.assertTrue(it8.hasNext());
   Assertions.assertEquals(55, it8.next());
   //expected next is 75
   Assertions.assertTrue(it8.hasNext());
   Assertions.assertEquals(75, it8.next());
   //expected next is 135
   Assertions.assertTrue(it8.hasNext());
   Assertions.assertEquals(135, it8.next());
   //expected next is nothing
   Assertions.assertEquals(false, it8.hasNext());
   try {
     it8.next();
   }
   catch(NoSuchElementException e){
     count++;
   }
   Assertions.assertEquals(1, count); //only 1 exception should have been caught
   
   
 }
}
