package expression.exceptions;

import expression.PrioritizedExpression;
import expression.Const;
import expression.Variable;
import expression.parser.BaseParser;
import expression.parser.StringSource;

public class ExpressionParser extends BaseParser implements Parser {
    @Override
    public PrioritizedExpression parse(final String expression) throws ExpressionParsingException {
        source = new StringSource(expression);
        take();
        final PrioritizedExpression result = parseExpression();
        expect(EOI);
        return result;
    }

    private PrioritizedExpression parseExpression() throws ExpressionParsingException {
        return parseShift();
    }

    private PrioritizedExpression parseShift() throws ExpressionParsingException {
        PrioritizedExpression result = parseAddition();
        skipWhitespaces();
        while (true) {
            if (take('<')) {
                expect('<');
                result = new CheckedShiftLeft(result, parseAddition());
            } else if (take('>')) {
                expect('>');
                if (!take('>')) {
                    result = new CheckedShiftRightArifm(result, parseAddition());
                } else {
                    result = new CheckedShiftRight(result, parseAddition());
                }
            } else {
                return result;
            }
            skipWhitespaces();
        }
    }

    private PrioritizedExpression parseAddition() throws ExpressionParsingException {
        PrioritizedExpression result = parseMultiplication();
        skipWhitespaces();
        while (test('+') || test('-')) {
            if (take('+')) {
                result = new CheckedAdd(result, parseMultiplication());
            } else if (take('-')) {
                result = new CheckedSubtract(result, parseMultiplication());
            }
            skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parseMultiplication() throws ExpressionParsingException {
        PrioritizedExpression result = parsePowLog();
        skipWhitespaces();
        while (test('*') || test('/')) {
            if (take('*')) {
                result = new CheckedMultiply(result, parsePowLog());
            } else if (take('/')) {
                result = new CheckedDivide(result, parsePowLog());
            }
            skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parsePowLog() throws ExpressionParsingException {
        PrioritizedExpression result = parseUnary();
        skipWhitespaces();
        while (test('*') || test('/')) {
            if (take('*')) {
                if (!take('*')) {
                    revert();
                    return result;
                }
                result = new CheckedPow(result, parseUnary());
            } else if (take('/')) {
                if (!take('/')) {
                    revert();
                    return result;
                }
                result = new CheckedLog(result, parseUnary());
            }
            skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parseBraces() throws ExpressionParsingException {
        final PrioritizedExpression result = parseExpression();
        expect(')');
        return result;
    }

    private PrioritizedExpression parseUnary() throws ExpressionParsingException {
        skipWhitespaces();
        if (take('-')) {
            if (testBetween('0', '9')) {
                return parseConst("-");
            } else {
                return new CheckedNegate(parseUnary());
            }
            // :NOTE: Идентификаторы
        } else if (take('a')) {
            expect("bs");
            final boolean skipped = skipWhitespaces();
            if (take('(')) {
                return new CheckedAbs(parseBraces());
            } else {
                if (!skipped) {
                    throw source.error("Whitespace", "not found any");
                }
                return new CheckedAbs(parseUnary());
            }
        } else if (take('(')) {
            return parseBraces();
        } else if (test('x') || test('y') || test('z')) {
            // :NOTE: Тоже идентификаторы
            return new Variable(String.valueOf(take()));
        } else {
            return parseConst("");
        }
    }

    private Const parseConst(final String s) {
        StringBuilder sb = new StringBuilder(s);
        do {
            sb.append(take());
        } while (testBetween('0', '9'));
        try {
            return new Const(Integer.parseInt(sb.toString()));
        } catch (final NumberFormatException e) {
            // :NOTE: Все же сделать exprected expression, и вероятно не здесь
            throw source.error("int number", sb.toString());
        }
    }

    private boolean skipWhitespaces() {
        boolean skipped = false;
        while (Character.isWhitespace(ch)) {
            skipped = true;
            take();
        }
        return skipped;
    }
}
