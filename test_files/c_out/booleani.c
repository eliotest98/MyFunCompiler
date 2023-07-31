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

int sceltaT = 0, sceltaF = 0;
int decisione;


printf("%s\n", "Per ognuna delle seguenti frasi rispondere con 1 ('true') o 0 ('false')");

printf("%s\n", "1. Ti piace vedere serie TV?");

scanf("%d", &decisione);
if (decisione== 1) {


sceltaT = sceltaT + 1;
}else {


sceltaF = sceltaF + 1;
}

printf("%s\n", "2. Ti piace la pizza?");

scanf("%d", &decisione);
if (decisione== 1) {


sceltaT = sceltaT + 1;
}else {


sceltaF = sceltaF + 1;
}

printf("%s\n", "3. Ti piace fare sport?");

scanf("%d", &decisione);
if (decisione== 1) {


sceltaT = sceltaT + 1;
}else {


sceltaF = sceltaF + 1;
}

printf("%s\n", "4. Ti piace uscire con gli amici?");

scanf("%d", &decisione);
if (decisione== 1) {


sceltaT = sceltaT + 1;
}else {


sceltaF = sceltaF + 1;
}

printf("%s\n", "5. Ti piace fare shopping?");

scanf("%d", &decisione);
if (decisione== 1) {


sceltaT = sceltaT + 1;
}else {


sceltaF = sceltaF + 1;
}

printf("%s\n",concatStringToString(concatIntegerToString(concatStringToString(concatIntegerToString( "hai dato ",sceltaT), " risposte 'true' e "),sceltaF), " risposte 'false'"));
if (sceltaT> sceltaF) {



printf("%s\n", "Sei una persona davvero Interessante!");
}else {
}
if (sceltaF> sceltaT) {



printf("%s\n", "Prova a socializzare di piu");
}else {
}

}