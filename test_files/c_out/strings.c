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

char* creaString(char* string){
	char* s = malloc(256);
	sprintf(s, "%s",string);
	return s;
}

int main() {

char* lettera = malloc(256);
char* oracolo = creaString( "e");
int tentativiMancanti = 10;


printf("%s\n",concatStringToString(concatIntegerToString( "Questo gioco si chiama indovina la lettera, il tuo obiettivo e quello di indovinare la lettera in massimo ",tentativiMancanti), " tentativi in bocca al lupo!"));

printf( "Scrivi una lettera ");

scanf("%s", lettera);
while (strcmp(lettera, oracolo) != 0 && tentativiMancanti!= 0) {


tentativiMancanti = tentativiMancanti - 1;
if (strcmp(lettera, oracolo) == 0) {



printf("%s\n", "Trovata!");
}else {



printf("%s\n", "Non e la lettera giusta riprova!");

printf( "Scrivi una lettera ");

scanf("%s", lettera);
}
}if (tentativiMancanti!= 0) {



printf("%s\n",concatStringToString(concatIntegerToString( "Ti mancavano solo ",tentativiMancanti), " tentativi, hai trovato la lettera bravo!"));
}else {



printf("%s\n",concatStringToString( "Mi spiace hai esaurito i tuoi tentativi a disposizione, la lettera era ",oracolo));
}

}