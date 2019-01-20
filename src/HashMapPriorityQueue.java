import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Wrapper for Java's PriorityQueue adding a HashMap for constant time lookup of
 * Node objects by state. Keeps track of total number of nodes dequeued via the remove()
 * method and the maximum size of the queue.
 */
public class HashMapPriorityQueue {
    private PriorityQueue<Node> priorityQueue;
    private HashMap<ArrayList<Byte>, Node> stateNodeMap;

    private int totalNodesDequeued = 0;
    private int maxQueueSize = 0;

    public HashMapPriorityQueue(Comparator<Node> comparator) {
        this.priorityQueue = new PriorityQueue<>(1, comparator);
        this.stateNodeMap = new HashMap<>();
    }

    /**
     * Adds a Node object to the end of the queue
     * @param node Node object
     */
    public void add(Node node) {
        priorityQueue.add(node);
        stateNodeMap.put(node.getState(), node);
        maxQueueSize = Math.max(priorityQueue.size(), maxQueueSize);
    }


    /**
     * Removes a Node object from the front of the queue and returns it
     * @return the removed Node object
     */
    public Node remove() {
        Node node = priorityQueue.remove();
        stateNodeMap.remove(node.getState());
        totalNodesDequeued++;
        return node;
    }


    /**
     * Removes a particular Node from the queue.
     * Does NOT increment total nodes dequeued
     * @param state puzzle board state to remove from the queue
     * @return the removed Node object
     */
    public Node remove(ArrayList<Byte> state) {
        Node node = stateNodeMap.get(state);
        this.priorityQueue.remove(node);
        this.stateNodeMap.remove(state);
        return node;
    }

    /**
     * Replaces a specified Node in the queue with a new Node
     * Does NOT increment total nodes dequeued
     * @param state puzzle board state of the Node to be removed from the queue
     * @param newNode the new Node to be added to the queue
     */
    public void replace(ArrayList<Byte> state, Node newNode) {
        this.remove(state);
        this.add(newNode);
    }

    /**
     * Checks whether the queue is empty
     * @return true or false
     */
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
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
     * Searches for a particular Node in the queue by state and returns it
     * @param state puzzle board state to search for
     * @return Node object containing the puzzle board state
     */
    public Node getNode(ArrayList<Byte> state) { return stateNodeMap.get(state); }

    /**
     * Prints total nodes dequeued and max queue size reached
     */
    public void printStats() {
        System.out.println("  Total nodes dequeued: " + totalNodesDequeued);
        System.out.println("Max queue size reached: " + maxQueueSize);
    }

}
