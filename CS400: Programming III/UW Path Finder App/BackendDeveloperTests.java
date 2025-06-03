import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;

public class BackendDeveloperTests {
    @Test
    public void testSummaryString() {
        Backend b = new Backend();
	Assertions.assertEquals("Campus graph with 0 nodes, 0 edges and a total edge weight of 0.00", b.getDatasetSummaryString());
	Backend b2 = Backend.with("example.dot");
	Assertions.assertEquals("Campus graph with 3 nodes, 4 edges and a total edge weight of 80.03", b2.getDatasetSummaryString());
    }

    /** Test the ability of the backend to take in a file with our input data, and its ability to reject bad files. */
    @Test
    public void testReadData() {
	Backend b = new Backend();
	try {
	    // Whoops, our idealized user made a typo.
	    b.readDataFromFile("exmaple.dot");
	} catch (IOException e) {
	    Assertions.assertEquals("File does not exist", e.getMessage());
	}
	try {
	    // The data is bad.
	    b.readDataFromFile("malformed.dot");
	} catch (IOException e) {
	    Assertions.assertEquals("Bad format", e.getMessage());
	}
	try {
	    // Our user made a typo, but it was in the extension.
	    // Let's be helpful and let them know
	    b.readDataFromFile("bad-extension.dto");
	} catch (IOException e) {
	    Assertions.assertEquals("Invalid extension", e.getMessage());
	}
	try {
	    // This shouldn't fail
	    b.readDataFromFile("example.dot");
	} catch (IOException e) {
	    Assertions.fail("Failed to load simple file that should load without issue");
	}
    }

    /** Test the ability of our backend to correctly determine what path to take in a simplified scenario. */
    @Test
    public void testSimpleNavigation() {
	Backend b = Backend.with("example.dot");
	PathResultInterface path = b.getShortestPathBetween("our house", "your house");
	// Assert that getTotalCosts() is within +/- 0.1 of 30.
	Assertions.assertEquals(30, path.getTotalCosts(), 0.1);
    }

    /** Test our ability to propagate the proper error up the call stack */
    @Test
    public void testNonexistentPath() {
	Backend b = Backend.with("i_cant_get_home.dot");
	try {
	    b.getShortestPathBetween("me", "my house");
	    Assertions.fail("Should encounter NoSuchElementException in prior statement");
	} catch (java.util.NoSuchElementException e) {
	    // All good.
	}
    }

    /** Test our ability to ignore self-loops as they are always sub-optimal when
     * edge weights are >= 0.
     * */
    @Test
    public void testSelfLoopAvoidance() {
	Backend b = Backend.with("i_cant_get_home.dot");
	PathResultInterface p = b.getShortestPathBetween("my house", "my house");
	// If it manages to pick the wrong path then its path cost will be over 1.0
	// This test is not too lenient to see whether that happens with `epsilon=0.01`
	Assertions.assertEquals(0, p.getTotalCosts(), 0.01);
    }
}
