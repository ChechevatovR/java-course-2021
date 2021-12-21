package expression;

class Main {
    public static void main(final String[] args) {
        final Expression expr = new Add(
            new Subtract(
                new Multiply(
                    new Variable("x"),
                    new Variable("x")
                ),
                new Multiply(
                    new Const(2),
                    new Variable("x")
                )
            ),
            new Const(1)
        );
        
        try {
            final int x = Integer.parseInt(args[0]);
            final int res = expr.evaluate(x);
            System.out.println(res);
        } catch (final ArrayIndexOutOfBoundsException|NumberFormatException e) {
            System.err.println("Integer value for variable x expected as the first argument");
        }
    }
}