import java.util.ArrayList;
import java.util.Comparator;

/**
 * Compares f(n) = g(n) + h(n) between two Nodes where
 * g(n) = path cost
 * h(n) = total cost of swapping any misplaced tile with zero until the board reaches the goal state
 */
public class PathCostZeroSwapComparator implements Comparator<Node> {

    private ArrayList<Byte> goal;

    public PathCostZeroSwapComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return (first.getPathCost() + BoardUtilities.getZeroSwapCost(first.getState(), this.goal)) -
               (second.getPathCost() + BoardUtilities.getZeroSwapCost(second.getState(), this.goal));
    }
}

