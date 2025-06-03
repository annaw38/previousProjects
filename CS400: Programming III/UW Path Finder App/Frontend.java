import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Frontend implements FrontendInterface{
  
  private BackendInterface reference;// reference to the Backend
  private Scanner scanner; // reference of a Scanner Object to read inputs
     
  /**
   * Constructor for Frontend
   * @param reference A reference to the Backend
   * @param scanner A reference to a Scanner
   */
  public Frontend(BackendInterface reference, Scanner scanner){
      this.reference = reference;
      this.scanner = scanner;
  }

  /**
   * A method that starts the app. This method will then call the showMainMenu command to start the 
   * loop.
   */
  @Override
  public void start() {
    //welcomes the user to the app
    System.out.println("Welcome to the UW Path Finder app!");
    //starts the loop asking for user input 
    showMainMenu();
    
  }
  
  /**
   * Prints all the commands the user can run. 
   * 1 - Load file
   * 2 - Show the statistics of the graph
   * 3 - Enter specific buildings to find the path between
   * 4 - Exit the app
   */
  @Override
  public void showMainMenu() {
    //print out all the options in the main menu to the user
    System.out.println("Please enter the command you want to run: ");
    System.out.println("Enter the number of the command, e.g. 1 for Load file");
    System.out.println("1.Load file");
    System.out.println("2.Show statistics");
    System.out.println("3.Enter the buildings you want to find the path between: ");
    System.out.println("4.Exit app");
    int input;
    try {
      //input is the integer value of the user input 
      input = Integer.valueOf(scanner.nextLine());
    }catch(Exception e) {
      System.out.println("You entered an invalid command please try again."); 
      //continue asking the user for their input
      System.out.println("Please enter the command you want to run: ");
      System.out.println("Enter the number of the command, e.g. 1 for Load file");
      System.out.println("1.Load file");
      System.out.println("2.Show statistics");
      System.out.println("3.Enter the buildings you want to find the path between: ");
      System.out.println("4.Exit app");
      //get the new input the user entered
      input = Integer.valueOf(scanner.nextLine());
    }
    
    //if the user types in 4 then the program will terminate
    while(input != 4) {
    //first check if the input is valid
      if(input == 1 || input == 2 || input == 3) {
        switch (input) {
        //the user wants to load a file
        case 1:
          //System.out.print("Enter the file you want to load: ");
          //String filename = scanner.nextLine();
          this.loadDataCommand();
          break;
        //the user wants to see the statistics of the current graph
        case 2:
          this.showStatsCommand();
          break;
        //the user wants to input 2 buildings to find the path between them
        case 3:
          this.getRouteInput();
          //this.displayRoute();
          break;
        }
      }
        
      //if the input is invalid
      else {
          System.out.println("You entered an invalid command please try again."); 
      }
  
      //continue asking the user for their input
      System.out.println("Please enter the command you want to run: ");
      System.out.println("Enter the number of the command, e.g. 1 for Load file");
      System.out.println("1.Load file");
      System.out.println("2.Show statistics");
      System.out.println("3.Enter the buildings you want to find the path between: ");
      System.out.println("4.Exit app");
      //get the new input the user entered
      input = Integer.valueOf(scanner.nextLine());
    
    }
    this.exitCommand(); //4 is entered, so the app exits by calling exitCommand
  }
  
  /**
   * Reads the user's inputed file name into a graph with the readDataFromFile command. 
   */
  @Override
  public void loadDataCommand() {
    //get the fileName from the user 
    System.out.println("Enter the file name: ");
    String fileName = scanner.nextLine();
    
    //check if the file name inputed is in .dot form
    if(fileName.endsWith(".dot") != true) {
      //print that there was an error reading in the file
      System.out.println("File read in error: The file is not in .dot form. Please enter a valid file "
          + "name.");
      return;
    }
    
    //try reading in the file with backend's readDataFromFile
    try {
      reference.readDataFromFile(fileName);
      System.out.println("The file has been loaded.");
    }
    //If the file was not successfully loaded: the file doesn't exist or is in a bad format.
    catch(IOException e) {
      System.out.println("File read in error: This file does not exist. Please enter a valid file"
          + "name.");
      return;
    }
  }

  /**
   * Shows the statistics of the current graph. The number of buildings, paths, and the total walking
   * time (sum of all the edges)
   */
  @Override
  public void showStatsCommand() {
    String stats = reference.getDatasetSummaryString(); //reference variable for the stats of the graph
    System.out.println("Summary of the current graph: " + stats);
  }

  /**
   * Gets the start building and destination building from the user and calls the display route to 
   * print the shortest path and the other statistics.
   */
  @Override
  public void getRouteInput() {
    //reference variables for the start and final destination buildings
    String start = null;
    String destination = null;
    boolean flag = true; //used to signify if the input is valid 
    
    //get the start building from the user 
    while(flag) {
      try {
        //ask the user to enter the start building
        System.out.println("Enter the start building: ");
        //get the start building 
        start = scanner.nextLine();
        flag = false; //set flag to false because it is valid input 
      } catch (NoSuchElementException e) {
        System.out.println("Please enter a valid building.");
      }
    }
    
    flag = true; //set flag to true to allow the user to input the destination building 
    //get the destination building from the user
    while(flag) {
       try {
         //get the destination building
         System.out.println("Enter the destination building: ");
         destination = scanner.nextLine(); 
         flag = false; //set flag to false because it is valid input 
       }
       catch (NoSuchElementException e) {
           System.out.println("Please enter a valid building.");
       }
    }
    
    //try to find and display the shortest path from these 2 buildings
    try {
      displayRoute(start, destination);
    }catch(Exception e) {
      System.out.println("There was no path found between these buildings.");
    }
  }

  /**
   * Prints the shortest path between the 2 buildings, all the buildings on the way and estimated
   * walking time for each segment, and the total time it takes to walk the path.
   */
  @Override
  public void displayRoute(String from, String to) {
    String start = from; //reference variable for the start building
    String destination = to; //reference variable for the destination building 
    
    //The graph with the shortest path 
    PathResultInterface graph = reference.getShortestPathBetween(start, destination);
    
    //check if graph is null, then there was no file loaded and the graph is empty
    if(graph == null) {
      System.out.println("This graph is empty.");
      System.out.println("The buildings on the path: none");
      System.out.println("Estimated walking time for each segment: 0.0");
      System.out.println("Total estimated walking time for the whole path: 0.0");
    }
    //else the graph is not null, and there was a file loaded, so the graph is not empty
    else {
      List<String> buildings = graph.getPathStops(); //List of the buildings on the path
      List<Double> indCost = graph.getSegmentCosts(); //List of the cost of each segment on the path
      double totalCost = graph.getTotalCosts(); //Total cost of the shortest path
      
      //print the shortest path with the buildings on the path, the cost for each segment, and the total cost
      System.out.println("A path was found!");
      System.out.println("The buildings on the path: " + buildings.toString());
      System.out.println("Estimated walking time for each segment: " + indCost.toString());
      System.out.println("Total estimated walking time for the whole path: " + totalCost);
    }
  }

  /**
   * Exits the app and thanks the user for using it.
   */
  @Override
  public void exitCommand() {
    //close the scanner
    scanner.close();
    //print the goodbye statement
    System.out.println("Goodbye! Thank you for using the UW Path Finder App!");
  }
  
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    Backend backend = new Backend();
    Frontend frontend = new Frontend(backend, s);
    frontend.start();
  }
}
