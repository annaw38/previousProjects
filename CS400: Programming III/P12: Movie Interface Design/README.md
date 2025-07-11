Your responsibility this week is to work with your group of 6 to design the interfaces that you will implement in a future week (P14.Role Code). In another future week (P15.Iterator) you will implement a subclass of the Binary Search Tree class from last week that will allow storing multiple (duplicate) keys in a single node of the tree and add an iterator to the tree to iterate over all keys in a sorted order. This KeyListInterface will help us store multiple objects in each node of the tree. EDIT 09/23: Depending on your app those objects are either car objects, song objects, ingredient objects, meteorite objects, or movie object. The extended tree will implement the IterableMultiKeySortedCollectionInterface that allows iteration over the objects stored in the tree. You can use these types in the interfaces you'll create this week. More specifically, since the backend will need to interact with a tree instance which implements the IterableMultiKeySortedCollectionInterface, the backend interface will need to accept one or two instances (depending on the app you plan to implement) of that type as a parameter to its constructor.

# Create Initial Interface Version
Once you have completed your list, create a set of methods that expose the listed functionality. Find method names that make it clear what a method does and choose clearly named parameters for them. It can be helpful to add any additional information (such as, for example, the desired behavior in edge cases) as a header comment to the method.
Add constructors as comments to the interface definitions. Even though interfaces do not support defining constructors directly, it is good practice to add constructors and their parameter list that every implementation is expected to have. Generally, the constructors should accept references to objects as parameters on which the class depends.
