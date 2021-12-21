package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements Parser {
    public ExpressionParser() {
        super();
    }

    @Override
    public PrioritizedExpression parse(String expression) {
        this.source = new StringSource(expression);
        this.take();
        PrioritizedExpression result = this.parseExpression();
        expect(EOI);
        return result;
    }

    private PrioritizedExpression parseExpression() {
        return this.parseShift();
    }

    private PrioritizedExpression parseShift() {
        PrioritizedExpression result = this.parseAddition();
        this.skipWhitespaces();
        while (this.test('<') || this.test('>')) {
            if (this.take('<')) {
                this.expect('<');
                result = new ShiftLeft(result, this.parseAddition());
            } else if (this.take('>')) {
                this.expect('>');
                if (!this.take('>')) {
                    result = new ShiftRightArifm(result, this.parseAddition());
                } else {
                    result = new ShiftRight(result, this.parseAddition());
                }
            }
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
        PrioritizedExpression result = this.parseUnary();
        this.skipWhitespaces();
        while (this.test('*') || this.test('/')) {
            if (this.take('*')) {
                result = new Multiply(result, this.parseUnary());
            } else if (this.take('/')) {
                result = new Divide(result, this.parseUnary());
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

    private PrioritizedExpression parseUnary() {
        this.skipWhitespaces();
        if (this.take('-')) {
            if (this.testBetween('0', '9')) {
                return this.parseValue(true);
            }
            return new UnaryMinus(this.parseUnary());
        }
        if (this.take('l')) {
            this.expect('0');
            return new CountLeadingZeroes(this.parseUnary());
        }
        if (this.take('t')) {
            this.expect('0');
            return new CountTrailingZeroes(this.parseUnary());
        }
        else {
            return this.parseValue(false);
        }
    }

    private PrioritizedExpression parseValue(boolean isNegated) {
        this.skipWhitespaces();
        if (this.take('(')) {
            return this.parseBraces();
        } else if (this.take('0')) {
            return new Const(0);
        } else if (this.test('x') || this.test('y') || this.test('z')) {
            return new Variable("" + this.take());
        } else {
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
