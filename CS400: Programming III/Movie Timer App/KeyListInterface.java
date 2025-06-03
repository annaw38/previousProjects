// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: <your group's name: C20
// TA: Manas Trivedi
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

/**
 * This is the interface of a type that will allow us to store duplicate keys in a single node of a tree.
 */
public interface KeyListInterface<T extends Comparable<T>> extends Comparable<KeyListInterface<T>>, Iterable<T> {

    /**
     * Adds another object with the same key to the list.
     * @param newKey new object that maps to the same key as all objects in the list
     */
    public void addKey(T newKey);

    /**
     * Checks if the list contains key.
     * @param key the key object to check for
     */
    public boolean containsKey(T key);

}