# Update the style rules for this game from A09
In this section we'll be updating the style rules defined within this document. 

Update the provided style rule for button elements so that the size of the text label within each button is 16px. Also give the buttons rounded corners by setting their border-radius to 8px.

To give the player more feedback about which button their cursor is currently hovering over, add a css rule that swaps the color and background color (as defined in the style rule for elements of type button) of any button when the mouse is above them. You can use the css-selector button:hover to select button elements that the mouse is currently hovering over.
Add another new style rule that makes the "Score: 0" text appear bold. Specifically, we'd like the font-weight for this text to be set to 800.

# Update the HTML for this game
Update the meta tag with the name author. 

Add 7 more button object to the body of this html document. The label on each of the buttons should be "Desert" notice the difference between the single 's' in this label, vs the double 'ss' in the label of the provided button. Because the position of these buttons is set to absolute, all 8 of these buttons should be stacked one on top of the other at position 0,0. But we'll fix this in the next step.

# Update the Javascript for this game
Notice that the provided randomizeButtonPositions function is being called within the body element's onload handler when the page is loaded. We've provided a partial implementation of this method to randomize the positions of each button in the page. You just need to update the css selector within this method from FIXME to something that selects all button objects. After doing this, you should see the buttons scattered to different random locations. For the purposes of this assignment it's alright if these buttons sometimes overlap.

Create a new javascript function called handleDessertClick (note the two 'ss'). This method should do two things: First, it should call the randomizeButtonPositions method that we just got working. Second, it should increment the points displayed within the "Score: 0" text on this page. To keep track of this score as it changes, create a new score variable outside of these method definitions that is initialized to 0. Then after updating this variable, you can update the Score: display to reflect this variable's updated value. Set this function to be called whenever the Dessert button is clicked.

Next define a similar function called handleDesertClick (note the single 's'). The only difference between this function and the one defined in the previous step, is that this method should decrement rather than increment your score variable before displaying it's value. This function should also call randomizeButtonPositions.

