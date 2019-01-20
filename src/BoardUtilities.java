import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Static utility class that performs operations on puzzle boards stored
 * as an ArrayList of Byte objects. Alternatively, I could have chosen to
 * create a Board class and use these functions as instance methods.
 */
public final class BoardUtilities {

    private BoardUtilities() { }

    /**
     * Clones a puzzle board, swaps indices a and b, and returns the new puzzle board
     * @param board the original puzzle board
     * @param a array index of tile to be swapped
     * @param b array index of tile to be swapped
     * @return a new puzzle board with indices a and b swapped
     */
    public static ArrayList<Byte> cloneAndSwap(ArrayList<Byte> board, int a, int b) {

        // Clone the puzzle board
        ArrayList<Byte> newBoard = new ArrayList<>(board);

        // Swap a and b
        Collections.swap(newBoard,a,b);

        //Return new puzzle board
        return newBoard;
    }

    /**
     * Performs a specified action on a puzzle board and returns the new puzzle board.
     * The original board remains in its original unmodified configuration.
     * @param board the original puzzle board
     * @param action an action to be performed on the puzzle board: move zero tile UP, DOWN, LEFT, RIGHT
     * @return a new puzzle board reflecting the results of the specified action
     */
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
                //TODO: Throwing an error may be better than returning unmodified board
                newBoard = board;
        }

        return newBoard;
    }

    /**
     * Calculates the sum of the cost of each misplaced tile in the puzzle board.
     * @param board puzzle board representing the problem state
     * @param goal puzzle board representing the goal state
     * @return sum of the cost of each misplaced tile in the puzzle board
     */
    public static int getMisplacedTilesCost(ArrayList<Byte> board, ArrayList<Byte> goal) {

        int misplacedTilesCost = 0;

        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).equals(goal.get(i))) {
                misplacedTilesCost += board.get(i);
            }
        }

        return misplacedTilesCost;
    }

    /**
     * Calculates the the sum of the Manhattan distance of each tile from its goal position
     * For the modified Eight Puzzle, this multiplies the value of each Manhattan distance by value of the tile (cost of move)
     * @param board puzzle board representing the problem state
     * @param goal puzzle board representing the goal state
     * @return sum of the Manhattan distance of each tile from its goal position
     */
    public static int getSumManhattanDistances(ArrayList<Byte> board, ArrayList<Byte> goal) {
        //TODO: Is there a way to do this better than quadratic time?
        int sum = 0;
        for(int i = 0; i < board.size(); i++) {

            // Find goal index of tile i
            int j = goal.indexOf(board.get(i));

            // Calculate board index (iRow, iCol) from byte array indices
            // Calculate  goal index (jRow, jCol) from byte array indices
            // Calculate Manhattan Distance
            int manhattan = Math.abs((i / 3) - (j / 3)) + Math.abs((i % 3) - (j % 3));

            // Multiply distance moved by cost of moving the tile
            int cost = board.get(i) * manhattan;

            sum += cost;
        }

        return sum;
    }

    /**
     * Calculates the total cost of swapping any misplaced tile with zero until the board reaches the goal state
     * @param board puzzle board representing the problem state
     * @param goal puzzle board representing the goal state
     * @return total cost of swapping any misplaced tile with zero until the board reaches the goal state
     */
    public static int getZeroSwapCost(ArrayList<Byte> board, ArrayList<Byte> goal) {
        int totalCost = 0;
        board = new ArrayList<>(board);

        while(!board.equals(goal)) {
            int boardZeroLocation = board.indexOf((byte) 0);
            byte goalTile = goal.get(boardZeroLocation);
            if (goalTile != ( (byte) 0)) {
                totalCost += goalTile;
                int swapLocation = board.indexOf(goalTile);
                Collections.swap(board, boardZeroLocation, swapLocation);
            } else {
                for(int i = 0; i < board.size(); i++) {
                    if(!board.get(i).equals(goal.get(i))) {
                        totalCost += board.get(i);
                        Collections.swap(board, boardZeroLocation, i);
                        break;
                    }
                }
            }
        }

        return totalCost;
    }

    /**
     * Generates a puzzle board represented as an ArrayList of Byte objects
     * @param byteArray puzzle board represented as a primitive byte array
     * @return puzzle board represented as an ArrayList of Byte objects
     */
    public static ArrayList<Byte> generateBoard(byte[] byteArray) {
        ArrayList<Byte> board = new ArrayList<>();

        for (byte b : byteArray) {
            board.add(b);
        }

        return board;
    }

    /**
     * Menu allowing user to select a puzzle board
     * @return the selected puzzle board represented as an ArrayList of Byte objects
     */
    public static ArrayList<Byte> selectBoard() {
        ArrayList<Byte> board = null;
        boolean done = false;

        while (!done) {
            System.out.println("------------------------------------");
            System.out.println("Choose a difficulty level");
            System.out.println("------------------------------------");
            System.out.println("1 - Easy");
            System.out.println("2 - Medium");
            System.out.println("3 - Hard");
            System.out.println("------------------------------------");
            System.out.println("0 - Quit");
            System.out.println("------------------------------------");
            System.out.print("YOUR SELECTION: ");

            Scanner input = new Scanner(System.in);
            int option = input.nextInt();

            switch (option) {
                case 1:
                    byte[] easy = {1, 3, 4, 8, 6, 2, 7, 0, 5};
                    board = generateBoard(easy);
                    done = true;
                    break;
                case 2:
                    byte[] medium = {2, 8, 1, 0, 4, 3, 7, 6, 5};
                    board = generateBoard(medium);
                    done = true;
                    break;
                case 3:
                    byte[] hard = {5, 6, 7, 4, 0, 8, 3, 2, 1};
                    board = generateBoard(hard);
                    done = true;
                    break;
                case 0:
                    board = new ArrayList<>();
                    done = true;
                    break;
                default:
                    System.out.println("INVALID SELECTION");
                    break;
            }
        }
        return board;
    }

}
