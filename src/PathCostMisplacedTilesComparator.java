import java.util.ArrayList;
import java.util.Comparator;

/**
 * Compares f(n) = g(n) + h(n) between two Nodes where
 * g(n) = path cost
 * h(n) = number of misplaced tiles
 */
public class PathCostMisplacedTilesComparator implements Comparator<Node> {

    private final ArrayList<Byte> goal;

    public PathCostMisplacedTilesComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return (first.getPathCost() + BoardUtilities.getNumMisplacedTiles(first.getState(), this.goal)) -
               (second.getPathCost() + BoardUtilities.getNumMisplacedTiles(second.getState(), this.goal));
    }
}