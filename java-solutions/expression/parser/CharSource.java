package expression.parser;

public interface CharSource {

    char cur();

    char next();

    boolean hasNext();

    IllegalArgumentException error(String message);
}