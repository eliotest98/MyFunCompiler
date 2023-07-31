#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

int n = 0;

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

int factorial(int n) {

int result = 0;

if (n== 0) {


result = 1;
}else {


result = n * factorial( n - 1) ;
}
return result;

}

int main() {


while (n< 10) {



printf("%s\n", "Enter n or >= 10 to exit: ");

scanf("%d", &n);

printf("%s\n",concatIntegerToString(concatStringToString(concatIntegerToString( "Factorial of ",n), " is "),factorial( n) ));
}
}