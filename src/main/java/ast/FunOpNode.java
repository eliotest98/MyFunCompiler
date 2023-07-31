package ast;

import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

public class FunOpNode extends AbstractSyntaxNode {

    private IdentifierNode identifierNode;
    private ParamDeclListNode paramDeclListNode;
    private TypeNode typeNode;
    private BodyOp bodyOp;
    private SymbolTable<String, SymbolTableRecord> symbolTable;

    public FunOpNode(IdentifierNode identifierNode, ParamDeclListNode paramDeclListNode, TypeNode typeNode, BodyOp bodyOp) {
        this.identifierNode = identifierNode;
        this.paramDeclListNode = paramDeclListNode;
        this.typeNode = typeNode;
        this.bodyOp = bodyOp;
        bodyOp.setParent(this);
        identifierNode.setParent(this);
        paramDeclListNode.setParent(this);
        typeNode.setParent(this);
    }

    public FunOpNode(IdentifierNode identifierNode, ParamDeclListNode paramDeclListNode, BodyOp bodyOp) {
        this.identifierNode = identifierNode;
        this.paramDeclListNode = paramDeclListNode;
        this.bodyOp = bodyOp;
        bodyOp.setParent(this);
        identifierNode.setParent(this);
        paramDeclListNode.setParent(this);
    }

    public IdentifierNode getIdentifierNode() {
        return identifierNode;
    }

    public ParamDeclListNode getParamDeclListNode() {
        return paramDeclListNode;
    }

    public TypeNode getTypeNode() {
        return typeNode;
    }

    public BodyOp getBodyOp() {
        return bodyOp;
    }


    public SymbolTable<String, SymbolTableRecord> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable<String, SymbolTableRecord> symbolTable) {
        this.symbolTable = symbolTable;
    }

}
