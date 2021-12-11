package expression;

public class Multiply extends BinaryOperatorExpression {
    public Multiply(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right, (a, b) -> a.multiply(b), "*");
    }

    @Override
    public int getPriority() {
        return 20;
    }
}