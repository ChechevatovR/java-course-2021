package expression;

import util.StringWrapper;

import java.math.BigDecimal;

public abstract class BinaryOperatorExpression implements PrioritizedExpression {
    protected final PrioritizedExpression operandLeft;
    protected final PrioritizedExpression operandRight;
    private final BinaryOperator operator;
    private final String operatorString;
    
    public BinaryOperatorExpression(PrioritizedExpression left, PrioritizedExpression right, BinaryOperator operator, String operatorString) {
        this.operandLeft = (PrioritizedExpression) left;
        this.operandRight = (PrioritizedExpression) right;
        this.operator = operator;
        this.operatorString = operatorString;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return this.operator.apply(
            ((BigDecimalExpression) this.operandLeft).evaluate(x),
            ((BigDecimalExpression) this.operandRight).evaluate(x)
        );
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.operator.apply(
                new BigDecimal(((TripleExpression) this.operandLeft).evaluate(x, y, z)),
                new BigDecimal(((TripleExpression) this.operandRight).evaluate(x, y, z))
        ).intValue();
    }

    @Override
    public int evaluate(int x) {
        return this.operator.apply(
                new BigDecimal(this.operandLeft.evaluate(x)),
                new BigDecimal(this.operandRight.evaluate(x))
        ).intValue();
    }

    @Override
    public String toString() {
        return "(" + operandLeft.toString() + " " + operatorString + " " + operandRight.toString() + ")";
    }

    @Override
    public String toMiniString() {
        return StringWrapper.wrapIf(
                this.operandLeft.toMiniString(),
                "(", ")",
                Math.abs(this.operandLeft.getPriorityLeft()) < Math.abs(this.getPriority())
        )
                + " " + this.operatorString + " "
                + StringWrapper.wrapIf(
                this.operandRight.toMiniString(),
                "(", ")",
                this.getPriority() > 0
                        ? Math.abs(this.operandRight.getPriorityRight()) < Math.abs(this.getPriority())
                        : Math.abs(this.operandRight.getPriorityRight()) <= Math.abs(this.getPriority())
        );
    }

    @Override
    public int hashCode() {
        return this.operandLeft.hashCode() * 2161
            + this.operandRight.hashCode() * 313
            + this.operatorString.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BinaryOperatorExpression) {
            BinaryOperatorExpression that = (BinaryOperatorExpression) other;
            return this.operatorString.equals(that.operatorString)
                && this.operandLeft.equals(that.operandLeft)
                && this.operandRight.equals(that.operandRight);
        }
        return false;
    }
}