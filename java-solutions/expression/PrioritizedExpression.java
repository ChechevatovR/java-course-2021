package expression;

public interface PrioritizedExpression extends Expression, TripleExpression, BigDecimalExpression, ToMiniString {
    default int getPriority() {
        return Integer.MIN_VALUE;
    }

    default int getPriorityLeft() {
        return getPriority();
    }

    default int getPriorityRight() {
        return getPriority();
    }
}
