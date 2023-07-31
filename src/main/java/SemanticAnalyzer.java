import ast.AbstractSyntaxNode;
import ast.visitors.ScopeCheckerVisitor;

import ast.visitors.TypeCheckerVisitor;
import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

public class SemanticAnalyzer {
    public static SymbolTable<String, SymbolTableRecord> semanticAnalysis(AbstractSyntaxNode root) {
        SymbolTable<String, SymbolTableRecord> rootSymbolTable = (SymbolTable<String, SymbolTableRecord>) root.accept(new ScopeCheckerVisitor());

        root.accept(new TypeCheckerVisitor());

        return rootSymbolTable;
    }
}