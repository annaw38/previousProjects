//////////////// P07 Quizzer//////////////////////////
// Title:    P07 Linked Node
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
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////
/**
 * An instance of this class represents a single node within a singly linked list.
 *
 */
public class LinkedNode<T>{
  private T data; //data carried by this linked node
  private LinkedNode<T> next; //reference to the next linked node
  /**
   * Constructs a new node with the provided information.
   * @param data to be stored within this node
   * @param next node, which comes after this node in a singly linked list
   * @throws NullPointerException with a descriptive error message if data is null
   */
  public LinkedNode(T data, LinkedNode<T> next) {
    if(data == null) {
      throw new NullPointerException("The data can't be null");
    }
    this.data = data;
    this.next = next;
  }
  /**
   * Constructs a new node with a specific data and NO next node in the list.
   * @param data to be stored within this node
   * @throws NullPointerException with a descriptive error message if data is null
   */
  public LinkedNode(T data) {
    if(data == null) {
      throw new NullPointerException("The data can't be null");
    }
    this.data = data;
  }
  /**
   * Accessor method for this node's next node reference.
   * @return the next reference to the node that comes after this one in the list, or null when this
   *         is the last node in that list
   */
  public LinkedNode<T> getNext(){
    return this.next;
  }
  /**
   * Mutator method for this node's next node reference.
   * @param next node, which comes after this node in a doubly linked list
   */
  public void setNext(LinkedNode<T> next) {
    this.next = next;
  }
  /**
   * Accessor method for this node's data.
   * @return the data stored within this node.
   */
  public T getData() {
    return this.data;
  }
  
  /**
   * Returns a string representation of this linked node formatted as follows:
   *   data.toString() if this node does NOT have a next node in the list
   *   data.toString() + "->" if this node has a next node in the list
   * @Override toString in class Object
   * @return a String representation of this node in the list
   */
  @Override
  public String toString() {
    //if node doesn't have next node
    if(next == null) {
      return data.toString();
    }
    return data.toString() + "->";
  }
}