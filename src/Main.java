import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // TEST STUFF
        byte[] e = { 1, 3, 4, 8, 6, 2, 7, 0, 5 };
        byte[] m = {2, 8, 1, 0, 4, 3, 7, 6, 5 };
        byte[] h = {5, 6, 7, 4, 0, 8, 3, 2, 1 };
        byte[] g = { 1, 2, 3, 8, 0, 4, 7, 6, 5 };
        ArrayList<Byte> hard = new ArrayList<>();

        for (byte b : h) {
            hard.add(b);
        }

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

        Node testNode = new Node(hard);

        Instant start = Instant.now();
        ArrayList<Node> solution = uniformCostSearch(testNode, goal);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + "ms\n");

        for (Node node : solution) {
            System.out.println(node.toString());
        }

    }

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

            node.setExpanded(true);

            for(Action action : node.getActions()) {
                ArrayList<Byte> childState = performAction(nodeState, action);
                //TODO: Should I check if childState in searchQueue if only possible in linear time?
                if (!explored.contains(childState)) {
                    Node child = new Node(childState, node, action);
                    node.addChild(child);
                    if (childState.equals(goal)) {
                        return child.getPath();
                    }
                    // BFS: add to END of searchQueue
                    searchQueue.add(child);
                }
            }

        }

        return new ArrayList<>();

    }

    public static ArrayList<Node> depthFirstSearch(Node root, ArrayList<Byte> goal) {

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

            node.setExpanded(true);

            for(Action action : node.getActions()) {
                ArrayList<Byte> childState = performAction(nodeState, action);
                //TODO: Should I check if childState in searchQueue if only possible in linear time?
                if (!explored.contains(childState)) {
                    Node child = new Node(childState, node, action);
                    node.addChild(child);
                    if (childState.equals(goal)) {
                        return child.getPath();
                    }
                    // DFS: add to FRONT of searchQueue
                    searchQueue.addFirst(child);
                }
            }

        }

        return new ArrayList<>();

    }

    public static ArrayList<Node> uniformCostSearch(Node root, ArrayList<Byte> goal) {

        PathCostPriorityQueue searchQueue = new PathCostPriorityQueue();
        searchQueue.add(root);

        HashSet<ArrayList<Byte>> explored = new HashSet<>();

        while(!searchQueue.isEmpty()) {

            Node node = searchQueue.remove();
            ArrayList<Byte> nodeState = node.getState();

            if (nodeState.equals(goal)) {
                return node.getPath();
            }

            explored.add(nodeState);
            node.setExpanded(true);

            for(Action action : node.getActions()) {
                ArrayList<Byte> childState = performAction(nodeState, action);
                Node child = new Node(childState, node, action);

                if (!explored.contains(childState) && !searchQueue.contains(childState)) {
                    searchQueue.add(child);
                    node.addChild(child);
                } else if (searchQueue.contains(childState)
                       && (searchQueue.getPathCost(childState) > child.getPathCost())) {
                    searchQueue.remove(childState);
                    searchQueue.add(child);
                    node.addChild(child);
                }

            }
        }

        // Return empty ArrayList if no path found
        return new ArrayList<>();
    }

}
