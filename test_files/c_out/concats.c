#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>


int main() {

int x = 1, y = 5, z = 9;
float k = 0.0;
char* parola =  "elio";
int unBooleano = 1;

while ((((((x== 1) || (y== 5)) || z== 9) && strcmp(parola,  "elio") == 0) && unBooleano== 1)) {



printf( "Dammi un numero con decimale ");

scanf("%g", &k);
k = ((k + x) + (y + z));
if (k> 70) {


unBooleano = 0;
}else {

char* nuova = malloc(256);


printf( "Scrivi la parola (elio) ");

scanf("%s", nuova);
if (strcmp(parola, nuova) == 0) {

int nuovax;


printf( "Scrivi il numero (1) ");

scanf("%d", &nuovax);
if (nuovax== x) {


x = 2;
}else {
}
}else {
}
}
}
printf("%s\n", "");

printf("%s\n", "fine");

}