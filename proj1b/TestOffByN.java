import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByN(5);

    // Your tests go here.
    @Test
    public void TestequalChars() {
        assertTrue(offByOne.equalChars('b', 'g'));
        assertTrue(offByOne.equalChars('c', 'h'));
        assertTrue(offByOne.equalChars('i', 'n'));
        assertTrue(offByOne.equalChars('k', 'p'));
        assertTrue(offByOne.equalChars('t', 'y'));
        assertFalse(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('a', 'd'));
        assertFalse(offByOne.equalChars('a', 'e'));
    }
}
