%{
import java.io.*;
%}

%token EXTERN PROCEDURE FUNCTION INT BOOL FLOAT LONG CHAR DOUBLE STRING VOID AUTO RECORD CONST 
%token RETURN BREAK CONTINUE SIZEOF IF ELSE SWITCH OF DEFAULT CASE FOR FOREACH REPEAT 
%token UNTIL INPUT OUTPUT STATIC GOTO EQ NE LE GE AND OR DEC INC IN
%token NL /* newline */
%token <ival> IC
%token <dval> RC
%token <ival> HC
%token <sval> ID
%token <sval> CC
%token <sval> SC
%token <obj> BC

%type <sval> expr typee
%type <sval> variable const_val

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
	|	var_dcl sc program
	|	func_proc  program
	|	struct_dec program
	;

sc:	';' {System.out.println("semi solon detected!");}
  ;

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

arguments:	typee ID opt_full_brackets 
		|	arguments ',' typee ID opt_full_brackets 
		;

opt_brackets: /* empty */
				|	opt_brackets '[' ']'
				;


proc_dcl:	PROCEDURE ID '(' opt_arguments ')' sc
		|	PROCEDURE ID '(' opt_arguments ')' block
		;

typee:	INT {$$ = "4";}
  | 	BOOL {$$ = "1";}
  | 	FLOAT{$$ = "32";} 
  | 	LONG {$$ = "32";}
  | 	CHAR {$$ = "4";}
  | 	DOUBLE {$$ = "32";}
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
	   	    | var_dcl
	   	    ;

var_dcl:	typee var_dcl_cnts{cg.declarationSetType($1);}
	   |	CONST typee var_dcl_cnts 
	   ;

// vat_dcl_cnt,* vat_dcl_cnt
var_dcl_cnts:	 var_dcl_cnt 
			| 	 var_dcl_cnts ',' var_dcl_cnt 
			;

var_dcl_cnt:	variable{ cg.declarationNoType($1); } 
		   | 	variable '=' expr {cg.declarationNoType($1);cg.assign($1,$3);}
		   ;


opt_full_brackets: /* empty */ 
				|	opt_full_brackets '[' expr ']' 
				;

block:	'{' block_content '}' 
	 ;

block_content:	// empty
			 |	 var_dcl sc block_content
			 |	 statement  block_content
			 |   block      block_content
			 ;

statement:	assignment sc
		|	variable_ sc
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

assignment:     variable '=' expr{cg.assign($1,$3);}
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

expr:	expr '+' expr {$$ = cg.arithmeticOperand("+",$1,$3);}
	|   expr '-' expr {$$ = cg.arithmeticOperand("-",$1,$3);}
	|   expr '*' expr {$$ = cg.arithmeticOperand("*",$1,$3);}
	|   expr '/' expr {$$ = cg.arithmeticOperand("/",$1,$3);}
	|   expr '%' expr {$$ = cg.arithmeticOperand("%",$1,$3);}
	|   expr '|' expr {$$ = cg.arithmeticOperand("|",$1,$3);}
	|   expr '&' expr {$$ = cg.arithmeticOperand("&",$1,$3);}
	|   expr '^' expr {$$ = cg.arithmeticOperand("^",$1,$3);}
	|   expr '>' expr {$$ = cg.arithmeticOperand(">",$1,$3);}
	|   expr '<' expr {$$ = cg.arithmeticOperand("<",$1,$3);}
	|   expr AND expr {$$ = cg.arithmeticOperand("&&",$1,$3);}
	|   expr OR expr {$$ = cg.arithmeticOperand("||",$1,$3);}
	|   expr EQ expr {$$ = cg.arithmeticOperand("==",$1,$3);}
	|   expr NE expr {$$ = cg.arithmeticOperand("!=",$1,$3);}
	|   expr GE expr {$$ = cg.arithmeticOperand(">=",$1,$3);}
	|   expr LE expr {$$ = cg.arithmeticOperand("<=",$1,$3);}
	|	'(' expr ')' {$$ = $2;}
	|	method_call
	|	variable 	
	|	const_val
	|	'-' expr %prec '!'
	|	'+' expr %prec '!'
	|	'!' expr
	|	SIZEOF '(' typee ')'
	;

variable:	ID opt_full_brackets %prec fuck {$$ = $1;}
		|	ID opt_full_brackets '.' variable %prec fuck
		|   variable_
		;

variable_:   '~' variable
		 |   DEC variable
		 |   INC variable
		 |   variable DEC %prec ttt
		 |   variable INC %prec ttt
		 ;

const_val:	IC {$$ = new Integer($1).toString();}
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