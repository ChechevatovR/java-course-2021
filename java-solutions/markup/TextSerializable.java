package markup;

public interface TextSerializable {
    void toMarkdown(StringBuilder sb);

    void toHtml(StringBuilder sb);
}
