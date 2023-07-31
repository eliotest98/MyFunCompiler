package ast;

import java.util.ArrayList;
import java.util.List;

public class IdListInitObblNode extends AbstractSyntaxNode {

    private List<CoppiaIdConstNode> listCoppiaIdConstNode = new ArrayList<>();

    public IdListInitObblNode(IdentifierNode idNode, ConstNode constNode) {
        CoppiaIdConstNode tmp = new CoppiaIdConstNode(idNode, constNode);
        tmp.setParent(this);
        this.listCoppiaIdConstNode.add(tmp);
    }

    public IdListInitObblNode(List<CoppiaIdConstNode> listCoppiaIdConstNode, IdentifierNode idNode, ConstNode constNode) {
        this.listCoppiaIdConstNode = listCoppiaIdConstNode;

        CoppiaIdConstNode tmp = new CoppiaIdConstNode(idNode, constNode);
        this.listCoppiaIdConstNode.add(tmp);

        for (CoppiaIdConstNode node : this.listCoppiaIdConstNode) {
            node.setParent(this);
        }
    }

    public List<CoppiaIdConstNode> getListCoppiaIdConstNode() {
        return listCoppiaIdConstNode;
    }
}
