import java.util.ArrayList;
import java.util.Collections;

public class BoardUtilities {
    public static ArrayList<Byte> cloneAndSwap(ArrayList<Byte> board, int a, int b) {

        // Clone the puzzle board
        ArrayList<Byte> newBoard = new ArrayList<>(board);

        // Swap a and b
        Collections.swap(newBoard,a,b);

        //Return new puzzle board
        return newBoard;
    }

    public static ArrayList<Byte> performAction(ArrayList<Byte> board, Action action) {

        ArrayList<Byte> newBoard;
        int zeroLocation = board.indexOf((byte) 0);

        switch(action) {
            case UP:
                newBoard = cloneAndSwap(board, zeroLocation, zeroLocation - 3);
                break;
            case DOWN:
                newBoard = cloneAndSwap(board, zeroLocation, zeroLocation + 3);
                break;
            case LEFT:
                newBoard = cloneAndSwap(board, zeroLocation, zeroLocation - 1);
                break;
            case RIGHT:
                newBoard = cloneAndSwap(board, zeroLocation, zeroLocation + 1);
                break;
            default:
                //TODO: Throwing an error is likely better than returning unmodified board
                newBoard = board;
        }

        return newBoard;
    }

    public static int getNumMisplacedTiles(ArrayList<Byte> board, ArrayList<Byte> goal) {

        int numMisplaced = 0;

        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) != goal.get(i)) {
                numMisplaced++;
            }
        }

        return numMisplaced;
    }
}
