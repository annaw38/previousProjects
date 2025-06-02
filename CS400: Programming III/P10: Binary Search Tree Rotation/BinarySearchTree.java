import java.util.LinkedList;
import java.util.Stack;



/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes of the tree. We will turn this Binary Search Tree into a self-balancing
 * tree as part of project 1 by modifying its insert functionality.
 * In week 0 of project 1, we will start this process by implementing tree rotations.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T> {
        public T data;

        // up stores a reference to the node's parent
        public Node<T> up;
        // The down array stores references to the node's children:
        // - down[0] is the left child reference of the node,
        // - down[1] is the right child reference of the node.
        // The @SupressWarning("unchecked") annotation is use to supress an unchecked
        // cast warning. Java only allows us to instantiate arrays without generic
        // type parameters, so we use this cast here to avoid future casts of the
        // node type's data field.
        @SuppressWarnings("unchecked")
        public Node<T>[] down = (Node<T>[])new Node[2];
        public Node(T data) { this.data = data; }
        
        /**
         * @return true when this node has a parent and is the right child of
         * that parent, otherwise return false
         */
        public boolean isRightChild() {
            return this.up != null && this.up.down[1] == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Inserts a new data value into the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided data argument is null
     */
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
			throw new NullPointerException("Cannot insert data value null into the tree.");
		return this.insertHelper(new Node<>(data));
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the new node
     * in a leaf position within the tree. After this insertion, no attempt is made
     * to restructure or balance the tree.
     * @param node the new node to be inserted
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided node is null
     */
    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
        if(newNode == null) throw new NullPointerException("new node cannot be null");

        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                	return false;
				} else if (compare < 0) {
                    // insert in left subtree
                    if (current.down[0] == null) {
                        // empty space to insert into
                        current.down[0] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[0];
                    }
                } else {
                    // insert in right subtree
                    if (current.down[1] == null) {
                        // empty space to insert into
                        current.down[1] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[1]; 
                    }
                }
            }
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // TODO: Implement this method.
        //check if either the provided child or parent node is null
        if(parent.data == null || child.data == null) {
          throw new NullPointerException("The parent and child node cannot be null.");
        }
        
        //check if the provided parent and child nodes are not related
        if(parent.down[0] != child && parent.down[1] != child && child.up != parent) {
          throw new IllegalArgumentException("The provided nodes are not related.");
        }
        
        //if the child is a right child then rotate left
        if(child.isRightChild() == true) {
          //set child as root if parent doesn't have parent
          if(parent.up == null) {
            root = child;
            child.up = null; //change the child's parent to null as it is the new root
          }
          //set the child to parent's parent if not null
          else {
            child.up = parent.up;
            //check if parent is grandparent's left or right child and set child as that child
            if(parent.up.down[0] == parent) {
              parent.up.down[0] = child;
            }
            else {
              parent.up.down[1] = child;
            }
          }
          //set parent's right child as null if child does not have a left child
          if(child.down[0] == null) {
            parent.down[1] = null;
          }
          //set parent's right child as child's left child if not null
          else {
            parent.down[1] = child.down[0];
            parent.down[1].up = parent; //set child's left child's parent as parent 
          }
          
          //then set parent as left child 
          child.down[0] = parent;
          //set the child as the parent's parent
          parent.up = child; 
        }
        
        //if the child is a left child then rotate right
        else if(child.isRightChild() == false) {
          //set child as root if parent doesn't have parent
          if(parent.up == null) {
            root = child;
            child.up = null; //set child's parent as null since it is the root
          }
          //set the child to parent's parent if not null
          else {
            child.up = parent.up;
            //check if parent is grandparent's left or right child and set child as that child
            if(parent.up.down[0] == parent) {
              parent.up.down[0] = child;
            }
            else {
              parent.up.down[1] = child;
            }
          }
          
          //set parent's left child as null if child does not have a right child
          if(child.down[1] == null) {
            parent.down[0] = null;
          }
          //set parent's left child as child's right child if not null
          else {
            parent.down[0] = child.down[1];
            parent.down[0].up = parent; //set child's right child's parent as parent 
          }
         
          //then set child's right child as parent
          child.down[1] = parent;
          //set the child as the parent's parent
          parent.up = child;
        }
    }

	/**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() returns 0, false if this.size() != 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data a comparable for the data value to check for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(Comparable<T> data) {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This tree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNode(data);
            // return false if the node is null, true otherwise
            return (nodeWithData != null);
        }
    }

    /**
     * Removes all keys from the tree.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Helper method that will return the node in the tree that contains a specific
     * key. Returns null if there is no node that contains the key.
     * @param data the data value for which we want to find the node that contains it
     * @return the node that contains the data value or null if there is no such node
     */
    protected Node<T> findNode(Comparable<T> data) {
        Node<T> current = this.root;
        while (current != null) {
            int compare = data.compareTo(current.data);
            if (compare == 0) {
                // we found our value
                return current;
            } else if (compare < 0) {
                if (current.down[0] == null) {
                    // we have hit a null node and did not find our node
                    return null;
                }
                // keep looking in the left subtree
                current = current.down[0];
            } else {
                if (current.down[1] == null) {
                    // we have hit a null node and did not find our node
                    return null;
                }
                // keep looking in the right subtree
                current = current.down[1];
            }
        }
        return null;
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if(!nodeStack.isEmpty() || popped.down[1] != null) sb.append(", ");
                    current = popped.down[1];
                } else {
                    nodeStack.add(current);
                    current = current.down[0];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.down[0] != null) q.add(next.down[0]);
                if(next.down[1] != null) q.add(next.down[1]);
                sb.append(next.data.toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }

    // Implement at least 3 tests using the methods below. You can
    // use your notes from lecture for ideas of rotation examples to test with.
    // Make sure to include rotations at the root of a tree in your test cases.
    // Give each of the methods a meaningful header comment that describes what is being
    // tested and make sure your tests have inline comments that help with reading your test code.
    // If you'd like to add additional tests, then name those methods similar to the ones given below.
    // Eg: public static boolean test4() {}
    // Do not change the method name or return type of the existing tests.
    // You can run your tests through the static main method of this class.

    //Test case 1: Right rotation using the root of tree as parent and left child as child in a tree with height 2. 
    public static boolean test1() {
        // TODO: Implement this test.
        try {
          //create new BST and insert integers
          BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
          tree.insert(50);
          tree.insert(30);
          tree.insert(10);
          
          
          //set the nodes to their corresponding integers
          Node<Integer> one = tree.root; //parent node
          Node<Integer> two = one.down[0]; //child node
          Node<Integer> three = two.down[0];
          
          
          //right rotation between integers 50 and 30
          tree.rotate(two, one);
          
          String e1 = "[ 10, 30, 50 ]"; //expected In order string after rotation
          String e2 = "[ 30, 10, 50 ]"; //expected Level order string after rotation
     
      
          /*test that the hierarchy matches the expected hierarchy of [30, 10, 50] in level order 
          traversal*/
          if(one.isRightChild() == true && three.isRightChild() == false && one.up == two && 
              two.up == null && two.down[1] == one && two.down[1] == one && two.down[0] == three ) {
            //test that the In order and level order traversals match what's expected
            if(tree.toInOrderString().equals(e1) && tree.toLevelOrderString().equals(e2)) {
              return true;
            }
          }
          return false;
        }
        
        //no exception is expected
        catch(Exception e) {
          return false;
        }
    }
    //Test case 2: Left rotation in middle of tree with height 4 to height 3 using left child of root as parent and its right child as child
    public static boolean test2() {
        // TODO: Implement this test.
        try {
          //create a new BST and insert integers
          BinarySearchTree<Integer> tree2 = new BinarySearchTree<Integer>();
          tree2.insert(250);
          tree2.insert(40);
          tree2.insert(50);
          tree2.insert(100);
          
          //set the nodes to their corresponding integers
          Node<Integer> n1 = tree2.root;
          Node<Integer> n2 = n1.down[0]; //parent node
          Node<Integer> n3 = n2.down[1]; //child node
          Node<Integer> n4 = n3.down[1]; 
         
          //left rotation between 40 and 50
          tree2.rotate(n3, n2);
          
          String e3 = "[ 40, 50, 100, 250 ]"; //expected In order string after rotation
          String e4 = "[ 250, 50, 40, 100 ]"; //expected Level order string after rotation
     
          
          /*check that the hierarchy matches the Level order of [250, 50, 40, 100] */
          if(n2.isRightChild() == false && n4.isRightChild() == true && n4.up == n3 && n2.up == n3
              && n3.down[1] == n4 && n3.down[0] == n2) {
            //test that the In order and level order traversals match what's expected
            if(tree2.toInOrderString().equals(e3) && tree2.toLevelOrderString().equals(e4)) {
              return true;
            }
          }
          return false;
        }
        
        //no exception is expected
        catch(Exception e) {
          return false;
        }

    }
    //test case 3: Testing 2 nodes that are not related as parent and child, child being random node and parent in tree
    public static boolean test3() {
        // TODO: Implement this test.
        try {
          //create a new BST and insert integers
          BinarySearchTree<Integer> tree3 = new BinarySearchTree<Integer>();
          tree3.insert(10);
          tree3.insert(50);
          
          //create 2 related nodes from tree3 and create new node that isn't related to tree3
          Node<Integer> node1 = tree3.root; 
          Node<Integer> node2 = node1.down[1]; //parent node
          Node<Integer> node3 = new Node<Integer>(100); //child node
          
         
          //rotating 2 unrelated nodes, one from tree one unrelated node
          try {
            tree3.rotate(node3, node2);
          }
          
          //Catching an IllegalArgumentException is expected
          catch(IllegalArgumentException i) {
            return true;
          }
          //if an exception is not returned then it is false
          return false;
        }
        
        //if any exception other than IllegalArgumentException is caught then return false
        catch(Exception e) {
          return false;
        }

    }
    //Test case 4: Tree with height 4 to height 3 after a left rotation in left half of tree with middle leaf(left child of root node)
    //as the parent and right child as child
    public static boolean test4() {
      try {
        //Create new BST and insert integers into it
        BinarySearchTree<Integer> tree4 = new BinarySearchTree<Integer>();
        tree4.insert(100);
        tree4.insert(200);
        tree4.insert(150);
        tree4.insert(300);
        tree4.insert(50);
        tree4.insert(60);
        tree4.insert(80);
        
        //set the nodes to their corresponding integers
        Node<Integer> no1 = tree4.root; 
        Node<Integer> no2 = no1.down[1]; 
        Node<Integer> no3 = no2.down[0]; 
        Node<Integer> no4 = no2.down[1]; 
        Node<Integer> no5 = no1.down[0]; //parent node
        Node<Integer> no6 = no5.down[1]; //child node
        Node<Integer> no7 = no6.down[1]; 
       
        //left rotation with 50 and 60 in left half of tree
        tree4.rotate(no6, no5);
        
        String e5 = "[ 50, 60, 80, 100, 150, 200, 300 ]"; //expected In order string after rotation
        String e6 = "[ 100, 60, 200, 50, 80, 150, 300 ]"; //expected Level order string after rotation
 
        //test that the In order and level order traversals match what's expected
        if(tree4.toInOrderString().equals(e5) && tree4.toLevelOrderString().equals(e6)) {
          //check that the parent and children relationships after rotation match what's expected
          if(no6.down[0] == no5 && no6.down[1] == no7 && no5.up == no6 && no7.up == no6 &&
              no1.down[0] == no6 && no6.up == no1) { 
            return true;
          }
        }
        return false;
      }
      
      //no exception is expected
      catch(Exception e) {
          return false; 
      }
    }
    /*Test case 5: Tree with height 4 to height 3 with a right rotation in right half of tree 
     * with right child of root node/middle leaf as parent and left child as child
     * and null in place of parent and/or child node when calling rotate method
     */
    public static boolean test5() {
      try {
        //Create new BST and insert integers into it
        BinarySearchTree<Integer> tree5 = new BinarySearchTree<Integer>();
        tree5.insert(150);
        tree5.insert(100);
        tree5.insert(80);
        tree5.insert(125);
        tree5.insert(175);
        tree5.insert(160);
        tree5.insert(155);
        
        //count the number of NullPointerExceptions thrown in null test cases
        int count = 0;
        
        //set the nodes to their corresponding integers
        Node<Integer> N1 = tree5.root; 
        Node<Integer> N2 = N1.down[0]; 
        Node<Integer> N3 = N2.down[0]; 
        Node<Integer> N4 = N2.down[1]; 
        Node<Integer> N5 = N1.down[1]; //parent node
        Node<Integer> N6 = N5.down[0]; //child node
        Node<Integer> N7 = N6.down[0]; 
        
        //Null test case 1: null in place of child node
        try {
          tree5.rotate(null, N7);
        }
        //NullPointerException is expected 
        catch(NullPointerException n) {
          count++;
        }
        //any exception other than NullPointerException is not expected 
        catch(Exception e) {
          return false;
        }
        
        //Null test case 2: null in place of parent node
        try {
          tree5.rotate(N3, null);
        }
        //NullPointerException is expected here
        catch(NullPointerException n) {
          count++;
        }
        //any exception other than NullPointerException is not expected 
        catch(Exception e) {
          return false;
        }
        
        //Null test case 3: null in place of child and parent nodes
        try {
          tree5.rotate(null, null);
        }
        //NullPointerException is expected here as well
        catch(NullPointerException n) {
          count++;
        }
        //any exception other than NullPointerException is not expected 
        catch(Exception e) {
          return false;
        }
        
        //right rotation with 176 and 160 in right half of tree
        tree5.rotate(N6, N5);
        
        String e7 = "[ 80, 100, 125, 150, 155, 160, 175 ]"; //expected In order string after rotation
        //expected Level order string after rotation
        String e8 = "[ 150, 100, 160, 80, 125, 155, 175 ]"; 
   
        /*check that the tree's in order and level order match what's expected and that all 3 null 
         * test cases caught a NullPointerException (expected count is 3)
         */
        if(tree5.toInOrderString().equals(e7) && tree5.toLevelOrderString().equals(e8) && count == 3) {
          //check that the parent children relationships match what's expected
          if(N6.down[0] == N7 && N6.down[1] == N5 && N7.up == N6 && N5.up == N6 && N1.down[1] == N6 && N6.up == N1) {
            return true;
          }
        }
        
        return false;
      }
      
      //no exception is expected
      catch(Exception e) {
          return false; 
      }
    }
    //Test Case 6: Tree with height 3 to height 4 after right rotation with right child of root/middle 
    //leaf and leaf node as child
    public static boolean test6() {
      try {
        //Create new BST and insert integers into it
        BinarySearchTree<Integer> tree6 = new BinarySearchTree<Integer>();
        tree6.insert(60);
        tree6.insert(40);
        tree6.insert(10);
        tree6.insert(50);
        tree6.insert(80);
        tree6.insert(70);
        tree6.insert(100);
        
        //set the nodes to their corresponding integers
        Node<Integer> root6 = tree6.root; 
        Node<Integer> n40 = root6.down[0]; 
        Node<Integer> n10 = n40.down[0]; 
        Node<Integer> n50 = n40.down[1]; 
        Node<Integer> n80 = root6.down[1]; //parent node
        Node<Integer> n70 = n80.down[0]; //child node
        Node<Integer> n100 = n80.down[1]; 
        
        //right rotate n80 and n70 so that root6's right child is n70, n70's right child is n80, and n80's child is n100
        tree6.rotate(n70,n80);
        
        String e9 = "[ 10, 40, 50, 60, 70, 80, 100 ]"; //expected In order string after rotation
        //expected Level order string after rotation
        String e10 = "[ 60, 40, 70, 10, 50, 80, 100 ]"; 
   
        //check that the tree's in order and level order match what's expected 
        if(tree6.toInOrderString().equals(e9) && tree6.toLevelOrderString().equals(e10)) {
          //check that the parent children relationships match what's expected
          if(n70.down[1] == n80 && n80.down[1] == n100 && n100.up == n80 && n80.up == n70 && root6.down[1] == n70 && n70.up == root6) {
            return true;
          }
        }
        
        return false;
      }
      //no exception is expected to be caught
      catch(Exception e) {
        return false;
      }
      
    }
    
    //Test Case 7: tree with height 3 to height 4 after left rotation with root as parent and root's
    //right child as child and child has a left child
    public static boolean test7() {
      try {
        //Create new BST and insert integers into it
        BinarySearchTree<Integer> tree7 = new BinarySearchTree<Integer>();
        tree7.insert(80);
        tree7.insert(40);
        tree7.insert(10);
        tree7.insert(100);
        tree7.insert(90);
        tree7.insert(110);
        
        //set the nodes to their corresponding integers
        Node<Integer> root7 = tree7.root; //parent node
        Node<Integer> N40 = root7.down[0]; 
        Node<Integer> N10 = N40.down[0]; 
        Node<Integer> N100 = root7.down[1]; //child node
        Node<Integer> N110 = N100.down[1]; 
        Node<Integer> N90 = N100.down[0];
        
        //left rotate N100 and root7 so that root7's right child is N90, root7's left child is N40, and N100's right child is N110
        tree7.rotate(N100, root7);
        
        String e11 = "[ 10, 40, 80, 90, 100, 110 ]"; //expected In order string after rotation
        //expected Level order string after rotation
        String e12 = "[ 100, 80, 110, 40, 90, 10 ]"; 
   
        //check that the tree's in order and level order match what's expected 
        if(tree7.toInOrderString().equals(e11) && tree7.toLevelOrderString().equals(e12)) {
          //check that the parent children relationships match what's expected
          if(tree7.root == N100 && N100.down[0] == root7 && N100.down[1] == N110 && root7.down[1] == N90 && 
              N110.up == N100 && N90.up == root7 && N40.up == root7 && root7.up == N100) {
            return true;
          }
        }
        
        return false;
      }
      //no exception is expected to be caught
      catch(Exception e) {
        return false;
      }
      
    }
    
    //Test Case 8: Tree with height 4 to height 3 after right rotation with root as parent and root's
    //left child as child and has a right child
    public static boolean test8() {
      try {
        //Create new BST and insert integers into it
        BinarySearchTree<Integer> tree8 = new BinarySearchTree<Integer>();
        tree8.insert(50);
        tree8.insert(60);
        tree8.insert(30);
        tree8.insert(45);
        tree8.insert(10);
        tree8.insert(5);
        tree8.insert(15);
        
        //set the nodes to their corresponding integers
        Node<Integer> node50 = tree8.root; //parent node
        Node<Integer> node30 = node50.down[0]; //child node
        Node<Integer> node60 = node50.down[1]; 
        Node<Integer> node45 = node30.down[1]; 
        Node<Integer> node10 = node30.down[0]; 
        Node<Integer> node5 = node10.down[0];
        Node<Integer> node15 = node10.down[1];
        
        //right rotate node30 and node50 so that node 50's new left child is node45,and node30's new right child is node50
        tree8.rotate(node30, node50);
        
        String e13 = "[ 5, 10, 15, 30, 45, 50, 60 ]"; //expected In order string after rotation
        //expected Level order string after rotation
        String e14 = "[ 30, 10, 50, 5, 15, 45, 60 ]"; 
   
        //check that the tree's in order and level order match what's expected 
        if(tree8.toInOrderString().equals(e13) && tree8.toLevelOrderString().equals(e14)) {
          //check that the parent children relationships match what's expected
          if(tree8.root == node30 && node30.down[0] == node10 && node30.down[1] == node50 && 
              node50.down[0] == node45 && node50.down[1] == node60 && node45.up == node50 &&
              node50.up == node30 && node10.up == node30) {
            return true;
          }
        }
        
        return false;
      }
      //no exception is expected to be caught
      catch(Exception e) {
        return false;
      }
      
    }
    
    /**
     * Main method to run tests. If you'd like to add additional test methods, add a line for each
     * of them.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Test 1 passed: " + test1());
        System.out.println("Test 2 passed: " + test2());
        System.out.println("Test 3 passed: " + test3());
        System.out.println("Test 4 passed: " + test4());
        System.out.println("Test 5 passed: " + test5());
        System.out.println("Test 6 passed: " + test6());
        System.out.println("Test 7 passed: " + test7());
        System.out.println("Test 8 passed: " + test8());
    }

}