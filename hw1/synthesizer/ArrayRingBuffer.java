// TODO: Make sure to make this class a part of the synthesizer package
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
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
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
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
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
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
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
        // TODO: Return the first item. None of your instance variables should change.
        int firstIndex = plusOne(first);
        return rb[firstIndex];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
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
