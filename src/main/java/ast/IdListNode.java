package ast;

import java.util.ArrayList;

public class IdListNode extends AbstractSyntaxNode {

    private ArrayList<IdentifierNode> idListNode = new ArrayList<IdentifierNode>();

    public IdListNode(IdentifierNode identifierNode) {
        idListNode.add(identifierNode);
        identifierNode.setParent(this);
    }

    public IdListNode(ArrayList<IdentifierNode> idListNode, IdentifierNode identifierNode) {
        this.idListNode = idListNode;
        this.idListNode.add(identifierNode);
        for (IdentifierNode node : idListNode) {
            node.setParent(this);
        }
    }

    public ArrayList<IdentifierNode> getIdListNode() {
        return idListNode;
    }
}
