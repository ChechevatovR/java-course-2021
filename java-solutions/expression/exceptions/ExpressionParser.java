package expression.exceptions;

import expression.PrioritizedExpression;
import expression.Const;
import expression.Variable;
import expression.parser.BaseParser;
import expression.parser.StringSource;

public class ExpressionParser extends BaseParser implements expression.exceptions.Parser {
    public ExpressionParser() {
        super();
    }

    @Override
    public PrioritizedExpression parse(String expression) {
        this.source = new StringSource(expression);
        this.take();
        PrioritizedExpression result = this.parseExpression();
        this.expect(EOF);
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
                result = new CheckedShiftLeft(result, this.parseAddition());
            } else if (this.take('>')) {
                this.expect('>');
                if (!this.take('>')) {
                    result = new CheckedShiftRightArifm(result, this.parseAddition());
                } else {
                    result = new CheckedShiftRight(result, this.parseAddition());
                }
            }
            this.skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parseAddition() {
        PrioritizedExpression result = this.parseMultiplication();
        this.skipWhitespaces();
        while (this.test('+') || this.test('-')) {
            if (this.take('+')) {
                result = new CheckedAdd(result, this.parseMultiplication());
            } else if (this.take('-')) {
                result = new CheckedSubtract(result, this.parseMultiplication());
            }
            this.skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parseMultiplication() {
        PrioritizedExpression result = this.parsePowLog();
        this.skipWhitespaces();
        while (this.test('*') || this.test('/')) {
            if (this.take('*')) {
                result = new CheckedMultiply(result, this.parsePowLog());
            } else if (this.take('/')) {
                result = new CheckedDivide(result, this.parsePowLog());
            }
            this.skipWhitespaces();
        }
        return result;
    }

    private PrioritizedExpression parsePowLog() {
        PrioritizedExpression result = this.parseUnary();
        this.skipWhitespaces();
        while (this.test('*') || this.test('/')) {
            if (this.take('*')) {
                if (!this.take('*')) {
                    this.revert();
                    return result;
                }
                result = new CheckedPow(result, this.parseUnary());
            } else if (this.take('/')) {
                if (!this.take('/')) {
                    this.revert();
                    return result;
                }
                result = new CheckedLog(result, this.parseUnary());
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
                this.revert();
                return this.parseValue();
            }
            return new CheckedNegate(this.parseUnary());
        } else if (this.take('a')) {
            this.expect("bs");
            boolean skipped = this.skipWhitespaces();
            if (this.take('(')) {
                return new CheckedAbs(this.parseBraces());
            } else {
                if (!skipped) {
                    throw this.source.error("Expected whitespace after abs, not found any");
                }
                return new CheckedAbs(this.parseUnary());
            }

        } else {
            return this.parseValue();
        }
    }

    private PrioritizedExpression parseValue() {
        this.skipWhitespaces();
        if (this.take('(')) {
            return this.parseBraces();
        } else if (this.take('0')) {
            return new Const(0);
        } else if (this.test('x') || this.test('y') || this.test('z')) {
            return new Variable("" + this.take());
        } else {
            StringBuilder sb = new StringBuilder(this.take('-') ? "-" : "");
            do {
                sb.append(this.take());
            } while (this.testBetween('0', '9'));
            return new Const(Integer.parseInt(sb.toString()));
        }
    }

    private boolean skipWhitespaces() {
        boolean skipped = false;
        while (Character.isWhitespace(this.ch)) {
            skipped = true;
            this.take();
        }
        return skipped;
    }
}
