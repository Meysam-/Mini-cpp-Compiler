import java.util.ArrayList;

/**
 * Created by Meysam & Mohammad on 6/3/2016.
 */
public class CG
{

    int dclCount=0;
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
        if(symbolTable.lookup(a)==null)
            System.err.println(a +" not declared in this scope");
        if(symbolTable.lookup(b)==null)
            System.err.println(b +" not declared in this scope");

        code[pc]=new TacCode("=",a,b);
        System.out.println(code[pc]);
        pc++;
    }

    void declarationNoType(String name)
    {
        if(symbolTable.lookup(name)!=null)
            System.err.println("--redeclare: "+name);
        symbolTable.insert(name,0);
        code[pc]=new TacCode("VAR",name,"");
        pc++;
        this.dclCount++;
    }
    void declarationSetType(String type)
    {
        int sizeType=Integer.parseInt(type);

        for(int i=pc-1;i>=pc-dclCount;i--){
            code[i].opr2=type;
            symbolTable.table.get(code[i].opr1).setSize(sizeType);
            System.out.println(code[i]);
        }
        this.dclCount=0;
    }
}
