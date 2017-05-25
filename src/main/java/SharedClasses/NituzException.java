package SharedClasses;

public class NituzException extends Exception {
    private int code;
    
    public NituzException(int code,String detail){
        super(detail);
        this.code = code;
    }

    public String toString() {
        return "ERROR:"+code+" "+super.getMessage();
    }
}
