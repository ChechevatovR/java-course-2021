package expression;

import java.math.BigDecimal;

public class Add extends BinaryOperatorExpression {
    public Add(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    protected BinaryOperator getOperator() {
        return BigDecimal::add;
    }

    @Override
    protected String getOperatorString() {
        return "+";
    }
}