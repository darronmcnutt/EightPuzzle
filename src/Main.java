import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        ArrayList<Byte> board = null;
        ArrayList<Byte> goal = generateBoard(new byte[] { 1, 2, 3, 8, 0, 4, 7, 6, 5 });
        boolean finished = false;

        while(!finished) {

            System.out.println("Choose a difficulty level");
            System.out.println("1 - Easy");
            System.out.println("2 - Medium");
            System.out.println("3 - Hard");
            System.out.println("0 - Quit");

            Scanner input = new Scanner(System.in);
            int option = input.nextInt();

            switch(option) {
                case 1:
                    byte[] easy = { 1, 3, 4, 8, 6, 2, 7, 0, 5 };
                    board = generateBoard(easy);
                    finished = true;
                    break;
                case 2:
                    byte[] medium = { 2, 8, 1, 0, 4, 3, 7, 6, 5 };
                    board = generateBoard(medium);
                    finished = true;
                    break;
                case 3:
                    byte[] hard = { 5, 6, 7, 4, 0, 8, 3, 2, 1 };
                    board = generateBoard(hard);
                    finished = true;
                    break;
                case 0:
                    finished = true;
                    return;
                default:
                    System.out.println("INVALID SELECTION");
                    break;
            }
        }

        Node root = new Node(board);
        ArrayList<Node> solution = null;

        finished = false;

        while(!finished) {
            System.out.println("Choose a search strategy");
            System.out.println("1 - Breadth-first");
            System.out.println("2 - Depth-first");
            System.out.println("3 - Iterative deepening");
            System.out.println("4 - Uniform-cost");
            System.out.println("5 - Best-first (Greedy)");
            System.out.println("6 - A*1 (number of misplaced tiles)");
            System.out.println("7 - A*2 (sum of Manhattan distances)");
            System.out.println("8 - A*3 (custom heuristic)");
            System.out.println("0 - Quit");

            Scanner input = new Scanner(System.in);
            int option = input.nextInt();

            switch(option) {
                case 1:
                    solution = breadthFirstSearch(root, goal);
                    break;
                case 2:
                    solution = depthFirstSearch(root, goal);
                    break;
                case 3:
                    solution = iterativeDeepeningSearch(root, goal);
                    break;
                case 4:
                    solution = uniformCostSearch(root, goal);
                    break;
                case 5:
                    solution = greedyBestFirstSearch(root, goal);
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 0:
                    finished = true;
                    break;

                default:
                    System.out.println("INVALID SELECTION");
                    break;
            }

            for (Node node : solution) {
                System.out.println(node.toString());
            }

        }


        // TEST STUFF
/*        byte[] g = { 1, 2, 3, 8, 0, 4, 7, 6, 5 };
        byte[] e = { 1, 3, 4, 8, 6, 2, 7, 0, 5 };
        byte[] m = { 2, 8, 1, 0, 4, 3, 7, 6, 5 };
        byte[] h = { 5, 6, 7, 4, 0, 8, 3, 2, 1 };*/


        Instant start = Instant.now();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + "ms\n");


    }



    public static ArrayList<Node> breadthFirstSearch(Node root, ArrayList<Byte> goal) {

        if (root.getState().equals(goal)) {
            return root.getPath();
        }

        HashMapDeque searchQueue = new HashMapDeque();
        searchQueue.add(root);

        HashSet<ArrayList<Byte>> explored = new HashSet<>();

        while(!searchQueue.isEmpty()) {
            Node node = searchQueue.remove();
            ArrayList<Byte> nodeState = node.getState();

            explored.add(nodeState);

            node.setExpanded(true);

            for(Action action : node.getActions()) {
                ArrayList<Byte> childState = BoardUtilities.performAction(nodeState, action);
                if (!explored.contains(childState) && !searchQueue.contains(childState)) {
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

        // If no path is found, return an empty ArrayList
        return new ArrayList<>();

    }

    /**
     * Performs depthLimitedSearch with the maximum possible int value as the depth limit
     * Will return an empty path if maximum depth limit is exceeded
     * @param root search tree node containing the initial puzzle board state
     * @param goal the goal puzzle board state
     * @return path from initial puzzle board state to goal puzzle board state or empty path
     */
    public static ArrayList<Node> depthFirstSearch(Node root, ArrayList<Byte> goal) {
        return depthLimitedSearch(root, goal, Integer.MAX_VALUE);
    }

    public static ArrayList<Node> depthLimitedSearch(Node root, ArrayList<Byte> goal, int maxDepth) {

        if (root.getState().equals(goal)) {
            return root.getPath();
        }

        HashMapDeque searchQueue = new HashMapDeque();
        searchQueue.add(root);

        HashSet<ArrayList<Byte>> explored = new HashSet<>();

        while(!searchQueue.isEmpty()) {
            Node node = searchQueue.remove();

            // Node is eligible for expansion only if less than maxDepth
            // Goal check occurs at time of node creation, not expansion
            if (node.getDepth() < maxDepth) {
                ArrayList<Byte> nodeState = node.getState();

                explored.add(nodeState);

                node.setExpanded(true);

                for(Action action : node.getActions()) {
                    ArrayList<Byte> childState = BoardUtilities.performAction(nodeState, action);
                    if (!explored.contains(childState) && !searchQueue.contains(childState)) {
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

        }

        // If no path is found, return an empty ArrayList
        return new ArrayList<>();

    }

    public static ArrayList<Node> iterativeDeepeningSearch(Node root, ArrayList<Byte> goal) {
        ArrayList<Node> path = null;

        for(int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            path = depthLimitedSearch(root, goal, depth);
            if (!path.isEmpty()) {
                return path;
            }
        }

        return new ArrayList<>();
    }

    public static ArrayList<Node> uniformCostSearch(Node root, ArrayList<Byte> goal) {

        HashMapPriorityQueue searchQueue = new HashMapPriorityQueue(new PathCostComparator());
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
                ArrayList<Byte> childState = BoardUtilities.performAction(nodeState, action);
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

    public static ArrayList<Node> greedyBestFirstSearch(Node root, ArrayList<Byte> goal) {

        HashMapPriorityQueue searchQueue = new HashMapPriorityQueue(new MisplacedTileComparator(goal));
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
                ArrayList<Byte> childState = BoardUtilities.performAction(nodeState, action);
                Node child = new Node(childState, node, action);

                if (!explored.contains(childState) && !searchQueue.contains(childState)) {
                    searchQueue.add(child);
                    node.addChild(child);
                }

            }
        }

        // Return empty ArrayList if no path found
        return new ArrayList<>();
    }

    public static ArrayList<Byte> generateBoard(byte[] byteBoard) {
        ArrayList<Byte> board = new ArrayList<>();

        for (byte b : byteBoard) {
            board.add(b);
        }

        return board;
    }



}
