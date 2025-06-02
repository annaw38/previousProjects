//test ptr is null, ptr is already freed, ptr is outside heap 

#include <assert.h>
#include <stdlib.h>
#include "p3Heap.h"

int main() {
	assert(init_heap(4096) == 0);
	void * ptr[3];
	
	void *ptr1 = NULL;
	void *p2 = (balloc(25));
	void *p3 = (balloc(14));
    assert(bfree(ptr1) == -1);
	assert(bfree(p2) == 0);
	assert(bfree(p2) == -1);
	ptr1 = &p3 - 1;
	assert(bfree(ptr1) == -1);
	ptr1 = (void *)0x007;
	assert(bfree(ptr1) == -1);
	exit(0);
}
