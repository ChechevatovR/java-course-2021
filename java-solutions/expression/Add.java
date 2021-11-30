package expression;

public class Add extends BinaryOperatorExpression {
    public Add(Expression left, Expression right) {
        super(left, right, (a, b) -> a.add(b), "+");
    }
    
    @Override
    public String toMiniString() {
        return this.operandLeft.toMiniString()
            + " + "
            + this.operandRight.toMiniString();
    }
}