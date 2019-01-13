import java.util.*;

public class Main {

    public static void main(String[] args) {
        byte[] e = { 1, 3, 4, 8, 6, 2, 7, 0, 5 };
        byte[] g = { 1, 2, 3, 8, 0, 4, 7, 6, 5 };
        ArrayList<Byte> easy = new ArrayList<>();
        easy.add((byte)1);
        easy.add((byte)3);
        easy.add((byte)4);
        easy.add((byte)8);
        easy.add((byte)6);
        easy.add((byte)2);
        easy.add((byte)7);
        easy.add((byte)0);
        easy.add((byte)5);
        ArrayList<Byte> goal = new ArrayList<>();
        goal.add((byte)1);
        goal.add((byte)2);
        goal.add((byte)3);
        goal.add((byte)8);
        goal.add((byte)0);
        goal.add((byte)4);
        goal.add((byte)7);
        goal.add((byte)6);
        goal.add((byte)5);

        Node testNode = new Node(easy,null,null,0,0);


        ArrayList<Node> solution = breadthFirstSearch(testNode, goal);

               for (Node node : solution) {
            System.out.println(node.toString());
        }

/*
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
*/

    }

    public static ArrayList<Byte> cloneAndSwap(ArrayList<Byte> board, int a, int b) {

        // Clone the puzzle board
        ArrayList<Byte> newBoard = new ArrayList<>(board);

        // Swap a and b
        Collections.swap(newBoard,a,b);

        //Return new puzzle board
        return newBoard;
    }

    public static ArrayList<Byte> performAction(ArrayList<Byte> board, int zeroLocation, Action action) {

        ArrayList<Byte> newBoard;

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

    public static String boardToString(ArrayList<Byte> board) {

        return
            board.get(0) + " " + board.get(1) + " " + board.get(2) + "\n" +
            board.get(3) + " " + board.get(4) + " " + board.get(5) + "\n" +
            board.get(6) + " " + board.get(7) + " " + board.get(8) + "\n";

    }

    public static int getZeroLocation(ArrayList<Byte> board) {
        return board.indexOf((byte) 0);
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

    public static ArrayList<Node> breadthFirstSearch(Node root, ArrayList<Byte> goal) {

        if (root.getState().equals(goal)) {
            ArrayList<Node> path = new ArrayList<>();
            path.add(root);
            return path;
        }

        ArrayDeque<Node> searchQueue = new ArrayDeque<>();
        searchQueue.add(root);

        HashSet<ArrayList<Byte>> explored = new HashSet<>();

        while(!searchQueue.isEmpty()) {
            Node node = searchQueue.remove();
            ArrayList<Byte> nodeState = node.getState();

            explored.add(nodeState);
            int zeroLocation = node.getZeroLocation();

            node.setExpanded(true);

            for(Action action : getActions(zeroLocation)) {
                ArrayList<Byte> childState = performAction(nodeState, zeroLocation, action);
                if (!explored.contains(childState)) {
                    Node child = new Node(childState, node, action, node.getDepth() + 1, childState.get(zeroLocation));
                    node.addChild(child);
                    if (childState.equals(goal)) {
                        return child.getPath();
                    }
                    searchQueue.add(child);
                }
            }

        }

        return new ArrayList<>();

    }
}
