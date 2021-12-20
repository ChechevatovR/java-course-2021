package expression.exceptions;

import expression.PrioritizedExpression;

public class CheckedPow extends CheckedBinaryOperatorExpression {
    public CheckedPow(PrioritizedExpression left, PrioritizedExpression right) {
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
        if (left == 0 && right == 0) {
            return new FunctionDomainException();
        } if (right < 0) {
            return new UnderflowException();
        }
        int res = 1;
        while (right > 0) {
            ExpressionEvaluationException e = CheckedMultiply.checkStatic(res, left);
            if (e != null) {
                return e;
            }
            res *= left;
            right--;
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

    private static int checkMultiply(int left, int right) {
        ExpressionEvaluationException e = CheckedMultiply.checkStatic(left, right);
        if (e != null) {
            throw e;
        }
        return left * right;
    }

    private static int checkNegate(int operand) {
        ExpressionEvaluationException e = CheckedNegate.checkStatic(operand);
        if (e != null) {
            throw e;
        }
        return -operand;
    }
}
