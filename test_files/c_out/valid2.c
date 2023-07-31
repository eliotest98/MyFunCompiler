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

char* concatRealToString(char *s1, float i) {
    char* s = malloc(256);
    sprintf(s, "%s%.2f", s1, i);
    return s;
}

int print_menu() {

int choose;


printf("%s\n", "Scegli quale operazione vuoi svolgere per continuare");

printf("%s\n", "	(1) Somma di due numeri");

printf("%s\n", "	(2) Moltiplicazione di due numeri");

printf("%s\n", "	(3) Divisione intera fra due numeri positivi");

printf("%s\n", "	(4) Elevamento a potenza");

printf("%s\n", "	(5) Successione di Fibonacci (ricorsiva)");

printf("%s\n", "	(6) Successione di Fibonacci (iterativa)");

printf("%s\n", "	(0) Esci");

printf( "--> ");

scanf("%d", &choose);
return choose;

}

void do_sum() {

float op1, op2;


printf("%s\n", "(1) SOMMA");

printf( "Inserisci il primo operando: ");

scanf("%g", &op1);

printf( "Inserisci il secondo operando: ");

scanf("%g", &op2);

printf("%s\n", "");

printf("%s\n",concatRealToString(concatStringToString(concatRealToString(concatStringToString(concatRealToString( "La somma tra ",op1), " e "),op2), " vale "),op1 + op2));

}

void do_mul() {

float op1, op2;


printf("%s\n", "(2) MOLTIPLICAZIONE");

printf( "Inserisci il primo operando: ");

scanf("%g", &op1);

printf( "Inserisci il secondo operando: ");

scanf("%g", &op2);

printf("%s\n", "");

printf("%s\n",concatRealToString(concatStringToString(concatRealToString(concatStringToString(concatRealToString( "La moltiplicazione tra ",op1), " e "),op2), " vale "),op1 * op2));

}

void do_div_int() {

int op1, op2;


printf("%s\n", "(3) DIVISIONE INTERA");

printf( "Inserisci il primo operando: ");

scanf("%d", &op1);

printf( "Inserisci il secondo operando: ");

scanf("%d", &op2);

printf("%s\n", "");

printf("%s\n",concatIntegerToString(concatStringToString(concatIntegerToString(concatStringToString(concatIntegerToString( "La divisione intera tra ",op1), " e "),op2), " vale "),op1 / op2));

}

void do_pow() {

float op1, op2;


printf("%s\n", "(4) POTENZA");

printf( "Inserisci la base: ");

scanf("%g", &op1);

printf( "Inserisci esponente: ");

scanf("%g", &op2);

printf("%s\n", "");

printf("%s\n",concatRealToString(concatStringToString(concatRealToString(concatStringToString(concatRealToString( "La potenza di ",op1), " elevato a "),op2), " vale "),pow(op1 , op2)));

}

int recursive_fib(int n) {


if (n== 1) {


return 0;
}else {
}
if (n== 2) {


return 1;
}else {
}
return recursive_fib( n - 1)  + recursive_fib( n - 2) ;

}

int iterative_fib(int n) {


if (n== 1) {


return 0;
}else {
}
if (n== 2) {


return 1;
}else {
}
if (n> 2) {

int i = 3, res = 1, prev = 0;

while (i<= n) {

int tmp = res;

res = res + prev;
prev = tmp;
i = i + 1;
}return res;
}else {
}
return  - 1;

}

void do_fib(int recursive) {

int n;
char* message = malloc(256);


printf("%s\n", "(5) FIBONACCI");

printf( "Inserisci n: ");

scanf("%d", &n);

printf("%s\n", "");
*message = concatStringToString(concatIntegerToString( "Il numero di Fibonacci in posizione ",n), " vale ");
if (recursive) {


*message = concatIntegerToString(message,recursive_fib( n) );
}else {


*message = concatIntegerToString(message,iterative_fib( n) );
}

printf("%s\n",message);

}

void do_operation(int choose) {


if (choose== 1) {


do_sum() ;
}else {


if (choose== 2) {


do_mul() ;
}else {


if (choose== 3) {


do_div_int() ;
}else {


if (choose== 4) {


do_pow() ;
}else {


if (choose== 5) {


do_fib( 1) ;
}else {


if (choose== 6) {


do_fib( 0) ;
}else {
}
}
}
}
}
}

}

void print_continue(int *cont) {

char* in = malloc(256);


printf( "Vuoi continuare? (s/n) --> ");

scanf("%s", in);
if (strcmp(in,  "s") == 0) {


cont = 1;
}else {


cont = 0;
}

}

int main() {

int choose = 0;
int cont = 1;

while (cont) {


choose = print_menu();
if (choose== 0) {


cont = 0;
}else {


do_operation( choose) ;
print_continue( cont) ;
}
}
}