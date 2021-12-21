package markup;

import java.util.List;

public class UnorderedList extends AbstractWrapper implements HtmlSerializable {
    public UnorderedList(final List<ListItem> children) {
        super(new SimpleParent(children));
    }

    @Override
    public void toHtml(final StringBuilder sb) {
        toHtml(sb, "<ul>", "</ul>");
    }

    @Override
    public void toMarkdown(final StringBuilder sb) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
