package ast;

public class NotOpNode extends ExprNode {

    private ExprNode child;

    public NotOpNode(ExprNode child) {
        this.child = child;
        child.setParent(this);
    }

    public ExprNode getChild() {
        return child;
    }

}
