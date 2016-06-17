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
    0,    0,    0,    0,    7,    8,    8,    8,   10,   10,
   11,   13,   13,   15,   15,   16,   16,   12,   12,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    9,
   17,   17,   18,   18,    6,    6,   19,   19,   20,   20,
    3,    3,   14,   21,   21,   21,   21,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   23,
   25,   30,   30,   31,   31,   26,   26,   26,   32,   32,
   27,   27,   27,   27,   28,   29,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    4,    4,   24,   24,   24,   24,   24,    5,    5,
    5,    5,    5,   33,   33,
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
   29,    0,    0,   26,    0,    0,    0,    0,    0,    6,
    7,    8,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   37,  103,    5,    0,    3,    4,    0,    0,
    0,   41,    0,  105,  106,    0,    0,  104,  107,  108,
    0,    0,    2,   11,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  109,  110,    0,  111,  113,  112,
    0,    0,    0,    0,    0,    0,   96,   94,   38,   41,
    0,    0,   31,    0,    0,    0,    0,  102,    0,    0,
   98,   97,   99,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   18,   19,    0,   30,   32,    9,   10,   42,
    0,    0,    0,    0,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   79,   80,
   81,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,   52,    0,   56,   41,  100,   61,    0,    0,   53,
   57,   58,    0,    0,    0,    0,    0,    0,   75,   76,
    0,    0,   47,   43,   46,   48,   49,   50,   55,    0,
    0,   54,    0,    0,    0,   34,    0,    0,    0,    0,
   45,    0,    0,    0,    0,    0,    0,   59,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   67,   69,
    0,    0,    0,   74,   73,    0,    0,    0,    0,    0,
   72,   71,    0,  114,  115,    0,    0,    0,   68,   70,
};
final static short yydgoto[] = {                         15,
   75,   58,   47,   76,   77,   17,   36,   18,   19,   20,
   21,   22,   56,  155,   57,    0,   60,  197,   32,   33,
  156,  157,  158,   34,   78,  161,  162,  163,  164,  123,
  124,  226,  236,
};
final static short yysindex[] = {                       885,
  280, -299,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -282,  280,    0,    0,  -81,    2,  885,  885,    0,
    0,    0, -239,   31,  -39,  -67,  -67,  -67,   66,  -67,
  -59,   67,    0,    0,    0,  885,    0,    0,    2,  280,
  594,    0,   67,    0,    0,  280,  -29,    0,    0,    0,
  -22,  -67,    0,    0, -187,   76,   93,  -67,    2,  143,
   97,  -22,  -67,   99,    0,    0,  111,    0,    0,    0,
  -22,  -22,  -22,  -22,  395, -200,    0,    0,    0,    0,
  -40,  280,    0,    2,    2,  -40,   60,    0,  280,  -22,
    0,    0,    0,   71,  -22,  -22,  -22,  -22,  -22,  -22,
  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,
   64,  792,    0,    0, -133,    0,    0,    0,    0,    0,
  134,  395,  139,  138,    0,  748,  748,  163,  163,  578,
  443,  617,  700,  711,  163,  163,   43,   43,    0,    0,
    0,  -33,    2,    2,  150,  153,  154,  157,  159,   78,
 -100,  -36,  -56,    2,  792,   84,  792,    2,    2,    2,
    0,    0,    2,    0,    0,    0,    0,  -22,   98,    0,
    0,    0,  280,  -22,  -99,  594,  -91,  -72,    0,    0,
  -22,  792,    0,    0,    0,    0,    0,    0,    0,   64,
  395,    0,  172,  125,  175,    0,    2,  -80,  178,  395,
    0,    2,   78,  -58,  -22,  -79,  -22,    0,  -52,  167,
   98,  185,  136,   78,  104,  -22,   78,    2,    0,    0,
  272,  -56,  190,    0,    0, -151,   78,   78,  174, -272,
    0,    0,   78,    0,    0,  176,  119,   78,    0,    0,
};
final static short yyrindex[] = {                       233,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  233,  233,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -31,    0,
  -30,    2,    0,    0,    0,  233,    0,    0,    0,  204,
    0,    0,  187,    0,    0,  204,    6,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  206,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   -5,    0,    0,    0,
    0,    0,    0,    0,   28,   32,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  207,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  106,  128,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  108,    0,  213,    0,  300,  554,  432,  476,  -35,
  145,   83,  342,  558,  490,  513,  403,  415,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -45,    0,    0,  128,    0,  128,    0,  -53,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  196,    0,    0,    0,    0,
    0,  128,    0,    0,    0,    0,    0,    0,    0,  120,
  147,    0,    0,    0,    0,    0,    0,    0,    0,   23,
    0,    0,    0,    0,    0,    0,    0,    0,  749,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  337,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                        16,
 1031,  947,  -55,  770,    0,  660,  735,    0,    0,    0,
    0,    0,  210,  868,    0,    0,    0,    0,  235,  223,
  -23,    0,   63,  -34,  -11,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=1247;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         73,
   41,   51,   24,   90,  181,   87,   74,  103,   87,   71,
   73,   72,   41,   39,   41,   41,   63,   74,   35,   25,
   71,  180,   72,   87,  111,   35,  234,   41,   39,   41,
  235,   41,   41,   37,   38,   41,   41,   41,   41,   41,
   41,   41,  101,  101,   30,   41,  101,  101,  101,  101,
  101,   53,  101,   41,   41,   41,   41,   87,   30,   41,
   35,   62,   39,   60,  101,  101,  101,  101,   95,   95,
   40,   40,   95,   95,   95,   95,   95,  159,   95,  110,
   26,   60,  112,   41,  108,   41,   40,   41,   41,  109,
   95,   95,   30,   95,   49,   50,  110,  103,  101,  101,
  160,  108,  106,   30,  107,   46,  109,  110,  103,  190,
   52,  125,  108,  106,   80,  107,   81,  109,   41,  104,
  159,  105,  159,   82,   95,   95,   82,  229,  230,  101,
  104,  183,  105,  185,  110,  103,   82,   86,   89,  108,
  106,   82,  107,  160,  109,  160,   14,  159,   64,   14,
   90,   64,  120,  102,   62,   95,   35,  104,  201,  105,
   15,  110,  103,   15,  102,  203,  108,  106,  165,  107,
  160,  109,  110,  103,  166,   82,  218,  108,  106,  167,
  107,  168,  109,  101,  104,   88,  105,   65,   88,  173,
   65,  102,  174,  175,  101,  104,  176,  105,  177,  110,
  112,  179,  195,   88,  108,  106,   82,  107,  184,  109,
  198,  199,  202,   27,   28,  204,  206,  207,  102,  210,
   29,  101,  212,  214,  215,  217,  220,   27,   28,  102,
  228,  233,    1,  238,   42,   49,   50,   88,   49,   50,
   64,  103,  103,  239,   12,   36,   13,   62,  101,   26,
   26,   64,   44,   63,   33,   61,   26,   87,   87,  101,
   43,   27,   28,   41,   41,   65,   66,   84,   67,   68,
   69,   70,   27,   28,   79,    0,   65,   66,  223,   67,
   68,   69,   70,   41,   41,   41,   41,   41,   41,   41,
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
   86,    0,    0,   86,   91,   91,  102,   91,   78,    3,
    4,    5,    6,    7,    8,    9,   10,   11,   86,   86,
   85,   86,    0,   85,    0,   92,   85,    0,    0,    0,
   95,   96,   97,   98,   99,  100,  101,    0,   91,   91,
    0,   85,   85,    0,   85,    0,    0,    0,    0,    0,
    0,   14,   86,   86,    0,    0,    0,    0,   89,   89,
    0,   90,   89,   89,   90,   83,    0,   90,   83,   91,
    0,   83,    0,    0,    0,   85,   85,    0,    0,    0,
    0,    0,   90,   86,  110,  103,   83,    0,    0,  108,
  106,    0,  107,    0,  109,   95,   95,   95,   95,   95,
   95,    0,    0,    0,   84,   84,   85,  104,    0,  105,
    0,    0,    0,    0,    0,    0,   90,   90,    0,    0,
   83,   83,    0,  110,  103,    0,    0,    0,  108,  106,
    0,  107,    0,  109,    0,    0,    0,    0,    0,    0,
    0,  102,    0,    0,    0,    0,  104,   90,  105,    0,
    0,   83,    0,   95,   96,   97,   98,   99,  100,    0,
    0,   77,   77,   77,   77,   77,   77,    0,    0,    0,
   59,  101,    0,   78,   78,   78,   78,   78,   78,    0,
  102,    0,    0,    0,    0,    0,    0,    0,    0,   85,
   92,   92,   92,   92,   92,   92,    0,    0,    0,    0,
    0,   95,   96,   97,   98,   99,  110,  103,    0,    0,
    0,  108,  106,    0,  107,    0,  109,  110,    0,    0,
    0,    0,  108,  106,    0,  107,    0,  109,    0,  104,
    0,  105,    0,    0,   91,   91,   91,   91,   91,   91,
  104,  154,  105,   54,    0,    0,    0,    0,   86,   86,
   86,   86,   86,   86,  110,   31,    0,    0,    0,  108,
  106,    0,  107,   83,  109,   31,   44,   45,    0,   48,
    0,   85,   85,   85,   85,   85,   85,  104,    0,  105,
    0,    0,    0,    0,  154,  113,  154,    0,  116,  117,
  118,   31,    0,    0,    0,    0,    0,   31,    0,    0,
    0,    0,   88,    0,    0,  196,    0,    0,    0,    0,
    0,  154,   90,   90,    0,    0,   90,   90,    0,    0,
   83,   83,    0,    3,    4,    5,    6,    7,    8,    9,
   10,   11,    0,   13,    0,    0,   95,   96,   97,   98,
    0,   66,    0,   66,   66,    0,  170,  171,  172,    0,
    0,  153,    0,    0,    0,    0,    0,    0,  182,    0,
    0,    0,  186,  187,  188,   14,    0,  189,    0,    0,
    0,    0,    0,  192,    0,   95,   96,   97,   98,    0,
    0,    0,    0,    0,  112,    0,    0,   30,    0,    0,
    0,    0,    0,    0,  153,    0,  153,    0,    0,    0,
    0,  205,    0,    0,    0,    0,  208,    0,    0,    0,
    0,    0,    0,    0,    0,  216,   16,   23,  114,    0,
    0,  153,  225,  119,    0,    0,    0,    0,    0,   26,
    0,    0,    0,    0,   16,   16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   16,    0,    0,  222,   55,    0,   95,   96,
   97,   98,   55,    0,    0,    0,    0,    0,    0,   95,
   96,   97,   98,    0,    0,    0,    0,    0,   66,   66,
   66,   66,   66,   66,   66,   66,   66,  178,   66,   66,
   66,   66,   66,   66,    0,   66,    0,    0,  115,   66,
   66,   66,    0,    0,    0,  121,   66,    0,   97,   98,
    0,    0,    0,   66,   66,    0,    0,    0,    0,    0,
   66,    3,    4,    5,    6,    7,    8,    9,   10,   11,
    0,   13,  142,  143,  144,  145,  146,    0,  147,    0,
  209,    0,  148,  149,  150,    0,    0,    0,    0,  151,
    0,  219,    0,    0,  224,    0,   27,   28,    0,    0,
    0,    0,   87,  152,  231,  232,    0,    0,    0,    0,
  237,   91,   92,   93,   94,  240,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  193,
  122,    0,    0,    0,    0,  126,  127,  128,  129,  130,
  131,  132,  133,  134,  135,  136,  137,  138,  139,  140,
  141,    1,    2,    0,    3,    4,    5,    6,    7,    8,
    9,   10,   11,   12,   13,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  169,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   14,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  191,    0,
    0,    0,    0,    0,  194,    0,    0,    0,    0,    0,
    0,  200,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  211,    0,  213,    0,    0,
    0,    0,    0,    0,    0,    0,  221,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   46,   61,  302,   40,   61,   41,   40,   61,   44,   43,
   33,   45,   44,   44,   46,   61,   46,   40,   59,  302,
   43,   58,   45,   59,   80,   59,  299,   59,   59,   61,
  303,   37,   38,   18,   19,   41,   42,   43,   44,   45,
   46,   47,   37,   38,  126,   91,   41,   42,   43,   44,
   45,   36,   47,   59,   60,   61,   62,   93,  126,   91,
   59,   91,  302,   41,   59,   60,   61,   62,   37,   38,
   40,   44,   41,   42,   43,   44,   45,  112,   47,   37,
  126,   59,  123,  123,   42,   91,   59,   93,   94,   47,
   59,   60,  126,   62,  295,  296,   37,   38,   93,   94,
  112,   42,   43,  126,   45,   40,   47,   37,   38,  165,
   44,   41,   42,   43,  302,   45,   41,   47,  124,   60,
  155,   62,  157,   41,   93,   94,   44,  279,  280,  124,
   60,  155,   62,  157,   37,   38,   44,   41,   40,   42,
   43,   59,   45,  155,   47,  157,   41,  182,   41,   44,
   40,   44,   93,   94,   91,  124,   59,   60,  182,   62,
   41,   37,   38,   44,   94,   41,   42,   43,  302,   45,
  182,   47,   37,   38,   41,   93,   41,   42,   43,   41,
   45,   44,   47,  124,   60,   41,   62,   41,   44,   40,
   44,   94,   40,   40,  124,   60,   40,   62,   40,   37,
  123,  302,  302,   59,   42,   43,  124,   45,  125,   47,
  302,  284,   41,  295,  296,   41,  297,   40,   94,  278,
  302,  124,  302,  276,   58,   41,  123,  295,  296,   94,
   41,   58,    0,   58,  302,  295,  296,   93,  295,  296,
  274,  295,  296,  125,   41,   59,   41,   41,  124,  295,
  296,  274,  125,   41,   59,   46,  302,  293,  294,  124,
   26,  295,  296,  295,  296,  299,  300,  125,  302,  303,
  304,  305,  295,  296,   52,   -1,  299,  300,  216,  302,
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
   41,   -1,   -1,   44,   59,   60,   94,   62,  124,  260,
  261,  262,  263,  264,  265,  266,  267,  268,   59,   60,
   38,   62,   -1,   41,   -1,  124,   44,   -1,   -1,   -1,
  289,  290,  291,  292,  293,  294,  124,   -1,   93,   94,
   -1,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   -1,  302,   93,   94,   -1,   -1,   -1,   -1,  289,  290,
   -1,   38,  293,  294,   41,   38,   -1,   44,   41,  124,
   -1,   44,   -1,   -1,   -1,   93,   94,   -1,   -1,   -1,
   -1,   -1,   59,  124,   37,   38,   59,   -1,   -1,   42,
   43,   -1,   45,   -1,   47,  289,  290,  291,  292,  293,
  294,   -1,   -1,   -1,  293,  294,  124,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   94,   -1,   -1,
   93,   94,   -1,   37,   38,   -1,   -1,   -1,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   94,   -1,   -1,   -1,   -1,   60,  124,   62,   -1,
   -1,  124,   -1,  289,  290,  291,  292,  293,  294,   -1,
   -1,  289,  290,  291,  292,  293,  294,   -1,   -1,   -1,
   41,  124,   -1,  289,  290,  291,  292,  293,  294,   -1,
   94,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,
  289,  290,  291,  292,  293,  294,   -1,   -1,   -1,   -1,
   -1,  289,  290,  291,  292,  293,   37,   38,   -1,   -1,
   -1,   42,   43,   -1,   45,   -1,   47,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   -1,   47,   -1,   60,
   -1,   62,   -1,   -1,  289,  290,  291,  292,  293,  294,
   60,  112,   62,   39,   -1,   -1,   -1,   -1,  289,  290,
  291,  292,  293,  294,   37,   16,   -1,   -1,   -1,   42,
   43,   -1,   45,   59,   47,   26,   27,   28,   -1,   30,
   -1,  289,  290,  291,  292,  293,  294,   60,   -1,   62,
   -1,   -1,   -1,   -1,  155,   81,  157,   -1,   84,   85,
   86,   52,   -1,   -1,   -1,   -1,   -1,   58,   -1,   -1,
   -1,   -1,   63,   -1,   -1,  176,   -1,   -1,   -1,   -1,
   -1,  182,  289,  290,   -1,   -1,  293,  294,   -1,   -1,
  293,  294,   -1,  260,  261,  262,  263,  264,  265,  266,
  267,  268,   -1,  270,   -1,   -1,  289,  290,  291,  292,
   -1,  123,   -1,  125,  126,   -1,  142,  143,  144,   -1,
   -1,  112,   -1,   -1,   -1,   -1,   -1,   -1,  154,   -1,
   -1,   -1,  158,  159,  160,  302,   -1,  163,   -1,   -1,
   -1,   -1,   -1,  169,   -1,  289,  290,  291,  292,   -1,
   -1,   -1,   -1,   -1,  123,   -1,   -1,  126,   -1,   -1,
   -1,   -1,   -1,   -1,  155,   -1,  157,   -1,   -1,   -1,
   -1,  197,   -1,   -1,   -1,   -1,  202,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  211,    0,    1,   81,   -1,
   -1,  182,  218,   86,   -1,   -1,   -1,   -1,   -1,   13,
   -1,   -1,   -1,   -1,   18,   19,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   36,   -1,   -1,  216,   40,   -1,  289,  290,
  291,  292,   46,   -1,   -1,   -1,   -1,   -1,   -1,  289,
  290,  291,  292,   -1,   -1,   -1,   -1,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  150,  270,  271,
  272,  273,  274,  275,   -1,  277,   -1,   -1,   82,  281,
  282,  283,   -1,   -1,   -1,   89,  288,   -1,  291,  292,
   -1,   -1,   -1,  295,  296,   -1,   -1,   -1,   -1,   -1,
  302,  260,  261,  262,  263,  264,  265,  266,  267,  268,
   -1,  270,  271,  272,  273,  274,  275,   -1,  277,   -1,
  203,   -1,  281,  282,  283,   -1,   -1,   -1,   -1,  288,
   -1,  214,   -1,   -1,  217,   -1,  295,  296,   -1,   -1,
   -1,   -1,   62,  302,  227,  228,   -1,   -1,   -1,   -1,
  233,   71,   72,   73,   74,  238,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  173,
   90,   -1,   -1,   -1,   -1,   95,   96,   97,   98,   99,
  100,  101,  102,  103,  104,  105,  106,  107,  108,  109,
  110,  257,  258,   -1,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  270,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  142,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  302,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  168,   -1,
   -1,   -1,   -1,   -1,  174,   -1,   -1,   -1,   -1,   -1,
   -1,  181,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  205,   -1,  207,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  216,
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

//#line 247 "parser.yacc"




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
//#line 706 "Parser.java"
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
//#line 61 "parser.yacc"
{System.out.println("semi solon detected!");}
break;
case 14:
//#line 81 "parser.yacc"
{ cg.argument(val_peek(2).sval,val_peek(1).sval,val_peek(0).sval); }
break;
case 15:
//#line 82 "parser.yacc"
{ cg.argument(val_peek(2).sval,val_peek(1).sval,val_peek(0).sval); }
break;
case 20:
//#line 94 "parser.yacc"
{yyval.sval = "4";}
break;
case 21:
//#line 95 "parser.yacc"
{yyval.sval = "1";}
break;
case 22:
//#line 96 "parser.yacc"
{yyval.sval = "4";}
break;
case 23:
//#line 97 "parser.yacc"
{yyval.sval = "8";}
break;
case 24:
//#line 98 "parser.yacc"
{yyval.sval = "1";}
break;
case 25:
//#line 99 "parser.yacc"
{yyval.sval = "8";}
break;
case 35:
//#line 119 "parser.yacc"
{cg.declarationSetType(val_peek(1).sval);}
break;
case 39:
//#line 128 "parser.yacc"
{ cg.declarationNoType(val_peek(0).sval); }
break;
case 40:
//#line 129 "parser.yacc"
{cg.declarationNoType(val_peek(2).sval);cg.assign(val_peek(2).sval,val_peek(0).sval);}
break;
case 41:
//#line 133 "parser.yacc"
{yyval.sval = "0";}
break;
case 42:
//#line 134 "parser.yacc"
{yyval.sval = val_peek(3).sval + "," + val_peek(1).sval;}
break;
case 60:
//#line 160 "parser.yacc"
{cg.assign(val_peek(2).sval,val_peek(0).sval);}
break;
case 77:
//#line 196 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("+",val_peek(2).sval,val_peek(0).sval);}
break;
case 78:
//#line 197 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("-",val_peek(2).sval,val_peek(0).sval);}
break;
case 79:
//#line 198 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("*",val_peek(2).sval,val_peek(0).sval);}
break;
case 80:
//#line 199 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("/",val_peek(2).sval,val_peek(0).sval);}
break;
case 81:
//#line 200 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("%",val_peek(2).sval,val_peek(0).sval);}
break;
case 82:
//#line 201 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("|",val_peek(2).sval,val_peek(0).sval);}
break;
case 83:
//#line 202 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("&",val_peek(2).sval,val_peek(0).sval);}
break;
case 84:
//#line 203 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("^",val_peek(2).sval,val_peek(0).sval);}
break;
case 85:
//#line 204 "parser.yacc"
{yyval.sval = cg.arithmeticOperand(">",val_peek(2).sval,val_peek(0).sval);}
break;
case 86:
//#line 205 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("<",val_peek(2).sval,val_peek(0).sval);}
break;
case 87:
//#line 206 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("&&",val_peek(2).sval,val_peek(0).sval);}
break;
case 88:
//#line 207 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("||",val_peek(2).sval,val_peek(0).sval);}
break;
case 89:
//#line 208 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("==",val_peek(2).sval,val_peek(0).sval);}
break;
case 90:
//#line 209 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("!=",val_peek(2).sval,val_peek(0).sval);}
break;
case 91:
//#line 210 "parser.yacc"
{yyval.sval = cg.arithmeticOperand(">=",val_peek(2).sval,val_peek(0).sval);}
break;
case 92:
//#line 211 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("<=",val_peek(2).sval,val_peek(0).sval);}
break;
case 93:
//#line 212 "parser.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 101:
//#line 222 "parser.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 109:
//#line 234 "parser.yacc"
{yyval.sval = new Integer(val_peek(0).ival).toString();}
break;
//#line 991 "Parser.java"
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
