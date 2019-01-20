/**
 * Singleton class used to get stats from iterative deepening
 * This is the only global I use, I promise!
 */
public class Stats {

    private int totalNodesDequeued = 0;
    private int maxQueueSize = 0;

    private static Stats ourInstance = new Stats();

    public static Stats getInstance() {
        return ourInstance;
    }

    private Stats() {
    }

    public int getTotalNodesDequeued() {
        return totalNodesDequeued;
    }

    public void setTotalNodesDequeued(int totalNodesDequeued) {
        this.totalNodesDequeued = totalNodesDequeued;
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public void printStats() {
        System.out.println("------------------------------------");
        System.out.println("  Total nodes dequeued: " + totalNodesDequeued);
        System.out.println("Max queue size reached: " + maxQueueSize);
    }
}
