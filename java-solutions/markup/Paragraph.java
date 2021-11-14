package markup;

public class Paragraph extends AbstractWrapper implements HtmlSerializable, MarkdownSerializable {
    private int headingLevel = 0;

    public Paragraph(InlineMarkup content) {
        super(content);
    }

    public static Paragraph fromMdString(String s, int offset) {
        int level = 0;
        while (level < 6 && s.length() > level && s.charAt(level) == '#') {
            level++;
        }

        if (s.length() == level || !Character.isWhitespace(s.charAt(level))) {
            level = 0;
        }

        Paragraph result = new Paragraph(TextWithInlines.fromMdString(
                s,
                offset + level + (level > 0 ? 1 : 0),
                false,
                false,
                false,
                false,
                null
        ).first);
        result.headingLevel = level;
        return result;
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
