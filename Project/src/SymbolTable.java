import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Meysam Kazemi on 6/3/2016.
 */
public class SymbolTable
{
    Map<String , STVal> table;//name,typeSize

    public SymbolTable(){
        table = new TreeMap<String, STVal>();
    }

    public STVal lookup(String id){
        if(table.containsKey(id))
           return table.get(id);
        return null;
    }
    public void insert(String id,int type,int size){
        table.put(id,new STVal(type,size));
    }
    public void insert(String id,int type){
        table.put(id,new STVal(type,0));
    }
    public void insert(String id ,STVal stVal){
        table.put(id,stVal);
    }
}
