#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


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

int main() {

int number;
int oracolo = 70;
int tentativiMancanti = 10;


printf("%s\n",concatStringToString(concatIntegerToString( "Questo gioco si chiama indovina il numero, il tuo obiettivo e quello di indovinare il numero in massimo ",tentativiMancanti), " tentativi in bocca al lupo!"));

printf( "Scrivi un numero fra 1 e 100 ");

scanf("%d", &number);
while (number!= oracolo) {


tentativiMancanti = tentativiMancanti - 1;
if (number> oracolo) {



printf("%s\n", "Il numero che stai cercando e più piccolo!");
}else {



printf("%s\n", "Il numero che stai cercando e più grande!");
}
if (number!= oracolo) {



printf( "Inserisci un altro numero ");

scanf("%d", &number);
}else {
}
if (tentativiMancanti== 0) {


number = 70;
}else {
}
}if (tentativiMancanti!= 0) {



printf("%s\n",concatStringToString(concatIntegerToString( "Ti mancavano solo ",tentativiMancanti), " tentativi, hai trovato il numero bravo!"));
}else {



printf("%s\n",concatIntegerToString( "Mi spiace hai esaurito i tuoi tentativi a disposizione, il numero era ",oracolo));
}

}