/**
 * Created by Mohammad on 6/17/2016.
 */
public class STVal {
    public STVal(int typeSize,int size) {
        this.typeSize = typeSize;
        this.size=size;
    }

    public int getTypeSize() {
        return typeSize;
    }

    public void setTypeSize(int typeSize) {
        this.typeSize = typeSize;
    }

    int typeSize;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    int size;
}
