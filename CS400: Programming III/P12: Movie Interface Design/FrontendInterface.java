/**
 * An interface for a class that implements the functionality of the frontend for the Movie Timer app
 */
public interface FrontendInterface<Movies> {

    /*
     * Constructor for the class:
     * public IndividualFrontendInterface(Backend reference, Scanner scnr){}
     */

    /**
     * A method that starts the main command loop for the user. It will call the methods below based on user input.
     */
    public void getCommand();

    /**
     * Converts specified file into an array of movies by calling the backend's fileReader()
     * @param fileName the name of the file that is about to be read in
     */
    public void loadFile(String fileName);

    /**
     * Display an array with the movies of the shortest length by calling the backend's withMinDuration()
     */
    public void shortestMovies();

    /**
     * Display an array of movies with the specified length by calling the backend's betweenDuration()
     * @param shortestLength the shortest length that movies 
     * @param longestLength the longest length that movies can be to be returned
     */
    public void specifiedLengthMovies(int shortestLength, int longestLength);

    /**
     * A method that exits the app
     */
    public void exitApp();

    /**
     * A method that display the error message to the user
     * @param code specifies what is the error
     * @param message the error message
     */
    public void invalidInput(int code, String message);

}
