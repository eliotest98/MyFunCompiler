package ast;

import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

public class WhileStatOpNode extends AbstractSyntaxNode implements StatNode {

    private ExprNode exprNode;
    private BodyOp bodyOp;
    private SymbolTable<String, SymbolTableRecord> symbolTable;

    public WhileStatOpNode(ExprNode exprNode, BodyOp bodyOp) {
        this.exprNode = exprNode;
        this.bodyOp = bodyOp;
        exprNode.setParent(this);
        bodyOp.setParent(this);
    }

    public ExprNode getExprNode() {
        return exprNode;
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