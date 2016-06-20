import java.io.*;

/**
 * Created by Meysam on 6/13/2016.
 */
public class test {
    public static void main(String[] args) {
        try {
//            Parser yyparser = new Parser(new InputStreamReader(System.in));
            Parser yyparser = new Parser(new FileReader(args[0]));
            File output = new File(args[1]);
            PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(output)));
            yyparser.yyparse();
            if(yyparser.cg.symbolTable.lookup("start") == null)
                yyparser.cg.m2Error("No Start function found");
            for(int i = 0; i < yyparser.cg.pc; i++){
                    out.write(yyparser.cg.code[i].toString() + "\n");
            }
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
