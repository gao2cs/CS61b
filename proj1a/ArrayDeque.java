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
        nextFirst = minusOne(nextFirst);
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
        nextLast = plusOne(nextLast);
        size += 1;
    }
    /**
     * Checks if the ArrayDeque is empty.
     *
     * @return {@code true} if the ArrayDeque is empty, {@code false} otherwise
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
        } else {
            int index = plusOne(nextFirst);
            for (int i = 0; i < size(); ++i) {
                System.out.print(items[index] + " ");
                index = plusOne(index);
            }
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
        int index = plusOne(nextFirst);
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
        }
        if (capacity >= 16 && usageFactor() < 0.25) {
            resize(capacity / 2);
        }
        int index = minusOne(nextLast);
        T item = items[index];
        items[index] = null;
        nextLast = index;
        size -= 1;
        return item;
    }
    /**
     * Retrieves the item at the specified index in the ArrayDeque.
     *
     * @param index The zero-based index of the item to retrieve
     * @return The item at the specified index, or null if the index is invalid
     */
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        } else {
            int equivalentIndex = (plusOne(nextFirst) + index) % capacity;
            return items[equivalentIndex];
        }
    }
    /**
     * Resize the ArrayDeque to a new capacity.
     *
     * @param newCapacity The capacity toward which ArrayDeque is resized
     */
    private void resize(int newCapacity) {
        T[] a = (T[]) new Object[newCapacity];
        int startIndex = plusOne(nextFirst);
        for (int i = 0; i < size; ++i) {
            a[i] = items[startIndex];
            startIndex = plusOne(startIndex);
        }
        items = a;
        capacity = newCapacity;
        nextLast = size;
        nextFirst = capacity - 1;
    }
    /**
     * Compute the usage factor of the ArrayDeque.
     *
     * @return The usageFactor in double
     */
    private double usageFactor() {
        return (double) size / capacity;
    }
    /**
     * Increments the given index by one with wrapping around.
     *
     * @param index the current index in the array
     * @return the next index, wrapped around if it exceeds the array's capacity
     */
    private int plusOne(int index) {
        return (index + 1) % capacity;
    }
    /**
     * Decrements the given index by one with wrapping around.
     *
     * @param index the current index in the array
     * @return the previous index, wrapped around if it goes below 0
     */
    private int minusOne(int index) {
        return (index - 1 + capacity) % capacity;
    }
}
