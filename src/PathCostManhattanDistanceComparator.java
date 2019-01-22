import java.util.ArrayList;
import java.util.Comparator;


/**
 * Compares f(n) = g(n) + h(n) between two Nodes where
 * g(n) = path cost
 * h(n) = sum of the Manhattan Distance of each tile from goal
 */
public class PathCostManhattanDistanceComparator implements Comparator<Node> {

    private final ArrayList<Byte> goal;

    public PathCostManhattanDistanceComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return (first.getPathCost() + BoardUtilities.getSumManhattanDistances(first.getState(), this.goal)) -
               (second.getPathCost() + BoardUtilities.getSumManhattanDistances(second.getState(), this.goal));
    }
}

