import java.util.ArrayList;
import java.util.Comparator;

/**
 * Compares the number of misplaced tiles between two puzzle board states
 */
public class MisplacedTileComparator implements Comparator<Node> {

    ArrayList<Byte> goal;

    public MisplacedTileComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return BoardUtilities.getMisplacedTilesCost(first.getState(), this.goal) -
               BoardUtilities.getMisplacedTilesCost(second.getState(), this.goal);
    }
}
