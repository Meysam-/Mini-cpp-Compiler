%{
import java.io.*;
%}

%token EXTERN PROCEDURE INT BOOL FLOAT LONG CHAR DOUBLE STRING VOID AUTO RECORD CONST 
%token RETURN BREAK CONTINUE SIZEOF IF ELSE SWITCH OF DEFAULT CASE FOR FOREACH REPEAT 
%token UNTIL GOTO EQ NE LE GE AND OR DEC INC IN
%token NL /* newline */
%token <ival> IC
%token <dval> RC
%token <ival> HC
%token <sval> ID
%token <sval> CC
%token <sval> SC
%token <obj> BC

%right '='
%left OR
%left AND
%left '|'
%left '^'
%left '&'
%left EQ NE
%left '<' LE '>' BE
%left '+' '-'
%left '*' '/' '%'
%nonassoc '!' '~' INC DEC

%start program

%%  




program:	// empty
	|	program var_dcl sc
	|	program func_proc
	|	program struct_dec
	;

sc:	';' ;

func_proc:	func_dcl
		 |  extern_dcl 
		 |  proc_dcl
		 ;	

func_dcl:	typee ID '(' opt_arguments ')' sc 
		|	typee ID '(' opt_arguments ')' block
		;

extern_dcl:	EXTERN typee ID sc ;
struct_dec:	RECORD ID '{' var_dcls '}' sc ;
proc_dcl:	PROCEDURE ID '(' opt_arguments ')' sc
		|	PROCEDURE ID '(' opt_arguments ')' block
		;


opt_var_dcls:	/* empty */ 
	   	    | var_dcls
	   	    ;

var_dcl:	typee var_dcl_cnts 
	   |	CONST typee var_dcl_cnts 
	   ;

// vat_dcl_cnt,* vat_dcl_cnt
var_dcl_cnts:	 var_dcl_cnt 
			| 	 var_dcl_cnts ',' var_dcl_cnt 
			;

var_dcl_cnt:	variable 
		   | 	variable '=' expr 
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