// --== CS400 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: G40
// Group TA: Zheyang Xiong
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;


/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // implement in step 5.3
      //if start or end doesn't correspond to a graph node 
      if(start == null || end == null || nodes.get(start) == null || nodes.get(end) == null) {
        throw new NoSuchElementException("The data item in the starting start and destination node can't be null.");
      }
     
      //create a new priority queue of searchNodes and a placeholder map
      PriorityQueue<SearchNode> pq = new PriorityQueue<SearchNode>();
      PlaceholderMap<Node, NodeType> visitedMap = new PlaceholderMap<Node, NodeType>();
      
      EdgeType cost = null; //cost of the edge 
      SearchNode suc = null; //reference node for the successor SearchNode
      
      //create a new SearchNode for start and add it to the priority queue
      SearchNode current = new SearchNode(nodes.get(start), 0, null);
      //add the current SearchNode with the start data into the priority queue
      pq.add(current);
      
      //while the priority queue is not empty, pull the smallest cost searchNode off the queue
      //if it is empty then there is no path or it is the same node
      while(!pq.isEmpty()) {
        //remove minimum cost SearchNode 
        current = pq.poll();
       
        //check if the current node is in the map, if not, add it to the map so it is now visited
        if(!visitedMap.containsKey(current.node)) {
          //put the current node in the path 
          visitedMap.put(current.node, current.node.data);
        
          //then add the nodes that the current SearchNode's node has an edge going towards that node
          for(int i = 0;i<current.node.edgesLeaving.size();i++) {
            //the node that has an edge going to it from the current SearchNode's node
            Node next = current.node.edgesLeaving.get(i).successor;
            cost = getEdge(current.node.data, next.data); //the cost of the edge
            //create a new SearchNode for the next node and add it to the priority queue 
            //cost is the previous SearchNode's cost plus the cost of the edge
            suc = new SearchNode(next, (current.cost + cost.doubleValue()), current);
            pq.add(suc);
          }
        }
        
       //check if the current search node's node's data matches end
       if(current.node.data.equals(end)) {
          return current;
       }      
      }
      //if the map does not contain the end node then throw NoSuchElementException
      if(!visitedMap.containsKey(nodes.get(end)))
        throw new NoSuchElementException("There is no path from start to end.");
      
      //return the current node;
      return current;
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // implement in step 5.4
      //create a new Linked list to put the data items of the nodes of the shortest path into
      List<NodeType> nodes = new LinkedList<NodeType>();
      //find the last node in the path 
      SearchNode path = computeShortestPath(start, end);
      //add end to the list 
      nodes.add(end);
      //while the predecessor of the current node is not null add its node's data into the linked list
      while(path.predecessor != null) {
        //add the predecessor of the current node to the list at index 0
        nodes.add(0, path.predecessor.node.data);
        //set path to the current path's predecessor
        path = path.predecessor;
      }
      //return the list of data items from the nodes along the shortest path
      return nodes;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // implement in step 5.4
      //return the cost of the shortestPath from start to end
      return computeShortestPath(start, end).cost;
    }

    // TODO: implement 3+ tests in step 4.1
    /**
     * Test 1: Same graph as the one traced through in lecture; find the path and its cost for A to E.
     */
    @Test
    public void testLectureExample() {
      //create a new graph that has strings as the nodetype
      DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
      
      //insert the nodes used in lecture into the graph 
      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");
      
      //insert the edges used in lecture into the path
      graph.insertEdge("A", "B", 4);
      graph.insertEdge("A", "C", 2);
      graph.insertEdge("A", "E", 15);
      graph.insertEdge("B", "D", 1);
      graph.insertEdge("B", "E", 10);
      graph.insertEdge("C", "D", 5);
      graph.insertEdge("D", "E", 3);
      graph.insertEdge("D", "F", 0);
      graph.insertEdge("F", "D", 2);
      graph.insertEdge("F", "H", 4);
      graph.insertEdge("G", "H", 4);
      
      //test that the path cost from "A" to "E" equals 8, what was computed in lecture 
      Assertions.assertEquals(8, graph.shortestPathCost("A", "E"));
      //test that the nodes on the path from "A" to "E" have the nodetype of A, B, D, and E
      Assertions.assertEquals("[A, B, D, E]", graph.shortestPathData("A", "E").toString());
    }
    
    /**
     * Test 2: Same graph as the one above and the one in lecture; find the path and its cost for A to F.
     */
    @Test
    public void testLectureExampleDifferentPath() {
      //create a new graph
      DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
      //insert the same nodes as in the previous test case into this graph
      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      graph.insertNode("G");
      graph.insertNode("H");
      
      //insert the same edges as in the previous test case into this graph
      graph.insertEdge("A", "B", 4);
      graph.insertEdge("A", "C", 2);
      graph.insertEdge("A", "E", 15);
      graph.insertEdge("B", "D", 1);
      graph.insertEdge("B", "E", 10);
      graph.insertEdge("C", "D", 5);
      graph.insertEdge("D", "E", 3);
      graph.insertEdge("D", "F", 0);
      graph.insertEdge("F", "D", 2);
      graph.insertEdge("F", "H", 4);
      graph.insertEdge("G", "H", 4);
      
      //check that the shortestPathCost from A to F is 5 and that the path is A, B, D, F
      Assertions.assertEquals(5, graph.shortestPathCost("A", "F"));
      Assertions.assertEquals("[A, B, D, F]", graph.shortestPathData("A", "F").toString());
    }
    
    /**
     * Test case 3: Test the behavior of implementation when node exists, but there's no path to that 
     * node from the starting node.
     */
    @Test
    public void testNoPath() {
      //create a new graph of integers as the nodetype
      DijkstraGraph<Integer, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
      
      //insert 4 new nodes
      graph.insertNode(0);
      graph.insertNode(1);
      graph.insertNode(2);
      graph.insertNode(3);
      
      //insert edges between the nodes
      graph.insertEdge(0, 1, 4);
      graph.insertEdge(0, 2, 18);
      graph.insertEdge(1, 2, 9);
      graph.insertEdge(2, 1, 3);
      graph.insertEdge(1, 0, 3);
      
      int count = 0; //number of exceptions caught
      
      //test 1: find the shortest path cost between 2 nodes in the graph, but there is no path
      try{
        Assertions.assertEquals(null, graph.shortestPathCost(3, 0));
      }catch(NoSuchElementException e) {
        count++;
      }catch(Exception e) {
        Assertions.fail("A NoSuchElementException was expected, but not caught.");
      }
      
      //test 2: find the shortest path cost between a node in the graph and a nonexistent node
      try{
        Assertions.assertEquals(null, graph.shortestPathCost(0, 8));
      }catch(NoSuchElementException e) {
        count++;
      }catch(Exception e) {
        Assertions.fail("A NoSuchElementException was expected, but not caught.");
      }
      
      //test 3: find the shortest path cost between 2 nonexistent nodes 
      try{
        Assertions.assertEquals(null, graph.shortestPathCost(192, 8));
      }catch(NoSuchElementException e) {
        count++;
      }catch(Exception e) {
        Assertions.fail("A NoSuchElementException was expected, but not caught.");
      }
      
      //test 4: find the shortest path of nodes between 2 nodes in the graph with no path 
      try {
        Assertions.assertEquals("[]", graph.shortestPathData(3, 0).toString());
      }catch(NoSuchElementException e) {
        count++;
      }catch(Exception e) {
        Assertions.fail("A NoSuchElementException was expected, but not caught.");
      }
      
      //test 5: find the shortest path of nodes between a node in the graph and a nonexistent node
      try {
        Assertions.assertEquals("[]", graph.shortestPathData(0, 8).toString());
      }catch(NoSuchElementException e) {
        count++;
      }catch(Exception e) {
        Assertions.fail("A NoSuchElementException was expected, but not caught.");
      }
      
      //test 6: find the shortest path of nodes between 2 nonexistent nodes
      try {
        Assertions.assertEquals("[]", graph.shortestPathData(192, 8).toString());
      }catch(NoSuchElementException e) {
        count++;
      }catch(Exception e) {
        Assertions.fail("A NoSuchElementException was expected, but not caught.");
      }
      
      //since a NoSuchElementException is expected to be caught for all the tests, count is expected to be 6
      Assertions.assertEquals(6, count);
    }
    
    /**
     * Test case 4: test that one of two possible paths with the same cost is returned
     */
    @Test
    public void testTwoPossiblePaths() {
      //create a new graph using string as the nodetype
      DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
      
      //insert 6 nodes into the graph
      graph.insertNode("A");
      graph.insertNode("B");
      graph.insertNode("C");
      graph.insertNode("D");
      graph.insertNode("E");
      graph.insertNode("F");
      
      //insert some edges between the nodes
      graph.insertEdge("A", "B", 1);
      graph.insertEdge("B", "C", 9);
      graph.insertEdge("B", "D", 3);
      graph.insertEdge("C", "B", 2);
      graph.insertEdge("C", "F", 4);
      graph.insertEdge("D", "B", 2);
      graph.insertEdge("D", "C", 5);
      graph.insertEdge("E", "A", 19);
      graph.insertEdge("E", "D", 6);
      graph.insertEdge("E", "F", 15);
      graph.insertEdge("F", "D", 1);
      
      //check that the shortestPathCost from C to D is 5 
      Assertions.assertEquals(5, graph.shortestPathCost("C", "D"));
      
      //check that the shortest path data is either C, B, D or C, F, D
      //create a list for each possible path that can be returned
      List<String> r1 = new LinkedList<String>();
      r1.add("C");
      r1.add("B");
      r1.add("D");
      
      List<String> r2 = new LinkedList<String>();
      r2.add("C");
      r2.add("F");
      r2.add("D");
      
      //store the actual path returned in another list
      List<String> actual = graph.shortestPathData("C", "D");
      
      //check that the one of the toStrings of the expected possible paths match the actual toString of the 
      //path returned
      if(!actual.toString().equals(r1.toString()) && !actual.toString().equals(r2.toString())) {
        Assertions.fail();
      }
    }

}