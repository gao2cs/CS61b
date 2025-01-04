import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        for (int i = 0; i < 1000; i += 1) {
            int operation = StdRandom.uniform(4);
            if (operation == 0) {
                int randomInteger = StdRandom.uniform(1000000000);
                studentDeque.addFirst(randomInteger);
                solutionDeque.addFirst(randomInteger);
            } else if (operation == 1) {
                int randomInteger = StdRandom.uniform(1000000000);
                studentDeque.addLast(randomInteger);
                solutionDeque.addLast(randomInteger);
            } else if (operation == 2) {
                if (!solutionDeque.isEmpty()) {
                    Integer a = studentDeque.removeFirst();
                    Integer b = solutionDeque.removeFirst();
                    String message = "removeFirst(), student was " + a + ", correct was " + b;
                    assertEquals(message, b, a);
                }
            } else {
                if (!solutionDeque.isEmpty()) {
                    Integer a = studentDeque.removeLast();
                    Integer b = solutionDeque.removeLast();
                    String message = "removeLast(), student was " + a + ", correct was " + b;
                    assertEquals(message, b, a);
                }
            }
        }
    }
}