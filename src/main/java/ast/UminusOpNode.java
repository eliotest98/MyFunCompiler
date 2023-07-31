package ast;

public class UminusOpNode extends ExprNode {

    private ExprNode child;

    public UminusOpNode(ExprNode child) {
        this.child = child;
        child.setParent(this);
    }

    public ExprNode getChild() {
        return child;
    }

}
