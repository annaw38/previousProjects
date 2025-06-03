// --== CS400 Fall 2023 File Header Information ==--
// Name: Anna Wang
// Email: awang282@wisc.edu
// Group: <your group's name: C20
// TA: Manas Trivedi
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

public interface SortedCollectionInterface<T extends Comparable<T>> {

    public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

    public boolean contains(Comparable<T> data);

    public int size();

    public boolean isEmpty();

    public void clear();
    
}
