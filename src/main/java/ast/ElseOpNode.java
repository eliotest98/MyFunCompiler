package ast;

import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

public class ElseOpNode extends AbstractSyntaxNode {

    private BodyOp bodyOp;
    private SymbolTable<String, SymbolTableRecord> symbolTable;

    public ElseOpNode() {
        bodyOp = null;
    }

    public ElseOpNode(BodyOp bodyOp) {
        this.bodyOp = bodyOp;
        bodyOp.setParent(this);
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
