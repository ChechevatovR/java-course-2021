package expression;

import java.math.BigDecimal;

public class ShiftRight extends BinaryOperatorExpression {
    public ShiftRight(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right, (a, b) -> new BigDecimal(a.intValue() >>> b.intValue()), ">>>");
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public int getPriorityRight() {
        return this.getPriority() - 1;
    }
}
