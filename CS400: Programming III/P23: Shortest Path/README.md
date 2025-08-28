# Implement Tests
Develop the following new JUnit5 tests within the bottom portion of your DijkstraGraph class definition:
Create a test that makes use of an example that you traced through in lecture, and confirm that the results of your implementation match what you previously computed by hand.

Create another test using the same graph as you did for the test above, but check the cost and sequence of data along the shortest path between a different start and end node.

Create a test that checks the behavior of your implementation when the nodes that you are searching for a path between existing nodes in the graph, but there is no sequence of directed edges that connects them from the start to the end.

# Implement Dijkstra's Algorithm
Your job for this assignment is to implement one protected helper method, and then to use that method to implement two public methods in this class. All of the code that you write for this assignment should go into one of these three methods. You can write additional private helper methods to better organize this code. But you are not allowed to add any extra fields or inner classes.

We have provided you with a SearchNode inner class definition that will be useful for keeping track of the nodes in your priority queue, along with associated information about the paths leading from the start node to them: for example the cost of that complete path and the predecessor SearchNode along that path. You should not modify this inner class for the purpose of this assignment.

Start by implementing the computeShortestPath helper method using Dijkstra's Algorithm. Note the import statements at the top of this file. They should be used within your computeShortestPath. Your solution should:
make use of a java.util.PriorityQueue to greedily explore shorter path possibilities before longer ones within your computeShortestPath definition.

Make use of MapADT and PlaceholderMap to keep track of the nodes that you have already visited (and found the shortest path reaching them from the start node). To achieve this, you can use the map as a set by inserting graph nodes as keys and values into the map and use the containsKey method to check if a node is contained in the set. 

List and LinkedList can be used for the return type in your shortestPathData method (described below).
And all of these methods should throw a NoSuchElementException when the start and end data passed into them as arguments either 1) do not correspond to the data held in any nodes within the graph, or 2) there is no directed path that connects from the start node to the end node. Note that this is only documented for the protected helper method, but that it should be thrown through either of the public helper methods under these conditions.

Once your computeShortestPath method is working, use that protected helper method to implement the public shortestPathData and shortestPathCost methods.
