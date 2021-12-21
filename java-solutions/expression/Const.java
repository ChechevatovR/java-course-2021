package expression;

import java.math.BigDecimal;

public class Const implements PrioritizedExpression {
    private final Number value;

    public Const(final BigDecimal value) {
        this.value = value;
    }

    public Const(final int value) {
        this.value = value;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public BigDecimal evaluate(final BigDecimal x) {
        return (BigDecimal) value;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return value.intValue();
    }

    @Override
    public int evaluate(final int x) {
        return value.intValue();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public String toMiniString() {
        return value.toString();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof Const) {
            final Const that = (Const) other;
            return value.equals(that.value);
        }
        return false;
    }
}