This activity is designed to give you practice using pipes with a few different bash commands to quickly extract data from a csv 
encoded file. By the end of this activity you should be able to develop algorithms that operate on streams of data, and identify 
tasks that are well suited to stream processing.

# Find the Highest Midwest Price Index from 2023
The first question we'd like to answer is "Which of the rows in this table has the highest Midwest Price Index in Oct 2023?" 
in other words, which row has the largest value in the 5th column from the left (counting the left most column as the 1st).
We'll start this command by using cat to output the contents of the midwestConsumerPriceIndex.csv data file.
Pipe the output of the cat program as the input into the cut program. We'll use two arguments to extract the data from column 5
from each row: first we'll use -d, to set commas as delimiters between columns or fields, and second we'll use -f5 to extract 
the fith such field from each row.
Next we want to sort this data numerically. So we'll pipe the output of the cut command as input into the sort command. We'll 
also pass the -n option to the sort command, so that it sorts these line numerically rather than alphabetically.
Since we only want the last (and largest) value in this list, we'll pass the entire list to the tail command with the argument 
-n1. This should only return the last 1 line of the previous command's output.
Next, we'd like to search for the value output by tail within the midwestConsumerPriceIndex.csv file. To do this we are going 
to make use of two commands: xargs and printf to create the grep command that we'd like to run in the final step. The command 
that we'd like to run is "grep %s midwestConsumerPriceIndex.csv" but we'd like to replace the %s with the output from the 
previous command. printf is a command that can take the exact string above followed by the value that we'd like to replace the %s by, 
and it will do that substitution for us. Since we'd like this final argument to come from the output of the previous tail command, 
we'll use xargs to use that output as an extra argument to printf. The complete command that we'll pipe the output from tail 
into is: xargs printf "grep %s midwestConsumerPriceIndex.csv". Running this command should result in printing out the desired 
grep command, rather than running it, so we'll change this in the next step.
In order to run the command printed to standard out by xargs, we'll pipe this output to the program bash. This will cause that 
command to be executed, so that we can see the one line from the csv file that contains the exact number that we are searching 
for: which corresponds to the highest Midwest Price Index from 2023.
Once you have this entire command working, copy in into your Makefile so that it is run as a part of the rule with the target 
p1 (replacing/extending the provided cat command within this rule). After doing this, running "make p1" should produce the 
same output as running this command directly from the command line did before.

# Find the Average US Percent Change from 2022 for Food
Next we'd like to compute the average percent change in US food indexes since last year. These numbers that we are interested 
in averaging are in the third column through the first 10 rows. We'll start liked we did above by using cat to output the 
contents of the provided file.
Next we'll use the head command to extract only the first 10 lines of the output from cat. No arguments are needed to get these 
first 10 lines.
However we would like to skip the first line since it only contains header labels. We can use the tail command with the argument
-n+2 to extra all last lines in this file starting with the second.
Next we want to extract the 3rd column from these remaining lines. Use the cut command to accomplish this. We can uses similar 
arguments two what we used in the previous section, but will extract the third column rather than the fifth.
Now we want to paste together each of these lines into a single expression that adds them all together. Do this by sending the 
output of the cut command to the paste command. We'll use the arguments -sd+ to insert a + character between each pair of number
s on different lines within a single output source (the output of the previous cut command).
The bc command is a basic calculator that can process and evaluate the sum that we've just pasted together. But we'd also like 
to divide this number by 9 to compute the average of these nine values. For this we'll use the xargs and printf commands 
together again as we did in the previous section. The full command that we'll pipe the paste commands output into is: xargs 
printf "scale=2; ( %s )/9\n". The scale=2; at the beginning will instruct bc to give us back a floating point answer with two 
digits of decimal precision. The rest will insert the sum from the previous paste step into parentheses before dividing by 9.
You can run this command to ensure that the full equation that we'd like to calculate is correctly preceded by scale=2.
The last step is to send this calculation to the bc calculator. No arguments should be needed for this: running this command 
should output the average percent change.
Once you have this entire command working, copy in into your Makefile so that it is run as a part of the rule with the target 
p2 (replacing/extending the provided cat command within this rule). After doing this, running "make p2" should produce the 
same output as running this command directly from the command line did before.
