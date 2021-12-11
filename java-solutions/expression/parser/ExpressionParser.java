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
            return new UnaryMinus((PrioritizedExpression) expression);
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
        return this.parseShift();
    }

    private TripleExpression parseShift() {
        TripleExpression result = this.parseAddition();
        this.skipWhitespaces();
        if (this.take('<')) {
            this.expect('<');
            return new ShiftLeft((PrioritizedExpression) result, (PrioritizedExpression) this.parseAddition());
        }
        else if (this.take('>')) {
            this.expect('>');
            if (!this.take('>')) {
                return new ShiftRightArifm((PrioritizedExpression) result,(PrioritizedExpression) this.parseAddition());
            }
            return new ShiftRight((PrioritizedExpression) result, (PrioritizedExpression) this.parseAddition());
        }
        return result;
    }

    private TripleExpression parseAddition() {
        TripleExpression result = this.parseMultiplication();
        this.skipWhitespaces();
        while (this.test('+') || this.test('-')) {
            if (this.take('+')) {
                result = new Add((PrioritizedExpression) result, (PrioritizedExpression) this.parseMultiplication());
            } else if (this.take('-')) {
                result = new Subtract((PrioritizedExpression) result, (PrioritizedExpression) this.parseMultiplication());
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
                result = new Multiply((PrioritizedExpression) result,(PrioritizedExpression) this.parseUnary(false));
            } else if (this.take('/')) {
                result = new Divide((PrioritizedExpression) result, (PrioritizedExpression) this.parseUnary(false));
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
        if (this.take('l')) {
            this.expect('0');
            return new CountLeadingZeroes((PrioritizedExpression) this.parseUnary(isNegated));
        }
        if (this.take('t')) {
            this.expect('0');
            return new CountTrailingZeroes((PrioritizedExpression) this.parseUnary(isNegated));
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
