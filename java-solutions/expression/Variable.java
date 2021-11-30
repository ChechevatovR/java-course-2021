package expression;

import java.math.BigDecimal;

public class Variable implements Expression, BigDecimalExpression, TripleExpression {
    final String name;
    
    public Variable(String name) {
        this.name = name;
        if (!name.equals("x") && !name.equals("y") && !name.equals("z")) {
            throw new AssertionError("Variable has unsupported name. Only x, y, z are supported");
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