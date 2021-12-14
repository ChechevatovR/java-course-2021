package expression;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide extends BinaryOperatorExpression {
    public Divide(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return -20;
    }

    @Override
    public int getPriorityRight() {
        return this.getPriority() + 1;
    }

    @Override
    protected int apply(int left, int right) {
        return left / right;
    }

    @Override
    protected BigDecimal apply(BigDecimal left, BigDecimal right) {
        return left.divide(right);
    }

    @Override
    protected String getOperatorString() {
        return "/";
    }
}