import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Meysam & Mohammad on 6/3/2016.
 */
public class CG
{
    TacCode[] code;
    int pc;
    int t;
    SymbolTable symbolTable;
    int label;
    String temp1,temp2;
    Stack<Integer> labelStack;
    ArrayList<Integer> dclNoTypeAssigned;
    ArrayList<TacCode> tempTacCodes;
    boolean saveInTemp=false;
    CG(){
        code = new TacCode[500];
        dclNoTypeAssigned=new ArrayList<Integer>();
        labelStack=new Stack<Integer>();
        tempTacCodes=new ArrayList<TacCode>();
        symbolTable = new SymbolTable();
        pc = 0;
        t = 0;
        label=0;
    }

    void arithmeticOperand(String op,String res,String a , String b )
    {
        if(a.split(",").length>2)//it's array!
            a=arr_calc_addr(a);
        else
            a=a.split(",")[0];
        if(b.split(",").length>2)//it's array!
            b=arr_calc_addr(b);
        else
            b=b.split(",")[0];

        if(saveInTemp)
            tempTacCodes.add(new TacCode(op,res.split(",")[0],a,b));
        else{
            code[pc] = new TacCode(op,res.split(",")[0],a,b);
            pc++;
        }
    }

    String arithmeticOperand(String op,String a , String b )
    {
        t++;
        if(a.split(",").length>2)//it's array!
            a=arr_calc_addr(a);
        else
            a=a.split(",")[0];
        if(b.split(",").length>2)//it's array!
            b=arr_calc_addr(b);
        else
            b=b.split(",")[0];

        if(saveInTemp)
            tempTacCodes.add(new TacCode(op,"$t" + t,a,b));
        else{
            code[pc] = new TacCode(op,"$t" + t,a,b);
            pc++;
        }
        return "$t"+t;
    }

    void assign(String a , String b)
    {
        /*if(symbolTable.lookup(a)==null)
            m2Error(a + " not declared in this scope");
        if(b.matches("[a-zA-Z_] [0-9a-zA-Z_]*") && symbolTable.lookup(b)==null)
            m2Error(b + " not declared in this scope");*/

        if(a.split(",").length>2)//it's array!
            a=arr_calc_addr(a);
        else
            a=a.split(",")[0];

        if(b.split(",").length>2)//it's array!
            b=arr_calc_addr(b);
        else
            b=b.split(",")[0];

        if(saveInTemp)
            tempTacCodes.add(new TacCode("=",a,b));
        else {
            code[pc] = new TacCode("=", a, b);
            pc++;
        }
    }

    void declarationNoType(String name,String value) {
        if (symbolTable.lookup(name.split(",")[0]) != null)
            m2Error("redeclare: " + name.split(",")[0]);

        String a[] = name.split(",#");
        if (a.length > 2) {//is Array!!
            STVal stVal = new STVal();
            int diim=1;
            for (int i = 2; i < a.length; i++) {
                int numm=Integer.parseInt(a[i]);
                stVal.getArrLengths().add(numm);
                diim*=numm;
            }
            stVal.setDim(diim);
            for(int  i = 0;i<stVal.arrLength.size();i++){
                int temp = 1;
                for(int j = i+1;j<stVal.arrLength.size();j++){
                    temp *= stVal.arrLength.get(j);
                }
                stVal.arrOffset.add(temp);
            }
            symbolTable.insert(a[0], stVal);
            code[pc] = new TacCode("ARR","",a[0],Integer.toString(diim));
            dclNoTypeAssigned.add(pc);
            pc++;

        } else {

            symbolTable.insert(a[0], 0);
            code[pc] = new TacCode("VAR","",a[0],value.split(",")[0]);
            dclNoTypeAssigned.add(pc);
            pc++;
        }
    }
    void declarationSetType(String type)
    {
        if(!type.equals("STR")) {
            int sizeType = Integer.parseInt(type);

            for (int i : dclNoTypeAssigned) {
                code[i].opr1 = type;
                symbolTable.table.get(code[i].opr2).setSize(sizeType);
            }
        }else{

            for (int i : dclNoTypeAssigned) {
                code[i].opCode="STR";
                code[i].opr1=code[i].opr2;
                code[i].opr2=code[i].opr3;
                code[i].opr3="";
            }
        }
        this.dclNoTypeAssigned.clear();
    }

    void functionDeclaration(String name,String sizeOfOutput){
        if (symbolTable.lookup(name) != null)
            m2Error("redeclare: " + name);
        STVal stVal = new STVal(0,Integer.parseInt(sizeOfOutput));
        stVal.isMethod = true;
        symbolTable.insert(name, stVal);
        code[pc] = new TacCode("FUN",name,"");
        if(!sizeOfOutput.equals("0"))
            code[pc].opr2 = sizeOfOutput;
        pc++;
    }

    void endFunction(String name){
        code[pc] = new TacCode("END",name,"");
        pc++;
    }

    void argument(String size,String name,String dim){
        code[pc] = new TacCode("ARG");
        code[pc].opr1 = size;
        String a[] = dim.split(",#");
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
        pc++;
    }

    void retStm(String retVal){
        code[pc] = new TacCode("RET",retVal,"");
        pc++;
    }

    void ifState(String id)
    {
        code[pc]=new TacCode("JNZ","Label"+label,id);
        labelStack.push(label++);
        pc++;
    }

    void endIfState()
    {
        code[pc]=new TacCode("LB","Label"+Integer.toString(labelStack.pop()),"");
        pc++;
    }

    void startElse()
    {
        int ii=labelStack.pop();
        code[pc]=new TacCode("JMP","Label"+label,"");
        labelStack.push(label++);
        pc++;

        code[pc]=new TacCode("LB","Label"+Integer.toString(ii),"");
        pc++;
    }

    void endElse()
    {
        code[pc]=new TacCode("LB","Label"+Integer.toString(labelStack.pop()),"");
        pc++;
    }

    void for_start()
    {
        code[pc]=new TacCode("LB","Label"+label,"");
        labelStack.push(label++);
        pc++;
    }

    void for_middle(String id )
    {
        code[pc]=new TacCode("JNZ","Label"+label,id);
        labelStack.push(label++);
        pc++;
        saveInTemp=false;
    }

    void for_end()
    {
        for(TacCode tc:tempTacCodes) {
            code[pc]=tc;
            pc++;
        }
        tempTacCodes.clear();

        int ll=labelStack.pop();
        code[pc]=new TacCode("JMP","Label"+Integer.toString(labelStack.pop()),"");
        pc++;
        code[pc]=new TacCode("LB","Label"+Integer.toString(ll),"");
        pc++;
    }

    void repeat_start()
    {
        code[pc]=new TacCode("LB","Label"+label,"");
        labelStack.push(label++);
        pc++;
    }

    void repeat_end(String id)
    {
        code[pc]=new TacCode("JZ","Label"+Integer.toString(labelStack.pop()),id);
        pc++;
    }

    void foreach_start(String id,String arr)
    {
        t++;
        code[pc] = new TacCode("=","$t" + t,"#0");
        pc++;

        labelStack.push(t);

        code[pc]=new TacCode("LB","Label"+label,"");
        labelStack.push(label++);
        pc++;
        String val = arithmeticOperand("+","@" + arr,"$t"+t);
        code[pc]=new TacCode("=",id,"@" + val);
        pc++;
    }

    void foreach_end(String id,String arr)
    {
        int dim=symbolTable.lookup(arr).getDim();
        if(dim==0)
            m2Error(arr+" is not array");
        int size=symbolTable.lookup(arr).getSize();

        int ll=labelStack.pop();
        int tt=labelStack.pop();
        code[pc]=new TacCode("+","$t" + Integer.toString(tt),"$t" + Integer.toString(tt),Integer.toString(size));
        pc++;
        t++;
        code[pc]=new TacCode(">","$t"+Integer.toString(t),"$t"+Integer.toString(tt),Integer.toString(dim*size));
        pc++;
        code[pc]=new TacCode("JZ","Label"+Integer.toString(ll),"$t"+Integer.toString(t));
        pc++;
    }

    /*void isDeclaredGlobaly(String id)
    {
        if(symbolTable.lookup(id)==null)
            m2Error( id + " not declared!!");

    }*/

    void m2Error(String err)
    {
        System.err.println("--ridi: " +err);
        System.exit(0);
    }

    String arr_calc_addr(String arr)
    {
        String a[] = arr.split(",");//a[0] is id
        if(a.length == 1) return arr;
        arr_is_valid(a);

        int address=0;
        if(!arr.equals("0") && a.length > 2){
            String addr = "@" + a[0];
            for(int i = 2; i < a.length;i++){
                int k=symbolTable.lookup(a[0]).arrOffset.get(i-2);
                String z = arithmeticOperand("*",a[i],"#" + new Integer(k).toString());
                addr = arithmeticOperand("+",addr,z);
            }
            return "@" + addr;
        }
        return a[0];
    }

    void arr_is_valid(String a[])
    {
        if(a.length>2 && symbolTable.lookup(a[0]).getDim()==0)
            m2Error("goh nakhor");

        if(a.length>2){
        if(symbolTable.lookup(a[0]).getDim()>0 && a.length-2!=symbolTable.lookup(a[0]).arrLength.size())
            m2Error(a[0]+" is "+(a.length-2)+"d array");
        for(int i=2;i<a.length;i++) {
            if (a[i].charAt(0) == '#' && symbolTable.table.get(a[0]).arrLength.get(i-2)<=Integer.parseInt(a[i].substring(1)))
                m2Error("Index Out of Bounds for "+a[0]);
        }
        }

    }

    void setLabel(String id){
        code[pc] = new TacCode("LB",id,"");
        pc++;
    }

    void jumpToLabel(String id){
        code[pc] = new TacCode("JMP",id,"");
        pc++;
    }

    String funcCall(String name,String param){
        if (symbolTable.lookup(name) == null)
            m2Error("function not declared: " + name);
        if (!symbolTable.lookup(name).isMethod)
            m2Error("this id is not a function: " + name);
        code[pc] = new TacCode("CALL",name,param);
        pc++;
        return name;
    }

    void switch_start(String id)
    {
        temp1=id;
        temp2=Integer.toString(label);
        label++;

        labelStack.push(label);//for first case
        label++;
    }

    void switch_case(String id) {
        code[pc]=new TacCode("LB","Label"+labelStack.pop(),"");
        pc++;
        code[pc] = new TacCode("-", "$t" + t,temp1,id);
        pc++;
        code[pc] = new TacCode("JNZ", "Label" + label,"$t" + t);
        labelStack.push(label);
        label++;
        pc++;
    }
    void switch_case_end() {
        code[pc] = new TacCode("JMP", "Label" + temp2,"");
        pc++;
    }

    void switch_default(){
        t++;
        code[pc]=new TacCode("LB","Label"+labelStack.pop(),"");
        pc++;
    }
    void switch_end(){
        code[pc]=new TacCode("LB","Label"+temp2,"");
        pc++;
    }
}
