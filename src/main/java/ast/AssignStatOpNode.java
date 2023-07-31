package ast;

public class AssignStatOpNode extends AbstractSyntaxNode implements StatNode {

    private IdentifierNode identifierNode;
    private ExprNode exprNode;

    public AssignStatOpNode(IdentifierNode identifierNode, ExprNode exprNode) {
        this.identifierNode = identifierNode;
        this.exprNode = exprNode;
        identifierNode.setParent(this);
        exprNode.setParent(this);
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public ExprNode getExprNode() {
        return exprNode;
    }
}
