import java.util.*;

/**
 * Wrapper for Java's ArrayDeque adding a HashMap for constant time lookup of
 * Node objects by state. Keeps track of total number of nodes dequeued via the remove
 * method and the maximum size of the queue.
 */
public class HashMapDeque {
    ArrayDeque<Node> deque;
    HashMap<ArrayList<Byte>, Node> stateNodeMap;

    Stats stats = Stats.getInstance();

    int totalNodesDequeued = 0;
    int maxQueueSize = 0;

    public HashMapDeque() {
        this.deque = new ArrayDeque<>();
        this.stateNodeMap = new HashMap<>();
    }

    /**
     * Adds a Node object to the end of the queue
     * @param node
     */
    public void add(Node node) {
        deque.add(node);
        stateNodeMap.put(node.getState(), node);
        maxQueueSize = Math.max(deque.size(), maxQueueSize);
    }

    /**
     * Adds a Node object to the front of the queue
     * @param node
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

    public Node getNode(ArrayList<Byte> state) { return stateNodeMap.get(state); }

    /**
     * Prints total nodes dequeued and max queue size reached
     */
    public void printStats() {

        // Update Stats singleton (used for iterative deepening)
        stats.setTotalNodesDequeued(stats.getTotalNodesDequeued() + totalNodesDequeued);
        stats.setMaxQueueSize(Math.max(stats.getMaxQueueSize(), maxQueueSize));

        System.out.println("  Total nodes dequeued: " + totalNodesDequeued);
        System.out.println("Max queue size reached: " + maxQueueSize);
    }
}
