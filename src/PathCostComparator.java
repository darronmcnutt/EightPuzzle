import java.util.Comparator;

/**
 * Compares the path cost between two Nodes
 */
public class PathCostComparator implements Comparator<Node> {
    @Override
    public int compare(Node first, Node second) {
        return first.getPathCost() - second.getPathCost();
    }
}