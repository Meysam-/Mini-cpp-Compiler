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
   33,   33,   33,   33,   33,   32,   32,   32,   32,   32,
   31,   31,
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
    3,    1,    1,    1,    2,    2,    4,    2,    4,    1,
    2,    2,    2,    2,    2,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    0,   20,   21,   22,   23,   24,   25,   27,
   28,   29,    0,    0,   26,    0,    3,    4,    6,    7,
    8,    0,    0,    0,    0,    0,    5,    2,    0,    0,
    0,    0,    0,   37,    0,  100,    0,    0,    0,   41,
    0,  102,  103,    0,    0,  101,    0,  104,  105,    0,
   11,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   38,    0,  106,  107,    0,  108,  110,  109,    0,    0,
    0,    0,    0,   92,   94,   16,    0,    0,   31,    0,
    0,    0,    0,   99,    0,    0,    0,   96,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   44,   18,   19,    0,
   30,   32,    9,   10,   42,    0,    0,    0,    0,   91,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   77,   78,   79,    0,    0,   16,   97,
   59,    0,   17,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   43,    0,    0,   46,    0,    0,
   49,   50,    0,   54,    0,    0,   51,    0,   55,   56,
    0,    0,    0,    0,    0,    0,   73,   74,   45,    0,
   47,   48,   53,   52,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   65,
   67,    0,    0,    0,   72,   71,    0,    0,    0,    0,
    0,   70,   69,    0,  111,  112,    0,    0,    0,   66,
   68,
};
final static short yydgoto[] = {                          1,
   55,   28,   17,   18,   19,   20,   21,   56,   53,  109,
   54,  106,   57,  189,   33,   34,   72,   73,   45,  138,
  158,  159,   74,  161,  162,  163,  164,  118,  119,  217,
  227,   75,   36,
};
final static short yysindex[] = {                         0,
  832,  -50, -293,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -288,  -50,    0,  -30,    0,    0,    0,    0,
    0, -101, -271,   -9,  -88,  -98,    0,    0,  -98,  -98,
   12,  -98,   18,    0,  -58,    0,  -30,  -50,  137,    0,
   18,    0,    0,  -50,  -32,    0,  -98,    0,    0,  -22,
    0, -220,   46,   40,  -30,  -98,  597,   65,  -22,  -98,
    0,   61,    0,    0,   70,    0,    0,    0,  -22,  -22,
  -22, -209,  391,    0,    0,    0,  -51,  -50,    0,  -30,
  -30,  -51,   60,    0,  -50,  -22,   43,    0,   71,  -22,
  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,  -22,
  -22,  -22,  -22,  -22,  -22,   24,    0,    0,    0, -187,
    0,    0,    0,    0,    0,   76,  391,   80,   79,    0,
  682,  682,  181,  181,  566,  452,  194,  606,  590,  181,
  181,   43,   43,    0,    0,    0,   35,  644,    0,    0,
    0,  -22,    0,  -33,  -30,  -30,   99,  104,  106,  107,
  108,   15, -149,  -38,    0,  -30,  -48,    0,  -30,  -30,
    0,    0,  -30,    0,   24,  391,    0,   98,    0,    0,
  -50,  -22, -148,  137, -147, -128,    0,    0,    0,  -22,
    0,    0,    0,    0,  118,  125,  120,  137,  -30, -129,
  129,  391,  -30,   15, -106,  -22, -123,  -22,    0,  -95,
  124,   98,  145,  136,   15,   66,  -22,   15,  -30,    0,
    0,  -48,  268,  147,    0,    0, -183,   15,   15,  141,
 -278,    0,    0,   15,    0,    0,  143,   78,   15,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  490,    0,  146,    0,  -25,    0,    0,  163,    0,    0,
  149,    0,    0,  163,    6,    0,    0,    0,    0,    0,
    0,    0,    0,  165,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   -5,    0,    0,    0,    0,    0,
    0,   32,  -14,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  179,  399,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   19,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   20,    0,  180,    0,
  486,  546,  469,  478,  553,  -35,   83,  231,  548,  506,
  523,  411,  424,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -45,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   88,   93,    0,    0,    0,    0,
    0,    0,    0,  166,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  168,    0,    0,
    0,  -37,    0,    0,    0,    0,    0,    0,    0,  714,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  333,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
   14,  864,    0,    0,    0,    0,    0,  883,  185,  735,
    0,   96,   64,    0,  216,  198,  758,  944,    0,    0,
    0,   45,  112,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=1151;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         70,
   41,   86,   50,   58,   24,   86,   71,   27,   86,   25,
   70,   69,  180,   60,   16,   41,  225,   71,   39,  178,
  226,   58,   69,   86,   32,   27,   37,   32,   27,   40,
   38,   41,   41,   39,   39,   41,   41,   41,   41,   41,
   41,   41,   98,   98,   40,   41,   98,   98,   98,   98,
   98,   44,   98,   41,   41,   41,   41,   86,   59,   14,
   62,   47,   14,   62,   98,   98,   98,   98,   93,   93,
   81,  107,   93,   93,   93,   93,   93,   76,   93,  105,
   26,   48,   49,   78,  103,   41,   77,   41,   41,  104,
   93,   93,   32,   93,  220,  221,  105,   98,   98,   98,
   85,  103,  101,   32,  102,   82,  104,  105,   98,   86,
  139,  120,  103,  101,  137,  102,  140,  104,   41,   99,
  141,  100,  142,   80,   93,   93,   80,  143,   15,   98,
   99,   15,  100,   63,  105,   98,   63,  107,  171,  103,
  101,   80,  102,  172,  104,  173,  174,  175,  177,  187,
  190,  156,  115,   97,  191,   93,   27,   99,  193,  100,
  195,  105,   98,  197,   97,  194,  103,  101,  198,  102,
  201,  104,  105,   98,  203,   80,  209,  103,  101,  205,
  102,  206,  104,   96,   99,  208,  100,  219,  211,   29,
   30,   97,   29,   30,   96,   99,   31,  100,  224,   40,
  229,   81,  230,   12,   35,   13,   80,   36,    4,    5,
    6,    7,    8,    9,   10,   11,   12,  105,   97,   60,
   61,   96,  103,  101,   33,  102,   34,  104,   58,   97,
  105,   98,   48,   49,  165,  103,  101,  188,  102,   62,
  104,   41,   48,   49,   61,   26,   26,   15,   96,  160,
   62,  214,   26,   99,   86,  100,    0,   29,   30,   96,
    0,   63,   64,    0,   65,   66,   67,   68,   29,   30,
    0,   82,   63,   64,   82,   65,   66,   67,   68,   41,
   41,   41,   41,   41,   41,   41,   41,   97,    0,   82,
   98,   98,   98,   98,   98,   98,   98,   98,    0,    0,
    0,    0,    0,    0,  105,   98,    0,    0,  218,  103,
  101,    0,  102,    0,  104,    0,   93,   93,   93,   93,
   93,   93,    0,   82,   82,    0,    0,   99,    0,  100,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   90,   91,   92,   93,   94,   95,
    0,    0,    0,    0,   82,   90,   91,   92,   93,   94,
   95,   97,    0,    0,    0,    0,    0,    0,    0,   93,
   93,   80,   80,   93,   93,   93,    0,   93,    0,   93,
    0,    0,   90,   91,   92,   93,   94,   95,    0,    0,
    0,   96,   93,    0,   93,    4,    5,    6,    7,    8,
    9,   10,   11,   12,    0,   14,    0,    0,    0,   90,
   91,   92,   93,   94,   95,    0,    0,    0,    0,    0,
   90,   91,   92,   93,   94,   95,   93,  105,   98,    0,
    0,    0,  103,  101,   15,  102,   95,  104,    0,   95,
    0,   95,   95,   95,    0,    0,    0,    0,   75,    0,
   99,   75,  100,   75,   75,   75,   93,   95,   95,    0,
   95,   76,    0,    0,   76,    0,   76,   76,   76,   75,
   75,    0,   75,    0,    0,    0,    0,    0,   90,   91,
   92,   93,   76,   76,   97,   76,    0,    0,  105,   98,
    0,   95,   95,  103,  101,    0,  102,    0,  104,    0,
    0,    0,    0,   75,   75,    0,   90,    0,    0,   90,
    0,   99,   90,  100,   96,   89,   76,   76,   89,   82,
   82,   89,   95,   87,    0,    0,   87,   90,   90,   87,
   90,    0,    0,   41,   75,   41,   89,   89,    0,   89,
    0,    0,    0,   84,   87,   97,   84,   76,   41,   84,
   41,    0,   90,   91,   92,   93,   94,   95,    0,    0,
   83,   90,   90,   83,   84,   84,   83,   84,    0,    0,
   89,   89,    0,    0,    0,   96,    0,    0,   87,   87,
   41,   83,   83,   88,   83,   81,   88,    0,   81,   88,
    0,   81,   90,   85,    0,    0,   85,    0,   84,   84,
    0,   89,  105,   98,   88,    0,   81,  103,  101,   87,
  102,   85,  104,    0,    0,   83,   83,   93,   93,   93,
   93,   93,   93,    0,    0,   99,  105,  100,    0,   84,
    0,  103,  101,    0,  102,    0,  104,    0,   88,   88,
   81,   81,  105,   98,    0,   85,   83,  103,  101,   99,
  102,  100,  104,    0,    0,    0,    0,    0,    0,   97,
    0,    0,    0,    0,    0,   99,    0,  100,    0,   88,
    0,   81,    0,    0,    0,   90,   91,   92,   93,   94,
   95,    0,    0,   95,   95,   95,   95,   95,   95,   96,
    0,    0,    0,    0,    0,   75,   75,   75,   75,   75,
   75,    0,    0,    0,    0,    0,    0,    0,   76,   76,
   76,   76,   76,   76,    0,    0,    0,    0,  105,    0,
    0,   80,    0,  103,  101,    0,  102,    0,  104,    0,
    0,    0,    0,    0,    0,    0,   90,   91,   92,   93,
   94,   99,    0,  100,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,   90,   90,   90,   90,   90,    0,
    0,    0,   89,   89,   89,   89,   89,   89,  155,   32,
   87,   87,    0,    0,   87,   87,    0,    0,    0,   35,
   41,   41,    0,   35,    0,    0,   42,   43,    0,   46,
   84,   84,   84,   84,   84,   84,    0,    0,    0,    0,
    0,    0,    0,    0,   35,    0,    0,   83,   83,   83,
   83,   83,   83,   35,    0,    0,  114,   84,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   88,   88,    0,    0,   88,   88,   81,   81,   64,   64,
    0,   85,   85,    0,    0,    0,    0,    0,    0,    0,
   90,   91,   92,   93,    0,    4,    5,    6,    7,    8,
    9,   10,   11,   12,    0,   14,    0,    0,    0,    0,
    0,    0,    0,    0,   90,   91,   92,   93,    0,    0,
    0,    0,    0,   22,   23,    0,  176,    0,    0,    0,
   90,   91,   92,   93,   15,  157,   26,    0,    0,    0,
   51,    0,    4,    5,    6,    7,    8,    9,   10,   11,
   12,    0,   14,  144,  145,  146,  147,  148,   79,  149,
   52,    0,    0,  150,  151,  152,   52,  153,  200,    0,
    0,    0,    0,    0,   29,   30,    0,    0,    0,  210,
  108,  154,  215,  111,  112,  113,    0,    0,    0,    0,
    0,    0,  222,  223,    0,    0,    0,    0,  228,    0,
  110,    0,    0,  231,  212,    0,    0,  116,   92,   93,
    0,    0,   64,   64,   64,   64,   64,   64,   64,   64,
   64,    0,   64,   64,   64,   64,   64,   64,    0,   64,
    0,    0,    0,   64,   64,   64,    0,   64,    0,    0,
    0,    0,   83,    0,   64,   64,    0,  167,  169,  170,
    0,   64,   87,   88,   89,    0,    0,    0,    0,  179,
    0,    0,  181,  182,    0,    0,  183,    0,    0,  117,
    0,  184,    0,  121,  122,  123,  124,  125,  126,  127,
  128,  129,  130,  131,  132,  133,  134,  135,  136,    0,
    0,    0,  196,  185,    0,    0,  199,    0,    0,    0,
    0,    0,    0,    0,    0,  207,    0,    0,    0,    0,
    0,    0,  216,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  166,    0,  168,    2,    3,
    4,    5,    6,    7,    8,    9,   10,   11,   12,   13,
   14,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  186,    0,    0,    0,    0,
    0,    0,    0,  192,    0,    0,    0,    0,    0,   15,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  202,
    0,  204,    0,    0,    0,    0,    0,    0,    0,    0,
  213,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   46,   40,   61,   41,  298,   41,   40,   59,   44,  298,
   33,   45,   61,   46,    1,   61,  295,   40,   44,   58,
  299,   59,   45,   59,  126,   59,  298,  126,   59,   44,
   40,   37,   38,   59,  123,   41,   42,   43,   44,   45,
   46,   47,   37,   38,   59,   91,   41,   42,   43,   44,
   45,   40,   47,   59,   60,   61,   62,   93,   91,   41,
   41,   44,   44,   44,   59,   60,   61,   62,   37,   38,
   57,  123,   41,   42,   43,   44,   45,  298,   47,   37,
  126,  291,  292,   44,   42,   91,   41,   93,   94,   47,
   59,   60,  126,   62,  278,  279,   37,   38,   93,   94,
   40,   42,   43,  126,   45,   41,   47,   37,   38,   40,
  298,   41,   42,   43,   91,   45,   41,   47,  124,   60,
   41,   62,   44,   41,   93,   94,   44,   93,   41,  124,
   60,   44,   62,   41,   37,   38,   44,  123,   40,   42,
   43,   59,   45,   40,   47,   40,   40,   40,  298,  298,
  298,  138,   93,   94,  283,  124,   59,   60,   41,   62,
   41,   37,   38,  293,   94,   41,   42,   43,   40,   45,
  277,   47,   37,   38,  298,   93,   41,   42,   43,  275,
   45,   58,   47,  124,   60,   41,   62,   41,  123,  291,
  292,   94,  291,  292,  124,   60,  298,   62,   58,  298,
   58,  188,  125,   41,   59,   41,  124,   59,  259,  260,
  261,  262,  263,  264,  265,  266,  267,   37,   94,   41,
   41,  124,   42,   43,   59,   45,   59,   47,   44,   94,
   37,   38,  291,  292,  139,   42,   43,  174,   45,  273,
   47,   26,  291,  292,   47,  291,  292,  298,  124,  138,
  273,  207,  298,   60,  290,   62,   -1,  291,  292,  124,
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
  290,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  285,  286,
  287,  288,  289,  290,   -1,   -1,   -1,   -1,   37,   -1,
   -1,  125,   -1,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  285,  286,  287,  288,
  289,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  285,  286,  287,  288,  289,  290,   -1,
   -1,   -1,  285,  286,  287,  288,  289,  290,  125,  126,
  285,  286,   -1,   -1,  289,  290,   -1,   -1,   -1,   22,
  291,  292,   -1,   26,   -1,   -1,   29,   30,   -1,   32,
  285,  286,  287,  288,  289,  290,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   47,   -1,   -1,  285,  286,  287,
  288,  289,  290,   56,   -1,   -1,   82,   60,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  285,  286,   -1,   -1,  289,  290,  289,  290,  125,  126,
   -1,  289,  290,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  285,  286,  287,  288,   -1,  259,  260,  261,  262,  263,
  264,  265,  266,  267,   -1,  269,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  285,  286,  287,  288,   -1,   -1,
   -1,   -1,   -1,    1,    2,   -1,  152,   -1,   -1,   -1,
  285,  286,  287,  288,  298,  138,   14,   -1,   -1,   -1,
   37,   -1,  259,  260,  261,  262,  263,  264,  265,  266,
  267,   -1,  269,  270,  271,  272,  273,  274,   55,  276,
   38,   -1,   -1,  280,  281,  282,   44,  284,  194,   -1,
   -1,   -1,   -1,   -1,  291,  292,   -1,   -1,   -1,  205,
   77,  298,  208,   80,   81,   82,   -1,   -1,   -1,   -1,
   -1,   -1,  218,  219,   -1,   -1,   -1,   -1,  224,   -1,
   78,   -1,   -1,  229,  207,   -1,   -1,   85,  287,  288,
   -1,   -1,  259,  260,  261,  262,  263,  264,  265,  266,
  267,   -1,  269,  270,  271,  272,  273,  274,   -1,  276,
   -1,   -1,   -1,  280,  281,  282,   -1,  284,   -1,   -1,
   -1,   -1,   59,   -1,  291,  292,   -1,  144,  145,  146,
   -1,  298,   69,   70,   71,   -1,   -1,   -1,   -1,  156,
   -1,   -1,  159,  160,   -1,   -1,  163,   -1,   -1,   86,
   -1,  168,   -1,   90,   91,   92,   93,   94,   95,   96,
   97,   98,   99,  100,  101,  102,  103,  104,  105,   -1,
   -1,   -1,  189,  171,   -1,   -1,  193,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  202,   -1,   -1,   -1,   -1,
   -1,   -1,  209,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  142,   -1,  144,  257,  258,
  259,  260,  261,  262,  263,  264,  265,  266,  267,  268,
  269,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  172,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  180,   -1,   -1,   -1,   -1,   -1,  298,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  196,
   -1,  198,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  207,
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

//#line 242 "Parser.yacc"




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
//#line 680 "Parser.java"
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
