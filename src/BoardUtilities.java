import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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

    public static int getSumManhattanDistances(ArrayList<Byte> board, ArrayList<Byte> goal) {
        //TODO: Is there a way to do this better than quadratic time?
        int sum = 0;
        for(int i = 0; i < board.size(); i++) {

            // Find goal index of tile i
            int j = goal.indexOf(board.get(i));

            // Calculate board[iRow, iCol] from byte array indices
            // Calculate  goal[jRow, jCol] from byte array indices
            // Add Manhattan distance to running total
            sum += Math.abs((i / 3) - (j / 3)) + Math.abs((i % 3) - (j % 3));

        }

        return sum;
    }

    public static int getZeroSwapTotal(ArrayList<Byte> board, ArrayList<Byte> goal) {
        int totalSwaps = 0;
        board = new ArrayList<>(board);

        while(!board.equals(goal)) {
            int boardZeroLocation = board.indexOf((byte) 0);
            byte goalTile = goal.get(boardZeroLocation);
            if (goalTile != ( (byte) 0)) {
                int swapLocation = board.indexOf(goalTile);
                Collections.swap(board, boardZeroLocation, swapLocation);
                totalSwaps++;
            } else {
                for(int i = 0; i < board.size(); i++) {
                    if(!board.get(i).equals(goal.get(i))) {
                        Collections.swap(board, boardZeroLocation, i);
                        totalSwaps++;
                        break;
                    }
                }
            }
        }
        return totalSwaps;
    }

    public static ArrayList<Byte> generateBoard(byte[] byteArray) {
        ArrayList<Byte> board = new ArrayList<>();

        for (byte b : byteArray) {
            board.add(b);
        }

        return board;
    }

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
