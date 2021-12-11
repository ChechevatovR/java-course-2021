package expression;

import util.StringWrapper;

import java.math.BigDecimal;

public abstract class UnaryOperatorExpression implements PrioritizedExpression {
    final PrioritizedExpression operand;
    final UnaryOperator operator;
    final String operatorString;

    public UnaryOperatorExpression(PrioritizedExpression operand, UnaryOperator operator, String operatorString) {
        this.operand = operand;
        this.operator = operator;
        this.operatorString = operatorString;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return this.operator.apply(((BigDecimalExpression) this.operand).evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return this.operator.apply(
                new BigDecimal(((TripleExpression) this.operand).evaluate(x, y, z))
        ).intValue();
    }

    @Override
    public int evaluate(int x) {
        return this.operator.apply(
                new BigDecimal(this.operand.evaluate(x))
        ).intValue();
    }

    @Override
    public String toString() {
        return this.operatorString + "(" + this.operand + ")";
    }

    @Override
    public String toMiniString() {
        return this.operatorString
                + (this.operand instanceof BinaryOperatorExpression ? "" : " ")
                + StringWrapper.wrapIf(
                    this.operand.toMiniString(),
                    "(", ")",
                    this.getPriority() > this.operand.getPriority()
        );
    }

    @Override
    public int hashCode() {
        return this.operand.hashCode() * 313
            + this.operatorString.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof UnaryOperatorExpression) {
            UnaryOperatorExpression that = (UnaryOperatorExpression) other;
            return this.operatorString.equals(that.operatorString)
                && this.operand.equals(that.operand);
        }
        return false;
    }
}