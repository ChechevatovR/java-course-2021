package expression.exceptions;

import expression.PrioritizedExpression;
import util.StringWrapper;

import java.math.BigDecimal;

public abstract class CheckedUnaryOperatorExpression implements PrioritizedExpression {
    private final PrioritizedExpression operand;

    public CheckedUnaryOperatorExpression(final PrioritizedExpression operand) {
        this.operand = operand;
    }

    protected abstract String getOperatorString();

    protected abstract int apply(int operand);

    protected abstract ExpressionEvaluationException check(int operand);

    public int checkedApply(final int operand) throws ExpressionEvaluationException {
        final ExpressionEvaluationException e = check(operand);
        if (e != null) {
            throw e;
        }
        return apply(operand);
    }

    @Override
    public BigDecimal evaluate(final BigDecimal x) {
        throw new UnsupportedOperationException("Only checked integer operations are available");
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return checkedApply(operand.evaluate(x, y, z));
    }

    @Override
    public int evaluate(final int x) {
        return checkedApply(operand.evaluate(x));
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
        if (other instanceof CheckedUnaryOperatorExpression) {
            final CheckedUnaryOperatorExpression that = (CheckedUnaryOperatorExpression) other;
            return getOperatorString().equals(that.getOperatorString())
                && operand.equals(that.operand);
        }
        return false;
    }
}