/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Robert Jarman
*/

import java.util.*;


public class SkipList {
  public SkipListEntry head;    // First element of the top level
  public SkipListEntry tail;    // Last element of the top level


  public int n; 		// number of entries in the Skip list

  public int h;       // Height
  public Random r;    // Coin toss

  /* ----------------------------------------------
     Constructor: empty skiplist

                          null        null
                           ^           ^
                           |           |
     head --->  null <-- -inf <----> +inf --> null
                           |           |
                           v           v
                          null        null
     ---------------------------------------------- */
  public SkipList() {    // Default constructor... 
     SkipListEntry p1, p2;

     p1 = new SkipListEntry(SkipListEntry.negInf, null);
     p2 = new SkipListEntry(SkipListEntry.posInf, null);

     head = p1;
     tail = p2;

     p1.right = p2;
     p2.left = p1;

     n = 0;
     h = 0;

     r = new Random();
  }


  /** Returns the number of entries in the hash table. */
  public int size() { return n; }

  /** Returns whether or not the table is empty. */
  public boolean isEmpty() { return (n == 0); }

  /* ------------------------------------------------------
     findEntry(k): find the largest key x <= k
		   on the LOWEST level of the Skip List
     ------------------------------------------------------ */
  public SkipListEntry findEntry(String k) {
    SkipListEntry p;

    /* -----------------
    Start at "head"
    ----------------- */
    p = head;

    while ( true ) {
      /* --------------------------------------------
      Search RIGHT until you find a LARGER entry

      E.g.: k = 34

      10 ---> 20 ---> 30 ---> 40
                      ^
                      |
                      p stops here
      p.right.key = 40
      -------------------------------------------- */
      while ( p.right.getKey() != SkipListEntry.posInf && 
                    p.right.getKey().compareTo(k) <= 0 ) {
        p = p.right;
      }

      /* ---------------------------------
      Go down one level if you can...
      --------------------------------- */
      if ( p.down != null ) {
        p = p.down;
      }
      else
        break;	// We reached the LOWEST level... Exit...
    }

    return(p);         // p.key <= k
  }


  /** Returns the value associated with a key. */
  public Integer get (String k) {
    SkipListEntry p;

    p = findEntry(k);

    if ( k.equals( p.getKey() ) )
      return(p.getValue());
    else
      return(null);
  }

  /* ------------------------------------------------------------------
     insertAfterAbove(p, q, y=(k,v) )
 
        1. create new entry (k,v)
      	2. insert (k,v) AFTER p
        3. insert (k,v) ABOVE q

             p <--> (k,v) <--> p.right
                      ^
		                  |
		                  v
		                  q

      Returns the reference of the newly created (k,v) entry
     ------------------------------------------------------------------ */
  public SkipListEntry insertAfterAbove(SkipListEntry p, SkipListEntry q, 
                                 String k) {
    SkipListEntry e;

    e = new SkipListEntry(k, null);

    /* ---------------------------------------
    Use the links before they are changed !
    --------------------------------------- */
    e.left = p;
    e.right = p.right;
    e.down = q;

    /* ---------------------------------------
    Now update the existing links..
    --------------------------------------- */
    p.right.left = e;
    p.right = e;
    q.up = e;

    return(e);
  }

  /** Put a key-value pair in the map, replacing previous one if it exists. */
  public Integer put (String k, Integer v) {
    SkipListEntry p, q;
    int i;

    p = findEntry(k);

    /* ------------------------
    Check if key is found
    ------------------------ */
    if ( k.equals( p.getKey() ) ) {
      Integer old = p.getValue();

      p.setValue(v);

      return(old);
    }

    /* --------------------------------------
    Insert new entry (k,v) at the lowet level
    ----------------------------------------- */

    q = new SkipListEntry(k, v);
    q.left = p;
    q.right = p.right;
    p.right.left = q;
    p.right = q;

    i = 0;                   // Current level = 0

    while ( r.nextDouble() < 0.5 ) {
      // Coin flip success: make one more level....

      /* ---------------------------------------------
      Check if height exceed current height.
      If so, make a new EMPTY level
      --------------------------------------------- */
      if ( i >= h ) {
        SkipListEntry p1, p2;

        h = h + 1;

        p1 = new SkipListEntry(SkipListEntry.negInf,null);
        p2 = new SkipListEntry(SkipListEntry.posInf,null);

        p1.right = p2;
        p1.down  = head;

        p2.left = p1;
        p2.down = tail;

        head.up = p1;
        tail.up = p2;

        head = p1;
        tail = p2;
      }


      /* -------------------------
      Scan backwards...
      ------------------------- */
      while ( p.up == null ) {
        p = p.left;
      }

      p = p.up;


      /* ---------------------------------------------
      Add one more (k,v) to the column
      --------------------------------------------- */
      SkipListEntry e;

      e = new SkipListEntry(k, null);  // Don't need the value...

      /* ---------------------------------------
      Initialize links of e
      --------------------------------------- */
      e.left = p;
      e.right = p.right;
      e.down = q;

      /* ---------------------------------------
      Change the neighboring links..
      --------------------------------------- */
      p.right.left = e;
      p.right = e;
      q.up = e;

      q = e;		// Set q up for the next iteration

      i = i + 1;	// Current level increased by 1

    }

    n = n + 1;

    return(null);   // No old value
}

  /** Removes the key-value pair with a specified key. */
  // public Integer remove (String key) { return(null); }
  public Integer remove(String key) {
    Integer v = null;

    // find the entry with the given key
    SkipListEntry p = findEntry(key); 

    // if found, remove it
    if(p.getKey().equals(key)){
        // save the value to return
        v = p.getValue();
        // iterate upwards remove entry at each level
        while(p != null){
          // remove from this level
            p.left.right = p.right;
            p.right.left = p.left;

          // move up a level
            p = p.up;
        }
    }

    return v;
  }
  

  public SkipListEntry firstEntry(){
    // return null if empty
    if(isEmpty()){
      return null;
    }
    SkipListEntry p = head;

    // move down to lowest level
    while(p.down != null){
      p = p.down;
    }

    // return node after head
    p = p.right;
    return p;
  }

  public SkipListEntry lastEntry(){
    // return null if empty
    if(isEmpty()){
      return null;
    }
    SkipListEntry p = tail;

    // move down to lowest level
    while(p.down != null){
      p = p.down;
    }
    
    p = p.left;
    return p;
  }

  public SkipListEntry ceilingEntry(String key){
    // find smallest entry with key >= given key
    SkipListEntry p = findEntry(key);
    if(p.getKey() == key){
      return p;
    }
    else{
      return p.right;
    }
  }

  public SkipListEntry floorEntry(String key){
    // find largest entry with key <= given key
    return findEntry(key);
  }

  public SkipListEntry upperEntry(String key){
    // return entry after given key
    return findEntry(key).right;
  }

  public SkipListEntry lowerEntry(String key){
    SkipListEntry p = findEntry(key);
    // if match, return preceding node
    if (p.getKey().equals(key)){
      return p.left;
    } else {
      // otherwise return findEntry result
      return p;
    }
  }

  public void printHorizontal() {
    String s = "";
    int i;

    SkipListEntry p;

    /* ----------------------------------
    Record the position of each entry
    ---------------------------------- */
    p = head;

    while ( p.down != null ) {
      p = p.down;
    }

    i = 0;
    while ( p != null ) {
      p.pos = i++;
      p = p.right;
    }

    /* -------------------
    Print...
    ------------------- */
    p = head;

    while ( p != null ) {
      s = getOneRow( p );
      System.out.println(s);

      p = p.down;
    }
  }

  public String getOneRow( SkipListEntry p ) {
    String s;
    int a, b, i;

    a = 0;

    s = "" + p.getKey();
    p = p.right;


    while ( p != null ) {
      SkipListEntry q;

      q = p;
      while (q.down != null)
      q = q.down;
      b = q.pos;

      s = s + " <-";


      for (i = a+1; i < b; i++)
      s = s + "--------";

      s = s + "> " + p.getKey();

      a = b;

      p = p.right;
    }

    return(s);
  }

  public void printVertical() {
    String s = "";

    SkipListEntry p;

    p = head;

    while ( p.down != null )
      p = p.down;

    while ( p != null ) {
      s = getOneColumn( p );
      System.out.println(s);

      p = p.right;
    }
  }


  public String getOneColumn( SkipListEntry p ) {
    String s = "";

    while ( p != null ) {
      s = s + " " + p.getKey();

      p = p.up;
    }

    return(s);
  }

}