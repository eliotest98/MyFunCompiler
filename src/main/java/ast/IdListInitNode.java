package ast;

import java.util.ArrayList;
import java.util.List;

public class IdListInitNode extends AbstractSyntaxNode {
    private List<CoppiaIdExprNode> listCoppiaIdExprNode = new ArrayList<>();

    public IdListInitNode(IdentifierNode idNode) {
        CoppiaIdExprNode tmp = new CoppiaIdExprNode(idNode);
        tmp.setParent(this);
        this.listCoppiaIdExprNode.add(tmp);
    }

    public IdListInitNode(IdentifierNode idNode, List<CoppiaIdExprNode> listCoppiaIdExprNode) {
        this.listCoppiaIdExprNode = listCoppiaIdExprNode;
        CoppiaIdExprNode tmp = new CoppiaIdExprNode(idNode);
        this.listCoppiaIdExprNode.add(tmp);

        for (CoppiaIdExprNode node : this.listCoppiaIdExprNode) {
            node.setParent(this);
        }
    }

    public IdListInitNode(IdentifierNode idNode, ExprNode exprNode) {
        CoppiaIdExprNode tmp = new CoppiaIdExprNode(idNode, exprNode);
        tmp.setParent(this);
        this.listCoppiaIdExprNode.add(tmp);
    }

    public IdListInitNode(List<CoppiaIdExprNode> listCoppiaIdExprNode, IdentifierNode idNode, ExprNode exprNode) {
        this.listCoppiaIdExprNode = listCoppiaIdExprNode;

        CoppiaIdExprNode tmp = new CoppiaIdExprNode(idNode, exprNode);
        this.listCoppiaIdExprNode.add(tmp);

        for (CoppiaIdExprNode node : this.listCoppiaIdExprNode) {
            node.setParent(this);
        }
    }

    public List<CoppiaIdExprNode> getListCoppiaIdExprNode() {
        return listCoppiaIdExprNode;
    }
}
