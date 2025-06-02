////////////////////////////////////////////////////////////////////////////////
// Main File:        my_c_signal_handler.c and my_div0_handler.c
// This File:        send_signal.c
// Other Files:      my_c_signal_handler.c and my_div0_handler.c
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
//  4/21	Created the send_signal.c file. 
//
//  4/24	Checked the number of CLAs, got the send user signal and terminate 
//			to send to signal handler. 
//
//  4/25    Added error checking and checking if the second cla is not -i or -u.
// 
//  5/3		Edited the if checking statements for kill and made final changes.
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
#include <string.h>
#include <sys/types.h>
#include <signal.h>

/* This program sends signals to my_c_signal_handler.c.
 *
 * Usage: No additional CLAs beyond executable name is required. 
 *
 * argc: the number of command line args (CLAs)
 * argv: the CLA strings, includes the program name
 *
 * Exits only when it is killed by SIGINT, otherwise in an infinite loop. 
 */
int main(int argc, char **argv ){
	//check number of CLAs
	if(argc != 3){
		printf("Usage: send_signal <signal type> <pid>\n");
		exit(1);
	}

	//printf("argv1: %d\n", argv[1][1]); //debug line
	//send signal as interrupt to stop loop
	if(argv[1][1] == 'i'){
		if(kill(atoi(argv[2]), SIGINT) != 0){
			printf("Error: Unable to send SIGINT signal\n");
			exit(1);
		}
	}

	//send signal as user signal
	else if(argv[1][1] == 'u'){
		if(kill(atoi(argv[2]), SIGUSR1) != 0){
			printf("Error: Unable to send SIGUSR1 signal\n");
			exit(1);
		}
	}

	//The signal type is not -u or -i 
	else{
		printf("Error: The signal type must be -u or -i\n");
		exit(1);
	}
}
