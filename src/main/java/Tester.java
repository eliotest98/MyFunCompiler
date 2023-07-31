import java.io.FileReader;
import java.io.Reader;
import java.util.regex.Pattern;

import ast.*;
import ast.visitors.CGeneratorVisitor;
import ast.visitors.XMLVisitor;
import java_cup.runtime.*;
import symbol_table.SymbolTable;
import symbol_table.SymbolTableRecord;

public class Tester {

    /**
     * Effettua la compilazione del programma scritto in MyFun
     *
     * @param args path relativo del file da compilare
     */
    public static void main(String[] args) {

        if (args[0] != null) {
            try {
                Reader reader = new FileReader(args[0]);
                Lexer lexer = new Lexer(reader);
                Parser parser = new Parser(lexer);

                Symbol result = parser.parse();
                lexer.printStringTable();

                AbstractSyntaxNode root = (AbstractSyntaxNode) result.value;
                //System.out.println("\n\n\t\t\t\t\t\t*** XML VIEW OF AST ***\n\n" + root.accept(new XMLVisitor()));
                root.accept(new XMLVisitor());

                SymbolTable<String, SymbolTableRecord> rootSymbolTable = SemanticAnalyzer.semanticAnalysis(root);

                //System.out.println("\n\n\t\t\t\t\t\t*** SYMBOL TABLES ***\n" + rootSymbolTable.toString());

                String[] split = args[0].split(Pattern.quote("\\"));
                if(split.length == 1) {
                    split = args[0].split(Pattern.quote("/"));
                }
                CGeneratorVisitor cGeneratorVisitor = new CGeneratorVisitor(split[split.length-1]);
                cGeneratorVisitor.visit(root);
                cGeneratorVisitor.dispose();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Percorso del file non specificato.");
        }
    }
}
