package expression.exceptions;

import expression.PrioritizedExpression;
import util.StringWrapper;

import java.math.BigDecimal;

public abstract class CheckedUnaryOperatorExpression implements PrioritizedExpression {
    final PrioritizedExpression operand;

    public CheckedUnaryOperatorExpression(PrioritizedExpression operand) {
        this.operand = operand;
    }

    protected abstract String getOperatorString();

    protected abstract int apply(int operand);

    protected abstract ExpressionEvaluationException check(int operand);

    public int checkedApply(int operand) throws ExpressionEvaluationException {
        ExpressionEvaluationException e = this.check(operand);
        if (e != null) {
            throw e;
        }
        return this.apply(operand);
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        throw new UnsupportedOperationException("Only checked integer operations are available");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.checkedApply(this.operand.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return this.checkedApply(this.operand.evaluate(x));
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
        if (other instanceof CheckedUnaryOperatorExpression) {
            CheckedUnaryOperatorExpression that = (CheckedUnaryOperatorExpression) other;
            return this.getOperatorString().equals(that.getOperatorString())
                && this.operand.equals(that.operand);
        }
        return false;
    }
}