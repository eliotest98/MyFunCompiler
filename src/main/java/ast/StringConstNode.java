package ast;

public class StringConstNode extends ExprNode {

    private String value;

    public StringConstNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}