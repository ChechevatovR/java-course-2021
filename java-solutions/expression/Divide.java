package expression;

import util.StringWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide extends BinaryOperatorExpression {
    public Divide(Expression left, Expression right) {
        super(left, right, (a, b) -> a.divide(b, RoundingMode.DOWN), "/");
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return ((BigDecimalExpression) this.operandLeft).evaluate(x)
                .divide(((BigDecimalExpression) this.operandRight).evaluate(x));
    }

    @Override
    public String toMiniString() {
        return StringWrapper.wrapIf(
                        this.operandLeft.toMiniString(),
                        "(", ")",
                        this.operandLeft instanceof Add
                                || this.operandLeft instanceof Subtract
        )
                + " / "
                + StringWrapper.wrapIf(
                        this.operandRight.toMiniString(),
                        "(", ")",
                        this.operandRight instanceof Add
                                || this.operandRight instanceof Subtract
                                || this.operandRight instanceof Multiply
                                || this.operandRight instanceof Divide
        );
    }
}