public class SkipListTest {
    public static void main(String[] args) {
        SkipList list = new SkipList();

        // Insert entries
        list.put("A", 1);
        list.put("C", 2);
        list.put("B", 3);

        // Print list
        System.out.println("List after insertion:");
        list.printHorizontal();

        // Test remove
        System.out.println("Removing entry B:");
        Integer removed = list.remove("B");
        System.out.println("Removed: " + removed); 

        // Print list
        System.out.println("List after removing B:"); 
        list.printHorizontal();

        // Test firstEntry
        System.out.println("First entry:");
        SkipListEntry first = list.firstEntry();
        System.out.println(first);

        // Test lastEntry 
        System.out.println("Last entry:");
        SkipListEntry last = list.lastEntry();
        System.out.println(last);

        // Print list
        System.out.println("Final list:");
        list.printHorizontal();

        System.out.println("SkipList tests passed!");
    }
}
