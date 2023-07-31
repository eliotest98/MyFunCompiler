package ast;

public class ParDeclOpNode extends AbstractSyntaxNode {

    private TypeNode typeNode;
    private IdentifierNode identifierNode;
    private boolean isOut;

    public ParDeclOpNode(TypeNode typeNode, IdentifierNode identifierNode, boolean isOut) {
        this.typeNode = typeNode;
        this.identifierNode = identifierNode;
        this.isOut = isOut;
        typeNode.setParent(this);
        identifierNode.setParent(this);
    }

    public TypeNode getTypeNode() {
        return typeNode;
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public boolean isOut() {
        return isOut;
    }
}
