package ast;

import java.util.ArrayList;
import java.util.List;

public class FunListNode extends AbstractSyntaxNode {

    private List<FunOpNode> funOpNodeList = new ArrayList<FunOpNode>();

    public FunListNode() {
    }

    public FunListNode(List<FunOpNode> funOpNodeList, FunOpNode funOpNode) {
        this.funOpNodeList = funOpNodeList;
        this.funOpNodeList.add(funOpNode);
        for (FunOpNode node : this.funOpNodeList) {
            node.setParent(this);
        }
    }

    public List<FunOpNode> getFunOpNodeList() {
        return funOpNodeList;
    }
}
