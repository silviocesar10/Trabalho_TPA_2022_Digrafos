/******************************************************************************
 *  Compilation:  javac IndexMinPQ.java
 *  Execution:    java IndexMinPQ
 *  Dependencies: StdOut.java
 *
 *  Minimum-oriented indexed PQ implementation using a binary heap.
 *
 ******************************************************************************/

package br.edu.ifes.ci.si;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The <tt>FilaPrioridadeMinIndex</tt> class represents an indexed priority queue of generic chaves.
 *  It supports the usual <em>insere</em> and <em>remove-the-minimum</em>
 *  operations, along with <em>remove</em> and <em>altera-the-key</em> 
  methods. In order to let the client refer to chaves on the priority queue,
  an integer between 0 and maxN-1 is associated with each key&mdash;the client
  uses this integer to specify which key to remove or altera.
  It also supports methods for peeking at the minimum key,
  testing if the priority queue is empty, and iterating through
  the chaves.
  <p>
  This implementation uses a binary heap along with an array to associate
  chaves with integers in the given range.
  The <em>insere</em>, <em>remove-the-minimum</em>, <em>remove</em>,
 *  <em>altera-key</em>, <em>decrease-key</em>, and <em>increase-key</em>
 *  operations take logarithmic time.
 *  The <em>is-empty</em>, <em>tamanho</em>, <em>min-index</em>, <em>min-key</em>, and <em>key-of</em>
 *  operations take constant time.
 *  Construction takes time proportional to the specified capacity.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Key> the generic type of key on this priority queue
 */
public class FilaPrioridadeMinIndex<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int maxN;        // maximum number of elements on PQ
    private int n;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing
    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private Key[] chaves;      // chaves[i] = priority of i

    /**
     * Initializes an empty indexed priority queue with indices between <tt>0</tt>
     * and <tt>maxN - 1</tt>.
     * @param  maxN the chaves on this priority queue are index from <tt>0</tt>
     *         <tt>maxN - 1</tt>
     * @throws IllegalArgumentException if <tt>maxN</tt> &lt; <tt>0</tt>
     */
    public FilaPrioridadeMinIndex(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        chaves = (Key[]) new Comparable[maxN + 1];    // make this of length maxN??
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   // make this of length maxN??
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return <tt>true</tt> if this priority queue is empty;
     *         <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Is <tt>i</tt> an index on this priority queue?
     *
     * @param  i an index
     * @return <tt>true</tt> if <tt>i</tt> is an index on this priority queue;
     *         <tt>false</tt> otherwise
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     */
    public boolean contem(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    /**
     * Returns the number of chaves on this priority queue.
     *
     * @return the number of chaves on this priority queue
     */
    public int tamanho() {
        return n;
    }

    /**
     * Associates key with index <tt>i</tt>.
     *
     * @param  i an index
     * @param  key the key to associate with index <tt>i</tt>
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if there already is an item associated
     *         with index <tt>i</tt>
     */
    public void insere(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (contem(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        qp[i] = n;
        pq[n] = i;
        chaves[i] = key;
        swim(n);
    }

    /**
     * Returns an index associated with a minimum key.
     *
     * @return an index associated with a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int minIndex() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    /**
     * Returns a minimum key.
     *
     * @return a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key minChave() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return chaves[pq[1]];
    }

    /**
     * Removes a minimum key and returns its associated index.
     * @return an index associated with a minimum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public int removeMin() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        troca(1, n--);
        sink(1);
        assert min == pq[n+1];
        qp[min] = -1;        // remove
        chaves[min] = null;  // to help with garbage collection
        pq[n+1] = -1;        // not needed
        return min;
    }

    /**
     * Returns the key associated with index <tt>i</tt>.
     *
     * @param  i the index of the key to return
     * @return the key associated with index <tt>i</tt>
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public Key chaveDe(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contem(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return chaves[i];
    }

    /**
     * Change the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to altera
     * @param  key altera the key associated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void alteraChave(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contem(i)) throw new NoSuchElementException("index is not in the priority queue");
        chaves[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    /**
     * Change the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to altera
     * @param  key altera the key associated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @deprecated Replaced by {@link #changeKey(int, Key)}.
     */
    public void altera(int i, Key key) {
        alteraChave(i, key);
    }

    /**
     * Decrease the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to decrease
     * @param  key decrease the key associated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if key &ge; key associated with index <tt>i</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void diminuiChave(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contem(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (chaves[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        chaves[i] = key;
        swim(qp[i]);
    }

    /**
     * Increase the key associated with index <tt>i</tt> to the specified value.
     *
     * @param  i the index of the key to increase
     * @param  key increase the key associated with index <tt>i</tt> to this key
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws IllegalArgumentException if key &le; key associated with index <tt>i</tt>
     * @throws NoSuchElementException no key is associated with index <tt>i</tt>
     */
    public void aumentaChave(int i, Key key) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contem(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (chaves[i].compareTo(key) >= 0)
            throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
        chaves[i] = key;
        sink(qp[i]);
    }

    /**
     * Remove the key associated with index <tt>i</tt>.
     *
     * @param  i the index of the key to remove
     * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
     * @throws NoSuchElementException no key is associated with index <t>i</tt>
     */
    public void remove(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        if (!contem(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        troca(index, n--);
        swim(index);
        sink(index);
        chaves[i] = null;
        qp[i] = -1;
    }


   /***************************************************************************
    * General helper functions.
    ***************************************************************************/
    private boolean maior(int i, int j) {
        return chaves[pq[i]].compareTo(chaves[pq[j]]) > 0;
    }

    private void troca(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }


   /***************************************************************************
    * Heap helper functions.
    ***************************************************************************/
    private void swim(int k) {
        while (k > 1 && maior(k/2, k)) {
            troca(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && maior(j, j+1)) j++;
            if (!maior(k, j)) break;
            troca(k, j);
            k = j;
        }
    }


   /***************************************************************************
    * Iterators.
    ***************************************************************************/

    /**
     * Returns an iterator that iterates over the chaves on the
 priority queue in ascending order.
     * The iterator doesn't implement <tt>remove()</tt> since it's optional.
     *
     * @return an iterator that iterates over the chaves in ascending order
     */
    public Iterator<Integer> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private FilaPrioridadeMinIndex<Key> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no chaves move
        public HeapIterator() {
            copy = new FilaPrioridadeMinIndex<Key>(pq.length - 1);
            for (int i = 1; i <= n; i++)
                copy.insere(pq[i], chaves[pq[i]]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.removeMin();
        }
    }


    /**
     * Unit tests the <tt>FilaPrioridadeMinIndex</tt> data type.
     */
    public static void main(String[] args) {
        // insere a bunch of strings
        String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

        FilaPrioridadeMinIndex<String> pq = new FilaPrioridadeMinIndex<String>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            pq.insere(i, strings[i]);
        }

        // remove and print each key
        while (!pq.isEmpty()) {
            int i = pq.removeMin();
            System.out.println(i + " " + strings[i]);
        }
        System.out.println();

        // reinsert the same strings
        for (int i = 0; i < strings.length; i++) {
            pq.insere(i, strings[i]);
        }

        // print each key using the iterator
        for (int i : pq) {
            System.out.println(i + " " + strings[i]);
        }
        while (!pq.isEmpty()) {
            pq.removeMin();
        }

    }
}
