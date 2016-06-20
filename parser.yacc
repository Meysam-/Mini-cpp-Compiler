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
%token <sval> BC

%type <sval> expr typee opt_full_brackets parameters opt_parameters
%type <sval> variable const_val method_call int_const

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

sc:	';' {}
  ;

func_proc:	func_dcl
		 |  extern_dcl 
		 |  proc_dcl
		 ;	


func_dcl:	typee ID { cg.functionDeclaration($2,$1); } '(' opt_arguments ')' func_end { cg.endFunction($2); }
		;

func_end:   sc
		|	block
		;

extern_dcl:	EXTERN typee ID sc ;

// [arguments]
opt_arguments:	/* empty */ 
			 |  arguments 
			 ;

arguments:	typee ID opt_full_brackets { cg.argument($1,$2,$3); }
		|	arguments ',' typee ID opt_full_brackets { cg.argument($3,$4,$5); }
		;

opt_brackets: /* empty */
				|	opt_brackets '[' ']'
				;


proc_dcl:	PROCEDURE ID '(' opt_arguments ')' sc
		|	PROCEDURE ID '(' opt_arguments ')' block
		;

typee:	INT {$$ = "4";}
  | 	BOOL {$$ = "1";}
  | 	FLOAT{$$ = "4";} 
  | 	LONG {$$ = "8";}
  | 	CHAR {$$ = "1";}
  | 	DOUBLE {$$ = "8";}
  | 	ID       /* pre-defined type */
  | 	STRING {$$= "STR";}
  | 	VOID {$$ = "0";}
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

var_dcl_cnt:	variable{ cg.declarationNoType($1,""); } 
		   | 	variable '=' expr {cg.declarationNoType($1,$3);}
		   ;


opt_full_brackets: /* empty */ {$$ = "0";}
				|	opt_full_brackets '[' expr ']' {$$ = $1 + "," + $3.split(",")[0];}
				;

block:	'{' block_content '}' 
	 ;

block_content:	// empty
			 |	 var_dcl sc block_content
			 |	 statement  block_content
			 |   block      block_content
			 ;

statement:	assignment sc
		|	method_call sc
		|	cond_stmt
		|	loop_stmt
		|	RETURN sc { cg.retStm(""); }
		|	RETURN expr sc { cg.retStm($2); }
		|	goto sc
		|	label
		|	BREAK sc
		|	CONTINUE sc
		|   SIZEOF '(' typee ')' sc
		;

assignment:     variable '=' expr{cg.assign($1,$3);}
		  |		variable_
		  ;

method_call:	ID '(' opt_parameters ')' { $$ = cg.funcCall($1,$3); }
		   ;

opt_parameters:	/* empty */ {$$ = "";}
			  | parameters  {$$ = $1;}
			  ;	

parameters:	expr {$$ = cg.arr_calc_addr($1);}
		|	parameters ',' expr {$$ = $1 + "," + cg.arr_calc_addr($3);}
		;

cond_stmt:  IF '(' expr ')' {cg.ifState($3);} block end_if
    	 |  SWITCH '(' ID ')' OF ':' {cg.switch_start($3);} '{' opt_cases DEFAULT ':' {cg.switch_default();} block '}'{cg.switch_end();}
    	 ;

end_if:     {cg.endIfState();}
      |		ELSE{ cg.startElse(); } block {cg.endElse();}
      ;

// [case cINT ':' block]*
opt_cases: /* empty */ 
		 | opt_cases CASE int_const ':' {cg.switch_case($3);} block {cg.switch_case_end();} 
		 ;

loop_stmt:  FOR '(' opt_var_dcls {cg.for_start();} sc expr {cg.saveInTemp=true;} sc assignment ')' {cg.for_middle($6);} block {cg.for_end();}
    |  REPEAT {cg.repeat_start();} block UNTIL '(' expr ')' sc  {cg.repeat_end($6);}
    |  FOREACH '(' ID IN ID ')' {cg.foreach_start($3,$5);} block {cg.foreach_end($3,$5);}
    ;

goto:	GOTO ID { cg.jumpToLabel($2); }
	;

label:	ID ':' { cg.setLabel($1); }
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
	|	method_call {$$ = $1;}
	|	variable {$$=$1;}
	|	const_val
	|	'-' expr %prec '!' {$$ = cg.arithmeticOperand("*",$2,"#-1");}
	|	'+' expr %prec '!' {$$ = $2;}
	|	'!' expr
	|	SIZEOF '(' typee ')'
	;

variable:	ID opt_full_brackets %prec fuck {$$ = $1+",#"+$2;}
		|	ID opt_full_brackets '.' variable %prec fuck
		;

variable_:   '~' variable 
		 |   DEC variable {cg.arithmeticOperand("-",$2,$2,"1");}
		 |   INC variable {cg.arithmeticOperand("+",$2,$2,"1");}
		 |   variable DEC %prec ttt {cg.arithmeticOperand("-",$1,$1,"1");}
		 |   variable INC %prec ttt {cg.arithmeticOperand("+",$1,$1,"1");}
		 ;

const_val:	IC {$$ = "#" + (new Integer($1).toString());}
		|	RC 
		|	CC {$$ = "#" + (new Integer((int)$1.charAt(0)).toString());}
		|	BC {$$ = "#" + $1;}
		|	SC {$$ = $1;}
		;

int_const:  IC {$$=new Integer($1).toString();}
		 |  CC {$$=$1;}
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
	//yydebug = true;
}
/* that's how you use the parser */
public static void main(String args[]) throws IOException 
{
	//Parser yyparser = new Parser(new FileReader(args[0]));
 	Parser yyparser = new Parser(new InputStreamReader(System.in));
	yyparser.yyparse();
}