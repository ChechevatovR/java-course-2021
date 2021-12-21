package expression.exceptions;

import expression.PrioritizedExpression;

class Main {
    public static void main(String[] args) {
        String inputExpr = "1000000*x*x*x*x*x/(x-1)";
        PrioritizedExpression expr;
        try {
            expr = new ExpressionParser().parse(inputExpr);
        } catch (ExpressionParsingException e) {
            System.err.println("Invalid input: " + e.getMessage());
            return;
        }

        System.out.println("x\tf");
        for (int x = 0; x <= 10; x++) {
            System.out.print(x + "\t");
            try {
                System.out.println(expr.evaluate(x));
            } catch (ExpressionEvaluationException e) {
                System.out.println(e.getMessageAsResult());
            }
        }
    }
}