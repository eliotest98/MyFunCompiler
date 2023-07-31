package ast;

import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

public class ProgramNode extends AbstractSyntaxNode {

    private VarDeclListNode varDeclListNode;
    private FunListNode funListNode;
    private BodyOp mainNode;
    private SymbolTable<String, SymbolTableRecord> symbolTable;

    public ProgramNode(VarDeclListNode varDeclListNode, FunListNode funListNode, BodyOp mainNode) {
        this.varDeclListNode = varDeclListNode;
        this.funListNode = funListNode;
        this.mainNode = mainNode;
        varDeclListNode.setParent(this);
        funListNode.setParent(this);
        mainNode.setParent(this);
    }

    public VarDeclListNode getVarDeclListNode() {
        return varDeclListNode;
    }

    public FunListNode getFunListNode() {
        return funListNode;
    }

    public BodyOp getBodyOp() {
        return mainNode;
    }

    public SymbolTable<String, SymbolTableRecord> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable<String, SymbolTableRecord> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
