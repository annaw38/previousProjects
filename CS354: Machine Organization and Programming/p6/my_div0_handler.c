////////////////////////////////////////////////////////////////////////////////
// Main File:        send_signal.c and my_div0_handler.c
// This File:        my_div0_handler.c
// Other Files:      send_signal.c and my_c_signal_handler.c
// Semester:         CS 354 Lecture 002     SPRING 2024
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
// 4/21		Created the my_div0_handler.c file.
// 
// 4/26     Added handler for the SIGFPE.
//
// 4/27		Added another handler for SIGINT.
// 
// 4/28		Added function headers and more comments.
//
// 5/2		Added comments and changed the if statements to == NULL.
//
// 5/3		Made final changes and added comments.
///////////////////////// OTHER SOURCES OF HELP ////////////////////////////// 
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
// Date:   Name (email):   Helped with: (brief description)
// ---------------------------------------------------------------------------
// 
//////////////////////////// 80 columns wide ///////////////////////////////////

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>

int count = 0; //Number of functions successfully calculated

/* Handles SIGFPE signals
 *
 * sig The signal number that called the handler.
 * info Pointer to struct that has more indormation about the signal.
 * ucontext Pointer to struct that has signal context information saved on the 
 *			stack by the kernel.
 */
void sigfpeHandler(int sig, siginfo_t *info, void *ucontext)
{
	printf("Error: a division by 0 operation was attempted.\n");
	printf("Total number of operations completed successfully: %i\n", count);
	printf("The program will be terminated.\n");
	exit(1);
}

/* Handles SIGINT signals 
 *
 * sig The signal number that called the handler.
 * info Pointer to struct that has more indormation about the signal.
 * ucontext Pointer to struct that has signal context information saved on the 
 *			stack by the kernel.
 */
void sigintHandler(int sig, siginfo_t *info, void *ucontext)
{
	printf("\nTotal number of operations completed successfully: %i\n", count);
	printf("The program will be terminated.\n");
	exit(0);
}

/* This program asks the user to enter two numbers until the user enters 0 as
 * the second number or is killed by SIGINT. 
 *
 * Usage: No additional CLAs beyond executable name is required. 
 *
 * argc: the number of command line args (CLAs)
 * argv: the CLA strings, includes the program name
 *
 * Exits only when it is killed by SIGINT, otherwise in an infinite loop. 
 */
int main(int argc, char **argv ) {
	char intBuf[100]; // Buffer with 100 bytes

	//struct for sigfpe
	struct sigaction a;  
	//clear the struct to 0
	memset (&a, 0, sizeof(a));
	//register the sigfpeHandler
	a.sa_sigaction = &sigfpeHandler;
	if(sigaction(SIGFPE, &a, NULL) != 0){
		printf("Error: The sigfpeHandler was not able to be registered\n");
		return 1;
	}

	//struct for sigint
	struct sigaction b;
	//clear the struct to 0
	memset (&b, 0, sizeof(b));
	//register the sigintHandler
	b.sa_sigaction = &sigintHandler;
	if(sigaction(SIGINT, &b, NULL) != 0){
		printf("Error: The sigintHandler was not able to be registered\n");
		return 1; 
	}

	//infinite loop asking the user for two integers
	while(1){
		fputs("Enter first integer: ", stdout);

		//check that there is user input
		if(fgets(intBuf, sizeof(intBuf), stdin) == NULL){
			printf("Error: Stream is now closed.");
			exit(1);
		}

		int firstInt = atoi(intBuf); //The first integer entered

		fputs("Enter second integer: ",stdout);
		//check that there is user input
		if(fgets(intBuf, sizeof(intBuf), stdin) == NULL){
			printf("Error: Stream is now closed.");
			exit(1);
		}

		int secondInt = atoi(intBuf); // Second integer entered
		int answer = firstInt/secondInt; //Resulting quotient of the two ints
	    //Remainder of the division of the integers
		int remainder = firstInt % secondInt; 
		printf("%i/%i is %i with a remainder of %i\n", firstInt, secondInt,
				answer, remainder);
		++count;
	}

	return 0;
}
