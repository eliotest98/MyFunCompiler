package ast;

public class ReadStatOpNode extends AbstractSyntaxNode implements StatNode {

    private IdListNode idListNode;
    private ExprNode exprNode;

    public ReadStatOpNode(IdListNode idListNode, ExprNode exprNode) {
        this.idListNode = idListNode;
        this.exprNode = exprNode;
        idListNode.setParent(this);
        exprNode.setParent(this);
    }

    public ReadStatOpNode(IdListNode idListNode) {
        this.idListNode = idListNode;
        idListNode.setParent(this);
    }

    public IdListNode getIdListNode() {
        return idListNode;
    }

    public ExprNode getExprNode() {
        return exprNode;
    }
}
