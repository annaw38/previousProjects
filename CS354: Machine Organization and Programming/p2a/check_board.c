////////////////////////////////////////////////////////////////////////////////
// Main File:        check_board.c
// This File:        check_board.c
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
// 2/5      Copied check_board.c file from public/code and added if condition to
//			main to check number of CLA 
// 2/6		Dynamically allocated space for the 2D array, fixed compiler error,
//			and started algorithm for valid_board
// 2/8		Added to valid_board to check rows and columns for duplicates - still
//		    need to debug possibly - and fixed compiler errors
// 2/9      Fixed the valid_board function and got rid of all memory leaks from
//			that function 
// 2/11		Updated variable names to be more concise and better describe their 
//			purpose 
// 2/13		Updated malloc and argc error messages
// 2/15		Updated the size of the array holding the count of each digit in a 
//			row or col to use size instead of 10
// 2/17     Edited to match style and comment expectations
///////////////////////// OTHER SOURCES OF HELP ////////////////////////////// 
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
// Date:   Name (email):   Helped with: (brief description)
// ---------------------------------------------------------------------------
// 
//////////////////////////// 80 columns wide ///////////////////////////////////



///////////////////////////////////////////////////////////////////////////////
// Copyright 2021-24 Deb Deppeler
// Posting or sharing this file is prohibited, including any changes/additions.
//
// We have provided comments and structure for this program to help you get 
// started.  Later programs will not provide the same level of commenting,
// rather you will be expected to add same level of comments to your work.
// 09/20/2021 Revised to free memory allocated in get_board_size function.
// 01/24/2022 Revised to use pointers for CLAs
////////////////////////////////////////////////////////////////////////////////

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char *DELIM = ",";  // commas ',' are a common delimiter character for data strings

/* Checks if the given board is a valid board or not
 *
 * Pre-conditions: a non null **board and non null size
 * param1 **board heap allocated 2D array of integers
 * piaram2 size the number of rows and columns in the board
 * retval return 1 if and only if the board is in a valid Sodoku board state, 
 * otherwise returns 0.
 */
/* Returns 1 if and only if the board is in a valid Sudoku board state.
 * Otherwise returns 0.
 * 
 * A valid row or column contains only blanks or the digits 1-size, 
 * with no duplicate digits, where size is the value 1 to 9.
 * 
 * Note: p2A requires only that each row and each column are valid.
 * 
 * board: heap allocated 2D array of integers 
 * size:  number of rows and columns in the board
 */
int valid_board(int **board, int size) {

	//Check if size is a number not between 1 and 9.
	if(size <= 0 || size >= 10){
		printf("The board size must be between 1 and 9.\n");
		return 0;
	}

	int *count_row = malloc(sizeof(int) * size+1); // Count the digits in each row. 
	if(count_row == NULL){	
		printf("Malloc was not able to allocate memory for count_row.\n");
		return 0;
	}
	int *count_col = malloc(sizeof(int) * size+1); // Count the digits in each col.
	if(count_col == NULL){
		printf("Malloc was not able to allocate memory for count_col.\n");
		return 0;
	}

	int valid = 0; // Number of valid rows and cols (should be 2*size).

	// Check each row for duplicates.
	for(int i = 0; i < size; i++){
		for(int j = 0; j < size; j++){
		 	// Check if the value is greater than size or less than 0.
			if(*(*(board + i) + j) > size || *(*(board + i) + j) < 0){
				free(count_row);
				free(count_col);
				return 0;
			}	
				
			//int value = *(*(board+i)+j); //value used for debugging 
			// Add 1 to count_row for the corresponding value.
			for(int k = 0; k < size + 1; k++){
				if(*(*(board + i) + j) == k){
					*(count_row + k) += 1;//possible bug
					break;
				}
			}

			// Check if there's duplicates in this row.
			for(int m = 1; m < size + 1; m++){
				if(*(count_row + m) != 1 && *(count_row + m) != 0){
					free(count_row);
					free(count_col);
			 		return 0;
				}
			}
		}
		valid++; 
		// Reset all the values in row_dup after each row.
		for(int n = 1; n < size + 1; n++){
			*(count_row + n) = 0;
		}
	}
	
	// Check for duplicates in the columns.
	for(int j = 0; j < size; j++){
		for(int i = 0; i < size; i++){
			// Check if the value is greater than size or less than 0.
			if(*(*(board + i) + j) > size || *(*(board + i) + j) < 0){
				free(count_row);
				free(count_col);
				return 0;
			}	
				
			//int value = *(*(board+i)+j); value used for debugging
			// Add 1 to count_col for the corresponding value.
			for(int k = 0; k < size + 1; k++){
				if(*(*(board + i) + j) == k){
					*(count_col + k) += 1;//possible bug
					break;
				}
			}
			// Check if there's duplicates in this column.
			for(int m = 1; m < size + 1; m++){
				if(*(count_col + m) != 1 && *(count_col + m) != 0){
					free(count_row);
					free(count_col);
					return 0;
				}
			}

		}
		valid++; 
		// Reset all the values in count_col to 0 after each column.
		for(int n = 1; n < size + 1; n++){
			*(count_col + n) = 0;
		}
	}	
	// Check that valid matches the expected number of rows+columns.
	if(valid == 2 * size){
		free(count_row);
		free(count_col);
		return 1;
	}
	
	return 0;
}    

/* COMPLETED (DO NOT EDIT):       
 * Read the first line of file to get the size of that board.
 * 
 * PRE-CONDITION #1: file exists
 * PRE-CONDITION #2: first line of file contains valid non-zero integer value
 *
 * fptr: file pointer for the board's input file
 * size: a pointer to an int to store the size
 *
 * POST-CONDITION: the integer whos address is passed in as size (int *) 
 * will now have the size (number of rows and cols) of the board being checked.
 */
void get_board_size(FILE *fptr, int *size) {      
	char *line = NULL;
	size_t len = 0;

	// 'man getline' to learn about <stdio.h> getline
	if ( getline(&line, &len, fptr) == -1 ) {
		printf("Error reading the input file.\n");
		free(line);
		exit(1);
	}

	char *size_chars = NULL;
	size_chars = strtok(line, DELIM);
	*size = atoi(size_chars);

	// free memory allocated for line 
	free(line);
	line = NULL;
}


/* This program prints "valid" (without quotes) if the input file contains
 * a valid state of a Sudoku puzzle board wrt to rows and columns only.
 * It prints "invalid" (without quotes) if the input file is not valid.
 *
 * Usage: A single CLA that is the name of a file that contains board data.
 *
 * argc: the number of command line args (CLAs)
 * argv: the CLA strings, includes the program name
 *
 * Returns 0 if able to correctly output valid or invalid.
 * Exit with a non-zero result if unable to open and read the file given.
 */
int main(int argc, char **argv ) {              

	// Check that the number of command-line arguments is correct.
	if(argc != 2){
		printf("There should be 2 command line arguments.\n");
		exit(1);
	}

	// Open the file
	FILE *fp = fopen(*(argv + 1), "r");
	if (fp == NULL) {
		printf("Can't open file for reading.\n");
		exit(1);
	}

	// Store the board's size, number of rows and columns.
	int size;

	// Call get_board_size to read first line of file as the board size.
	int *ptr_size = &size; //pointer to size to get the board's size
	get_board_size(fp, ptr_size);

	// Dynamically allocate a 2D array for given board size.
	// You must dyamically create a 1D array of pointers to other 1D arrays of ints
	int **board = malloc(sizeof(int*) * size); //2D array of the board 
	// Check that array of pointers is not null.
	if(board == NULL){
		printf("Malloc was not able to allocate memory for the 1D array of pointers.\n");
		exit(1);
	}
	// Check that the arrays that the pointers are pointing to is not null. 
	for(int i = 0; i < size; i++){
		*(board + i) = malloc(sizeof(int) * size);
		if(*(board + i) == NULL){
			printf("Malloc was not able to allocate memory for the 1D array of ints.\n");
			exit(1);
		}
	}	

	// Read the remaining lines.
	// Tokenize each line and store the values in your 2D array.
	char *line = NULL;
	size_t len = 0;
	char *token = NULL;
	for (int i = 0; i < size; i++) {

		// read the line
		if (getline(&line, &len, fp) == -1) {
			printf("Error while reading line %i of the file.\n", i + 2);
			exit(1);
		}
	
		token = strtok(line, DELIM);
		for (int j = 0; j < size; j++) {
			// Initialize elements of your 2D array.
			*(*(board + i) + j) = atoi(token); 
			token = strtok(NULL, DELIM);
		}
	}

	// Call valid_board and print the appropriate output depending on the function's return value.
	if(valid_board(board, size) == 1){
		printf("valid\n");
	}
	else{
		printf("invalid\n");
	}
	// Free dynamically allocated memory.
	free(line);
	for(int i = 0; i < size; i++){
		free(*(board + i));
	}
	free(board);	
	//Close the file.
	if (fclose(fp) != 0) {
		printf("Error while closing the file.\n");
		exit(1);
	} 

	return 0;       
}       

