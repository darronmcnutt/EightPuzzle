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
                    solution = aStarOneSearch(root, goal);
                    break;
                case 7:
                    solution = aStarTwoSearch(root, goal);
                    break;
                case 8:
                    solution = aStarThreeSearch(root, goal);
                    break;
                case 0:
                    finished = true;
                    break;

                default:
                    System.out.println("INVALID SELECTION");
                    break;
            }

            System.out.println("Press enter for results: ");
            input.nextLine();

            for (Node node : solution) {
                System.out.println(node.toString());
            }


        }


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
                        searchQueue.printStats();
                        return child.getPath();
                    }
                    // BFS: add to END of searchQueue
                    searchQueue.add(child);
                }
            }

        }

        // If no path is found, return an empty ArrayList
        searchQueue.printStats();
        return new ArrayList<>();

    }

    /**
     * Performs depthLimitedSearch with Java's maximum possible int value as the depth limit
     * Will return an empty path if maximum depth limit is exceeded or no path to the goal state is found
     * @param root node containing the initial puzzle board state
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
                            searchQueue.printStats();
                            return child.getPath();
                        }
                        // DFS: add to FRONT of searchQueue
                        searchQueue.addFirst(child);
                    }
                }
            }

        }

        // If no path is found, return an empty ArrayList
        searchQueue.printStats();
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


    public static ArrayList<Node> comparatorCostSearch(Node root, ArrayList<Byte> goal, Comparator comparator) {

        HashMapPriorityQueue searchQueue = new HashMapPriorityQueue(comparator);
        searchQueue.add(root);

        HashSet<ArrayList<Byte>> explored = new HashSet<>();

        while(!searchQueue.isEmpty()) {

            Node node = searchQueue.remove();
            ArrayList<Byte> nodeState = node.getState();

            if (nodeState.equals(goal)) {
                searchQueue.printStats();
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
                        && (comparator.compare(searchQueue.getNode(childState), child) > 0)) {
                    searchQueue.replace(childState, child);
                    node.addChild(child);
                }

            }
        }

        // Return empty ArrayList if no path found
        searchQueue.printStats();
        return new ArrayList<>();
    }


    public static ArrayList<Node> uniformCostSearch(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostComparator());
    }

    public static ArrayList<Node> greedyBestFirstSearch(Node root, ArrayList<Byte> goal) {
        // Some efficiency has been sacrificed for the purpose of code reusability here
        // The else-if block in comparatorCostSearch is unnecessary for greedy best first search
        return comparatorCostSearch(root, goal, new MisplacedTileComparator(goal));
    }


    public static ArrayList<Node> aStarOneSearch(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostMisplacedTileComparator(goal));
    }

    public static ArrayList<Node> aStarTwoSearch(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostManhattanDistancesComparator(goal));
    }

    /**
     * Uses the heuristic suggested on pages 105 and 119 of Russel/Norvig: Artificial Intelligence
     * Relaxed problem: "A tile can move from square A to square B if B is blank"
     * @param root search tree node containing the initial puzzle board state
     * @param goal the goal puzzle board state
     * @return path from initial puzzle board state to goal puzzle board state or empty path
     */
    public static ArrayList<Node> aStarThreeSearch(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostZeroSwapComparator(goal));
    }

    public static ArrayList<Byte> generateBoard(byte[] byteArray) {
        ArrayList<Byte> board = new ArrayList<>();

        for (byte b : byteArray) {
            board.add(b);
        }

        return board;
    }



}
