////////////////////////////////////////////////////////////////////////////////
// Main File:        sequence.c
// This File:        sequence.c
// Other Files:      n/a
// Semester:         CS 354 Lecture 002      SPRING 2024
// Grade Group:      gg_2_  (See canvas.wisc.edu/groups for your gg#)
// Instructor:       deppeler
//
// Author:           Anna Wang
// Email:            awang282@wisc.edu
// CS Login:         annaw
//
/////////////////////////// SEARCH LOG ////////////////////////////////
// Online sources: do not rely web searches to solve your problems,
// but if you do search for help on a topic, include Query and URLs here.
// IF YOU FIND CODED SOLUTIONS, IT IS ACADEMIC MISCONDUCT TO USE THEM
//                               (even for "just for reference")
// Date:   Query:                      URL:
// ---------------------------------------------------------------------
//
//
//
//
//
// AI chats: save a transcript.  No need to submit, just have available
// if asked.
/////////////////////////// COMMIT LOG  //////////////////////////////
//  Date and label your work sessions, include date and brief description
//  Date:   Commit Message:
//  -------------------------------------------------------------------
//  2/13        Checked for the correct number of CLAs and printed the usage statement
//                       if it doesn't meet the expected number
//  2/17        Finished the equation function and main
//  2/24        Added function headers
//
//
///////////////////////// OTHER SOURCES OF HELP //////////////////////////////
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
// Date:   Name (email):   Helped with: (brief description)
// ---------------------------------------------------------------------------
//
//////////////////////////// 80 columns wide ///////////////////////////////////
#include <stdio.h>
#include <stdlib.h>
// Define a function that computes y=mx+b, with m, x, and b as its parameters.
/* Computes y = mx+b
 *
 * Pre-conditions: needs m, x, and b as its parameters
 * m used as a multiplier of the previous term in the sequence can be any integer
 * x the previous term in the sequence
 * b an integer added to the (m*previous) term
 * return mx+b
 */
int equation(int m, int x, int b){
        int y = (m*x) +b;
        return y;
}

/* Outputs a sequence of a certain size given by the user using the formula x_1
 * = m*x_0 + c.
 *
 * Pre-conditions: needs n, x0, m, and c as parameters
 * n nonzero positive number of values in the sequence
 * x0 the first value in the sequence
 * m the multiplier of the previous term in the sequence
 * c added to the (m*previous) term
 * print the sequence and return 0 if everything worked as intended, otherwise exit(1)
 */
int main( int argc, char *argv[]){
        if(argc !=5){
                printf("Usage: ./sequence n x0 m c   ==> where next value in sequence is");
                printf(" computed as x_1 = m * x_0 + c where n is a non-zero positive");
                printf(" number of values in the sequence and x0 is an integer and is the");
                printf(" first value in the sequence and m is an integer and is used as");
                printf(" a multiplier of the previous term in the sequence and c is an");
                printf(" integer and is added to the (m*previous) term \n");
                exit(1);
        }
        else{
                int x_0 = atoi(argv[2]); // The first or previous number in the sequence.
                //int result;
                printf("%i,",x_0);
                for(int i = 1;i<atoi(argv[1]);i++){
                        int result = equation(atoi(argv[3]), x_0, atoi(argv[4])); // The next number in the sequence.
                        //if it is the last number in the sequence
                        if(i == atoi(argv[1])-1){
                                printf("%i\n",result);
                        }
                        else{
                                printf("%i,",result);
                        }
                        x_0 = result;
                }
        }
        return 0;
}
