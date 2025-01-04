import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> studentDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solutionDeque = new ArrayDequeSolution<>();
        String log = "";

        for (int i = 0; i < 1000; i += 1) {
            int operation = StdRandom.uniform(4);
            if (operation == 0) {
                studentDeque.addFirst(i);
                solutionDeque.addFirst(i);
                log = log + "addFirst(" + i + ")\n";
            } else if (operation == 1) {
                studentDeque.addLast(i);
                solutionDeque.addLast(i);
                log = log + "addLast(" + i + ")\n";

            } else if (operation == 2) {
                if (!solutionDeque.isEmpty()) {
                    Integer a = studentDeque.removeFirst();
                    Integer b = solutionDeque.removeFirst();
                    log = log + "removeFirst()\n";
                    assertEquals(log, b, a);
                }
            } else {
                if (!solutionDeque.isEmpty()) {
                    Integer a = studentDeque.removeLast();
                    Integer b = solutionDeque.removeLast();
                    log = log + "removeLast()\n";
                    assertEquals(log, b, a);
                }
            }
        }
    }
}