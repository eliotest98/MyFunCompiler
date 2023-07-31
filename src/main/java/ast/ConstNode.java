package ast;

public class ConstNode extends AbstractSyntaxNode {

    private AbstractSyntaxNode child;
    private BoolConstNode boolConstNode;

    public ConstNode(AbstractSyntaxNode child) {
        this.child = child;
        if (child instanceof BoolConstNode) {
            this.boolConstNode = (BoolConstNode) child;
            boolConstNode.setParent(this);
        }
        child.setParent(this);
    }

    public AbstractSyntaxNode getChild() {
        return child;
    }

    public void setChild(AbstractSyntaxNode child) { this.child = child; };

    public BoolConstNode getBoolConstNode() {
        return boolConstNode;
    }
}
