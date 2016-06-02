%{
import java.io.*;
%}
%token NL /* newline */
%token <dval> NUM /* a number */
%token <sval> ID
%type <sval> exp
%left '-' '+'
%left '*' '/'
%start start


%%
start: stm start
|

stm:exp NL{}
|assign NL{}
assign : ID '=' exp {cg.assign($1,$3);}
exp: NUM { $$ = String.valueOf($1); }
| ID {$$ = $1;}
|exp '+' exp{$$ = cg.arithmeticOperand("+",$1,$3);}
|exp '-' exp{$$ = cg.arithmeticOperand("-",$1,$3);}
|exp '*' exp{$$ = cg.arithmeticOperand("*",$1,$3);}
|exp '/' exp{$$ = cg.arithmeticOperand("/",$1,$3);}
|'('exp')'{$$=$2;}
;
%%




/* a reference to the lexer object */
private Yylex lexer;
CG cg;
/* interface to the lexer */
private int yylex () 
{
	int yyl_return = -1;
	try
	{
		yyl_return = lexer.yylex();
	}
	catch (IOException e) 
	{
		System.err.println("IO error :"+e);
	}
	return yyl_return;
}
/* error reporting */
public void yyerror (String error) 
{
	System.err.println ("Error: " + error);
}
/* lexer is created in the constructor */
public Parser(Reader r) 
{
    cg= new CG();
	lexer = new Yylex(r, this);
}
/* that's how you use the parser */
public static void main(String args[]) throws IOException 
{
	//Parser yyparser = new Parser(new FileReader(args[0]));
 	Parser yyparser = new Parser(new InputStreamReader(System.in));
	yyparser.yyparse();
}