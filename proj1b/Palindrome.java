public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    private boolean helper(Deque<Character> d) {
        if (d.size() < 2) {
            return true;
        }
        Character front = d.removeFirst();
        Character back = d.removeLast();
        return front.equals(back) &&helper(d);
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        return helper(d);
    }

    private boolean helperWithCharacterComparator(Deque<Character> d, CharacterComparator cc) {
        if (d.size() < 2) {
            return true;
        }
        Character front = d.removeFirst();
        Character back = d.removeLast();
        return cc.equalChars(front, back) && helperWithCharacterComparator(d, cc);
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        return helperWithCharacterComparator(d, cc);
    }
}
