#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


char* concatStringToString(char *s1, char* i) {
    char* s = malloc(256);
    sprintf(s, "%s%s", s1, i);
    return s;
}

char* concatIntegerToString(char *s1, int i) {
    char* s = malloc(256);
    sprintf(s, "%s%d", s1, i);
    return s;
}

int powFun(int x, int y) {

int temp;

temp = pow(x , y);
return temp;

}

int main() {

int x, y, z;


printf( "Inserire il primo numero : ");

scanf("%d", &x);

printf( "Inserire il secondo numero : ");

scanf("%d", &y);
z = powFun(x, y);

printf("%s\n",concatIntegerToString(concatStringToString(concatIntegerToString(concatStringToString(concatIntegerToString( "result pow ",x), "*"),y), "="),z));

}