import java.util.ArrayList;
import java.util.Comparator;

public class PathCostManhattanDistancesComparator implements Comparator<Node> {

    ArrayList<Byte> goal;

    public PathCostManhattanDistancesComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return (first.getPathCost() + BoardUtilities.getSumManhattanDistances(first.getState(), this.goal)) -
               (second.getPathCost() + BoardUtilities.getSumManhattanDistances(second.getState(), this.goal));
    }
}

