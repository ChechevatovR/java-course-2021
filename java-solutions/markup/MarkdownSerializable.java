package markup;

public interface MarkdownSerializable extends HtmlSerializable {
    void toMarkdown(StringBuilder sb);
}
