package markup;

public class ParagraphOrHeading extends AbstractWrapper implements HtmlSerializable, MarkdownSerializable {
    private final int headingLevel;

    public ParagraphOrHeading(InlineMarkup content, int headingLevel) {
        super(content);
        this.headingLevel = headingLevel;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(
                sb,
                "#".repeat(this.headingLevel) + (this.headingLevel > 0 ? " " : ""),
                System.lineSeparator().repeat(2)
        );
    }

    @Override
    public void toHtml(StringBuilder sb) {
        String tag = this.headingLevel > 0 ? "h" + this.headingLevel : "p";
        super.toHtml(sb, "<" + tag + ">", "</" + tag + ">" + System.lineSeparator());
    }
}
