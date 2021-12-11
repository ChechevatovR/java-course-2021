package expression.parser;

class StringSource implements CharSource {
    private final String string;
    private int pos;

    public StringSource(String string) {
        this.string = string;
        this.pos = 0;
    }

    @Override
    public char next() {
        return this.string.charAt(this.pos++);
    }
    
    @Override
    public boolean hasNext() {
        return this.pos < this.string.length();
    }

    @Override
    public IllegalArgumentException error(String message) {
        return new IllegalArgumentException("Error at position " + this.pos + ":" + System.lineSeparator() + message);
    }
}