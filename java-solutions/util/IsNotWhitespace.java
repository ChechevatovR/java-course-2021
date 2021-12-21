package util;

import java.util.function.Predicate;

public class IsNotWhitespace implements Predicate<Character> {
    @Override
    public boolean test(final Character c) {
        return !Character.isWhitespace(c);
    }
}
