package ast;

public class NEOpNode extends ExprNode {

    private ExprNode left;
    private ExprNode right;

    public NEOpNode(ExprNode left, ExprNode right) {
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
