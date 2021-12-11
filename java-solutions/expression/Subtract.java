package expression;

public class Subtract extends BinaryOperatorExpression {
    public Subtract(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right, (a, b) -> a.subtract(b), "-");
    }

    @Override
    public int getPriority() {
        return -10;
    }
}