public class ArrayDeque<T> {

    private T[] items;
    private int capacity;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        capacity = 8;
        items = (T[]) new Object[capacity];
        nextLast = 0;
        nextFirst = capacity - 1;
        size = 0;
    }

    /**
    * Adds an element to the front of the ArrayDeque.
    *
    * @param item the element to be added to the front of the ArrayDeque
    */
    public void addFirst(T item) {
        if (size() == capacity) {
            resize(2 * capacity);
        } 
        items[nextFirst] = item;
        nextFirst = ((nextFirst - 1) + capacity) % capacity;
        size += 1;
        
    }
    /**
     * Adds an element to the back of the ArrayDeque.
     *
     * @param item the element to be added to the back of the ArrayDeque
     */
    public void addLast(T item) {
        if (size() == capacity) {
            resize(2 * capacity);
        } 
        items[nextLast] = item;
        nextLast = (nextLast + 1) % capacity;
        size += 1;
        
    }
    /**
     * Checks if the ArrayDeque is empty.
     *
     * @return {@code true} if the DLList is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Returns the size of the ArrayDeque.
     *
     * @return the number of elements in the ArrayDeque
     */
    public int size() {
        return size;
    }
    /**
     * Prints the elements of the ArrayDeque from first to last.
     *
     */
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
    }
    /**
     * Removes first element in the front of the ArrayDeque.
     *
     * @return the first element in the front of the ArrayDeque
    */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } 

        if (capacity >= 16 && usageFactor() < 0.25) {
            resize(capacity / 2);
        }
        int index = (nextFirst + 1) % capacity;
        T item = items[index];
        items[index] = null;
        nextFirst = index;
        size -= 1;
        return item;
    }
    /**
     * Removes first element in the back of the ArrayDeque.
     *
     * @return the first element in the back of the ArrayDeque
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            int index = ((nextLast - 1) + capacity) % capacity;
            T item = items[index];
            items[index] = null;
            nextLast = index;
            size -= 1;
            return item;
        }
    }
    /**
     * Retrieves the item at the specified index in the DLList.
     *
     * @param index The zero-based index of the item to retrieve
     * @return The item at the specified index, or null if the index is invalid
     */
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        } else {
            int equivalentIndex = ((nextFirst + 1) % capacity + index) % capacity;
            return items[equivalentIndex];
        }
    }
    /**
     * Resize the ArrayDeque to a new capacity.
     *
     * @param newCapacity The capacity which the ArrayDeque is resized toward
     */
    private void resize(int newCapacity) {
        T[] a = (T[]) new Object[newCapacity];
        int startIndex = (nextFirst + 1) % capacity;
        for(int i = 0; i < size; ++i) {
            a[i] = items[startIndex];
            startIndex = (startIndex + 1) % capacity;
        }
        items = a;
        capacity = newCapacity;
        nextLast = size;
        nextFirst = capacity - 1;
    }
    /**
     * Compute the usage factor of the ArrayDeque.
     *
     * @return The usage factor in double of the ArrayDeque
     */
    private double usageFactor() {
        double factor = (double) size / capacity;
    }
}
