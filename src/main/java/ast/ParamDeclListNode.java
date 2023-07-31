package ast;

public class ParamDeclListNode extends AbstractSyntaxNode {

    NonEmptyParamDeclListNode nonEmptyParamDeclListNode;

    public ParamDeclListNode() {

    }

    public ParamDeclListNode(NonEmptyParamDeclListNode nonEmptyParamDeclListNode) {
        this.nonEmptyParamDeclListNode = nonEmptyParamDeclListNode;
        nonEmptyParamDeclListNode.setParent(this);
    }

    public NonEmptyParamDeclListNode getNonEmptyParamDeclListNode() {
        return nonEmptyParamDeclListNode;
    }
}
