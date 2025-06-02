////////////////////////////////////////////////////////////////////////////////
// Main File:        test101.c, test102.c, test103.c, test104.c, test105.c,  
//					 test110.c, test 121.c, test122.c, test123.c, test 201.c,
//					 test 202.c, test 211.c, test213.c, test 212_immedcoal.c,
//					 test 214_immedcoal.c, test 301.c, test 302.c
// This File:        p3Heap.c
// Other Files:      N/A
//					 
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
//  2/26	Copied files from canvas
//	2/28	Coded creating the block size with the payload, HDR, and padding
//  2/29	Added the conditional surrounding if a block big enough was found
//  3/1		Added case to split the block
//  3/2     Added checking pbit and abit in splitting block case, still need 
//			debugging
//  3/3		Fixed balloc to return the pointer to the start of the block and 
//			check the pbit and abit before changing the current block - passed 
//			partA tests.
//  3/4		Updated to meet style and comment guidelines.
//	3/4		Started implementing bfree, identified bug that clears size_status.
//  3/8		Changed the equal size case in balloc to return the correct payload
//			pointer, and debugged bfree - passed partB tests
//	3/8		Started implementing immediate coalescing - still buggy
//  3/9		Fixed bugs of assuming previous and next blocks are free and adding 
//			2 to the current block's size_status - passed partB tests.
//  3/10	Fixed bug of not checking if the previous block is free, bug in line 
//			45 of test 212 immedcoal
//	3/11	Fixed bug in bfree of checking the previous block's footer even when
//			p-bit is 1. Passed all tests.
//	3/12	Updated comments and style to meet guidelines.
//  3/13	Added comments to balloc and bfree. Fixed warnings in checking if ptr
//			is outside the heap.
//  3/14	Updated comments.
//  3/15	Everything seems to be working, checked that footers were correctly
//			set with size of block and the p-bits of blocks. 
///////////////////////// OTHER SOURCES OF HELP ////////////////////////////// 
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
// Date:   Name (email):   Helped with: (brief description)
// ---------------------------------------------------------------------------
// 3/11	   jgao298@wisc.edu	Helped with debugging infinite loop in bfree.
// 3/13	   jfcooper2@wisc.edu Helped with checking if ptr is outside of heap space
//////////////////////////// 80 columns wide ///////////////////////////////////

/////////////////////////////////////////////////////////////////////////////
//
// Copyright 2020-2024 Deb Deppeler based on work by Jim Skrentny
// Posting or sharing this file is prohibited, including any changes.
// Used by permission SPRING 2024, CS354-deppeler
//
/////////////////////////////////////////////////////////////////////////////

#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <string.h>
#include "p3Heap.h"

/*
 * This structure serves as the header for each allocated and free block.
 * It also serves as the footer for each free block.
 */
typedef struct blockHeader {           

	/*
	 * 1) The size of each heap block must be a multiple of 8
	 * 2) heap blocks have blockHeaders that contain size and status bits
	 * 3) free heap block contain a footer, but we can use the blockHeader 
	 *.
	 * All heap blocks have a blockHeader with size and status
	 * Free heap blocks have a blockHeader as its footer with size only
	 *
	 * Status is stored using the two least significant bits.
	 *   Bit0 => least significant bit, last bit
	 *   Bit0 == 0 => free block
	 *   Bit0 == 1 => allocated block
	 *
	 *   Bit1 => second last bit 
	 *   Bit1 == 0 => previous block is free
	 *   Bit1 == 1 => previous block is allocated
	 * 
	 * Start Heap: 
	 *  The blockHeader for the first block of the heap is after skip 4 bytes.
	 *  This ensures alignment requirements can be met.
	 * 
	 * End Mark: 
	 *  The end of the available memory is indicated using a size_status of 1.
	 * 
	 * Examples:
	 * 
	 * 1. Allocated block of size 24 bytes:
	 *    Allocated Block Header:
	 *      If the previous block is free      p-bit=0 size_status would be 25
	 *      If the previous block is allocated p-bit=1 size_status would be 27
	 * 
	 * 2. Free block of size 24 bytes:
	 *    Free Block Header:
	 *      If the previous block is free      p-bit=0 size_status would be 24
	 *      If the previous block is allocated p-bit=1 size_status would be 26
	 *    Free Block Footer:
	 *      size_status should be 24
	 */
	int size_status;

} blockHeader;         

/* Global variable - DO NOT CHANGE NAME or TYPE. 
 * It must point to the first block in the heap and is set by init_heap()
 * i.e., the block at the lowest address.
 */
blockHeader *heap_start = NULL;     

/* Size of heap allocation padded to round to nearest page size.
 */
int alloc_size;

/*
 * Additional global variables may be added as needed below
 */




/* 
 * Function for allocating 'size' bytes of heap memory.
 * Argument size: requested size for the payload
 * Returns address of allocated block (payload) on success.
 * Returns NULL on failure.
 *
 * This function must:
 * - Check size - Return NULL if size < 1 
 * - Determine block size rounding up to a multiple of 8 
 *   and possibly adding padding as a result.
 *
 * - Use BEST-FIT PLACEMENT POLICY to chose a free block
 *
 * - If the BEST-FIT block that is found is exact size match
 *   - 1. Update all heap blocks as needed for any affected blocks
 *   - 2. Return the address of the allocated block payload
 *
 * - If the BEST-FIT block that is found is large enough to split 
 *   - 1. SPLIT the free block into two valid heap blocks:
 *         1. an allocated block
 *         2. a free block
 *         NOTE: both blocks must meet heap block requirements 
 *       - Update all heap block header(s) and footer(s) 
 *              as needed for any affected blocks.
 *   - 2. Return the address of the allocated block payload
 *
 *   Return if NULL unable to find and allocate block for required size
 *
 * Note: payload address that is returned is NOT the address of the
 *       block header.  It is the address of the start of the 
 *       available memory for the requesterr.
 *
 * Tips: Be careful with pointer arithmetic and scale factors.
 */
void* balloc(int size) { 
	if(size < 1){
		return NULL;
	}
	// Size of the payload and blockHeader.
	int blockSize = size + sizeof(blockHeader);
	// Check if padding is needed.
	if(blockSize % 8 != 0){
		blockSize = blockSize - blockSize % 8 + 8;
	}
	// Current block in the heap.
	blockHeader *currBlock = heap_start;
	int currStatus = currBlock -> size_status;
	// Best fit block in heap.
	blockHeader *bestFit = heap_start;
	while(currStatus != 1){
		// Check if the block is free - a-bit must be 0.
		if(currStatus % 8 == 2 || currStatus % 8 == 0){
			// Check that the size of the free block is big enough.
			if(currStatus >= blockSize){
				int currSize = currStatus;
				currSize = currStatus - 2;
				// Check if the current block is the exact size of blockSize. 
				if(currSize == blockSize){
					// Change the current block's a-bit to 1.
					currBlock -> size_status = currStatus + 1;
					// Move to the next block and change its p-bit to 1.
					currBlock = (blockHeader *)((void *)currBlock + blockSize);
					if(currBlock -> size_status != 1){
						currBlock -> size_status = currBlock -> size_status + 2;
					}
					return ((void *)currBlock - blockSize + sizeof(blockHeader)); 
				}
				else{
					// heap_start is not free or it is too small, update bestFit	
					if(bestFit -> size_status % 8 != 2 || 
							bestFit -> size_status < blockSize){
						bestFit = currBlock;	
					}
					// or if it's a smaller block that is larger than blockSize.
					else{
						if(currStatus < bestFit -> size_status){	
							bestFit = currBlock;
						}
					}
				}
			}
		}
		// The current block was already allocated - get the next block.
		// p-bit and a-bit are 1
		if(currBlock -> size_status % 8 == 3){
			currBlock = (blockHeader *)((void *)currBlock + currBlock -> size_status - 3);
			currStatus = currBlock -> size_status;
		}
		// p-bit is 1 and a-bit is 0
		else if(currBlock -> size_status % 8 == 2){
			currBlock = (blockHeader *)((void *)currBlock + currBlock -> size_status - 2);
			currStatus = currBlock -> size_status;
		}
		// p-bit is 0 and a-bit is 1
		else if(currBlock -> size_status % 8 == 1){
			currBlock = (blockHeader *)((void *)currBlock + currBlock -> size_status - 1);
			currStatus = currBlock -> size_status; 
		}
		/*Should not be necessary because immediate coalescing is implemented*/
		/*else if(currBlock -> size_status % 8 == 0){
		  currBlock = (blockHeader *)((void *)currBlock + currBlock -> size_status);
		  currStatus = currBlock -> size_status; 
		  }*/
	}
	/* Check that best fit is free if heap_start, otherwise assume large enough
	   and free*/
	if(bestFit != heap_start || (bestFit == heap_start && heap_start -> 
				size_status % 8 == 2)){
		currBlock = bestFit;
		currStatus = currBlock -> size_status;
		blockHeader *freeBlock = (blockHeader *)((void *)currBlock + blockSize);
		// currBlock is the allocated block
		// Check if the p-bit of the currBlock is 1.
		if(currBlock -> size_status % 8 == 2){
			currBlock -> size_status = blockSize + 3;
		}
		else{
			currBlock -> size_status = blockSize + 1;
		}
		freeBlock -> size_status = currStatus - blockSize;
		// Change the p-bit of the freeBlock to 1. 
		if(freeBlock -> size_status % 8 == 0){
			freeBlock -> size_status += 2;
		}
		blockHeader *ftr = (blockHeader *)(((void *)freeBlock + 
					freeBlock -> size_status - 2) - sizeof(blockHeader)); 
		// Update the footer to have the size of the freeBlock. 
		ftr -> size_status = freeBlock -> size_status - 2;
		return ((void *)currBlock + sizeof(blockHeader));
	}
	return NULL;
}




/* 
 * Function for freeing up a previously allocated block.
 * Argument ptr: address of the block to be freed up.
 * Returns 0 on success.
 * Returns -1 on failure.
 * This function should:
 * - Return -1 if ptr is NULL.
 * - Return -1 if ptr is not a multiple of 8.
 * - Return -1 if ptr is outside of the heap space.
 * - Return -1 if ptr block is already freed.
 * - Update header(s) and footer as needed.
 *
 * If free results in two or more adjacent free blocks,
 * they will be immediately coalesced into one larger free block.
 * so free blocks require a footer (blockHeader works) to store the size
 *
 * TIP: work on getting immediate coalescing to work after your code 
 *      can pass the tests in partA and partB of tests/ directory.
 *      Submit code that passes partA and partB to Canvas before continuing.
 */                    
int bfree(void *ptr) {  
	if(ptr == NULL){
		return -1;
	}
	if((int)ptr % 8 != 0){
		return -1;
	}
	if((blockHeader *)ptr < heap_start || ptr > ((void *)heap_start + alloc_size)){
		return -1;
	}
	// Current block the ptr is pointing to.
	blockHeader *curBlock = (blockHeader *)(ptr - sizeof(blockHeader));
	// current size of the block
	int curSize = curBlock -> size_status;
	// p-bit is 1, a-bit is 1
	if(curSize % 8 == 3){	
		curSize -= 3;
	}
	// p-bit is 0, a-bit is 1
	else if(curSize % 8 == 1){
		curSize -= 1;
	}
	else{
		return -1;
	}
	// Change the a-bit to 0.
	curBlock -> size_status -= 1;
	// The p-bit is 1.
	if(curBlock -> size_status % 8 == 2){	
		curSize = curBlock -> size_status - 2;
	}
	else{
		curSize = curBlock -> size_status;
	}
	// curBlock is now next block in the heap.
	curBlock = (blockHeader *)((void *)curBlock + curSize);
	if(curBlock -> size_status == 1){
		// Check if the previous block is free.
		blockHeader *prevFtr = (blockHeader *)((void *)curBlock - curSize - 
				sizeof(blockHeader));
		if(prevFtr -> size_status % 8 == 0){
			curBlock = (blockHeader *)((void *)curBlock - prevFtr -> size_status);
			// Set p-bit to 1 since currblock coalesced with the previous block.
			curBlock -> size_status = curBlock -> size_status + curSize + 2; 
			blockHeader *ftr = (blockHeader *)((void *)curBlock + curBlock ->
					size_status - 2 - sizeof(blockHeader));
			// Update the footer to have the size of the currentBlock + prevBlock. 
			ftr -> size_status = curBlock -> size_status - 2;
		}
		else{
			// Set the current block's pbit to 1.
			curBlock -> size_status += 2;
			blockHeader *ftr = (blockHeader *)((void *)curBlock + curBlock ->
					size_status - 2 - sizeof(blockHeader));
			// Update the footer to have the size of the currBlock + prev block. 
			ftr -> size_status = curBlock -> size_status - 2;
		}
		return 0;
	}
	else{	
		// curBlock is the next block.
		curBlock -> size_status = curBlock -> size_status - 2; 
		if(curBlock -> size_status % 8 == 0){
			// Size of the next block.
			int nextSize = curBlock -> size_status;
			// CurBlock is the block ptr was pointing to.
			curBlock = (blockHeader *)((void *)curBlock - curSize);
			curBlock -> size_status = curBlock -> size_status + nextSize;
			// total size of the curBlock and next block.
			int totalSize = curBlock -> size_status;
			blockHeader *prevFtr = (blockHeader *)((void *)curBlock - 
					sizeof(blockHeader));
			if(prevFtr -> size_status % 8 == 0 && prevFtr -> size_status != 0){
				// curBlock is the previous free block.
				curBlock = (blockHeader *)((void *)curBlock - prevFtr -> 
						size_status);
				curBlock -> size_status += totalSize;
				totalSize = curBlock -> size_status - 2;
				// Set footer to have the size of all 3 blocks. 
				blockHeader *ftr = (blockHeader *)((void *)curBlock + curBlock
						-> size_status - 2 - sizeof(blockHeader));
				ftr -> size_status = totalSize;
			}
			else{
				// Set footer with curBlock and next block.
				blockHeader *ftr = (blockHeader *)((void *)curBlock + curBlock 
						-> size_status - 2 - sizeof(blockHeader));
				ftr -> size_status = curBlock -> size_status - 2;
			}
		}
		else{ 
			// Move curBlock to the block that ptr was pointing to.
			curBlock = (blockHeader *)((void *)curBlock - curSize);
			if(curBlock -> size_status % 8 != 0){
				// Set the footer of just curBlock.
				blockHeader *ftr = (blockHeader *)((void *)curBlock + curSize
						- sizeof(blockHeader));
				ftr -> size_status = curSize;
			}
			else{
				// Get the previous block's footer since curBlock's p-bit is 0.
				blockHeader *prevFtr = (blockHeader *)((void *)curBlock
						- sizeof(blockHeader));
				curBlock = (blockHeader *)((void *)curBlock - prevFtr -> 
						size_status);
				curBlock -> size_status += curSize;
				// Set the footer of curBlock + previous block.
				blockHeader *ftr = (blockHeader *)((void *)curBlock + curBlock 
						-> size_status - 2 - sizeof(blockHeader));
				ftr -> size_status = curBlock -> size_status - 2;
			}
		}	
		return 0;
	}
	return -1;
} 


/* 
 * Initializes the memory allocator.
 * Called ONLY once by a program.
 * Argument sizeOfRegion: the size of the heap space to be allocated.
 * Returns 0 on success.
 * Returns -1 on failure.
 */                    
int init_heap(int sizeOfRegion) {    

	static int allocated_once = 0; //prevent multiple myInit calls

	int   pagesize; // page size
	int   padsize;  // size of padding when heap size not a multiple of page size
	void* mmap_ptr; // pointer to memory mapped area
	int   fd;

	blockHeader* end_mark;

	if (0 != allocated_once) {
		fprintf(stderr, 
				"Error:mem.c: InitHeap has allocated space during a previous call\n");
		return -1;
	}

	if (sizeOfRegion <= 0) {
		fprintf(stderr, "Error:mem.c: Requested block size is not positive\n");
		return -1;
	}

	// Get the pagesize from O.S. 
	pagesize = getpagesize();

	// Calculate padsize as the padding required to round up sizeOfRegion 
	// to a multiple of pagesize
	padsize = sizeOfRegion % pagesize;
	padsize = (pagesize - padsize) % pagesize;

	alloc_size = sizeOfRegion + padsize;

	// Using mmap to allocate memory
	fd = open("/dev/zero", O_RDWR);
	if (-1 == fd) {
		fprintf(stderr, "Error:mem.c: Cannot open /dev/zero\n");
		return -1;
	}
	mmap_ptr = mmap(NULL, alloc_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
	if (MAP_FAILED == mmap_ptr) {
		fprintf(stderr, "Error:mem.c: mmap cannot allocate space\n");
		allocated_once = 0;
		return -1;
	}

	allocated_once = 1;

	// for double word alignment and end mark
	alloc_size -= 8;

	// Initially there is only one big free block in the heap.
	// Skip first 4 bytes for double word alignment requirement.
	heap_start = (blockHeader*) mmap_ptr + 1;

	// Set the end mark
	end_mark = (blockHeader*)((void*)heap_start + alloc_size);
	end_mark->size_status = 1;

	// Set size in header
	heap_start->size_status = alloc_size;

	// Set p-bit as allocated in header
	// note a-bit left at 0 for free
	heap_start->size_status += 2;

	// Set the footer
	blockHeader *footer = (blockHeader*) ((void*)heap_start + alloc_size - 4);
	footer->size_status = alloc_size;

	return 0;
} 

/* STUDENTS MAY EDIT THIS FUNCTION, but do not change function header.
 * TIP: review this implementation to see one way to traverse through
 *      the blocks in the heap.
 *
 * Can be used for DEBUGGING to help you visualize your heap structure.
 * It traverses heap blocks and prints info about each block found.
 * 
 * Prints out a list of all the blocks including this information:
 * No.      : serial number of the block 
 * Status   : free/used (allocated)
 * Prev     : status of previous block free/used (allocated)
 * t_Begin  : address of the first byte in the block (where the header starts) 
 * t_End    : address of the last byte in the block 
 * t_Size   : size of the block as stored in the block header
 */                     
void disp_heap() {     

	int    counter;
	char   status[6];
	char   p_status[6];
	char * t_begin = NULL;
	char * t_end   = NULL;
	int    t_size;

	blockHeader *current = heap_start;
	counter = 1;

	int used_size =  0;
	int free_size =  0;
	int is_used   = -1;

	fprintf(stdout, 
			"********************************** HEAP: Block List ****************************\n");
	fprintf(stdout, "No.\tStatus\tPrev\tt_Begin\t\tt_End\t\tt_Size\n");
	fprintf(stdout, 
			"--------------------------------------------------------------------------------\n");

	while (current->size_status != 1) {
		t_begin = (char*)current;
		t_size = current->size_status;

		if (t_size & 1) {
			// LSB = 1 => used block
			strcpy(status, "alloc");
			is_used = 1;
			t_size = t_size - 1;
		} else {
			strcpy(status, "FREE ");
			is_used = 0;
		}

		if (t_size & 2) {
			strcpy(p_status, "alloc");
			t_size = t_size - 2;
		} else {
			strcpy(p_status, "FREE ");
		}

		if (is_used) 
			used_size += t_size;
		else 
			free_size += t_size;

		t_end = t_begin + t_size - 1;

		fprintf(stdout, "%d\t%s\t%s\t0x%08lx\t0x%08lx\t%4i\n", counter, status, 
				p_status, (unsigned long int)t_begin, (unsigned long int)t_end, t_size);

		current = (blockHeader*)((char*)current + t_size);
		counter = counter + 1;
	}

	fprintf(stdout, 
			"--------------------------------------------------------------------------------\n");
	fprintf(stdout, 
			"********************************************************************************\n");
	fprintf(stdout, "Total used size = %4d\n", used_size);
	fprintf(stdout, "Total free size = %4d\n", free_size);
	fprintf(stdout, "Total size      = %4d\n", used_size + free_size);
	fprintf(stdout, 
			"********************************************************************************\n");
	fflush(stdout);

	return;  
} 


//		p3Heap.c (SP24)                     

