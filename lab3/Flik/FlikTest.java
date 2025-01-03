import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {

    /**
     * Performs a few arbitrary tests to see if the isSameNumber method is correct.
     */
    @Test
    public void TestIsSameNumber() {
        assertTrue(Flik.isSameNumber((Integer) 5, (Integer) 5));
        assertTrue(Flik.isSameNumber((Integer) (-6), (Integer) (-6)));
        assertTrue(Flik.isSameNumber((Integer) 128, (Integer) 128));
    }
}
