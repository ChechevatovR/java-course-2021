package markup;

public interface MaybeSerializable {
    void toMarkdown(StringBuilder sb) throws UnsupportedOperationException;

    void toHtml(StringBuilder sb) throws UnsupportedOperationException;
}
