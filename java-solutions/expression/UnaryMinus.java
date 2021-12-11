package expression;

public class UnaryMinus extends UnaryOperatorExpression {
    public UnaryMinus(PrioritizedExpression operand) {
        super(operand, a -> a.negate(), "-");
    }

    @Override
    public String toString() {
        return super.toString();

    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE / 2;
    }

    //    @Override
//    public String toMiniString() {
//        if (this.operand instanceof Variable
//                || this.operand instanceof Const
//                || this.operand instanceof UnaryOperatorExpression) {
//            return "- " + this.operand.toMiniString();
//        } else {
//            return "-(" + this.operand.toMiniString() + ")";
//        }
//    }
}
