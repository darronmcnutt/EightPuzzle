import java.util.ArrayList;
import java.util.Comparator;

/**
 * Compares f(n) = g(n) + h(n) between two Nodes where
 * g(n) = path cost
 * h(n) = sum of (the Manhattan Distance of each tile from goal * cost of moving that tile)
 */
public class PathCostManhattanCostComparator implements Comparator<Node> {

    private ArrayList<Byte> goal;

    public PathCostManhattanCostComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return (first.getPathCost() + BoardUtilities.getSumManhattanCosts(first.getState(), this.goal)) -
               (second.getPathCost() + BoardUtilities.getSumManhattanCosts(second.getState(), this.goal));
    }
}

