/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Robert Jarman
*/
// left and right children are backwards, focus on #3 to fix #1, 2, 4, 1 should be root, 4 is left child and 3 right child; swap method; lopsided tree (all left, no right); insert
// heap priority queue
import java.util.Comparator;

/* 
 * This class extension of LinkedBinaryTree class which adds the necessary 
 * tree operations that the priority queue will make use of, such as swap,
 * upHeap, downHeap, insert, remove the root, remove the max node, and 
 * a right most node method helper method for TreePQ
 * NO lists was used in any way in this class
*/
public class HeapTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {
    // Instance comparator for HeapTree nodes
    private Comparator<K> comparator;
    // variable size = 0

    // Constructor for HeapTree with comparator
    public HeapTree(Comparator<K> comparator){
        super();
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        this.comparator = comparator;
    }

    // Default constructor
    public HeapTree() {
        // Extends LinkedBinaryTree class and initializes with a default 
        // comparator
        super();
        // Initialize with a default comparator if needed
        this.comparator = new DefaultComparator<>(); 
    }

    // Method used to swap 2 positions in a tree without traversing 
    // the whole tree
    // could be Node<Entry<K,V>>, imagine E is Entry<K,V>
    public void swap(Position<Entry<K, V>> p1, Position<Entry<K,V>> p2){
        Entry<K,V> temp = p1.getElement();
        set(p1, p2.getElement());
        set(p2, temp);
    }

    // Method used to move a node up in a tree 1 position at a time 
    // and used for insert method
    // Time Complexity is O(log n)
    public void upHeap(Position<Entry<K,V>> p){
        // Traverses HeapTree upwards until it reaches the root
        while(p != null){
            Position<Entry<K,V>> parent = parent(p);
            if (parent == null || compare(p.getElement(), parent.getElement()) >= 0) {
                break;
            } else {
                swap(p, parent);
                p = parent;
            }
        }
    }

    protected int compare(Entry<K,V> e1, Entry<K,V> e2) {
		return comparator.compare(e1.getKey(), e2.getKey());
	}

    // Method used to move a node down in a tree and used for remove method
    // Time Complexity is O(log n)
    public void downHeap(Position<Entry<K, V>> p){
        // Traverses HeapTree downwards until it reaches a leaf
        while(left(p) != null){
            // left() uses BinaryTree instead of getLeft uses LinkedBinaryTree
            Position<Entry<K, V>> leftChild = left(p);
            Position<Entry<K, V>> rightChild = right(p);
            Position<Entry<K, V>> minChild;

            if(rightChild != null && compare(leftChild.getElement(), rightChild.getElement()) > 0){
                minChild = rightChild;
            } else {
                minChild = leftChild;
            }
            
            if(compare(p.getElement(), minChild.getElement()) <= 0){
                break;
            } else {
                swap(p, minChild);
                p = minChild;    
            }
        }
    }
    public void insert(Entry<K, V> element) {
        Position<Entry<K, V>> newNode = null;
        if (isEmpty()) {
            addRoot(element);
        } else {
            Position<Entry<K, V>> current = root();
            int leftIndex = 2*size() + 1;
            int rightIndex = 2*size() + 2;
            
            while (true) {
            if (leftIndex < size()) {
                if (left(current) == null) {
                    newNode = addLeft(current, element);
                    break;
                } else {
                    current = left(current);
                }
            } else if (rightIndex < size()) {
                if (right(current) == null) {
                    newNode = addRight(current, element);
                    break;
                } else {
                    current = right(current);
                } 
            }

            leftIndex /= 2;
            rightIndex /= 2;
            }
        }
        upHeap(newNode);
    }

    // Method used to remove and return the minimum element from the heap
    // to replace with the next min node
    public Entry<K, V> remove() {
        if (isEmpty()) {
            return null;
        }

        Entry<K, V> minElement = root().getElement();
        
        if (size() == 1) {
            remove(root());
        } else {
            // creates a tracer node to traverse the heap tree
            Position<Entry<K, V>> current = root();
            
            // Traverse down the tree to find the last node AKA the leaf
            // to swap with top "great-grandfather" node
            while (left(current) != null) {
                current = left(current);
            }
            Position<Entry<K, V>> lastNode = current;
            set(root(), lastNode.getElement());
            remove(lastNode);
            downHeap(root());
        }
        return minElement;
    }
}