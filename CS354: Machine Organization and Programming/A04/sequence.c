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
//  2/13	Checked for the correct number of CLAs and printed the usage statement if it doesn't meet the expected number 
// 
// 
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
//TODO: Define a function that computes y=mx+b, with m, x, and b as its parameters.
int equation(int m, int x, int b){
	return 0;
}

int main( int argc, char *argv[]){
	if(argc !=5){
		printf("Usage: ./sequence n x0 m c   ==> where next value in sequence is computed as x_1 = m * x_0 + c");
		printf(" where n is a non-zero positive number of values in the sequence");
		printf(" and x0 is an integer and is the first value in the sequence");
		printf(" and m is an integer and is used as a multiplier");
		printf("of the previous term in the sequence and c is an integer and is added to the (m*previous) term\n");
	}

	return 0;
}
