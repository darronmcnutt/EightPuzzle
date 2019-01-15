import java.util.Comparator;

public class PathCostComparator implements Comparator<Node> {
    @Override
    public int compare(Node first, Node second) {
        return first.getPathCost() - second.getPathCost();
    }
}
