package ast;

public class WriteStatOpNode extends AbstractSyntaxNode implements StatNode {

    ExprNode exprNode;
    Integer typeWrite;

    public WriteStatOpNode(Integer typeWrite, ExprNode exprNode) {
        this.exprNode = exprNode;
        this.typeWrite = typeWrite;
        exprNode.setParent(this);
    }

    public ExprNode getExprNode() {
        return exprNode;
    }

    public Integer getTypeWrite() {
        return typeWrite;
    }
}