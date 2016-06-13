%%
%byaccj
%{
/* store a reference to the parser object */
private Parser yyparser;
/* constructor taking an additional parser object */
public Yylex(java.io.Reader r, Parser yyparser) {
this(r);
this.yyparser = yyparser;
}
%}

IC = [+-]? [0-9]+
RC = [+-]? (([0-9]*"."[0-9]+) | ([0-9]+"."[0-9]*)) ([eE][+-]? [0-9]+)?
HC = [-+]? "0x" [0-9a-fA-F]+
CC = "'" ([^\\]  | "\\" [rnt']) "'"
NL = \n | \r | \r\n
ID = [a-zA-Z_] [0-9a-zA-Z_]*
CM = ("##"[^\r\n]* {NL}) | ("/#" ([^#]|[\r\n]|("#"+([^#/]|[\r\n])))* "#"+ "/") 
WS = [ \n\t\r]+

%%
/* operators */
"+" |
"-" |
"(" |
"=" |
"*" |
"/" |
")" { return (int) yycharat(0); }
/* newline */
{NL} { return Parser.NL; }
/* float */
{IC} { yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
return Parser.IC; }
{ID} { yyparser.yylval = new ParserVal(yytext());
return Parser.ID; }

"bool" {}
"break" {}
"case" {}
"char" {}
"const" {}
"continue" {}
"default" {}
"double" {}
"else" {}
"extern" {}
"false" {}
"function" {}
"float" {}
"for" {}
"foreach" {}
"goto" {}
"if" {}
"input" {}
"int" {}
"in" {}
"long" {}
"output" {}
"of" {}
"return" {}
"record" {}
"repeat" {}
"sizeof" {}
"static" {}
"string" {}
"switch" {}
"true" {}
"until" {}