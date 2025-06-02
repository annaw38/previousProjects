This activity is designed to give you practice using JavaFX to develop a simple graphical user interface (GUI) driven application.
By the end of this activity, you should be able to 1) create a simple interactive GUI application, and 2) compile and run that 
application on your local machine and on a remote linux machine through RDP.

# Creating a simple Makefile
Create a Makefile in this assignment folder that contains two rules: one rule with the target run: with a command that will 
run the provided FirstJavaFX application, and a second rule with the target FirstJavaFX.class: with a command to compile your
FirstJavaFX.java source code. Ensure that the first of these rules depends on the second so that your source code is recompiled whenever this is needed. Note that the commands for compiling and running this FirstJavaFX application are (note that these refernce the renamed javafx folder that you downloaded as part of this setup):
javac --module-path ../javafx/lib --add-modules javafx.controls FirstJavaFX.java  
java --module-path ../javafx/lib --add-modules javafx.controls FirstJavaFX
Ensure that compiling and running this starter application leads to the creation of a blank window. And the clicking the "X" 
in the upper-right corner to close this window leads to the application ending and the output "Goodbye" being printed out to 
the console

# Developing a simple JavaFX Application
Use the stage's setTitle() method to change the window's title to read: "CS400" (without the quotes). We'll use this title later
to capture a screenshot of this application.
Next create a Label object that contains the following quote (organized into three lines as follows) updated formatting 10/22:
The key to making programs fast 
is to make them do practically nothing.
-- Mike Haertel
Ensure that this label object is passed as an argument into the Group object's construct, so that it is visible on the screen
after compiling and running your code.

Next we'll add some graphics to this graphical user interface. Pass the arguments 160, 120, 30 into the constructor of the 
Circle class to create a new Circle object, and pass this object into the Group's constructor in your code.
Now create a new Polygon object by passing the following corner coordinates into it's constructor: 160, 120, 200, 220, 120, 
220. Like the previous label and circle, add this object to the group so that it can be seen when your program is compiled 
and run.Double check that the strings and numbers used in your code match exactly what is described above. Then create a
screenshot of your work by first compiling and then running your java program with an "&" at the end of the java command. 
This "&" character will allow your program to run in the background so that you can run the following command from the 
terminal before your java program exits. The command to capture a screenshot of your running java program is:
import -window CS400 screenshot.png 

This command uses the imagemagick program to create a screenshot of the window named CS400 that is stored in the file
screenshot.png within your working directory. You can open this image in a browser like firefox to check it's contents
with the command firefox screenshot.png
