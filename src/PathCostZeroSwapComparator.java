import java.util.ArrayList;
import java.util.Comparator;

public class PathCostZeroSwapComparator implements Comparator<Node> {

    ArrayList<Byte> goal;

    public PathCostZeroSwapComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return (first.getPathCost() + BoardUtilities.getZeroSwapTotal(first.getState(), this.goal)) -
               (second.getPathCost() + BoardUtilities.getZeroSwapTotal(second.getState(), this.goal));
    }
}

