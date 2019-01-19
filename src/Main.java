import java.util.*;

public class Main {

    public static void main(String[] args) {

        ArrayList<Byte> goal = BoardUtilities.generateBoard(new byte[] { 1, 2, 3, 8, 0, 4, 7, 6, 5 });
        ArrayList<Byte> board = null;
        boolean done = false;

        while(!done) {

            board = BoardUtilities.selectBoard();

            if (board.size() > 0) {
                done = Search.selectAndRunSearch(board, goal);
            }
            else {
                done = true;
            }

        }
    }

}
