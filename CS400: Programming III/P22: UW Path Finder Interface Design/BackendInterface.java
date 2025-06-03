import java.io.FileNotFoundException;
import java.util.Optional;
import java.io.IOException;
/**
 * A specification for how our backend class should operate.
 * Note that the actual BackendImplementation class will not be generic in
 * NodeT and EdgeT; it will simply implement these methods with a fixed type
 * for NodeT and EdgeT.
 *
 * Constructors for classes implementing this interface do not need to stick
 * to any format, as data will always be loaded via the readDataFromFile()
 * method some time after any instance has already been constructed.
 * However, a constructor taking a filename which automatically loads it
 * may be useful for writing shorter test cases.
 */
public interface BackendInterface {
    // Recommended constructions
    // -- A constructor that automatically loads a file
    // public BackendImpl(String file2Load);
    // -- Testing constructor
    // public BackendImpl(GraphADT<String, Double> graph)
    // -- Constructor to initialize with empty graph
    // public BackendImpl();

    /** 
     * Attempts to load data from a file.
     * @throws IOException with an appropriate error message:
     * 	       new IOException("File does not exist") if file doesn't exist
     * 	       new IOException("Bad format") if file is in a bad format
     * 	       new IOException("Invalid extension") if the file ends with anything but
     * 	       .dot
     */
    public void readDataFromFile(String inputPath) throws IOException;

    /**
     * Attempts to find the shortest path between nodes `from` and `to`.
     * @return an implementor of PathResultInterface guiding us (get ready for a surprise)
     * 	       from `from` to `to`.
     */
    public PathResultInterface getShortestPathBetween(String from, String to);

    /**
     * Get a human-readable string that gives us an idea of the size of our data set.
     * @return A string telling us the number of nodes, edges, and the sum of all edge
     *         weights in the graph.
     */
    public String getDatasetSummaryString();
}
