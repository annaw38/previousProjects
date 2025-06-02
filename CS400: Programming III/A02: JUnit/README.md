# Practice Compiling and Running Java Code from the Command Line
Review the provided definitions for the ListADT interface, the MyList class, and the sample Main application that makes use of them.
Use the javac command to compile each of these three source files, and then use the java command to run the entry point within Main.
Follow the instructions within Main.java's TODO comment, and ensure that these changes compile and run as expected.
# Practice Formatting, Compiling, and Running JUnit tests
Review, compile, and run the provided test code within TestMyList.java. Notice that these are not JUnit tests, but instead java methods that print test results through System.out.
Find a way to change the code within MyList.java so that exactly one of these three tests fails (and the other two continue to pass). Document with comments and make this change within MyList.java. This will help us see whether both passing and failing tests are being reported correctly.
Next make a copy of the TestMyList.java file called MyFirstJUnit.java:
Within this copy, rename the class defined within this file, and remove its main method.
Then turn the remaining test methods into JUnit tests. Start by commenting out the code that allows them to fail, so that all three tests always pass, and you can confirm that you are able to compile and run these always passing tests.
Then add Assertions to each test, so that the same conditions causing them to fail in TestMyList are now causing them to throw assertions in MyFirstJUnit.
Confirm that your MyFirstJunit tests are producing the same results as the original TestMyList class, when they are compiled and run using the test runner within the provided junit5.jar file (that is right outside your working directory).
