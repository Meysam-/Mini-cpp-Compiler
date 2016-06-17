import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Meysam Kazemi on 6/3/2016.
 */
public class SymbolTable
{
    Map<String , Integer> table;//name,typeSize

    public SymbolTable(){
        table = new TreeMap<String, Integer>();
    }

    public int lookup(String id){
        if(table.containsKey(id))
           return table.get(id);
        return 0;
    }
    public void insert(String id,String type){
        int sizeType=0;
        switch (type){
            case "int":
                sizeType=4;
                break;
            default:
                System.out.println("--error in type: "+type);
        }
        table.put(id,sizeType);
    }
}
