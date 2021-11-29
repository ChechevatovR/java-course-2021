package util;

import java.util.function.Predicate;

public class IsWordCharacter implements Predicate<Character> {
    @Override
    public boolean test(Character c) {
        return Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'';
    }
}
