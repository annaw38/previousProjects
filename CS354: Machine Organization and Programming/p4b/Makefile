# Note: requires a 64-bit x86-64 system 
CC = gcc
CFLAGS = -Wall -std=gnu99 -m64 -g

all: csim.c
	$(CC) $(CFLAGS) -o csim csim.c -lm

memcheck: 
	valgrind --leak-check=yes ./csim -s 4 -E 1 -b 4 -t ./traces/trace2 # or other test
	valgrind --leak-check=yes ./csim -s 1 -E 1 -b 1 -t ./traces/trace1 # or other test
	valgrind --leak-check=yes ./csim -s 4 -E 2 -b 4 -t ./traces/trace2
	valgrind --leak-check=yes ./csim -s 2 -E 1 -b 4 -t ./traces/trace3
	valgrind --leak-check=yes ./csim -s 2 -E 1 -b 3 -t ./traces/trace4
	valgrind --leak-check=yes ./csim -s 2 -E 2 -b 3 -t ./traces/trace4  
	valgrind --leak-check=yes ./csim -s 2 -E 4 -b 3 -t ./traces/trace4 
	valgrind --leak-check=yes ./csim -s 5 -E 1 -b 5 -t ./traces/trace4 
	valgrind --leak-check=yes ./csim -s 5 -E 1 -b 5 -t ./traces/trace5
 
# Clean the src dirctory
clean:
	rm -f csim
