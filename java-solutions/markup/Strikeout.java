package markup;

import java.util.List;

public class Strikeout extends AbstractWrapper implements InlineMarkup {
    public Strikeout(final List<InlineMarkup> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(final StringBuilder sb) {
        toMarkdown(sb, "~", "~");
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        toHtml(sb, "<s>", "</s>");
    }
}
