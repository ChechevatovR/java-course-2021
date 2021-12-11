package expression;

import util.StringWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide extends BinaryOperatorExpression {
    public Divide(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right, (a, b) -> a.divide(b, RoundingMode.DOWN), "/");
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
    public BigDecimal evaluate(BigDecimal x) {
        return this.operandLeft.evaluate(x).divide(this.operandRight.evaluate(x));
    }
}