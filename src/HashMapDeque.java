import java.util.*;

public class HashMapDeque {
    ArrayDeque<Node> deque;
    HashMap<ArrayList<Byte>, Node> stateNodeMap;

    int totalNodesDequeued = 0;
    int maxQueueSize = 0;

    public HashMapDeque() {
        this.deque = new ArrayDeque<>();
        this.stateNodeMap = new HashMap<>();
    }

    public void add(Node node) {
        deque.add(node);
        stateNodeMap.put(node.getState(), node);
        maxQueueSize = Math.max(deque.size(), maxQueueSize);
    }

    public void addFirst(Node node) {
        deque.addFirst(node);
        stateNodeMap.put(node.getState(), node);
        maxQueueSize = Math.max(deque.size(), maxQueueSize);
    }

    public Node remove() {
        Node node = deque.remove();
        stateNodeMap.remove(node.getState());
        totalNodesDequeued++;
        return node;
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public boolean contains(ArrayList<Byte> state) {
        return stateNodeMap.containsKey(state);
    }

    public Node getNode(ArrayList<Byte> state) { return stateNodeMap.get(state); }

    public void printStats() {
        System.out.println("  Total nodes dequeued: " + totalNodesDequeued);
        System.out.println("Max queue size reached: " + maxQueueSize);
    }
}
