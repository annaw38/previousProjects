////////////////////////////////////////////////////////////////////////////////
// Main File:        my_c_signal_handler.c and my_div0_handler.c
// This File:        my_c_signal_handler.c
// Other Files:      send_signal.c and my_div0_handler.c
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
// 4/21		Added main, seconds global variable, print statements and, infinite 
//			loop.
// 
// 4/24     Created three handlers, structs, and registered all of them. Also 
//			created alarm and signals to test the alarm and sigusr1 and sigint
//			handlers. 
// 
// 4/25		Added error checking to the alarmHandler and when registering the 
//			handlers.
//
// 4/27		Added function headers and other comments. 
//
// 5/2		Added comments and fixed if statements
//
// 5/3		Made final changes and added comments.
///////////////////////// OTHER SOURCES OF HELP ////////////////////////////// 
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
// Date:   Name (email):   Helped with: (brief description)
// ---------------------------------------------------------------------------
// 4/24	  jfcooper2@wisc.edu  Getting started and understanding how to register
//							  the handler.
//////////////////////////// 80 columns wide ///////////////////////////////////
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>

int seconds = 5; //Number of seconds before triggering the alarm
int count = 0;   //Number of SIGUSR1 signals received

/* Handles the alarms
 *
 * sig The signal number that called the handler.
 * info Pointer to struct that has more indormation about the signal.
 * ucontext Pointer to struct that has signal context information saved on the 
 *			stack by the kernel.
 */
void alarmHandler(int sig, siginfo_t *info, void *ucontext)
{
	time_t curTime;
	curTime = time(NULL);
	//Check that there was no error in setting curtime
	if(curTime == -1){
		printf("An error getting time occured\n");
		exit(1);
	}

	char *time = ctime(&curTime);
	//Check that time is not null
	if(time == NULL){
		printf("Error: ctime returned NULL\n");
		exit(1);
	}

	//Print PID and current time and set new alarm
	printf("PID: %i CURRENT TIME: %s", getpid(), time);
	alarm(seconds);
}

/* Handles the SIGUSR1 signals
 *
 * sig The signal number that called the handler.
 * info Pointer to struct that has more indormation about the signal.
 * ucontext Pointer to struct that has signal context information saved on the 
 *			stack by the kernel.
 */
void sigusr1Handler(int sig, siginfo_t *info, void *ucontext)
{
	count++; 
	printf("SIGUSR1 handled and counted!\n");
}

/* Handles the SIGINT signals 
 *
 * sig The signal number that called the handler.
 * info Pointer to struct that has more indormation about the signal.
 * ucontext Pointer to struct that has signal context information saved on the 
 *			stack by the kernel.
 */
void sigintHandler(int sig, siginfo_t *info, void *ucontext)
{
	printf("\nSIGINT handled.\n");
	printf("SIGUSR1 was handled %i times. Exiting now.\n", count);
	exit(0);
}

/* This program prints the PID and time every 5 seconds until killed by SIGINT. 
 *
 * Usage: No additional CLAs beyond executable name is required. 
 *
 * argc: the number of command line args (CLAs)
 * argv: the CLA strings, includes the program name
 *
 * Exits only when it is killed by SIGINT, otherwise in an infinite loop. 
 */
int main(int argc, char **argv ) {  
	//Print instructions to user
	printf("PID and time print every 5 seconds.\n");
	printf("Type Ctrl-C to end the program.\n");

	//struct for alarm
	struct sigaction act;  
	//clear the struct to 0
	memset (&act, 0, sizeof(act));
	act.sa_sigaction = &alarmHandler;
	//register the alarmHandler 
	if(sigaction(SIGALRM, &act, NULL) != 0){
		printf("Error: The alarmHandler was not able to be registered\n");
		return 1;
	}
	alarm(seconds);
	
	//struct for SIGUSR1 
	struct sigaction act2;
	//clear the struct to 0
	memset (&act2, 0, sizeof(act2));
	//register sigusr1Handler
	act2.sa_sigaction = &sigusr1Handler;
	if(sigaction(SIGUSR1, &act2, NULL) != 0){
		printf("Error: The sigusr1Handler was not able to be registered\n");
		return 1;
	}

	//struct for SIGINT
	struct sigaction act3;
	//clear the struct to 0
	memset (&act3, 0, sizeof(act3));
	//register the sigintHandler
	act3.sa_sigaction = &sigintHandler;
	if(sigaction(SIGINT, &act3, NULL) != 0){
		printf("Error: The sigintHandler was not able to be registered\n");
		return 1; 
	}
	
	//infinite loop
	while (1){ 
		
	}
}
