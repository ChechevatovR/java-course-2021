package expression;

public class Add extends BinaryOperatorExpression {
    public Add(Expression left, Expression right) {
        super(left, right, (a, b) -> a + b, "+");
    }
    
    @Override
    public String toMiniString() {
        String opLeft = this.operandLeft.toMiniString(false);
        String opRight = this.operandRight.toMiniString(false);
        return opLeft + " + " + opRight;
    }
}