package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements Parser {
    public ExpressionParser() {
        super();
    }

    private static PrioritizedExpression negator(PrioritizedExpression expression, boolean condition) {
        if (condition) {
            return new UnaryMinus(expression);
        } else {
            return expression;
        }
    }

    @Override
    public PrioritizedExpression parse(String expression) {
        this.source = new StringSource(expression);
        this.take();
        PrioritizedExpression result = this.parseExpression();
        expect(EOF);
        return result;
    }

    private PrioritizedExpression parseExpression() {
        return this.parseShift();
    }

    private PrioritizedExpression parseShift() {
        PrioritizedExpression result = this.parseAddition();
        this.skipWhitespaces();
        if (this.take('<')) {
            this.expect('<');
            return new ShiftLeft(result, this.parseAddition());
        }
        else if (this.take('>')) {
            this.expect('>');
            if (!this.take('>')) {
                return new ShiftRightArifm(result, this.parseAddition());
            }
            return new ShiftRight(result, this.parseAddition());
        }
        return result;
    }

    private PrioritizedExpression parseAddition() {
        PrioritizedExpression result = this.parseMultiplication();
        this.skipWhitespaces();
        while (this.test('+') || this.test('-')) {
            if (this.take('+')) {
                result = new Add(result, this.parseMultiplication());
            } else if (this.take('-')) {
                result = new Subtract(result, this.parseMultiplication());
            }
            this.skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parseMultiplication() {
        PrioritizedExpression result = this.parseUnary(false);
        this.skipWhitespaces();
        while (this.test('*') || this.test('/')) {
            if (this.take('*')) {
                result = new Multiply(result, this.parseUnary(false));
            } else if (this.take('/')) {
                result = new Divide(result, this.parseUnary(false));
            }
            this.skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parseBraces() {
        PrioritizedExpression result = this.parseExpression();
        this.expect(')');
        return result;
    }

    private PrioritizedExpression parseUnary(boolean isNegated) {
        this.skipWhitespaces();
        if (this.take('-')) {
            return this.parseUnary(!isNegated);
        }
        if (this.take('l')) {
            this.expect('0');
            return new CountLeadingZeroes(this.parseUnary(isNegated));
        }
        if (this.take('t')) {
            this.expect('0');
            return new CountTrailingZeroes(this.parseUnary(isNegated));
        }
        else {
            return this.parseValue(isNegated);
        }
    }

    private PrioritizedExpression parseValue(boolean isNegated) {
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
