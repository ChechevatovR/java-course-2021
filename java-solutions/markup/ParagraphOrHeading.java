package markup;

public class ParagraphOrHeading extends AbstractWrapper implements HtmlSerializable, MarkdownSerializable {
    private final int headingLevel;

    public ParagraphOrHeading(final InlineMarkup content, final int headingLevel) {
        super(content);
        this.headingLevel = headingLevel;
    }

    @Override
    public void toMarkdown(final StringBuilder sb) {
        toMarkdown(
                sb,
                "#".repeat(headingLevel) + (headingLevel > 0 ? " " : ""),
                System.lineSeparator().repeat(2)
        );
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        final String tag = headingLevel > 0 ? "h" + headingLevel : "p";
        toHtml(sb, "<" + tag + ">", "</" + tag + ">" + System.lineSeparator());
    }
}
