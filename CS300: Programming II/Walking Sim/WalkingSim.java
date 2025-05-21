//////////////// FILE HEADER Walking Sim//////////////////////////
// Title:    P02 Walking Sim
// Course:   CS 300 Fall 2022
//
// Author:   Anna Wang
// Email:    awang282@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    None
// Partner Email:   None
// Partner Lecturer's Name: None
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  https://piazza.com/class/l7f41s35yau64i/post/369 - This piazza post helped me 
//understand the coordinates and logic for isMouseOver.
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Random;
import java.io.File;
import processing.core.PImage;

/**
 * This class shows an animation of a walker walking
 */
public class WalkingSim {
  private static int bgColor;
  private static Random randGen;
  private static PImage[] frames;
  private static Walker[] walkers;
  //private static PImage frame;
  
  /**
   * Setup for the walkers to be drawn and the random background color
   * 
   * @param none
   * @return nothing 
   */
  public static void setup() 
  {
    randGen = new Random();
    bgColor = randGen.nextInt();
    frames = new PImage[Walker.NUM_FRAMES];
    walkers = new Walker[8];
    int upperBound = 8;
    int widthUpBound = 801;
    int heightUpBound = 601;
    int randNum = 1 + randGen.nextInt(upperBound);
    //frame = Utility.loadImage("images" + File.separator + "walk-0.png");
    
    for(int index = 0; index < frames.length; index++) 
    {
      frames[index] = Utility.loadImage("images"+File.separator+"walk-"+index+".png");
    }
    //walkers[0] = new Walker();
    //x value between 0 and Utility.width() 
    //y value between 0 and Utility.height()
    //System.out.println(randNum);
    
    for(int i = 0; i < randNum; i++) 
    {
      int xValue = randGen.nextInt(widthUpBound);
      int yValue = randGen.nextInt(heightUpBound);
      walkers[i] = new Walker((float)xValue,(float)yValue);
    }
  }
  
  /**
   * Draws the walkers and the random background color
   * 
   * @param none
   * @return none
   */
  public static void draw() 
  {
    // changes the background color
    Utility.background(bgColor);
    //Utility.image(frames[0],walkers[0].getPositionX(),walkers[0].getPositionY());
    
    //loop through to draw the characters if not null
    for(int j = 0; j < walkers.length; j++) 
    {
      if(walkers[j] != null)  //check to see if it's not null, null means do not do anything
      {  
        
        if(walkers[j].isWalking() == true)
        {
          //makes it so that the walkers don't walk off the screen forever and updates coordinates 3
          //to the right
          walkers[j].setPositionX(walkers[j].getPositionX()+3);
          walkers[j].setPositionX(walkers[j].getPositionX()%800);
          walkers[j].update();
        }
        Utility.image(frames[walkers[j].getCurrentFrame()], walkers[j].getPositionX(), 
        walkers[j].getPositionY());
        
      }
    }
    /*//testing the isMouseOver method
    for(int k = 0; k < walkers.length; k++) 
    {
      if(walkers[j] != null)  //check to see if it's not null, null means do not do anything
      { 
        if(isMouseOver(walkers[0]) == true){
          System.out.println("Mouse is over a walker!");
        }
      }
    }
    */
  }
  
  /**
   * Checks if the mouse is over a walker
   * 
   * @param walker the walker tested if the mouse is over it
   * @return boolean value of if the mouse is over the walker or not
   */
  public static boolean isMouseOver(Walker bob)
  {
    //MouseX > WalkerX - Width/2 AND MouseX < WalkerX + Width/2 AND
    //MouseY > WalkerY - Height/2 AND MouseY < WalkerY + Height/2
    
    //check if the mouse is over the walker, not including the edges
    if(Utility.mouseX() > bob.getPositionX() - frames[0].width/2 && Utility.mouseX() 
       < bob.getPositionX() + frames[0].width/2)
    {
      if(Utility.mouseY() > bob.getPositionY() - frames[0].height/2 && Utility.mouseY() <
         bob.getPositionY() + frames[0].height/2)
      {
        return true;
      }
      return false;
    }
    return false;
  }
  
  /**
   * Checks if mouse is pressed, and if it is then it starts the walkers' walking
   * 
   * @param none
   * @return nothing
   */
  public static void mousePressed()
  {
    for(int j = 0; j < walkers.length; j++) 
    {
      if(walkers[j] != null) 
      {
        //check if the mouse is pressed on the walker, not including the edges, and starts walking
        if(Utility.mouseX() > walkers[j].getPositionX() - frames[0].width/2 && Utility.mouseX() 
            < walkers[j].getPositionX() + frames[0].width/2)
         {
           if(Utility.mouseY() > walkers[j].getPositionY() - frames[0].height/2 && Utility.mouseY() 
              < walkers[j].getPositionY() + frames[0].height/2)
           {
             walkers[j].setWalking(true);
             break;
           }
         }
      }
     }
  }
  
  /**
   * Checks if 'a' or 'A' or 's' or 'S' is pressed and either adds a walker or stops all the walkers 
   * from walking after they start walking 
   * 
   * @param character key the key pressed to add a walker or stop all the walking walkers
   * @return nothing
   */
  public static void keyPressed(char key)
  {
    int widthUpBound = 801;
    int heightUpBound = 601;
    //check if 'a' is upper or lower case
    if(key == 'a' || key == 'A') 
    {
      for(int i = 0; i < walkers.length; i++) 
      {
        if(walkers[i] == null)  //check to see if it's null, add a new Walker object to replace it
        {  
          int xValue = randGen.nextInt(widthUpBound);
          int yValue = randGen.nextInt(heightUpBound);
          walkers[i] = new Walker((float)xValue,(float)yValue);
          Utility.image(frames[0], xValue, yValue);
          break;
        }
      }
    }
    //check if 's' is upper or lower case
    if(key == 's' || key == 'S')
    {
      for(int i = 0; i < walkers.length; i++) 
      {
        if(walkers[i] != null)  //check to see if it's not null, so setWalking will be false 
        {  
          walkers[i].setWalking(false);
        }
      }
    }
  }
  
  //main method to start the application
  public static void main(String[] args) 
  {
    Utility.runApplication(); // starts the application
  }
}
