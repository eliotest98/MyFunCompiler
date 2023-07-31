#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


char* creaString(char* string){
	char* s = malloc(256);
	sprintf(s, "%s",string);
	return s;
}

int main() {

char* a =  "b";
char* b = creaString( "a");
int result = 0;
int flag = 0;


}