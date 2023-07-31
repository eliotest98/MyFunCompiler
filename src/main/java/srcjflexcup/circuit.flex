// C:\JFLEX\bin\jflex -d src  src\main\java\srcjflexcup\circuit.flex

import java_cup.runtime.*;
import java.util.HashMap;
import java.util.Collection;

%%

%class Lexer
%function next_token
%unicode
%cup
%cupsym CircuitSym
%line
%column
%char

%{
    private HashMap<String, Symbol> stringTable = new HashMap<String, Symbol>();

    private StringBuffer stringBuffer = new StringBuffer();

    private Symbol installID(String lessema) {
           if(stringTable.containsKey(lessema))
               return stringTable.get(lessema);
           else{
               Symbol symbol = new Symbol(CircuitSym.ID, lessema);
               stringTable.put(lessema, symbol);
               return symbol;
           }
       }

       public void printStringTable(){
           Collection<Symbol> symbols = stringTable.values();
           //System.out.println("\n\t\t\t\t\t\t*** STRING TABLE ***\n");
           //System.out.format("%13s%13s%13s%13s%13s\n", "TOKEN NAME", "TOKEN VALUE", "ROW", "COLUMN", "PARSE STATE");
           /*for(Symbol symbol : symbols){
               System.out.format("%13s%13s%13s%13s%13s\n", CircuitSym.terminalNames[symbol.sym], symbol.value, symbol.left, symbol.right, symbol.parse_state);
           }*/
       }
%}

%init{
    stringTable = new HashMap<String, Symbol>();
    stringTable.put("if", new Symbol(CircuitSym.IF));
    stringTable.put("then", new Symbol(CircuitSym.THEN));
    stringTable.put("else", new Symbol(CircuitSym.ELSE));
    stringTable.put("while", new Symbol(CircuitSym.WHILE));
    stringTable.put("integer", new Symbol(CircuitSym.INTEGER));
    stringTable.put("real", new Symbol(CircuitSym.REAL));
    stringTable.put("string", new Symbol(CircuitSym.STRING));
    stringTable.put("bool", new Symbol(CircuitSym.BOOL));
    stringTable.put("and", new Symbol(CircuitSym.AND));
    stringTable.put("or", new Symbol(CircuitSym.OR));
    stringTable.put("not", new Symbol(CircuitSym.NOT));
    stringTable.put("main", new Symbol(CircuitSym.MAIN));
    stringTable.put("div", new Symbol(CircuitSym.DIVINT));
    stringTable.put("fun", new Symbol(CircuitSym.FUN));
    stringTable.put("end", new Symbol(CircuitSym.END));
    stringTable.put("true", new Symbol(CircuitSym.TRUE));
    stringTable.put("false", new Symbol(CircuitSym.FALSE));
    stringTable.put("loop", new Symbol(CircuitSym.LOOP));
    stringTable.put("out", new Symbol(CircuitSym.OUT));
    stringTable.put("var", new Symbol(CircuitSym.VAR));
    stringTable.put("return", new Symbol(CircuitSym.RETURN));
%init}

%state STRING_CONSTANT

%state COMMENTS

Delimitator = \s|\t|\n|\r

IntegerConst = (0|[1-9][0-9]*)

RealConst = {IntegerConst}(\.[0-9]+)?(E[+-]?[0-9]+)?

Identifier = [$_A-Za-z][$_@A-Za-z0-9]*

Comment = ("/""/")(.*)

%%

<YYINITIAL> {

{Delimitator} { /* ignore */ }

{Identifier} { Symbol tmp = installID(yytext()); return new Symbol(tmp.sym, tmp.value);}

{Comment} { /* ignore */ }

{IntegerConst} { return new Symbol(CircuitSym.INTEGER_CONST, yyline, yycolumn, yytext());}

{RealConst} { return new Symbol(CircuitSym.REAL_CONST, yyline, yycolumn, yytext());}

\'  { stringBuffer.setLength(0); yybegin(STRING_CONSTANT); }

"#*" { stringBuffer.setLength(0); yybegin(COMMENTS); }

"(" { return new Symbol(CircuitSym.LPAR, yyline, yycolumn, "LPAR"); }

")" { return new Symbol(CircuitSym.RPAR, yyline, yycolumn, "RPAR"); }

":" { return new Symbol(CircuitSym.COLON, yyline, yycolumn, "COLON"); }

":=" { return new Symbol(CircuitSym.ASSIGN, yyline, yycolumn, "ASSIGN"); }

"+" { return new Symbol(CircuitSym.PLUS, yyline, yycolumn, "PLUS"); }

"-" { return new Symbol(CircuitSym.MINUS, yyline, yycolumn, "MINUS"); }

"*" { return new Symbol(CircuitSym.TIMES, yyline, yycolumn, "TIMES"); }

"/" { return new Symbol(CircuitSym.DIV, yyline, yycolumn, "DIV"); }

"^" { return new Symbol(CircuitSym.POW, yyline, yycolumn, "POW"); }

"&" { return new Symbol(CircuitSym.STR_CONCAT, yyline, yycolumn, "STR_CONCAT"); }

"=" { return new Symbol(CircuitSym.EQ, yyline, yycolumn, "EQ"); }

"<>" { return new Symbol(CircuitSym.NE, yyline, yycolumn, "NE"); }

"!=" { return new Symbol(CircuitSym.NE, yyline, yycolumn, "NE"); }

"<" { return new Symbol(CircuitSym.LT, yyline, yycolumn, "LT"); }

"<=" { return new Symbol(CircuitSym.LE, yyline, yycolumn, "LE"); }

">" { return new Symbol(CircuitSym.GT, yyline, yycolumn, "GT"); }

">=" { return new Symbol(CircuitSym.GE, yyline, yycolumn, "GE"); }

"," { return new Symbol(CircuitSym.COMMA, yyline, yycolumn, "COMMA"); }

";" { return new Symbol(CircuitSym.SEMI, yyline, yycolumn, "SEMI"); }

"%" { return new Symbol(CircuitSym.READ, "READ"); }

"?" { return new Symbol(CircuitSym.WRITE, "WRITE"); }

"?." { return new Symbol(CircuitSym.WRITELN, "WRITELN"); }

"?," { return new Symbol(CircuitSym.WRITEB, "WRITEB"); }

"?:" { return new Symbol(CircuitSym.WRITET, "WRITET"); }

"@" { return new Symbol(CircuitSym.OUTPAR,"OUTPAR"); }

[^] { return new Symbol(CircuitSym.error, yyline, yycolumn, yytext()); }

<<EOF>> {return new Symbol(CircuitSym.EOF);}
}

<COMMENTS> {

    "#"  {yybegin(YYINITIAL);}

    [^\*\#]+  {stringBuffer.append(yytext()); }

    "*"  {stringBuffer.append(yytext()); }

    <<EOF>> {return new Symbol(CircuitSym.error, "COMMENTO NON CHIUSO: "+ stringBuffer.toString()); }
}

<STRING_CONSTANT> {
    \'  {yybegin(YYINITIAL); return new Symbol(CircuitSym.STRING_CONST, stringBuffer.toString()); }

    [^\t\n\r\'\\]+    { stringBuffer.append(yytext()); }

    \\t { stringBuffer.append("\t"); }

    \\n { stringBuffer.append("\n"); }

    \\r { stringBuffer.append("\r"); }

    \\\' { stringBuffer.append("\'"); }

    \\  { stringBuffer.append("\\"); }

    \n  { /* ignore */ }

    \r  { /* ignore */ }

    \t { stringBuffer.append("\t"); }

    <<EOF>> {return new Symbol(CircuitSym.error, "STRINGA COSTANTE NON COMPLETATA: "+ stringBuffer.toString()); }
}

<<EOF>> {return new Symbol(CircuitSym.error);}


