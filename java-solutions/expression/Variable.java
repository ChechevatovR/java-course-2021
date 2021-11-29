package expression;

public class Variable implements Expression {
    final String name;
    
    public Variable(String name) {
        this.name = name;
    }
    
    @Override
    public int evaluate(int x) {
        return x;
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