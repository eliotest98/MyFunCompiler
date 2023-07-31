package ast;

public class CoppiaIdConstNode extends AbstractSyntaxNode {
    private IdentifierNode first;
    private ConstNode second;

    public CoppiaIdConstNode(IdentifierNode first, ConstNode second) {
        this.first = first;
        this.second = second;
        first.setParent(this);
        second.setParent(this);
    }

    public CoppiaIdConstNode(IdentifierNode first) {
        this.first = first;
        this.second = null;
        first.setParent(this);
    }

    public AbstractSyntaxNode getFirst() {
        return first;
    }

    public AbstractSyntaxNode getSecond() {
        return second;
    }

}
