package expression;

import util.StringWrapper;

import java.beans.Expression;
import java.math.BigDecimal;

public class Multiply extends BinaryOperatorExpression {
    public Multiply(Expression left, Expression right) {
        super(left, right, BigDecimal::multiply, "*");
    }
    
    @Override
    public String toMiniString() {
        return StringWrapper.wrapIf(
                this.operandLeft.toMiniString(),
                "(", ")",
                // :NOTE: instanceof
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