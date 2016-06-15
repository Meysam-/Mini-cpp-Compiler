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
RC = [+-]?(([0-9]*"."[0-9]+)|([0-9]+"."[0-9]*))([eE][+-]?[0-9]+)?
HC = [-+]? "0x" [0-9a-fA-F]+
BC = "true"|"false"
CC = "'" ([^\\]  | "\\" [rnt']) "'"
SC = \"(([^\\\"])|(\\[rnt\\\"]))*\"
NL = \n | \r | \r\n
ID = [a-zA-Z_] [0-9a-zA-Z_]*
CM = ("##"[^\r\n]* {NL}) | ("/#" ([^#]|[\r\n]|("#"+([^#/]|[\r\n])))* "#"+ "/") 
WS = [ \n\t\r]+


%%
/* operators */
"==" { return Parser.EQ; }
"!=" { return Parser.NE; }
"<=" { return Parser.LE; }
">=" { return Parser.GE; }
"&&" { return Parser.AND; }
"||" { return Parser.OR; }
"++" { return Parser.INC; }
"--" { return Parser.DEC; }
"<" |
">" |
"=" |
"!" |
"~" |
"&" |
"|" |
"^" |
"*" |
"+" |
"-" |
"/" |
"%" |
"{" |
"}" |
"(" |
")" |
"[" |
"]" |
"." |
"," |
":" |
";" { System.out.println(yytext()); }


{RC} { yyparser.yylval = new ParserVal(Double.ParseDouble(yytext()));
	return Parser.RC; }

{IC} { yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
	return Parser.IC; }

{HC} { yyparser.yylval = new ParserVal((yycharat(0) == '-' ? -1 : 1) * Integer.parseInt((yytext().substring(2 + ( yytext().charAt(0) == '0' ? 0 : 1))), 16));	// hex digits at 3rd or 4th char
	return Parser.IC; }

{BC} {yyparser.yylval = new ParserVal(Boolean.parseBoolean(yytext()));
	return Parser.BC;}

{CC} { if (yycharat(1) == '\\') {
			switch(yycharat(2)) {
				case 'r' :
					yyparser.yylval = new ParserVal('\r');
					break;
				case 'n' :
					yyparser.yylval = new ParserVal('\n');
					break;
				case 't' :
					yyparser.yylval = new ParserVal('\t');
					break;
				case '\\' :
					yyparser.yylval = new ParserVal('\\');
					break;
				case '\"' :
					yyparser.yylval = new ParserVal('\"');
					break;
				default :
					// TODO: emit error
					yyparser.yylval = new ParserVal('\'');	// char error mark
					break;
			}
		} else {
			yyparser.yylval = new ParserVal(yycharat(1));
		}
		return Parser.CC;
	}

{NL} { return Parser.NL; }

{ID} { yyparser.yylval = new ParserVal(yytext());
	return Parser.ID; }

{SC} { yyparser.yylval = new ParserVal(yytext());
	return Parser.SC; }

{CM} {}
{WS} {}


"bool" { return Parser.BOOL; }
"break" {return Parser.BREAK; }
"case" {return Parser.CASE; }
"char" {return Parser.CHAR; }
"const" {return Parser.CONST; }
"continue" {return Parser.CONTINUE; }
"default" {return Parser.DEFAULT; }
"double" {return Parser.DOUBLE; }
"else" {return Parser.ELSE; }
"extern" {return Parser.EXTERN; }
"false" {return Parser.FALSE; }
"function" {return Parser.FUNCTION; }
"float" {return Parser.FLOAT; }
"for" {return Parser.FOR; }
"foreach" {return Parser.FOREACH; }
"goto" {return Parser.GOTO; }
"if" {return Parser.IF; }
"input" {return Parser.INPUT; }
"int" {return Parser.INT; }
"in" {return Parser.IN; }
"long" {return Parser.LONG; }
"output" {return Parser.OUTPUT; }
"of" {return Parser.OF; }
"return" {return Parser.RETUEN; }
"record" {return Parser.RECORD; }
"repeat" {return Parser.REPEAT; }
"sizeof" {return Parser.SIZEOF; }
"static" {return Parser.STATIC; }
"string" {return Parser.STRING; }
"switch" {return Parser.SWITCH; }
"true" {return Parser.TRUE; }
"until" {return Parser.UNTIL; }

[^] { System.err.println("Lexical error! " + yytext()); }