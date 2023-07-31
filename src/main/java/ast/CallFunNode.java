package ast;

public class CallFunNode extends ExprNode implements StatNode {

    private IdentifierNode identifierNode;
    private ExprListNode exprListNode;

    public CallFunNode(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
        identifierNode.setParent(this);
    }

    public CallFunNode(IdentifierNode identifierNode, ExprListNode exprListNode) {
        this.identifierNode = identifierNode;
        this.exprListNode = exprListNode;
        identifierNode.setParent(this);
        exprListNode.setParent(this);
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public ExprListNode getExprListNode() {
        return exprListNode;
    }
}
