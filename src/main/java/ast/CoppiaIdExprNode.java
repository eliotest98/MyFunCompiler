package ast;

public class CoppiaIdExprNode extends AbstractSyntaxNode {
    private IdentifierNode first;
    private ExprNode second;

    public CoppiaIdExprNode(IdentifierNode first, ExprNode second) {
        this.first = first;
        this.second = second;
        first.setParent(this);
        second.setParent(this);
    }

    public CoppiaIdExprNode(IdentifierNode first) {
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
