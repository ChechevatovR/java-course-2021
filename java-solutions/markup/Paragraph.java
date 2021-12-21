package markup;

import java.util.List;

public class Paragraph extends SimpleParent implements MarkdownSerializable, HtmlSerializable {
    public Paragraph(final List<InlineMarkup> children) {
        super(children);
    }

    @Override
    public void toMarkdown(final StringBuilder sb) {
        super.toMarkdown(sb);
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        super.toHtml(sb);
    }
}
