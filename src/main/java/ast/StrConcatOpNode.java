package ast;

public class StrConcatOpNode extends ExprNode {

    private ExprNode left;
    private ExprNode right;

    public StrConcatOpNode(ExprNode left, ExprNode right) {
        this.left = left;
        this.right = right;
        left.setParent(this);
        right.setParent(this);
    }

    public ExprNode getLeft() {
        return left;
    }

    public ExprNode getRight() {
        return right;
    }

}
