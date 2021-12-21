package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements Parser {
    @Override
    public PrioritizedExpression parse(final String expression) {
        source = new StringSource(expression);
        take();
        final PrioritizedExpression result = parseExpression();
        expect(EOI);
        return result;
    }

    private PrioritizedExpression parseExpression() {
        return parseShift();
    }

    private PrioritizedExpression parseShift() {
        PrioritizedExpression result = parseAddition();
        skipWhitespaces();
        while (test('<') || test('>')) {
            if (take('<')) {
                expect('<');
                result = new ShiftLeft(result, parseAddition());
            } else if (take('>')) {
                expect('>');
                if (!take('>')) {
                    result = new ShiftRightArifm(result, parseAddition());
                } else {
                    result = new ShiftRight(result, parseAddition());
                }
            }
        }
        return result;
    }

    private PrioritizedExpression parseAddition() {
        PrioritizedExpression result = parseMultiplication();
        skipWhitespaces();
        while (test('+') || test('-')) {
            if (take('+')) {
                result = new Add(result, parseMultiplication());
            } else if (take('-')) {
                result = new Subtract(result, parseMultiplication());
            }
            skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parseMultiplication() {
        PrioritizedExpression result = parseUnary();
        skipWhitespaces();
        while (test('*') || test('/')) {
            if (take('*')) {
                result = new Multiply(result, parseUnary());
            } else if (take('/')) {
                result = new Divide(result, parseUnary());
            }
            skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parseBraces() {
        final PrioritizedExpression result = parseExpression();
        expect(')');
        return result;
    }

    private PrioritizedExpression parseUnary() {
        skipWhitespaces();
        if (take('-')) {
            if (testBetween('0', '9')) {
                return parseValue(true);
            }
            return new UnaryMinus(parseUnary());
        }
        if (take('l')) {
            expect('0');
            return new CountLeadingZeroes(parseUnary());
        }
        if (take('t')) {
            expect('0');
            return new CountTrailingZeroes(parseUnary());
        }
        else {
            return parseValue(false);
        }
    }

    private PrioritizedExpression parseValue(final boolean isNegated) {
        skipWhitespaces();
        if (take('(')) {
            return parseBraces();
        } else if (take('0')) {
            return new Const(0);
        } else if (test('x') || test('y') || test('z')) {
            return new Variable("" + take());
        } else {
            final StringBuilder sb = new StringBuilder(isNegated ? "-" : "");
            do {
                sb.append(take());
            } while (testBetween('0', '9'));
            return new Const(Integer.parseInt(sb.toString()));
        }
    }

    private void skipWhitespaces() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }
}
