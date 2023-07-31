package ast;

public class IdentifierNode extends ExprNode {

    private String index;
    private boolean isOut = false;

    public IdentifierNode(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public boolean setOut(boolean isOut){
        return this.isOut = isOut;
    }

    public boolean isOut(){
        return isOut;
    }

}
