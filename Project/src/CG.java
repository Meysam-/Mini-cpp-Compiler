import java.util.ArrayList;

/**
 * Created by Meysam Kazemi on 6/3/2016.
 */
public class CG
{
    //kjhsdifhdivhdvhiodfvd
    TacCode[] code;
    int pc;
    int t;
    SymbolTable symbolTable;
    CG(){
        code = new TacCode[500];
        symbolTable = new SymbolTable();
        for(TacCode val:code){
            val = new TacCode();
        }
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
        System.out.printf("= %s,%s\n",a,b);
    }
}
