// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: G40
// TA: Zheyang Xiong 
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * A Hashtable map that is a hashtable that uses chaining to store pairs
 * @param <KeyType> the type of key to be stored in this hashtable map
 * @param <ValueType> the type of value to be stored in this hastable map
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{
  /**
   * A key and value pair to be stored in the hashmap table 
   */
  protected class Pair {

    public KeyType key;
    public ValueType value;

    public Pair(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }
  }
  
  private int capacity; // the capacity of the hashtable -- default capacity is 32
  private int size; //the number of pairs stored in the hashtable
  protected LinkedList<Pair>[] table; //the hashtable that stores the pairs
 
  /**
   * One of two constructors to create a new Hashtable map, this constructor allows the user
   * to specify the capacity of the hashtable 
   * @param capacity the capacity of the hashtable
   */
  @SuppressWarnings("unchecked")
  public HashtableMap(int capacity) {
    this.capacity = capacity;
    size = 0;
    table = (LinkedList<Pair>[]) new LinkedList[capacity];
  }
  
  /**
   * The second constructor that creates a new Hashtable map with the default capacity of 32
   */
  @SuppressWarnings("unchecked")
  public HashtableMap() {
    capacity = 32;
    size = 0;
    table = (LinkedList<Pair>[]) new LinkedList[32]; //uses 32 as capacity
  }

  /**
   * Adds a new key, value pair to the hashtable map
   * @param key the key of the key,value pair
   * @param value the value that key maps to
   * @throws IllegalArgumentException if key already maps to a value 
   * @throws NullPointerException if key is null
   */
  @Override
  public void put(KeyType key, ValueType value) throws IllegalArgumentException {
    //if the key is null throw new NullPointerException
    if(key == null) {
      throw new NullPointerException("The key cannot be null.");
    }
   
    Pair pair = new Pair(key, value); // create a new pair with the key and value to put into the table
    int index = Math.abs(key.hashCode())%capacity; //find the index to insert they key into by taking the absolute value of the key's hashcode mod capacity of the table
    //check if a linked list exists at the index the key will be inserted in 
    if(table[index] == null) {
      table[index] = new LinkedList<Pair>();
    }
    //loop through the linked list at index to see if the key is a duplicate
    for(int i = 0; i < table[index].size();i++) {
      //if they key is a duplicate throw an IllegalArgumentException explaining that duplicates aren't allowed
      if(table[index].get(i).key.equals(key)) {
        throw new IllegalArgumentException("No duplicates are allowed.");
      }
    }
    table[index].add(pair); //add the pair into at index
    size++; //increase size by 1
    
    double lf = (double)size/capacity; //loadfactor of the table = number of pairs in the table/capacity of the table
   
    //if lf >= 0.75 double the capacity and rehash all the pairs already in the table
    if(lf>=0.75) {
      //set table as the new table with double the capacity
      table = doubleCapacity();
      //rehash the pairs so they're in the correct index 
      rehashPairs();
    }
  }

  /**
   * Helper method to double the capacity of the hashtable
   * @return LinkedList<Pair>[] new table with doubled capacity
   */
  private LinkedList<Pair>[] doubleCapacity() {
    capacity = 2*capacity; //double the current capacity 
    @SuppressWarnings("unchecked")
    LinkedList<Pair>[] newTable = (LinkedList<Pair>[]) new LinkedList[capacity]; //create a new array of LinkedLists of pairs with the new capacity
    //put the pairs that were in the original table into the same spots in the new table
    for(int i = 0;i<table.length;i++) {
      //check if the linkedlist at table index i is null 
      if(table[i] != null) {
        //if not null in table, add a new linkedlist at the same index in the new table
        newTable[i] = new LinkedList<Pair>();
        //add all the pairs in the linkedlist in table to the new table's linkedlist
        for(int j = 0; j < table[i].size();j++) {
          newTable[i].add(table[i].get(j));
        }
      }
    }
    return newTable; //return the new resized table
  }
  
  /**
   * Helper method to rehash the pairs already in the table (assume doubleCapacity called before this)
   */
  private void rehashPairs() {
    //loop through all the linked lists to rehash all the pairs 
    for(int i = 0; i < capacity; i++) {
      //check if there is a linked list at index
      if(table[i] != null) {
        //if there is, loop through all the pairs in the linked list to rehash them
        for(int j = 0; j < table[i].size(); j++) {
          //get the key of the pair at index j in the linked list at index i 
          KeyType key = table[i].get(j).key;
          //find the index to be inserted into 
          int index = Math.abs(key.hashCode())%capacity;
          
          //check if the index is the same as the previous index
          if(index != i) {
            //make a new LinkedList at the index if there isn't one
            if(table[index] == null) {
              table[index] = new LinkedList<Pair>();
            }
            //otherwise add the pair to the linked list
            table[index].add(table[i].get(j));
            //then remove the pair from the old linkedlist
            table[i].remove(j);
          }
        }
      }    
    }
  }
  
  /**
   * Checks whether a key maps to a value in this collection.
   * @param key the key to check
   * @return true if the key maps to a value, and false otherwise
   */
  @Override
  public boolean containsKey(KeyType key) {
    int index = Math.abs(key.hashCode())%capacity; //find the index of the linked list the key would be at if it is in the table
    //if the linkedlist at index is empty then return false
    if(table[index] == null) {
      return false;
    }
    
    //loop through all the pairs in the linked list to see if key matches one of the pairs' key
    for(int j = 0; j < table[index].size(); j++) {
      //if the pair's key in a linked list in the hastable equals key then return true
      if(table[index].get(j).key.equals(key)) {
        return true;
      }
    }
    return false; //otherwise return false
  }

  /**
   * Retrieves the specific value that a key maps to.
   * @param key the key to look up
   * @return the value that key maps to
   * @throws NoSuchElementException when key is not stored in this
   *         hashtable Map
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    int index =  Math.abs(key.hashCode())%capacity; //find the index of the linked list the key would be at if it is in the table
    //if the linkedlist at index is empty then return false
    if(table[index] == null) {
      throw new NoSuchElementException("This key is not stored in this hashtable Map.");
    }
    
    //loop through all the pairs in the linked list to see if key matches one of the pairs' key
    for(int j = 0; j < table[index].size(); j++) {
      //if the pair's key in a linked list in the hastable equals key then return true
      if(table[index].get(j).key.equals(key)) {
        return table[index].get(j).value;
      }
    }
    
    //otherwise this key was not found
    throw new NoSuchElementException("This key is not stored in this hashtable Map.");
  }

  /**
   * Remove the mapping for a key from this collection.
   * @param key the key whose mapping to remove
   * @return the value that the removed key mapped to
   * @throws NoSuchElementException when key is not stored in this
   *         hashtable Map
   */
  @Override
  public ValueType remove(KeyType key) throws NoSuchElementException {
    int index = Math.abs(key.hashCode())%capacity; //find the index of the linked list the key would be at if it is in the table
    //if the linkedlist at index is empty then return false
    if(table[index] == null) {
      throw new NoSuchElementException("This key is not stored in this hashtable Map.");
    }
    
    ValueType returnValue = null;// the valueType of the value the removed key mapped to 
    //loop through all the pairs in the linked list to see if key matches one of the pairs' key
    for(int j = 0; j < table[index].size(); j++) {
      //if the pair's key in a linked list in the hastable equals key then return true
      if(table[index].get(j).key.equals(key)) {
        returnValue = table[index].get(j).value;
        table[index].remove(j);
        size--;
        return returnValue;
      }
    }
    //otherwise this key was not found
    throw new NoSuchElementException("This key is not stored in this hashtable Map.");
  }

  /**
   * Removes all key,value pairs from this collection.
   */
  @Override
  public void clear() {
    //loop through all the linked lists to clear all the pairs 
    for(int i = 0; i < capacity;i++) {
      if(table[i] != null) {
        //loop through all the pairs in the linked list to remove them
        for(int j = 0; j < table[i].size(); j++) {
          table[i].remove(j);
        }
      }    
    }
    size = 0;
  }

  /**
   * Retrieves the number of keys stored in this collection.
   * @return the number of keys stored in this collection
   */
  @Override
  public int getSize() {
    return size;
  }

  /**
   * Retrieves this collection's capacity.
   * @return the size of the underlying array for this collection
   */
  @Override
  public int getCapacity() {
    return capacity;
  }
  
  /**
   * Test 1: Test the put and getSize method
   */
  @Test
  public void testPutAndGetSize() {
    //create a new hastable map
    HashtableMap<Integer, String> map = new HashtableMap<>(8);
    //put 3 pairs into the map
    map.put(1, "1");
    map.put(2, "2");
    map.put(3, "3");
    
    //check that the size of the map is now 3 and the capacity remains 8
    Assertions.assertEquals(3, map.getSize());
    Assertions.assertEquals(8, map.getCapacity());
    
    int count = 0; //number of exceptions caught
    //try putting in a duplicate pair in the map, an IllegalArgumentException is expected to be caught
    try {
      map.put(3, "3");
    }catch(IllegalArgumentException e) {
      count++;
    }
    //try putting a pair with a null value in the map
    try {
      map.put(4, null);
    }
    catch(Exception e) {
      //an exception is not expected to be caught
      Assertions.fail("An exception is not expected to be caught");
    }
    //try putting in a pair with a null key in the map, a NullPointerException is expected to be caught
    try {
      map.put(null, "4");
    }
    catch(NullPointerException e) {
      count++;
    }
    //try putting in a pair with a null key and null value in the map, a NullPointerException is expected to be caught
    try {
      map.put(null, null);
    }
    catch(NullPointerException e) {
      count++;
    }
    Assertions.assertEquals(3, count); //check that the 3 expected exceptions were caught
    Assertions.assertEquals(4, map.getSize()); //check that the map now has 4 pairs in it
    Assertions.assertEquals(8, map.getCapacity());//check that the capacity is still 8
    
  }
  
  /**
   * Test 2: Test the containsKey method
   */
  @Test
  public void testContainsKey() {
    //create a new hastable map
    HashtableMap<String, String> map2 = new HashtableMap<>(8);
    //put 5 pairs into the map
    map2.put("a", "1");
    map2.put("b", "2");
    map2.put("c", "3");
    map2.put("d", "4");
    map2.put("e", "5");
    
    //check that the size of the map is now 3 and the capacity remains 8
    Assertions.assertEquals(5, map2.getSize());
    Assertions.assertEquals(8, map2.getCapacity());
    Assertions.assertEquals(true, map2.containsKey("e")); //check that the map contains key "e"
    Assertions.assertEquals(false, map2.containsKey("f")); //check that the map does not contain key "f"
    
    //insert a new key 
    map2.put("f", "5");
    //check that the map now contains "f" 
    Assertions.assertEquals(true, map2.containsKey("f"));
  }
  
  /**
   * Test 3: Test the remove method
   */
  @Test
  public void testRemove() {
    //create a new hastable map
    HashtableMap<String, String> map3 = new HashtableMap<>(8);
    //put 4 pairs into the map
    map3.put("a", "1");
    map3.put("b", "2");
    map3.put("c", "3");
    map3.put("d", "4");
    
    //remove a key in the map
    Assertions.assertEquals("1", map3.remove("a"));
    Assertions.assertEquals(3, map3.getSize()); //check that the size is decreased by 1 
   
    int count = 0; //the number of exceptions caught 
    
    //try removing a key not in the map
    try {
      map3.remove("f");
    }catch(NoSuchElementException e) {
      count++;
    }
    
    //try getting the value that a maps to after removing it
    try {
      map3.get("a");
    }catch(NoSuchElementException e) {
      count++;
    }
    Assertions.assertEquals(2, count);
  }
  
  /**
   * Test 4: Test the get and getCapacity methods
   */
  @Test
  public void testGetAndGetCapacity() {
    //create a new hastable map
    HashtableMap<String, String> map4 = new HashtableMap<>(8);
    //put 4 pairs into the map
    map4.put("a", "b");
    map4.put("b", "c");
    map4.put("c", "d");
    map4.put("d", "e");
    map4.put("e", "f");
    //check that the capacity is still 8, since the lf < 0.75
    Assertions.assertEquals(8, map4.getCapacity());
    Assertions.assertEquals(5, map4.getSize());
    Assertions.assertEquals("b", map4.get("a"));
    
    int count = 0;
    //try get(key) with invalid input of "A" instead of "a" 
    try {
      map4.get("A");
    }catch(NoSuchElementException e) {
      count++;
    }
    //try get(key) with invalid input of "f" because it isn't in the map yet
    try {
      map4.get("f");
    }catch(NoSuchElementException e) {
      count++;
    }
    
    //insert 2 more pairs 
    map4.put("f", "g");
    map4.put("g", "h");
    
    //check that the capacity doubled since the lf>0.75
    Assertions.assertEquals(16, map4.getCapacity());
    //check that the size increased by 2 after inserting 2 more pairs
    Assertions.assertEquals(7, map4.getSize());
    //check that f exists in the map now
    Assertions.assertEquals("g", map4.get("f"));
    Assertions.assertEquals(2, count); //check that the 2 expected exceptions were caught
    
    
  }
  
  /**
   * Test 5: Test the clear method
   */
  @Test
  public void testClear() {
    //create a new hastable map
    HashtableMap<String, String> map5 = new HashtableMap<>(10);
    //put 4 pairs into the map
    map5.put("x", "1");
    map5.put("y", "2");
    map5.put("z", "3");
    map5.put("a", "4");
    
    //test the clear method
    map5.clear();
    
    Assertions.assertEquals(0, map5.getSize()); //the map size should be 0
    Assertions.assertEquals(false, map5.containsKey("x"));//the key x should not be in the map anymore
    Assertions.assertEquals(false, map5.containsKey("y"));//the key y should not be in the map anymore
    Assertions.assertEquals(false, map5.containsKey("z"));//the key z should not be in the map anymore
    Assertions.assertEquals(false, map5.containsKey("a"));//the key a should not be in the map anymore
    Assertions.assertEquals(10, map5.getCapacity()); //the capacity of the map should still be 10
    
  }
  
  /**
   * Test 6: Check the pairs correctly 
   */
  @Test
  public void testRehash() {
    HashtableMap<String, String> map6 = new HashtableMap<>(4);
    map6.put("d", "4");
    
    //check that d is inserted into the linked list at index 0 initially 
    Assertions.assertEquals("d", map6.table[0].get(0).key);
    map6.put("c", "53");
    //check that c is inserted into the linked list at index 3 initially 
    Assertions.assertEquals("c", map6.table[3].get(0).key);
    map6.put("b", "128"); 
    //check that b is inserted into the linked list at index 2 initially 
    Assertions.assertEquals("b", map6.table[2].get(0).key);
    
    //check that the capacity doubled since the load factor == 0.75
    Assertions.assertEquals(8, map6.getCapacity());
    Assertions.assertEquals(3, map6.getSize());
    
    map6.put("j", "32");
    //check that j is inserted into the linked list at index 2 in the larger table
    Assertions.assertEquals("j", map6.table[2].get(1).key);
    
    //check that the other pairs that were also in the table match their expected indicies
    Assertions.assertEquals("d", map6.table[4].get(0).key);
    Assertions.assertEquals("c", map6.table[3].get(0).key);
    
    //check that both keys b and j are in the linked list at index 2 
    Assertions.assertEquals(2, map6.table[2].size());
    Assertions.assertEquals("b", map6.table[2].get(0).key);
    Assertions.assertEquals("j", map6.table[2].get(1).key);

    //add 2 more keys so the load factor is >=0.75 
    map6.put("k", "234");
    //check that the k is inserted at index 3 
    Assertions.assertEquals("k", map6.table[3].get(1).key);
    //check that the capacity remains at 8
    Assertions.assertEquals(8, map6.getCapacity());
    Assertions.assertEquals(5, map6.getSize());
    
    map6.put("l", null);
    
    //check that the capacity doubled to 16 since the lf >=0.75
    Assertions.assertEquals(16, map6.getCapacity());
    Assertions.assertEquals(6, map6.getSize());
    //check that the l is at index 12 in the doubled capacity table 
    Assertions.assertEquals("l", map6.table[12].get(0).key);
    //check the rehash of each pair and that it matches their expected index
    Assertions.assertEquals("b", map6.table[2].get(0).key);
    Assertions.assertEquals("c", map6.table[3].get(0).key);
    Assertions.assertEquals("d", map6.table[4].get(0).key);
    Assertions.assertEquals("j", map6.table[10].get(0).key);
    Assertions.assertEquals("k", map6.table[11].get(0).key);
  }
}