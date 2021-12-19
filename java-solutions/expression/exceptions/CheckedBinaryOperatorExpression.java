package expression.exceptions;

import expression.PrioritizedExpression;
import util.StringWrapper;

import java.math.BigDecimal;

public abstract class CheckedBinaryOperatorExpression implements PrioritizedExpression {
    protected final PrioritizedExpression operandLeft;
    protected final PrioritizedExpression operandRight;

    protected abstract int apply(int left, int right);

    protected abstract String getOperatorString();

    protected abstract ExpressionEvaluationException check(int left, int right);

    public CheckedBinaryOperatorExpression(PrioritizedExpression left, PrioritizedExpression right) {
        this.operandLeft = left;
        this.operandRight = right;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        throw new UnsupportedOperationException("Only checked integer operations are available");
    }

    private int checkedEvaluate(int left, int right) {
        ExpressionEvaluationException exception = this.check(left, right);
        if (exception == null) {
            return this.apply(left, right);
        } else {
            throw exception;
        }
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.checkedEvaluate(this.operandLeft.evaluate(x, y, z), this.operandRight.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return this.checkedEvaluate(this.operandLeft.evaluate(x), this.operandRight.evaluate(x));
    }

    @Override
    public String toString() {
        return "(" + this.operandLeft + " " + this.getOperatorString() + " " + this.operandRight + ")";
    }

    @Override
    public String toMiniString() {
        final int thisPriority = this.getPriority();
        final int rightPriority = this.operandRight.getPriorityRight();
        return wrapOperand(this.operandLeft, Math.abs(this.operandLeft.getPriorityLeft()) < Math.abs(thisPriority))
                + " " + this.getOperatorString() + " "
                + wrapOperand(this.operandRight, Math.abs(rightPriority) < Math.abs(thisPriority) + (thisPriority > 0 ? 0 : 1));
    }

    private static String wrapOperand(final PrioritizedExpression operand, final boolean condition) {
        return StringWrapper.wrapIf(operand.toMiniString(), "(", ")", condition);
    }

    @Override
    public int hashCode() {
        return this.operandLeft.hashCode() * 2161
            + this.operandRight.hashCode() * 313
            + this.getOperatorString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CheckedBinaryOperatorExpression) {
            CheckedBinaryOperatorExpression that = (CheckedBinaryOperatorExpression) other;
            return this.getOperatorString().equals(that.getOperatorString())
                && this.operandLeft.equals(that.operandLeft)
                && this.operandRight.equals(that.operandRight);
        }
        return false;
    }
}