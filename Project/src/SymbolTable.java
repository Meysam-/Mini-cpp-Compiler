import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Pouya Payandeh on 5/15/2016.
 */
public class SymbolTable
{
    Map<String , Integer> table;

    public SymbolTable()
    {
        table = new TreeMap<String, Integer>();
    }
    public int getValue(String id)
    {
        if(table.containsKey(id))
           return table.get(id);
        table.put(id,0);
        return 0;
    }
    public void setValue(String id,Double Value)
    {
        table.put(id,Value.intValue());
    }
}
