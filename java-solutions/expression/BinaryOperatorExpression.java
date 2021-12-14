package expression;

import util.StringWrapper;
import java.math.BigDecimal;

public abstract class BinaryOperatorExpression implements PrioritizedExpression {
    private final PrioritizedExpression operandLeft;
    private final PrioritizedExpression operandRight;

    protected abstract int apply(int left, int right);

    protected abstract BigDecimal apply(BigDecimal left, BigDecimal right);

    protected abstract String getOperatorString();

    public BinaryOperatorExpression(PrioritizedExpression left, PrioritizedExpression right) {
        this.operandLeft = left;
        this.operandRight = right;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return this.apply(
                this.operandLeft.evaluate(x),
                this.operandRight.evaluate(x)
        );
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.apply(
                this.operandLeft.evaluate(x, y, z),
                this.operandRight.evaluate(x, y, z)
        );
    }

    @Override
    public int evaluate(int x) {
        return this.apply(
                this.operandLeft.evaluate(x),
                this.operandRight.evaluate(x)
        );
    }

    @Override
    public String toString() {
        return "(" + this.operandLeft + " " + this.getOperatorString() + " " + this.operandRight + ")";
    }

    @Override
    public String toMiniString() {
        final int priority = getPriority();
        final int right = operandRight.getPriorityRight();
        return toMinistring(operandLeft, Math.abs(operandLeft.getPriorityLeft()) < Math.abs(priority))
                + " " + this.getOperatorString() + " "
                + toMinistring(operandRight, Math.abs(right) < Math.abs(priority) + (getPriority() > 0 ? 0 : 1));
    }

    private String toMinistring(final PrioritizedExpression operand, final boolean condition) {
        return StringWrapper.wrapIf(
                operand.toMiniString(),
                "(", ")",
                condition
        );
    }

    @Override
    public int hashCode() {
        return this.operandLeft.hashCode() * 2161
            + this.operandRight.hashCode() * 313
            + this.getOperatorString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BinaryOperatorExpression) {
            BinaryOperatorExpression that = (BinaryOperatorExpression) other;
            return this.getOperatorString().equals(that.getOperatorString())
                && this.operandLeft.equals(that.operandLeft)
                && this.operandRight.equals(that.operandRight);
        }
        return false;
    }
}