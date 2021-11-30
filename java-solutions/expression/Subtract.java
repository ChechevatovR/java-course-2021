package expression;

import util.StringWrapper;

public class Subtract extends BinaryOperatorExpression {
    public Subtract(Expression left, Expression right) {
        super(left, right, (a, b) -> a.subtract(b), "-");
    }
    
    @Override
    public String toMiniString() {
        return this.operandLeft.toMiniString()
                + " - "
                + StringWrapper.wrapIf(
                        this.operandRight.toMiniString(),
                        "(", ")",
                        this.operandRight instanceof Subtract
                            || this.operandRight instanceof Add
                );
    }
}