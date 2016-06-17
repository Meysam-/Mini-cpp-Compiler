import java.util.ArrayList;

/**
 * Created by Meysam & Mohammad on 6/3/2016.
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
        code[pc] = new TacCode(op,"$t" + t,a,b);
        System.out.println(code[pc]);
        pc++;
        return "$t"+t;
    }
    void assign(String a , String b)
    {
        if(symbolTable.lookup(a)==0)
            System.err.println(a +" not declared in this scope");
        if(symbolTable.lookup(b)==0)
            System.err.println(b +" not declared in this scope");

        code[pc]=new TacCode("=",a,b);
        System.out.println(code[pc]);
        pc++;
    }
}
