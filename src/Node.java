import java.util.ArrayList;
import java.util.Collections;

/**
 * Search tree node containing the state of the puzzle board, links to
 * parent and child nodes, and other bookkeeping data
 */
public class Node {

    private final ArrayList<Byte> state;
    private Node parent;
    private final ArrayList<Node> children;
    private Action action;
    private final int depth;
    private boolean expanded;

    // Cost of the previous move that resulted in this Node
    private final int cost;

    // Total cost from root Node to this Node
    private final int pathCost;

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
     * @param action the action performed that resulted in this puzzle board state
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

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    private int getZeroLocation() { return this.state.indexOf((byte) 0); }

    public ArrayList<Byte> getState() {
        return state;
    }

    public int getDepth() {
        return depth;
    }

    public int getPathCost() { return pathCost; }



    /**
     * Returns a path from the root Node to the current Node
     * @return path from the root Node to the current Node as an ArrayList
     */
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

    /**
     * The successor function. Returns list of possible actions - direction of movement of the zero tile (empty tile)
     * in puzzle board (UP, DOWN, LEFT, RIGHT)
     * @return an array of Action objects
     */
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
                actions = new Action[] { Action.RIGHT, Action.UP, Action.DOWN };
                break;
            case 4:
                actions = new Action[] { Action.LEFT, Action.RIGHT, Action.UP, Action.DOWN };
                break;
            case 5:
                actions = new Action[] { Action.LEFT, Action.UP, Action.DOWN,  };
                break;
            case 6:
                actions = new Action[] { Action.RIGHT, Action.UP };
                break;
            case 7:
                actions = new Action[] { Action.LEFT, Action.RIGHT, Action.UP };
                break;
            case 8:
                actions = new Action[] { Action.LEFT, Action.UP };
                break;
            default:
                //TODO: Throwing error may be better than returning empty array of actions
                actions = new Action[0];
        }
        return actions;
    }

    /**
     * Returns the String representation of this Node
     * @return the String representation of this Node
     */
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
