package enums;

public enum OperatorEnum {

    ADD("+","加法"),

    SUBTRACT("-","减法"),

    MULTIPLY("*","乘法"),

    DIVIDE("/","除法")

    ;

    private final String code;

    private final String desc;

    OperatorEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

}
