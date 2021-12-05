package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements Parser {
    public ExpressionParser() {
        super();
    }

    public ExpressionParser(CharSource source) {
        super(source);
    }

    private static TripleExpression negator(TripleExpression expression, boolean condition) {
        if (condition) {
            return new UnaryMinus((Expression) expression);
        } else {
            return expression;
        }
    }

    @Override
    public TripleExpression parse(String expression) {
        this.source = new StringSource(expression);
        this.take();
        TripleExpression result =  this.parseExpression();
        return result;
    }

    private TripleExpression parseExpression() {
        return this.parseAddition();
    }

    private TripleExpression parseAddition() {
        TripleExpression result = this.parseMultiplication();
        this.skipWhitespaces();
        while (this.test('+') || this.test('-')) {
            if (this.take('+')) {
                result = new Add((Expression) result,(Expression) this.parseMultiplication());
            } else if (this.take('-')) {
                result = new Subtract((Expression) result,(Expression) this.parseMultiplication());
            }
            this.skipWhitespaces();
        }
        return result;
    }

    private TripleExpression parseMultiplication() {
        TripleExpression result = this.parseUnary(false);
        this.skipWhitespaces();
        while (this.test('*') || this.test('/')) {
            if (this.take('*')) {
                result = new Multiply((Expression) result,(Expression) this.parseUnary(false));
            } else if (this.take('/')) {
                result = new Divide((Expression) result,(Expression) this.parseUnary(false));
            }
            this.skipWhitespaces();
        }
        return result;
    }

    private TripleExpression parseBraces() {
        TripleExpression result = this.parseExpression();
        this.expect(')');
        return result;
    }

    private TripleExpression parseUnary(boolean isNegated) {
        this.skipWhitespaces();
        if (this.take('-')) {
            return this.parseUnary(!isNegated);
        }
        else {
            return this.parseValue(isNegated);
        }
    }

    private TripleExpression parseValue(boolean isNegated) {
        this.skipWhitespaces();
        if (this.take('(')) {
            return negator(this.parseBraces(), isNegated);
        } else if (this.take('0')) {
            return new Const(0);
        } else if (this.test('x') || this.test('y') || this.test('z')) {
            return negator(new Variable("" + this.take()), isNegated);
        } else {
//            this.testBetween('1', '9');
            StringBuilder sb = new StringBuilder(isNegated ? "-" : "");
            do {
                sb.append(this.take());
            } while (this.testBetween('0', '9'));
            return new Const(Integer.parseInt(sb.toString()));
        }
    }

    private void skipWhitespaces() {
        while (Character.isWhitespace(this.ch)) {
            this.take();
        }
    }
}
