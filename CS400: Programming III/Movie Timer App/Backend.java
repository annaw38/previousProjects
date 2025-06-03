// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: <your group's name: C20
// TA: Manas Trivedi
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * Backend class of the Movie Timer app that implements the BackendInterface
 *
 */
public class Backend implements BackendInterface<Movie> {
  //data field for iterableMultiKeyRBT
  //private IterableMultiKeySortedCollectionInterfacePlaceholder multiKey;
 // private IterableMultiKeyRBT<Movie> multiKey;
  private IterableMultiKeySortedCollectionInterface<Movie> multiKey;
  /**
   * Creates a new Backend that takes in an implementation of IterableMultiKeySortedCollectionInterfacePlaceholder
   * @param multiKey an implementation of IterableMultiKeySortedCollectionInterfacePlaceholder
   */
  /*Backend(IterableMultiKeyRBT<Movie> iterableMultiKey){
    multiKey = iterableMultiKey;
  }*/
  Backend(IterableMultiKeySortedCollectionInterface<Movie> multiKey){
    this.multiKey = multiKey;
  }
  
  /**
   * Reads a csv file into an array 
   * @param filepath the File object to be read 
   * @return an array with all the objects in the file 
   * @throws FileNotFoundException if the file cannot be loaded
   */
  @Override
  public Movie[] fileReader(String filepath) throws FileNotFoundException {
    //Create a new ArrayList of movies in file    
    ArrayList<Movie> movies = new ArrayList<Movie>();
    Movie[] movieList;
    String[] parts = new String[6]; //create a new array to hold the object's attributes
    //filepath = new File(".").getAbsolutePath() + filepath; debug
    //filepath = filepath.replace("\\", "\\\\"); debug 
    
    //if the filepath is null throw FileNotFoundException
    if(filepath == null) {
      throw new FileNotFoundException("The filepath can not be null.");
    }
    Scanner fileReader = null; //scanner to read the csv
    
    try {
      //System.out.println(filepath); debug line
      //turn the filepath to a new file
      File file = new File(filepath);
      //System.out.println("reached end of new file");debug line
      //scanner to read from file
      fileReader = new Scanner(file);
      //System.out.println("reached end of new scanner"); debug line
     
      String nextLine = fileReader.nextLine();//skip the header
      //read line by line until none left
      while(fileReader.hasNext()) {
        nextLine = fileReader.nextLine();//get the next Line in the file
        
        //call the helper method to split the line into the attributes
        parts = splitNextLine(nextLine, parts, 0, 0);
        //                          1          2            3           4             5
        //Movie constructor: (String title, String genre, int year, String country, int duration)
        //parts expected order: [filmtv_id,title,year,genre,duration,country]
        //                         0         1    2     3     4,       5      
        //create a new Movie based on the values in parts and add it to the ArrayList
        Movie newMovie = new Movie(parts[1], parts[3], Integer.parseInt(parts[2]), parts[5], Integer.parseInt(parts[4]));
        movies.add(newMovie);
        multiKey.insertSingleKey(newMovie);
      }
     
      //put the movies in the ArrayList into an array in the same size and order
      movieList = new Movie[movies.size()];
      for(int i = 0; i<movies.size();i++ ) {
        movieList[i] = movies.get(i);
      }
    }
    
    //if the load was unsuccessful
    catch(FileNotFoundException e) {
     throw new FileNotFoundException("This file could not be loaded.");
    }
    
    finally {
      if(fileReader != null)
        fileReader.close(); //close scanner regardless of what happened 
    }
    return movieList; //return the array of Movies
  }
  /**
   * Helper method for FileReader that identifies if a comma splits attributes or is part of an attribute
   * @param nextLine the nextLine in the file
   * @param parts the array of the object's properties 
   * @param splitingIndex current index of the array storing all the properties
   * @param commaLocation the index of the line to split the line at
   * @return an array with all of the object's properties
   */
  private String[] splitNextLine(String nextLine, String[] parts, int splitingIndex, int commaLocation) {
    String line = nextLine; //current String of Movie attributes
    String[] split = parts; //attributes of a Movie split into parts in an array
    int count = 0; //number of " before a comma
    int index = commaLocation; //index of splitting line starting with the next attribute as the first thing
    int splitIndex = splitingIndex; //index of split (the String array)
    int commaLoc = line.indexOf(",", index+1); //location of the first comma
    
    //while the index of the array is less than or equal 5 to get all the properties required to create
    //a new movie 
    while(splitIndex <= 5) {
      //count the number of " before a comma
      for(int i = 0; i < commaLoc; i++) {
       // if(nextLine.charAt(i) == ('\\"\\"')) {
        if(line.charAt(i) == ('\"')) {
          count++;
        }
      }
      
      //2 cases: even and odd 
      //if even: comma separates attributes
      if(count%2 == 0) {
        //put a substring of line into the index of the array that match the expected attribute
        //expected: 0 = film id, 1 = title, 2 = year, 3 = genre, 4 = duration, and 5 = country
        split[splitIndex] = line.substring(0, commaLoc);
        splitIndex++;
        //increase the index to split the line by 1 
        line = line.substring(commaLoc+1);
        //find the index of the next comma
        commaLoc = line.indexOf(",");
        //if there is nothing left in the string or the index of "," is -1 then break and return the
        //array
        if(commaLoc == -1) {
          break;
        }
       
      }
      //if odd: it is a comma within an attribute
      else {
        //use recursion to find the next comma to see if it is preceded by even or odd quotes
        splitNextLine(line, split, splitIndex, commaLoc); 
        break;// break when done 
      }
      
    }
    return split;//return the array with the attributes
    
  }

  
  /**
   * Finds the movie(s) with the minimum duration in the RBT 
   * @return an array with the movie(s) with the minimum duration
   */
  @Override
  public Movie[] withMinDuration() {
    //array to return that has the size of the number of keys in the tree 
    Movie[] min = new Movie[multiKey.numKeys()];
    
    //if tree is empty
    if(multiKey.isEmpty() || multiKey.size() == 0) {
      return null;
      //return min; //return an empty list 
    }
    //create a new IMKRBT iterator
    Iterator<Movie> it = multiKey.iterator();
    int duration; //smallest duration
    int i = 0; //index of array of min
    Movie m;
    
    //the tree has one or more elements 
    m = it.next(); //Movie m is the first element in the tree 
   
    duration = m.getDuration(); //set duration initially as the first element's duration
    //if the tree has 1 node and only 1 key within that node
    min[0] = m;//add that one element into the array 
    i++; //increase the index of the array
    
    //if the tree has 1 node and 1 key 
    if(multiKey.size() == 1 && multiKey.numKeys() == 1) {
      return min; //return the array with the smallest duration movie already in it 
    }
    //otherwise the key has 1 or more nodes and at least 1 key/movies
    else {
      m = it.next(); //get the next movie
      //while there are still more movies in the stack continue
      while(it.hasNext()) {
          //if there are more than 1 keys/movies with the same duration( the minimum duration)
          if(m.getDuration() == duration) {
            min[i] = m; //add it to the array
            i++; //increase index by 1
            m = it.next(); //get the next movie
          }
          //otherwise we have the minimum duration key(s)
          else {
            break;
          }
      }
    }
    return min; //return the resulting array
  }
  /**
   * Finds the movie(s) with a duration between minTime and maxTime in the RBT 
   * @param minTime the minimum duration
   * @param maxTime the maximum duration
   * @return an array with the movie(s) with a duration between minTime and maxTime
   * @throws IllegalArgumentException if the minTime or maxTime is null, minTime is larger than maxTime, or 
   * either minTime or maxTime is negative
   */
  @Override
  public Movie[] betweenDuration(int minTime, int maxTime) throws IllegalArgumentException {
    /*
     * Reasons to throw an IllegalArgumentException:
     *  -minTime or maxTime is null
     *  -minTime is larger than maxTime
     *  -minTime or maxTime is negative
     */
    if((Integer)minTime == null || (Integer)maxTime == null || minTime > maxTime || minTime < 0 || maxTime < 0) {
      throw new IllegalArgumentException("The minimum and maximum duration has to be a positive integer and "
          + "the maximum duration has to be larger than the minimum duration");
    }
    
    //create a new array of movies with the size being the number of keys in the RBT
    Movie[] between = new Movie[multiKey.numKeys()];
    //create a new IMKRBT iterator
    Iterator<Movie> it = multiKey.iterator();
    //reference variables for the min and max durations
    int min = minTime;
    int max = maxTime;
    int i = 0; //index of between
    Movie m; //the current movie
   
    //if the tree is empty
    if(multiKey.isEmpty()) {
      return null;
      //return between; //return an empty array
    }
    m = it.next(); //set m as the first key 

    //if the tree has 1 node and 1 key
    if(multiKey.numKeys() == 1 && multiKey.size() == 1) {
      //if that key's duration is not between the min and max duration return an empty array otherwise put it in the array 
      if(m.getDuration() > max || m.getDuration() < min) {
        return between;
      }
      else {
        between[0] = m; //set m as the only movie in the list 
      }
    }
    //if the tree has 2 or more nodes or a node with more than 1 keys
    else {
      //while m still has a movie
      while(m != null) {
        //if the duration of next > max or next < min get the next element 
        if(m.getDuration() > max || m.getDuration() < min) {
          //if it.hasNext is false set m as null and return the array
          if(it.hasNext() == false) {
            m = null;
          }
          //otherwise get the next movie
          else {
            m = it.next();
          }
        }
        //otherwise add it to between and increase the index
        else {
          between[i] = m;
          //check if it hasNext if it doesn't set m to null otherwise set m to next
          if(it.hasNext() == false) {
            m = null;
          }
          else {
            m = it.next();
          }
          i++;//increase the index
        }
      }
    } 
    return between; //return the array of movies
  }
  public static void main(String[] args) {
    // TODO Auto-generated method stub 
    //create a new IMKRBT and backend to pass into frontend
    IterableMultiKeyRBT<Movie> rbt = new IterableMultiKeyRBT<Movie>();
    Backend backend = new Backend(rbt);
    //create a new frontend and pass backend into it
    Scanner s = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, s);
    frontend.getCommand();

  }
}
