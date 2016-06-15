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
%left '<' LE '>' GE
%left '+' '-'
%left '*' '/' '%'
%nonassoc '!' '~' INC DEC

%nonassoc ttt
%nonassoc fuck

%start program

%%  
//    start: stm start
//    |
//    
//    stm:exp NL{}
//    |assign NL{}
//    assign : ID '=' exp {cg.assign($1,$3);}
//    exp: IC { $$ = String.valueOf($1); }
//    | ID {$$ = $1;}
//    |exp '+' exp{$$ = cg.arithmeticOperand("+",$1,$3);}
//    |exp '-' exp{$$ = cg.arithmeticOperand("-",$1,$3);}
//    |exp '*' exp{$$ = cg.arithmeticOperand("*",$1,$3);}
//    |exp '/' exp{$$ = cg.arithmeticOperand("/",$1,$3);}
//    |'('exp')'{$$ = $2;}
//    ;



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

// [arguments]
opt_arguments:	/* empty */ 
			 |  arguments 
			 ;

arguments:	typee ID opt_brackets 
		|	arguments ',' typee ID opt_brackets 
		;

opt_brackets: /* empty */
				|	opt_brackets '[' ']'
				;


proc_dcl:	PROCEDURE ID '(' opt_arguments ')' sc
		|	PROCEDURE ID '(' opt_arguments ')' block
		;

typee:	INT 
  | 	BOOL 
  | 	FLOAT 
  | 	LONG 
  | 	CHAR 
  | 	DOUBLE 
  | 	ID       /* pre-defined type */
  | 	STRING 
  | 	VOID 	
  | 	AUTO 
  ; 

struct_dec:	RECORD ID '{' var_dcls '}' sc ;

// var_dcl+
var_dcls:	var_dcl sc 
		| var_dcls var_dcl sc 
		;


/* for FOR loop */
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


//  [expr]*
opt_full_brackets: /* empty */ 
				|	opt_full_brackets '[' expr ']' 
				;

block:	'{' block_content '}' 
	 ;

// {var_dcl|statement}*
block_content:	// empty
			 |	block_content var_dcl sc
			 |	block_content statement
			 ;

statement:	assignment sc
		|	method_call sc
		|	cond_stmt
		|	loop_stmt
		|	RETURN sc
		|	RETURN expr sc
		|	goto sc
		|	label
		|	BREAK sc
		|	CONTINUE sc
		|   SIZEOF '(' typee ')' sc
		;

assignment:     variable '=' expr
		  ;

method_call:	ID '(' opt_parameters ')' 
		   ;

opt_parameters:	/* empty */ 
			  | parameters  
			  ;	

parameters:	expr
		|	parameters ',' expr
		;

cond_stmt:	IF '(' expr ')' block
		|	IF '(' expr ')' block ELSE block
		|	SWITCH '(' ID ')' OF ':' '{' opt_cases DEFAULT ':' block '}'
		;

// [case cINT ':' block]*
opt_cases: /* empty */ 
		 | opt_cases CASE int_const ':' block 
		 ;

loop_stmt:	FOR '(' opt_var_dcls sc expr sc assignment ')' block
		|	FOR '(' opt_var_dcls sc expr sc expr ')' block
		|	REPEAT block UNTIL '(' expr ')' sc
		|	FOREACH '(' ID IN ID ')' block
		;

goto:	GOTO ID 
	;

label:	ID ':' 
	 ;

expr:	expr '+' expr
	|   expr '-' expr
	|   expr '*' expr
	|   expr '/' expr
	|   expr '%' expr
	|   expr '|' expr
	|   expr '&' expr
	|   expr '^' expr
	|   expr '>' expr
	|   expr '<' expr
	|   expr AND expr
	|   expr OR expr
	|   expr EQ expr
	|   expr NE expr
	|   expr GE expr
	|   expr LE expr
	|	'(' expr ')'
	|	method_call
	|	variable	
	|	const_val
	|	'-' expr 
	|	'!' expr
	|	SIZEOF '(' typee ')'
	;

variable:	ID opt_full_brackets %prec fuck
		|	ID opt_full_brackets '.' variable %prec fuck
		|   '~' variable
		|   DEC variable
		|   INC variable
		|   variable DEC %prec ttt
		|   variable INC %prec ttt
		;

const_val:	IC
		|	RC
		|	CC
		|	BC
		|	SC
		;

int_const:  IC 
		 |  CC 
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