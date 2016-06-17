/**
 * Created by Meysam on 6/17/2016.
 */
public class TacCode {
    String opCode;
    String opr1;
    String opr2;
    String opr3;

    TacCode(){
    }
    TacCode(String opCode){
        this.opCode = opCode;
    }
    TacCode(String opCode,String opr1,String opr2,String opr3){
        this.opCode = opCode;
        this.opr1 = opr1;
        this.opr2 = opr2;
        this.opr3 = opr3;
    }
}
