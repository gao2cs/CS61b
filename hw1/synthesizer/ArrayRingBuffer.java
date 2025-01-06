package synthesizer;
import java.util.Iterator;

// This class and all of its methods are public
// This class extends AbstractBoundedQueue<T>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        fillCount = 0;
        first = this.capacity - 1;
        last = 0;
        rb = (T[]) new Object[this.capacity];
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
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = plusOne(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        int firstIndex = plusOne(first);
        T item = rb[firstIndex];
        rb[firstIndex] = null;
        first = firstIndex;
        fillCount -= 1;
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        int firstIndex = plusOne(first);
        return rb[firstIndex];
    }

    /**
     * Return an iterator into current object.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;
        public ArrayRingBufferIterator() {
            pos = plusOne(first);
        }
        /**
         * Check if seer can see the element below him.
         *
         * @return False if seer cannot see the element below him
         */
        @Override
        public boolean hasNext() {
            return pos != last;
        }
        /**
         * Move seer to the top of next element.
         *
         * @return T by asking seer to do so
         */
        @Override
        public T next() {
            T item = rb[pos];
            pos = plusOne(pos);
            return item;
        }
    }
}
