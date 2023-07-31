package ast;

public class LparRparNode extends ExprNode {

    private ExprNode child;

    public LparRparNode(ExprNode child) {
        this.child = child;
        child.setParent(this);
    }

    public ExprNode getChild() {
        return child;
    }

}
