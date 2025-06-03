// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: <your group's name: C20
// TA: Manas Trivedi
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.io.FileNotFoundException;

/**
 * An interface for a class that implements the functionality of the backend properties for the Movie Timer app
 */
public interface BackendInterface<T> {
  /*
   * Backend Constructor 
   * public BackendInterface(IterableMultiKeySortedCollectionInterface multiKey){
   * multiKey = this.multiKey
   * }
   */
    /**
    * Read in the data from a csv file using a scanner and put it into an array
    * @param filepath filepath of the csv file
    * @return Array of all the movies and their properties in the dataset
    * @throws FileNotFoundException if the filepath is null or if the file could not be found 
    */
    public T[] fileReader(String filepath) throws FileNotFoundException;


    /**
     * Find the movie(s) with the minimum duration in the dataset. 
     * @return Array with all the movie(s) with the minimum duration
     */
    public T[] withMinDuration();

    /**
     * Get a list of movies with a duration between 2 specified thresholds
     * @param minTime the lower duration of the threshold 
     * @param maxTime the upper duration of the threshold 
     * @return Array of the list of the movie(s) that has a duration between the 2 thresholds
     * @throws IllegalArgumentException if the minTime or maxTime is null
     */
    public T[] betweenDuration(int minTime, int maxTime) throws IllegalArgumentException;
} 