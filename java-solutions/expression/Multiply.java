package expression;

import java.math.BigDecimal;

public class Multiply extends BinaryOperatorExpression {
    public Multiply(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 20;
    }


    @Override
    protected BinaryOperator getOperator() {
        return BigDecimal::multiply;
    }

    @Override
    protected String getOperatorString() {
        return "*";
    }
}