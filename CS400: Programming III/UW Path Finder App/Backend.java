import java.util.regex.Pattern;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.stream.Stream;
public class Backend implements BackendInterface {
    private DijkstraGraph<String, Double> campusGraph;
    private Double totalGraphCost = 0.0;
    public Backend() {
	campusGraph = new DijkstraGraph(new PlaceholderMap());
    }
    /** A "smart constructor" that takes an input file, but returns null if
     * there was any error encountered in loading the file.
     * For testing purposes only, ideally. This is done so we don't have to
     * encapsulate every call to `readDataFromFile` with a `try` block.
     * Handy, right? **/
    public static Backend with(String inputFile) {
	Backend b = new Backend();
	try {
	    b.readDataFromFile(inputFile);
	} catch (Exception e) {
	    return null;
	}
	return b;
    }

    private static Pattern nameAndBody = Pattern.compile("graph\\s+(\\w+)\\s+\\{([^}]+\\})");
    private static Pattern extension = Pattern.compile("\\.dot$");
    /** A regular expression matching a single record in our .dot graph file.
     * Has the format "from" -- "to" [key=number]; 
     * It is tolerant to whitespace */
    private static Pattern singleRecord = Pattern.compile("\"([^\"]+)\"\\s*\\-\\-\\s*\"([^\"]+)\"\\s*\\[\\s*seconds\\s*=\\s*(\\d+\\.\\d+)+\\]\\s*;");
    public void readDataFromFile(String inputPath) throws IOException {
	if (!extension.matcher(inputPath).find()) {
	    throw new IOException("Invalid extension");
	}
	String theFile;
	try {
	    theFile = Files.readString(Path.of(inputPath));
	} catch (IOException i) {
	    throw new IOException("File does not exist");
	}
	// We now have a Scanner object we are ready to read from
	Matcher graphHeader = nameAndBody.matcher(theFile);
	graphHeader.find();
	String name = graphHeader.group(1);
	String body = graphHeader.group(2);
	Matcher recordMatcher = singleRecord.matcher(body);
	while (recordMatcher.find()) {
	    String nodeFrom = recordMatcher.group(1);
	    String nodeTo = recordMatcher.group(2);
	    String secondsStr = recordMatcher.group(3);
	    Double seconds;
	    try {
		seconds = Double.parseDouble(secondsStr);
	    } catch (java.lang.NumberFormatException e) {
		throw new IOException("Bad format");
	    }
	    recordEdge(nodeFrom, nodeTo, seconds);
	}
    }
    private void recordEdge(String from, String to, Double seconds) {
	campusGraph.insertNode(from);
	campusGraph.insertNode(to);
	campusGraph.insertEdge(from, to, seconds);
	totalGraphCost += seconds;
    }
    public PathResultInterface getShortestPathBetween(String from, String to) {
	return new PathResultInterface() {
	    private List<String> stops = campusGraph.shortestPathData(from, to);
	    // This is of type DijkstraGraph.SearchNode but the 
	    // type is pretty verbose
	    private DijkstraGraph<String, Double>.SearchNode searchNode = campusGraph.computeShortestPath(from, to);

	    public List<String> getPathStops() { return stops; }
	    public List<Double> getSegmentCosts() {
		List<Double> result = new LinkedList();
		DijkstraGraph<String, Double>.SearchNode curSearchNode = searchNode;
		// None of the public methods of the DijkstraGraph expose
		while (curSearchNode.predecessor != null) {
		    String fromName = curSearchNode.predecessor.node.data;
		    String toName = curSearchNode.node.data;
		    result.add(0, campusGraph.getEdge(fromName, toName));
		    curSearchNode = curSearchNode.predecessor;
		}
		return result;
	    }
	};
    }
    public String getDatasetSummaryString() {
	return String.format(
		"Campus graph with %d nodes, %d edges and a total edge weight of %.2f",
		campusGraph.getNodeCount(),
		campusGraph.getEdgeCount(),
		getTotalEdgeWeight()
		);
    }
    private double getTotalEdgeWeight() {
	return totalGraphCost;
    }
}
