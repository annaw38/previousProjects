////////////////////////////////////////////////////////////////////////////////
// Main File:        cla_sequence.c
// This File:        cla_sequence.c
// Other Files:      
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
// 2/4    cast char to int in c        https://sentry.io/answers/char-to-int-in-c-and-cpp/#:~:text=The%20easiest%20way%20to%20convert,which%20is%20what%20we%20want.
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
//  2/4     Copied simple_sequence.c from A02 and created main if/else 
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

int main(int argc, char*argv[]){
	//char* val1 = argv[1];
	//char* val2 = argv[2];
	if(argv[2] == NULL || *argv[1] > *argv[2]){
		printf("Usage: cla_sequence start_integer end_integer where start is less than end\n");
	}
	
	else{
		int start = *argv[1] - '0';
		int end = *argv[2] - '0';
		//int start = *val1-'0';
		//int end = *val2 - '0';
		for(int i = start; i < end; i++){
			printf("%i,", i);
		}
		printf("%i\n", end);
	}	
	return 0;
}
