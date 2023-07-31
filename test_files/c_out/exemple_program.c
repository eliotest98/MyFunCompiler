#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>

int c = 1;
int m = 10;
int d = 2;

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

float moltiplicazionec(int a, float b, char* *size) {

float result;

result = a * b * c;
if (result> 1000) {

char* valore = creaString( "grande");

*size = valore;
}else {

char* valore = creaString( "piccola");

*size = valore;
}
return result;

}

float divisionec(int a, float b, char* *size) {

float result;

result = a / b / d;
if (result> 100) {

char* valore = creaString( "grande");

*size = valore;
}else {

char* valore = creaString( "piccola");

*size = valore;
}
return result;

}

float sottrazionec(int a, float b, char* *size) {

float result;

result = a - b - m;
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

char* taglia = malloc(256);
int ans = 1;
float risultato = 0.0;
char* operazione = malloc(256);
int a;
float b;


printf( "quale operazione vuoi svolgere? (*,/,+,-) (exit per uscire):	");

scanf("%s", operazione);
if (strcmp(operazione,  "exit") == 0) {


ans = 0;
}else {
}
while (ans== 1) {



printf( "inserisci un intero:");

scanf("%d", &a);

printf( "inserisci un reale:");

scanf("%g", &b);
if (strcmp(operazione,  "+") == 0) {


risultato = sommac(a, b, &taglia);
stampa( concatStringToString(concatStringToString(concatIntegerToString(concatStringToString(concatRealToString(concatStringToString(concatIntegerToString( "la somma di ",a), " e "),b), " incrementata di "),c), " è "),taglia)) ;
stampa( concatRealToString( " ed è pari a ",risultato)) ;
}else {


if (strcmp(operazione,  "-") == 0) {


risultato = sottrazionec(a, b, &taglia);
stampa( concatStringToString(concatStringToString(concatIntegerToString(concatStringToString(concatRealToString(concatStringToString(concatIntegerToString( "la sottrazione di ",a), " e "),b), " decrementata di "),m), " è "),taglia)) ;
stampa( concatRealToString( " ed è pari a ",risultato)) ;
}else {


if (strcmp(operazione,  "/") == 0) {


risultato = divisionec(a, b, &taglia);
stampa( concatStringToString(concatStringToString(concatIntegerToString(concatStringToString(concatRealToString(concatStringToString(concatIntegerToString( "la divisione di ",a), " e "),b), " divisa ulteriormente per "),d), " è "),taglia)) ;
stampa( concatRealToString( " ed è pari a ",risultato)) ;
}else {


if (strcmp(operazione,  "*") == 0) {


risultato = moltiplicazionec(a, b, &taglia);
stampa( concatStringToString(concatIntegerToString(concatStringToString(concatRealToString(concatStringToString(concatIntegerToString( "la moltiplicazione di ",a), " e "),b), " moltiplicata ulteriormente per "),m), " è ")) ;
stampa( concatRealToString( " ed è pari a ",risultato)) ;
}else {


stampa( concatIntegerToString( "simbolo non valido",ans)) ;
}
}
}
}

printf( "quale operazione vuoi svolgere? (*,/,+,-) (exit per uscire):	");

scanf("%s", operazione);
if (strcmp(operazione,  "exit") == 0) {


ans = 0;
}else {
}
}
printf("%s\n", "");

printf("%s", "ciao");

}