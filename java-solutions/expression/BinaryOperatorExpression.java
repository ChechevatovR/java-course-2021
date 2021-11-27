package expression;

public class BinaryOperatorExpression implements Expression, ToMiniString {
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
    public int evaluate(int x) {
        return this.operator.apply(
            this.operandLeft.evaluate(x),
            this.operandRight.evaluate(x)
        );
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