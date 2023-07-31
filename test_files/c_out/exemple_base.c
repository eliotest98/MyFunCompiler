#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

int c = 1;

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

char* creaString(char* string){
	char* s = malloc(256);
	sprintf(s, "%s",string);
	return s;
}

float sommac(int a, float b, char* *size) {

float result;

result = a + b + c;
if (result> 100) {

char* valore = creaString( "grande");

*size = valore;
}else {

char* valore = creaString( "piccola");

*size = valore;
}
return result;

}

void stampa(char* messaggio) {

int i = 1;

while (i<= 4) {

int incremento = 1;


printf("%s\n", "");
i = i + incremento;
}
printf("%s\n",messaggio);

}

int main() {

int a = 1;
float b = 2.2;
char* taglia = malloc(256);
char* ans = creaString( "no");
float risultato = sommac( a, b, &taglia) ;

stampa( concatStringToString(concatStringToString(concatIntegerToString(concatStringToString(concatRealToString(concatStringToString(concatIntegerToString( "la somma di ",a), " e "),b), " incrementata di "),c), " e "),taglia)) ;
stampa( concatRealToString( "ed e pari a ",risultato)) ;

printf("%s\t", "vuoi continuare? (si/no)");

scanf("%s", ans);
while (strcmp(ans,  "si") == 0) {



printf( "inserisci un intero:");

scanf("%d", &a);

printf( "inserisci un reale:");

scanf("%g", &b);
risultato = sommac(a, b, &taglia);
stampa( concatStringToString(concatStringToString(concatIntegerToString(concatStringToString(concatRealToString(concatStringToString(concatIntegerToString( "la somma di ",a), " e "),b), " incrementata di "),c), " e "),taglia)) ;
stampa( concatRealToString( " ed e pari a ",risultato)) ;

printf( "vuoi continuare? (si/no):	");

scanf("%s", ans);
}
printf("%s\n", "");

printf("%s", "ciao");

}