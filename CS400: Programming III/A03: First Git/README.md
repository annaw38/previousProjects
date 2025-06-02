This activity is designed to give you practice working with a single branch within a local Git repository. By the end of this activity, you should be able to both inspect the history of past versions within a single branch, and also make new changes of your own to extend this history.

# Setup FirstGit Activity
We've made some improvements to the cs400 script that you have been using over the past two weeks, so start by downloading an updated version of that script to your virtual machine with the following command:
dst=/usr/local/bin/cs400 && sudo wget -O $dst https://pages.cs.wisc.edu/~cs400/cs400 && sudo chmod 755 $dst
Run the cs400 get command, and choose A03.FirstGit from the menu to download a folder named A03.FirstGit containing the starter files for this assignment, and navigate into this folder to complete this activity.
While working through this activity, it is important for your commit message to exactly match what is in quotes below (omitting the quotes themselves). If you make any mistakes or ever want to restart this activity you can do so with the "cs400 restart" command. This will not necessarily erase your previous commits, but when grading we'll only every look for and grade you most recent commits with the exact messages described below.

# Inspect the History of this Repository
Find the commit within the history of this repository that contains the commit message "Joke Explanation", and review the contents of the explanation.txt file within this commit. This file contains references to some wikipedia pages that help explain the origins of the joke below.
Next, find the commit within the history of this repository that contains the commit message "Joke Answer", and review the contents of the answer.txt file within this commit. In fact, copy the contents of this file to your clipboard, for use in the following step.

# Extend the History of this Repository
Return to the head of the main branch within your repository, and then edit the Main.java file there. Within the body of the main method, add a second print statement that prints exactly the text the from within the answer.txt file that you retrieved in the last step.
Use git status to confirm that your edits are not yet a part of a tracked commit, before you: Create a new commit with the message "Joke Program Complete" that incudes the edit you just made. Use git status again to confirm that these edits are now saved as the most recent commit, and use git log to confirm that your commit and commit message are displayed.
Compile and run this java program from the command line. Use git status to confirm that the new .class file you just created is being displayed as an untracked change. Since we don't want to track changes to this file, define a new .gitignore file to hide this from the output of git status. Note that since this .gitignore is not ignoring itself, the git status message should continue to reflect that this file is new and untracked through step 5 below.
Next add the following class header to the top of your Main.java source file. Replace the <Your Name> and <Your NetID> with your own name and netid.
/**
 * Main class for CS400 A03.FirstGit activity Fall2023
 * @author: <Your Name>, <Your NetID>
 **/  
Ensure that your Main.java program continues to compile and run before making a new commit with these changes (and your new .gitignore file). Make the message for this commit "Added Header Comment and .gitignore".
