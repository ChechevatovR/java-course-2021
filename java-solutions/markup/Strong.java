package markup;

import java.util.List;

public class Strong extends AbstractWrapper implements InlineMarkup {
    public Strong(final List<InlineMarkup> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(final StringBuilder sb) {
        toMarkdown(sb, "__", "__");
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        toHtml(sb, "<strong>", "</strong>");
    }
}