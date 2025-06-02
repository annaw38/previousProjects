////////////////////////////////////////////////////////////////////////////////
// Semester:         CS 354 Lecture 002      SPRING 2024
// Grade Group:      gg_2_  (See canvas.wisc.edu/groups for your gg#)
// Instructor:       deppeler
// 
// Author:           Anna Wang
// Email:            awang282@wisc.edu
// CS Login:         annaw
//////////////////////////// 80 columns wide ///////////////////////////////////

int arr[128][8];

int main(int argc, char* argv[]){
	for(int i = 0; i < 100; i++){
		for(int j = 0; j < 128; j += 64){
			for(int k = 0; k < 8; k++){
				arr[i][j] = i + j + k;
			}
		}
	}
	return 0;
}
