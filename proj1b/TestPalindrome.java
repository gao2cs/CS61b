import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
    }

    @Test
    public void testIsPalinedromeOffByOne() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("abwab", cc));
        assertTrue(palindrome.isPalindrome("bija", cc));
        assertTrue(palindrome.isPalindrome("climb", cc));
        assertTrue(palindrome.isPalindrome("detrude", cc));
        assertFalse(palindrome.isPalindrome("za", cc));
        assertFalse(palindrome.isPalindrome("ae", cc));
        assertFalse(palindrome.isPalindrome("aa", cc));
    }
    @Test
    public void testIsPalinedromeOffByN() {
        CharacterComparator cc = new OffByN(5);
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("couth", cc));
        assertTrue(palindrome.isPalindrome("fink", cc));
        assertTrue(palindrome.isPalindrome("bidding", cc));
        assertTrue(palindrome.isPalindrome("binding", cc));
        assertTrue(palindrome.isPalindrome("bg", cc));
        assertTrue(palindrome.isPalindrome("af", cc));
        assertFalse(palindrome.isPalindrome("climb", cc));
        assertFalse(palindrome.isPalindrome("detrude", cc));
        assertFalse(palindrome.isPalindrome("racecar", cc));
        assertFalse(palindrome.isPalindrome("noon", cc));
    }
}
