package markup;

public interface MarkdownSerializable extends MaybeSerializable {
    void toMarkdown(StringBuilder sb);
}
