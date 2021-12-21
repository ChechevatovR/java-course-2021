package expression.exceptions;

import expression.PrioritizedExpression;

class Main {
    public static void main(final String[] args) {
        final String inputExpr = "1000000*x*x*x*x*x/(x-1)";
        final PrioritizedExpression expr;
        try {
            expr = new ExpressionParser().parse(inputExpr);
        } catch (final ExpressionParsingException e) {
            System.err.println("Invalid input: " + e.getMessage());
            return;
        }

        System.out.println("x\tf");
        for (int x = 0; x <= 10; x++) {
            System.out.print(x + "\t");
            try {
                System.out.println(expr.evaluate(x));
            } catch (final ExpressionEvaluationException e) {
                System.out.println(e.getMessageAsResult());
            }
        }
    }
}