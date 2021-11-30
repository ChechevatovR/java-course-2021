package expression;

import util.StringWrapper;

import java.math.RoundingMode;

public class Multiply extends BinaryOperatorExpression {
    public Multiply(Expression left, Expression right) {
        super(left, right, (a, b) -> a.multiply(b), "*");
    }
    
    @Override
    public String toMiniString() {
        return StringWrapper.wrapIf(
                this.operandLeft.toMiniString(),
                "(", ")",
                this.operandLeft instanceof Add
                        || this.operandLeft instanceof Subtract
        )
                + " * "
                + StringWrapper.wrapIf(
                this.operandRight.toMiniString(),
                "(", ")",
                this.operandRight instanceof Add
                        || this.operandRight instanceof Subtract
                        || this.operandRight instanceof Divide
        );
    }
}