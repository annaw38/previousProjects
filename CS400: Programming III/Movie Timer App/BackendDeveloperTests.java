// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: <your group's name: C20
// TA: Manas Trivedi
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Backend Tests for Movies
 */
public class BackendDeveloperTests {
  
  /**
   * Test case 1: Find the minimum duration movie in an empty tree
   */
  @Test
  public void testFindMinDuration() {
    //expected array is null because it is an empty tree
    IterableMultiKeySortedCollectionInterfacePlaceholder ph= new IterableMultiKeySortedCollectionInterfacePlaceholder();
    Backend backend = new Backend(ph);
    
    //expected: null 
    Movie[] expected = null;
   
    if(expected != backend.withMinDuration()){
      Assertions.fail("Null was expected, but was not returned.");
    }
    
  }
  
  /**
   * Test case 2: Find the minimum duration movie in non-empty trees
   */
  @Test
  public void testFindMinDurationInNonemptyTree() {
    //code for a RBT with 1 element 
    IterableMultiKeySortedCollectionInterfacePlaceholder ph1 = new IterableMultiKeySortedCollectionInterfacePlaceholder();
    Movie movie = new Movie("Bugs Bunny's Third Movie: 1001 Rabbit Tales", "Animation", 1982, "United States", 76);
    ph1.insertSingleKey(movie);
    Backend backend1 = new Backend(ph1);
    //placeholder: array consisting of a single movie 
     
    //expected to return the only movie in the tree
    Movie[] minDurationOneElement = new Movie[]{movie};
    //test that withMinDuration() returns the only movie in the tree
    backend1.withMinDuration();
    if(!minDurationOneElement[0].equals(backend1.withMinDuration()[0]) || minDurationOneElement.length != backend1.withMinDuration().length) {
      Assertions.fail("The movie that is expected to be returned doesn't match the returned movie or the size of the returned array doesn't match the expected size.");
    }
    
    IterableMultiKeySortedCollectionInterfacePlaceholder ph12 = new IterableMultiKeySortedCollectionInterfacePlaceholder();
    Movie movie2 = new Movie("Diner", "Comedy", 1982, "United States", 95);
    Movie movie3 = new Movie("18 anni tra una settimana", "Drama", 1991, "Italy", 98);
    ph12.insertSingleKey(movie);
    ph12.insertSingleKey(movie2);
    ph12.insertSingleKey(movie3);
    Backend backend12 = new Backend(ph12);
    
    //code for a RBT with more than 1 element (root with only right children)
    //placeholder: array consisting of 3 movies ordered from lowest to highest duration
    
    //Movie[] actual2 = new Movie[] {movie, movie2, movie3};
    
    //expected to return the movie with the lowest duration in the tree (movie)
    Movie[] minDurationMoreElements = new Movie[]{movie};
    //test that withMinDuration() returns the lowest duration movie in the tree
    if(!minDurationMoreElements[0].equals(backend12.withMinDuration()[0])) {
      Assertions.fail("The movie that is expected to be returned doesn't match the returned movie or the size of the returned array doesn't match the expected size.");
    }
    
    IterableMultiKeySortedCollectionInterfacePlaceholder ph13 = new IterableMultiKeySortedCollectionInterfacePlaceholder();
    Movie movie4 = new Movie("Harem Holiday", "Musical", 1965, "United States", 76);
    
    ph13.insertSingleKey(movie);
    ph13.insertSingleKey(movie4);
    ph13.insertSingleKey(movie2);
    ph13.insertSingleKey(movie3);
  
    Backend backend13 = new Backend(ph13);
    //code for RBT with more than 1 element and more than 1 movie has the same min duration
    //placeholder: array consisting of 4 movies ordered from lowest to highest duration
   
    
    //expected to return the movies with the lowest duration in the tree
    //both have the minimum duration of 76
    Movie[] minDurationMoreThanOne = new Movie[]{movie, movie4};
    Movie[] actualMinDurationMoreThanOne = backend13.withMinDuration();
    //test that withMinDuration() returns the lowest duration movies in the tree
    if(!minDurationMoreThanOne[0].equals(backend13.withMinDuration()[0]) || !minDurationMoreThanOne[1].equals(backend13.withMinDuration()[1])) {
      Assertions.fail("The movie that is expected to be returned doesn't match the returned movie or the size of the returned array doesn't match the expected size.");
    }
  }
  
  /**
   * Test case 3: Find the movies with the duration between the non valid durations that min duration 
   * is larger than the max duration and when the max duration is smaller than the min duration.
   *    
   */
  @Test
  public void testBetweenInvalidDurations() {
    //test invalid durations for betweenDuration
    IterableMultiKeySortedCollectionInterfacePlaceholder ph2 = new IterableMultiKeySortedCollectionInterfacePlaceholder();
    Backend backend2 = new Backend(ph2);
    int count = 0;//number of IllegalArgumentExceptions caught 
    
    //test that betweenDuration throws an IllegalArgumentException when the minTime is greater than maxTime
    try{
      Assertions.assertEquals(null, backend2.betweenDuration(300, 150));
    }catch(IllegalArgumentException e) {
      //this is expected for because the minTime is greater than the maxTime, so increase count by 1
      count++;
    }
    
    //test negative durations -- IllegalArgumentException is expected for all 3 cases
    try {   
      Assertions.assertEquals(null, backend2.betweenDuration(-20, 30));
    }catch(IllegalArgumentException e) {
      //this is expected for a negative duration, so increase count by 1
      count++;
    }
    try {   
      Assertions.assertEquals(null, backend2.betweenDuration(30, -90));
    }catch(IllegalArgumentException e) {
      //this is expected for a negative duration, so increase count by 1
      count++;
    }
    try {   
      Assertions.assertEquals(null, backend2.betweenDuration(-60, -40));
    }catch(IllegalArgumentException e) {
      //this is expected for a negative duration, so increase count by 1
      count++;
    }
    Assertions.assertEquals(4, count); //4 IllegalArgumentExceptions are expected based on the cases above
  
  }
  
  /**
   * Test case 4: Find the movies with the duration between the same time and a valid duration where
   *  minTime is less than maxTime.
   */
  @Test
  public void testFindValidDurations() {
    //test valid durations - a non-empty array is expected from both cases 
    IterableMultiKeySortedCollectionInterfacePlaceholder ph3 = new IterableMultiKeySortedCollectionInterfacePlaceholder();
    Movie movie = new Movie("A Message from Holly", "Drama", 1992, "United States", 90);
    Movie movie2 = new Movie("Diner", "Comedy", 1982, "United States", 95);
    Movie movie3 = new Movie("18 anni tra una settimana", "Drama", 1991, "Italy", 98);
    Movie movie4 = new Movie("Harem Holiday", "Musical", 1965, "United States", 76);
    Movie movie5 = new Movie("Broken Angel", "Drama", 1988, "United States", 90);
    
    ph3.insertSingleKey(movie);
    ph3.insertSingleKey(movie5);
    ph3.insertSingleKey(movie2);
    ph3.insertSingleKey(movie3);
    ph3.insertSingleKey(movie4);
    
    
    Backend backend3 = new Backend(ph3);
    
    //code for a non-empty RBT that includes "A Message from Holly" and "Broken Angel" 
    //placeholder: array with 5 movies that includes these 2 movies ordered by smallest to largest duration
   
    
    //code for a non-empty RBT that includes "A Message from Holly", "18 anni tra una settimana", "Diner", and "Broken Angel" 
    //placeholder: same movieList as above
    
    //both of these movies have a duration of 90 
    Movie[] expectedSameDuration = new Movie[] {movie, movie5};
    //all of these movies except "Harem Holiday" has a duration between 90 and 120
    Movie[] expectedValidDuration = new Movie[] {movie, movie5, movie2, movie3};
    
    //test that betweenDuration return with these movies for each case
    if(!expectedSameDuration[0].equals(backend3.betweenDuration(90,90)[0]) || !expectedSameDuration[1].equals(backend3.betweenDuration(90,90)[1])) {
      Assertions.fail("The movie that is expected to be returned doesn't match the returned movie or the size of the returned array doesn't match the expected size.");
    }
    //movie array that is returned after calling betweenDuration(90,120)
    Movie[] actual = backend3.betweenDuration(90, 120);
    
    if(!expectedValidDuration[0].equals(actual[0]) || !expectedValidDuration[1].equals(actual[1]) || !expectedValidDuration[2].equals(actual[2]) || !expectedValidDuration[3].equals(actual[3])) {
      Assertions.fail("The movie that is expected to be returned doesn't match the returned movie or the size of the returned array doesn't match the expected size.");
    }
   // Assertions.assertEquals(expectedSameDuration, backend3.betweenDuration(90, 90));
   // Assertions.assertEquals(expectedValidDuration, backend3.betweenDuration(90, 120));
    
  }
  /**
   * Test case 5: Test fileReader with null, a random file that doesn't exist, and a file that does exist.
   */
  @Test
  public void testFileReader() {
    //test fileReader with a null and nonexistent file then a shortened version of movies.csv 
    IterableMultiKeySortedCollectionInterfacePlaceholder ph4 = new  IterableMultiKeySortedCollectionInterfacePlaceholder();
    Backend backend4 = new Backend(ph4);
    int count = 0; //number of times FileNotFoundException is caught
    
    //expect FileNotFoundException for null as the argument of fileReader
    try {
      Assertions.assertEquals(null, backend4.fileReader(null));
    } catch (FileNotFoundException e) {
      //A FileNotFoundException is expected so increase count by 1 if it is caught
      count++;
    }    
    
    //expect FileNotFoundException for a nonexistent file as the argument of fileReader
    try {
      Assertions.assertEquals(null, backend4.fileReader("ducks.csv"));
    } catch (FileNotFoundException e) {
      //A FileNotFoundException is expected so increase count by 1 if it is caught 
      count++;
    }    
    
    Assertions.assertEquals(2, count); //2 is the number of FileNotFoundExceptions expected to be caught
    
  }

  /**
   * Test case 6: Test the getters in MovieInterface (getTitle, getDuration, getYear, getCountry, and getGenre)
   */
  @Test
  public void testMovieGetters() {
    //test that the movie constructor works and that the getters return the expected movie properties
    
    //new movie object to test
    Movie movie1 = new Movie("Diner", "Comedy", 1982, "United States", 95);
    
    //expected movie properties after getting the properties of the movie "Diner"
    String title = "Diner";
    String genre = "Comedy";
    String country = "United States";
    int duration = 95;
    int year = 1982;
    
    //check that the properties returned from the getters match their expected properties
    Assertions.assertEquals(genre, movie1.getGenre());
    Assertions.assertEquals(title, movie1.getTitle());
    Assertions.assertEquals(country, movie1.getCountry());
    Assertions.assertEquals(duration, movie1.getDuration());
    Assertions.assertEquals(year, movie1.getYear());
    
  }
  /**
   * Test case 7: Test FileReader with a real file - a shortened version of movies.csv
   */
  @Test 
  public void testFileReaderRealFile() {
    //create a new IMKRBT to put into backend's constructor
    IterableMultiKeyRBT<Movie> rbt = new IterableMultiKeyRBT<Movie>();
    Backend backend4 = new Backend(rbt);
    //create 5 new movies and put them into an array to be the expected array after the file is read in
    Movie movie = new Movie("Bugs Bunny's Third Movie: 1001 Rabbit Tales", "Animation", 1982, "United States", 76);
    Movie movie2 = new Movie("18 anni tra una settimana", "Drama", 1991, "Italy", 98);
    Movie movie3 = new Movie("Ride a Wild Pony","Romantic", 1976, "United States", 91);
    Movie movie4 = new Movie("Diner", "Comedy", 1982, "United States", 95);
    Movie movie5 = new Movie("A che servono questi quattrini?", "Comedy", 1942,"Italy",85);
    Movie[] expectedRealFile = new Movie[] {movie, movie2, movie3, movie4, movie5};
  
    //actual movies array after reading in the file
    //expected: no exception and should match expectedRealFile
    Movie[] actual = new Movie[5];
    try {
      actual = backend4.fileReader("../Movies project/src/movies_short.csv");
    } catch (FileNotFoundException e) {
      Assertions.fail("A FileNotFoundException is not expected.");
    }
    //check that all the properties match the expected movies' properties
    //country
    if(!expectedRealFile[0].getCountry().equalsIgnoreCase(actual[0].getCountry()) || !expectedRealFile[1].getCountry().equalsIgnoreCase(actual[1].getCountry()) || !expectedRealFile[2].getCountry().equalsIgnoreCase(actual[2].getCountry()) ||
        !expectedRealFile[3].getCountry().equalsIgnoreCase(actual[3].getCountry()) || !expectedRealFile[4].getCountry().equalsIgnoreCase(actual[4].getCountry())) {
      Assertions.fail("One of the actual movie's country does not match the expected movie's country.");
    }
    //genre
    if(!expectedRealFile[0].getGenre().equalsIgnoreCase(actual[0].getGenre()) || !expectedRealFile[1].getGenre().equalsIgnoreCase(actual[1].getGenre()) || !expectedRealFile[2].getGenre().equalsIgnoreCase(actual[2].getGenre()) ||
        !expectedRealFile[3].getGenre().equalsIgnoreCase(actual[3].getGenre()) || !expectedRealFile[4].getGenre().equalsIgnoreCase(actual[4].getGenre())) {
      Assertions.fail("One of the actual movie's genre does not match the expected movie's genre.");
    }
    //movie title
    if(!expectedRealFile[0].getTitle().equalsIgnoreCase(actual[0].getTitle()) || !expectedRealFile[1].getTitle().equalsIgnoreCase(actual[1].getTitle()) || !expectedRealFile[2].getTitle().equalsIgnoreCase(actual[2].getTitle()) ||
        !expectedRealFile[3].getTitle().equalsIgnoreCase(actual[3].getTitle()) || !expectedRealFile[4].getTitle().equalsIgnoreCase(actual[4].getTitle())) {
      Assertions.fail("One of the actual movie's genre does not match the expected movie's genre.");
    }
    //year
    if(expectedRealFile[0].getYear()!=actual[0].getYear() || expectedRealFile[1].getYear()!=actual[1].getYear() || expectedRealFile[2].getYear()!=actual[2].getYear() ||
        expectedRealFile[3].getYear()!=actual[3].getYear() || expectedRealFile[4].getYear()!=actual[4].getYear()) {
      Assertions.fail("One of the actual movie's year does not match the expected movie's year.");
    }
    //duration
    if(expectedRealFile[0].getDuration()!=actual[0].getDuration() || expectedRealFile[1].getDuration()!=actual[1].getDuration() || expectedRealFile[2].getDuration()!=actual[2].getDuration() ||
        expectedRealFile[3].getDuration()!=actual[3].getDuration() || expectedRealFile[4].getDuration()!=actual[4].getDuration()) {
      Assertions.fail("One of the actual movie's genre does not match the expected movie's genre.");
    }
    //check that the movies were all inserted into the tree
    //expected: 5 keys and 5 nodes because there are no duplicates
    Assertions.assertEquals(5,rbt.numKeys());
    Assertions.assertEquals(5, rbt.size());
    
  }
  /**
   * Test Case 8/Integration Test 1: Test loadFile and calling shortestMovies
   */
  @Test
  public void testIntegrationShortestMovies() {
    //create a new IMKSC placeholder and backend to pass into frontend
    IterableMultiKeyRBT<Movie> rbt = new IterableMultiKeyRBT<Movie>();
    Backend backend = new Backend(rbt);
    //create a new frontend and pass backend into it
    Scanner scan = new Scanner("L\n../Movies project/src/movies_short.csv\nS\nE");
    
    //save the output to compare to later to ensure that the method is interacting with the user and not null 
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    
    //expected: to not be null and interact with the user by printing the shortest movie in the 
    //movies_short.csv file and then exit
    try {
      Frontend front = new Frontend(backend, scan);
      if(outContent.toString() == null) {
        Assertions.fail("This should return Bugs Bunny's Third Movie: 1001 Rabbit Tales");
      }
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    
  }
  /**
   * Test Case 9/Integration Test 2: Test loadFile then get the movies between valid durations
   */
  @Test
  public void testIntegrationMovieMethods() {
    //create a new IMKSC placeholder and backend to pass into frontend
    IterableMultiKeyRBT<Movie> rbt = new IterableMultiKeyRBT<Movie>();
    Backend backend = new Backend(rbt);
    //create a new frontend and pass backend and scanner into it
    Scanner s1 = new Scanner("L\n../Movies project/src/movies_short.csv\nSP\n90\n100\nE");
    
    //save the output to compare to later to ensure that the method is interacting with the user and not null 
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    
    //expected: to not be null and interact with the user by printing the shortest movie in the 
    //movies_short.csv file and then exit
    try {
      Frontend front = new Frontend(backend, s1);
      if(outContent.toString() == null) {
        Assertions.fail("This should return Bugs Bunny's Third Movie: 1001 Rabbit Tales");
      }
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
  
  }
  /**
   * Test Case 10/Partner Code Test 1: Test exitApp 
   */
  @Test
  public void testFrontendExitApp() {
    //create a new IMKSC placeholder and backend to pass into frontend
    IterableMultiKeyRBT<Movie> rbt = new IterableMultiKeyRBT<Movie>();
    Backend backend = new Backend(rbt);
    //create a new frontend and pass backend and scanner into it
    Scanner s1 = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, s1);
    int count = 0; //number of exceptions caught
    
    //case 1: exit app then try to get next from scanner
    //expected: exception is caught
    frontend.exitApp();
    try {
      s1.next();
    }catch(Exception e) {
      count++;
    }
    
    //case 2: create a new scanner and frontend to test exit by entering "E"
    //expected "Closing the app...\nThank you for using our app!!"
    Scanner s2 = new Scanner("E");
    Frontend frontend1 = new Frontend(backend, s2);
    try {
      frontend1.getCommand();
    }catch(Exception e) {
      Assertions.fail("An Exception was not expected to be caught.");
    }
    Assertions.assertEquals(1, count); //count is expected to be 1 because only 1 exception should have been caught
  }
  /**
   * Test Case 11/Partner Test 2: Test shortestMovies
   */
  @Test
  public void testFrontendShortestMovies() {
    //create a new IMKSC placeholder and backend to pass into frontend
    IterableMultiKeyRBT<Movie> rbt = new IterableMultiKeyRBT<Movie>();
    Backend backend = new Backend(rbt);
    //create a new frontend and pass backend and scanner into it
    Scanner s = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, s);
    
    //case 1: calling shortestMovies() before reading a file in 
    //expected: "Command order error: \n Haven't loaded the data yet"
    try {
      frontend.shortestMovies();
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    frontend.loadFile("../Movies project/src/movies_short.csv");
    
    //case 2: calling shortestMovies after reading a file in 
    //expected: "Here's your data:\nBugs Bunny's Third Movie: 1001 Rabbit Tales" to be returned 
    try {
      frontend.shortestMovies();
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
   
  }
  /**
   * Test Case 12/Partner Test 3: Test specifiedLengthMovies
   */
  @Test
  public void testFrontendSpecifiedLengthMovies() {
  //create a new IMKSC placeholder and backend to pass into frontend
    IterableMultiKeyRBT<Movie> rbt = new IterableMultiKeyRBT<Movie>();
    Backend backend = new Backend(rbt);
    //create a new frontend and pass backend and scanner into it
    Scanner s = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, s);
    int count = 0;
    
    //case 1: calling specifiedLengthMovies before reading a file in 
    //expected: "Command order error: \n Haven't loaded the data yet"
    try {
      frontend.specifiedLengthMovies(15,60);
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
   
    //case 2: calling specifiedLengthMovies with negative durations 
    //expected: IllegalArgumentException to be caught 
    try {
      frontend.specifiedLengthMovies(-5, -1);
    }catch(IllegalArgumentException e) {
      count++;
    }
    
    //case 3: calling specifiedLengthMovies with durations larger than the largest duration in the file  
    //expected: "Here's your data: \n"
    try {
      frontend.specifiedLengthMovies(140, 150);
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    
    //case 4: calling specifiedLengthMovies with letters as the durations 
    //expected: "Input mismatch error: \nYou should enter an integer"
    try {
      Scanner scnr = new Scanner("L\n../Movies project/src/movies_short.csv\nn\no\nE");
      Frontend frontend1 = new Frontend(backend, scnr);
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    
    //case 5: calling specifiedLengthMovies with a maximum durations smaller than the minimum duration 
    //expected: "Input mismatch error:\nUpper bound should be greater than lower bound"
    try {
      frontend.specifiedLengthMovies(190, 150);
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    frontend.loadFile("../Movies project/src/movies_short.csv");
    
    //case 6: calling specifiedLengthMovies with valid durations after loading in a valid file
    //expected: "Here's your data: \nBugs Bunny's Third Movie: 1001 Rabbit Tales\nA che servono questi quattrini?\nRide a Wild Pony"
    try {
      frontend.specifiedLengthMovies(70, 91);
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    //count is expected to be 1 because of case 2: calling specifiedLengthMovies with negative durations
    Assertions.assertEquals(1, count);
    
  }
  /**
   * Test case 13/Partner Test 4: Test loadFile
   */
  @Test
  public void testFrontendLoadFile() {
    IterableMultiKeyRBT<Movie> rbt = new IterableMultiKeyRBT<Movie>();
    Backend backend = new Backend(rbt);
    //create a new frontend and pass backend and scanner into it
    Scanner s = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, s);
    
    //case 1: a string of characters 
    //expected: frontend prints "File read in error: \n file not in .csv form"
    try {
      frontend.loadFile("abcd");
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    
    //case 2: a file with the wrong extension (not .csv)
    //expected: frontend prints "File read in error: \n file not in .csv form"
    try {
      frontend.loadFile("movies.pdf");
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    
    //case 3: a valid name but nonexistent file
    //expected: frontend prints "File read in error: \nThe file you enter does not exist."
    try {
      frontend.loadFile("ducks.csv");
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    
    //case 4: a valid and existing file in folder
    //expected: Your file is loaded.
    try {
      frontend.loadFile("../Movies project/src/movies_short.csv");
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
  }

}