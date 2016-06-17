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






//#line 2 "parser.yacc"
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
public final static short FUNCTION=259;
public final static short INT=260;
public final static short BOOL=261;
public final static short FLOAT=262;
public final static short LONG=263;
public final static short CHAR=264;
public final static short DOUBLE=265;
public final static short STRING=266;
public final static short VOID=267;
public final static short AUTO=268;
public final static short RECORD=269;
public final static short CONST=270;
public final static short RETURN=271;
public final static short BREAK=272;
public final static short CONTINUE=273;
public final static short SIZEOF=274;
public final static short IF=275;
public final static short ELSE=276;
public final static short SWITCH=277;
public final static short OF=278;
public final static short DEFAULT=279;
public final static short CASE=280;
public final static short FOR=281;
public final static short FOREACH=282;
public final static short REPEAT=283;
public final static short UNTIL=284;
public final static short INPUT=285;
public final static short OUTPUT=286;
public final static short STATIC=287;
public final static short GOTO=288;
public final static short EQ=289;
public final static short NE=290;
public final static short LE=291;
public final static short GE=292;
public final static short AND=293;
public final static short OR=294;
public final static short DEC=295;
public final static short INC=296;
public final static short IN=297;
public final static short NL=298;
public final static short IC=299;
public final static short RC=300;
public final static short HC=301;
public final static short ID=302;
public final static short CC=303;
public final static short SC=304;
public final static short BC=305;
public final static short ttt=306;
public final static short fuck=307;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    2,    3,    3,    3,    5,    5,
    6,    9,    9,   11,   11,   13,   13,    7,    7,    8,
    8,    8,    8,    8,    8,    8,    8,    8,    8,    4,
   14,   14,   15,   15,    1,    1,   16,   16,   17,   17,
   12,   12,   10,   20,   20,   20,   20,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   22,
   24,   29,   29,   30,   30,   25,   25,   25,   31,   31,
   26,   26,   26,   26,   27,   28,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   18,   18,   18,   23,   23,   23,   23,   23,   33,   33,
   33,   33,   33,   32,   32,
};
final static short yylen[] = {                            2,
    0,    3,    2,    2,    1,    1,    1,    1,    6,    6,
    4,    0,    1,    3,    5,    0,    3,    6,    6,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    6,
    2,    3,    0,    1,    2,    3,    1,    3,    1,    3,
    0,    4,    3,    0,    3,    2,    2,    2,    2,    2,
    1,    1,    2,    3,    2,    1,    2,    2,    5,    3,
    4,    0,    1,    1,    3,    5,    7,   12,    0,    5,
    9,    9,    7,    7,    2,    2,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    1,    1,    1,    2,    2,    2,    4,
    2,    4,    1,    2,    2,    2,    2,    2,    1,    1,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   20,   21,   22,   23,   24,   25,   27,   28,
   29,    0,    0,   26,    0,    0,    0,    0,    6,    7,
    8,    0,    0,    0,    0,    0,    5,    0,    3,    4,
    0,    0,    0,    0,    0,   37,    0,  103,    0,    0,
    0,   41,    0,    2,  105,  106,    0,    0,  104,    0,
  107,  108,    0,   11,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   38,    0,  109,  110,    0,  111,  113,
  112,    0,    0,    0,    0,    0,    0,   94,   96,   41,
    0,    0,   31,    0,    0,    0,    0,  102,    0,    0,
   98,   97,   99,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   18,   19,    0,   30,   32,    9,   10,   42,
    0,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   79,   80,
   81,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,   52,    0,   56,   41,  100,   61,    0,   53,    0,
   57,   58,    0,    0,    0,    0,    0,    0,   75,   76,
    0,   47,    0,   43,   46,   48,   49,   50,   55,    0,
    0,   54,    0,    0,    0,   34,    0,    0,    0,   45,
    0,    0,    0,    0,    0,    0,    0,   59,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   67,   69,
    0,    0,    0,   74,   73,    0,    0,    0,    0,    0,
   72,   71,    0,  114,  115,    0,    0,    0,   68,   70,
};
final static short yydgoto[] = {                         15,
   16,   28,   17,   18,   19,   20,   21,   59,   56,  154,
   57,   48,    0,   60,  197,   35,   36,   76,   77,  156,
  157,  158,   38,   78,  161,  162,  163,  164,  123,  124,
  226,  236,   79,
};
final static short yysindex[] = {                       876,
  574, -299,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -239,  574,    0,    0,   19,  876,  876,    0,    0,
    0,  -81, -201,   75,   -2,  -67,    0,  876,    0,    0,
  -67,  -67,   77,  -67,   79,    0,  -59,    0,   19,  574,
  845,    0,   79,    0,    0,    0,  574,  -29,    0,  -67,
    0,    0,  -22,    0, -174,  105,  106,   19,  -67,  143,
  111,  -22,  -67,    0,  115,    0,    0,  119,    0,    0,
    0,  -22,  -22,  -22,  -22, -200,  395,    0,    0,    0,
  -40,  574,    0,   19,   19,  -40,   60,    0,  574,  -22,
    0,    0,    0,   71,  -22,  -22,  -22,  -22,  -22,  -22,
  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,
   70,  749,    0,    0, -138,    0,    0,    0,    0,    0,
  128,  395,  130,  138,    0,  748,  748,  163,  163,  578,
  443,  617,  700,  711,  163,  163,   43,   43,    0,    0,
    0,  -33,   19,   19,  140,  148,  150,  153,  157,   76,
 -101,  -36,   19,  749,  -56,   78,  749,   19,   19,   19,
    0,    0,   19,    0,    0,    0,    0,  -22,    0,   98,
    0,    0,  574,  -22, -100,  845,  -93,  -73,    0,    0,
  749,    0,  -22,    0,    0,    0,    0,    0,    0,   70,
  395,    0,  171,  125,  172,    0,   19,  -80,  176,    0,
  395,   19,   76,  -58,  -22,  -79,  -22,    0,  -52,  167,
   98,  185,  136,   76,  104,  -22,   76,   19,    0,    0,
  -56,  272,  190,    0,    0, -141,   76,   76,  174, -272,
    0,    0,   76,    0,    0,  175,  109,   76,    0,    0,
};
final static short yyrindex[] = {                       244,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  244,  244,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  244,    0,    0,
    0,    0,  -31,    0,  186,    0,  -30,    0,    0,  205,
    0,    0,  188,    0,    0,    0,  205,    6,    0,    0,
    0,    0,    0,    0,    0,    0,  207,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -5,    0,    0,
    0,    0,    0,    0,    0,   32,  -24,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  212,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   20,  129,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   88,    0,  214,    0,  300,  554,  432,  476,  -35,
  145,   83,  342,  558,  490,  513,  403,  415,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -45,    0,  129,    0,    0,  129,    0,  -53,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  197,    0,    0,    0,    0,
  129,    0,    0,    0,    0,    0,    0,    0,    0,   93,
  103,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   -7,    0,    0,    0,    0,    0,    0,    0,  692,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  337,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                        54,
  499,  775,    0,    0,    0,    0,    0, 1035,  228,  663,
    0,  -55,    0,    0,    0,  235,  226,  766,  993,  -70,
    0,   63,   -6,   37,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=1209;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         74,
   41,   53,   24,   90,  183,   87,   75,  103,   87,   72,
   74,   73,   41,   39,   41,   41,   63,   75,   27,   40,
   72,  180,   73,   87,  111,   27,  234,   41,   39,   41,
  235,   41,   41,   60,   40,   41,   41,   41,   41,   41,
   41,   41,  101,  101,   34,   41,  101,  101,  101,  101,
  101,   60,  101,   41,   41,   41,   41,   87,   34,   41,
   14,   62,   25,   14,  101,  101,  101,  101,   95,   95,
   29,   30,   95,   95,   95,   95,   95,   27,   95,  110,
   26,   44,  112,  182,  108,   41,  185,   41,   41,  109,
   95,   95,   34,   95,   51,   52,  110,  103,  101,  101,
   39,  108,  106,   34,  107,  159,  109,  110,  103,  190,
  200,  125,  108,  106,   40,  107,   47,  109,   41,  104,
   41,  105,   50,   82,   95,   95,   82,   80,   64,  101,
  104,   64,  105,   15,  110,  103,   15,  229,  230,  108,
  106,   82,  107,   65,  109,   81,   65,  159,  160,   82,
  159,   86,  120,  102,   89,   95,   27,  104,   90,  105,
   62,  110,  103,  165,  102,  203,  108,  106,  166,  107,
  167,  109,  110,  103,  159,   82,  218,  108,  106,  173,
  107,  168,  109,  101,  104,   88,  105,  174,   88,  175,
  160,  102,  176,  160,  101,  104,  177,  105,  112,  110,
  179,  195,  184,   88,  108,  106,   82,  107,  198,  109,
  199,  202,  204,   31,   32,  207,  206,  160,  102,  210,
   33,  101,  212,  214,  215,  217,  220,   31,   32,  102,
  228,  233,  238,  239,   42,   51,   52,   88,   51,   52,
   65,  103,  103,    1,   35,   12,   36,   13,  101,   26,
   26,   65,   62,   44,   63,   33,   26,   87,   87,  101,
   43,   31,   32,   41,   41,   66,   67,   84,   68,   69,
   70,   71,   31,   32,   61,   64,   66,   67,  223,   68,
   69,   70,   71,   41,   41,   41,   41,   41,   41,   41,
   41,    0,    0,    0,  101,  101,  101,  101,  101,  101,
  101,  101,    0,    0,    0,    0,    0,    0,  110,  103,
    0,    0,  227,  108,  106,    0,  107,    0,  109,    0,
   95,   95,   95,   95,   95,   95,    0,    0,    0,    0,
    0,  104,    0,  105,    0,    0,    0,   89,    0,    0,
   89,    0,    0,   89,    0,    0,    0,    0,   95,   96,
   97,   98,   99,  100,    0,    0,    0,    0,   89,   95,
   96,   97,   98,   99,  100,  102,    0,    0,    0,    0,
    0,    0,    0,   95,   95,   82,   82,   95,   95,   95,
    0,   95,   84,   95,    0,   84,   95,   96,   97,   98,
   99,  100,   89,   89,    0,  101,   95,    0,   95,    0,
   84,    0,    3,    4,    5,    6,    7,    8,    9,   10,
   11,    0,   13,   95,   96,   97,   98,   99,  100,    0,
    0,    0,    0,   89,   95,   96,   97,   98,   99,  100,
   95,  110,  103,    0,   84,   84,  108,  106,   88,  107,
   77,  109,    0,   77,   14,   77,   77,   77,    0,    0,
    0,    0,   78,    0,  104,   78,  105,   78,   78,   78,
   95,   77,   77,    0,   77,   84,    0,    0,    0,   92,
    0,    0,   92,   78,   78,   92,   78,    0,    0,  110,
  103,    0,    0,    0,  108,  106,    0,  107,  102,  109,
   92,   92,    0,   92,    0,   77,   77,    0,    0,    0,
    0,    0,  104,    0,  105,    0,    0,   78,   78,    0,
    0,    0,    0,   91,    0,    0,   91,    0,  101,   91,
    0,    0,    0,    0,   92,   92,   77,   86,    0,    0,
   86,    0,    0,   86,   91,   91,  102,   91,   78,   58,
    0,    0,    0,    0,    0,    0,    0,    0,   86,   86,
   85,   86,    0,   85,    0,   92,   85,    0,   85,    0,
   95,   96,   97,   98,   99,  100,  101,    0,   91,   91,
    0,   85,   85,    0,   85,    0,    0,    0,    0,    0,
    0,    0,   86,   86,    0,    0,    0,    0,   89,   89,
    0,   90,   89,   89,   90,   83,    0,   90,   83,   91,
    0,   83,    0,    0,    0,   85,   85,    0,    0,    0,
  153,    0,   90,   86,  110,  103,   83,    0,    0,  108,
  106,    0,  107,    0,  109,   95,   95,   95,   95,   95,
   95,    0,    0,    0,   84,   84,   85,  104,    0,  105,
    0,    0,    0,    0,    0,    0,   90,   90,    0,    0,
   83,   83,  153,  110,  103,  153,    0,    0,  108,  106,
    0,  107,    0,  109,    0,    0,    0,    0,    0,    0,
    0,  102,    0,    0,  196,    0,  104,   90,  105,  153,
    0,   83,    0,   95,   96,   97,   98,   99,  100,    0,
    0,   77,   77,   77,   77,   77,   77,    0,    0,    0,
    0,  101,    0,   78,   78,   78,   78,   78,   78,    0,
  102,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,   92,   92,   92,   92,   92,    0,    0,    0,    0,
    0,   95,   96,   97,   98,   99,  110,  103,    0,    0,
    0,  108,  106,  114,  107,    0,  109,  110,  119,    0,
    0,    0,  108,  106,    0,  107,    0,  109,    0,  104,
    0,  105,    0,    0,   91,   91,   91,   91,   91,   91,
  104,    0,  105,    0,    0,    0,    0,    0,   86,   86,
   86,   86,   86,   86,  110,    0,    0,   37,    0,  108,
  106,   37,  107,    0,  109,    0,   45,   46,    0,   49,
    0,   85,   85,   85,   85,   85,   85,  104,    0,  105,
    0,    0,  178,   54,   66,   37,   66,   66,    0,    0,
    0,    0,    0,    0,   37,    0,    0,    0,   88,    0,
    0,    0,   83,    3,    4,    5,    6,    7,    8,    9,
   10,   11,   90,   90,    0,    0,   90,   90,    0,    0,
   83,   83,    0,    0,    0,  113,    0,    0,  116,  117,
  118,    0,    0,    0,    0,  209,   95,   96,   97,   98,
    0,  112,    0,    0,   34,   14,  219,  155,    0,  224,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  231,
  232,    0,    0,    0,    0,  237,    0,    0,    0,    0,
  240,    0,    0,    0,    0,   95,   96,   97,   98,    0,
    0,    0,    0,    0,    0,    0,  169,  171,  172,  155,
    0,    0,  155,    0,    0,    0,    0,  181,    0,    0,
    0,    0,  186,  187,  188,    0,    0,  189,    0,    0,
    0,    0,    0,    0,  192,    0,  155,    0,    0,    0,
    0,   66,   66,   66,   66,   66,   66,   66,   66,   66,
    0,   66,   66,   66,   66,   66,   66,    0,   66,    0,
    0,  205,   66,   66,   66,    0,  208,    0,    0,   66,
    0,  221,    0,    0,    0,  216,   66,   66,   95,   96,
   97,   98,  225,   66,    0,    0,    0,    0,    0,   95,
   96,   97,   98,    0,    0,    0,    0,    0,    3,    4,
    5,    6,    7,    8,    9,   10,   11,    0,   13,  142,
  143,  144,  145,  146,    0,  147,    0,    0,    0,  148,
  149,  150,    0,    0,   22,   23,  151,    0,   97,   98,
    0,    0,    0,   31,   32,    0,    0,   26,    0,    0,
  152,   22,   22,    0,   87,    0,    0,    0,    0,    0,
    0,    0,   22,    0,   91,   92,   93,   94,    0,    0,
    0,    0,    0,    0,   55,    0,    0,    0,    0,    0,
    0,   55,  122,    0,    0,    0,    0,  126,  127,  128,
  129,  130,  131,  132,  133,  134,  135,  136,  137,  138,
  139,  140,  141,    0,    3,    4,    5,    6,    7,    8,
    9,   10,   11,    0,   13,    0,  115,    0,    0,    0,
    0,    0,    0,  121,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    2,  170,    3,    4,    5,    6,    7,
    8,    9,   10,   11,   12,   13,   14,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  191,    0,    0,    0,    0,    0,  194,    0,    0,    0,
    0,    0,    0,    0,    0,  201,    0,   14,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  211,    0,  213,
    0,    0,    0,    0,    0,    0,    0,  193,  222,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   46,   61,  302,   40,   61,   41,   40,   61,   44,   43,
   33,   45,   44,   44,   46,   61,   46,   40,   59,   44,
   43,   58,   45,   59,   80,   59,  299,   59,   59,   61,
  303,   37,   38,   41,   59,   41,   42,   43,   44,   45,
   46,   47,   37,   38,  126,   91,   41,   42,   43,   44,
   45,   59,   47,   59,   60,   61,   62,   93,  126,   91,
   41,   91,  302,   44,   59,   60,   61,   62,   37,   38,
   17,   18,   41,   42,   43,   44,   45,   59,   47,   37,
  126,   28,  123,  154,   42,   91,  157,   93,   94,   47,
   59,   60,  126,   62,  295,  296,   37,   38,   93,   94,
  302,   42,   43,  126,   45,  112,   47,   37,   38,  165,
  181,   41,   42,   43,   40,   45,   40,   47,  124,   60,
  123,   62,   44,   41,   93,   94,   44,  302,   41,  124,
   60,   44,   62,   41,   37,   38,   44,  279,  280,   42,
   43,   59,   45,   41,   47,   41,   44,  154,  112,   44,
  157,   41,   93,   94,   40,  124,   59,   60,   40,   62,
   91,   37,   38,  302,   94,   41,   42,   43,   41,   45,
   41,   47,   37,   38,  181,   93,   41,   42,   43,   40,
   45,   44,   47,  124,   60,   41,   62,   40,   44,   40,
  154,   94,   40,  157,  124,   60,   40,   62,  123,   37,
  302,  302,  125,   59,   42,   43,  124,   45,  302,   47,
  284,   41,   41,  295,  296,   40,  297,  181,   94,  278,
  302,  124,  302,  276,   58,   41,  123,  295,  296,   94,
   41,   58,   58,  125,  302,  295,  296,   93,  295,  296,
  274,  295,  296,    0,   59,   41,   59,   41,  124,  295,
  296,  274,   41,  125,   41,   59,  302,  293,  294,  124,
   26,  295,  296,  295,  296,  299,  300,  125,  302,  303,
  304,  305,  295,  296,   47,   50,  299,  300,  216,  302,
  303,  304,  305,  289,  290,  291,  292,  293,  294,  295,
  296,   -1,   -1,   -1,  289,  290,  291,  292,  293,  294,
  295,  296,   -1,   -1,   -1,   -1,   -1,   -1,   37,   38,
   -1,   -1,   41,   42,   43,   -1,   45,   -1,   47,   -1,
  289,  290,  291,  292,  293,  294,   -1,   -1,   -1,   -1,
   -1,   60,   -1,   62,   -1,   -1,   -1,   38,   -1,   -1,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,  289,  290,
  291,  292,  293,  294,   -1,   -1,   -1,   -1,   59,  289,
  290,  291,  292,  293,  294,   94,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   37,   38,  293,  294,   41,   42,   43,
   -1,   45,   41,   47,   -1,   44,  289,  290,  291,  292,
  293,  294,   93,   94,   -1,  124,   60,   -1,   62,   -1,
   59,   -1,  260,  261,  262,  263,  264,  265,  266,  267,
  268,   -1,  270,  289,  290,  291,  292,  293,  294,   -1,
   -1,   -1,   -1,  124,  289,  290,  291,  292,  293,  294,
   94,   37,   38,   -1,   93,   94,   42,   43,  294,   45,
   38,   47,   -1,   41,  302,   43,   44,   45,   -1,   -1,
   -1,   -1,   38,   -1,   60,   41,   62,   43,   44,   45,
  124,   59,   60,   -1,   62,  124,   -1,   -1,   -1,   38,
   -1,   -1,   41,   59,   60,   44,   62,   -1,   -1,   37,
   38,   -1,   -1,   -1,   42,   43,   -1,   45,   94,   47,
   59,   60,   -1,   62,   -1,   93,   94,   -1,   -1,   -1,
   -1,   -1,   60,   -1,   62,   -1,   -1,   93,   94,   -1,
   -1,   -1,   -1,   38,   -1,   -1,   41,   -1,  124,   44,
   -1,   -1,   -1,   -1,   93,   94,  124,   38,   -1,   -1,
   41,   -1,   -1,   44,   59,   60,   94,   62,  124,   41,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,
   38,   62,   -1,   41,   -1,  124,   44,   -1,   60,   -1,
  289,  290,  291,  292,  293,  294,  124,   -1,   93,   94,
   -1,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   93,   94,   -1,   -1,   -1,   -1,  289,  290,
   -1,   38,  293,  294,   41,   38,   -1,   44,   41,  124,
   -1,   44,   -1,   -1,   -1,   93,   94,   -1,   -1,   -1,
  112,   -1,   59,  124,   37,   38,   59,   -1,   -1,   42,
   43,   -1,   45,   -1,   47,  289,  290,  291,  292,  293,
  294,   -1,   -1,   -1,  293,  294,  124,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   94,   -1,   -1,
   93,   94,  154,   37,   38,  157,   -1,   -1,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   94,   -1,   -1,  176,   -1,   60,  124,   62,  181,
   -1,  124,   -1,  289,  290,  291,  292,  293,  294,   -1,
   -1,  289,  290,  291,  292,  293,  294,   -1,   -1,   -1,
   -1,  124,   -1,  289,  290,  291,  292,  293,  294,   -1,
   94,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  289,  290,  291,  292,  293,  294,   -1,   -1,   -1,   -1,
   -1,  289,  290,  291,  292,  293,   37,   38,   -1,   -1,
   -1,   42,   43,   81,   45,   -1,   47,   37,   86,   -1,
   -1,   -1,   42,   43,   -1,   45,   -1,   47,   -1,   60,
   -1,   62,   -1,   -1,  289,  290,  291,  292,  293,  294,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,  289,  290,
  291,  292,  293,  294,   37,   -1,   -1,   22,   -1,   42,
   43,   26,   45,   -1,   47,   -1,   31,   32,   -1,   34,
   -1,  289,  290,  291,  292,  293,  294,   60,   -1,   62,
   -1,   -1,  150,   39,  123,   50,  125,  126,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   63,   -1,
   -1,   -1,   58,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  289,  290,   -1,   -1,  293,  294,   -1,   -1,
  293,  294,   -1,   -1,   -1,   81,   -1,   -1,   84,   85,
   86,   -1,   -1,   -1,   -1,  203,  289,  290,  291,  292,
   -1,  123,   -1,   -1,  126,  302,  214,  112,   -1,  217,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  227,
  228,   -1,   -1,   -1,   -1,  233,   -1,   -1,   -1,   -1,
  238,   -1,   -1,   -1,   -1,  289,  290,  291,  292,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  142,  143,  144,  154,
   -1,   -1,  157,   -1,   -1,   -1,   -1,  153,   -1,   -1,
   -1,   -1,  158,  159,  160,   -1,   -1,  163,   -1,   -1,
   -1,   -1,   -1,   -1,  170,   -1,  181,   -1,   -1,   -1,
   -1,  260,  261,  262,  263,  264,  265,  266,  267,  268,
   -1,  270,  271,  272,  273,  274,  275,   -1,  277,   -1,
   -1,  197,  281,  282,  283,   -1,  202,   -1,   -1,  288,
   -1,  216,   -1,   -1,   -1,  211,  295,  296,  289,  290,
  291,  292,  218,  302,   -1,   -1,   -1,   -1,   -1,  289,
  290,  291,  292,   -1,   -1,   -1,   -1,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,   -1,  270,  271,
  272,  273,  274,  275,   -1,  277,   -1,   -1,   -1,  281,
  282,  283,   -1,   -1,    0,    1,  288,   -1,  291,  292,
   -1,   -1,   -1,  295,  296,   -1,   -1,   13,   -1,   -1,
  302,   17,   18,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   28,   -1,   72,   73,   74,   75,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,
   -1,   47,   90,   -1,   -1,   -1,   -1,   95,   96,   97,
   98,   99,  100,  101,  102,  103,  104,  105,  106,  107,
  108,  109,  110,   -1,  260,  261,  262,  263,  264,  265,
  266,  267,  268,   -1,  270,   -1,   82,   -1,   -1,   -1,
   -1,   -1,   -1,   89,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  142,  260,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  270,  302,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  168,   -1,   -1,   -1,   -1,   -1,  174,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  183,   -1,  302,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  205,   -1,  207,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  173,  216,
};
}
final static short YYFINAL=15;
final static short YYMAXTOKEN=307;
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
null,null,null,null,null,null,null,null,null,"EXTERN","PROCEDURE","FUNCTION",
"INT","BOOL","FLOAT","LONG","CHAR","DOUBLE","STRING","VOID","AUTO","RECORD",
"CONST","RETURN","BREAK","CONTINUE","SIZEOF","IF","ELSE","SWITCH","OF",
"DEFAULT","CASE","FOR","FOREACH","REPEAT","UNTIL","INPUT","OUTPUT","STATIC",
"GOTO","EQ","NE","LE","GE","AND","OR","DEC","INC","IN","NL","IC","RC","HC","ID",
"CC","SC","BC","ttt","fuck",
};
final static String yyrule[] = {
"$accept : program",
"program :",
"program : var_dcl sc program",
"program : func_proc program",
"program : struct_dec program",
"sc : ';'",
"func_proc : func_dcl",
"func_proc : extern_dcl",
"func_proc : proc_dcl",
"func_dcl : typee ID '(' opt_arguments ')' sc",
"func_dcl : typee ID '(' opt_arguments ')' block",
"extern_dcl : EXTERN typee ID sc",
"opt_arguments :",
"opt_arguments : arguments",
"arguments : typee ID opt_full_brackets",
"arguments : arguments ',' typee ID opt_full_brackets",
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
"opt_var_dcls : var_dcl",
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
"block_content : var_dcl sc block_content",
"block_content : statement block_content",
"block_content : block block_content",
"statement : assignment sc",
"statement : variable_ sc",
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
"expr : '+' expr",
"expr : '!' expr",
"expr : SIZEOF '(' typee ')'",
"variable : ID opt_full_brackets",
"variable : ID opt_full_brackets '.' variable",
"variable : variable_",
"variable_ : '~' variable",
"variable_ : DEC variable",
"variable_ : INC variable",
"variable_ : variable DEC",
"variable_ : variable INC",
"const_val : IC",
"const_val : RC",
"const_val : CC",
"const_val : BC",
"const_val : SC",
"int_const : IC",
"int_const : CC",
};

//#line 244 "parser.yacc"




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
//#line 698 "Parser.java"
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
case 5:
//#line 58 "parser.yacc"
{System.out.println("semi solon detected!");}
break;
case 20:
//#line 91 "parser.yacc"
{System.out.println("myDebug: seen a type");}
break;
case 101:
//#line 219 "parser.yacc"
{System.out.println("myDebug: seen an id name and brackets");}
break;
//#line 859 "Parser.java"
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
