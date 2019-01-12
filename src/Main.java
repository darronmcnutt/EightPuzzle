import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        byte[] board = new byte[9];
        int zeroLocation;

        // Generate sample board
        for(byte i = 0; i < board.length; i++) {
            board[i] = i;
        }


        for (int i = 0; i < 9; i++) {
            System.out.println("Level: " + i);
            zeroLocation = getZeroLocation(board);
            System.out.println(boardToString(board));
            for(Action a : getActions(zeroLocation)) {
                System.out.println("Zero moved: " + a);
                System.out.println(boardToString(performAction(board, zeroLocation, a)));
            }
            if (i+1 < 9) { board = cloneAndSwap(board,i,i+1); }
        }

    }

    public static byte[] cloneAndSwap(byte[] board, int a, int b) {

        // Clone the puzzle board
        byte[] newBoard = Arrays.copyOf(board, board.length);
        
        // Swap a and b
        byte temp = newBoard[a];
        newBoard[a] = newBoard[b];
        newBoard[b] = temp;

        //Return new puzzle board
        return newBoard;
    }

    public static byte[] performAction(byte[] board, int zeroLocation, Action action) {

        byte[] newBoard;

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

    public static String boardToString(byte[] board) {

        return
            board[0] + " " + board[1] + " " + board[2] + "\n" +
            board[3] + " " + board[4] + " " + board[5] + "\n" +
            board[6] + " " + board[7] + " " + board[8] + "\n";

    }

    public static int getZeroLocation(byte[] board) {
        for(int i = 0; i < board.length; i++) {
            if (board[i] == 0) return i;
        }
        return -1;
    }

    public static Action[] getActions(int zeroLocation) {
        Action[] actions;

        switch(zeroLocation) {
            case 0:
                actions = new Action[] { Action.RIGHT, Action.DOWN };
                break;
            case 1:
                actions = new Action[] { Action.LEFT, Action.RIGHT, Action.DOWN };
                break;
            case 2:
                actions = new Action[] { Action.LEFT, Action.DOWN };
                break;
            case 3:
                actions = new Action[] { Action.UP, Action.RIGHT, Action.DOWN };
                break;
            case 4:
                actions = new Action[] { Action.UP, Action.DOWN, Action.LEFT, Action.RIGHT };
                break;
            case 5:
                actions = new Action[] { Action.UP, Action.DOWN, Action.LEFT };
                break;
            case 6:
                actions = new Action[] { Action.UP, Action.RIGHT };
                break;
            case 7:
                actions = new Action[] { Action.LEFT, Action.UP, Action.RIGHT };
                break;
            case 8:
                actions = new Action[] { Action.UP, Action.LEFT };
                break;
            default:
                //TODO: Throwing error may be better than returning empty array
                actions = new Action[0];
        }
        return actions;
    }
}
