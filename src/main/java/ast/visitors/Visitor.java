package ast.visitors;

import ast.AbstractSyntaxNode;

public interface Visitor {
    Object visit(AbstractSyntaxNode node);
}
