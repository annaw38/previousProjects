# Create the HashtableMap Class and Implement Tests
Create a new class called HashtableMap that takes to generic type parameters in this order: <KeyType, ValueType>. Make sure that the class is left in the default package and that the class contains no specific package name that the class is defined in.
In your hashtableMap class, add the definition of the following inner class. We will use this class to store pairs of key-value pairs in our hashtable array.
protected class Pair {

    public KeyType key;
    public ValueType value;

    public Pair(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

}
In the HashtableMap class, include two constructors with the following signatures:
public HashtableMap(int capacity)
public HashtableMap() // with default capacity = 32
Define exactly one protected single-dimensional array as an instance field of the HashtableMap class as the array that we are going to store our key-value pairs in. EDIT 10/28: As described in 5.1, use chaining with linked lists (you can use java.util.LinkedList) to handle collisions. Give the field the name table. Since Java doesn't allow us to instantiate an array with an associated generic type, you should instantiate the array using the raw type (without any generic specifications) and then cast to the complete type that includes generics before storing this reference in the table field. This will produce an unchecked cast warning that can either be ignored, or suppressed by adding the @SuppressWarnings("unchecked") annotation in front of the constructor that contains the line of code with the cast.
Now add in the methods defined by MapADT into your HashtableMap class so that it compiles. For now, leave the body of all those methods empty, or add single return statement if the method's return type is non-void. For methods that return a boolean, add return false; as the return statement. For methods that return an int, add return 0;. For methods that return a non-primitive type, add return null; as the single return statement. Adding these method placeholder implementations will allow us to compile our class and add our tests to it. Later, in section 5, we will replace these method placeholders with the full implementation of the methods.
Develop five new JUnit5 tests within the bottom portion of your HashtableMap class definition. Each test should be designed to test a different feature of your HashtableMap implementation. Be sure to provide descriptive comments for each of these test methods, which describe the high-level functionality that they are designed to check for.
When running your HashtableMap class with the JUnit test runner, you'll likely get the error "HashtableMap mus declare a single constructor". If this is the case, we recommend running your tests through the P25SubmissionChecker class.
Submit the HashtableMap class with the five test methods by Monday, Nov 27.

# Complete the HashtableMap Class Implementation
Use chaining to handle hash collisions, and make use of the java.util.LinkedList class for this purpose. Use the helper class Pair from step 4 to group together key-value pairs within a single object.
Store new key-value pairs in your hash table at the index corresponding to { the absolute value of the key's hashCode() } modulus the HashtableMap's current capacity. When the put method is passed a key that is null EDIT 11/30: it should throw a NullPointerException. If it is passed a key that is already stored in your hash table, it should throw an IllegalArgumentException without making any changes to the hashtable.
Dynamically grow your hashtable by doubling its capacity and rehashing, whenever its load factor becomes greater than or equal to 75%. Define private helper methods to better organize the code that does this.
Implement the remove method to return a reference to the value associated with the key that is being removed. When the key being removed cannot be found in the HashtableMap, this method should throw a NoSuchElementException.
Implement the getSize method to return the number of key-value pairs stored in the HashtableMap, and implement the clear method to remove all key-value pairs from the map (without changing the underlying array capacity). Also implement the getCapacity and containsKey method according to the specification in their respective header comments in MapADT.java.
Throw exceptions as indicated within the comments of the MapADT interface. You should make use of java.util.NoSuchElementException for this. Note that when checking keys for equality, you should check whether their content is the same using the .equals() method, rather than requiring the more strict == equality.
Do NOT make use of any classes from libraries like java.util other than the two exceptions listed above: you may use java.util.LinkedList, and java.util.NoSuchElementException.
