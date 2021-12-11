package expression;

import java.math.BigDecimal;

public class ShiftRightArifm extends BinaryOperatorExpression {
    public ShiftRightArifm(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public int getPriorityRight() {
        return this.getPriority() - 1;
    }

    @Override
    protected BinaryOperator getOperator() {
        return (a, b) -> new BigDecimal(a.intValue() >> b.intValue());
    }

    @Override
    protected String getOperatorString() {
        return ">>";
    }
}
