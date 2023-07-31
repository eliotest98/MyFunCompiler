package ast;

import java.util.ArrayList;

public class StatListNode extends AbstractSyntaxNode {

    private ArrayList<StatNode> statListNode = new ArrayList<StatNode>();

    public StatListNode() {

    }

    public StatListNode(StatNode statNode, ArrayList<StatNode> statListNode) {
        this.statListNode = statListNode;
        this.statListNode.add(statNode);
        for (StatNode node : statListNode) {
            ((AbstractSyntaxNode) node).setParent(this);
        }
    }

    public ArrayList<StatNode> getStatListNode() {
        return statListNode;
    }

}