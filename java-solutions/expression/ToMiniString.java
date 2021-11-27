package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }

    default String toMiniString(boolean printBraces) {
        if (printBraces) {
            return "(" + this.toMiniString() + ")";
        }
        else {
            return this.toMiniString();
        }
    }
}
