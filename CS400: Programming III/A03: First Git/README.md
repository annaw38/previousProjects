# Inspect the History of this Repository
Find the commit within the history of this repository that contains the commit message "Joke Explanation", and review the contents of the explanation.txt file within this commit. This file contains references to some wikipedia pages that help explain the origins of the joke below.

Next, find the commit within the history of this repository that contains the commit message "Joke Answer", and review the contents of the answer.txt file within this commit. In fact, copy the contents of this file to your clipboard, for use in the following step.


# Extend the History of this Repository
Return to the head of the main branch within your repository, and then edit the Main.java file there. Within the body of the main method, add a second print statement that prints exactly the text the from within the answer.txt file that you retrieved in the last step.

Use git status to confirm that your edits are not yet a part of a tracked commit, before you: Create a new commit with the message "Joke Program Complete" that incudes the edit you just made. Use git status again to confirm that these edits are now saved as the most recent commit, and use git log to confirm that your commit and commit message are displayed.

Compile and run this java program from the command line. Use git status to confirm that the new .class file you just created is being displayed as an untracked change. Since we don't want to track changes to this file, define a new .gitignore file to hide this from the output of git status. Note that since this .gitignore is not ignoring itself, the git status message should continue to reflect that this file is new and untracked through step 5 below.

Ensure that your Main.java program continues to compile and run before making a new commit with these changes (and your new .gitignore file). Make the message for this commit "Added Header Comment and .gitignore".
