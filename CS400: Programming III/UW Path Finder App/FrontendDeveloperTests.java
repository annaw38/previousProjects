/**
 * This tests the frontend for the UW Path finder
 */
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class FrontendDeveloperTests {
  
  /**
   * Test case 1: Test start Command with invalid input 
   */
  @Test
  public void testStart() {
    //test case 1: invalid input that consists of random numbers
    //create a new scanner, frontend, and tester 
    Scanner s = new Scanner("983\n4");
    Frontend front = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s);
    TextUITester tester = new TextUITester("983\n4");
    
    //run start command 
    front.start();
    
    //check that the output contains the start message and starts with it even though it is invalid input
    String output = tester.checkOutput();
    if(!output.startsWith("Welcome to the UW Path Finder app!") || !output.contains("Welcome to the UW Path Finder app!")) {
      Assertions.fail("There was invalid input, but it was not flagged as invalid input.");
    } 
    
    //test case 2: invalid input that consists of letters
    //create a new scanner, frontend, and tester to test this test case
    Scanner s1 = new Scanner("kjfdab\n4");
    Frontend front1 = new Frontend(new BackendPlaceholder((new DijkstraGraph<String,Integer>(new PlaceholderMap<>()))), s1);
    TextUITester tester2 = new TextUITester("kjfdab\n4");
    
    //run the start command
    front1.start();
    
    //check that the output contains the start message and starts with it even though it is invalid input
    String output2 = tester2.checkOutput();
    if(!output2.startsWith("Welcome to the UW Path Finder app!") || !output2.contains("Welcome to the UW Path Finder app!")) {
      Assertions.fail("There was invalid input, but it was not flagged as invalid input.");
    } 
    
    /*
     * test case 3: valid input for the commands 1 = load data, elephants.csv is invalid input for the 
     * load data file command but we are not testing it specifically here, and 4 = exit app
     */
    //create a new scanner, frontend, and tester to test this test case
    Scanner s2 = new Scanner("1\nelephants.csv\n4");
    Frontend front2 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s2);
    TextUITester tester3 = new TextUITester("1\nelephants.csv\n4");
    
    //run the start command
    front2.start();
    
    /*
     * check that the output contains the start message and starts with it even when it is valid input
     * (for the commands). 
     */
    String output3 = tester3.checkOutput();
    if(!output3.startsWith("Welcome to the UW Path Finder app!") || !output3.contains("Welcome to the UW Path Finder app!")) {
      Assertions.fail("There was valid input, so there was no exception to be thrown.");
    } 
  }
  
  /**
   * Test case 2: Test showMainMenu Command with invalid input(should print the main menu regardless of 
   * input unless it is to quit)
   */
  @Test
  public void testShowMainMenu() {
    //test case 1: invalid input that consists of random numbers
    //create a new scanner, frontend, and tester to test this test case
    Scanner s = new Scanner("983\n4");
    Frontend front = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s);
    TextUITester tester = new TextUITester("983\nq");
    
    //run showMainMenu
    front.showMainMenu();
    
    //check that the menu asks the user to enter a command and tells them to try again after invalid input
    String output = tester.checkOutput();
    if(!output.startsWith("Please enter the command you want to run: ") || !output.contains("You entered "
        + "an invalid command please try again.")) {
      Assertions.fail("The main menu was expected to be displayed, but it was not "
          + "displayed");
    } 
    
    //test case 2: invalid input that consists of random letters
    //create a new scanner, frontend, and tester to test this test case
    Scanner s1 = new Scanner("dsoihr\n4");
    Frontend front1 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s1);
    TextUITester tester2 = new TextUITester("dsoihr\n4");
    
    //run showMainMenu
    front1.showMainMenu();
    
    //check that the menu asks the user to enter a command and tells them to try again after invalid input
    String output2 = tester2.checkOutput();
    if(!output2.startsWith("Please enter the command you want to run: ") || !output2.contains("You entered "
        + "an invalid command please try again.")) {
      Assertions.fail("The main menu was expected to be displayed, but it was not "
          + "displayed");
    } 
    
    //test case 3: invalid input that consists of random letters and numbers
    //create a new scanner, frontend, and tester to test this test case
    Scanner s2 = new Scanner("dsoihr\n4");
    Frontend front2 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s2);
    TextUITester tester3 = new TextUITester("dwegu329\n4");
    
    //run showMainMenu
    front2.showMainMenu();
    
    //check that the menu asks the user to enter a command and tells them to try again after invalid input
    String output3 = tester3.checkOutput();
    if(!output3.startsWith("Please enter the command you want to run: ") || !output3.contains("You entered "
        + "an invalid command please try again.")) {
      Assertions.fail("The main menu was expected to be displayed, but it was not "
          + "displayed");
    } 
    
    /*
     * test case 4: valid input for the command 1 = load data, elephants.csv is invalid input for the 
     * load data file command but we are not testing it specifically here
     */
    //create a new scanner, frontend, and tester to test this test case
    Scanner s3 = new Scanner("1\nelephants.csv\n4");
    Frontend front3 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s3);
    TextUITester tester4 = new TextUITester("1\nelephants.csv\n4");
    
    //run the start command to test that the main menu runs correctly when called from it 
    front3.start();
    
    /*
     * check that the output contains the start message and starts with it even when it is valid input
     * (for the commands). 
     */
    String output4 = tester4.checkOutput();
    if(!output4.startsWith("Welcome to the UW Path Finder app!") 
        || !output4.contains("Welcome to the UW Path Finder app!")
        || !output4.contains("Please enter the command you want to run: ") 
        || !output4.contains("Enter the file name: ")) {
      Assertions.fail("There was valid input, so all the lines above were expected to be printed.");
    } 
    
    /*
     * test case 5: the valid input of 2, show statistics of the graph is entered and tests the 
     * correct output is displayed by the app 
     */
    //create a new scanner, frontend, and tester to test this test case
    Scanner s4 = new Scanner("2\n4");
    Frontend front4 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s4);
    TextUITester tester5 = new TextUITester("2\n4");
    
    //run the start command to test that the main menu runs correctly when called from it 
    front4.start();
    
    /*
     * check that the output contains the start message and starts with it even when it is valid input
     * (for the commands). 
     */
    String output5 = tester5.checkOutput();
    if(!output5.startsWith("Welcome to the UW Path Finder app!") 
        || !output5.contains("Welcome to the UW Path Finder app!")
        || !output5.contains("Please enter the command you want to run: ") 
        || !output5.contains("Summary of the current graph: ")) {
      Assertions.fail("There was valid input, so all the lines above were expected to be printed.");
    } 
    
    /*
     * test case 6: the valid input of 3, enter 2 buildings and display the shortest path between them,
     * is entered and tests the correct output is displayed by the app 
     */
    //create a new scanner, frontend, and tester to test this test case
    Scanner s5 = new Scanner("3\nDiscovery Building\nMemorial Union\n4");
    Frontend front5 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s5);
    TextUITester tester6 = new TextUITester("3\nDiscovery Building\nMemorial Union\n4");
    
    //run the start command to test that the main menu runs correctly when called from it 
    front5.start();
    
    /*
     * check that the output contains the start message and starts with it even when it is valid input
     * (for the commands). 
     */
    String output6 = tester6.checkOutput();
    if(!output6.startsWith("Welcome to the UW Path Finder app!") 
        || !output6.contains("Welcome to the UW Path Finder app!")
        || !output6.contains("Please enter the command you want to run: ") 
        || !output6.contains("Enter the start building: ")
        || !output6.contains("Enter the destination building: ")) {
      Assertions.fail("There was valid input, but one of the lines above was not correctly printed.");
    } 
    
    /*
     * test case 7: the valid input of 4, exit app, is entered and tests the correct output is 
     * displayed by the app 
     */
    //create a new scanner, frontend, and tester to test this test case
    Scanner s6 = new Scanner("4");
    Frontend front6 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s6);
    TextUITester tester7 = new TextUITester("4");
    
    //run the start command to test that the main menu runs correctly when called from it 
    front6.start();
    
    /*
     * check that the output contains the start message and starts with it even when it is valid input
     * (for the commands). 
     */
    String output7 = tester7.checkOutput();
    if(!output7.startsWith("Welcome to the UW Path Finder app!") 
        || !output7.contains("Welcome to the UW Path Finder app!")
        || !output7.contains("Please enter the command you want to run: ") 
        || !output7.contains("Goodbye! Thank you for using the UW Path Finder App!")) {
      Assertions.fail("There was valid input, but one of the lines above was not printed.");
    } 
  }
  
  /**
   * Test case 3: Test loadDataCommand with different invalid inputs
   * 
   */
  @Test
  public void testLoadDataCommand() {
    //test case 1: invalid input of a data file that doesn't exist
    //create a new scanner, frontend, and tester to test this test case
    Scanner s = new Scanner("elephant.dot\n4");
    Frontend front = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s);
    TextUITester tester = new TextUITester("elephant.dot\n4");
    
    //call the loadDataCommand to test it 
    front.loadDataCommand();
    
    //Check that the user is told that this file does not exist and prompted to enter another file name
    String output = tester.checkOutput();
    if(!output.contains("File read in error: This file does not exist. Please enter a valid file"
        + "name.")) {
      Assertions.fail("There was invalid input of a nonexistent file, but it was not flagged as "
          + "a nonexistent file.");
    } 
    
    //test case 2: invalid input of a data file that has a different file extension than what is expected
    //create a new scanner, frontend, and tester to test this test case
    Scanner s1 = new Scanner("graphs.csv\n4");
    Frontend front1 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s1);
    TextUITester tester2 = new TextUITester("graphs.csv\n4");
    
    //call the loadDataCommand to test it 
    front1.loadDataCommand();
    
    //Check that the user is told that this file is not in the .dot format and prompted to enter another file name
    String output2 = tester2.checkOutput();
    if(!output2.contains("File read in error: The file is not in .dot form. Please enter a valid file name.")) {
      Assertions.fail("There was invalid input of a nonexistent file, but it was not flagged as "
          + "a nonexistent file.");
    } 
    
    //test case 3: invalid input of a data file that consists of random letters
    //create a new scanner, frontend, and tester to test this test case
    Scanner s2 = new Scanner("oirwgiprjg\n4");
    Frontend front2 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s2);
    TextUITester tester3 = new TextUITester("oirwgiprjg\n4");
    
    //call the loadDataCommand to test it
    front2.loadDataCommand();
    
    //Check that the user is told that this file is not in .dot format and prompted to enter another file name
    String output3 = tester3.checkOutput();
    if(!output3.contains("File read in error: The file is not in .dot form. Please enter a valid file name.")) {
      Assertions.fail("There was invalid input of a nonexistent file, but it was not flagged as "
          + "a nonexistent file.");
    } 
  }
 
  /**
   * Test case 4: Test showStatsCommand with different cases of inputs
   */
  @Test
  public void testShowStatsCommand() {
    //test case 1: the user asks to show the statistics of a graph before loading a file
    //create a new scanner, frontend, and tester to test this test case
    Scanner s = new Scanner("2\n4");
    Frontend front = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s);
    TextUITester tester = new TextUITester("2\n4");
    
    //call the showStatsCommand to test it 
    front.showStatsCommand();
    
    //check that the output matches the expected output of 0 buildings, 0 paths, and a total walking time of 0.0
    String output = tester.checkOutput();
    if(!output.startsWith("Summary of the current graph: ") || !output.contains("Campus graph with 0 nodes,") 
        || !output.contains("0 edges") || !output.contains("and a total edge weight of 0.00")) {
      Assertions.fail("There were statistics for a path printed that contained numbers besides 0 and 0.0, which"
          + "is not expected.");
    } 
    
    //test case 2: the user asks to show the statistics of a graph after loading a nonexistent file
    //create a new scanner, frontend, and tester to test this test case
    Scanner s1 = new Scanner("1\nducks.dot\n2\n4");
    Frontend front2 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s1);
    TextUITester tester2 = new TextUITester("1\nducks.dot\n2\n4");
    
    //call the showStatsCommand to test it
    front2.showStatsCommand();
    
    //check that the output matches the expected output of 0 buildings, 0 paths, and a total walking time of 0.0
    String output2 = tester2.checkOutput();
    if(!output2.startsWith("Summary of the current graph: ") || !output2.contains("Campus graph with 0 nodes,") 
        || !output2.contains("0 edges") || !output2.contains("and a total edge weight of 0.00")) {
      Assertions.fail("There were statistics for a path printed that does not match the expected path,"
          + "but a path was not expected to be found.");
    } 
  }
 
  /**
   * Test case 5: Test the getRouteInput command 
   */
  @Test
  public void testGetRouteInput() {
    //test case 1: input of random numbers in an empty graph
    //create a new scanner, frontend, and tester to test this test case
    Scanner s = new Scanner("3\n3204\n32428\n4");
    Frontend front = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s);
    TextUITester tester = new TextUITester("3\n3204\n32428\n4");
    
    //call the getRouteInput command to test it
    front.getRouteInput();
    
    /*
     * Check that the output matches what's expected that the graph is empty, there are no buildings 
     * on the path, the walking time for each segment is 0.0, and the total walking time is 0.0
     */
    String output = tester.checkOutput();
    if(!output.contains("This graph is empty.") || !output.contains("The buildings on the path: none") 
        || !output.contains("Estimated walking time for each segment: 0.0")
        || !output.contains("Total estimated walking time for the whole path: 0.0")) {
      Assertions.fail("There was a path found that differs from the expected path, so this was not "
          + "expected.");
    } 
    
    //test case 2: input of random letters in an empty graph
    //create a new scanner, frontend, and tester to test this test case
    Scanner s1 = new Scanner("3\nsugsh\noirewhf\n4");
    Frontend front1 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s1);
    TextUITester tester2 = new TextUITester("3\nsugsh\noirewhf\n4");
    
    //call the getRouteInput command to test it
    front1.getRouteInput();
    
    /*
     * Check that the output matches what's expected that the graph is empty, there are no buildings 
     * on the path, the walking time for each segment is 0.0, and the total walking time is 0.0
     */
    String output2 = tester2.checkOutput();
    if(!output2.contains("This graph is empty.") || !output2.contains("The buildings on the path: none") 
        || !output2.contains("Estimated walking time for each segment: 0.0")
        || !output2.contains("Total estimated walking time for the whole path: 0.0")) {
      Assertions.fail("There was a path found that differs from the expected path, so this was not "
          + "expected.");
    } 
    
    //test case 3: load a nonexistent file then input random building names not in the graph 
    //create a new scanner, frontend, and tester to test this test case
    Scanner s2 = new Scanner("1\nbuilding.dot\n3\nSouth Union\nMemory Library\n4");
    Frontend front2 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s2);
    TextUITester tester3 = new TextUITester("1\nbuilding.dot\n3\nSouth Union\nMemory Library\n4");
    
    //call the getRouteInput command to test it
    front2.getRouteInput();
    
    /*
     * Check that the output matches what's expected that the graph is empty, there are no buildings 
     * on the path, the walking time for each segment is 0.0, and the total walking time is 0.0
     */
    String output3 = tester3.checkOutput();
    if(!output3.contains("This graph is empty.") ||!output3.contains("The buildings on the path: none") 
        || !output3.contains("Estimated walking time for each segment: 0.0")
        || !output3.contains("Total estimated walking time for the whole path: 0.0")){
      Assertions.fail("There was a path found that differs from the expected path, so this was not "
          + "expected.");
    } 
  }
  
  /**
   * Test case 6: Test displayRoute command
   */
  @Test
  public void testDisplayRoute() {
    //test case 1: input of random numbers in an empty graph
    //create a new scanner, frontend, and tester to test this test case
    Scanner s = new Scanner("3\n3204\n32428\n4");
    Frontend front = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s);
    TextUITester tester = new TextUITester("3\n3204\n32428\n4");
    
    //call the displayRoute command with the parameters as random numbers as the parameters
    front.displayRoute("3204","32428");
    
    /*
     * Check that the output matches what's expected that the graph is empty, there are no buildings 
     * on the path, the walking time for each segment is 0.0, and the total walking time is 0.0
     */
    String output = tester.checkOutput();
    if(!output.contains("This graph is empty.") || !output.contains("The buildings on the path: none") 
        || !output.contains("Estimated walking time for each segment: 0.0")
        || !output.contains("Total estimated walking time for the whole path: 0.0")){
      Assertions.fail("There was invalid input of random numbers, so a path was not expected to be "
          + "displayed, but a path was displayed");
    } 
    
    //test case 2: input of random letters in an empty graph
    //create a new scanner, frontend, and tester to test this test case
    Scanner s1 = new Scanner("3\norgw\nnjds\n4");
    Frontend front2 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s1);
    TextUITester tester2 = new TextUITester("3\norgw\nnjds\n4");
    
    //call the displayRoute command with random letters as the parameters
    front2.displayRoute("orgw","njds");
    
    /*
     * Check that the output matches what's expected that the graph is empty, there are no buildings 
     * on the path, the walking time for each segment is 0.0, and the total walking time is 0.0
     */
    String output2 = tester2.checkOutput();
    if(!output2.contains("This graph is empty.") || !output2.contains("The buildings on the path: none") 
        || !output2.contains("Estimated walking time for each segment: 0.0")
        || !output2.contains("Total estimated walking time for the whole path: 0.0")){
      Assertions.fail("There was invalid input of random numbers, so a path was not expected to be "
          + "displayed, but a path was displayed");
    } 
    
    //test case 3: input of random building names in an empty graph
    //create a new scanner, frontend, and tester to test this test case
    Scanner s2 = new Scanner("3\nFreedom Library\nAncient Hall\n4");
    Frontend front3 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s2);
    TextUITester tester3 = new TextUITester("3\nFreedom Library\nAncient Hall\n4");
    
    //call displayRoute with random building names as the parameters 
    front3.displayRoute("Freedom Library", "Ancient Hall");
    
    /*
     * Check that the output matches what's expected that the graph is empty, there are no buildings 
     * on the path, the walking time for each segment is 0.0, and the total walking time is 0.0
     */
    String output3 = tester3.checkOutput();
    if(!output3.contains("This graph is empty.") || !output3.contains("The buildings on the path: none") 
        || !output3.contains("Estimated walking time for each segment: 0.0")
        || !output3.contains("Total estimated walking time for the whole path: 0.0")){
      Assertions.fail("There was invalid input of random numbers, so a path was not expected to be "
          + "displayed, but a path was displayed");
    } 
  }
  
  /**
   * Test case 7: Test that exitCommand exits when only 4 is inputed
   */
  @Test
  public void testExitCommand() {
    //test case 1: input of random numbers then 4 is inputed which will then cause the app to end
    //create a new scanner, frontend, and tester to test this test case
    Scanner s = new Scanner("3204\n32428\n4");
    Frontend front = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s);
    TextUITester tester = new TextUITester("3204\n32428\n4");
    
    //call start to check that only 4 (the command Exit app) causes the program to end
    front.start();
    
    //check that the program ends and contains the goodbye statement
    String output = tester.checkOutput();
    if(!output.contains("Goodbye! Thank you for using the UW Path Finder App!")) {
      Assertions.fail("The app was expected to exit, but did not.");
    } 
    
    //test case 2: input of just 4 causes the app to exit
    //create a new scanner, frontend, and tester to test this test case
    Scanner s1 = new Scanner("4\n");
    Frontend front1 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s1);
    TextUITester tester2 = new TextUITester("4\n");
    
    //call start to check that only 4 (the command Exit app) causes the program to end
    front1.start();
    
    //check that the program ends and contains the goodbye statement
    String output2 = tester2.checkOutput();
    if(!output2.contains("Goodbye! Thank you for using the UW Path Finder App!")) {
      Assertions.fail("The app was expected to exit, but did not.");
    } 
    
    //test case 3: 4 is entered then random numbers are inputed
    //create a new scanner, frontend, and tester to test this test case   
    Scanner s2 = new Scanner("4\n8923");
    Frontend front2 = new Frontend(new BackendPlaceholder(new DijkstraGraph<String,Integer>(new PlaceholderMap<>())), s2);
    TextUITester tester3 = new TextUITester("4\n8923");
    
    //call start to check that only 4 (the command Exit app) causes the program to end
    front2.start();
    
    //check that the program ends and contains the goodbye statement
    String output3 = tester3.checkOutput();
    if(!output3.contains("Goodbye! Thank you for using the UW Path Finder App!")) {
      Assertions.fail("The app was expected to exit, but did not.");
    } 
  }
  
  /**
   * Integration test 1/Test case 8: Test that the readDataFromFile and getDataSummaryString commands work as expected
   */
  @Test
  public void integrationTestShowStats() {
    //show the statistics of the campus.dot file
    Scanner s = new Scanner("1\ncampus.dot\n2\n4");
    //instantiate a new frontend using backend
    Frontend front = new Frontend(new Backend(), s);
    TextUITester tester = new TextUITester("1\ncampus.dot\n2\n4");
    
    front.start();
    
    //check that the program loads the file and shows the statistics of the file
    String output = tester.checkOutput();
    if(!output.contains("The file has been loaded.") || !output.contains("Summary of the current graph: ") 
        || !output.contains("Campus graph with 160 nodes,") || !output.contains("788 edges")
        || !output.contains("and a total edge weight of 110675.50")) {
      Assertions.fail("The app was expected to load a valid file, return the statistics of the graph"
         +" and exit, but failed to.");
    } 
  }
  
  /**
   * Integration test 2/Test case 9: Test that the readDataFromFile and getShortestPathBetween commands work as expected
   */
  @Test
  public void integrationTestShortestPath() {
    //show the statistics of a valid path in the campus.dot file
    Scanner s = new Scanner("1\ncampus.dot\n3\nDeLuca Biochemistry Laboratories\nBabcock Hall\n4");
    //instantiate a new frontend using backend
    Frontend front = new Frontend(new Backend(), s);
    TextUITester tester = new TextUITester("1\ncampus.dot\n2\n4");
    
    front.start();
    
    //check that the program loads the file and returns a valid path with its statistics
    String output = tester.checkOutput();
    if(!output.contains("The file has been loaded.") || !output.contains("A path was found!")
        || !output.contains("The buildings on the path: [DeLuca Biochemistry Laboratories, Babcock Hall]") 
        || !output.contains("Estimated walking time for each segment: [106.8]") 
        || !output.contains("Total estimated walking time for the whole path: 106.8")) {
      Assertions.fail("The app was expected to load a valid file, return the path details, the buildings"
          + " on the path, estimated walking time for each segment, and total walking time for the whole path");
    } 
    
    //show the statistics of a invalid path in the campus.dot file
    Scanner s1 = new Scanner("1\ncampus.dot\n3\nChem Building\nBabcock Hall\n4");
    //instantiate a new frontend using backend
    Frontend front1 = new Frontend(new Backend(), s1);
    TextUITester tester1 = new TextUITester("1\ncampus.dot\n3\nChem Building\nBabcock Hall\n4");
    
    front1.start();
    
    //check that the program loads the file and returns a valid path with its statistics
    String output1 = tester1.checkOutput();
    if(!output1.contains("The file has been loaded.") || !output1.contains("There was no path found between these buildings.")){
      Assertions.fail("The app was expected to load a valid file, return the path details, the buildings"
          + " on the path, estimated walking time for each segment, and total walking time for the whole path");
    } 
  }
  /**
   * Partner Code Test 1/Test case 10: Test that the readDataFromFile and getDatasetSummaryString commands work as expected
   */
  @Test
  public void partnerTestReadDataGetSummary() {
    Backend b = new Backend();
    //test that the empty graph returns a 0 for all the stats
    //actual result
    String emptyStats = b.getDatasetSummaryString();
    //expected result
    String emptyExpected = "Campus graph with 0 nodes, 0 edges and a total edge weight of 0.00";
    if(!emptyStats.equals(emptyExpected)) {
      Assertions.fail("Empty string statistics does not match 0 for all the statistics.");
    }
    //try reading in campus.dot
    try {
      b.readDataFromFile("campus.dot");
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught");
    }
    
    String campusStats = b.getDatasetSummaryString();
    String campusExpected = "Campus graph with 160 nodes, 788 edges and a total edge weight of 110675.50";
    
    //check that the actual summary matches the expected summary
    Assertions.assertEquals(campusExpected, campusStats);
  }
  /**
   * Partner Code Test 2/Test case 11: Test that the readDataFromFile and getShortestPathBetween commands work as expected
   */
  @Test
  public void partnerTestShortestPath() {
    Backend b = new Backend();
    int count = 0; //number of exceptions caught
    //try loading in the campus.dot file 
    try {
      b.readDataFromFile("campus.dot");
    }catch(Exception e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    
    //try an invalid path 
    try {
      b.getShortestPathBetween("Chem Building", "Paladise Apartments");
    }catch(NoSuchElementException e) {
      count++;
    }
    
    PathResultInterface path = null;
    //try a valid path 
    try {
      path = b.getShortestPathBetween("Helen C White Hall", "Brat Stand");
    }catch(NoSuchElementException e) {
      Assertions.fail("An exception was not expected to be caught.");
    }
    
    //excpected values of the stops
    List<String> expectedStops = new LinkedList<String>();
    expectedStops.add("Helen C White Hall");
    expectedStops.add(1,"Brat Stand");
    //expected costs of the segments
    List<Double> expectedCosts = new LinkedList<Double>();
    expectedCosts.add(125.80000000000001);
    //expected total cost
    double expectedTotal = 125.80000000000001;
    
    //check that all the stops, costs of each segment, and total matches their expected values
    Assertions.assertEquals(expectedStops.toString(), path.getPathStops().toString());
    Assertions.assertEquals(expectedCosts.toString(), path.getSegmentCosts().toString());
    Assertions.assertEquals(expectedTotal, path.getTotalCosts());
  
    
    //case 2: another valid path with more stops   
    List<String> expectedStops2 = new LinkedList<String>();
    expectedStops2.add("Hotel Red");
    expectedStops2.add(1,"Fire Station No. 4");
    expectedStops2.add(2,"Camp Randall Sports Center");
    //expected costs of the segments
    List<Double> expectedCosts2 = new LinkedList<Double>();
    expectedCosts2.add(141.99999999999997);
    expectedCosts2.add(118.19999999999999);
    //expected total cost
    double expectedTotal2 = 260.19999999999993;
    
    //another valid path
    PathResultInterface path2 = null;
    try {
      path2 = b.getShortestPathBetween("Hotel Red", "Camp Randall Sports Center");
    }catch(NoSuchElementException e) {
      Assertions.fail("A NoSuchElementException was not expected to be caught.");
    }

    Assertions.assertEquals(expectedStops2.toString(), path2.getPathStops().toString());
    Assertions.assertEquals(expectedCosts2.toString(), path2.getSegmentCosts().toString());
    Assertions.assertEquals(expectedTotal2, path2.getTotalCosts());
  }
  
}
