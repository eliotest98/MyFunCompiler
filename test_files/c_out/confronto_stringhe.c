#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


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

char* name = malloc(256);
char* surname = malloc(256);
char* provaNome = malloc(256);
char* provaCognome = malloc(256);
char* codice = malloc(256);
char* provaCode = creaString( "Sn4aG9Lc25");
int nm, cm, cd;
int i;


printf( "Inserisci il nome : ");

scanf("%s", name);

printf( "Inserisci il cognome : ");

scanf("%s", surname);

printf("%s\n", "");

printf( "Ripetere il nome per effettuare autenticazione : ");

scanf("%s", provaNome);

printf( "Ripetere il cognome per effettuare autenticazione : ");

scanf("%s", provaCognome);
while (strcmp(provaCode, codice) != 0) {



printf("%s\n", "Verifichiamo che non sei un Robot");

printf("%s\n",concatStringToString( "Inserisci la seguente frase: ",provaCode));

scanf("%s", codice);
if (strcmp(codice, provaCode) == 0) {


cd = 1;
}else {
}
}if (strcmp(provaNome, name) == 0) {


nm = 1;
}else {


nm = 0;

printf("%s\b", "ERRORE : I due nomi inseriti non coincidono");
}
if (strcmp(provaCognome, surname) == 0) {


cm = 1;
}else {


cm = 0;

printf("%s\b", "ERRORE : I due cognomi inseriti non coincidono");
}
if (nm== 1) {


if (cm== 1) {


if (cd== 1) {



printf("%s\b", "Autenticazione effettuata con successo");
}else {
}
}else {
}
}else {
}

printf("%s\n", "");

printf("%s\n", "fine");

}