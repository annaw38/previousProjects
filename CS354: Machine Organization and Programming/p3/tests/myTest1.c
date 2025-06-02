// check for coalesce free space
#include <assert.h>
#include <stdlib.h>
#include "p3Heap.h"

int main() {
   assert(init_heap(4096) == 0);
   void * ptr[5];

	ptr[0] = balloc(60);
	
	ptr[1] = balloc(80);
	assert(ptr[1] != NULL);

   
   ptr[2] = balloc(800);
   assert(ptr[2] != NULL);

   ptr[3] = balloc(90);
   assert(ptr[3] != NULL);

   ptr[4] = balloc(90);
	assert(ptr[4] != NULL);

	bfree(ptr[0]);
	bfree(ptr[1]);
	bfree(ptr[3]);

   ptr[3] = balloc(90);
   assert(ptr[3] != NULL);



   exit(0);
}
