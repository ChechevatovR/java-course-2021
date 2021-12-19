package expression.parser;

public interface CharSource {
    char next();

    boolean hasNext();

    char revert();

    IllegalArgumentException error(String message);
}