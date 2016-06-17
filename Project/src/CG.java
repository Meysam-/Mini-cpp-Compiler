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
            m2Error(a + " not declared in this scope");
        if(symbolTable.lookup(b)==null)
            m2Error(b + " not declared in this scope");

        code[pc]=new TacCode("=",a,b);
        System.out.println(code[pc]);
        pc++;
    }

    void declarationNoType(String name)
    {
        if(symbolTable.lookup(name)!=null)
            m2Error("redeclare: " + name);
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

    void argument(String size,String name,String dim){
        code[pc] = new TacCode("ARG");
        code[pc].opr1 = size;
        String a[] = dim.split(",");
        int diim = 1;
        if(!dim.equals("0")){
            for(int i = 1; i < a.length;i++){
                diim *= Integer.parseInt(a[i]);
            }
            code[pc].opr2 = new Integer(diim).toString();
            code[pc].opr3 = name;
        }
        else
            code[pc].opr2 = name;
        System.out.println(code[pc]);
        pc++;
    }

    /*void isDeclaredGlobaly(String id)
    {
        if(symbolTable.lookup(id)==null)
            m2Error( id + " not declared!!");

    }*/

    void m2Error(String err)
    {
        System.err.println("--error: " +err);
        System.exit(0);
    }
}
