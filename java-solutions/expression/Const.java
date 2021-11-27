package expression;

public class Const implements Expression {
    final int value;
    
    public Const(int value) {
        this.value = value;
    }
    
    @Override
    public int evaluate(int x) {
        return this.value;
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
    
    @Override
    public String toMiniString() {
        return Integer.toString(this.value);
    }
    
    @Override 
    public String toMiniString(boolean printBraces) {
        return this.toMiniString();
    }
    
    @Override
    public int hashCode() {
        return value;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Const) {
            Const that = (Const) other;
            return this.value == that.value;
        }
        return false;
    }
}