/******************************************************************************
 *  Compilation:  javac Queue.java
 *  Execution:    java Queue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt  
 *
 *  A generic queue, implemented using a linked list.
 *
 *  % java Queue < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 ******************************************************************************/

package br.edu.ifes.ci.si;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The <tt>Fila</tt> class represents a primeiro-in-primeiro-out (FIFO)
  queue of generic items.
 *  It supports the usual <em>enfileira</em> and <em>desenfileira</em>
  operations, along with methods for peeking at the primeiro item,
  testing if the queue is empty, and iterating through
  the items in FIFO order.
  <p>
 *  This implementation uses a singly-linked list with a static nested class for
 *  linked-list nodes. See {@link LinkedQueue} for the version from the
 *  textbook that uses a non-static nested class.
 *  The <em>enfileira</em>, <em>desenfileira</em>, <em>primeiro</em>, <em>tamanho</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Item> the generic type of an item in this queue
 */
public class Fila<Item> implements Iterable<Item> {
    private No<Item> primeiro;    // beginning of queue
    private No<Item> ultimo;     // end of queue
    private int n;               // number of elements on queue

    // helper linked list class
    private static class No<Item> {
        private Item item;
        private No<Item> proximo;
    }

    /**
     * Initializes an empty queue.
     */
    public Fila() {
        primeiro = null;
        ultimo  = null;
        n = 0;
    }

    /**
     * Returns true if this queue is empty.
     *
     * @return <tt>true</tt> if this queue is empty; <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return primeiro == null;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int tamanho() {
        return n;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public Item primeiro() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return primeiro.item;
    }

    /**
     * Adds the item to this queue.
     *
     * @param  item the item to add
     */
    public void enfileira(Item item) {
        No<Item> oldlast = ultimo;
        ultimo = new No<Item>();
        ultimo.item = item;
        ultimo.proximo = null;
        if (isEmpty()) primeiro = ultimo;
        else           oldlast.proximo = ultimo;
        n++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    public Item desenfileira() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = primeiro.item;
        primeiro = primeiro.proximo;
        n--;
        if (isEmpty()) ultimo = null;   // to avoid loitering
        return item;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    } 

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(primeiro);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private No<Item> current;

        public ListIterator(No<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.proximo; 
            return item;
        }
    }


    /**
     * Unit tests the <tt>Fila</tt> data type.
     */
    /*public static void main(String[] args) {
        Fila<String> queue = new Fila<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                queue.enfileira(item);
            else if (!queue.isEmpty())
                System.out.print(queue.desenfileira() + " ");
        }
        System.out.println("(" + queue.tamanho() + " left on queue)");
    }*/
}
