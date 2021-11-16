package markup;

import java.util.List;

public class Insertion extends AbstractWrapper implements InlineMarkup {
    public Insertion(List<InlineMarkup> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "<<", ">>");
    }

    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<ins>", "</ins>");
    }
}
