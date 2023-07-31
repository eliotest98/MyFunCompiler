package ast;

public class BoolConstNode extends AbstractSyntaxNode {

    private AbstractSyntaxNode child;

    public BoolConstNode(AbstractSyntaxNode child) {
        this.child = child;
        child.setParent(this);
    }

    public AbstractSyntaxNode getChild() {
        return child;
    }

}
