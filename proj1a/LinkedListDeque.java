public class LinkedListDeque<T> {

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        private Node (T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
    /**
     * Adds an element to the front of the DLList.
     *
     * @param item the element to be added to the front of the list
     */
    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }
    /**
     * Adds an element to the back of the DLList.
     *
     * @param item the element to be added to the back of the list
     */
    public void addLast(T item) {
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }
    /**
     * Checks if the DLList is empty.
     *
     * @return {@code true} if the DLList is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /**
     * Returns the size of the DLList.
     *
     * @return the number of elements in the DLList
     */
    public int size() {
        return size;
    }

    /**
     * Prints the elements of the DLList from first to last.
     *
     * This method starts at the front element of the DLList and traverse the list, printing each item seperated by white space on the same line
     */
    public void printDeque() {
        if (isEmpty()) {
            return;
        } else {
            Node ptr = sentinel.next;
            while (ptr != sentinel) {
                System.out.print(ptr.item + " ");
                ptr = ptr.next;
            }
        }
    }
    /**
     * Removes first element in the front of the DLList.
     *
     * @return the first element in the front of the DLList
    */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T item = sentinel.next.item;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size -= 1;
            return item;
        }
    }
    /**
     * Removes first element in the back of the DLList.
     *
     * @return the first element in the back of the DLList
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            T item  = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
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
            Node ptr = sentinel.next;
            while (index > 0) {
                ptr = ptr.next;
                index -= 1;
            }
            return ptr.item;
        }
    }
}