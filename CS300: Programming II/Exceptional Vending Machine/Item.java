//////////////// P04 Exceptional Vending Machine  //////////////////////////
//
// Title:    Item in Exceptional Vending Machine 
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
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         
// Online Sources:  (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This class represents an item in the p04 vending machine
 * @author Anna
 *
 */
public class Item {
  private String description;
  private int expirationDate;
  
  /**
   * Constructor for Item in the po4 vending machine
   * @param description description of the item 
   * @param expirationDate expiration date of the item
   * @throws IllegalArgumentException when description or expirationDate is not a valid argument
   */
  public Item(String description, int expirationDate) throws IllegalArgumentException{
    this.description = description;
    this.expirationDate = expirationDate;
    if(expirationDate < 0) {
      throw new IllegalArgumentException("The expirationDate can not be negative");
    }
    if(description == null|| description.equals("\n")|| description.equals(" ") || 
        description.isBlank() == true) {
      throw new IllegalArgumentException("The description can not be blank or null.");
    }
  }
  /**
   * Gets the description of the Item
   * @return the description of the item
   */
  public String getDescription() {
    return description;
  }
  /**
   * Sets the description to the new description
   * @param description the new description of the item
   */
  public void setDescription(String description) {
    this.description = description;
  }
  /**
   * Get the expiration date of the item
   * @return the expiration date of the item
   */
  public int getExpirationDate() {
    return expirationDate;
  }
  
  /**
   * Returns a string representation of this item
   * @return the string representation of this item's description and expiration date
   */
  @Override
  public String toString() {
    return getDescription()+ ": " + getExpirationDate();
  }
    
  /**
   * Checks if the object passed in as the argument is equal to the item
   * @param other, another object to check if it is equal to this item
   * @return true if the two items are equal, and false if they aren't
   */
  @Override
  public boolean equals(Object other) {
    if(other instanceof Item) {
      Item otherItem = (Item) other;
      return this.description == otherItem.description;
    }
    return false;
  }
}
