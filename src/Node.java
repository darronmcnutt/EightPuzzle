import java.util.ArrayList;
import java.util.Collections;

public class Node {

    private ArrayList<Byte> state;

    private Node parent;
    private ArrayList<Node> children;

    private Action action;
    private int depth;
    private int cost;
    private int pathCost;
    private boolean expanded;

    /**
     * Constructor for root node in search tree
     * @param state initial state of the puzzle board
     */
    public Node(ArrayList<Byte> state) {
        this.state = state;
        this.depth = 0;
        this.cost = 0;
        this.pathCost = 0;

        this.expanded = false;
        this.children = new ArrayList<>();
    }

    /**
     * Constructor for child nodes in search tree
     * @param state the state of the puzzle board after an action is performed
     * @param parent the parent of this node
     * @param action the action performed that resulted in the state
     */
    public Node(ArrayList<Byte> state, Node parent, Action action) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.depth = parent.getDepth() + 1;

        // Previous location of the zero tile == the value of the tile moved == cost of the move
        this.cost = state.get(parent.getZeroLocation());

        this.pathCost = parent.getPathCost() + this.cost;
        this.expanded = false;
        this.children = new ArrayList<>();
    }

    public ArrayList<Byte> getState() {
        return state;
    }

    public int getZeroLocation() { return this.state.indexOf((byte) 0); }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public int getDepth() {
        return depth;
    }

    public int getPathCost() { return pathCost; }

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

    public Action[] getActions() {
        Action[] actions;

        switch(this.getZeroLocation()) {
            case 0:
                actions = new Action[] { Action.RIGHT, Action.DOWN };
                break;
            case 1:
                actions = new Action[] { Action.LEFT, Action.RIGHT, Action.DOWN };
                break;
            case 2:
                actions = new Action[] { Action.LEFT, Action.DOWN };
                break;
            case 3:
                actions = new Action[] { Action.UP, Action.RIGHT, Action.DOWN };
                break;
            case 4:
                actions = new Action[] { Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT };
                break;
            case 5:
                actions = new Action[] { Action.UP, Action.DOWN, Action.LEFT };
                break;
            case 6:
                actions = new Action[] { Action.UP, Action.RIGHT };
                break;
            case 7:
                actions = new Action[] { Action.LEFT, Action.UP, Action.RIGHT };
                break;
            case 8:
                actions = new Action[] { Action.UP, Action.LEFT };
                break;
            default:
                //TODO: Throwing error may be better than returning empty array of actions
                actions = new Action[0];
        }
        return actions;
    }

    @Override
    public String toString() {

        String actionString = this.action != null ? this.action.toString() : "NO MOVE";

        return
            this.state.get(0) + " " + this.state.get(1) + " " + this.state.get(2) + "\n" +
            this.state.get(3) + " " + this.state.get(4) + " " + this.state.get(5) + "\n" +
            this.state.get(6) + " " + this.state.get(7) + " " + this.state.get(8) + "\n" +
            "Zero moved: " + actionString + " | Move Cost: " + this.cost + " | Total Path Cost: " +
                    this.pathCost + " | Depth: " + this.depth + "\n";

    }
}
