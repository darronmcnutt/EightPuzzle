import java.util.ArrayList;

public class Node {

    private byte[] state;

    private Node parent;
    private ArrayList<Node> children;

    private Action action;
    private int depth;
    private int cost;
    private boolean expanded;

    public Node(byte[] state, Node parent, Action action, int depth, int cost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.depth = depth;
        this.cost = cost;

        this.expanded = false;
        this.children = new ArrayList<>();
    }
}
