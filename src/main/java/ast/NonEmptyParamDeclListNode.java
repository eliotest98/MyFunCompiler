package ast;

import java.util.ArrayList;
import java.util.List;

public class NonEmptyParamDeclListNode extends AbstractSyntaxNode {

    private List<ParDeclOpNode> nonEmptyParamDeclList = new ArrayList<ParDeclOpNode>();

    public NonEmptyParamDeclListNode(ParDeclOpNode parDeclNode) {
        this.nonEmptyParamDeclList.add(parDeclNode);
        for (ParDeclOpNode node : this.nonEmptyParamDeclList) {
            node.setParent(this);
        }
    }

    public NonEmptyParamDeclListNode(List<ParDeclOpNode> nonEmptyParamDeclList, ParDeclOpNode parDeclNode) {
        this.nonEmptyParamDeclList = nonEmptyParamDeclList;
        this.nonEmptyParamDeclList.add(parDeclNode);
        for (ParDeclOpNode node : this.nonEmptyParamDeclList) {
            node.setParent(this);
        }
    }

    public List<ParDeclOpNode> getNonEmptyParamDeclList() {
        return nonEmptyParamDeclList;
    }

}
