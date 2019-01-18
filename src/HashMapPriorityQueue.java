import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Wrapper for Java PriorityQueue that adds a HashMap for constant time lookup by Node state
 */
public class HashMapPriorityQueue {
    PriorityQueue<Node> priorityQueue;
    HashMap<ArrayList<Byte>, Node> stateNodeMap;

    int totalNodesDequeued = 0;
    int maxQueueSize = 0;

    public HashMapPriorityQueue(Comparator comparator) {
        this.priorityQueue = new PriorityQueue<>(1, comparator);
        this.stateNodeMap = new HashMap<>();
    }

    public void add(Node node) {
        priorityQueue.add(node);
        stateNodeMap.put(node.getState(), node);
        maxQueueSize = Math.max(priorityQueue.size(), maxQueueSize);
    }

    public Node remove() {
        Node node = priorityQueue.remove();
        stateNodeMap.remove(node.getState());
        totalNodesDequeued++;
        return node;
    }

    public Node remove(ArrayList<Byte> state) {
        Node node = stateNodeMap.get(state);
        this.priorityQueue.remove(node);
        this.stateNodeMap.remove(state);
        return node;
    }

    public void replace(ArrayList<Byte> state, Node newNode) {
        this.remove(state);
        this.add(newNode);
    }

    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    public boolean contains(ArrayList<Byte> state) {
        return stateNodeMap.containsKey(state);
    }

    public int getPathCost(ArrayList<Byte> state) {
        return stateNodeMap.get(state).getPathCost();
    }

    public Node getNode(ArrayList<Byte> state) { return stateNodeMap.get(state); }

    public void printStats() {
        System.out.println("  Total nodes dequeued: " + totalNodesDequeued);
        System.out.println("Max queue size reached: " + maxQueueSize);
    }

}
