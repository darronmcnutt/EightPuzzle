import java.util.*;

public class HashMapDeque {
    ArrayDeque<Node> deque;
    HashMap<ArrayList<Byte>, Node> stateNodeMap;

    public HashMapDeque() {
        this.deque = new ArrayDeque<>();
        this.stateNodeMap = new HashMap<>();
    }
    public void add(Node node) {
        deque.add(node);
        stateNodeMap.put(node.getState(), node);
    }

    public void addFirst(Node node) {
        deque.addFirst(node);
        stateNodeMap.put(node.getState(), node);
    }

    public Node remove() {
        Node node = deque.remove();
        stateNodeMap.remove(node.getState());
        return node;
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public boolean contains(ArrayList<Byte> state) {
        return stateNodeMap.containsKey(state);
    }

    public Node getNode(ArrayList<Byte> state) { return stateNodeMap.get(state); }
}
