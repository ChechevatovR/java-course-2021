package expression;

import java.math.BigDecimal;

public class Const implements Expression, TripleExpression, BigDecimalExpression {
    private BigDecimal value;

    public Const(BigDecimal value) {
        this.value = value;
    }

    public Const(int value) {
        this.value = new BigDecimal(value);
    }

    public Const negate() {
        return new Const(this.value.negate());
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