import java.io.FileNotFoundException;
import java.util.ArrayList;



/**
 * An interface for the backend that reads data from a file, gets a list of movies with the minimum 
 * duration, and gets a list of movies with duration between two specified thresholds.
 * @author Anna Wang
 */

public interface IndividualBackendInterface<T extends Comparable<T>> extends SortedCollectionInterface<KeyListInterface<T>>, Iterable<T>, IterableMultiKeySortedCollectionInterface<T> {
  

  /**
   * Read in the data from a csv file using a scanner and put it into an ArrayList 
   * @param filepath filepath of the csv file
   * @return ArrayList<String> an ArrayList of all the movies and their properties in the dataset
   * @throws FileNotFoundException if the filepath is null or if the file could not be found 
   */
  public ArrayList<String> readFile(String filepath) throws FileNotFoundException;
  /*
   * check if the file can be found, if not throw a FileNotFoundException
   * read in with scanner
   * put all the movies in an arrayList
   * 
   */
  
  
 
  
  /*
   * Edge cases: 
   * case 1: if the movie with the minimum duration is the root because it is the only node in the tree 
   * or because it only has right children 
   * --> return the root
   * 
   * case 2: if multiple movies have the same duration as the minimum duration
   * --> return all the movies
   * 
   * case 3: if the tree is empty
   * --> return an empty ArrayList
   */
  /**
   * Find the movie(s) with the minimum duration in the dataset. 
   * @return ArrayList<String> with all the movie(s) with the minimum duration
   */
  public ArrayList<String> findMinDuration();
  /*
   * Find the most left child in the tree and use that node's duration as the min duration then
   * traverse the left half of the tree to check if any other movies have the same duration
   * If they have the same duration then add them to the ArrayList, otherwise return the ArrayList
   */
  
  
 

  /*
   * Edge cases:
   * 
   * case 1: if the minTime and maxTime are the same 
   * --> return movie(s) with that specific duration (if there are any)
   * 
   * case 2: if the maxTime is smaller than the minTime or the minTime is larger than the maxTime 
   * --> throw an IllegalArgumentExeption() that specifies the problem 
   * 
   * case 3: if the minTime and maxTime are both larger or smaller than the movie in the dataset's duration or if the tree is empty
   * --> return an empty ArrayList
   * 
   */
  /**
   * Get a list of movies with a duration between 2 specified thresholds
   * @param minTime the lower duration of the threshold 
   * @param maxTime the upper duration of the threshold 
   * @return ArrayList<String> the list of the movie(s) that has a duration between the 2 thresholds
   * @throws IllegalArgumentException if the minTime or maxTime is null
   */
  public ArrayList<String> findSpecificDuration(int minTime, int maxTime) throws IllegalArgumentException;
    /*
     * Find the movie that has the same duration minTime, and if they don't have the same time then 
     * the closest to minTime and add it to the arrayList
     * Then check if the movies around it its parent and its sibling(if it has a parent and sibling) are within the threshold 
     * and add them if they are within the threshold.
     * While checking if these movies are within the threshold, check if it is equal to or smaller than the maxTime.
     * If it is equal to or the movie with the largest duration that is still under the maxTime, add it to the ArrayList
     * then return the ArrayList
     */
  
  
}