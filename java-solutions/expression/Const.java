package expression;

import java.math.BigDecimal;

public class Const implements PrioritizedExpression {
    private final Number value;

    public Const(BigDecimal value) {
        this.value = value;
    }

    public Const(int value) {
        this.value = new BigDecimal(value);
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return (BigDecimal) this.value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.value.intValue();
    }

    @Override
    public int evaluate(int x) {
        return this.value.intValue();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public String toMiniString() {
        return this.value.toString();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Const) {
            Const that = (Const) other;
            return this.value.equals(that.value);
        }
        return false;
    }
}