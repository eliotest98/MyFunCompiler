package ast;

public class RealConstNode extends ExprNode {

    private String value;

    public RealConstNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}