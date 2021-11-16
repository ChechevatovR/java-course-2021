package markup;

import java.util.List;

public class Paragraph extends SimpleParent implements MarkdownSerializable, HtmlSerializable {
    public Paragraph(List<InlineMarkup> children) {
        super(children);
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb);
    }
}
