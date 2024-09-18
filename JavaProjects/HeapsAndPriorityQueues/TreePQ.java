/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Robert Jarman
*/

import java.security.KeyStore.Entry;
import java.util.Comparator;

/* This class extends AbstractPriorityQueue and implements the PriorityQueue
 * it stores the Heap Tree and does advanced commands to make it a priority queue
 * methods contained include changePriority, findPosition, and removeMax
 * which use some methods in HeapTree to perform the functions
 * overriden methods include size, insert, min, removeMin, removeMax, 
 * and toString
 * 3 constructors for TreePQ are provided for each instance the user might
 * use for the class
 * 2 comparator methods: one static and one instance to compare node keys or entries
 * NO lists was used in any way in this class
*/
public class TreePQ<Key, Value> extends AbstractPriorityQueue<Key, Value>{
    // Instance variable for HeapTree to be used throughout class
    protected static class PQEntry<Key,Value> implements Entry<Key, Value>{
        private Key k;
        private Value v;
        private double x;
        private double y;
    }

    public PQEntry(Key k, Value v){
        this.k = k;
        this.v = v;
    }

    // methods of Entry Interface
    public Key getKey(){
        return k;
    }

    // utilities not part of Entry interface
    protected void setKey(Key k){
        this.k = k;
    }
    protected void setValue(Value v){
        this.v = v;
    }
    public String toString(){
        return "(" + k + ", " + v + " )";
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    // a comparator: which will be sent into HeapTree.java for comparison
    protected static class TreeComparator<Key> implements Comparator<Key> {
        // override method that has to be constructed
        @Override
        public int compare(Key v, Key w){
            return ((Comparable<Key>) v).compareTo(w);
        }

        // a compare method that will be called in HeapTree.java to compare PQEntry
        public int compare(PQEntry v, PQEntry w){
            Key v_Key = (Key) v.getKey();
            Key w_Key = (Key) w.getKey();
            return compare(v_Key, w_Key);
        }
    }

    protected Comparator<Key> comp;
    protected HeapTree<PQEntry> heap;

    // constructor

    protected TreePQ(Comparator<Key> c){
        comp = c;
        HeapTree<TreePQ.PQEntry> heap = new HeapTree<>();
        this.heap = heap;
    }

    // default constructor
    protected TreePQ(){
        this(new TreeComparator<>());
    }

    @Override
    protected int compare(Entry<Key, Value> v, Entry<Key, Value> w){
        return comp.compare(v.getKey(), w.getKey());
    }

    // return the size of the current heap
    public int size(){
        return heap.size();
    }

    //insert element to TreePQ
    public Entry<Key,Value> insert(Key k, Value v){
        PQEntry<Key, Value> newEntry = new PQEntry<>(k, v);
        heap.addNodeToHeap(newEntry, (Comparator<Object>) comp);
        return newEntry;
    }

    //a peek to the minimum element
    public Entry<Key, Value> min(){
        return heap.root.getElement();
    }

    // remove and return the minimum element
    public Entry<Key, Value> removeMin(){
        return this.heap.remove(heap.root, (Comparator<Object>) comp);
    }
}