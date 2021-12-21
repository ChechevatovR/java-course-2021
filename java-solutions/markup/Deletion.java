package markup;

import java.util.List;

public class Deletion extends AbstractWrapper implements InlineMarkup {
    public Deletion(final List<InlineMarkup> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(final StringBuilder sb) {
        toMarkdown(sb, "}}", "{{");
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        toHtml(sb, "<del>", "</del>");
    }
}
