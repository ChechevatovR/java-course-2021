package expression;

public class Subtract extends BinaryOperatorExpression {
    public Subtract(Expression left, Expression right) {
        super(left, right, (a, b) -> a - b, "-");
    }
    
    @Override
    public String toMiniString() {
        String opLeft = this.operandLeft.toMiniString(false);
        String opRight = this.operandRight.toMiniString(
            this.operandRight instanceof Subtract 
            || this.operandRight instanceof Add
        );
        return opLeft + " - " + opRight;
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