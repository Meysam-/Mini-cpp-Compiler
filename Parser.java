//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Parser.yacc"
import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short EXTERN=257;
public final static short PROCEDURE=258;
public final static short INT=259;
public final static short BOOL=260;
public final static short FLOAT=261;
public final static short LONG=262;
public final static short CHAR=263;
public final static short DOUBLE=264;
public final static short STRING=265;
public final static short VOID=266;
public final static short AUTO=267;
public final static short RECORD=268;
public final static short CONST=269;
public final static short RETURN=270;
public final static short BREAK=271;
public final static short CONTINUE=272;
public final static short SIZEOF=273;
public final static short IF=274;
public final static short ELSE=275;
public final static short SWITCH=276;
public final static short OF=277;
public final static short DEFAULT=278;
public final static short CASE=279;
public final static short FOR=280;
public final static short FOREACH=281;
public final static short REPEAT=282;
public final static short UNTIL=283;
public final static short GOTO=284;
public final static short EQ=285;
public final static short NE=286;
public final static short LE=287;
public final static short GE=288;
public final static short AND=289;
public final static short OR=290;
public final static short DEC=291;
public final static short INC=292;
public final static short IN=293;
public final static short NL=294;
public final static short IC=295;
public final static short RC=296;
public final static short HC=297;
public final static short ID=298;
public final static short CC=299;
public final static short SC=300;
public final static short BC=301;
public final static short ttt=302;
public final static short fuck=303;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    2,    3,    3,    3,    5,    5,
    6,    9,    9,   11,   11,   12,   12,    7,    7,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    4,
   13,   13,   14,   14,    1,    1,   15,   15,   16,   16,
   19,   19,   10,   20,   20,   20,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   22,   23,   28,
   28,   29,   29,   24,   24,   24,   30,   30,   25,   25,
   25,   25,   26,   27,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   17,   17,   17,
   17,   17,   17,   17,   32,   32,   32,   32,   32,   31,
   31,
};
final static short yylen[] = {                            2,
    0,    3,    2,    2,    1,    1,    1,    1,    6,    6,
    4,    0,    1,    3,    5,    0,    3,    6,    6,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    6,
    2,    3,    0,    1,    2,    3,    1,    3,    1,    3,
    0,    4,    3,    0,    3,    2,    2,    2,    1,    1,
    2,    3,    2,    1,    2,    2,    5,    3,    4,    0,
    1,    1,    3,    5,    7,   12,    0,    5,    9,    9,
    7,    7,    2,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    1,    1,    1,    2,    2,    4,    2,    4,    2,
    2,    2,    2,    2,    1,    1,    1,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,   20,   21,   22,   23,   24,   25,   27,
   28,   29,    0,    0,   26,    0,    3,    4,    6,    7,
    8,    0,    0,    0,    0,    0,    5,    2,    0,    0,
    0,    0,    0,   37,    0,    0,    0,    0,   41,    0,
  101,  102,    0,    0,  100,    0,  103,  104,    0,   11,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   38,
    0,  105,  106,    0,  107,  109,  108,    0,    0,    0,
    0,    0,   92,   94,   16,    0,    0,   31,    0,    0,
    0,    0,   99,    0,    0,    0,   96,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   44,   18,   19,    0,   30,
   32,    9,   10,   42,    0,    0,    0,    0,   91,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   77,   78,   79,    0,    0,   16,   97,   59,
    0,   17,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   43,    0,    0,   46,    0,    0,   49,
   50,    0,   54,    0,    0,   51,    0,   55,   56,    0,
    0,    0,    0,    0,    0,   73,   74,   45,    0,   47,
   48,   53,   52,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   65,   67,
    0,    0,    0,   72,   71,    0,    0,    0,    0,    0,
   70,   69,    0,  110,  111,    0,    0,    0,   66,   68,
};
final static short yydgoto[] = {                          1,
   54,   28,   17,   18,   19,   20,   21,   55,   52,  108,
   53,  105,   56,  188,   33,   34,   71,   72,   44,  137,
  157,  158,   73,  160,  161,  162,  163,  117,  118,  216,
  226,   74,
};
final static short yysindex[] = {                         0,
  831,  -50, -293,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -288,  -50,    0,  -30,    0,    0,    0,    0,
    0, -101, -271,   12,  -88,  -92,    0,    0,  -92,  -92,
   22,  -92,   34,    0,  -58,  -30,  -50,  137,    0,   34,
    0,    0,  -50,  -32,    0,  -92,    0,    0,  -22,    0,
 -216,   46,   40,  -30,  -92,  597,   65,  -22,  -92,    0,
   61,    0,    0,   70,    0,    0,    0,  -22,  -22,  -22,
 -220,  391,    0,    0,    0,  -40,  -50,    0,  -30,  -30,
  -40,   60,    0,  -50,  -22,   43,    0,   71,  -22,  -22,
  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,
  -22,  -22,  -22,  -22,   24,    0,    0,    0, -187,    0,
    0,    0,    0,    0,   76,  391,   80,   79,    0,  682,
  682,  181,  181,  566,  452,  194,  606,  590,  181,  181,
   43,   43,    0,    0,    0,   35,  644,    0,    0,    0,
  -22,    0,  -33,  -30,  -30,   99,  106,  107,  108,  109,
   15, -148,  -38,    0,  -30,  -48,    0,  -30,  -30,    0,
    0,  -30,    0,   24,  391,    0,   98,    0,    0,  -50,
  -22, -147,  137, -146, -128,    0,    0,    0,  -22,    0,
    0,    0,    0,  118,  125,  120,  137,  -30, -129,  129,
  391,  -30,   15, -106,  -22, -123,  -22,    0,  -95,  124,
   98,  145,  136,   15,   66,  -22,   15,  -30,    0,    0,
  -48,  268,  147,    0,    0, -183,   15,   15,  135, -278,
    0,    0,   15,    0,    0,  143,   77,   15,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  490,    0,  144,    0,  -29,    0,  163,    0,    0,  146,
    0,    0,  163,    6,    0,    0,    0,    0,    0,    0,
    0,    0,  167,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -5,    0,    0,    0,    0,    0,    0,
   32,    1,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  179,  399,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -13,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   20,    0,  180,    0,  486,
  546,  469,  478,  553,  -35,   83,  231,  548,  506,  523,
  411,  424,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -45,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   88,   93,    0,    0,    0,    0,    0,
    0,    0,  166,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  168,    0,    0,    0,
  -37,    0,    0,    0,    0,    0,    0,    0,  691,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  333,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    7,  865,    0,    0,    0,    0,    0,  978,  186,  622,
    0,   97,   69,    0,  212,  199,  775,  944,    0,    0,
    0,   44,  115,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=1150;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         69,
   41,   85,   49,   58,   24,   86,   70,   16,   86,   25,
   69,   68,  179,   59,   39,   41,  224,   70,   27,  177,
  225,   58,   68,   86,   32,   27,   36,   14,   27,   39,
   14,   41,   41,   32,   38,   41,   41,   41,   41,   41,
   41,   41,   98,   98,   40,   41,   98,   98,   98,   98,
   98,   37,   98,   41,   41,   41,   41,   86,   58,   40,
   62,   43,   80,   62,   98,   98,   98,   98,   93,   93,
   47,   48,   93,   93,   93,   93,   93,   46,   93,  104,
   26,   75,  106,   77,  102,   41,   76,   41,   41,  103,
   93,   93,   32,   93,  219,  220,  104,   97,   98,   98,
   84,  102,  100,   32,  101,   81,  103,  104,   97,   85,
  138,  119,  102,  100,  136,  101,  139,  103,   41,   98,
  140,   99,  141,   80,   93,   93,   80,  142,   15,   98,
   98,   15,   99,   63,  104,   97,   63,  106,  170,  102,
  100,   80,  101,  155,  103,  171,  172,  173,  174,  176,
  186,  189,  114,   96,  190,   93,   27,   98,  192,   99,
  194,  104,   97,  196,   96,  193,  102,  100,  197,  101,
  200,  103,  104,   97,  202,   80,  208,  102,  100,  204,
  101,  205,  103,   95,   98,  207,   99,  218,  210,   29,
   30,   96,  223,   80,   95,   98,   31,   99,   29,   30,
  228,  229,   35,   12,   36,   39,   80,   13,    4,    5,
    6,    7,    8,    9,   10,   11,   12,  104,   96,   60,
   61,   95,  102,  100,   33,  101,   34,  103,   57,   96,
  104,   97,   47,   48,  164,  102,  100,   40,  101,   61,
  103,  187,   47,   48,   60,   26,   26,   15,   95,  213,
   61,  159,   26,   98,   86,   99,    0,   29,   30,   95,
    0,   62,   63,    0,   64,   65,   66,   67,   29,   30,
    0,   82,   62,   63,   82,   64,   65,   66,   67,   41,
   41,   41,   41,   41,   41,   41,   41,   96,    0,   82,
   98,   98,   98,   98,   98,   98,   98,   98,    0,    0,
    0,    0,    0,    0,  104,   97,    0,    0,  217,  102,
  100,    0,  101,    0,  103,    0,   93,   93,   93,   93,
   93,   93,    0,   82,   82,    0,    0,   98,    0,   99,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   89,   90,   91,   92,   93,   94,
    0,    0,    0,    0,   82,   89,   90,   91,   92,   93,
   94,   96,    0,    0,    0,    0,    0,    0,    0,   93,
   93,   80,   80,   93,   93,   93,    0,   93,    0,   93,
    0,    0,   89,   90,   91,   92,   93,   94,    0,    0,
    0,   95,   93,    0,   93,    4,    5,    6,    7,    8,
    9,   10,   11,   12,    0,   14,    0,    0,    0,   89,
   90,   91,   92,   93,   94,    0,    0,    0,    0,    0,
   89,   90,   91,   92,   93,   94,   93,  104,   97,    0,
    0,    0,  102,  100,   15,  101,   95,  103,    0,   95,
    0,   95,   95,   95,    0,    0,    0,    0,   75,    0,
   98,   75,   99,   75,   75,   75,   93,   95,   95,    0,
   95,   76,    0,    0,   76,    0,   76,   76,   76,   75,
   75,    0,   75,    0,    0,    0,    0,    0,   89,   90,
   91,   92,   76,   76,   96,   76,    0,    0,  104,   97,
    0,   95,   95,  102,  100,    0,  101,    0,  103,    0,
    0,    0,    0,   75,   75,    0,   90,    0,    0,   90,
    0,   98,   90,   99,   95,   89,   76,   76,   89,   82,
   82,   89,   95,   87,    0,    0,   87,   90,   90,   87,
   90,    0,    0,   41,   75,   41,   89,   89,    0,   89,
    0,    0,    0,   84,   87,   96,   84,   76,   41,   84,
   41,    0,   89,   90,   91,   92,   93,   94,    0,    0,
   83,   90,   90,   83,   84,   84,   83,   84,    0,    0,
   89,   89,    0,    0,    0,   95,    0,    0,   87,   87,
   41,   83,   83,   88,   83,   81,   88,    0,   81,   88,
    0,   81,   90,   85,    0,    0,   85,    0,   84,   84,
    0,   89,  104,   97,   88,    0,   81,  102,  100,   87,
  101,   85,  103,    0,    0,   83,   83,   93,   93,   93,
   93,   93,   93,    0,    0,   98,  104,   99,    0,   84,
    0,  102,  100,    0,  101,    0,  103,    0,   88,   88,
   81,   81,  104,   97,    0,   85,   83,  102,  100,   98,
  101,   99,  103,    0,    0,    0,    0,    0,    0,   96,
    0,    0,    0,    0,    0,   98,    0,   99,    0,   88,
    0,   81,    0,    0,    0,   89,   90,   91,   92,   93,
   94,    0,    0,   95,   95,   95,   95,   95,   95,   95,
    0,    0,    0,    0,    0,   75,   75,   75,   75,   75,
   75,    0,  113,    0,    0,    0,    0,    0,   76,   76,
   76,   76,   76,   76,    0,    0,    0,    0,  104,    0,
    0,   79,    0,  102,  100,    0,  101,    0,  103,    0,
    0,    0,    0,    0,    0,    0,   89,   90,   91,   92,
   93,   98,    0,   99,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,   90,   90,   90,   90,   90,    0,
    0,    0,   89,   89,   89,   89,   89,   89,  154,   32,
   87,   87,  175,    0,   87,   87,    0,    0,    0,    0,
   41,   41,    0,    0,    0,    0,    0,    0,    0,    0,
   84,   84,   84,   84,   84,   84,   35,    0,    0,    0,
   35,    0,    0,   41,   42,    0,   45,   83,   83,   83,
   83,   83,   83,    0,  199,   64,   64,    0,    0,    0,
   35,    0,    0,    0,    0,  209,    0,    0,  214,   35,
   88,   88,    0,   83,   88,   88,   81,   81,  221,  222,
    0,   85,   85,    0,  227,    0,    0,    0,    0,  230,
   89,   90,   91,   92,    0,    4,    5,    6,    7,    8,
    9,   10,   11,   12,    0,   14,    0,    0,    0,    0,
    0,    0,    0,    0,   89,   90,   91,   92,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   89,   90,   91,   92,   15,    0,    0,    0,    0,    0,
   50,    0,    4,    5,    6,    7,    8,    9,   10,   11,
   12,  156,   14,  143,  144,  145,  146,  147,   78,  148,
    0,    0,    0,  149,  150,  151,    0,  152,    0,    0,
    0,    0,    0,    0,   29,   30,    0,    0,    0,    0,
  107,  153,    0,  110,  111,  112,    0,    0,    0,   64,
   64,   64,   64,   64,   64,   64,   64,   64,    0,   64,
   64,   64,   64,   64,   64,    0,   64,    0,   91,   92,
   64,   64,   64,    0,   64,    0,    0,    0,   22,   23,
  211,   64,   64,    0,    0,    0,    0,    0,   64,    0,
    0,   26,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   82,    0,    0,    0,    0,    0,  166,  168,  169,
    0,   86,   87,   88,   51,    0,    0,    0,    0,  178,
   51,    0,  180,  181,    0,    0,  182,    0,  116,    0,
    0,  183,  120,  121,  122,  123,  124,  125,  126,  127,
  128,  129,  130,  131,  132,  133,  134,  135,    0,    0,
    0,    0,  195,    0,  109,    0,  198,    0,    0,    0,
    0,  115,    0,    0,    0,  206,    0,    0,    0,    0,
    0,    0,  215,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  165,    0,  167,    2,    3,    4,
    5,    6,    7,    8,    9,   10,   11,   12,   13,   14,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  185,    0,    0,    0,    0,    0,
    0,    0,  191,    0,    0,    0,    0,    0,   15,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  201,    0,
  203,    0,    0,    0,    0,    0,    0,  184,    0,  212,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   46,   40,   61,   41,  298,   41,   40,    1,   44,  298,
   33,   45,   61,   46,   44,   61,  295,   40,   59,   58,
  299,   59,   45,   59,  126,   59,  298,   41,   59,   59,
   44,   37,   38,  126,  123,   41,   42,   43,   44,   45,
   46,   47,   37,   38,   44,   91,   41,   42,   43,   44,
   45,   40,   47,   59,   60,   61,   62,   93,   91,   59,
   41,   40,   56,   44,   59,   60,   61,   62,   37,   38,
  291,  292,   41,   42,   43,   44,   45,   44,   47,   37,
  126,  298,  123,   44,   42,   91,   41,   93,   94,   47,
   59,   60,  126,   62,  278,  279,   37,   38,   93,   94,
   40,   42,   43,  126,   45,   41,   47,   37,   38,   40,
  298,   41,   42,   43,   91,   45,   41,   47,  124,   60,
   41,   62,   44,   41,   93,   94,   44,   93,   41,  124,
   60,   44,   62,   41,   37,   38,   44,  123,   40,   42,
   43,   59,   45,  137,   47,   40,   40,   40,   40,  298,
  298,  298,   93,   94,  283,  124,   59,   60,   41,   62,
   41,   37,   38,  293,   94,   41,   42,   43,   40,   45,
  277,   47,   37,   38,  298,   93,   41,   42,   43,  275,
   45,   58,   47,  124,   60,   41,   62,   41,  123,  291,
  292,   94,   58,  187,  124,   60,  298,   62,  291,  292,
   58,  125,   59,   41,   59,  298,  124,   41,  259,  260,
  261,  262,  263,  264,  265,  266,  267,   37,   94,   41,
   41,  124,   42,   43,   59,   45,   59,   47,   43,   94,
   37,   38,  291,  292,  138,   42,   43,   26,   45,  273,
   47,  173,  291,  292,   46,  291,  292,  298,  124,  206,
  273,  137,  298,   60,  290,   62,   -1,  291,  292,  124,
   -1,  295,  296,   -1,  298,  299,  300,  301,  291,  292,
   -1,   41,  295,  296,   44,  298,  299,  300,  301,  285,
  286,  287,  288,  289,  290,  291,  292,   94,   -1,   59,
  285,  286,  287,  288,  289,  290,  291,  292,   -1,   -1,
   -1,   -1,   -1,   -1,   37,   38,   -1,   -1,   41,   42,
   43,   -1,   45,   -1,   47,   -1,  285,  286,  287,  288,
  289,  290,   -1,   93,   94,   -1,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  285,  286,  287,  288,  289,  290,
   -1,   -1,   -1,   -1,  124,  285,  286,  287,  288,  289,
  290,   94,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   37,
   38,  289,  290,   41,   42,   43,   -1,   45,   -1,   47,
   -1,   -1,  285,  286,  287,  288,  289,  290,   -1,   -1,
   -1,  124,   60,   -1,   62,  259,  260,  261,  262,  263,
  264,  265,  266,  267,   -1,  269,   -1,   -1,   -1,  285,
  286,  287,  288,  289,  290,   -1,   -1,   -1,   -1,   -1,
  285,  286,  287,  288,  289,  290,   94,   37,   38,   -1,
   -1,   -1,   42,   43,  298,   45,   38,   47,   -1,   41,
   -1,   43,   44,   45,   -1,   -1,   -1,   -1,   38,   -1,
   60,   41,   62,   43,   44,   45,  124,   59,   60,   -1,
   62,   38,   -1,   -1,   41,   -1,   43,   44,   45,   59,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,  285,  286,
  287,  288,   59,   60,   94,   62,   -1,   -1,   37,   38,
   -1,   93,   94,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,   -1,   -1,   93,   94,   -1,   38,   -1,   -1,   41,
   -1,   60,   44,   62,  124,   38,   93,   94,   41,  289,
  290,   44,  124,   38,   -1,   -1,   41,   59,   60,   44,
   62,   -1,   -1,   44,  124,   46,   59,   60,   -1,   62,
   -1,   -1,   -1,   38,   59,   94,   41,  124,   59,   44,
   61,   -1,  285,  286,  287,  288,  289,  290,   -1,   -1,
   38,   93,   94,   41,   59,   60,   44,   62,   -1,   -1,
   93,   94,   -1,   -1,   -1,  124,   -1,   -1,   93,   94,
   91,   59,   60,   38,   62,   38,   41,   -1,   41,   44,
   -1,   44,  124,   41,   -1,   -1,   44,   -1,   93,   94,
   -1,  124,   37,   38,   59,   -1,   59,   42,   43,  124,
   45,   59,   47,   -1,   -1,   93,   94,  285,  286,  287,
  288,  289,  290,   -1,   -1,   60,   37,   62,   -1,  124,
   -1,   42,   43,   -1,   45,   -1,   47,   -1,   93,   94,
   93,   94,   37,   38,   -1,   93,  124,   42,   43,   60,
   45,   62,   47,   -1,   -1,   -1,   -1,   -1,   -1,   94,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,  124,
   -1,  124,   -1,   -1,   -1,  285,  286,  287,  288,  289,
  290,   -1,   -1,  285,  286,  287,  288,  289,  290,  124,
   -1,   -1,   -1,   -1,   -1,  285,  286,  287,  288,  289,
  290,   -1,   81,   -1,   -1,   -1,   -1,   -1,  285,  286,
  287,  288,  289,  290,   -1,   -1,   -1,   -1,   37,   -1,
   -1,  125,   -1,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  285,  286,  287,  288,
  289,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  285,  286,  287,  288,  289,  290,   -1,
   -1,   -1,  285,  286,  287,  288,  289,  290,  125,  126,
  285,  286,  151,   -1,  289,  290,   -1,   -1,   -1,   -1,
  291,  292,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  285,  286,  287,  288,  289,  290,   22,   -1,   -1,   -1,
   26,   -1,   -1,   29,   30,   -1,   32,  285,  286,  287,
  288,  289,  290,   -1,  193,  125,  126,   -1,   -1,   -1,
   46,   -1,   -1,   -1,   -1,  204,   -1,   -1,  207,   55,
  285,  286,   -1,   59,  289,  290,  289,  290,  217,  218,
   -1,  289,  290,   -1,  223,   -1,   -1,   -1,   -1,  228,
  285,  286,  287,  288,   -1,  259,  260,  261,  262,  263,
  264,  265,  266,  267,   -1,  269,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  285,  286,  287,  288,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  285,  286,  287,  288,  298,   -1,   -1,   -1,   -1,   -1,
   36,   -1,  259,  260,  261,  262,  263,  264,  265,  266,
  267,  137,  269,  270,  271,  272,  273,  274,   54,  276,
   -1,   -1,   -1,  280,  281,  282,   -1,  284,   -1,   -1,
   -1,   -1,   -1,   -1,  291,  292,   -1,   -1,   -1,   -1,
   76,  298,   -1,   79,   80,   81,   -1,   -1,   -1,  259,
  260,  261,  262,  263,  264,  265,  266,  267,   -1,  269,
  270,  271,  272,  273,  274,   -1,  276,   -1,  287,  288,
  280,  281,  282,   -1,  284,   -1,   -1,   -1,    1,    2,
  206,  291,  292,   -1,   -1,   -1,   -1,   -1,  298,   -1,
   -1,   14,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   -1,   -1,   -1,   -1,   -1,  143,  144,  145,
   -1,   68,   69,   70,   37,   -1,   -1,   -1,   -1,  155,
   43,   -1,  158,  159,   -1,   -1,  162,   -1,   85,   -1,
   -1,  167,   89,   90,   91,   92,   93,   94,   95,   96,
   97,   98,   99,  100,  101,  102,  103,  104,   -1,   -1,
   -1,   -1,  188,   -1,   77,   -1,  192,   -1,   -1,   -1,
   -1,   84,   -1,   -1,   -1,  201,   -1,   -1,   -1,   -1,
   -1,   -1,  208,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  141,   -1,  143,  257,  258,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  171,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  179,   -1,   -1,   -1,   -1,   -1,  298,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  195,   -1,
  197,   -1,   -1,   -1,   -1,   -1,   -1,  170,   -1,  206,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=303;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'","'&'",null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'","'^'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'","'|'","'}'","'~'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"EXTERN","PROCEDURE","INT","BOOL",
"FLOAT","LONG","CHAR","DOUBLE","STRING","VOID","AUTO","RECORD","CONST","RETURN",
"BREAK","CONTINUE","SIZEOF","IF","ELSE","SWITCH","OF","DEFAULT","CASE","FOR",
"FOREACH","REPEAT","UNTIL","GOTO","EQ","NE","LE","GE","AND","OR","DEC","INC",
"IN","NL","IC","RC","HC","ID","CC","SC","BC","ttt","fuck",
};
final static String yyrule[] = {
"$accept : program",
"program :",
"program : program var_dcl sc",
"program : program func_proc",
"program : program struct_dec",
"sc : ';'",
"func_proc : func_dcl",
"func_proc : extern_dcl",
"func_proc : proc_dcl",
"func_dcl : typee ID '(' opt_arguments ')' sc",
"func_dcl : typee ID '(' opt_arguments ')' block",
"extern_dcl : EXTERN typee ID sc",
"opt_arguments :",
"opt_arguments : arguments",
"arguments : typee ID opt_brackets",
"arguments : arguments ',' typee ID opt_brackets",
"opt_brackets :",
"opt_brackets : opt_brackets '[' ']'",
"proc_dcl : PROCEDURE ID '(' opt_arguments ')' sc",
"proc_dcl : PROCEDURE ID '(' opt_arguments ')' block",
"typee : INT",
"typee : BOOL",
"typee : FLOAT",
"typee : LONG",
"typee : CHAR",
"typee : DOUBLE",
"typee : ID",
"typee : STRING",
"typee : VOID",
"typee : AUTO",
"struct_dec : RECORD ID '{' var_dcls '}' sc",
"var_dcls : var_dcl sc",
"var_dcls : var_dcls var_dcl sc",
"opt_var_dcls :",
"opt_var_dcls : var_dcls",
"var_dcl : typee var_dcl_cnts",
"var_dcl : CONST typee var_dcl_cnts",
"var_dcl_cnts : var_dcl_cnt",
"var_dcl_cnts : var_dcl_cnts ',' var_dcl_cnt",
"var_dcl_cnt : variable",
"var_dcl_cnt : variable '=' expr",
"opt_full_brackets :",
"opt_full_brackets : opt_full_brackets '[' expr ']'",
"block : '{' block_content '}'",
"block_content :",
"block_content : block_content var_dcl sc",
"block_content : block_content statement",
"statement : assignment sc",
"statement : method_call sc",
"statement : cond_stmt",
"statement : loop_stmt",
"statement : RETURN sc",
"statement : RETURN expr sc",
"statement : goto sc",
"statement : label",
"statement : BREAK sc",
"statement : CONTINUE sc",
"statement : SIZEOF '(' typee ')' sc",
"assignment : variable '=' expr",
"method_call : ID '(' opt_parameters ')'",
"opt_parameters :",
"opt_parameters : parameters",
"parameters : expr",
"parameters : parameters ',' expr",
"cond_stmt : IF '(' expr ')' block",
"cond_stmt : IF '(' expr ')' block ELSE block",
"cond_stmt : SWITCH '(' ID ')' OF ':' '{' opt_cases DEFAULT ':' block '}'",
"opt_cases :",
"opt_cases : opt_cases CASE int_const ':' block",
"loop_stmt : FOR '(' opt_var_dcls sc expr sc assignment ')' block",
"loop_stmt : FOR '(' opt_var_dcls sc expr sc expr ')' block",
"loop_stmt : REPEAT block UNTIL '(' expr ')' sc",
"loop_stmt : FOREACH '(' ID IN ID ')' block",
"goto : GOTO ID",
"label : ID ':'",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : expr '%' expr",
"expr : expr '|' expr",
"expr : expr '&' expr",
"expr : expr '^' expr",
"expr : expr '>' expr",
"expr : expr '<' expr",
"expr : expr AND expr",
"expr : expr OR expr",
"expr : expr EQ expr",
"expr : expr NE expr",
"expr : expr GE expr",
"expr : expr LE expr",
"expr : '(' expr ')'",
"expr : method_call",
"expr : variable",
"expr : const_val",
"expr : '-' expr",
"expr : '!' expr",
"expr : SIZEOF '(' typee ')'",
"variable : ID opt_full_brackets",
"variable : ID opt_full_brackets '.' variable",
"variable : '~' variable",
"variable : DEC variable",
"variable : INC variable",
"variable : variable DEC",
"variable : variable INC",
"const_val : IC",
"const_val : RC",
"const_val : CC",
"const_val : BC",
"const_val : SC",
"int_const : IC",
"int_const : CC",
};

//#line 239 "Parser.yacc"




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
//#line 674 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
