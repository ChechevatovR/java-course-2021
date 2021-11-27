package expression;

public class Divide extends BinaryOperatorExpression {
    public Divide(Expression left, Expression right) {
        super(left, right, (a, b) -> a / b, "/");
    }
    
    @Override
    public String toMiniString() {
        String opLeft = this.operandLeft.toMiniString(
            this.operandLeft instanceof Add
            || this.operandLeft instanceof Subtract
        );
        String opRight = this.operandRight.toMiniString(true);
        return opLeft + " / " + opRight;
    }

    @Override
    public String toMiniString(boolean printBraces) {
        String res = this.toMiniString();
        if (printBraces) {
            return "(" + res + ")";
        } else {
            return res;
        }
    }
}