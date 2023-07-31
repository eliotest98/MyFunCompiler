package ast;

public class TypeNode extends AbstractSyntaxNode {

    private AbstractSyntaxNode child;

    public TypeNode(AbstractSyntaxNode child) {
        this.child = child;
        child.setParent(this);
    }

    public AbstractSyntaxNode getChild() {
        return child;
    }

}