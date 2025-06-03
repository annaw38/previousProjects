import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class FrontendDeveloperTests {
  
  @Test
  public void testGetCommand() {
  // This JUnit test checks Frontend correctly get commands from the user 
  
  // change the output stream to check the error message
  PrintStream saveSystemOut = System.out;
  ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  System.setOut(new PrintStream(outContent));

  // Assume Frontend implements FrontendInterface
  Scanner scnr = null;
  Frontend front = null;
  
  // if the user gives a incorrect input
  {
    scnr = new Scanner("S\nE");
    front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    front.getCommand();
    if (outContent.toString().contains("Please enter your command: ") == false &&
    outContent.toString().contains("[L]oad file") == false &&
    outContent.toString().contains("[E]xit app") == false &&
    outContent.toString().contains("[S]hortest movies") == false &&
    outContent.toString().contains("[SP]ecified movie length") == false){ 
      // restore the output streams
      System.setOut(saveSystemOut);
      Assertions.fail("Please :" + outContent.toString());
    }
  }
  
  // if the input is 'L'(load file)
  {       
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    scnr = new Scanner("L\nnnn\nE");
    front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    front.getCommand();
    
    // 'L' is a valid input
    if (outContent.toString().toLowerCase().contains("incorrect")) {
        // restore output streams
        System.setOut(saveSystemOut);
        Assertions.fail("Inputting 'L' should be correct" + outContent.toString());
    }

    // check if the code asks user for their file
    if (outContent.toString().toLowerCase().contains("file name") == false &&
	outContent.toString().toLowerCase().contains("file") == false &&
	outContent.toString().toLowerCase().contains("read") == false &&
	outContent.toString().toLowerCase().contains("load") == false){
     // restore output streams
        System.setOut(saveSystemOut);
        Assertions.fail("Should ask user for their file" + outContent.toString());
    }
  }

  // if the input is 'E'(exit app)
  {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    scnr = new Scanner("E");
    front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    front.getCommand();

    // 'E' is a valid input
    if (outContent.toString().toLowerCase().contains("incorrect")) {
        // restore output streams
        System.setOut(saveSystemOut);
        Assertions.fail("Inputting 'E' should be correct");
    }

    // check if the code greet the user
    if (outContent.toString().toLowerCase().contains("thank") == false && 
        outContent.toString().toLowerCase().contains("bye") == false &&
    outContent.toString().toLowerCase().contains("using") == false){
         // restore output streams
        System.setOut(saveSystemOut);
        Assertions.fail("Should not end the program right away");
    }
  }

  // if the input is 'S'(shortest movies)
  {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    scnr = new Scanner("S\nE");
    front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    front.getCommand();

    // 'S' is a valid input
    if (outContent.toString().toLowerCase().contains("incorrect")) {
        // restore the streams
        System.setOut(saveSystemOut);
        Assertions.fail("Inputting 'S' should be correct");
    }

    // check if the code outprint something
    if (outContent.toString() == null){
         // restore the streams
        System.setOut(saveSystemOut);
        Assertions.fail("Should at least print something to the user");
    }
  } 
  
  // if the input is 'SP'(specified movie length) correct input
  {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    scnr = new Scanner("SP\n5\n7\nE");
    front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    front.getCommand();

    // 'SP' is a valid input
    if (outContent.toString().toLowerCase().contains("incorrect input")) {
        // restore the streams
        System.setOut(saveSystemOut);
        Assertions.fail("Inputting 'S' should be correct");
    }

    // check if the code ask user for their lower/upper bound movie duration
    if (outContent.toString().contains("?") == false &&
    outContent.toString().contains("short") == false &&
    outContent.toString().contains("duration") == false &&
    outContent.toString().contains("time") == false &&
    outContent.toString().contains("upper") == false){
         // restore the streams
        System.setOut(saveSystemOut);
        Assertions.fail("Should ask the user for their input");
    }
  }

  // if the input is 'SP'(specified movie length) incorrect input
  {
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    scnr = new Scanner("SP\n5\nhello\n9\nE");
    front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    front.getCommand();
    if (outContent.toString().contains("You should enter an integer") == false){
        // restore the streams
        System.setOut(saveSystemOut);
        Assertions.fail("movie lenght should not be Strings");
    }
  }

  // restore the streams
  System.setOut(saveSystemOut);
}
  @Test
  public void testLoadFile() {
    // This JUnit test checks Frontend correctly reads in the user input and call the backend 
    // Assume Frontend implements FrontendInterface
    Scanner scnr = new Scanner(System.in);
    Frontend front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    
    // change the output stream to check the error message
    PrintStream saveSystemOut = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    
    // if the file name does not exist (cannot test because process takes place in Backend)
    /*
    {
      front.loadFile("hello.csv");
      if (outContent.toString().toLowerCase().contains("does not exist") == false) {
        // restore the output stream
        System.setOut(saveSystemOut);
        Assertions.fail("does not handle the case when the file does not exist");
      }
    }*/
    
    // if the file is not in .csv form
    {
      outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      front.loadFile("hello.txt");
      if (outContent.toString().contains(".csv") == false) {
        // restore the output stream
        System.setOut(saveSystemOut);
        Assertions.fail("does not handle the case when the file is not in .csv form");
      }
    }
    
    // if the argument is correct (for now every .csv file success because determination takes place in Backend)
    {
      outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      front.loadFile("movies.csv");
      if (outContent.toString() == null) {
        // restore the output stream
        System.setOut(saveSystemOut);
        Assertions.fail("Fail to interact with the user");
      }
    }
    
    // restore the output stream
    System.setOut(saveSystemOut);
  }

  @Test
  public void testShortestMovies() {
    // This JUnit test checks Frontend correctly print out movies with the shortest length 
    // Assume Frontend implements FrontendInterface
    Scanner scnr = new Scanner(System.in);
    Frontend front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    
    // change the output stream to check the error message
    PrintStream saveSystemOut = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    
    front.shortestMovies();
   
    // check the program interacts with the user 
    if (outContent.toString().equals("")) {
      // restore the output stream
      System.setOut(saveSystemOut);
      Assertions.fail("There must exist a movie with smallest duration or does not inform the user data is not loaded");
    }
    // restore the output stream
    System.setOut(saveSystemOut);
  }

  @Test
  public void testSpecifiedLengthMovies() {
    // This JUnit test checks Frontend correctly print out movies with the specified length 
    // Assume Frontend implements FrontendInterface
    Scanner scnr = new Scanner(System.in);
    Frontend front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    
    // change the output stream to check the error message
    PrintStream saveSystemOut = System.out;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    
    // check for invalid input(upper bound less than lower bound) to specifiedLengthMovies method
    {
      outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      front.specifiedLengthMovies(10, 5);
      if (outContent.toString().toLowerCase().contains("error") == false) {
        // restore the output stream
        System.setOut(saveSystemOut);
        Assertions.fail("specifiedLengthMovies method should make sure second argument is greater than first argument");
      }
    }
   
   // check for invalid input(upper bound less than lower bound) to specifiedLengthMovies method
    {
      outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      front.specifiedLengthMovies(5,10);
      if (outContent.toString().equals("")) {
        // restore the output stream
        System.setOut(saveSystemOut);
        Assertions.fail("Does not print the result to the user/fail to interact with them");
      }
    }

    // restore the output stream
    System.setOut(saveSystemOut);
  }

  @Test
  public void testExitApp() {
    // This JUnit test checks Frontend correctly ends a program
    // Assume Frontend implements FrontendInterface
    Scanner scnr = new Scanner("quit\newiuf");
    Frontend front = new Frontend(new BackendPlaceholderFrontend(new IterableMultiKeyRBT<Movie>()), scnr);
    front.exitApp();
    try{
        scnr.next();
    } catch (IllegalStateException e){
          if (e.getMessage().equals("Scanner closed") == false){
              System.out.print("Make sure you close the scanner.");
          }
    }
  }

}

