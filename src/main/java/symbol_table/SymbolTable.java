package symbol_table;

import ast.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymbolTable<String, SymbolTableRecord> extends HashMap<java.lang.String, symbol_table.SymbolTableRecord> {

    private SymbolTable<String, SymbolTableRecord> parent;
    private final List<SymbolTable<String, SymbolTableRecord>> children = new ArrayList<>();
    private AbstractSyntaxNode scopingNode;

    public SymbolTable(SymbolTable<String, SymbolTableRecord> parent) {
        this.setParent(parent);
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public void setParent(SymbolTable<String, SymbolTableRecord> parent) {
        this.parent = parent;
    }

    public void addChild(SymbolTable<String, SymbolTableRecord> child) {
        children.add(child);
    }

    public SymbolTable<String, SymbolTableRecord> getParent() {
        return parent;
    }

    public List<SymbolTable<String, SymbolTableRecord>> getChildren() {
        return children;
    }

    public SymbolTableRecord lookup(String key) {
        if (this.containsKey(key))
            return (SymbolTableRecord) this.get(key);
        else {
            if (this.parent != null) {
                return parent.lookup(key);
            } else {
                return null;
            }
        }
    }

    public AbstractSyntaxNode getScopingNode() {
        return scopingNode;
    }

    public void setScopingNode(AbstractSyntaxNode scopingNode) {
        this.scopingNode = scopingNode;
    }

    @Override
    public java.lang.String toString() {
        java.lang.String string = "";
        string += "\nGlobal : \n";
        for (symbol_table.SymbolTableRecord tmp : this.values())
            string += tmp + "\n";
        int number = 0;
        for (SymbolTable tmp : this.getChildren()) {
            if(tmp.getScopingNode() instanceof FunOpNode){
                string += "\n" + ((FunOpNode) tmp.getScopingNode()).getIdentifierNode().getIndex() + " : \n";
            }
            if(tmp.getScopingNode() instanceof WhileStatOpNode){
                string += "\nWhileOpNode_" + number +  " : \n";
                number++;
            }
            if(tmp.getScopingNode() instanceof IfStatOpNode){
                string += "\nIfStatNode_" + number +  " : \n";
                number++;
            }
            if(tmp.getScopingNode() instanceof ElseOpNode){
                string += "\nElseNode_" + number +  " : \n";
                number++;
            }
            for (Object tmp1 : tmp.values())
                string += tmp1 + "\n";
        }
        return string;
    }
}
