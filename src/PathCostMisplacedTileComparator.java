import java.util.ArrayList;
import java.util.Comparator;

/**
 * Compares f(n) = g(n) + h(n) between two Nodes where
 * g(n) = path cost
 * h(n) = number of misplaced tiles
 */
public class PathCostMisplacedTileComparator implements Comparator<Node> {

    ArrayList<Byte> goal;

    public PathCostMisplacedTileComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return (first.getPathCost() + BoardUtilities.getMisplacedTilesCost(first.getState(), this.goal)) -
               (second.getPathCost() + BoardUtilities.getMisplacedTilesCost(second.getState(), this.goal));
    }
}