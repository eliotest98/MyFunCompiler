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

int fibonacci(int number) {


if (number< 2) {


return number;
}else {
}
return fibonacci( number - 1)  + fibonacci( number - 2) ;

}

int main() {

int n = 0;
int result = 0;


printf( "Inserisci la lunghezza della sequenza di Fibonacci:");

scanf("%d", &n);
result = fibonacci(n);

printf("%s\n",concatIntegerToString(concatStringToString(concatIntegerToString( "Fibonacci of ",n), " is "),result));

}