import java.util.ArrayList;

/**
 * Created by Meysam Kazemi on 6/3/2016.
 */
public class CG
{
 
    TacCode[] code;
    int pc;
    int t;
    SymbolTable symbolTable;
    CG(){
        code = new TacCode[500];
        symbolTable = new SymbolTable();
        pc = 0;
        t = 0;
    }
    String arithmeticOperand(String op,String a , String b )
    {
        t++;
        System.out.printf("%s %s,%s,%s \n",op,"$t"+t,a,b);
        return "$t"+t;
    }
    void assign(String a , String b)
    {
        code[++pc]=new TacCode("=",a,b);
    }
}
