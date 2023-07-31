package ast;

import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

public class IfStatOpNode extends AbstractSyntaxNode implements StatNode {

    private ExprNode exprNode;
    private ElseOpNode elseNode;
    private BodyOp bodyOp;
    private SymbolTable<String, SymbolTableRecord> symbolTable;

    public IfStatOpNode(ExprNode exprNode, BodyOp bodyOp, ElseOpNode elseNode) {
        this.exprNode = exprNode;
        this.bodyOp = bodyOp;
        this.elseNode = elseNode;
        exprNode.setParent(this);
        bodyOp.setParent(this);
        elseNode.setParent(this);
    }

    public ExprNode getExprNode() {
        return exprNode;
    }

    public BodyOp getBodyOp() {
        return bodyOp;
    }

    public ElseOpNode getElseNode() {
        return elseNode;
    }

    public SymbolTable<String, SymbolTableRecord> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable<String, SymbolTableRecord> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
