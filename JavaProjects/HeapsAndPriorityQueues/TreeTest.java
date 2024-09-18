public class TreeTest {
    public static void main(String[] args) {
        // Test TreePQ
        // Create a TreePQ instance with Integer keys and String values
        TreePQ<Integer, String> pq = new TreePQ<>();

        // Test isEmpty on an empty priority queue
        System.out.println("Is the priority queue empty? " + pq.isEmpty()); // Expected: true

        // Test insert
        System.out.println("Inserting entries into the priority queue...");
        pq.insert(5, "Five");
        pq.insert(1, "One");
        pq.insert(3, "Three");
        pq.insert(2, "Two");
        pq.insert(4, "Four");

        // Test size after insertions
        System.out.println("Size of the priority queue after insertions: " + pq.size()); // Expected: 5

        // Test isEmpty on a non-empty priority queue
        System.out.println("Is the priority queue empty? " + pq.isEmpty()); // Expected: false

        // Test min
        System.out.println("Minimum entry: " + pq.min().getKey() + " => " + pq.min().getValue()); // Expected: 1 => One

        // Test toString
        System.out.println("Priority Queue: " + pq.toString());

        // Test removeMin and verify the heap order is maintained
        while (!pq.isEmpty()) {
            Entry<Integer, String> entry = pq.removeMin();
            System.out.println("Removed Entry: " + entry.getKey() + " => " + entry.getValue());
        }

        // Test size after all removals
        System.out.println("Size of the priority queue after all removals: " + pq.size()); // Expected: 0

        // Test isEmpty after all removals
        System.out.println("Is the priority queue empty? " + pq.isEmpty()); // Expected: true
    }
}
