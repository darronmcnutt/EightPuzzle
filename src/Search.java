import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Static utility class of graph search functions
 */
public class Search {

    private Search() { }

    /**
     * Breadth-first search
     * @param root Node containing the problem state of the puzzle board
     * @param goal ArrayList of Bytes representing the goal state of the puzzle board
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> breadthFirst(Node root, ArrayList<Byte> goal) {

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
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> depthFirst(Node root, ArrayList<Byte> goal) {
        return depthLimited(root, goal, Integer.MAX_VALUE, new HashMapDeque());
    }

    /**
     * Depth-limited search. Can be used to perform depth-first search with Integer.MAX_VALUE as depth limit
     * @param root node containing the initial puzzle board state
     * @param goal the goal puzzle board state
     * @param maxDepth depth limit for the search
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> depthLimited(Node root, ArrayList<Byte> goal, int maxDepth, HashMapDeque searchQueue) {

        if (root.getState().equals(goal)) {
            return root.getPath();
        }

        searchQueue.add(root);
        HashSet<ArrayList<Byte>> explored = new HashSet<>();

        while(!searchQueue.isEmpty()) {
            Node node = searchQueue.remove();
            ArrayList<Byte> nodeState = node.getState();
            explored.add(nodeState);

            // Node is eligible for expansion only if node depth is less than maxDepth
            // Goal check occurs at time of node creation, not expansion
            if (node.getDepth() < maxDepth) {

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

    /**
     * Iterative Deepening search. Repeatedly calls depth limited search function.
     * @param root Node containing the problem state of the puzzle board
     * @param goal ArrayList of Bytes representing the goal state of the puzzle board
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> iterativeDeepening(Node root, ArrayList<Byte> goal) {
        ArrayList<Node> path = null;

        HashMapDeque searchQueue = new HashMapDeque();

        for(int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            path = depthLimited(root, goal, depth, searchQueue);
            if (!path.isEmpty()) {
                return path;
            }
        }

        return new ArrayList<>();
    }

    /**
     * Helper function used for all cost-based search strategies
     * @param root Node containing the problem state of the puzzle board
     * @param goal ArrayList of Bytes representing the goal state of the puzzle board
     * @param comparator The evaluation function f(n) = g(n) + h(n)
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> comparatorCostSearch(Node root, ArrayList<Byte> goal, Comparator<Node> comparator) {

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

    /**
     * Uniform cost search. Uses comparatorCostSearch helper with PathCostComparator
     * @param root Node containing the problem state of the puzzle board
     * @param goal ArrayList of Bytes representing the goal state of the puzzle board
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> uniformCost(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostComparator());
    }

    /**
     * Greedy best first search. Uses comparatorCostSearch helper with MisplacedTileComparator
     * @param root Node containing the problem state of the puzzle board
     * @param goal ArrayList of Bytes representing the goal state of the puzzle board
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> greedyBestFirst(Node root, ArrayList<Byte> goal) {
        // Some efficiency has been sacrificed for the purpose of code reusability here
        // The else-if block in comparatorCostSearch is unnecessary for greedy best first search
        return comparatorCostSearch(root, goal, new MisplacedTileComparator(goal));
    }

    /**
     * A-star 1 search. Uses comparatorCostSearch helper with PathCostMisplacedTilesComparator
     * @param root Node containing the problem state of the puzzle board
     * @param goal ArrayList of Bytes representing the goal state of the puzzle board
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> aStarOne(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostMisplacedTilesComparator(goal));
    }

    /**
     * A-star 2 search. Uses comparatorCostSearch helper with PathCostManhattanDistanceComparator
     * @param root Node containing the problem state of the puzzle board
     * @param goal ArrayList of Bytes representing the goal state of the puzzle board
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> aStarTwo(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostManhattanDistanceComparator(goal));
    }

    /**
     * A-star 3 search. Uses comparatorCostSearch helper with PathCostManhattanCostComparator
     * @param root Node containing the problem state of the puzzle board
     * @param goal ArrayList of Bytes representing the goal state of the puzzle board
     * @return path from root Node to goal state, represented as an ArrayList of Nodes. Empty ArrayList if no path found.
     */
    public static ArrayList<Node> aStarThree(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostManhattanCostComparator(goal));
    }

    /**
     * Uses the heuristic suggested on pages 105 and 119 of Russel/Norvig: Artificial Intelligence
     * Relaxed problem: "A tile can move from square A to square B if B is blank"
     * @param root search tree node containing the initial puzzle board state
     * @param goal the goal puzzle board state
     * @return path from initial puzzle board state to goal puzzle board state or empty path
     */
    public static ArrayList<Node> aStarFour(Node root, ArrayList<Byte> goal) {
        return comparatorCostSearch(root, goal, new PathCostZeroSwapComparator(goal));
    }

    /**
     * Menu allowing the user to select a search strategy and run the search
     * @param board puzzle board representing the problem state
     * @param goal puzzle board representing the goal state
     * @return true or false (lets Main() know if the user wishes to quit)
     */
    public static boolean selectAndRunSearch(ArrayList<Byte> board, ArrayList<Byte> goal) {
        Node root = new Node(board);
        ArrayList<Node> solution = null;
        Instant start, end;

        while (true) {
            System.out.println("------------------------------------");
            System.out.println("      Choose a search strategy");
            System.out.println("------------------------------------");
            System.out.println("1 - Breadth-first");
            System.out.println("2 - Depth-first");
            System.out.println("3 - Iterative deepening");
            System.out.println("4 - Uniform-cost");
            System.out.println("5 - Best-first (Greedy)");
            System.out.println("6 - A*1 (number of misplaced tiles)");
            System.out.println("7 - A*2 (sum of Manhattan distances)");
            System.out.println("8 - A*3 (sum of Manhattan costs)");
            System.out.println("9 - Run all searches (stats only)");
            System.out.println("------------------------------------");
            System.out.println("10 - Return to difficulty level menu");
            System.out.println("11 - Quit");
            System.out.println("------------------------------------");
            System.out.print("YOUR SELECTION: ");

            Scanner input = new Scanner(System.in);
            int option = input.nextInt();

            switch (option) {
                case 1:
                    System.out.println("------------------------------------");
                    System.out.println("       Breadth-first search");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = breadthFirst(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    break;
                case 2:
                    System.out.println("------------------------------------");
                    System.out.println("         Depth-first search");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = depthFirst(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    break;
                case 3:
                    System.out.println("------------------------------------");
                    System.out.println("        Iterative deepening");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = iterativeDeepening(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    break;
                case 4:
                    System.out.println("------------------------------------");
                    System.out.println("        Uniform-cost search");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = uniformCost(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    break;
                case 5:
                    System.out.println("------------------------------------");
                    System.out.println("        Best-first (Greedy)");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = greedyBestFirst(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    break;
                case 6:
                    System.out.println("------------------------------------");
                    System.out.println("  A*1 (number of misplaced tiles)");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = aStarOne(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    break;
                case 7:
                    System.out.println("------------------------------------");
                    System.out.println("  A*2 (sum of Manhattan distances)  ");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = aStarTwo(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    break;
                case 8:
                    System.out.println("------------------------------------");
                    System.out.println("     A*3 (sum of Manhattan costs)   ");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = aStarThree(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    break;
                case 9:
                    runAllSearches(board, goal);
                    return false;
                case 10:
                    return false;
                case 11:
                    return true;

                default:
                    System.out.println("INVALID SELECTION");
                    break;
            }

            System.out.println("\n\nPress ENTER to view search path");
            input.nextLine(); input.nextLine();

            for (Node node : solution) {
                System.out.println(node.toString());
            }

//            // Print with heuristics
//            for (Node node : solution) {
//                System.out.println("h1(n) = " + BoardUtilities.getMisplacedTilesCost(node.getState(), goal) +
//                                   " h2(n) = " + BoardUtilities.getSumManhattanCosts(node.getState(), goal) +
//                                   " h3(n) = " + BoardUtilities.getZeroSwapCost(node.getState(), goal));
//                System.out.println("f(n) = " + (node.getPathCost() + BoardUtilities.getMisplacedTilesCost(node.getState(), goal)) +
//                        " f(n) = " + (node.getPathCost() + BoardUtilities.getSumManhattanCosts(node.getState(), goal)) +
//                        " f(n) = " + (node.getPathCost() + BoardUtilities.getZeroSwapCost(node.getState(), goal)));
//                System.out.println(node.toString());
//            }

        }

    }


    public static void runAllSearches(ArrayList<Byte> board, ArrayList<Byte> goal) {
        Node root = new Node(board);
        ArrayList<Node> solution = null;
        Instant start, end;

        for(int option = 1; option < 9; option++) {
            switch (option) {
                case 1:
                    System.out.println("------------------------------------");
                    System.out.println("       Breadth-first search");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = breadthFirst(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    System.out.println(solution.get(solution.size() - 1).toString());
                    break;
                case 2:
                    System.out.println("------------------------------------");
                    System.out.println("         Depth-first search");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = depthFirst(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    System.out.println(solution.get(solution.size() - 1).toString());
                    break;
                case 3:
                    System.out.println("------------------------------------");
                    System.out.println("        Iterative deepening");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = iterativeDeepening(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    System.out.println(solution.get(solution.size() - 1).toString());
                    break;
                case 4:
                    System.out.println("------------------------------------");
                    System.out.println("        Uniform-cost search");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = uniformCost(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    System.out.println(solution.get(solution.size() - 1).toString());
                    break;
                case 5:
                    System.out.println("------------------------------------");
                    System.out.println("        Best-first (Greedy)");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = greedyBestFirst(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    System.out.println(solution.get(solution.size() - 1).toString());
                    break;
                case 6:
                    System.out.println("------------------------------------");
                    System.out.println("  A*1 (number of misplaced tiles)");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = aStarOne(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    System.out.println(solution.get(solution.size() - 1).toString());
                    break;
                case 7:
                    System.out.println("------------------------------------");
                    System.out.println("  A*2 (sum of Manhattan distances)  ");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = aStarTwo(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    System.out.println(solution.get(solution.size() - 1).toString());
                    break;
                case 8:
                    System.out.println("------------------------------------");
                    System.out.println("       A*3 (custom heuristic) ");
                    System.out.println("------------------------------------");

                    start = Instant.now();
                    solution = aStarThree(root, goal);
                    end = Instant.now();
                    System.out.println("        Execution time: " + Duration.between(start, end).toMillis() + "ms\n");
                    System.out.println(solution.get(solution.size() - 1).toString());
                    break;

            }
        }

    }
}
