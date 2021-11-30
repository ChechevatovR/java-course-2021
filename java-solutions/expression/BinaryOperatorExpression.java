package expression;

import java.math.BigDecimal;

public class BinaryOperatorExpression implements Expression, ToMiniString, BigDecimalExpression, TripleExpression {
    final Expression operandLeft;
    final Expression operandRight;
    final BinaryOperator operator;
    final String operatorString;
    
    public BinaryOperatorExpression(Expression left, Expression right, BinaryOperator operator, String operatorString) {
        this.operandLeft = left;
        this.operandRight = right;
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
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.operandLeft.toString());
        sb.append(" ").append(this.operatorString).append(" ");
        sb.append(this.operandRight.toString());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        return this.toString();
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