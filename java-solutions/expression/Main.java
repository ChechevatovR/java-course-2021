package expression;

class Main {
    public static void main(String[] args) {
        Expression expr = new Add(
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
            int x = Integer.parseInt(args[0]);
            int res = expr.evaluate(x);
            System.out.println(res);
        } catch (ArrayIndexOutOfBoundsException|NumberFormatException e) {
            System.err.println("Integer value for variable x expected as the first argument");
        }
    }
}