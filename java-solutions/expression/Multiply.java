package expression;

public class Multiply extends BinaryOperatorExpression {
    public Multiply(Expression left, Expression right) {
        super(left, right, (a, b) -> a * b, "*");
    }
    
    @Override
    public String toMiniString() {
        String opLeft = this.operandLeft.toMiniString(
            this.operandLeft instanceof Add 
            || this.operandLeft instanceof Subtract 
        );
        String opRight = this.operandRight.toMiniString(
            this.operandRight instanceof Add 
            || this.operandRight instanceof Subtract
            || this.operandRight instanceof Divide
        );
        return opLeft + " * " + opRight;
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