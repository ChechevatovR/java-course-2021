package expression;

import java.math.BigDecimal;
import java.util.Set;

public class Variable implements PrioritizedExpression {
    private static final Set<String> VAR_NAME_NUMBERS = Set.of("x", "y", "z");

    private final String name;

    public Variable(String name) {
        this.name = name;
        if (!VAR_NAME_NUMBERS.contains(name)) {
            throw new AssertionError("Variable has invalid name");
        }
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public BigDecimal evaluate(BigDecimal x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (this.name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new AssertionError("Variable got an invalid name on evaluation time");
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String toMiniString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Variable) {
            Variable that = (Variable) other;
            return this.name.equals(that.name);
        }
        return false;
    }
}