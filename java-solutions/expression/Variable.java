package expression;

import java.math.BigDecimal;
import java.util.Map;

public class Variable implements Expression, BigDecimalExpression, TripleExpression {
    static Map<String, Integer> VAR_NAME_NUMBERS = Map.of(
            "x", 0,
            "y", 1,
            "z", 2
    );

    final String name;

    public Variable(String name) {
        this.name = name;
        if (!VAR_NAME_NUMBERS.containsKey(name)) {
            throw new AssertionError("Variable has invalid name");
        }
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
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
        }
        return x; // Must not happen
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