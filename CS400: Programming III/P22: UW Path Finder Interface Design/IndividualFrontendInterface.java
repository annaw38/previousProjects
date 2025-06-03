/**
 * An interface for a class that implements the functionality of the frontend for the UW Path Finder app
 */
public interface IndividualFrontendInterface<T> {

    /*
     * Constructor for this class:
     * public IndividualFrontendInterface(Backend backend, Scanner scnr){}
     */

    /**
     * Starts the main command loop for the user; it will call the methods below based on user input.
     */
    public void startLoop();

    /**
     * Loads the file the user inputs 
     * @param fileName the name of the file that is about to be read in
     */
    public void loadFile(String fileName);

    /**
     * Shows statistics about the data set: the number of buildings, number of edges, and 
     * total walking time in the graph. 
     */
    public void showStats();

    /**
     * Based on the user's input for a start and destination building, this method lists the shortest
     * path between them, the buildings on the way, the estimated walking time for each segment, and 
     * the total time it takes to walk the path. 
     * @param startPoint the start building 
     * @param finishPoint the destination building
     */
    public void specificPath(String startPoint, String finishPoint);

    /**
     * A method that exits the UW Path Finder app
     */
    public void exitApp();


}
