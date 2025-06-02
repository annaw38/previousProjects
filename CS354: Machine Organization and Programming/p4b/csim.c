///////////////////////////////////////////////////////////////////////////////
// Main File:        csim.c
// This File:        csim.c
// Other Files:      N/A
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
//  3/17	Copied starter files.
//  3/21	Started init_cache, free_cache, replay_trace. Need to learn bit masking.
//  3/26	Added comments of steps to access_data.
//  3/27	Started implementing least recently used algorithmn by adding counter
//			in the cache_line
//  3/29	Extracted the S and B bits in the access_data function
//	3/30	Added steps to look for the set number and check if the block is in cache.
//	4/1		Added for loops to look through if the set is full and find the max value.
//			BUG: 222-262 Does miss and evict even when it's a hit.
//  4/3		Fixed tMask to find the tag bits and LRU counter to find the lru.
//			However, still have to check for memory leaks.
//	4/5		Ran valgrind and there are no memory leaks for all the test traces. 
//	4/5		Added more comments, everything seems to be working.   
///////////////////////// OTHER SOURCES OF HELP ////////////////////////////// 
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
// Date:   Name (email):   Helped with: (brief description)
// ---------------------------------------------------------------------------
// 4/1		afge@wisc.edu  Understanding access_data's steps/algorithm.
// 4/3     jfcooper2@wisc.edu Helped me realize that my tMask was wrong and lru counter. 
//////////////////////////// 80 columns wide ///////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
// Copyright 2013,2019-2024
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission for Spring 2024
////////////////////////////////////////////////////////////////////////////////

/**
 * csim.c:  
 * A cache simulator that can replay traces (from Valgrind) and output
 * statistics to determine the number of hits, misses, and evictions.
 * The replacement policy is LRU (least-recently used).
 *
 * Implementation and assumptions:
 *  1. (L) load or (S) store cause at most one cache miss and a possible eviction.
 *  2. (I) Instruction loads are ignored.
 *  3. (M) Data modify is treated as a load followed by a store to the same
 *  address. Hence, an (M) operation can result in two cache hits, 
 *  or a miss and a hit plus a possible eviction.
 */  

#include <getopt.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <assert.h>
#include <math.h>
#include <limits.h>
#include <string.h>
#include <errno.h>
#include <stdbool.h>

/******************************************************************************/
/* DO NOT MODIFY THESE VARIABLE NAMES and TYPES                               */
/* DO UPDATE THEIR VALUES AS NEEDED BY YOUR CSIM                              */

//Globals set by command line args.
int b = 0; //number of (b) bits
int s = 0; //number of (s) bits
int E = 0; //number of lines per set

//Globals derived from command line args.
int B; //block size in bytes: B = 2^b
int S; //number of sets: S = 2^s

//Global counters to track cache statistics in access_data().
int hit_cnt = 0;
int miss_cnt = 0;
int evict_cnt = 0;

//Global to control trace output
int verbosity = 0; //print trace if set
/******************************************************************************/


//Type mem_addr_t: Use when dealing with addresses or address masks.
typedef unsigned long long int mem_addr_t;

//Type cache_line_t: Use when dealing with cache lines.
typedef struct cache_line {                    
	char valid;
	mem_addr_t tag;
	int counter;
} cache_line_t;

//Type cache_set_t: Use when dealing with cache sets
//Note: Each set is a pointer to a heap array of one or more cache lines.
typedef cache_line_t* cache_set_t;

//Type cache_t: Use when dealing with the cache.
//Note: A cache is a pointer to a heap array of one or more sets.
typedef cache_set_t* cache_t;

// Create the cache we're simulating. 
//Note: A cache is a pointer to a heap array of one or more cache sets.
cache_t cache;  

/* 
 * init_cache:
 * Allocates the data structure for a cache with S sets and E lines per set.
 * Initializes all valid bits and tags with 0s.
 */                    
void init_cache() {
	//S = 2^s and B = 2^b 
	S = pow(2, s);
	B = pow(2, b);
	//Pointer to the array of sets.
	cache = malloc(sizeof(cache_set_t) * S);
	//Check that the pointer to the array of sets is not NULL.
	if(cache == NULL){
		printf("Malloc for cache initialization failed\n");
		exit(1);
	}
	//Pointers to the array of cache lines in each set.
	for(int i = 0; i < S; i++){
		cache[i] = malloc(sizeof(cache_line_t) * E);
		//Check that the pointers to the array of lines in each set is not NULL.
		if(cache[i] == NULL){
			free(cache);
			printf("Malloc for line initialization failed\n");
			exit(1);
		}
		//set each cache line's valid bit, tag, and counter to 0. 
		for(int j = 0; j < E; j++){
			cache[i][j].valid = 0;
			cache[i][j].tag = 0;
			cache[i][j].counter = 0;
		}

	}
}


/*  
 * free_cache:
 * Frees all heap allocated memory used by the cache.
 */                    
void free_cache() {  
	//Free the pointers to the array of lines in each set.
	for(int i = 0; i < S; i++){
		free(cache[i]);
		cache[i] = NULL;
	}
	//Then free the cache.
	free(cache);      
	cache = NULL;     
}


/*  
 * access_data:
 * Simulates data access at given "addr" memory address in the cache.
 *
 * If already in cache, increment hit_cnt
 * If not in cache, cache it (set tag), increment miss_cnt
 * If a line is evicted, increment evict_cnt
 */                    
void access_data(mem_addr_t addr) {  
	//Extract the set number.
	//Start with the number of sets - 1 to get 0 then all 1s.
	double startSMask = S - 1;
	//printf("mask start: %f\n", a); //debug line
	//Shift left by the number of b bits.
	int sMask = (int)startSMask << b;
	//printf("mask = %d\n", (a << 2)); //debug line
	//And the addr and the sMask to get the set number.
	int setNum = addr & sMask;
	//Then shift right by the number of b bits to get the set number.
	setNum = setNum >> b;
	//printf("set number: %i\n", setNum); //debug line

	//Extract the t-bits - given 64 bit addr. 
	//Calculate the number of t bits 64 = t + s + b.
	int t = 64 - (s + b); //number of t-bits
	//Shift 1 to the left tbits then -1 to get t 1's then shift left again to
	//get them to the correct position.
	int tMask = ((1 << t) - 1) << (s + b);
	//And the addr and tMask to get the t-bits.
	mem_addr_t tBits = addr & tMask;
	//Shift the t bits back to the right to get the correct t-bits.
	tBits = tBits >> (s + b);
	//printf("tag addr:  %lli\n", tBits); //debug line

	//Using Least Recently Used (LRU) counter method.
	int max = 0; //max value of the counters in the set 
	int LRU = 0; //LRU - cache line with the smallest counter
	int min = cache[setNum][0].counter; //set the first cache line counter as the minimum
	for(int i = 0; i < E; i++){
		if(cache[setNum][i].counter > max){
			max = cache[setNum][i].counter; 
		}
		//Get the line with the smallest counter value.	
		if(cache[setNum][i].counter < min){
			min = cache[setNum][i].counter;
			LRU = i;
		}	
	}
	int match = -1; //holds the cache line with the block, otherwise cache miss
	for(int i = 0; i < E; i++){
		//If there's a hit, then set match to the current cache line.
		if(cache[setNum][i].valid == 1 && cache[setNum][i].tag == tBits){
			match = i; 
		}
	}
	//If the block is in cache:
	if(match != -1){
		//Increase the cahce line's counter, since it was accessed.
		cache[setNum][match].counter = max + 1;
		hit_cnt++;
		//printf("hit "); //debug line
	}
	//The block is not in the cache.
	else{
		int emptyLine = -1; //the line number of an empty cache line, -1 if full
		//Check if the cache lines are full.
		for(int j = 0; j < E; j++){
			if(cache[setNum][j].valid == 0){
				emptyLine = j;
			}
		}
		//There is an empty cache line:
		if(emptyLine != -1){
			//Set the tag, valid bit to 1, and increase counter to the max + 1.
			cache[setNum][emptyLine].tag = tBits;
			cache[setNum][emptyLine].valid = 1;
			cache[setNum][emptyLine].counter = max + 1;
			//Increase miss count.
			miss_cnt++;
			//printf("miss "); //debug line
		}
		//There are no empty lines - use LRU to evict a block.
		else{
			//Set the new tag, valid, and counter to max + 1.
			cache[setNum][LRU].tag = tBits;
			cache[setNum][LRU].valid = 1;
			cache[setNum][LRU].counter = max + 1;
			//Increase miss and evict count.
			miss_cnt++;
			evict_cnt++;
			//printf("miss evict "); //debug line
		}
	}	
}


/* 
 * replay_trace:
 * Replays the given trace file against the cache.
 *
 * Reads the input trace file line by line.
 * Extracts the type of each memory access : L/S/M
 * TRANSLATE each "L" as a load i.e. 1 memory access
 * TRANSLATE each "S" as a store i.e. 1 memory access
 * TRANSLATE each "M" as a load followed by a store i.e. 2 memory accesses 
 */                    
void replay_trace(char* trace_fn) {           
	char buf[1000];  
	mem_addr_t addr = 0;
	unsigned int len = 0;
	FILE* trace_fp = fopen(trace_fn, "r"); 

	if (!trace_fp) { 
		fprintf(stderr, "%s: %s\n", trace_fn, strerror(errno));
		exit(1);   
	}

	while (fgets(buf, 1000, trace_fp) != NULL) {
		if (buf[1] == 'S' || buf[1] == 'L' || buf[1] == 'M') {
			sscanf(buf+3, "%llx,%u", &addr, &len);

			if (verbosity)
				printf("%c %llx,%u ", buf[1], addr, len);

			if(buf[1] == 'S' || buf[1] == 'L'){
				access_data(addr);
			}
			else if(buf[1] == 'M'){
				access_data(addr);
				access_data(addr);
			}
			else{
				break;
			}

			if (verbosity)
				printf("\n");
		}
	}

	fclose(trace_fp);
}  


/*
 * print_usage:
 * Print information on how to use csim to standard output.
 */                    
void print_usage(char* argv[]) {                 
	printf("Usage: %s [-hv] -s <num> -E <num> -b <num> -t <file>\n", argv[0]);
	printf("Options:\n");
	printf("  -h         Print this help message.\n");
	printf("  -v         Verbose flag.\n");
	printf("  -s <num>   Number of s bits for set index.\n");
	printf("  -E <num>   Number of lines per set.\n");
	printf("  -b <num>   Number of b bits for word and byte offsets.\n");
	printf("  -t <file>  Trace file.\n");
	printf("\nExamples:\n");
	printf("  linux>  %s -s 4 -E 1 -b 4 -t traces/yi.trace\n", argv[0]);
	printf("  linux>  %s -v -s 8 -E 2 -b 4 -t traces/yi.trace\n", argv[0]);
	exit(0);
}  


/*
 * print_summary:
 * Prints a summary of the cache simulation statistics to a file.
 */                    
void print_summary(int hits, int misses, int evictions) {                
	printf("hits:%d misses:%d evictions:%d\n", hits, misses, evictions);
	FILE* output_fp = fopen(".csim_results", "w");
	assert(output_fp);
	fprintf(output_fp, "%d %d %d\n", hits, misses, evictions);
	fclose(output_fp);
}  


/*
 * main:
 * parses command line args, 
 * makes the cache, 
 * replays the memory accesses, 
 * frees the cache and 
 * prints the summary statistics.  
 */                    
int main(int argc, char* argv[]) {                      
	char* trace_file = NULL;
	char c;

	// Parse the command line arguments: -h, -v, -s, -E, -b, -t 
	while ((c = getopt(argc, argv, "s:E:b:t:vh")) != -1) {
		switch (c) {
			case 'b':
				b = atoi(optarg);
				break;
			case 'E':
				E = atoi(optarg);
				break;
			case 'h':
				print_usage(argv);
				exit(0);
			case 's':
				s = atoi(optarg);
				break;
			case 't':
				trace_file = optarg;
				break;
			case 'v':
				verbosity = 1;
				break;
			default:
				print_usage(argv);
				exit(1);
		}
	}

	//Make sure that all required command line args were specified.
	if (s == 0 || E == 0 || b == 0 || trace_file == NULL) {
		printf("%s: Missing required command line argument\n", argv[0]);
		print_usage(argv);
		exit(1);
	}

	//Initialize cache.
	init_cache();

	//Replay the memory access trace.
	replay_trace(trace_file);

	//Free memory allocated for cache.
	free_cache();

	//Print the statistics to a file.
	//DO NOT REMOVE: This function must be called for test_csim to work.
	print_summary(hit_cnt, miss_cnt, evict_cnt);
	return 0;   
}  

// 202401                                     

