This activity is designed to give you practice using JavaFX to develop a simple graphical user interface (GUI) driven application.
By the end of this activity, you should be able to 1) desgn and implement interactions between JavaFX Buttons the state of your
application, and 2) develop a small game using JavaFX.

# Creating a JavaFX Game
Create a makefile to compile and run the provided DessertGame.java application.
Create a BorderPane object at the root of your scene graph (replacing the Group object was previouly the root).
Place a new Label object with the text "Score: 0" at the top of your BorderPane.
Create a button with the label "Exit" that will end your application when clicked, and position it at the bottom of your 
BorderPane. Create a new Pane object as the center of your BorderPane, and add eight new Button objects to this BorderPane. 
One of these buttons should be labelled "Dessert" and the other seven should be labelled "Desert". Notice the differences 
between the numbers of s's in these two labels. The goal of this game will be to quickly find and click the "Dessert" button,
without accidentally hitting one of the "Desert" buttons in the process.
Define a private helper method named scrambleButtons that takes two parameters: first) a java.util.Random random number
generator, and second) an array of button objects to randomize the positions of. Within this method, you should call 
setLayoutX() and setLayoutY() on each button to randomize their positions. The X position should be set between 0 and 
600 pixels inclusive, and the Y position should be set between 0 and 400 pixels inclusive. This method should be called once 
when the application first starts running, and then again each time any button is clicked by the user.
Add a private instance field named score to the DessertGame class, and initialize it to zero. Every time the "Dessert" button
is clicked, increment this score by one. Every time any "Desert" button is clicked, decrement this score by one. Any time this
score field is changed, update the text in the Label that was initialized to "Score: 0" above, to instead report the updated 
value of this score field.
While playing this game, you may notice that any button clicked has focus and is drawn differently than the other buttons: 
which make this game a bit too easy. To fix this, call the requestFocus method on your "Exit" button both 1) when the game 
starts, and 2) every time any button is clicked. This should cause the Exit button to retain focus, so that all of the other 
buttons look more similar as the game is played.
Play this game until you have a score of 3. Then run the followin command to capture a screenshot of your game in this state.
Remember to include "&" at the end of your java command when running this program, so that you may execute the following extra
command before your application ends. Then run the following command to capture a screenshot of your running java program:
import -window DessertGame screenshot.png 
This command uses the imagemagick program to create a screenshot of the window named CS400 that is stored in the file
screenshot.png within your working directory. You can open this image in a browser like firefox to check it's contents 
with the command firefox screenshot.png
