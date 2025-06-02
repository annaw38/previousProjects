////////////////////////////////////////////////////////////////////////////////
// Semester:         CS 354 Lecture 002      SPRING 2024
// Grade Group:      gg_2_  (See canvas.wisc.edu/groups for your gg#)
// Instructor:       deppeler
// 
// Author:           Anna Wang
// Email:            awang282@wisc.edu
// CS Login:         annaw
//////////////////////////// 80 columns wide ///////////////////////////////////

int arr[3000][500];

int main(int argc, char* argv[]){
	for(int i = 0; i < 500; i++){
		for(int j = 0; j < 3000; j++){
			arr[j][i] = j + i;
		}
	}
	return 0;
}
