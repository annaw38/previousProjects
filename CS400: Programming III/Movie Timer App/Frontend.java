import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;

/**
 * This class prompts the user for commands and interacts with them based on the data set they load in
 */
public class Frontend implements FrontendInterface{
   
    private BackendInterface<Movie> reference;// reference to the Backend
    private Scanner scnr; // reference of a Scanner Object to read inputs
       
    /**
     * Constructor of Frontend
     * @param reference a reference to the Backend(where most of the things are computted)
     * @param scnr A reference to a Scanner Object
     */
    public Frontend(BackendInterface<Movie> reference, Scanner scnr){
        this.reference = reference;
        this.scnr = scnr;
    }

    /**
     * A method that starts the main command loop for the user. It will call the belowing methods based on user input.
     */
    public void getCommand() {
	// output the basic greetings and tell user how should they give commands
   	System.out.println("Welcome!!");
    	System.out.println("Please enter your command: ");
   	System.out.println("Enter the letter inside []");
    	System.out.println("[L]oad file");
    	System.out.println("[E]xit app");
    	System.out.println("[S]hortest movie");
    	System.out.println("[SP]ecified movie length");
    	String input = scnr.nextLine();

	// if the user types in 'E' it means to terminate the programm
    	while (input.equals("E") == false) {
	    // if the input is valid
      	    if (input.equals("L") || input.equals("S") || input.equals("SP")) {
        	switch (input) {
          	    case "L":
			// load the file
            		System.out.print("Enter the file you want to load: ");
            		String secondInput = scnr.nextLine();
            		this.loadFile(secondInput);
            		break;
          	    case "S":
			// print movies with shortest duration
            		this.shortestMovies();
            		break;
          	    case "SP":
			// ask the user for their lower/upper bound of movie duration
            		boolean gate = true;
            		int lower = -1, upper = -1;
            		while (gate) {
              		    try {
                		System.out.print("Enter the lower bound of movie duration: ");
                		lower = Integer.valueOf(scnr.nextLine());
                		gate = false;
              		    } catch (NumberFormatException e) {
                		this.invalidInput(1, "You should enter an integer");
              		    }
            		}
            		gate = true;
            	        while (gate) {
              		    try {
                		System.out.print("Enter the upper bound of movie duration: ");
                		upper = Integer.valueOf(scnr.nextLine());
                		gate = false;
              		    } catch (NumberFormatException e) {
               			this.invalidInput(1, "You should enter an integer");
              		    }
            		}
            		this.specifiedLengthMovies(lower, upper);
           	 	break;
        	}
      	    }

	    // if the input is invalid
	    else {
        	this.invalidInput(1, "Your entered an incorrect input.");
      	    }

	    // repeatedly asks the user for their input
      	    System.out.println("Enter the letter in []: ");
      	    System.out.println("[L]oad file");
      	    System.out.println("[E]xit app");
      	    System.out.println("[S]hortest movie");
      	    System.out.println("[SP]ecified movie length");
      	    input = scnr.nextLine();
    	}

    	this.exitApp();
    }

    /**
     * Converts specified file into an array of movies by calling the backend's fileReader()
     * @param fileName the name of the file that is about to be read in
     */
    public void loadFile(String fileName){

	// check if the file name is in .csv form
	if (fileName.endsWith(".csv") == false){
	    this.invalidInput(2, "file not in .csv form");
	    return;
	}

	// interact with the user
    	try {
	    reference.fileReader(fileName);
	    System.out.println("Your file is loaded.");
	} catch(FileNotFoundException e){
	    this.invalidInput(2, "The file you enter does not exist.");
	}
    }

    /**
     * Display an array with the movies of the shortest length by calling the backend's withMinDuration()
     */
    public void shortestMovies(){
    	Movie[] movies = reference.withMinDuration();

	// the data is not loaded
        if (movies == null){
            this.invalidInput(3, "Haven't loaded the data yet");
            return;
        }
	// print the data
	System.out.println("Here's your data: ");
	for (Movie tmp : movies){
	  if (tmp == null) {
	    continue;
	  }
	    System.out.println(tmp.getTitle());
	}
	
	return;	
    }

    /**
     * Display an array of movies with the specified length by calling the backend's betweenDuration()
     * @param shortestLength the shortest length that movies
     * @param longestLength the longest length that movies can be to be returned
     */
    public void specifiedLengthMovies(int shortestLength, int longestLength){
      
	// the parameter is incorrect
	if (longestLength < shortestLength){
	    this.invalidInput(1, "Upper bound should be greater than lower bound");
	    return;
	}
	Movie[] movies = reference.betweenDuration(shortestLength, longestLength);
	if (movies == null){
	    this.invalidInput(3, "Haven't loaded the data yet");
	    return;
	}
	if (movies.length == 0){
            System.out.println("No movie are within the specified length");
	}
	// print the data
	System.out.println("Here's your data: ");
        for (Movie tmp : movies){
          if (tmp== null) {
            continue;
          }
            System.out.println(tmp.getTitle());
        }
        return;
    }

    /**
     * A method that exits the app
     */
    public void exitApp(){
    	System.out.println("Closing the app...");
	scnr.close();
	System.out.println("Thank you for using our app!!");
    }

    /**
     * A method that display the error message to the user
     * @param code specifies what is the error
     * @param message the error message
     */
    public void invalidInput(int code, String message){
    	switch(code){
		case 1:
			System.out.println("Input mismatch error: ");
			System.out.println("\t" + message);
			break;
		case 2:
			System.out.println("File read in error: ");
			System.out.println("\t" + message);
			break;
		case 3:
			System.out.println("Command order error: ");
			System.out.println("\t" + message);
			break;
	}
    }
}
