import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Wrapper for Java PriorityQueue that adds a HashMap for constant time lookup by Node state
 */
public class PathCostPriorityQueue {
    PriorityQueue<Node> priorityQueue;
    HashMap<ArrayList<Byte>, Node> stateNodeMap;

    public PathCostPriorityQueue() {
        this.priorityQueue = new PriorityQueue<>(1, new PathCostComparator());
        this.stateNodeMap = new HashMap<>();
    }

    public void add(Node node) {
        priorityQueue.add(node);
        stateNodeMap.put(node.getState(), node);
    }

    public Node remove() {
        Node node = priorityQueue.remove();
        stateNodeMap.remove(node.getState());
        return node;
    }

    public Node remove(ArrayList<Byte> state) {
        Node node = stateNodeMap.get(state);
        this.priorityQueue.remove(node);
        this.stateNodeMap.remove(state);
        return node;
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

}
