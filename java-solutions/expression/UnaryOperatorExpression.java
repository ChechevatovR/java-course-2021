package expression;

import util.StringWrapper;

import java.math.BigDecimal;

public abstract class UnaryOperatorExpression implements PrioritizedExpression {
    private final PrioritizedExpression operand;

    protected abstract int apply(int operand);

    protected abstract BigDecimal apply(BigDecimal operand);

    protected abstract String getOperatorString();

    public UnaryOperatorExpression(final PrioritizedExpression operand) {
        this.operand = operand;
    }

    @Override
    public BigDecimal evaluate(final BigDecimal x) {
        return apply(operand.evaluate(x));
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return apply(operand.evaluate(x, y, z));
    }

    @Override
    public int evaluate(final int x) {
        return apply(operand.evaluate(x));
    }

    @Override
    public String toString() {
        return getOperatorString() + "(" + operand + ")";
    }

    @Override
    public String toMiniString() {
        if (operand.getPriority() < getPriority()) {
            return getOperatorString() + StringWrapper.wrap(operand.toMiniString(), "(", ")");
        } else {
            return getOperatorString() + " " + operand.toMiniString();
        }
    }

    @Override
    public int hashCode() {
        return operand.hashCode() * 313
            + getOperatorString().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof UnaryOperatorExpression) {
            final UnaryOperatorExpression that = (UnaryOperatorExpression) other;
            return getOperatorString().equals(that.getOperatorString())
                && operand.equals(that.operand);
        }
        return false;
    }
}