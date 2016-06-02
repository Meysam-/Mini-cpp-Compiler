/**
 * Created by Meysam Kazemi on 6/3/2016.
 */
public class CG
{
    int t =0 ;
   // SymbolTable symbolTable = new SymbolTable();
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
