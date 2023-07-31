package ast;

import java.util.ArrayList;

public class ExprListNode extends AbstractSyntaxNode {

    private ArrayList<ExprNode> exprNodeList = new ArrayList<ExprNode>();

    public ExprListNode(ExprNode exprNode) {
        this.exprNodeList.add(exprNode);
        exprNode.setParent(this);
    }

    public ExprListNode(ExprNode exprNode, ArrayList<ExprNode> exprNodeList) {
        this.exprNodeList = exprNodeList;
        this.exprNodeList.add(exprNode);
        for (ExprNode node : exprNodeList) {
            node.setParent(this);
        }
    }

    public ExprListNode(IdentifierNode identifierNode) {
        this.exprNodeList.add(identifierNode);
        identifierNode.setParent(this);
    }

    public ExprListNode(IdentifierNode identifierNode, ArrayList<ExprNode> exprNodeList) {
        this.exprNodeList = exprNodeList;
        this.exprNodeList.add(identifierNode);
        for (ExprNode node : exprNodeList) {
            node.setParent(this);
        }
    }

    public ArrayList<ExprNode> getExprNodeList() {
        return exprNodeList;
    }
}
