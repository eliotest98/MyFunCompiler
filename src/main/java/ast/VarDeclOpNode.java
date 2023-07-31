package ast;

public class VarDeclOpNode extends AbstractSyntaxNode {

    private TypeNode typeNode;
    private IdListInitNode idInitListNode;
    private IdListInitObblNode idListInitObblNode;

    public VarDeclOpNode(IdListInitObblNode idListInitObblNode) {
        this.idListInitObblNode = idListInitObblNode;
        idListInitObblNode.setParent(this);
    }

    public VarDeclOpNode(TypeNode typeNode, IdListInitNode idInitListNode) {
        this.typeNode = typeNode;
        this.idInitListNode = idInitListNode;
        typeNode.setParent(this);
        idInitListNode.setParent(this);
    }

    public TypeNode getTypeNode() {
        return typeNode;
    }

    public TypeNode setTypeNode(TypeNode typeNode) {
        return this.typeNode=typeNode;
    }

    public IdListInitNode getIdInitListNode() {
        return idInitListNode;
    }

    public IdListInitObblNode getIdInitListObblNode() {
        return idListInitObblNode;
    }

}
