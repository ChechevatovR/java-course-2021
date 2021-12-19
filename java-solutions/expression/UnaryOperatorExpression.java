package expression;

import util.StringWrapper;

import java.math.BigDecimal;

public abstract class UnaryOperatorExpression implements PrioritizedExpression {
    final PrioritizedExpression operand;

    protected abstract int apply(int operand);

    protected abstract BigDecimal apply(BigDecimal operand);

    protected abstract String getOperatorString();

    public UnaryOperatorExpression(PrioritizedExpression operand) {
        this.operand = operand;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return this.apply(this.operand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.apply(this.operand.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return this.apply(this.operand.evaluate(x));
    }

    @Override
    public String toString() {
        return this.getOperatorString() + "(" + this.operand + ")";
    }

    @Override
    public String toMiniString() {
        if (this.operand.getPriority() < this.getPriority()) {
            return this.getOperatorString() + StringWrapper.wrap(this.operand.toMiniString(), "(", ")");
        } else {
            return this.getOperatorString() + " " + this.operand.toMiniString();
        }
    }

    @Override
    public int hashCode() {
        return this.operand.hashCode() * 313
            + this.getOperatorString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof UnaryOperatorExpression) {
            UnaryOperatorExpression that = (UnaryOperatorExpression) other;
            return this.getOperatorString().equals(that.getOperatorString())
                && this.operand.equals(that.operand);
        }
        return false;
    }
}