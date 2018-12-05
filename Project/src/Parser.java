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
    0,    0,    0,    0,   11,   12,   12,   12,   17,   14,
   19,   19,   15,   18,   18,   21,   21,   22,   22,   16,
   16,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,   13,   23,   23,   24,   24,   10,   10,   25,   25,
   26,   26,    3,    3,   20,   27,   27,   27,   27,   28,
   28,   28,   28,   28,   28,   28,   28,   28,   28,   28,
   29,   29,    8,    5,    5,    4,    4,   35,   30,   37,
   39,   30,   36,   40,   36,   38,   41,   38,   42,   43,
   44,   31,   45,   31,   46,   31,   32,   33,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    6,    6,   34,   34,   34,   34,   34,    7,
    7,    7,    7,    7,    9,    9,
};
final static short yylen[] = {                            2,
    0,    3,    2,    2,    1,    1,    1,    1,    0,    7,
    1,    1,    4,    0,    1,    3,    5,    0,    3,    6,
    6,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    6,    2,    3,    0,    1,    2,    3,    1,    3,
    1,    3,    0,    4,    3,    0,    3,    2,    2,    2,
    2,    1,    1,    2,    3,    2,    1,    2,    2,    5,
    3,    1,    4,    0,    1,    1,    3,    0,    7,    0,
    0,   14,    0,    0,    3,    0,    0,    6,    0,    0,
    0,   12,    0,    8,    0,    8,    2,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    1,    1,    1,    2,    2,
    2,    4,    2,    4,    2,    2,    2,    2,    2,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   22,   23,   24,   25,   26,   27,   29,   30,
   31,    0,    0,   28,    0,    0,    0,    0,    0,    6,
    7,    8,    0,    0,    0,    0,    0,    0,    0,   39,
    5,    0,    3,    4,    0,    0,    0,   43,    0,    0,
    0,    0,    0,    2,   13,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  120,  121,    0,  122,  124,
  123,    0,    0,    0,    0,    0,  107,  108,  106,   40,
   43,    0,    0,   33,    0,    0,    0,  114,    0,    0,
    0,  110,  109,  111,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   20,   21,    0,   32,   34,   44,    0,
    0,    0,    0,    0,  105,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   91,   92,
   93,    0,    0,    0,    0,    0,    0,    0,    0,   83,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   52,   53,    0,   57,   62,   43,   11,   10,
   12,  112,    0,   63,    0,   54,   58,   59,    0,    0,
    0,    0,    0,    0,   87,  116,  117,   88,  115,  118,
  119,    0,   51,    0,   49,   45,   48,   50,   56,    0,
    0,   55,    0,    0,    0,   36,   79,    0,    0,    0,
   47,    0,   68,    0,    0,    0,    0,   60,    0,    0,
    0,    0,    0,    0,   70,    0,   85,    0,   74,   69,
    0,    0,    0,    0,    0,   76,    0,   86,   84,   75,
    0,    0,    0,    0,   81,   71,  125,  126,    0,    0,
    0,   77,   82,    0,    0,   72,   78,
};
final static short yydgoto[] = {                         15,
   66,   49,   40,  113,  114,   67,   68,   69,  239,   17,
   32,   18,   19,   20,   21,   22,   41,   47,  160,  149,
   48,    0,   51,  197,   29,   30,  150,  151,  152,  153,
  154,  155,  156,  157,  209,  220,  221,  231,  241,  225,
  245,  205,  222,  240,  174,  223,
};
final static short yysindex[] = {                       666,
  112, -285,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -280,  112,    0,    0, -270,   16,  666,  666,    0,
    0,    0, -264,   50,  -24, -204,    0,   47,   60,    0,
    0,  666,    0,    0,   16,  112,  611,    0,   60,  -21,
   70,  -25, -204,    0,    0, -190,   73,   75, -204,   16,
  349,  -25, -204,  112,   88,    0,    0,   93,    0,    0,
    0,  -25,  -25,  -25,  -25,  130,    0,    0,    0,    0,
    0,  -55,  112,    0,   16,   16,   29,    0,   79,  112,
  -25,    0,    0,    0,   64,  -25,  -25,  -25,  -25,  -25,
  -25,  -25,  -25,  -25,  -25,  -25,  -25,  -25,  -25,  -25,
  -25,   41,  681,    0,    0, -164,    0,    0,    0,  -55,
  101,  130,   99,  106,    0,  561,  561,  175,  175,  468,
  304,  528,  539,  492,  175,  175,  -23,  -23,    0,    0,
    0,  -33,   16,   16,  109,  115,  129,  134,  136,    0,
 -143, -204, -204,  -35, -204,  -59,   16,   16,  681,   53,
  681,   16,    0,    0,   16,    0,    0,    0,    0,    0,
    0,    0,  -25,    0,   92,    0,    0,    0,  112,  -25,
 -122,  611, -113,   68,    0,    0,    0,    0,    0,    0,
    0,  -25,    0,  681,    0,    0,    0,    0,    0,   41,
  130,    0,  152,  103,  153,    0,    0, -102,  -88,  130,
    0,   16,    0,  -80,   16, -103,  162,    0,   68,  145,
  -25,  164,  -25,  -68,    0,  130,    0,  119,    0,    0,
   83,   16,   68,   16,   68,    0,  -57,    0,    0,    0,
 -197,  173,  157, -226,    0,    0,    0,    0,  161,   68,
   68,    0,    0,   96,   68,    0,    0,
};
final static short yyrindex[] = {                       223,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  223,  223,    0,
    0,    0,    0,    0,    0,    0,  477,  -31,  171,    0,
    0,  223,    0,    0,    0,  191,    0,    0,  174,   -8,
    0,    0,    0,    0,    0,    0,    0,  199,    0,    0,
    0,    0,    0,  191,    0,    0,    0,    3,    0,    0,
    0,    0,    0,    0,    0,  -28,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  201,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   37,  121,    0,    0,    0,    0,    0,    0,    0,
    0,   43,  203,    0,    0,  448,  450,  388,  402,  -38,
   20,  209,  141,   77,  425,  439,  166,  266,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   34,    0,    0,    0,    0,  121,    0,
  121,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  188,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  121,    0,    0,    0,    0,    0,   59,
  143,    0,    0,    0,    0,    0,    0,    0,    0,  -32,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  623,    0,  189,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                        24,
  908,  631,  -70,    0,    0,  619,    0,  -92,    0,   80,
  715,    0,    0,    0,    0,    0,    0,  197,    0,  599,
    0,    0,    0,    0,  231,  215,  -91,    0,   35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=1121;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         64,
  102,  182,   99,   31,   81,   99,   65,   64,   61,   62,
  147,   63,   41,  101,   65,   42,   24,   62,   99,   63,
   99,   25,  178,  100,   53,   31,   61,   41,  113,  113,
   42,   27,  113,  113,  113,  113,  113,   35,  113,   43,
   43,   33,   34,   43,   43,   43,   43,   43,   43,   43,
  113,  113,  113,  113,   99,   44,  147,  185,  147,  187,
  100,   43,   43,  100,   43,  101,   94,  103,  145,   52,
   99,   97,  237,   98,   31,  100,  238,   16,  100,   43,
   16,  233,  234,   66,  113,  113,   66,  190,   95,   36,
   96,  147,  201,   43,   43,   43,   43,   38,   37,   17,
  101,   94,   17,   43,  115,   99,   97,   42,   98,   54,
  100,   71,  100,   72,   95,  113,   50,   95,   73,  110,
   95,  109,   93,   95,   43,   96,   43,   80,  101,   94,
   76,   52,   81,   99,   97,   95,   98,  158,  100,  101,
   94,  162,  163,  203,   99,   97,  164,   98,  169,  100,
   31,   95,   92,   96,  170,  101,   94,   93,  175,  224,
   99,   97,   95,   98,   96,  100,  101,   94,  171,   95,
   95,   99,   97,  172,   98,  173,  100,  186,   95,  195,
   96,   96,  148,   67,   96,   93,   67,   92,  198,   95,
  103,   96,  202,  204,  206,  207,   93,  210,  212,   96,
   95,  213,  215,   89,  217,  226,   89,  219,   89,   89,
   89,  101,   93,  235,  236,   92,   99,   97,  242,   98,
  246,  100,    1,   93,   89,   89,   92,   89,  148,   37,
  148,   14,   38,   96,   96,  180,  181,  142,  143,   15,
   55,   64,   92,   65,   38,   46,   35,   80,   55,   94,
   79,  196,   94,   92,   99,   99,   39,   70,   89,   89,
    0,  232,    0,  148,   96,   56,   57,   94,   58,   59,
   60,   61,    0,   56,   57,    0,   58,   59,   60,   61,
  113,  113,  113,  113,  113,  113,  113,  113,    0,   89,
    0,   43,   43,   43,   43,   43,   43,    0,    0,    0,
    0,   94,    0,   90,    0,    0,   90,    0,   90,   90,
   90,    0,    0,  100,    0,    0,    0,   86,   87,   88,
   89,   90,   91,    0,   90,   90,    0,   90,   43,   43,
    0,    0,   94,    0,    0,   28,    0,    0,    0,    0,
  101,   94,    0,    0,    0,   99,   97,    0,   98,    0,
  100,    0,   86,   87,   88,   89,   90,   91,   90,   90,
    0,    0,    0,   95,    0,   96,    0,    0,    0,   95,
   95,    3,    4,    5,    6,    7,    8,    9,   10,   11,
   86,   87,   88,   89,   90,   91,    0,    0,    0,   90,
    0,   86,   87,   88,   89,   90,   91,   93,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   86,   87,   88,
   89,   90,   91,   14,    0,    0,    0,    0,   86,   87,
   88,   89,   90,   91,    0,  104,    0,   92,  104,    0,
    0,  104,    0,   96,   96,    0,    0,    0,    0,  103,
    0,    0,  103,    0,    0,  103,  104,  104,    0,  104,
    0,    0,    0,    0,   89,   89,   89,   89,   89,   89,
  103,  103,   98,  103,    0,   98,    0,    0,   98,    0,
    0,    0,    0,   75,    0,    0,   97,    0,    0,   97,
  104,  104,   97,   98,   98,  101,   98,  102,  101,    0,
  102,  101,    0,  102,  103,  103,    0,   97,   97,    0,
   97,   94,   94,    0,  101,   94,  101,    0,  102,   99,
   97,  104,   98,    0,  100,    0,    9,   98,   98,    0,
   43,    0,   43,    0,    0,  103,    0,   95,  101,   96,
    0,   97,   97,   99,   97,   43,   98,   43,  100,    0,
  101,  101,  102,  102,    0,    0,    0,    0,   98,    0,
    0,   95,    0,   96,   90,   90,   90,   90,   90,   90,
    0,   93,   97,    0,  101,   94,    0,   43,    0,   99,
   97,  101,   98,  102,  100,  101,   94,    0,    0,    0,
   99,   97,    0,   98,    0,  100,    0,   95,    0,   96,
    0,   92,   86,   87,   88,   89,   90,  101,   95,    0,
   96,    0,   99,   97,    0,   98,    0,  100,    3,    4,
    5,    6,    7,    8,    9,   10,   11,    0,   13,    0,
   95,   93,   96,    0,    0,    0,    0,    0,    0,    0,
   16,   23,    0,    0,   28,    0,    0,    0,    0,    0,
    0,    0,    0,   26,   28,    0,    0,    0,   16,   16,
   14,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   28,   16,    0,    0,    0,   46,   28,    0,    0,
  105,   78,    0,    0,    0,    0,  104,  104,  104,  104,
  104,  104,    0,    0,   46,    0,    0,    0,    0,    0,
  103,  103,  103,  103,  103,  103,    0,    0,    0,    0,
    0,    0,    0,  106,    0,    0,    0,    0,  161,    0,
  111,    0,    0,   98,   98,   98,   98,   98,   98,    0,
    0,  146,    0,    0,    0,    0,    0,   97,   97,   97,
   97,   97,   97,    0,    0,    0,  101,  101,  102,  102,
  101,  101,  102,  102,    0,   73,    0,   73,   73,   45,
    0,    0,    0,    0,    0,    0,   86,   87,   88,   89,
  176,  177,    0,  179,   74,    0,    0,  146,    0,  146,
    0,    0,  199,    0,    0,    0,    0,    0,    0,    0,
   86,   87,   88,   89,    0,    0,  104,    0,    0,  107,
  108,    0,    0,    0,    0,    0,    0,    0,    0,  193,
    0,    0,  146,  103,    0,    0,  145,  214,    0,    0,
    0,    0,    0,    0,    0,    0,   86,   87,   88,   89,
    0,  228,    0,  230,  159,    0,    0,   86,   87,   88,
   89,    0,    0,    0,    0,    0,    0,    0,  243,  244,
    0,    0,    0,  247,    0,  146,  166,  167,  168,    0,
    0,   88,   89,    0,    0,    0,    0,    0,    0,    0,
    0,  183,  184,    0,    0,    0,  188,    0,    0,  189,
    3,    4,    5,    6,    7,    8,    9,   10,   11,  192,
   13,    0,   73,   73,   73,   73,   73,   73,   73,   73,
   73,    0,   73,   73,   73,   73,   73,   73,    0,   73,
    0,    0,    0,   73,   73,   73,    0,    0,    0,    0,
   73,    0,   14,    0,    0,    0,  208,   73,   73,  211,
    0,    0,    1,    2,   73,    3,    4,    5,    6,    7,
    8,    9,   10,   11,   12,   13,  227,    0,  229,    0,
    3,    4,    5,    6,    7,    8,    9,   10,   11,    0,
   13,  132,  133,  134,  135,  136,    0,  137,    0,   77,
    0,  138,  139,  140,    0,    0,    0,   14,  141,   82,
   83,   84,   85,    0,    0,  142,  143,    0,    0,    0,
    0,    0,  144,    0,    0,    0,    0,    0,  112,    0,
    0,    0,    0,  116,  117,  118,  119,  120,  121,  122,
  123,  124,  125,  126,  127,  128,  129,  130,  131,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  165,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  191,    0,    0,    0,    0,    0,    0,  194,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  200,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  216,    0,
  218,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   71,   61,   41,   59,   40,   44,   40,   33,   41,   43,
  103,   45,   44,   37,   40,   44,  302,   43,   42,   45,
   59,  302,   58,   47,   46,   59,   59,   59,   37,   38,
   59,  302,   41,   42,   43,   44,   45,  302,   47,   37,
   38,   18,   19,   41,   42,   43,   44,   45,   46,   47,
   59,   60,   61,   62,   93,   32,  149,  149,  151,  151,
   41,   59,   60,   44,   62,   37,   38,  123,  126,   91,
   42,   43,  299,   45,   59,   47,  303,   41,   59,   46,
   44,  279,  280,   41,   93,   94,   44,  158,   60,   40,
   62,  184,  184,   91,   61,   93,   94,  302,  123,   41,
   37,   38,   44,   44,   41,   42,   43,   61,   45,   40,
   47,  302,   93,   41,   38,  124,   37,   41,   44,   41,
   44,   93,   94,   60,   91,   62,  124,   40,   37,   38,
   51,   91,   40,   42,   43,   59,   45,  302,   47,   37,
   38,   41,   44,   41,   42,   43,   41,   45,   40,   47,
   59,   60,  124,   62,   40,   37,   38,   94,  302,   41,
   42,   43,   60,   45,   62,   47,   37,   38,   40,   93,
   94,   42,   43,   40,   45,   40,   47,  125,   60,  302,
   62,   41,  103,   41,   44,   94,   44,  124,  302,   60,
  123,   62,   41,   41,  297,  284,   94,  278,  302,   59,
  124,   40,   58,   38,   41,  123,   41,  276,   43,   44,
   45,   37,   94,   41,   58,  124,   42,   43,   58,   45,
  125,   47,    0,   94,   59,   60,  124,   62,  149,   59,
  151,   41,   59,   93,   94,  295,  296,  295,  296,   41,
  274,   41,  124,   41,  302,  125,   59,   59,  274,   41,
   54,  172,   44,  124,  293,  294,   26,   43,   93,   94,
   -1,  227,   -1,  184,  124,  299,  300,   59,  302,  303,
  304,  305,   -1,  299,  300,   -1,  302,  303,  304,  305,
  289,  290,  291,  292,  293,  294,  295,  296,   -1,  124,
   -1,  289,  290,  291,  292,  293,  294,   -1,   -1,   -1,
   -1,   93,   -1,   38,   -1,   -1,   41,   -1,   43,   44,
   45,   -1,   -1,  294,   -1,   -1,   -1,  289,  290,  291,
  292,  293,  294,   -1,   59,   60,   -1,   62,  295,  296,
   -1,   -1,  124,   -1,   -1,  302,   -1,   -1,   -1,   -1,
   37,   38,   -1,   -1,   -1,   42,   43,   -1,   45,   -1,
   47,   -1,  289,  290,  291,  292,  293,  294,   93,   94,
   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,   -1,  293,
  294,  260,  261,  262,  263,  264,  265,  266,  267,  268,
  289,  290,  291,  292,  293,  294,   -1,   -1,   -1,  124,
   -1,  289,  290,  291,  292,  293,  294,   94,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  289,  290,  291,
  292,  293,  294,  302,   -1,   -1,   -1,   -1,  289,  290,
  291,  292,  293,  294,   -1,   38,   -1,  124,   41,   -1,
   -1,   44,   -1,  293,  294,   -1,   -1,   -1,   -1,   38,
   -1,   -1,   41,   -1,   -1,   44,   59,   60,   -1,   62,
   -1,   -1,   -1,   -1,  289,  290,  291,  292,  293,  294,
   59,   60,   38,   62,   -1,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,  125,   -1,   -1,   38,   -1,   -1,   41,
   93,   94,   44,   59,   60,   38,   62,   38,   41,   -1,
   41,   44,   -1,   44,   93,   94,   -1,   59,   60,   -1,
   62,  293,  294,   -1,   37,   38,   59,   -1,   59,   42,
   43,  124,   45,   -1,   47,   -1,   40,   93,   94,   -1,
   44,   -1,   46,   -1,   -1,  124,   -1,   60,   37,   62,
   -1,   93,   94,   42,   43,   59,   45,   61,   47,   -1,
   93,   94,   93,   94,   -1,   -1,   -1,   -1,  124,   -1,
   -1,   60,   -1,   62,  289,  290,  291,  292,  293,  294,
   -1,   94,  124,   -1,   37,   38,   -1,   91,   -1,   42,
   43,  124,   45,  124,   47,   37,   38,   -1,   -1,   -1,
   42,   43,   -1,   45,   -1,   47,   -1,   60,   -1,   62,
   -1,  124,  289,  290,  291,  292,  293,   37,   60,   -1,
   62,   -1,   42,   43,   -1,   45,   -1,   47,  260,  261,
  262,  263,  264,  265,  266,  267,  268,   -1,  270,   -1,
   60,   94,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
    0,    1,   -1,   -1,   16,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   13,   26,   -1,   -1,   -1,   18,   19,
  302,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   43,   32,   -1,   -1,   -1,   36,   49,   -1,   -1,
   72,   53,   -1,   -1,   -1,   -1,  289,  290,  291,  292,
  293,  294,   -1,   -1,   54,   -1,   -1,   -1,   -1,   -1,
  289,  290,  291,  292,  293,  294,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   73,   -1,   -1,   -1,   -1,  110,   -1,
   80,   -1,   -1,  289,  290,  291,  292,  293,  294,   -1,
   -1,  103,   -1,   -1,   -1,   -1,   -1,  289,  290,  291,
  292,  293,  294,   -1,   -1,   -1,  289,  290,  289,  290,
  293,  294,  293,  294,   -1,  123,   -1,  125,  126,   35,
   -1,   -1,   -1,   -1,   -1,   -1,  289,  290,  291,  292,
  142,  143,   -1,  145,   50,   -1,   -1,  149,   -1,  151,
   -1,   -1,  174,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  289,  290,  291,  292,   -1,   -1,   72,   -1,   -1,   75,
   76,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  169,
   -1,   -1,  184,  123,   -1,   -1,  126,  209,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  289,  290,  291,  292,
   -1,  223,   -1,  225,  110,   -1,   -1,  289,  290,  291,
  292,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  240,  241,
   -1,   -1,   -1,  245,   -1,  227,  132,  133,  134,   -1,
   -1,  291,  292,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  147,  148,   -1,   -1,   -1,  152,   -1,   -1,  155,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  165,
  270,   -1,  260,  261,  262,  263,  264,  265,  266,  267,
  268,   -1,  270,  271,  272,  273,  274,  275,   -1,  277,
   -1,   -1,   -1,  281,  282,  283,   -1,   -1,   -1,   -1,
  288,   -1,  302,   -1,   -1,   -1,  202,  295,  296,  205,
   -1,   -1,  257,  258,  302,  260,  261,  262,  263,  264,
  265,  266,  267,  268,  269,  270,  222,   -1,  224,   -1,
  260,  261,  262,  263,  264,  265,  266,  267,  268,   -1,
  270,  271,  272,  273,  274,  275,   -1,  277,   -1,   52,
   -1,  281,  282,  283,   -1,   -1,   -1,  302,  288,   62,
   63,   64,   65,   -1,   -1,  295,  296,   -1,   -1,   -1,
   -1,   -1,  302,   -1,   -1,   -1,   -1,   -1,   81,   -1,
   -1,   -1,   -1,   86,   87,   88,   89,   90,   91,   92,
   93,   94,   95,   96,   97,   98,   99,  100,  101,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  132,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  163,   -1,   -1,   -1,   -1,   -1,   -1,  170,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  182,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  211,   -1,
  213,
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
"$$1 :",
"func_dcl : typee ID $$1 '(' opt_arguments ')' func_end",
"func_end : sc",
"func_end : block",
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
"assignment : variable_",
"method_call : ID '(' opt_parameters ')'",
"opt_parameters :",
"opt_parameters : parameters",
"parameters : expr",
"parameters : parameters ',' expr",
"$$2 :",
"cond_stmt : IF '(' expr ')' $$2 block end_if",
"$$3 :",
"$$4 :",
"cond_stmt : SWITCH '(' ID ')' OF ':' $$3 '{' opt_cases DEFAULT ':' $$4 block '}'",
"end_if :",
"$$5 :",
"end_if : ELSE $$5 block",
"opt_cases :",
"$$6 :",
"opt_cases : opt_cases CASE int_const ':' $$6 block",
"$$7 :",
"$$8 :",
"$$9 :",
"loop_stmt : FOR '(' opt_var_dcls $$7 sc expr $$8 sc assignment ')' $$9 block",
"$$10 :",
"loop_stmt : REPEAT $$10 block UNTIL '(' expr ')' sc",
"$$11 :",
"loop_stmt : FOREACH '(' ID IN ID ')' $$11 block",
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

//#line 251 "parser.yacc"




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
//#line 701 "Parser.java"
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
{}
break;
case 9:
//#line 70 "parser.yacc"
{ cg.functionDeclaration(val_peek(0).sval,val_peek(1).sval); }
break;
case 10:
//#line 70 "parser.yacc"
{ cg.endFunction(val_peek(5).sval); }
break;
case 16:
//#line 84 "parser.yacc"
{ cg.argument(val_peek(2).sval,val_peek(1).sval,val_peek(0).sval); }
break;
case 17:
//#line 85 "parser.yacc"
{ cg.argument(val_peek(2).sval,val_peek(1).sval,val_peek(0).sval); }
break;
case 22:
//#line 97 "parser.yacc"
{yyval.sval = "4";}
break;
case 23:
//#line 98 "parser.yacc"
{yyval.sval = "1";}
break;
case 24:
//#line 99 "parser.yacc"
{yyval.sval = "4";}
break;
case 25:
//#line 100 "parser.yacc"
{yyval.sval = "8";}
break;
case 26:
//#line 101 "parser.yacc"
{yyval.sval = "1";}
break;
case 27:
//#line 102 "parser.yacc"
{yyval.sval = "8";}
break;
case 29:
//#line 104 "parser.yacc"
{yyval.sval= "STR";}
break;
case 30:
//#line 105 "parser.yacc"
{yyval.sval = "0";}
break;
case 37:
//#line 122 "parser.yacc"
{cg.declarationSetType(val_peek(1).sval);}
break;
case 41:
//#line 131 "parser.yacc"
{ cg.declarationNoType(val_peek(0).sval,""); }
break;
case 42:
//#line 132 "parser.yacc"
{cg.declarationNoType(val_peek(2).sval,val_peek(0).sval);}
break;
case 43:
//#line 136 "parser.yacc"
{yyval.sval = "0";}
break;
case 44:
//#line 137 "parser.yacc"
{yyval.sval = val_peek(3).sval + "," + val_peek(1).sval.split(",")[0];}
break;
case 54:
//#line 153 "parser.yacc"
{ cg.retStm(""); }
break;
case 55:
//#line 154 "parser.yacc"
{ cg.retStm(val_peek(1).sval); }
break;
case 61:
//#line 162 "parser.yacc"
{cg.assign(val_peek(2).sval,val_peek(0).sval);}
break;
case 63:
//#line 166 "parser.yacc"
{ yyval.sval = cg.funcCall(val_peek(3).sval,val_peek(1).sval); }
break;
case 64:
//#line 169 "parser.yacc"
{yyval.sval = "";}
break;
case 65:
//#line 170 "parser.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 66:
//#line 173 "parser.yacc"
{yyval.sval = cg.arr_calc_addr(val_peek(0).sval);}
break;
case 67:
//#line 174 "parser.yacc"
{yyval.sval = val_peek(2).sval + "," + cg.arr_calc_addr(val_peek(0).sval);}
break;
case 68:
//#line 177 "parser.yacc"
{cg.ifState(val_peek(1).sval);}
break;
case 70:
//#line 178 "parser.yacc"
{cg.switch_start(val_peek(3).sval);}
break;
case 71:
//#line 178 "parser.yacc"
{cg.switch_default();}
break;
case 72:
//#line 178 "parser.yacc"
{cg.switch_end();}
break;
case 73:
//#line 181 "parser.yacc"
{cg.endIfState();}
break;
case 74:
//#line 182 "parser.yacc"
{ cg.startElse(); }
break;
case 75:
//#line 182 "parser.yacc"
{cg.endElse();}
break;
case 77:
//#line 187 "parser.yacc"
{cg.switch_case(val_peek(1).sval);}
break;
case 78:
//#line 187 "parser.yacc"
{cg.switch_case_end();}
break;
case 79:
//#line 190 "parser.yacc"
{cg.for_start();}
break;
case 80:
//#line 190 "parser.yacc"
{cg.saveInTemp=true;}
break;
case 81:
//#line 190 "parser.yacc"
{cg.for_middle(val_peek(4).sval);}
break;
case 82:
//#line 190 "parser.yacc"
{cg.for_end();}
break;
case 83:
//#line 191 "parser.yacc"
{cg.repeat_start();}
break;
case 84:
//#line 191 "parser.yacc"
{cg.repeat_end(val_peek(2).sval);}
break;
case 85:
//#line 192 "parser.yacc"
{cg.foreach_start(val_peek(3).sval,val_peek(1).sval);}
break;
case 86:
//#line 192 "parser.yacc"
{cg.foreach_end(val_peek(5).sval,val_peek(3).sval);}
break;
case 87:
//#line 195 "parser.yacc"
{ cg.jumpToLabel(val_peek(0).sval); }
break;
case 88:
//#line 198 "parser.yacc"
{ cg.setLabel(val_peek(1).sval); }
break;
case 89:
//#line 201 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("+",val_peek(2).sval,val_peek(0).sval);}
break;
case 90:
//#line 202 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("-",val_peek(2).sval,val_peek(0).sval);}
break;
case 91:
//#line 203 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("*",val_peek(2).sval,val_peek(0).sval);}
break;
case 92:
//#line 204 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("/",val_peek(2).sval,val_peek(0).sval);}
break;
case 93:
//#line 205 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("%",val_peek(2).sval,val_peek(0).sval);}
break;
case 94:
//#line 206 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("|",val_peek(2).sval,val_peek(0).sval);}
break;
case 95:
//#line 207 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("&",val_peek(2).sval,val_peek(0).sval);}
break;
case 96:
//#line 208 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("^",val_peek(2).sval,val_peek(0).sval);}
break;
case 97:
//#line 209 "parser.yacc"
{yyval.sval = cg.arithmeticOperand(">",val_peek(2).sval,val_peek(0).sval);}
break;
case 98:
//#line 210 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("<",val_peek(2).sval,val_peek(0).sval);}
break;
case 99:
//#line 211 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("&&",val_peek(2).sval,val_peek(0).sval);}
break;
case 100:
//#line 212 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("||",val_peek(2).sval,val_peek(0).sval);}
break;
case 101:
//#line 213 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("==",val_peek(2).sval,val_peek(0).sval);}
break;
case 102:
//#line 214 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("!=",val_peek(2).sval,val_peek(0).sval);}
break;
case 103:
//#line 215 "parser.yacc"
{yyval.sval = cg.arithmeticOperand(">=",val_peek(2).sval,val_peek(0).sval);}
break;
case 104:
//#line 216 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("<=",val_peek(2).sval,val_peek(0).sval);}
break;
case 105:
//#line 217 "parser.yacc"
{yyval.sval = val_peek(1).sval;}
break;
case 106:
//#line 218 "parser.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 107:
//#line 219 "parser.yacc"
{yyval.sval=val_peek(0).sval;}
break;
case 109:
//#line 221 "parser.yacc"
{yyval.sval = cg.arithmeticOperand("*",val_peek(0).sval,"#-1");}
break;
case 110:
//#line 222 "parser.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 113:
//#line 227 "parser.yacc"
{yyval.sval = val_peek(1).sval+",#"+val_peek(0).sval;}
break;
case 116:
//#line 232 "parser.yacc"
{cg.arithmeticOperand("-",val_peek(0).sval,val_peek(0).sval,"1");}
break;
case 117:
//#line 233 "parser.yacc"
{cg.arithmeticOperand("+",val_peek(0).sval,val_peek(0).sval,"1");}
break;
case 118:
//#line 234 "parser.yacc"
{cg.arithmeticOperand("-",val_peek(1).sval,val_peek(1).sval,"1");}
break;
case 119:
//#line 235 "parser.yacc"
{cg.arithmeticOperand("+",val_peek(1).sval,val_peek(1).sval,"1");}
break;
case 120:
//#line 238 "parser.yacc"
{yyval.sval = "#" + (new Integer(val_peek(0).ival).toString());}
break;
case 122:
//#line 240 "parser.yacc"
{yyval.sval = "#" + (new Integer((int)val_peek(0).sval.charAt(0)).toString());}
break;
case 123:
//#line 241 "parser.yacc"
{yyval.sval = "#" + val_peek(0).sval;}
break;
case 124:
//#line 242 "parser.yacc"
{yyval.sval = val_peek(0).sval;}
break;
case 125:
//#line 245 "parser.yacc"
{yyval.sval=new Integer(val_peek(0).ival).toString();}
break;
case 126:
//#line 246 "parser.yacc"
{yyval.sval=val_peek(0).sval;}
break;
//#line 1158 "Parser.java"
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