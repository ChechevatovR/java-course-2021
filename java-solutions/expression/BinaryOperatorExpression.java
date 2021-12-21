package expression;

import util.StringWrapper;
import java.math.BigDecimal;

public abstract class BinaryOperatorExpression implements PrioritizedExpression {
    private final PrioritizedExpression operandLeft;
    private final PrioritizedExpression operandRight;

    protected abstract int apply(int left, int right);

    protected abstract BigDecimal apply(BigDecimal left, BigDecimal right);

    protected abstract String getOperatorString();

    public BinaryOperatorExpression(final PrioritizedExpression left, final PrioritizedExpression right) {
        operandLeft = left;
        operandRight = right;
    }

    @Override
    public BigDecimal evaluate(final BigDecimal x) {
        return apply(
                operandLeft.evaluate(x),
                operandRight.evaluate(x)
        );
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return apply(
                operandLeft.evaluate(x, y, z),
                operandRight.evaluate(x, y, z)
        );
    }

    @Override
    public int evaluate(final int x) {
        return apply(
                operandLeft.evaluate(x),
                operandRight.evaluate(x)
        );
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
        if (other instanceof BinaryOperatorExpression) {
            final BinaryOperatorExpression that = (BinaryOperatorExpression) other;
            return getOperatorString().equals(that.getOperatorString())
                && operandLeft.equals(that.operandLeft)
                && operandRight.equals(that.operandRight);
        }
        return false;
    }
}