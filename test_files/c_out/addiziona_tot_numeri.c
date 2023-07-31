#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


char* concatRealToString(char *s1, float i) {
    char* s = malloc(256);
    sprintf(s, "%s%.2f", s1, i);
    return s;
}

char* concatIntegerToString(char *s1, int i) {
    char* s = malloc(256);
    sprintf(s, "%s%d", s1, i);
    return s;
}

char* concatStringToString(char *s1, char* i) {
    char* s = malloc(256);
    sprintf(s, "%s%s", s1, i);
    return s;
}

void numeri(int *scelta) {


if (scelta> 15) {



printf("%s\n", "hai inserito troppi numeri!");
while (scelta> 15) {



printf( "Quanti numeri vuoi sommare? (inserire meno di 15 numeri)");

scanf("%d", &scelta);
if (scelta> 15) {



printf("%s\n", "hai inserito troppi numeri!");
}else {
}
}}else {
}

}

float addiziono(int numeri) {

float addendo;
int count = 0;
float risultato = 0.0;

while (count< numeri) {



printf("%s\n",concatStringToString(concatIntegerToString( "inserisci il ",count), " numero: "));

scanf("%g", &addendo);
risultato = risultato + addendo;

printf("%s\n",concatRealToString( "attualmente il risultato e: ",risultato));
count = count + 1;
}return risultato;

}

int main() {

int num;
float risultato;


printf( "Quanti numeri vuoi sommare? (inserire meno di 15 numeri)");

scanf("%d", &num);
numeri( num) ;
risultato = addiziono(num);

printf("%s\n",concatRealToString( "Il risultato delle operazioni e: ",risultato));

}