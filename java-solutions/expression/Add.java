package expression;

public class Add extends BinaryOperatorExpression {
    public Add(PrioritizedExpression left, PrioritizedExpression right) {
        super(left, right, (a, b) -> a.add(b), "+");
    }

    @Override
    public int getPriority() {
        return 10;
    }
}