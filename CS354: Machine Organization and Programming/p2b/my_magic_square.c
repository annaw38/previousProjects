////////////////////////////////////////////////////////////////////////////////
// Main File:        my_magic_square.c
// This File:        my_magic_square.c
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
// 2/17	  check for null in 2D array in c https://stackoverflow.com/questions/
//										  39823004/c-checking-for-null-in-a-2d-
//										  array 
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
//	2/14	Coded getSize()'s prompt the user and check that it's a valid size
//	2/17	Finished generateMagicSquare(), still need to work on file output
//  2/19	Fixed the bug in fileOutputMagicSquare, but still has 20 bytes of mem 
//			leak
//  2/21    Changed generateMagicSquare() to add to struct's magic square member 
//			instead of a new 2D array - still have memory leak
//  2/21	Fixed memory leak, everything seems to be working as expected
//	2/23	Changed to match the style and comment guidelines 
///////////////////////// OTHER SOURCES OF HELP ////////////////////////////// 
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
// Date:   Name (email):   Helped with: (brief description)
// ---------------------------------------------------------------------------
// 2/19    Zongokafando@wisc.edu Helped with debugging writing to the file - not
//								 freeing the output until after writing to the file
// 2/21	   mxu339@wisc.edu		 Helped with dubugging the memory leak of 20 bytes.
//////////////////////////// 80 columns wide ///////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
// Copyright 2020 Jim Skrentny
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission, CS 354 Spring 2024, Deb Deppeler
////////////////////////////////////////////////////////////////////////////////

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Structure that represents a magic square
typedef struct {
	int size;           // dimension of the square
	int **magic_square; // pointer to heap allocated magic square
} MagicSquare;

/* 
 * Prompts the user for the magic square's size, read it,
 * check if it's an odd number >= 3 (if not, display the required
 * error message and exit)
 *
 * return the valid number
 */
int getSize() {
    char *str = malloc(50); // Pointer to the user input for the size
	// Check that malloc allocated memory.
	if(str == NULL){
		printf("Malloc was not able to allocate memory to the pointer to size\n");
		exit(1);
	}
	// Prompt the user for magic square's size.
    printf("Enter magic square's size (odd integer >=3)\n");
    if(fgets (str, 50, stdin) == NULL) {
        fprintf(stderr, "Error reading user input.\n");
        free(str);
		exit(1);
    }
    int size = atoi(str); // Stores the size the user entered.
	// Check that the size is an odd number.
	if(size % 2 != 1){
		printf("Magic square size must be odd.\n");
		free(str);
		exit(1);
	}
	// Check that the size is >= 3.
	if(size < 3){
		printf("Magic square size must be >= 3.\n");
		free(str);
		exit(1);
	}
	else{	
		free(str);
		return size;
	}	
} 

/* 
 * Makes a magic square of size n,
 * and stores it in a MagicSquare (on the heap)
 *
 * It may use the Siamese magic square algorithm 
 * or alternate from assignment 
 * or another valid algorithm that produces a magic square.
 *
 * n - the number of rows and columns
 *
 * returns a pointer to the completed MagicSquare struct.
 */
MagicSquare *generateMagicSquare(int n) {
	MagicSquare *ptrSquare = malloc(sizeof(MagicSquare));//Pointer to MagicSquare.
	// Check that malloc allocated memory to the pointer to the MagicSquare.
	if(ptrSquare == NULL){
		printf("Malloc wasn't able to allocate size to pointer to the MagicSquare\n");
		exit(1);
	}
	ptrSquare -> size = n; //Set size in the magic_square to n. 
	ptrSquare -> magic_square = malloc(sizeof(int*) * n); 
	// Check that the array of pointers isn't null.
	if(ptrSquare -> magic_square == NULL){
		free(ptrSquare);
		printf("Malloc wasn't able to allocate memory for the array of pointers.\n");
		exit(1);
	}
	for(int i = 0; i < n; i++){
		*(ptrSquare -> magic_square + i) = malloc(sizeof(int) * n);
		// Check that the arrays that the pointers are pointing to is not null. 
		if(*(ptrSquare -> magic_square + i) == NULL){
			free(ptrSquare -> magic_square);
			free(ptrSquare);
			printf("Malloc wasn't able to allocate memory for the array of ints.\n");
			exit(1);
		}
	}	
	for(int i = 0; i < n; i++){
		for(int j = 0; j < n; j++){
			*(*(ptrSquare -> magic_square + i) + j) = 0;
		}
	}	
	//Using the alternate Siamese method.
	//Start with 1 in the last column in the middle row.
	int count = 1; 
	int row = n/2;
	int col = n-1;
	while(count < (n * n + 1)){
		// Check that the space the value will be put in is free.
		if(*(*(ptrSquare -> magic_square + row) + col) == 0){
			*(*(ptrSquare -> magic_square+row) + col) = count;
		}
		// Otherwise put it to the column left of the previous value
		else{
			row--;
			col = col-2;
			*(*(ptrSquare -> magic_square + row) + col) = count;
		}
		count++;
		row++;
		col++;
		// If the next value is a corner piece outside of the square, put set the
		// value to be placed in the column left of the previous value.
		if(col > (n - 1) && row > (n - 1)){
			row--;
			col = col - 2;
		}
		else{
			// If the next number's column is out of the square, put it in the 
			// same row but the first column.
			if(col > (n - 1)){
				col = 0;
			}
			// If the next number's row is out of the square, put it in the same
			// column but the first row
			if(row > (n - 1)){
				row = 0; 
			}
		}
	}
	return ptrSquare;    
} 

/* 
 * Opens a new file (or overwrites the existing file)
 * and writes the magic square values to the file
 * in the specified format.
 *
 * magic_square - the magic square to write to a file
 * filename - the name of the output file
 */
void fileOutputMagicSquare(MagicSquare *magic_square, char *filename) {
	FILE *outFile = fopen(filename, "w"); // Pointer to the output file.
	// Check that the file could be opened.
	if(outFile == NULL){
		fprintf(stderr, "Can't open output file %s!\n", filename);
		exit(1);
	}
	int count = magic_square -> size; // Holds the size of the magic square.
	fprintf(outFile, "%i\n", count);
	for(int i = 0; i < count; i++){
		for(int j = 0; j < count; j++){
			//Check if this value is the last one in the square and print new line.
			if(j == count - 1){
				fprintf(outFile, "%i\n", *(*(magic_square -> magic_square + i) + j));
			}
			// Otherwise write the value then a comma to the file.
			else{	
				fprintf(outFile, "%i,", *(*(magic_square -> magic_square + i) + j));
			}	
		}
	}
	for(int i = 0; i < magic_square->size; i++){
		free(*(magic_square -> magic_square + i));
	}
	free(magic_square -> magic_square);
	free(magic_square);
	// Try closing the file and check if the file couldn't be closed.
	if(fclose(outFile) == EOF){
		printf("This file could not be closed.\n");
		exit(1);
	}
}

/* 
 * Generates a magic square of the user specified size and
 * outputs the square to the output filename.
 *  
 * Usage: A two CLAs the first being the program name and the second being
 * the file name to write to. 
 *
 * argc: the number of command line args (CLAs)
 * argv: the CLA strings, includes the program name and the file name
 *
 * Returns 0 if able to write the magic square and size to the given file name.
 * Exit with a non-zero result if unable to open and write to the file given. 
 * 
 */
int main(int argc, char **argv) {
	// Check input arguments to get output filename
	if(argc != 2){
		printf("Usage: ./my_magic_square <output_filename>\n");
		exit(1);
	}
	char *outFile = *(argv + 1);
	// Get magic square's size from user
	int size = getSize();
	// Generate the magic square by correctly interpreting 
	//       the algorithm(s) in the write-up or by writing or your own.  
	//       You must confirm that your program produces a 
	//       Magic Sqare as described in the linked Wikipedia page.
	MagicSquare *ptrSquare = generateMagicSquare(size);
	// Output the magic square
	fileOutputMagicSquare(ptrSquare, outFile);
	return 0;
} 

// Spring 2024


