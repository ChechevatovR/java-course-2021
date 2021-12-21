package expression.exceptions;

import expression.PrioritizedExpression;
import util.StringWrapper;

import java.math.BigDecimal;

public abstract class CheckedBinaryOperatorExpression implements PrioritizedExpression {
    protected final PrioritizedExpression operandLeft;
    protected final PrioritizedExpression operandRight;

    public CheckedBinaryOperatorExpression(final PrioritizedExpression left, final PrioritizedExpression right) {
        operandLeft = left;
        operandRight = right;
    }

    protected abstract int apply(int left, int right);

    protected abstract ExpressionEvaluationException check(int left, int right);

    protected abstract String getOperatorString();

    public int checkedApply(final int left, final int right) throws ExpressionEvaluationException {
        final ExpressionEvaluationException e = check(left, right);
        if (e != null) {
            throw e;
        }
        return apply(left, right);
    }

    @Override
    public BigDecimal evaluate(final BigDecimal x) {
        throw new UnsupportedOperationException("Only checked integer operations are available");
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return checkedApply(operandLeft.evaluate(x, y, z), operandRight.evaluate(x, y, z));
    }

    @Override
    public int evaluate(final int x) {
        return checkedApply(operandLeft.evaluate(x), operandRight.evaluate(x));
    }

    @Override
    public String toString() {
        return "(" + operandLeft + " " + getOperatorString() + " " + operandRight + ")";
    }

    @Override
    public String toMiniString() {
        final int thisPriority = getPriority();
        final int rightPriority = operandRight.getPriorityRight();
        return wrapOperand(operandLeft, Math.abs(operandLeft.getPriorityLeft()) < Math.abs(thisPriority))
                + " " + getOperatorString() + " "
                + wrapOperand(operandRight, Math.abs(rightPriority) < Math.abs(thisPriority) + (thisPriority > 0 ? 0 : 1));
    }

    private static String wrapOperand(final PrioritizedExpression operand, final boolean condition) {
        return StringWrapper.wrapIf(operand.toMiniString(), "(", ")", condition);
    }

    @Override
    public int hashCode() {
        return operandLeft.hashCode() * 2161
            + operandRight.hashCode() * 313
            + getOperatorString().hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof CheckedBinaryOperatorExpression) {
            final CheckedBinaryOperatorExpression that = (CheckedBinaryOperatorExpression) other;
            return getOperatorString().equals(that.getOperatorString())
                && operandLeft.equals(that.operandLeft)
                && operandRight.equals(that.operandRight);
        }
        return false;
    }
}