This activity is designed to give you practice making use of anonymous classes and lambda expressions. By the end of this 
activity, you should be able to create new instances of objects 1) using a traditional named class, 2) using an anonymous class,
and 3) using a lambda expression, and you should become comfortable translating code from using one style to another as is
appropriate for a given application.

# Creating a simple Makefile
Create a Makefile within your A06.Lambda folder with two rules: the first rule should use the tartget "run" and should run the 
provided CalculatorApp.java program. And the second rule should compile the provided CalculatorApp.java project into a .class file.
Because of this the first rule should include a dependency on the second rule, so that the .class file is only generated or 
updated as needed. When you compile and run this project, you should see a table with a bunch of dashes in place of the sum, 
difference, and product of the pair of numbers in each row.

# Instantiating Objects
There are three static methods inside CalculatorApp.java (outside of main) that each contain TODO comments. Follow these 
instructions to update the return value of each method. You are not required to add any additional comments beyond those that are
provided. Ensure that your code compile and runs, and prints out a table of sums, differences, and products, rather than the 
dashes that you saw before. Also double check your source code to ensure that each of the three MathOperation objects that you
are returning are instantiated according to the requirements described in the provided TODO comments.
