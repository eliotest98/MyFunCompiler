package ast;

import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

public class BodyOp extends AbstractSyntaxNode {

    private VarDeclListNode varDeclListNode;
    private StatListNode statListNode;
    private SymbolTable<String, SymbolTableRecord> symbolTable;

    public BodyOp(VarDeclListNode varDeclListNode, StatListNode statListNode) {
        this.varDeclListNode = varDeclListNode;
        this.statListNode = statListNode;
        varDeclListNode.setParent(this);
        statListNode.setParent(this);
    }

    public VarDeclListNode getVarDeclListNode() {
        return varDeclListNode;
    }

    public StatListNode getStatListNode() {
        return statListNode;
    }

    public SymbolTable<String, SymbolTableRecord> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable<String, SymbolTableRecord> symbolTable) {
        this.symbolTable = symbolTable;
    }

}
