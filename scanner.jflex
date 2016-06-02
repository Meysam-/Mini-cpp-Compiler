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
NUM = [0-9]+ ("." [0-9]+)?
NL = \n | \r | \r\n
ID = [:jletter:] [:jletterdigit:]*
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
{NUM} { yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
return Parser.NUM; }
{ID} { yyparser.yylval = new ParserVal(yytext());
return Parser.ID; }