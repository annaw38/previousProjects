//////////////// P09 Chugidex//////////////////////////
// Title:    P09 Chugimon
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
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////
/**
 * This class models the Chugimon data type.
 *
 */
public class Chugimon implements Comparable<Chugimon>{
  //data fields
  public static final int MIN_ID = ChugidexUtility.MIN_CHUGI_ID; //The minimum ID number
  public static final int MAX_ID = ChugidexUtility.MAX_CHUGI_ID;//The maximum ID number
  private final String NAME; //The name of the Chugimon
  private final int FIRST_ID; //The first ID of the Chugimon
  private final int SECOND_ID; //The second ID of the Chugimon
  private final ChugiType PRIMARY_TYPE; //The primary type of the Chugimon; cannot be null; cannot 
  //be the same as the secondary type
  private final ChugiType SECONDARY_TYPE; //The secondary type of the Chugimon; may be null; cannot 
  //be the same as the primary type
  private final double HEIGHT; //The height of the Chugimon in meters
  private final double WEIGHT; //The weight of the Chugimon in kilograms
  
  //constructor
  /**
   * Creates a new Chugimon with specific first and second IDs and initializes all its data fields 
   * accordingly.
   * @param firstID  the first ID of the Chugimon, between 1-151
   * @param secondID the second ID of the Chugimon, between 1-151
   * @throws IllegalArgumentException - if the first and second ID are out of bounds or equal to 
   * each other.
   */
  public Chugimon(int firstID, int secondID) {
    if(firstID == secondID) {
      throw new IllegalArgumentException("The firstID can't equal the secondID");
    }
    //if chugimon's IDs aren't in range of the max and min ids
    if(firstID > MAX_ID || firstID < MIN_ID|| secondID > MAX_ID || secondID < MIN_ID) {
      throw new IllegalArgumentException("The firstID and secondID should be within the range of "
          + "1-151");
    } 
    this.FIRST_ID = firstID;
    this.SECOND_ID = secondID;
    this.NAME = ChugidexUtility.getChugimonName(firstID, secondID);
    this.HEIGHT = ChugidexUtility.getChugimonHeight(firstID, secondID);
    this.WEIGHT = ChugidexUtility.getChugimonWeight(firstID, secondID);
    ChugiType[] chugi = ChugidexUtility.getChugimonTypes(firstID, secondID);
    this.PRIMARY_TYPE = chugi[0];
    //if chugi doesn't have a secondary type then set it to null 
    if(chugi[1] == null) {
      this.SECONDARY_TYPE = null;
    }
    //otherwise set it to the secondary type
    else {
      this.SECONDARY_TYPE = chugi[1];
    }   
  }
  /**
   * Gets the name of this Chugimon
   * @return the name of the Chugimon
   */
  public String getName() {
    return this.NAME;
  }
  /**
   * Gets the first ID of this Chugimon
   * @return the first ID of the Chugimon
   */
  public int getFirstID() {
    return this.FIRST_ID;
    
  }
  /**
   * Gets the second ID of thid Chugimon
   * @return the second ID of the Chugimon
   */
  public int getSecondID() {
    return this.SECOND_ID;
  }
  /**
   *  Gets the primary type of this Chugimon
   * @return the primary type of the Chugimon
   */
  public ChugiType getPrimaryType() {
    return this.PRIMARY_TYPE;
  }
  /**
   * Gets the secondary type of this Chugimon
   * @return the secondary type of the Chugimon
   */
  public ChugiType getSecondaryType() {
    return this.SECONDARY_TYPE;
    
  }
  /**
   * Gets the height of this Chugimon
   * @return the height of the Chugimon
   */
  public double getHeight() {
    return this.HEIGHT;
    
  }
  /**
   * Gets the the weight of the Chugimon.
   * @return the weight of the Chugimon.
   */
  public double getWeight() {
    return this.WEIGHT;
  }
  /**
   * Determines the ordering of Chugimon. Chugimon are ordered by: 1) name (alphabetical) 2) the 
   * first ID (if name is equal). The one with the smaller first ID is less than the other. 3) the 
   * second ID (if name and first ID are equal). The one with the smaller second ID is less than the 
   * other. A Chugimon with identical #1-3 are considered equal.
   * @param otherChugi - the other Chugimon to compare this Chugimon to.
   * @return a negative int if this Chugimon is less than other, a positive int if this Chugimon is 
   * greater than other, or 0 if this and the other Chugimon are equal.
   */
  @Override
  public int compareTo(Chugimon otherChugi) {
    int chugiValue = 0;
    //if name, firstID, and secondID all equal
    if(this.NAME.equals(otherChugi.getName()) && this.FIRST_ID == otherChugi.getFirstID() && 
        this.SECOND_ID == otherChugi.getSecondID()) {
      return chugiValue;
    }
    //if this chugimon's name comes alphabetically before the other chugimon's name
    else if(this.NAME.compareTo(otherChugi.getName())<0) {
      chugiValue = this.NAME.compareTo(otherChugi.getName());
    }
    //if this chugimon's name comes alphabetically after the other chugimon's name
    else if(this.NAME.compareTo(otherChugi.getName())>0) {
      chugiValue = this.NAME.compareTo(otherChugi.getName());
    }
    //if this chugimon's firstID is greater than the other chugimon's firstID, with the name equal
    else if(this.NAME.equals(otherChugi.getName())&& this.FIRST_ID > otherChugi.getFirstID()) {
      chugiValue = this.FIRST_ID - otherChugi.getFirstID();
    }
    //if this chugimon's firstID is less than the other chugimon's firstID, with the name equal
    else if(this.NAME.equals(otherChugi.getName())&& this.FIRST_ID < otherChugi.getFirstID()) {
      chugiValue = this.FIRST_ID - otherChugi.getFirstID();
    }
    //if this chugimon's secondID is greater than the other chugimon's secondID, name and firstID 
    //equal
    else if(this.NAME.equals(otherChugi.getName())&& this.FIRST_ID == otherChugi.getFirstID() && 
        this.SECOND_ID > otherChugi.getSecondID()) {
      chugiValue = this.SECOND_ID - otherChugi.getSecondID();
    }
    //if this chugimon's secondID is less than the other chugimon's secondID, name and firstID equal
    else if(this.NAME.equals(otherChugi.getName())&& this.FIRST_ID == otherChugi.getFirstID() && 
        this.SECOND_ID < otherChugi.getSecondID()) {
      chugiValue = this.SECOND_ID - otherChugi.getSecondID();
    }
    return chugiValue;
  }
  /**
   * A Chugimon's String representation is its name followed by "#FIRST_ID.SECOND_ID" -- Example: 
   * "Zapchu#145.25"
   * @return a String representation of this Chugimon
   * 
   */
  @Override
  public String toString() {
    //create a new string to hold the chugimon's string representation
    String chugimonRep = "";
    //then add the format of the chugimon's string representation
    chugimonRep += this.NAME +"#" + this.FIRST_ID + "." + this.SECOND_ID;
    return chugimonRep;
  }
  /**
   * Equals method for Chugimon. This Chugimon equals another object if other is a Chugimon with 
   * the exact same name, and their both first and second IDs match.
   * @param other - Object to determine equality against this Chugimon
   * @return true if this Chugimon and other Object are equal, false otherwise
   * 
   */
  @Override
  public boolean equals(Object other) {
    //if other is an instance of chugimon
    if(other instanceof Chugimon) {
      //then check if the name, firstID, and secondID are the same
      if(this.NAME.equals(((Chugimon) other).getName()) && 
          this.FIRST_ID == ((Chugimon) other).getFirstID() && 
          this.SECOND_ID == ((Chugimon) other).getSecondID()) {
        return true;
      }
    }
    return false;
  }
  
}
