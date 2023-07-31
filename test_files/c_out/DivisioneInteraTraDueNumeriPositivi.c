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

int dividi(int x, int y) {

int z = 0, i = x;

while (i>= y) {


i = i - y;
z = z + 1;
}return z;

}

int divInt(int x, int y) {

int z = x / y;

return z;

}

int main() {

int x, y, z, h;


printf( "Inserire il primo numero : ");

scanf("%d", &x);

printf( "Inserire il secondo numero : ");

scanf("%d", &y);
z = dividi(x, y);
h = divInt(x, y);

printf("%s\n",concatIntegerToString(concatStringToString(concatIntegerToString(concatStringToString(concatIntegerToString( "result divisione ",x), "/"),y), "="),z));

printf("%s\n",concatIntegerToString(concatStringToString(concatIntegerToString(concatStringToString(concatIntegerToString( "result divInt ",x), "/"),y), "="),z));

}