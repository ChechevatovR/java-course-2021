package markup;

import java.util.List;

public class OrderedList extends AbstractWrapper implements HtmlSerializable {
    public OrderedList(final List<ListItem> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        toHtml(sb, "<ol>", "</ol>");
    }

    @Override
    public void toMarkdown(final StringBuilder sb) {
        throw new UnsupportedOperationException();
    }
}
