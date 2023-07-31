#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

int numberDivision = 2;

char* concatStringToString(char *s1, char* i) {
    char* s = malloc(256);
    sprintf(s, "%s%s", s1, i);
    return s;
}

char* concatRealToString(char *s1, float i) {
    char* s = malloc(256);
    sprintf(s, "%s%.2f", s1, i);
    return s;
}

float calcolaArea(char* poligono, float base, float altezza, float *area) {


if (((strcmp(poligono,  "rettangolo") == 0) || (strcmp(poligono,  "quadrato") == 0) || (strcmp(poligono,  "triangolo") == 0))) {


area = base * altezza;
}else {


area = (base * altezza) / numberDivision;
}
return area;

}

int main() {

float base = 0, altezza = 0, area;
char* poligono = malloc(256);


printf( "inserisci il poligono di cui vuoi calcolare la sua area: ");

scanf("%s", poligono);

printf( "inserisci la sua base: ");

scanf("%g", &base);

printf( "inserisci la sua altezza: ");

scanf("%g", &altezza);
area = calcolaArea(poligono, base, altezza, area);

printf("%s\n",concatRealToString(concatStringToString(concatStringToString( "L area del poligono ",poligono), " e "),area));

}