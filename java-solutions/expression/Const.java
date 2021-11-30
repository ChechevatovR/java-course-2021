package expression;

import java.math.BigDecimal;

public class Const implements Expression, TripleExpression, BigDecimalExpression {
    final BigDecimal value;

    public Const(Object o) {
        if (o instanceof Integer) {
            o = new BigDecimal((int) o);
        }
        this.value = (BigDecimal) o;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return this.value;
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