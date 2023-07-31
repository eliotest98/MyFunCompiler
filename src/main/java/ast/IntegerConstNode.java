package ast;

public class IntegerConstNode extends ExprNode {

    private String value;

    public IntegerConstNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}