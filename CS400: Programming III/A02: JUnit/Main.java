/**
 * This is a simple application that demonstrates the use of MyList.
 */
public class Main {

    public static void main(String[] args) {
	// populate zoo with ant, bear, cat, dog, and elephant
	ListADT<String> miniZoo = new MyList<>();
	miniZoo.add("ant");
	miniZoo.add("bear");
	miniZoo.add("cat");
	miniZoo.add("dog");
	miniZoo.add("elephant");
	System.out.println("Miniature Zoo started with: "+miniZoo);

	// TODO: add code here to trade a cat for a fish
	// 1) remove the cat element miniZoo
	// 2) add a new element with the value "fish" to miniZoo
	miniZoo.remove(2);
	miniZoo.add("fish");
	System.out.println("Miniature Zoo ended with: "+miniZoo);
    }

}
