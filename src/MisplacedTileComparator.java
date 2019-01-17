import java.util.ArrayList;
import java.util.Comparator;

public class MisplacedTileComparator implements Comparator<Node> {

    ArrayList<Byte> goal;

    public MisplacedTileComparator(ArrayList<Byte> goal) {
        this.goal = goal;
    }

    @Override
    public int compare(Node first, Node second) {
        return BoardUtilities.getNumMisplacedTiles(first.getState(), this.goal) -
               BoardUtilities.getNumMisplacedTiles(second.getState(), this.goal);
    }
}
