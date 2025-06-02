This activity is designed to give you practice using Regular Expressions (Regex) to find occurrances of specific patterns within
plain text files. By the end of this activity, you should be able to iteratively develop and test regex patterns using the 
operators and notations described in lecture and listed on the http://regexone.comLinks to an external site. website.

# Practice Writing Regex
Review the contents of the provided Makefile, and of each of the .csv files from p1 that are provided in your A10.Regex folder. 
This Makefile contains five rules with targets p1-p5. The next five steps describe the regex that you will replace the ??? part 
of each rule's command to match. Each csv file contains data from a different p1 project. You can open these files in a standard
text editor to see the headers and data organization. Or if you just want to quickly see the header and top rows in any of these
files, you can use the command head file.csv to print out the contents of those top lines from the command prompt.
Implement and test rule p1: to display all lines in the movies.csv file that contain the exact word "Documentary".
Implement and test rule p2: to display all lines in the cars.csv file that list a car with a price that ends with the digit 9.
Implement and test rule p3: to display all lines in the songs.csv file that list a song with a two word title. The two words in a
two word title should be separated by one or more consecutive whitespace character. Do NOT count punctuation as a word separator.  Note that none of the quoted titles include only two words, so you do NOT need to design your regex to correctly handle song titles that are in quotes or that contain commas.
Implement and test rule p4: to display all lines in the ingredients.csv file that list an ingredient with 1000 or more calories 
per 100 grams. Implement and test rule p5: to display all lines in the meteorites.csv file that list a meteorite with a year 20XX 
where XX is any two digit number number.
Ensure that running each of these targets: make p1 through make p5 produces the expect output in terms of regular expression 
matches that match the above requirements.
