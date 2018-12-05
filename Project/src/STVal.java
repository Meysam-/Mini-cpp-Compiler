import java.util.ArrayList;

/**
 * Created by Mohammad on 6/17/2016.
 */
public class STVal {
    ArrayList<Integer> arrLength = null;
    ArrayList<Integer> arrOffset = null;
    int size;
    int dim=0;
    boolean isMethod;

    public STVal(int typeSize,int size) {
        this.dim = typeSize;
        this.size=size;
        isMethod = false;
    }

    public STVal(){
        arrLength = new ArrayList<Integer>();
        arrOffset = new ArrayList<Integer>();
        isMethod = false;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int typeSize) {
        this.dim = typeSize;
    }

    public ArrayList<Integer> getArrLengths() {
        return arrLength;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        if(arrOffset != null){
            for(int i = 0; i < arrOffset.size();i++){
                arrOffset.set(i,arrOffset.get(i)*size);
            }
        }
    }


}
