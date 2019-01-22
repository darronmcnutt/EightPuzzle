import java.util.*;

/**
 * Wrapper for Java's ArrayDeque adding a HashMap for constant time lookup of
 * Node objects by state. Keeps track of total number of nodes dequeued via the remove
 * method and the maximum size of the queue. NOTE: generally a bad idea to hash
 * mutable data, however within the context of this program the ArrayList of Bytes
 * is never modified once created.
 */
public class HashMapDeque {

    private final ArrayDeque<Node> deque;
    private final HashMap<ArrayList<Byte>, Node> stateNodeMap;

    private int totalNodesDequeued = 0;
    private int maxQueueSize = 0;

    public HashMapDeque() {
        this.deque = new ArrayDeque<>();
        this.stateNodeMap = new HashMap<>();
    }

    /**
     * Adds a Node object to the end of the queue
     * @param node Node object
     */
    public void add(Node node) {
        deque.add(node);
        stateNodeMap.put(node.getState(), node);
        maxQueueSize = Math.max(deque.size(), maxQueueSize);
    }

    /**
     * Adds a Node object to the front of the queue
     * @param node Node object
     */
    public void addFirst(Node node) {
        deque.addFirst(node);
        stateNodeMap.put(node.getState(), node);
        maxQueueSize = Math.max(deque.size(), maxQueueSize);
    }

    /**
     * Removes a Node object from the front of the queue and returns it
     * @return the removed Node object
     */
    public Node remove() {
        Node node = deque.remove();
        stateNodeMap.remove(node.getState());
        totalNodesDequeued++;
        return node;
    }

    /**
     * Checks whether the queue is empty
     * @return true or false
     */
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    /**
     * Checks whether a particular puzzle board state is currently on the queue
     * @param state puzzle board state
     * @return true or false
     */
    public boolean contains(ArrayList<Byte> state) {
        return stateNodeMap.containsKey(state);
    }

    /**
     * Prints total nodes dequeued and max queue size reached
     */
    public void printStats() {
        System.out.println("  Total nodes dequeued: " + totalNodesDequeued);
        System.out.println("Max queue size reached: " + maxQueueSize);
    }

    /**
     * Checks whether a node with the same state is on the queue with a larger depth value
     * @param state puzzle board state to test
     * @param depth depth of the puzzle board state
     * @return true or false
     */
    public boolean isStateOnQueueWithLargerDepth(ArrayList<Byte> state, int depth) {
        if (stateNodeMap.containsKey(state)) {
            Node node = stateNodeMap.get(state);
            if (depth < node.getDepth()) {
                return true;
            }
        }

        return false;
    }


    public Node getNode(ArrayList<Byte> state) { return stateNodeMap.get(state); }
}
