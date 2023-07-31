package ast;

import java.util.ArrayList;
import java.util.List;

public class VarDeclListNode extends AbstractSyntaxNode {

    private List<VarDeclOpNode> varDeclOpNodeList = new ArrayList<VarDeclOpNode>();

    public VarDeclListNode() {
    }

    public VarDeclListNode(List<VarDeclOpNode> varDeclOpNodeList, VarDeclOpNode varDeclOpNode) {
        this.varDeclOpNodeList = varDeclOpNodeList;
        this.varDeclOpNodeList.add(varDeclOpNode);
        for (VarDeclOpNode node : this.varDeclOpNodeList) {
            node.setParent(this);
        }
    }

    public List<VarDeclOpNode> getVarDeclOpNodeList() {
        return varDeclOpNodeList;
    }
}
