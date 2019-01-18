import java.util.ArrayList;
import java.util.Comparator;

public class PathCostMisplacedTileComparator implements Comparator<Node> {

    ArrayList<Byte> goal;

    public PathCostMisplacedTileComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return (first.getPathCost() + BoardUtilities.getNumMisplacedTiles(first.getState(), this.goal)) -
               (second.getPathCost() + BoardUtilities.getNumMisplacedTiles(second.getState(), this.goal));
    }
}