import java.util.ArrayList;
import java.util.Collections;

public class Node {

    private ArrayList<Byte> state;

    private Node parent;
    private ArrayList<Node> children;

    private Action action;
    private int depth;
    private int cost;
    private boolean expanded;

    public Node(ArrayList<Byte> state, Node parent, Action action, int depth, int cost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.depth = depth;
        this.cost = cost;

        this.expanded = false;
        this.children = new ArrayList<>();
    }

    public ArrayList<Byte> getState() {
        return state;
    }

    public int getZeroLocation() {
        return this.state.indexOf((byte) 0);
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public int getDepth() {
        return depth;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public ArrayList<Node> getPath() {
        ArrayList<Node> path = new ArrayList<>();
        path.add(this);

        Node ptr = this.parent;
        while(ptr != null) {
            path.add(ptr);
            ptr = ptr.parent;
        }

        Collections.reverse(path);

        return path;

    }

    @Override
    public String toString() {

        return
            this.state.get(0) + " " + this.state.get(1) + " " + this.state.get(2) + "\n" +
            this.state.get(3) + " " + this.state.get(4) + " " + this.state.get(5) + "\n" +
            this.state.get(6) + " " + this.state.get(7) + " " + this.state.get(8) + "\n" +
            "Zero moved: " + this.action + " Cost: " + this.cost + " Depth: " + this.depth;

    }
}
