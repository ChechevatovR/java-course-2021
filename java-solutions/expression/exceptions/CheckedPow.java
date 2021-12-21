package expression.exceptions;

import expression.PrioritizedExpression;

public class CheckedPow extends CheckedBinaryOperatorExpression {
    public CheckedPow(final PrioritizedExpression left, final PrioritizedExpression right) {
        super(left, right);
    }

    @Override
    protected String getOperatorString() {
        return "**";
    }

    @Override
    public int getPriority() {
        return 3000;
    }

    @Override
    public int getPriorityRight() {
        return this.getPriority() - 1;
    }

    @Override
    public ExpressionEvaluationException check(int left, int right) {
        if ((left == 0 && right == 0) || right < 0) {
            return new FunctionDomainException();
        }
        int res = 1;
        while (right > 0) {
            if (right % 2 == 0) {
                left = checkedMultiply(left, left);
                right /= 2;
            }
            else {
                res = checkedMultiply(res, left);
                right--;
            }
        }
        return null;
    }

    @Override
    protected int apply(int left, int right) {
        int res = 1;
        while (right > 0) {
            if (right % 2 == 0) {
                left *= left;
                right /= 2;
            }
            else {
                res *= left;
                right--;
            }
        }
        return res;
    }

    private static int checkedMultiply(final int left, final int right) {
        final ExpressionEvaluationException e = CheckedMultiply.checkStatic(left, right);
        if (e != null) {
            throw e;
        }
        return left * right;
    }
}
