package codeOct29.src.edu.auburn;

public class MessageModel {

    public static final int GET_PRODUCT = 100;
    public static final int PUT_PRODUCT = 101;
    public static final int GET_CUSTOMER = 102;
    public static final int PUT_CUSTOMER = 103;
    public static final int GET_PURCHASE = 104;
    public static final int PUT_PURCHASE = 105;
    

    public int code;
    public String data;

    public MessageModel() {

    }

    public MessageModel(int code, String data) {
        this.code = code;
        this.data = data;
    }
}
