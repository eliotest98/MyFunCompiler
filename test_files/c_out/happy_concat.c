#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


char* concatIntegerToString(char *s1, int i) {
    char* s = malloc(256);
    sprintf(s, "%s%d", s1, i);
    return s;
}

char* concatRealToString(char *s1, float i) {
    char* s = malloc(256);
    sprintf(s, "%s%.2f", s1, i);
    return s;
}

char* concatStringToString(char *s1, char* i) {
    char* s = malloc(256);
    sprintf(s, "%s%s", s1, i);
    return s;
}

char* creaString(char* string){
	char* s = malloc(256);
	sprintf(s, "%s",string);
	return s;
}

int main() {

int aInt;
float aReal;
int numberTime;
int counter = 0;
char* aString = creaString( "no");


printf( "inserisci un intero: ");

scanf("%d", &aInt);

printf( "inserisci una stringa: ");

scanf("%s", aString);

printf( "inserisci un reale: ");

scanf("%g", &aReal);

printf( "quante volte vuoi stampare? ");

scanf("%d", &numberTime);
while (counter< numberTime) {



printf("%s\n",concatStringToString(concatRealToString(concatStringToString(concatStringToString(concatStringToString(concatIntegerToString( "concatena un intero {",aInt), "} con una stringa {"),aString), "} e un real {"),aReal), "}"));
counter = counter + 1;
}
}